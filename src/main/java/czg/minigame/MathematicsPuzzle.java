package czg.minigame;

import czg.MainWindow;
import czg.objects.minigame.TangramPieceObject;
import czg.util.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Die verschiedenen Tangram-Rätsel für die drei Level
 * des Mathematik-Minigames
 */
public enum MathematicsPuzzle {

    /**
     * Ausgangszustand: Verpackt
     * Speichert zusätzlich noch die Breite und Höhe der Steine
     */
    P_INIT("/assets/minigames/mathematics/tangram_packed.png", 7, new double[][][] {
        {
            {0.0, 0.0, 0.0, 1.0, 0.5},      // Großes Dreieck 1
            {0.0, 0.0, 0.0, 0.5, 1.0},      // Großes Dreieck 2
            {0.5, 0.5, 0.0, 0.5, 0.5},      // Mittleres Dreieck
            {0.0, 0.75, 0.0, 0.5, 0.25},    // Kleines Dreieck 1
            {0.5, 0.25, 0.0, 0.25, 0.5},    // Kleines Dreieck 2
            {0.75, 0.0, 0.0, 0.25, 0.75},   // Parallelogramm
            {0.25, 0.5, 0.0, 0.5, 0.5}      // Quadrat
        }
    }),

    /**
     * Level 1: Herz
     */
    P_00("/assets/minigames/mathematics/puzzle_1_1.png", 2, new double[][][] {
        {
            {0.33, 0.4, 0.0},       // Großes Dreieck 1
            {0.0, 0.0, 180.0},      // Großes Dreieck 2
            {0.33, 0.0, 90.0},      // Mittleres Dreieck
            {0.5, 0.0, 0.0},        // Kleines Dreieck 1
            {0.33, 0.4, 180.0},     // Kleines Dreieck 2
            {0.5, 0.2, 90.0},       // Parallelogramm
            {0.33, 0.6, 0.0}        // Quadrat
        },
        {
            {0.0, 0.4, 0.0},        // Großes Dreieck 1
            {0.67, 0.0, 0.0},       // Großes Dreieck 2
            {0.33, 0.0, 0.0},       // Mittleres Dreieck
            {0.0, 0.2, 0.0},        // Kleines Dreieck 1
            {0.33, 0.6, 0.0},       // Kleines Dreieck 2
            {0.5, 0.4, 0.0},        // Parallelogramm
            {0.17, 0.0, 0.0}        // Quadrat
        }
    }),
    /**
     * Level 1: Schwan
     */
    P_01("/assets/minigames/mathematics/puzzle_1_2.png", 2, new double[][][] {
        {
            {0.0, 0.55, 0.0},       // Großes Dreieck 1
            {0.2, 0.55, 45.0},      // Großes Dreieck 2
            {0.68, 0.55, 315.0},    // Mittleres Dreieck
            {0.74, 0.0, 225.0},     // Kleines Dreieck 1
            {0.75, 0.47, 0.0},      // Kleines Dreieck 2
            {0.58, 0.0, 0.0},       // Parallelogramm
            {0.58, 0.31, 0.0}       // Quadrat
        }
    }),
    /**
     * Level 1: Berg
     */
    P_02("/assets/minigames/mathematics/puzzle_1_3.png", 2, new double[][][] {
        {
            {0.66, 0.16, 45.0},     // Großes Dreieck 1
            {0.0, 0.17, 45.0},      // Großes Dreieck 2
            {0.5, 0.17, 135.0},     // Mittleres Dreieck
            {0.33, 0.59, -135.0},    // Kleines Dreieck 1
            {0.5, 0.59, -45.0},      // Kleines Dreieck 2
            {0.33, 0.17, -45.0},     // Parallelogramm
            {0.39, 0.0, 0.0}         // Quadrat
        },
        {
            {0.66, 0.16, 45.0},     // Großes Dreieck 1
            {0.0, 0.17, 45.0},      // Großes Dreieck 2
            {0.33, 0.17, -45.0},    // Mittleres Dreieck
            {0.5, 0.17, 135.0},     // Kleines Dreieck 1
            {0.5, 0.58, -135.0},    // Kleines Dreieck 2
            {0.34, 0.57, 45.0},     // Parallelogramm
            {0.39, 0.0, 0.0}        // Quadrat
        }
    }),

