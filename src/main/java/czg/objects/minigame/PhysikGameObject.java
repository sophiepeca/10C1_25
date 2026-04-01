package czg.objects.minigame;

import czg.objects.BaseObject;
import czg.scenes.BaseScene;
import czg.util.Input;
import czg.util.Images;
import czg.scenes.minigame.LevelScene;

import static czg.MainWindow.*;

import java.awt.*;
import java.awt.event.MouseEvent;

import java.awt.geom.Rectangle2D;

/**
 * Das Physik-Minigame als BaseObject.
 * <br><br>
 * Der Spieler zieht einen Kraftpfeil vom Ball weg. Nach dem Klick auf
 * "Simulate" fliegt der Ball unter Einfluss der Basisbeschleunigungen
 * (Gravity + levelspezifische Kräfte) los. Trifft er das Ziel, gewinnt
 * der Spieler. Fliegt er aus dem Bildschirm oder gegen eine Wand, verliert
 * der Spieler.
 * <br><br>
 * Drei Level:
 * Level 0 – Nur Schwerkraft nach unten, Kraft nach rechts ziehen
 * Level 1 – Schwerkraft + Drift nach links, Kraft nach rechts ziehen
 * Level 2 – Schwerkraft + starker Drift nach rechts + Wand, Kraft nach oben ziehen
 */
public class PhysikGameObject extends BaseObject {

    //Spielfeldgröße
    private static final int BREITE = 720;
    private static final int HOEHE = 405;
    //Ball
    private static final int BALL_GROESSE = 32;
    //Simulate-Button (unten mittig)
    private static final int BUTTON_BREITE = 200;
    private static final int BUTTON_HOEHE = 80;
    private final int buttonX = (BREITE - BUTTON_BREITE) / 2;
    private final int buttonY = HOEHE - BUTTON_HOEHE - 15;

    //Pfeilmaßstab (Pixel pro Einheit Spielerkraft)
    private static final int MASSSTAB = 30; //Gravitationsbeschleunigung in Pixel/Frame2
    private static final double GRAVITY = 0.15;

    //Spielphasen
    private static final int PHASE_VORBEREITUNG = 0; // Spieler zieht Pfeil
    private static final int PHASE_SIMULATION = 1; // Ball fliegt
    private static final int PHASE_ERGEBNIS = 2; // Treffer oder Verfehlt

    //Spielzustand
    private int phase = PHASE_VORBEREITUNG;
    private final int level;
    private final LevelScene levelSzene;

    //Ball-Physik
    private double ballX, ballY; // aktuelle Position
    private double velX, velY; // aktuelle Geschwindigkeit

    // Spielerkraft (= Startgeschwindigkeit des Balls)
    private double spielerKraftX = 0;
    private double spielerKraftY = 0;
    private boolean ziehtGerade = false; // Maus wird gerade gehalten
    private String kraftRichtung;
    // "RIGHT" oder "UP"

    //Level-Daten
    private double[] ziel; // {x, y, breite, hoehe}
    private int[] wand; // {x, y, breite, hoehe} oder null
    //Basisbeschleunigungen des Levels
    private double[][] basisBeschleunigungen;
    //Ergebnis des letzten Schusses
    private boolean letzterSchussTreffer = false;
    // Maus-Status (verhindert Mehrfachklicks pro Frame)
    private boolean warGedrueckt = false;
    //Bilder
    private Image ballBild;
    private Image buttonBild;
    private Image wandBild;


    // Konstruktor

    /**
     * @param level      Das Level (0, 1 oder 2)
     * @param levelSzene Referenz auf die übergeordnete LevelScene,
     *                   um levelWon()/levelLost() aufrufen zu können
     */
    public PhysikGameObject(int level, LevelScene levelSzene) {
        super(null, 0, 0, WIDTH, HEIGHT);
        this.level = level;
        this.levelSzene = levelSzene;
        bilderLaden();
        levelInitialisieren();
    }

