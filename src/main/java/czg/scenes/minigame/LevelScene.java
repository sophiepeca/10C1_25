package czg.scenes.minigame;

import czg.MainWindow;
import czg.objects.*;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.scenes.cover_settings.CoverSettings;
import czg.util.Images;

/**
 * Basis-Klasse für Levels von Minigames. Enthält bereits einen
 * Hintergrund sowie Buttons zum Beenden des gesamten Minigames
 * und für die Levelauswahl.
 */
public abstract class LevelScene extends BaseScene {

    /**
     * Um welches der verfügbaren Level {@code 0}, {@code 1} und {@code 2} es sich handelt
     */
    final int LEVEL;
    /**
     * Belohnung, wenn das Level gewonnen wird
     */
    final ItemObject REWARD;

    /**
     * Erstellen einer neuen Level-Szene, welche Buttons zum
     * Beenden des gesamten Minigames und für die Levelauswahl
     * enthält
     * @param department Wird verwendet, um das richtige Hintergrundbild zu laden
     * @param level Siehe {@link #LEVEL}
     */
    protected LevelScene(Department department, int level) {
        super(new CoverSettings(false, true, false));

        this.LEVEL = level;
        this.REWARD = ItemObject.getMinigameReward(department, level);

        objects.add(new BackdropObject(Images.get(String.format("/assets/minigames/%s/background.png", department.name().toLowerCase()))));

        ButtonObject exitButton = new ButtonObject(Images.get("/assets/minigames/general/button_exit.png"), () -> {
            // Level entfernen
            SceneStack.INSTANCE.pop();
            // LevelSelector entfernen
            SceneStack.INSTANCE.pop();
        });

        exitButton.width /= 2;
        exitButton.height /= 2;
        exitButton.x = MainWindow.WIDTH - exitButton.width * 2;
        exitButton.y = (int) (exitButton.height * 0.3);

        objects.add(exitButton);

        ButtonObject menuButton = new ButtonObject(Images.get("/assets/minigames/general/button_menu.png"), SceneStack.INSTANCE::pop);
        menuButton.width /= 2;
        menuButton.height /= 2;
        menuButton.x = MainWindow.WIDTH - menuButton.width * 4;
        menuButton.y = (int) (menuButton.height * 0.3);

        objects.add(menuButton);
    }

    /**
     * Setzt das Level zurück, indem eine neue {@link LevelScene} erstellt
     * wird, deren Zustand dem Anfangszustand dieser Szene entspricht.
     * @return Eine <b>eventuell neue</b> {@link LevelScene}
     */
    public abstract LevelScene reset();

    /**
     * Das Level wurde gewonnen.
     * Zeigt die entsprechende {@link MinigameEndScene} und
     * fügt {@link #REWARD} zu {@link PlayerObject#inventar} hinzu.
     */
    public void levelWon() {
        SceneStack.INSTANCE.push(new MinigameEndScene(true, LEVEL, REWARD));
        if(! PlayerObject.INSTANCE.inventar.contains(REWARD))
            PlayerObject.INSTANCE.inventar.add(REWARD);
    }

    /**
     * Das Level wurde verloren. Zeigt die entsprechende {@link MinigameEndScene}.
     */
    public void levelLost() {
        SceneStack.INSTANCE.push(new MinigameEndScene(false, LEVEL, REWARD));
    }
}
