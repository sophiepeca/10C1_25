package czg.minigame;

import czg.scenes.minigame.ComputerScienceLevelScene;
import czg.util.Images;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Informationen über die verschiedenen Logikgatter für {@link ComputerSciencePuzzle}
 * und {@link ComputerScienceLevelScene}
 */
public enum LogicGate {

    AND("/assets/minigames/computer_science/and_gate.png"),
    OR("/assets/minigames/computer_science/or_gate.png"),
    NAND("/assets/minigames/computer_science/nand_gate.png"),
    NOR("/assets/minigames/computer_science/nor_gate.png"),
    XOR("/assets/minigames/computer_science/xor_gate.png"),
    XNOR("/assets/minigames/computer_science/xnor_gate.png");

    public final Image sprite;

    LogicGate(String path) {
        this.sprite = Images.get(path);
    }

    /**
     * Gibt eine zufällige Enum-Konstante zurück
     * @return Enum-Konstante
     */
    public static LogicGate getRandom() {
        int r = new Random().nextInt(values().length);
        return LogicGate.values()[r];
    }

    /**
     * Gibt einen Array von zufälligen Enum-Konstanten zurück.
     * Dabei kommt jeder Wert im Array nur ein Mal vor.
     * @param length Wie viele Elemente?
     * @param includedGates Diese Elemente werden definitiv im Ergebnis enthalten sein
     * @return Array mit Enum-Konstanten
     */
    public static LogicGate[] getRandomArray(int length, LogicGate[] includedGates) {
        LogicGate[] tmp = new LogicGate[length];
        LogicGate[] usedGates = new LogicGate[length];
        List<Integer> includedGatesIdx = new ArrayList<>();

        for (int i = 0; i < includedGates.length; i++) {
            int r = new Random().nextInt(length);

            while (includedGatesIdx.contains(r)) {
                r = new Random().nextInt(length);
            }

            includedGatesIdx.add(r);
        }

        for (int i = 0; i < length; i++) {
            boolean included = false;
            for (int j = 0; j < includedGatesIdx.size(); j++) {
                if (i == includedGatesIdx.get(j)) {
                    tmp[i] = includedGates[j];
                    included = true;
                    break;
                }
            }

            if (included) continue;

            LogicGate rGate = getRandom();
            while (Arrays.asList(includedGates).contains(rGate) || Arrays.asList(usedGates).contains(rGate)) {
                rGate = getRandom();
            }

            tmp[i] = rGate;
            usedGates[i] = rGate;
        }

        return tmp;
    }
}
