package czg.objects.minigame;

import czg.objects.BaseObject;
import czg.scenes.BaseScene;
import czg.util.Input;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

import static czg.MainWindow.*;

import czg.scenes.minigame.LevelScene;
import czg.util.Images;

/**
 * Das Chemie-Minigame als BaseObject.
 */
public class ChemieGameObject extends BaseObject {
    // Konstanten
    private static final int SCALE = 4;
    private static final int BLOCK_W_ORIG = 7;
    private static final int BLOCK_H_ORIG = 5;
    private static final int BLOCK_W = BLOCK_W_ORIG * SCALE;
    private static final int BLOCK_H = BLOCK_H_ORIG * SCALE;
    private static final int GLAS_BREITE = 32 * SCALE;
    private static final int GLAS_HOEHE = 32 * SCALE;
    private static final int BLOCK_X_OFFSET = 11 * SCALE;
    private static final int BLOCK_BODEN_Y = 31 * SCALE;
    private static final int ABSTAND = -40;

    // Spielbereich
    private static final int SPIEL_X = 140;
    private static final int SPIEL_Y = 200;
    private static final int SPIEL_BREITE = 560;
    // Farbnamen
    private static final String[] ALLE_FARBEN =
            {"Blue", "Green", "Pink", "Purple", "Yellow"};
    private static final int MAX_KAPAZITAET = 4;//Spielzustand
    private List<Reagenzglas> glaeser;
    private int ausgewaehltesGlas = -1;
    private final int level;
    private final LevelScene levelSzene;

    // Timer
    private int verbleibendeSekunden = 30;
    private long letzteZeitAktualisierung = System.currentTimeMillis();
    // Bilder
    private Image reagenzglasBild;
    private final Map<String, Image> blockBilder = new HashMap<>();
    // Maus-Status
    private boolean warGedrueckt = false;

    // Innere Klasse: Reagenzglas
    private static class Reagenzglas {
        private final List<String> bloecke = new ArrayList<>();
        private boolean ausgewaehlt = false;

        public void blockHinzufuegen(String farbe) {
            if (bloecke.size() < MAX_KAPAZITAET) {
                bloecke.add(farbe);
            }
        }

        public String oberstenBlockAnschauen() {
            return bloecke.isEmpty() ? null : bloecke.getLast();
        }

        public String oberstenBlockEntfernen() {
            return bloecke.isEmpty() ? null : bloecke.removeLast();
        }

        public boolean istLeer() {
            return bloecke.isEmpty();
        }

        public boolean istVoll() {
            return bloecke.size() >= MAX_KAPAZITAET;
        }

        public boolean istGeloest() {
            if (bloecke.size() != MAX_KAPAZITAET) return false;
            String f = bloecke.getFirst();
            for (String b : bloecke) if (!b.equals(f)) return false;
            return true;
        }

        public boolean kannAblegen(String farbe) {
            if (istVoll()) return false;
            return istLeer() || oberstenBlockAnschauen().equals(farbe);
        }

        public List<String> getBloecke() {
            return Collections.unmodifiableList(bloecke);
        }

        public void setAusgewaehlt(boolean a) {
            ausgewaehlt = a;
        }

        public boolean isAusgewaehlt() {
            return ausgewaehlt;
        }
    }

    //Konstruktor
    public ChemieGameObject(int level, LevelScene levelSzene) {
        super(null, 0, 0, WIDTH, HEIGHT);
        this.level = level;
        this.levelSzene = levelSzene;
        bilderLaden();
        levelErstellen();
    }

    //Level erstellen
    private void levelErstellen() {
        String[] farben = switch (level) {
            case 0 -> new String[]{"Blue", "Green", "Pink"};
            case 1 -> new String[]{"Blue", "Green", "Pink", "Purple"};
            default -> new String[]{"Blue", "Green", "Pink", "Purple", "Yellow"};
        };
        List<String> alle = new ArrayList<>();
        for (String f : farben)
            for (int i = 0; i < MAX_KAPAZITAET; i++) alle.add(f);
        Collections.shuffle(alle);
        glaeser = new ArrayList<>();
        for (String ignored : farben) {
            Reagenzglas g = new Reagenzglas();
            for (int j = 0; j < MAX_KAPAZITAET; j++)
                g.blockHinzufuegen(alle.removeFirst());
            glaeser.add(g);
        }
        // Zwei leere Gläser
        glaeser.add(new Reagenzglas());
        glaeser.add(new Reagenzglas());
        // Timer zurücksetzen
        verbleibendeSekunden = 30;
        letzteZeitAktualisierung = System.currentTimeMillis();
        ausgewaehltesGlas
                = -1;
    }

    // Bilder laden
    private void bilderLaden() {
        reagenzglasBild = Images.get("/assets/minigames/chemistry/ChemieTesttubeSimple.png");
        for (String f : ALLE_FARBEN)
            blockBilder.put(f, Images.get("/assets/minigames/chemistry/" + f + "ColorDefaultBlock.png"));
    }

    // Spiellogik + Timer
    @Override
    public void update(BaseScene scene) {
        //Timer
        long jetzt = System.currentTimeMillis();
        if (jetzt - letzteZeitAktualisierung >= 1000) {
            verbleibendeSekunden--;
            letzteZeitAktualisierung = jetzt;
            if (verbleibendeSekunden <= 0) {
                // Zeit abgelaufen, dann Level neu starten
                levelSzene.levelLost();
                return;
            }
        }
        //Mausklick (einmalig pro Klick, nicht pro Frame)
        boolean gedrueckt = Input.INSTANCE.getMouseState(MouseEvent.BUTTON1)
                == Input.KeyState.PRESSED;
        boolean geklickt = gedrueckt && !warGedrueckt;
        if (geklickt) {
            Point maus = Input.INSTANCE.getMousePosition();
            if (maus != null) mausGeklickt(maus.x, maus.y);
        }
        warGedrueckt = gedrueckt;
    }

