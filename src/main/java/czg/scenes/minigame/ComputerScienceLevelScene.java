package czg.scenes.minigame;

import czg.MainWindow;
import czg.minigame.ComputerSciencePuzzle;
import czg.minigame.LogicGate;
import czg.objects.BaseObject;
import czg.objects.ButtonObject;
import czg.objects.Department;
import czg.util.Images;

import java.util.Arrays;

/**
 * Level des Informatik-Minigames, in welchem Logikgatter in einem
 * Schaltkreis erkannt werden müssen.
 */
public class ComputerScienceLevelScene extends LevelScene {
    public final int LEVEL;
    public final ComputerSciencePuzzle PUZZLE;

    private final BaseObject[] ANSWER_FRAMES;
    private final LogicGate[] ACTIVE_ANSWERS;

    /**
     * Neue Level-Szene erstellen
     * @param level Entweder {@code 0}, {@code 1} oder {@code 2}
     */
    public ComputerScienceLevelScene(int level) {
        super(Department.COMPUTER_SCIENCE, level);

        this.LEVEL = level;

        // Zufällig eines von drei für dieses Level verfügbaren
        // Rätsel auswählen
        this.PUZZLE = ComputerSciencePuzzle.getPuzzle(LEVEL);

        this.ANSWER_FRAMES = new BaseObject[]{
            new BaseObject(Images.get("/assets/minigames/computer_science/answer_frame_0.png")),
            new BaseObject(Images.get("/assets/minigames/computer_science/answer_frame_1.png")),
            new BaseObject(Images.get("/assets/minigames/computer_science/answer_frame_2.png"))
        };

        this.ACTIVE_ANSWERS = new LogicGate[PUZZLE.SOLUTION.length];

        int availableHeight = (int) (MainWindow.HEIGHT * 0.7);
        int gateHeight = availableHeight / PUZZLE.ANSWERS.length;

        // ButtonObjects für die Antwortmöglichkeiten
        for (int i = 0; i < PUZZLE.ANSWERS.length; i++) {
            int finalI = i;
            objects.add(new ButtonObject(
                PUZZLE.ANSWERS[i].SPRITE,
                (int) (MainWindow.WIDTH * 0.125),
                (MainWindow.HEIGHT - availableHeight) / 2 + i * gateHeight,
                () -> setAnswer(
                    PUZZLE.ANSWERS[finalI],
                    (int) (MainWindow.WIDTH * 0.125),
                    (MainWindow.HEIGHT - availableHeight) / 2 + finalI * gateHeight)
                )
            );
        }

        // Das eigentliche Rätsel wird durch ein Bild dargestellt
        objects.add(new BaseObject(
                PUZZLE.SPRITE,
                (int) (MainWindow.WIDTH * 0.3),
                (MainWindow.HEIGHT - availableHeight) / 2,
                (int) (MainWindow.WIDTH * 0.6),
                availableHeight
        ));
    }

    /**
     * Zeichnen des Rahmens um das geantwortete Gatter und Überprüfung, ob die Lösung korrekt ist.
     * @param gate Das geantwortete Gatter
     * @param x Dessen X-Position
     * @param y Dessen Y-Position
     */
    private void setAnswer(LogicGate gate, int x, int y) {
        // Falls nur ein Gatter zu antworten ist, wird überprüft, ob dieses korrekt ist und
        // das Level dementsprechend gewonnen bzw. verloren
        if(PUZZLE.SOLUTION.length == 1) {
            if(gate == PUZZLE.SOLUTION[0])
                levelWon();
            else
                levelLost();

            return;
        }

        // Falls das geantwortete Gatter bereits geantwortet wurde, wird es wieder aus den Antworten entfernt
        if(Arrays.asList(ACTIVE_ANSWERS).contains(gate)) {
            for(int i = 0; i < ACTIVE_ANSWERS.length; i++) {
                if(ACTIVE_ANSWERS[i] == gate) {
                    ACTIVE_ANSWERS[i] = null;
                    objects.remove(ANSWER_FRAMES[i]);
                }
            }
        }
        // Andernfalls wird das geantwortete Gatter an der frühst möglichen Stelle der aktiven Antworten hinzugefügt
        // und der entsprechende Rahmen gezeichnet, sowie überprüft, ob das Puzzle gelöst wurde.
        else {
            for(int i = 0; i < ACTIVE_ANSWERS.length; i++) {
                if(ACTIVE_ANSWERS[i] == null) {
                    ACTIVE_ANSWERS[i] = gate;
                    ANSWER_FRAMES[i].x = x;
                    ANSWER_FRAMES[i].y = y;
                    objects.add(ANSWER_FRAMES[i]);
                    break;
                }
            }

            // Überprüfen, ob das Puzzle gelöst wurde
            boolean solved = true;
            for (int i = 0; i < PUZZLE.SOLUTION.length ; i++) {
                if (PUZZLE.SOLUTION[i] != ACTIVE_ANSWERS[i]) {
                    solved = false;
                    break;
                }
            }

            // Level gewinnen, falls ja
            if (solved) levelWon();
        }
    }

    /**
     * Generiert eine neue {@link ComputerScienceLevelScene}
     * @return {@code new {@link ComputerScienceLevelScene}}
     */
    @Override
    public LevelScene reset() {
        return new ComputerScienceLevelScene(LEVEL);
    }
}