    /**
     * Level 2: Haus
     */
    P_10("/assets/minigames/mathematics/puzzle_2_1.png", 1, new double[][][] {
        {
            {0.13, 0.59, 180.0},    // Großes Dreieck 1
            {0.26, 0.17, -90.0},    // Großes Dreieck 2
            {0.13, 0.59, 180.0},    // Mittleres Dreieck
            {0.5, 0.59, 180.0},     // Kleines Dreieck 1
            {0.69, 0.59, 0.0},      // Kleines Dreieck 2
            {0.0, 0.28, 45.0},      // Parallelogramm
            {0.27, 0.0, 45.0}       // Quadrat
        },
        {
            {0.13, 0.59, 180.0},    // Großes Dreieck 1
            {0.26, 0.17, -90.0},    // Großes Dreieck 2
            {0.5, 0.59, -90.0},     // Mittleres Dreieck
            {0.13, 0.59, 180.0},    // Kleines Dreieck 1
            {0.13, 0.59, 180.0},    // Kleines Dreieck 2
            {0.0, 0.28, 45.0},      // Parallelogramm
            {0.27, 0.0, 45.0}       // Quadrat
        }
    }),
    /**
     * Level 2: Wurfstern
     */
    P_11("/assets/minigames/mathematics/puzzle_2_2.png", 1, new double[][][] {
        {
            {0.0, 0.16, 0.0},       // Großes Dreieck 1
            {0.16, 0.32, 0.0},      // Großes Dreieck 2
            {0.5, 0.32, 0.0},       // Mittleres Dreieck
            {0.32, 0.67, 0.0},      // Kleines Dreieck 1
            {0.67, 0.0, 0.0},       // Kleines Dreieck 2
            {0.5, 0.67, 90.0},      // Parallelogramm
            {0.5, 0.16, 0.0}        // Quadrat
        }
    }),
    /**
     * Level 2: Baum
     */
    P_12("/assets/minigames/mathematics/puzzle_2_3.png", 1, new double[][][] {
        {
            {0.5, 0.0, 45.0},       // Großes Dreieck 1
            {0.0, 0.0, 45.0},       // Großes Dreieck 2
            {0.25, 0.5, -135.0},    // Mittleres Dreieck
            {0.5, 0.5, 45.0},       // Kleines Dreieck 1
            {0.75, 0.5, -45.0},     // Kleines Dreieck 2
            {0.0, 0.49, 45.0},      // Parallelogramm
            {0.38, 0.75, 45.0}      // Quadrat
        }
    }),

    /**
     * Level 3: Läufer
     */
    P_20("/assets/minigames/mathematics/puzzle_3_1.png", 0, new double[][][] {
        {
            {0.26, 0.26, 180.0},    // Großes Dreieck 1
            {0.26, 0.56, -135.0},   // Großes Dreieck 2
            {0.44, 0.0, 180.0},     // Mittleres Dreieck
            {0.49, 0.05, 135.0},    // Kleines Dreieck 1
            {0.62, 0.84, 90.0},     // Kleines Dreieck 2
            {0.0, 0.57, -45.0},     // Parallelogramm
            {0.44, 0.71, 0.0}       // Quadrat
        }
    }),
    /**
     * Level 3: Kamel
     */
    P_21("/assets/minigames/mathematics/puzzle_3_2.png", 0, new double[][][] {
        {
            {0.0, 0.51, 135.0},     // Großes Dreieck 1
            {0.35, 0.32, 180.0},    // Großes Dreieck 2
            {0.0, 0.27, -135.0},    // Mittleres Dreieck
            {0.65, 0.0, 180.0},     // Kleines Dreieck 1
            {0.65, 0.0, 180.0},     // Kleines Dreieck 2
            {0.69, 0.15, 0.0},      // Parallelogramm
            {0.31, 0.17, 0.0}       // Quadrat
        }
    }),
    /**
     * Level 3: Hai
     */
    P_22("/assets/minigames/mathematics/puzzle_3_3.png", 0, new double[][][] {
        {
            {0.0, 0.0, 45.0},       // Großes Dreieck 1
            {0.26, 0.31, 90.0},     // Großes Dreieck 2
            {0.48, 0.66, 180.0},    // Mittleres Dreieck
            {0.48, 0.14, 0.0},      // Kleines Dreieck 1
            {0.56, 0.49, 90.0},     // Kleines Dreieck 2
            {0.12, 0.49, 90.0},     // Parallelogramm
            {0.7, 0.31, 0.0}        // Quadrat
        }
    });

