package czg.minigame;

import czg.util.Images;

import java.awt.*;
import java.util.Random;

public enum ComputerSciencePuzzle {

    P_00("/assets/minigames/computer_science/puzzle_1_1.png", new LogicGate[]{LogicGate.AND}, 4),
    P_01("/assets/minigames/computer_science/puzzle_1_2.png", new LogicGate[]{LogicGate.OR}, 4),
    P_02("/assets/minigames/computer_science/puzzle_1_3.png", new LogicGate[]{LogicGate.NAND}, 4),

    P_10("/assets/minigames/computer_science/puzzle_2_1.png", new LogicGate[]{LogicGate.AND, LogicGate.OR}, 5),
    P_11("/assets/minigames/computer_science/puzzle_2_2.png", new LogicGate[]{LogicGate.AND, LogicGate.OR}, 5),
    P_12("/assets/minigames/computer_science/puzzle_2_3.png", new LogicGate[]{LogicGate.AND, LogicGate.OR}, 5),

    P_20("/assets/minigames/computer_science/puzzle_3_1.png", new LogicGate[]{LogicGate.AND, LogicGate.OR, LogicGate.NAND}, 6),
    P_21("/assets/minigames/computer_science/puzzle_3_2.png", new LogicGate[]{LogicGate.AND, LogicGate.OR, LogicGate.NAND}, 6),
    P_22("/assets/minigames/computer_science/puzzle_3_3.png", new LogicGate[]{LogicGate.AND, LogicGate.OR, LogicGate.NAND}, 6);

    public static final ComputerSciencePuzzle[][] PUZZLES = {
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
    public final LogicGate[] SOLUTION;
    public final LogicGate[] ANSWERS;

    ComputerSciencePuzzle(String path, LogicGate[] solution, int amountOfAnswers) {
        this.SPRITE = Images.get(path);
        this.SOLUTION = solution;
        this.ANSWERS = LogicGate.getRandomArray(amountOfAnswers, solution);
    }

    public static ComputerSciencePuzzle getPuzzle(int level) {
        int r = new Random().nextInt(3);
        return PUZZLES[level][r];
    }
}