    // Initialisierung
    private void levelInitialisieren() {
        // Ball oben links starten
        ballX = 60;
        ballY = 60;
        velX = 0;
        velY = 0;
        spielerKraftX = 0;
        spielerKraftY = 0;
        ziehtGerade = false;
        phase = PHASE_VORBEREITUNG;
        letzterSchussTreffer = false;

        // Ziel: unten rechts
        ziel = new double[]{BREITE - 90, HOEHE - 90, 50, 40};// Level-spezifische Konfiguration
        switch (level) {
            case 0:
                // Nur Schwerkraft nach unten, Spieler schießt nach rechts
                basisBeschleunigungen = new double[][]{{0, GRAVITY}};
                kraftRichtung = "RIGHT";
                wand = null;
                break;
            case 1:
                // Schwerkraft + leichter Drift nach links
                basisBeschleunigungen = new double[][]{{0, GRAVITY}, {-0.05, 0}};
                kraftRichtung = "RIGHT";
                wand = null;
                break;
            default:
                // Schwerkraft + starker Drift nach rechts + Wand in der Mitte
                basisBeschleunigungen = new double[][]{{0, GRAVITY}, {0.12, 0}};
                kraftRichtung = "UP";
                wand = new int[]{
                        BREITE / 5,
                        HOEHE / 5,
                        20,
                        (HOEHE * 2) / 5
                };
                break;
        }
    }

    // Bilder laden

    /**
     * Lädt alle Bilder aus dem assets-Ordner des Physik-Minigames.
     * Ist ein Bild nicht vorhanden, wird ein Fallback gezeichnet.
     */
    private void bilderLaden() {
        ballBild
                = Images.get("/assets/minigames/physics/PhysikBall.png");
        buttonBild
                = Images.get("/assets/minigames/physics/PhysikButton.png");
        wandBild
                = Images.get("/assets/minigames/physics/PhysikWall.png");
    }

    // Update – wird jeden Frame von der Engine aufgerufen
    @Override
    public void update(BaseScene scene) {
        // Linke Maustaste: einmalig pro Klick auslösen
        boolean gedrueckt = Input.INSTANCE.getMouseState(MouseEvent.BUTTON1)
                == Input.KeyState.PRESSED;
        boolean geklickt = gedrueckt && !warGedrueckt;
        warGedrueckt = gedrueckt;
        Point maus = Input.INSTANCE.getMousePosition();
        if (maus == null) return;
        if (phase == PHASE_SIMULATION) {
            // Ball einen Schritt weiterbewegen
            ballBewegen();
        } else if (phase == PHASE_VORBEREITUNG) {
            // Maus-Drag: Spielerkraft einstellen
            if (gedrueckt) {
                int mx = (int) ballX + BALL_GROESSE / 2;
                int my = (int) ballY + BALL_GROESSE / 2;
                // Nur ziehen, wenn Mausbutton auf dem Ball gedrückt wurde
                if (geklickt && new Rectangle((int) ballX, (int) ballY,
                        BALL_GROESSE, BALL_GROESSE).contains(maus)) {
                    ziehtGerade = true;
                }
                if (ziehtGerade) {
                    double rohX = (maus.x - mx) / (double) MASSSTAB;
                    double rohY = (maus.y - my) / (double) MASSSTAB;
                    if ("RIGHT".equals(kraftRichtung)) {
                        // Nur positive X-Richtung erlaubt
                        spielerKraftX = Math.max(0, rohX);
                        spielerKraftY = 0;
                    } else {
                        // Nur negative Y-Richtung (= nach oben) erlaubt
                        spielerKraftX = 0;
                        spielerKraftY = Math.min(0, rohY);
                    }
                }
            } else {
                ziehtGerade = false;
            }
            // Simulate-Button gedrückt → Simulation starten
            if (geklickt && new Rectangle(buttonX, buttonY,
                    BUTTON_BREITE, BUTTON_HOEHE).contains(maus)) {
                simulationStarten();
            }
        } else if (phase == PHASE_ERGEBNIS && geklickt) {
            // Klick auf Ergebnisscreen → nächstes Level oder Neustart
            if (letzterSchussTreffer) {
                levelSzene.levelWon();
            } else {
                levelSzene.levelLost();
            }
        }
    }

    // Physik-Simulation

    /**
     * Startet die Simulation:
     * Die Spielerkraft wird als Startgeschwindigkeit des Balls gesetzt.
     */

    private void simulationStarten() {
        velX = spielerKraftX;
        velY = spielerKraftY;
        phase = PHASE_SIMULATION;
    }

    /**
     * Bewegt den Ball einen Frame weiter:
     * 1. Alle Basisbeschleunigungen auf die Geschwindigkeit addieren
     * 2. Geschwindigkeit auf die Position addieren
     * 3. Kollisionen mit Rand, Wand und Ziel prüfen
     */

