package czg.objects.minigame;

import czg.objects.BaseObject;
import czg.scenes.BaseScene;

import java.awt.*;
import java.util.*;
import java.util.List;

import static czg.MainWindow.*;

import czg.scenes.minigame.LevelScene;
import czg.util.Images;

/**
 * Das Chemie-Minigame als BaseObject.
 */
public class ChemieGameObject extends BaseObject {
    private final Map<String, Image> blockBilder = new HashMap<>();
    private List<Reagenzglas> glaeser;
    private int ausgewaehltesGlas = -1;
    private final LevelScene levelSzene;
    private final int level;

    // Timer
    private int verbleibendeSekunden;
    private long letzteZeitAktualisierung;

    //Konstruktor
    public ChemieGameObject(int level, LevelScene levelSzene) {
        super(null, 0, 0, WIDTH, HEIGHT);
        this.level = level;
        this.levelSzene = levelSzene;
        bilderLaden();
        levelErstellen();
    }

    private static final int ABSTAND = -40;
    private static final int SPIEL_X = 140;
    private static final int SPIEL_Y = 200;
    private static final int SPIEL_BREITE = 560;

    private void levelErstellen() {
        String[] farben = switch (level) {
            case 0 -> new String[]{"Blue", "Green", "Pink"};
            case 1 -> new String[]{"Blue", "Green", "Pink", "Purple"};
            default -> new String[]{"Blue", "Green", "Pink", "Purple", "Yellow"};
        };

        List<String> alle = new ArrayList<>();
        for (String f : farben)
            for (int i = 0; i < Reagenzglas.MAX_KAPAZITAET; i++) alle.add(f);
        Collections.shuffle(alle);

        glaeser = new ArrayList<>();
        int anzahl = farben.length + 2; // +2 leere Gläser
        int gesamtBreite = anzahl * Reagenzglas.GLAS_BREITE + (anzahl - 1) * ABSTAND;
        int startX = SPIEL_X + (SPIEL_BREITE - gesamtBreite) / 2;

        for (int i = 0; i < farben.length; i++) {
            Reagenzglas g = new Reagenzglas(
                    Images.get("/assets/minigames/chemistry/ChemieTesttubeSimple.png"),
                    startX + i * (Reagenzglas.GLAS_BREITE + ABSTAND),
                    SPIEL_Y,
                    Reagenzglas.GLAS_BREITE, Reagenzglas.GLAS_HOEHE,
                    blockBilder
            );
            for (int j = 0; j < Reagenzglas.MAX_KAPAZITAET; j++)
                g.blockHinzufuegen(alle.removeFirst());
            glaeser.add(g);
        }

        for (int i = farben.length; i < anzahl; i++) {
            glaeser.add(new Reagenzglas(
                    Images.get("/assets/minigames/chemistry/ChemieTesttubeSimple.png"),
                    startX + i * (Reagenzglas.GLAS_BREITE + ABSTAND),
                    SPIEL_Y,
                    Reagenzglas.GLAS_BREITE, Reagenzglas.GLAS_HOEHE,
                    blockBilder
            ));
        }

        verbleibendeSekunden = 30;
        letzteZeitAktualisierung = System.currentTimeMillis();
        ausgewaehltesGlas = -1;
    }

    private static final String[] ALLE_FARBEN = {"Blue", "Green", "Pink", "Purple", "Yellow"};

    private void bilderLaden() {
        for (String f : ALLE_FARBEN)
            blockBilder.put(f, Images.get("/assets/minigames/chemistry/" + f + "ColorDefaultBlock.png"));
    }

    private void mausGeklickt(int geklickt) {
        if (ausgewaehltesGlas == -1) {
            if (!glaeser.get(geklickt).istLeer()) {
                ausgewaehltesGlas = geklickt;
                glaeser.get(geklickt).setAusgewaehlt(true);
            }
        } else if (ausgewaehltesGlas == geklickt) {
            auswahlAufheben();
        } else {
            umschuetten(ausgewaehltesGlas, geklickt);
            auswahlAufheben();
            if (istGewonnen()) levelSzene.levelWon();
        }
    }

    @Override
    public void update(BaseScene scene) {
        // Timer
        long jetzt = System.currentTimeMillis();
        if (jetzt - letzteZeitAktualisierung >= 1000) {
            verbleibendeSekunden--;
            letzteZeitAktualisierung = jetzt;
            if (verbleibendeSekunden <= 0) {
                levelSzene.levelLost();
                return;
            }
        }

        // Klick-Erkennung
        for (int i = 0; i < glaeser.size(); i++) {
            if (glaeser.get(i).isClicked()) {
                mausGeklickt(i);
                break;
            }
        }
    }


    private void umschuetten(int von, int nach) {
        Reagenzglas v = glaeser.get(von);
        Reagenzglas n = glaeser.get(nach);
        String farbe = v.oberstenBlockAnschauen();
        if (farbe == null || !n.kannAblegen(farbe)) return;
        while (v.oberstenBlockAnschauen() != null
                && v.oberstenBlockAnschauen().equals(farbe)
                && n.kannAblegen(farbe))
            n.blockHinzufuegen(v.oberstenBlockEntfernen());
    }

    private void auswahlAufheben() {
        if (ausgewaehltesGlas != -1)
            glaeser.get(ausgewaehltesGlas).setAusgewaehlt(false);
        ausgewaehltesGlas = -1;
    }

    private boolean istGewonnen() {
        for (Reagenzglas g : glaeser)
            if (!g.istLeer() && !g.istGeloest()) return false;
        return true;
    }


    @Override
    public void draw(Graphics2D g) {
        // Pixel-Art Rendering
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // Titel
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.drawString("Chemie-Minigame Level " + (level + 1), SPIEL_X + 10, SPIEL_Y - 30);

        // Hinweistext
        g.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g.drawString("Klicke auf ein Glas um es auszuwählen, klicke auf ein weiteres um umzuschütten.",
                SPIEL_X + 10, SPIEL_Y - 14);

        // Timer
        g.setFont(new Font("Monospaced", Font.BOLD, 18));
        g.setColor(verbleibendeSekunden <= 10 ? new Color(255, 60, 60) : new Color(100, 255, 100));
        g.drawString("Zeit: " + verbleibendeSekunden + "s", SPIEL_X + SPIEL_BREITE - 80, SPIEL_Y - 35);

        // Gläser zeichnen
        for (Reagenzglas g2 : glaeser)
            g2.draw(g);
    }




}