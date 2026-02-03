package czg.scenes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedSet;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Der Szenen-Stapel enthält beliebig viele Szenen (siehe {@link BaseScene}), die übereinander
 * angezeigt werden. Dabei kann, dem Stapel-Modell folgend, immer nur eine neue Szene
 * über allen anderen hinzugefügt, sowie nur die oberste Szene entfernt werden.
 * <br><b>WICHTIG:</b> Wird in einem Durchlauf eine Szene entfernt, wird deren {@link BaseScene#update()}-Methode möglicherweise
 * trotzdem noch einmal ausgeführt, da der nun geänderte Szenen-Stapel erst beim nächsten durchlauf
 * von der {@link #update()} berücksichtigt wird.
 */
public class SceneStack extends JPanel {

    /**
     * Die einzige Instanz des Szenen-Stapels
     */
    public static final SceneStack INSTANCE = new SceneStack();

    /**
     * Eigene Liste mit Szenen weil contentPane.getComponents()
     */
    private final List<BaseScene> scenes = new ArrayList<>();

    /**
     * Cache, der für jeden Index in der {@link #scenes}-Liste eine Liste
     * mit den Tags von allen darüber liegenden Szenen speichert
     */
    private final List<SequencedSet<String>> overlyingTagsCache = new ArrayList<>();

    /**
     * Aktualisiert {@link #overlyingTagsCache}
     * @param belowIndex Alle Szenen mit einem Index kleiner als dieser werden berücksichtigt
     */
    private void updateOverlyingTagsCache(int belowIndex) {
        for (int i = belowIndex-1; i >= 0; i--) {
            // Set nehmen
            SequencedSet<String> tags = overlyingTagsCache.get(i);

            // Tags sammeln
            for(int j = i+1; j < scenes.size(); j++) {
                tags.addAll(scenes.get(j).tags);
            }
        }

    }

    /**
     * Zeigt eine weitere Szene über allen bestehenden Szenen an
     * @param scene Beliebige Szene
     */
    public void push(BaseScene scene) {
        // Ggf. letzte Szene verdecken
        BaseScene last = getTop();
        if(last != null) {
            last.isCovered = true;
        }

        // Neues Set für Tags anlegen
        overlyingTagsCache.add(new LinkedHashSet<>());
        // Cache aktualisieren: alle Szenen außer die oberste
        updateOverlyingTagsCache(scenes.size()-1);

        // In die Liste aufnehmen
        scenes.add(scene);
    }

    /**
     * Entfernt die oberste Szene
     */
    public void pop() {
        BaseScene last = getTop();
        if(last != null) {
            // Aus der Liste entfernen & unload() ausführen
            scenes.removeLast().unload();

            // Aktualisieren
            last = getTop();
            // Nicht mehr bedecken
            if(last != null)
                last.isCovered = false;

            // Set mit Tags entfernen
            overlyingTagsCache.removeLast();
            // Cache aktualisieren: alle Szenen
            updateOverlyingTagsCache(scenes.size());
        } else
            System.err.println("Es wurde versucht, eine Szene zu entfernen, obwohl keine Szenen mehr auf dem Stapel sind!");
    }

    /**
     * Fügt {@code szene} in den Stapel ein, sodass {@code scenes.get(i) == scene}.
     * Alle Szenen ab der, die vorher an der Stelle war, an welcher die neue Szene
     * eingefügt wird, werden um eine Stelle nach hinten/oben geschoben.
     * @param scene Einzufügende Szene
     * @param index Stelle, an welcher die Szene eingefügt werden soll. Zwischen {@code 0} und {@code scenes.size()}, jeweils inklusiv.
     */
    public void insert(BaseScene scene, int index) {
        // Ganz oben einfügen: An push() weitergeben
        if(index >= scenes.size()) {
            push(scene);
            return;
        }

        // Irgendwo innerhalb des Stapels einfügen
        if(index >= 0) {
            scenes.add(index, scene);
            return;
        }

        // Neues Set für die neu eingefügte Szene einfügen
        overlyingTagsCache.add(index, new LinkedHashSet<>());
        // Cache aktualisieren: alle Szenen ab der, die eingefügt wurde (inklusiv)
        updateOverlyingTagsCache(index+1);

        System.err.printf("Kann keine Szene an Stelle %d einfügen%n", index);
    }

    /**
     * Findet die erste Szene {@code s} im Stapel, für die {@code s == toBeReplaced} gilt,
     * und ersetzt diese durch {@code replacement}
     * @param toBeReplaced Zu ersetzende Szene
     * @param replacement Szene, die an die Stelle der zu ersetzenden Szene tritt
     */
    public void replace(BaseScene toBeReplaced, BaseScene replacement) {
        for (int i = 0; i < scenes.size(); i++) {
            // toBeReplaced suchen...
            if(scenes.get(i) == toBeReplaced) {
                // ... und ersetzen
                scenes.set(i, replacement);
                // unload() ausführen
                toBeReplaced.unload();

                // Cache aktualisieren: alle Szenen ab der, die neu eingefügt wurde (inklusiv)
                updateOverlyingTagsCache(i+1);

                return;
            }
        }

        System.err.printf("Es wurde versucht, %s im Szenen-Stapel zu ersetzten, obwohl diese Szene nicht darin vorkommt%n", toBeReplaced);
    }

    /**
     * Findet die erste Szene {@code s} im Stapel, für die {@code s == toBeRemoved} gilt,
     * und entfernt diese. Ggf. wird {@link BaseScene#isCovered} der darunterliegenden Szene
     * auf {@code false} gesetzt.
     * @param toBeRemoved Die zu entfernende Szene
     */
    public void remove(BaseScene toBeRemoved) {
        for(int i = 0; i < scenes.size(); i++) {
            if(scenes.get(i) == toBeRemoved) {
                if(i == scenes.size()-1) {
                    // Ggf. die darunterliegende Szene nicht mehr verdecken
                    pop();
                } else {
                    // Entfernen & unload() ausführen
                    scenes.remove(i).unload();

                    // Cache aktualisieren: alle Szenen, die unter der eben entfernten liegen/lagen
                    updateOverlyingTagsCache(i);
                }

                return;
            }
        }

        System.err.printf("Es wurde versucht, %s aus dem Szenen-Stapel zu entfernen, obwohl diese Szene nicht darin vorkommt%n", toBeRemoved);
    }

    /**
     * Szene ganz oben auf dem Stapel abfragen
     * @return Szene oben auf dem Stapel
     */
    private BaseScene getTop() {
        return scenes.isEmpty() ? null : scenes.getLast();
    }


    /**
     * Iteriert über {@link #scenes} bzw. eine Kopie davon. Ermittelt für
     * jede Szene die effektiven {@link CoverSettings.Rules} und
     * bestimmt mithilfe der {@code settingExtractor}-Funtktion, ob der
     * {@code processor} für diese Szene aufgerufen werden soll.
     * @param settingExtractor {@link Function}, die eine {@link CoverSettings.Setting} aus einem Satz {@link CoverSettings.Rules} abfragt
     * @param processor {@link Consumer}, der ggf. auf die Szene angewendet wird
     * @param useCopy Ob eine Kopie von {@link #scenes} oder die Liste selbst verwendet werden soll
     */
    private void processScenes(
            Function<CoverSettings.Rules, CoverSettings.Setting> settingExtractor,
            Consumer<BaseScene> processor,
            boolean useCopy
    ) {
        // Es wird mit einer Kopie der scenes-Liste gearbeitet, da diese
        // sich ändern kann (z.B. durch Code in den update()-Methoden von
        // Objekten), während sie hier eigentlich noch durchlaufen wird.
        // Würde hier die scenes-Liste direkt benutzt werden, könnte es
        // so zu einer ConcurrentModificationException kommen!
        List<BaseScene> iterList = useCopy ? new ArrayList<>(scenes) : scenes;
        for (int i = 0; i < iterList.size(); i++) {
            BaseScene scene = iterList.get(i);

            boolean filterSetting = settingExtractor.apply(
                    scene.coverSettings.getEffectiveRules(overlyingTagsCache.get(i))
            ).toBoolean();

            if(! (scene.isCovered && filterSetting))
                processor.accept(scene);
        }
    }

    /**
     * Logik-Code der einzelnen Szenen ausführen
     */
    public void update() {
        processScenes(CoverSettings.Rules::coverPausesLogic, BaseScene::update, true);
    }

    /**
     * Zeichnet alle Szenen des Stapels. Die erste Szene in der Liste wird zuerst,
     * die letzte zuletzt, also über allen anderen, gezeichnet.
     * @param graphics Grafik-Objekt. Wird von Java bereitgestellt.
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics;

        // Anti-aliasing aktivieren (Text nicht pixelig darstellen)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Alle Szenen zeichnen, die nicht verdeckt und so eingestellt sind,
        // dass sie deshalb ausgeblendet sein sollte.
        processScenes(CoverSettings.Rules::coverDisablesDrawing, scene -> scene.draw(g2), false);
    }

}