    private void ballBewegen() {
        // Beschleunigungen anwenden (erzeugt Wurfparabel)
        for (double[] a : basisBeschleunigungen) {
            velX += a[0];
            velY += a[1];
        }
        ballX += velX;
        ballY += velY;
        // Fensterrand getroffen → verloren
        if (ballX <= 0 || ballX + BALL_GROESSE >= BREITE
                || ballY <= 0 || ballY + BALL_GROESSE >= HOEHE) {
            simulationStoppen(false);
            return;
        }
        // Wand getroffen (Level 2) = verloren
        if (wand != null && new Rectangle((int) ballX, (int) ballY,
                BALL_GROESSE, BALL_GROESSE)
                .intersects(new Rectangle(wand[0], wand[1], wand[2], wand[3]))) {
            simulationStoppen(false);
            return;
        }
        // Ziel getroffen = gewonnen
        if (new Rectangle((int) ballX, (int) ballY, BALL_GROESSE, BALL_GROESSE)
                .intersects(new Rectangle(
                        (int) ziel[0], (int) ziel[1],
                        (int) ziel[2], (int) ziel[3]))) {
            simulationStoppen(true);
        }
    }

    /**
     * Hält die Simulation an und zeigt das Ergebnis.
     *
     * @param treffer true = Ziel getroffen, false = verfehlt
     */

    private void simulationStoppen(boolean treffer) {
        letzterSchussTreffer = treffer;
        phase = PHASE_ERGEBNIS;
    }

