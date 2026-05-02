package czg.scenes.minigame;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.Department;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.scenes.cover_settings.CoverSettings;
import czg.sound.EndOfFileBehaviour;
import czg.sound.StreamSound;
import czg.util.Images;
import czg.util.Sounds;

/**
 * Szene, mit welcher die Levels eines Minigames gestartet werden
 * @see Minigames#generateMinigame(Department)
 */
public class LevelSelectorScene extends BaseScene {

    /**
     * Die drei {@link LevelScene}s, zu denen die Buttons führen
     */
    public final LevelScene[] levels;

    /**
     * Neuen Level-Selector erstellen
     * @param level1 Erstes Level ({@code levels[0]})
     * @param level2 Zweites Level ({@code levels[1]})
     * @param level3 Drittes Level ({@code levels[2]})
     */
    public LevelSelectorScene(LevelScene level1, LevelScene level2, LevelScene level3) {
        // Wichtig: Musik nicht pausieren, wenn die Level- oder End-Szenen gezeigt werden
        super(new CoverSettings(true, true, false));

        // Hintergrund
        objects.add(new BackdropObject(Images.get("/assets/minigames/general/level_selector_background.png")));

        // Levels speichern
        this.levels = new LevelScene[]{level1,level2,level3};

        // Buttons für das Spielen der Levels und zum Verlassen des Minigames
        // erstellen, positionieren und zur Szene hinzufügen
        ButtonObject buttonLevel1 = new ButtonObject(Images.get("/assets/minigames/general/button_level_1.png"), () -> SceneStack.INSTANCE.push(levels[0]));
        ButtonObject buttonLevel2 = new ButtonObject(Images.get("/assets/minigames/general/button_level_2.png"), () -> SceneStack.INSTANCE.push(levels[1]));
        ButtonObject buttonLevel3 = new ButtonObject(Images.get("/assets/minigames/general/button_level_3.png"), () -> SceneStack.INSTANCE.push(levels[2]));
        ButtonObject buttonExit = new ButtonObject(Images.get("/assets/minigames/general/button_exit.png"), SceneStack.INSTANCE::pop);

        buttonLevel1.x = MainWindow.WIDTH / 2 - buttonLevel1.width / 2;
        buttonLevel1.y = (int) (MainWindow.HEIGHT * 0.35);

        buttonLevel2.x = MainWindow.WIDTH / 2 - buttonLevel2.width / 2;
        buttonLevel2.y = (int) (MainWindow.HEIGHT * 0.55);

        buttonLevel3.x = MainWindow.WIDTH / 2 - buttonLevel3.width / 2;
        buttonLevel3.y = (int) (MainWindow.HEIGHT * 0.75);

        buttonExit.width /= 2;
        buttonExit.height /= 2;
        buttonExit.x = MainWindow.WIDTH - buttonExit.width * 2;
        buttonExit.y = (int) (buttonExit.height * 0.5);

        objects.add(buttonExit);

        objects.add(buttonLevel1);
        objects.add(buttonLevel2);
        objects.add(buttonLevel3);

        // Gang-Musik pausieren
        Sounds.HALLWAY_MUSIC.setPlaying(false);
        // Minigame-Musik starten
        sounds.get().addSound(new StreamSound("/assets/sound/minigame.ogg", true, EndOfFileBehaviour.LOOP));
    }

    @Override
    public void unload() {
        super.unload();

        // Gang-Musik fortsetzen
        Sounds.HALLWAY_MUSIC.setPlaying(true);
    }
}