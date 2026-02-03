package czg.scenes;

import czg.objects.BaseObject;
import czg.scenes.cover_settings.CoverSettings;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedSet;
import java.util.stream.Collectors;

/**
 * Eine Szene besteht aus einem Hintergrund und einer beliebigen Menge von
 * sich darauf bewegenden Objekten.
 */
public abstract class BaseScene {

    /**
     * Liste der Objekte in diese Szene
     */
    public final List<BaseObject> objects = new ArrayList<>();

    /**
     * Liste der Tags, die diese Szene hat. <b>Sollte nur beim Erstellen der Szene
     * (d.h. im Konstruktor) festgelegt werden.</b>
     */
    public final SequencedSet<String> tags = new LinkedHashSet<>();

    /**
     * Ob die Szene verdeckt ist
     */
    public boolean isCovered = false;

    /**
     * Einstellungen, wenn die Szene von anderen verdeckt ist
     */
    public final CoverSettings coverSettings;

    /**
     * Szene erstellen. Alle {@code CoverSettings}-Einstellungen werden
     * auf {@code false} gesetzt.
     */
    protected BaseScene() {
        this(new CoverSettings(false, false, false));
    }

    /**
     * Szene mit {@code CoverSettings} erstellen. Das Erstellen dieser
     * Einstellungen kann über verkettete Funktionsaufrufe erfolgen. Siehe
     * Dokumentation ({@code Fenster_Szenen_Objekte.md}).
     * @param coverSettings {@code CoverSettings}-Objekte
     */
    protected BaseScene(CoverSettings coverSettings) {
        this.coverSettings = coverSettings;
    }

    /**
     * Alle Objekte abfragen, die mit dem gegebenen überlappen (damit "kollidieren")
     * @param object Das Objekt, welches überprüft werden soll
     * @return Die Liste von kollidierenden Objekten
     */
    public List<BaseObject> getOverlappingObjects(BaseObject object) {
        Rectangle2D refHitbox = object.getHitbox();

        if(refHitbox == null)
            return new ArrayList<>(0);

        return objects.stream()
                .filter(obj -> {
                    if(obj == object)
                        return false;

                    Rectangle2D hitbox = obj.getHitbox();
                    return hitbox != null && hitbox.intersects(refHitbox);
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Wird aufgerufen, wenn die Szene aus dem Szenen-Stapel entfernt wird
     */
    public void unload() {}

    /**
     * Ruft {@link BaseObject#update(BaseScene)} für jedes Objekt in der {@link #objects}-Liste auf.
     * Übergibt diese Szene als Parameter.
     */
    public void update() {
        // Ähnlich wie bei SceneStack wird hier erst eine Kopie von objects angelegt.
        new ArrayList<>(objects).forEach(object -> object.update(this));
    }


    /**
     * Zeichnet den Hintergrund und alle Objekte der Szene
     * @param g Grafik-Objekt. Wird vom Szenen-Stapel bereitgestellt.
     * @see SceneStack#paintComponent(Graphics)
     */
    public void draw(Graphics2D g) {
        // Objekte zeichnen
        objects.forEach(o -> o.draw(g));
    }


    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