    // Zeichnen – wird jeden Frame von der Engine aufgerufen
    @Override
    public void draw(Graphics2D g2) {

        // Pixel-Art Rendering
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Titel
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 16));
        g2.drawString("Physik-Minigame – Level " + (level + 1), 20, 30);

        // 2. Hinweistext (nur vor Simulation)
        if (phase == PHASE_VORBEREITUNG) {
            g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
            g2.setColor(new Color(200, 200, 255));
            if ("RIGHT".equals(kraftRichtung)) {
                g2.drawString("Ziehe vom Ball nach rechts und klicke dann auf Simulate!", 20, 50);
            } else {
                g2.drawString("Ziehe vom den Ball nach oben und klicke dann auf Simulate!", 20, 50);
            }
        }
        // 3. Ziel zeichnen (grau-grün)
        zielZeichnen(g2);
        // 4. Wand zeichnen (nur Level 2)
        if (wand != null) wandZeichnen(g2);
        // 5. Kraftpfeile (nur vor Simulate)
        if (phase == PHASE_VORBEREITUNG) kraftpfeileZeichnen(g2);
        // 6. Ball zeichnen
        ballZeichnen(g2);
        // 7. Simulate-Button (nur Vorbereitungsphase)
        if (phase == PHASE_VORBEREITUNG) buttonZeichnen(g2);
        // 8. Ergebnis-Overlay
        if (phase == PHASE_ERGEBNIS) ergebnisZeichnen(g2);
    }

    /**
     * Zeichnet das Ziel-Rechteck mit Beschriftung.
     */
    private void zielZeichnen(Graphics2D g2) {
        int zx = (int) ziel[0], zy = (int) ziel[1];
        int zb = (int) ziel[2], zh = (int) ziel[3];
        g2.setColor(new Color(100, 140, 110, 200));
        g2.fillRect(zx, zy, zb, zh);
        g2.setColor(new Color(70, 110, 80));
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(zx, zy, zb, zh);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 10));
        g2.drawString("ZIEL", zx + 8, zy + zh / 2 + 4);
    }

    /**
     * Zeichnet die Wand (Level 2), Fallback braun, wenn kein Bild vorhanden.
     */
    private void wandZeichnen(Graphics2D g2) {
        if (wandBild != null) {
            g2.drawImage(wandBild, wand[0], wand[1], wand[2], wand[3], null);
        } else {
            g2.setColor(new Color(140, 100, 60));
            g2.fillRect(wand[0], wand[1], wand[2], wand[3]);
            g2.setColor(new Color(80, 60, 30));
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(wand[0], wand[1], wand[2], wand[3]);
            g2.setStroke(new BasicStroke(1));
        }
    }

    /**
     * Zeichnet die Kraftpfeile:
     * - Orange = Basisbeschleunigungen (Gravity, Drift)
     * - Blau = Spielerkraft (vom Ball wegzeigend)
     */

    private void kraftpfeileZeichnen(Graphics2D g2) {
        int mx = (int) ballX + BALL_GROESSE / 2;
        int my = (int) ballY + BALL_GROESSE / 2;

        // Basisbeschleunigungen in Orange (stark skaliert für Sichtbarkeit)
        g2.setStroke(new BasicStroke(3));
        g2.setColor(new Color(255, 140, 0));
        for (double[] k : basisBeschleunigungen) {
            int ex = mx + (int) (k[0] * MASSSTAB * 15);
            int ey = my + (int) (k[1] * MASSSTAB * 15);
            pfeilZeichnen(g2, mx, my, ex, ey);
        }
        // Spielerkraft in Blau
        if (spielerKraftX != 0 || spielerKraftY != 0) {
            g2.setColor(new Color(60, 140, 255));
            int ex = mx + (int) (spielerKraftX * MASSSTAB);
            int ey = my + (int) (spielerKraftY * MASSSTAB);
            pfeilZeichnen(g2, mx, my, ex, ey);
        }
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * Hilfsmethode: Zeichnet einen Pfeil von (x1,y1) nach (x2,y2).
     */
    private void pfeilZeichnen(Graphics2D g2, int x1, int y1, int x2, int y2) {
        g2.drawLine(x1, y1, x2, y2);
        double winkel = Math.atan2(y2 - y1, x2 - x1);
        int len = 12;
        double spread = Math.PI / 6;
        g2.drawLine(x2, y2,
                (int) (x2 - len * Math.cos(winkel - spread)),
                (int) (y2 - len * Math.sin(winkel - spread)));
        g2.drawLine(x2, y2,
                (int) (x2 - len * Math.cos(winkel + spread)),
                (int) (y2 - len * Math.sin(winkel + spread)));
    }

    /**
     * Zeichnet den Ball – Fallback rot, wenn kein Bild vorhanden.
     */
    private void ballZeichnen(Graphics2D g2) {
        if (ballBild != null) {
            g2.drawImage(ballBild, (int) ballX, (int) ballY,
                    BALL_GROESSE, BALL_GROESSE, null);
        } else {
            g2.setColor(new Color(220, 60, 60));
            g2.fillOval((int) ballX, (int) ballY, BALL_GROESSE, BALL_GROESSE);
            g2.setColor(Color.WHITE);
            g2.drawOval((int) ballX, (int) ballY, BALL_GROESSE, BALL_GROESSE);
        }
    }

    /**
     * Zeichnet den Simulate-Button – Fallback grün, wenn kein Bild vorhanden.
     */
    private void buttonZeichnen(Graphics2D g2) {
        if (buttonBild != null) {
            g2.drawImage(buttonBild, buttonX, buttonY,
                    BUTTON_BREITE, BUTTON_HOEHE, null);
        } else {
            g2.setColor(new Color(60, 180, 60));
            g2.fillRoundRect(buttonX, buttonY, BUTTON_BREITE, BUTTON_HOEHE, 10, 10);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Monospaced", Font.BOLD, 14));
            g2.drawString("SIMULATE",
                    buttonX + BUTTON_BREITE / 2 - 45, buttonY + BUTTON_HOEHE / 2 + 5);
        }
    }

    /**
     * Zeichnet das Ergebnis-Overlay (TREFFER / VERFEHLT).
     */
    private void ergebnisZeichnen(Graphics2D g2) {
        // Halbtransparenter Hintergrund
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(BREITE / 2 - 170, HOEHE / 2 - 55, 340, 110, 20, 20);
        if (letzterSchussTreffer) {
            g2.setColor(new Color(0, 255, 80));
            g2.setFont(new Font("Monospaced", Font.BOLD, 30));
            g2.drawString("TREFFER!", BREITE / 2 - 95, HOEHE / 2 + 5);
        } else {
            g2.setColor(new Color(255, 60, 60));
            g2.setFont(new Font("Monospaced", Font.BOLD, 30));
            g2.drawString("VERFEHLT!", BREITE / 2 - 105, HOEHE / 2 + 5);
        }
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2.setColor(Color.WHITE);
        g2.drawString(
                "Klicke zum " + (letzterSchussTreffer ? "Weitermachen" : "Neustart"),
                BREITE / 2 - 85, HOEHE / 2 + 35);
    }

    @Override
    public Rectangle2D getHitbox() {
        return null;
    }
}
