package czg.minigame;

import czg.util.Images;

import java.awt.*;
import java.util.Random;

public enum ComputerSciencePuzzle {

    P_00("/assets/minigames/computer_science/puzzle_1_1.png",new LogicGate[]{LogicGate.AND}, 4),
    P_01("/assets/minigames/computer_science/puzzle_1_2.png", new LogicGate[]{LogicGate.OR}, 4),
    P_02("/assets/minigames/computer_science/puzzle_1_3.png", new LogicGate[]{LogicGate.NAND}, 4),

    P_10("/assets/minigames/computer_science/puzzle_2_1.png", new LogicGate[]{LogicGate.AND, LogicGate.OR}, 5),
    P_11("/assets/minigames/computer_science/puzzle_2_2.png", new LogicGate[]{LogicGate.AND, LogicGate.OR}, 5),
    P_12("/assets/minigames/computer_science/puzzle_2_3.png", new LogicGate[]{LogicGate.AND, LogicGate.OR}, 5),

    P_20("/assets/minigames/computer_science/puzzle_3_1.png", new LogicGate[]{LogicGate.AND, LogicGate.OR, LogicGate.NAND}, 6),
    P_21("/assets/minigames/computer_science/puzzle_3_2.png", new LogicGate[]{LogicGate.AND, LogicGate.OR, LogicGate.NAND}, 6),
    P_22("/assets/minigames/computer_science/puzzle_3_3.png", new LogicGate[]{LogicGate.AND, LogicGate.OR, LogicGate.NAND}, 6);

    /**
     * Enthält alle Puzzle nach Level geordnet
     */
    private static final ComputerSciencePuzzle[][] PUZZLES = {
        {
            ComputerSciencePuzzle.P_00,
            ComputerSciencePuzzle.P_01,
            ComputerSciencePuzzle.P_02
        }, {
            ComputerSciencePuzzle.P_10,
            ComputerSciencePuzzle.P_11,
            ComputerSciencePuzzle.P_12
        }, {
            ComputerSciencePuzzle.P_20,
            ComputerSciencePuzzle.P_21,
            ComputerSciencePuzzle.P_22
        }
    };

    public final Image SPRITE;
    /**
     * Lösung des Puzzles
     */
    public final LogicGate[] SOLUTION;
    /**
     * Antwortmöglichkeiten des Puzzles
     */
    public final LogicGate[] ANSWERS;

    /**
     * Ein neues Puzzle erstellen.
     * @param path Der Pfad des Sprites
     * @param solution Die Lösung des Puzzles
     * @param amountOfAnswers Die Anzahl an Antwortmöglichkeiten
     */
    ComputerSciencePuzzle(String path, LogicGate[] solution, int amountOfAnswers) {
        this.SPRITE = Images.get(path);
        this.SOLUTION = solution;
        this.ANSWERS = LogicGate.getRandomArray(amountOfAnswers, solution);
    }

    /**
     * Gibt zufällig eines der drei Puzzles des Levels zurück.
     * @param level Das Level
     * @return Zufällige, zum Level gehörende Enum-Konstante
     */
    public static ComputerSciencePuzzle getPuzzle(int level) {
        int r = new Random().nextInt(3);
        return PUZZLES[level][r];
    }
}
