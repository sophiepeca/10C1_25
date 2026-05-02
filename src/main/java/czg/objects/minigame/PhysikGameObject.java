package czg.objects.minigame;

import czg.objects.BaseObject;
import czg.objects.ButtonObject;
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
 * <p>
 * Der Spieler zieht einen Kraftpfeil vom Ball weg. Nach dem Klick auf
 * "Simulate" fliegt der Ball unter Einfluss der Basisbeschleunigungen
 * (Gravity + levelspezifische Kräfte) los. Trifft er das Ziel, gewinnt
 * der Spieler. Fliegt er aus dem Bildschirm oder gegen eine Wand, verliert
 * der Spieler.
 * <p>
 * Drei Level:
 * Level 0 – Nur Schwerkraft nach unten, Kraft nach rechts ziehen
 * Level 1 – Schwerkraft + Drift nach links, Kraft nach rechts ziehen
 * Level 2 – Schwerkraft + starker Drift nach rechts + Wand, Kraft nach oben ziehen
 */
public class PhysikGameObject extends BaseObject {

    //Spielfeldgröße
    private static final int BREITE = WIDTH;
    private static final int HOEHE = HEIGHT;
    //Ball
    private static final int BALL_GROESSE = 64;
    //Simulate-Button (unten mittig)
    private static final int BUTTON_BREITE = 320;
    private static final int BUTTON_HOEHE = 160;
    private final ButtonObject simulateButton;

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
    // Maus-Status (verhindert Mehrfachklicks pro Frame)
    //Bilder
    private Image ballBild;
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
        simulateButton = new ButtonObject(
                Images.get("/assets/minigames/physics/PhysikButton.png"),
                this::simulationStarten
        );
        int buttonX = (BREITE - BUTTON_BREITE) / 2;
        int buttonY = HOEHE - BUTTON_HOEHE + 20;
        simulateButton.x = buttonX;
        simulateButton.y = buttonY;
        simulateButton.width = BUTTON_BREITE;
        simulateButton.height = BUTTON_HOEHE;
    }

    // Initialisierung
    private void levelInitialisieren() {
        // Ball oben links starten
        ballX = 60;
        ballY = 120;
        velX = 0;
        velY = 0;
        spielerKraftX = 0;
        spielerKraftY = 0;
        ziehtGerade = false;
        phase = PHASE_VORBEREITUNG;

        // Ziel: unten rechts
        ziel = new double[]{BREITE - 145, HOEHE - 125, 140, 120};

        // Level-spezifische Konfiguration
        switch (level) {
            case 0:
                // Nur Schwerkraft nach unten, Spieler schießt nach rechts
                basisBeschleunigungen = new double[][]{{0, GRAVITY}};
                // Spieler*in kann Kraftpfeil nur nach rechts ziehen
                kraftRichtung = "RIGHT";
                wand = null;
                break;
            case 1:
                // Schwerkraft + leichter Drift nach links
                basisBeschleunigungen = new double[][]{{0, GRAVITY}, {-0.05, 0}};
                // Spieler*in kann Kraftpfeil nur nach rechts ziehen
                kraftRichtung = "RIGHT";
                wand = null;
                break;
            default:
                // Schwerkraft + starker Drift nach rechts + Wand in der Mitte
                basisBeschleunigungen = new double[][]{{0, GRAVITY}, {0.12, 0}};
                // Spieler*in kann Kraftpfeil nur nach oben ziehen
                kraftRichtung = "UP";
                wand = new int[]{
                        BREITE / 4,
                        HOEHE / 3,
                        30,
                        133
                };
                break;
        }
    }

    // Bilder laden

    /**
     * Lädt alle Bilder aus dem assets-Ordner des Physik-Minigames.
     */
    private void bilderLaden() {
        ballBild
                = Images.get("/assets/minigames/physics/PhysikBall.png");
        wandBild
                = Images.get("/assets/minigames/physics/PhysikWall.png");
    }

    // Update
    @Override
    public void update(BaseScene scene) {
        Input.KeyState mouseState = Input.INSTANCE.getMouseState(MouseEvent.BUTTON1);
        boolean gedrueckt = mouseState == Input.KeyState.PRESSED
                || mouseState == Input.KeyState.HELD;
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
                if (!ziehtGerade && new Rectangle((int) ballX, (int) ballY,
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
            simulateButton.update(scene);
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
        phase = PHASE_ERGEBNIS;
        if (treffer) {
            levelSzene.levelWon();
        } else {
            levelSzene.levelLost();
        }
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
        // 3. Ziel zeichnen
        zielZeichnen(g2);
        // 4. Wand zeichnen (nur Level 3)
        if (wand != null) wandZeichnen(g2);
        // 5. Kraftpfeile (nur vor Simulate)
        if (phase == PHASE_VORBEREITUNG) kraftpfeileZeichnen(g2);
        // 6. Ball zeichnen
        ballZeichnen(g2);
        // 7. Simulate-Button (nur Vorbereitungsphase)
        if (phase == PHASE_VORBEREITUNG) simulateButton.draw(g2);
    }

    /**
     * Zeichnet das Ziel-Rechteck mit Beschriftung.
     */
    private void zielZeichnen(Graphics2D g2) {
        int zx = (int) ziel[0], zy = (int) ziel[1];
        int zb = (int) ziel[2], zh = (int) ziel[3];
        g2.setColor(new Color(40, 100, 55, 245));
        g2.fillRect(zx, zy, zb, zh);
        g2.setColor(new Color(20, 70, 35));
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(zx, zy, zb, zh);
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 10));
        FontMetrics fm = g2.getFontMetrics();
        int textX = zx + (zb - fm.stringWidth("ZIEL")) / 2;
        int textY = zy + (zh - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString("ZIEL", textX, textY);
    }

    /**
     * Zeichnet die Wand (Level 2), Fallback braun, wenn kein Bild vorhanden.
     */
    private void wandZeichnen(Graphics2D g2) {
        g2.drawImage(wandBild, wand[0], wand[1], wand[2], wand[3], null);
    }

    /**
     * Zeichnet die Kraftpfeile:
     * - Orange = Basisbeschleunigungen
     * - Blau = Spielerkraft (vom Ball wegzeigend)
     */

    private void kraftpfeileZeichnen(Graphics2D g2) {
        int mx = (int) ballX + BALL_GROESSE / 2;
        int my = (int) ballY + BALL_GROESSE / 2;

        // Basisbeschleunigungen in Orange (stark skaliert für Sichtbarkeit)
        g2.setStroke(new BasicStroke(3));
        g2.setColor(new Color(255, 140, 0));
        for (double[] k : basisBeschleunigungen) {
            int ex = mx + (int) (k[0] * MASSSTAB * 40);
            int ey = my + (int) (k[1] * MASSSTAB * 40);
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
     * Zeichnet den Ball
     */
    private void ballZeichnen(Graphics2D g2) {
        g2.drawImage(ballBild, (int) ballX, (int) ballY, BALL_GROESSE, BALL_GROESSE, null);
    }

    @Override
    public Rectangle2D getHitbox() {
        return null;
    }
}