    private void mausGeklickt(int mausX, int mausY) {
        int geklickt = glasAnKoordinate(mausX, mausY);
        // Klick ins Leere = Auswahl aufheben
        if (geklickt == -1) {
            auswahlAufheben();
            return;
        }
        if (ausgewaehltesGlas == -1) {
            // Erstes Glas auswählen (nur wenn nicht leer)
            if (!glaeser.get(geklickt).istLeer()) {
                ausgewaehltesGlas = geklickt;
                glaeser.get(geklickt).setAusgewaehlt(true);
            }
        } else if (ausgewaehltesGlas == geklickt) {
            // Gleiches Glas = Auswahl aufheben
            auswahlAufheben();
        } else {
            // Anderes Glas = Umschütten
            umschuetten(ausgewaehltesGlas, geklickt);
            auswahlAufheben();
            // Gewinnbedingung prüfen
            if (istGewonnen()) {
                levelSzene.levelWon();
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

    private int glasAnKoordinate(int mausX, int mausY) {
        int anzahl
                = glaeser.size();
        int gesamtBreite = anzahl * GLAS_BREITE + (anzahl - 1) * ABSTAND;
        int startX
                = SPIEL_X + (SPIEL_BREITE - gesamtBreite) / 2;
        for (int i = 0; i < glaeser.size(); i++) {
            int gx = startX + i * (GLAS_BREITE + ABSTAND);
            int gy = SPIEL_Y + (glaeser.get(i).isAusgewaehlt() ? -10 : 0);
            if (new Rectangle(gx, gy, GLAS_BREITE, GLAS_HOEHE)
                    .contains(mausX, mausY)) return i;
        }
        return -1;
    }//

    //Zeichnen
    @Override
    public void draw(Graphics2D g2) {
        // Pixel-Art Rendering
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);
        // Titel
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
        g2.drawString("Chemie-Minigame Level " + (level + 1),
                SPIEL_X + 10, SPIEL_Y - 30);
        // Hinweistext
        g2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g2.setColor(Color.WHITE);
        g2.drawString(
                "Klicke auf ein Glas um es auszuwählen, klicke auf ein weiteres um umzuschütten.",
                SPIEL_X + 10, SPIEL_Y - 14);
        // Timer
        g2.setFont(new Font("Monospaced", Font.BOLD, 18));
        g2.setColor(verbleibendeSekunden <= 10
                ? new Color(255, 60, 60)
                : new Color(100, 255, 100));
        g2.drawString("Zeit: " + verbleibendeSekunden + "s",
                SPIEL_X + SPIEL_BREITE - 80, SPIEL_Y - 35);
        // Alle Gläser zeichnen
        for (int i = 0; i < glaeser.size(); i++) {
            glasZeichnen(g2, i, glaeser.get(i));
        }
    }

    private void glasZeichnen(Graphics2D g2, int index, Reagenzglas glas) {
        int anzahl
                = glaeser.size();
        int gesamtBreite = anzahl * GLAS_BREITE + (anzahl - 1) * ABSTAND;
        int startX
                = SPIEL_X + (SPIEL_BREITE - gesamtBreite) / 2;
        int gx = startX + index * (GLAS_BREITE + ABSTAND);
        int gy = SPIEL_Y + (glas.isAusgewaehlt() ? -10 : 0);
        // Reagenzglas
        if (reagenzglasBild != null)
            g2.drawImage(reagenzglasBild, gx, gy, GLAS_BREITE, GLAS_HOEHE, null);
        else {
            g2.setColor(new Color(180, 220, 255, 80));
            g2.fillRect(gx + BLOCK_X_OFFSET, gy + (2 * SCALE), BLOCK_W, 24 * SCALE);
            g2.setColor(new Color(50, 80, 150));
            g2.drawRect(gx, gy, GLAS_BREITE, GLAS_HOEHE);
        }
        // Blöcke
        List<String> bloecke = glas.getBloecke();
        for (int i = 0; i < bloecke.size(); i++) {
            String
                    farbe = bloecke.get(i);
            int
                    blockX = gx + BLOCK_X_OFFSET;
            int
                    blockY = gy + BLOCK_BODEN_Y - (i + 1) * BLOCK_H;
            Image bild = blockBilder.get(farbe);
            if (bild != null)
                g2.drawImage(bild, blockX, blockY, BLOCK_W, BLOCK_H, null);
            else {
                g2.setColor(fallbackFarbe(farbe));
                g2.fillRect(blockX, blockY, BLOCK_W, BLOCK_H);
            }
        }
        // Glasnummer
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.PLAIN, 10));
        g2.drawString(String.valueOf(index + 1),
                gx + GLAS_BREITE / 2 - 3, gy + GLAS_HOEHE + 13);
    }

    private Color fallbackFarbe(String f) {
        return switch (f) {
            case "Blue" -> new Color(60, 120, 220);
            case "Green" -> new Color(60, 200, 80);
            case "Pink" -> new Color(240, 100, 160);
            case "Purple" -> new Color(150, 60, 210);
            case "Yellow" -> new Color(240, 210, 40);
            default -> Color.GRAY;
        };
    }

    @Override
    public Rectangle2D getHitbox() {
        return null;
    }
}