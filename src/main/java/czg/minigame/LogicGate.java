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

    public final Image SPRITE;

    LogicGate(String path) {
        this.SPRITE = Images.get(path);
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
        // Das zu generierende Array
        LogicGate[] out = new LogicGate[length];
        // Indizes der Gatter, die beinhaltet werden sollen
        List<Integer> includedGatesIdx = new ArrayList<>();

        // Abbruch, falls mehr Gatter zurückgegeben werden sollen, als Enum-Konstanten existieren.
        if (length > LogicGate.values().length) return null;

        // Abbruch, falls die Länge der zu beinhaltenden Gatter größer ist, als die der gesamten Liste.
        if (includedGates.length > length) return null;

        // Generierung der Indizes der Gatter, die beinhaltet werden sollen
        for (int i = 0; i < includedGates.length; i++) {
            // zufälliger Index
            int r = new Random().nextInt(length);

            // Neuer zufälliger Index, solange der alte bereits generiert wurde
            while (includedGatesIdx.contains(r)) {
                r = new Random().nextInt(length);
            }

            // Hinzufügen des Indexes
            includedGatesIdx.add(r);
        }

        // Generierung des Logikgatter-Arrays
        for (int i = 0; i < length; i++) {
            // Überprüfung, ob an Index i ein gegebenes Gatter platziert oder ein neues Gatter generiert werden soll
            if (includedGatesIdx.contains(i)) {
                // Hinzufügen des gegebenen Gatters an dem in includedGatesIdx gespeicherten Index
                out[i] = includedGates[includedGatesIdx.indexOf(i)];
            } else {
                // Zufälliges Gatter
                LogicGate rGate = getRandom();
                // Neues zufälliges Gatter, solange das alte entweder in den gegebenen Gattern oder in dem bereits generierten Array enthalten ist.
                while (Arrays.asList(includedGates).contains(rGate) || Arrays.asList(out).contains(rGate)) {
                    rGate = getRandom();
                }

                // Hinzufügen des Gatters
                out[i] = rGate;
            }
        }

        // Rückgabe des fertigen Arrays
        return out;
    }
}
