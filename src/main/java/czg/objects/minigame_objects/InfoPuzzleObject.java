package czg.objects.minigame_objects;

import czg.util.Images;

import java.awt.Image;
import java.util.Random;

public enum InfoPuzzleObject {
    P_00("/assets/minigames/informatics/puzzle_1_1.png", LogicGateObject.AND, 4),
    P_01("/assets/minigames/informatics/puzzle_1_2.png", LogicGateObject.OR, 4),
    P_02("/assets/minigames/informatics/puzzle_1_3.png", LogicGateObject.NAND, 4),

    P_10("/assets/minigames/informatics/puzzle_2_1.png", LogicGateObject.AND, 5),
    P_11("/assets/minigames/informatics/puzzle_2_2.png", LogicGateObject.OR, 5),
    P_12("/assets/minigames/informatics/puzzle_2_3.png", LogicGateObject.NAND, 5),

    P_20("/assets/minigames/informatics/puzzle_3_1.png", LogicGateObject.AND, 6),
    P_21("/assets/minigames/informatics/puzzle_3_2.png", LogicGateObject.OR, 6),
    P_22("/assets/minigames/informatics/puzzle_3_3.png", LogicGateObject.NAND, 6);

    public static final InfoPuzzleObject[][] PUZZLES = {
        {
            InfoPuzzleObject.P_00,
            InfoPuzzleObject.P_01,
            InfoPuzzleObject.P_02
        }, {
            InfoPuzzleObject.P_10,
            InfoPuzzleObject.P_11,
            InfoPuzzleObject.P_12
        }, {
            InfoPuzzleObject.P_20,
            InfoPuzzleObject.P_21,
            InfoPuzzleObject.P_22
        }
    };

    public final Image sprite;
    public final LogicGateObject solution;
    public final LogicGateObject[] answers;

    InfoPuzzleObject(String path, LogicGateObject solution, int amountOfAnswers) {
        this.sprite = Images.get(path);
        this.solution = solution;
        this.answers = LogicGateObject.getRandomArray(amountOfAnswers, solution);

        int r = (int) (new Random().nextDouble() * amountOfAnswers);

        this.answers[r] = solution;
    }

    public static InfoPuzzleObject getPuzzle(int level) {
        int r = (int) (new Random().nextDouble() * 3);

        return PUZZLES[level][r];
    }
}
