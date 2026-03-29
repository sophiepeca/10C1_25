package czg.minigame;

import czg.objects.minigame.TangramPieceObject;
import czg.scenes.minigame.MathematicsLevelScene;
import czg.util.Images;

import java.awt.*;
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
    P_10("/assets/minigames/mathematics/puzzle_2_1.png", 7, new double[][][] {
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
     * Level 2: Wurfstern TODO: weitere Lösungen hinzufügen (oder verwerfen)
     */
    P_11("/assets/minigames/mathematics/puzzle_2_2.png", 7, new double[][][] {
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
     * Level 2: TODO
     */
    P_12("/assets/minigames/mathematics/puzzle_1_1.png", 7, new double[][][] {
        {
            {0.33, 0.4, 0.0},
            {0.0, 0.0, 180.0},
            {0.33, 0.0, 90.0},
            {0.5, 0.0, 0.0},
            {0.33, 0.4, 180.0},
            {0.5, 0.2, 90.0},
            {0.33, 0.6, 0.0}
        }
    }),

    /**
     * Level 3: TODO
     */
    P_20("/assets/minigames/mathematics/puzzle_1_1.png", 0, new double[][][] {
        {
            {0.33, 0.4, 0.0},
            {0.0, 0.0, 180.0},
            {0.33, 0.0, 90.0},
            {0.5, 0.0, 0.0},
            {0.33, 0.4, 180.0},
            {0.5, 0.2, 90.0},
            {0.33, 0.6, 0.0}
        }
    }),
    /**
     * Level 3: TODO
     */
    P_21("/assets/minigames/mathematics/puzzle_1_1.png", 0, new double[][][] {
        {
            {0.33, 0.4, 0.0},
            {0.0, 0.0, 180.0},
            {0.33, 0.0, 90.0},
            {0.5, 0.0, 0.0},
            {0.33, 0.4, 180.0},
            {0.5, 0.2, 90.0},
            {0.33, 0.6, 0.0}
        }
    }),
    /**
     * Level 3: TODO
     */
    P_22("/assets/minigames/mathematics/puzzle_1_1.png", 0, new double[][][] {
        {
            {0.33, 0.4, 0.0},
            {0.0, 0.0, 180.0},
            {0.33, 0.0, 90.0},
            {0.5, 0.0, 0.0},
            {0.33, 0.4, 180.0},
            {0.5, 0.2, 90.0},
            {0.33, 0.6, 0.0}
        }
    });

    /**
     * Die möglichen Tangram-Formen für jedes Level
     */
    public static final MathematicsPuzzle[][] PUZZLES = {
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
     * Wie weit ein Tangram-Teil von seiner korrekten
     * Position entfernt sein darf und trotzdem noch als
     * richtig angesehen wird
     */
    public static final int MARGIN_OF_ERROR = 20;

    /**
     * Form der Lösung
     */
    public final Image sprite;

    /**
     * Wie viele Teile vorgegeben sind
     */
    public final int amountOfGivenPieces;

    /**
     * Mögliche Lösungen
     */
    public final double[][][] solutions;

    MathematicsPuzzle(String path, int amountOfGivenPieces, double[][][] solutions) {
        this.sprite = Images.get(path);
        this.amountOfGivenPieces = amountOfGivenPieces;
        this.solutions = solutions;
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
     * @param pieces Die Tangram-Teile in der {@link MathematicsLevelScene}
     * @param x X-Koordinate des Puzzle-Bereichs
     * @param y Y-Koordinate des Puzzle-Bereichs
     * @param width Breite des Puzzle-Bereichs
     * @param height Höhe des Puzzle-Bereichs
     * @return Ob die Form richtig gelegt wurde
     */
    public boolean isSolutionValid(TangramPieceObject[] pieces, int x, int y, int width, int height) {
        for(double[][] solution : solutions) {
            // Große Dreiecke
            boolean normal =
                matches(pieces[0], solution[0], 0.0, x, y, width, height) &&
                matches(pieces[1], solution[1], 0.0, x, y, width, height);

            boolean swapped =
                matches(pieces[0], solution[1], -90.0, x, y, width, height) &&
                matches(pieces[1], solution[0], 90.0, x, y, width, height);

            if(!(normal || swapped)) continue;

            // Mittleres Dreieck
            if(!matches(pieces[2], solution[2], 0.0, x, y, width, height)) continue;

            // Kleine Dreiecke
            normal =
                matches(pieces[3], solution[3], 0.0, x, y, width, height) &&
                matches(pieces[4], solution[4], 0.0, x, y, width, height);

            swapped =
                matches(pieces[3], solution[4], -90.0, x, y, width, height) &&
                matches(pieces[4], solution[3], 90.0, x, y, width, height);

            if(!(normal || swapped)) continue;

            // Parallelogramm
            pieces[5].rotation %= 180;
            if(!matches(pieces[5], solution[5], 0.0, x, y, width, height)) continue;

            // Quadrat
            pieces[6].rotation %= 90;
            if(!matches(pieces[6], solution[6], 0.0, x, y, width, height)) continue;
            
            return true;
        }
        
        return false;
    }

    private boolean matches(TangramPieceObject piece, double[] solution, double rotOffset, int x, int y, int width, int height) {
        double expectedRotation = (solution[2] + rotOffset) % 360;
        if (expectedRotation < 0) expectedRotation += 360.0;

        return Math.abs(piece.x - (x + solution[0]*width)) <= MARGIN_OF_ERROR &&
                Math.abs(piece.y - (y + solution[1]*height)) <= MARGIN_OF_ERROR &&
                piece.rotation == expectedRotation;
    }
    
    private void setGivenPieces(TangramPieceObject[] pieces, int x, int y, int width, int height) {
        int[] idx = new int[amountOfGivenPieces];
        for(int i = 0; i < amountOfGivenPieces; i++) {
            idx[i] = -1;
        }
        
        for(int i = 0; i < amountOfGivenPieces; i++) {
            int rIdx;
            while(true) {
                rIdx = new Random().nextInt(7);
                boolean validIdx = true;
                for(int j = 0; j < amountOfGivenPieces; j++) {
                    if (idx[j] == rIdx) {
                        validIdx = false;
                        break;
                    }
                }
                if(validIdx) break;
            }
            idx[i] = rIdx;
        }
        
        int rSolution = new Random().nextInt(solutions.length);
        for(int i : idx) {
            pieces[i].setRotation(solutions[rSolution][i][2]);
            pieces[i].x = (int) (x + solutions[rSolution][i][0]*width);
            pieces[i].y = (int) (y + solutions[rSolution][i][1]*height);

            // Höhe und Breite der Steine zurücksetzten, falls das Puzzle der Ausgangszustand ist
            if(this == P_INIT) {
                pieces[i].width = (int) (solutions[rSolution][i][3]*width);
                pieces[i].height = (int) (solutions[rSolution][i][4]*height);
                pieces[i].originalWidth = pieces[i].width;
                pieces[i].originalHeight = pieces[i].height;
            }
        }
    }
    
    public void reset(TangramPieceObject[] pieces, int x, int y, int size, int px, int py, int pwidth, int pheight) {
        P_INIT.setGivenPieces(pieces, x, y, size, size);
        setGivenPieces(pieces, px, py, pwidth, pheight);
    }
}