    /**
     * Die möglichen Tangram-Formen für jedes Level
     */
    private static final MathematicsPuzzle[][] PUZZLES = {
        {
            MathematicsPuzzle.P_00,
            MathematicsPuzzle.P_01,
            MathematicsPuzzle.P_02
        }, {
            MathematicsPuzzle.P_10,
            MathematicsPuzzle.P_11,
            MathematicsPuzzle.P_12
        }, {
            MathematicsPuzzle.P_20,
            MathematicsPuzzle.P_21,
            MathematicsPuzzle.P_22
        }
    };

    /**
     * Form der Lösung
     */
    public final Image SPRITE;

    /**
     * Wie viel Prozent des Puzzles unbedeckt bleiben darf,
     * damit es trotzdem als gelöst gilt
     */
    private static final double MARGIN_OF_ERROR = 0.02;

    /**
     * Wie viele Teile vorgegeben sind
     */
    private final int AMOUNT_OF_GIVEN_PIECES;

    /**
     * Mögliche Lösungen
     */
    private final double[][][] SOLUTIONS;

    MathematicsPuzzle(String path, int amountOfGivenPieces, double[][][] solutions) {
        this.SPRITE = Images.get(path);
        this.AMOUNT_OF_GIVEN_PIECES = amountOfGivenPieces;
        this.SOLUTIONS = solutions;
    }

    /**
     * Gibt ein Rätsel für das ausgewählte Level zurück. Die möglichen
     * Rätsel für jedes Level sind in {@link #PUZZLES} definiert. Aus
     * diesen wird zufällig eines ausgewählt.
     * @param level Das Level des Minigames. Entweder {@code 0}, {@code 1} oder {@code 2}.
     * @return Eine der in {@link MathematicsPuzzle} definierte Enum-Konstanten
     */
    public static MathematicsPuzzle getPuzzle(int level) {
        // Zufallszahl zwischen 0 (inklusiv) und 3 (exklusiv)
        int r = new Random().nextInt(3);
        // Entsprechenden Eintrag aus PUZZLES zurückgeben
        return PUZZLES[level][r];
    }

    /**
     * Prüft, ob die Tangram-Teile richtig gelegt wurden
     * @param x X-Koordinate des Puzzle-Bereichs
     * @param y Y-Koordinate des Puzzle-Bereichs
     * @param width Breite des Puzzle-Bereichs
     * @return Ob die Form richtig gelegt wurde
     */
    public boolean isSolutionValid(int x, int y, int width) {
        // Initialisierung des BufferedImage des Puzzles
        BufferedImage bufferedSprite = (BufferedImage) SPRITE;

        // Initialisierung des BufferedImage des Fensters
        BufferedImage bufferedMainWindow = new BufferedImage(MainWindow.WIDTH, MainWindow.HEIGHT, bufferedSprite.getType());

        Graphics2D g2d = bufferedMainWindow.createGraphics();
        MainWindow.INSTANCE.paint(g2d);
        g2d.dispose();

        // Skalierungsfaktor des Bildes
        double spriteScale = (double) width / bufferedSprite.getWidth();

        // Berechnung der Anzahl an gesamten, sowie verdeckten Pixeln
        double amountOfPixel = 0;
        double amountOfCoveredPixel = 0;
        for (int row = 0; row < bufferedSprite.getHeight(); row++) {
            for (int col = 0; col < bufferedSprite.getWidth(); col++) {
                // Transparente Pixel werden übersprungen
                int alpha = (bufferedSprite.getRGB(col, row) & 0xff000000) >>> 24;
                if (alpha == 0) continue;

                // Berechnung der Fenster-Koordinaten des aktuellen Pixels
                int px = (int) (x + col*spriteScale);
                int py = (int) (y + row*spriteScale);

                // Falls der Pixel im Bild eine andere Farbe als im Fenster hat, bedeutet das, er ist verdeckt.
                if (bufferedSprite.getRGB(col, row) != bufferedMainWindow.getRGB(px, py)) amountOfCoveredPixel++;

                amountOfPixel++;
            }
        }

        // Rückgabe, ob genug Pixel verdeckt sind
        return amountOfCoveredPixel / amountOfPixel > 1-MARGIN_OF_ERROR;
    }

    /**
     * Zufällig ausgewählte, vorgegebene Steine platzieren
     * @param pieces Array, das alle Tangram-Steine enthält
     * @param x X-Position des Puzzles
     * @param y Y-Position des Puzzles
     * @param width Breite des Puzzles
     * @param height Höhe des Puzzles
     */
    private void setGivenPieces(TangramPieceObject[] pieces, int x, int y, int width, int height) {
        // Initialisierung eines Index-Arrays mit Platzhalter-Werten
        int[] idx = new int[AMOUNT_OF_GIVEN_PIECES];
        for(int i = 0; i < AMOUNT_OF_GIVEN_PIECES; i++) {
            idx[i] = -1;
        }

        // Generierung der zufälligen Indizes der vorzugebenden Steine
        for(int i = 0; i < AMOUNT_OF_GIVEN_PIECES; i++) {
            int rIdx;
            // Neuer zufälliger Index, solange der alte bereits generiert wurde
            while(true) {
                rIdx = new Random().nextInt(7);
                boolean used = false;
                for(int j : idx) {
                    if (j == rIdx) {
                        used = true;
                        break;
                    }
                }

                if (!used) break;
            }
            idx[i] = rIdx;
        }
        // Festlegen einer Lösung des Puzzles
        int rSolution = new Random().nextInt(SOLUTIONS.length);
        // Positionierung der Steine
        for(int i : idx) {
            // Rotation wird als erstes geändert, da sich dadurch die Größe und somit die Position des Steines ändern kann.
            pieces[i].setRotation(SOLUTIONS[rSolution][i][2]);
            pieces[i].x = (int) (x + SOLUTIONS[rSolution][i][0]*width);
            pieces[i].y = (int) (y + SOLUTIONS[rSolution][i][1]*height);

            // Höhe und Breite der Steine zurücksetzten, falls es sich beim Puzzle um den Ausgangszustand handelt
            if(this == P_INIT) {
                pieces[i].width = (int) (SOLUTIONS[rSolution][i][3]*width);
                pieces[i].height = (int) (SOLUTIONS[rSolution][i][4]*height);
                pieces[i].originalWidth = pieces[i].width;
                pieces[i].originalHeight = pieces[i].height;
            }
        }
    }

    /**
     * Setzt das Puzzle zurück
     * @param pieces Die Tangram-Steine, die zurückgesetzt werden sollen
     * @param x Die X-Position der verpackten Tangram-Steine
     * @param y Die Y-Position der verpackten Tangram-Steine
     * @param size Die Größe der verpackten Tangram-Steine
     * @param px Die X-Position des Puzzles
     * @param py Die Y-Position des Puzzles
     * @param pwidth Die Breite des Puzzles
     * @param pheight Die Höhe des Puzzles
     */
    public void reset(TangramPieceObject[] pieces, int x, int y, int size, int px, int py, int pwidth, int pheight) {
        // Steine in Ausgangszustand
        P_INIT.setGivenPieces(pieces, x, y, size, size);
        // Steine vorgeben
        setGivenPieces(pieces, px, py, pwidth, pheight);
    }
}
