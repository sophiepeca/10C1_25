package czg.scenes.minigame_scenes;

import czg.MainWindow;
import czg.objects.*;
import czg.objects.minigame_objects.MinigameNameObject;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.scenes.cover_settings.CoverSettings;
import czg.util.Images;

public abstract class LevelScene extends BaseScene {
    final MinigameNameObject MINIGAME_TYPE;
    final int LEVEL;
    final ItemObject REWARD;

    public LevelScene(MinigameNameObject minigameType, int level) {
        super(new CoverSettings(false, true, false));

        this.MINIGAME_TYPE = minigameType;
        this.LEVEL = level;
        this.REWARD = ItemObject.getMinigameReward(minigameType, level);

        objects.add(new BackdropObject(Images.get(String.format("/assets/minigames/%s/background.png", minigameType.name().toLowerCase()))));

        ButtonObject exitButton = new ButtonObject(Images.get("/assets/minigames/general/button_exit.png"), () -> {SceneStack.INSTANCE.pop(); SceneStack.INSTANCE.pop();}
        );
        exitButton.width /= 2;
        exitButton.height /= 2;
        exitButton.x = (int) (MainWindow.WIDTH - exitButton.width * 2);
        exitButton.y = (int) (exitButton.height * 0.3);

        objects.add(exitButton);

        ButtonObject menuButton = new ButtonObject(Images.get("/assets/minigames/general/button_menu.png"), SceneStack.INSTANCE::pop);
        menuButton.width /= 2;
        menuButton.height /= 2;
        menuButton.x = (int) (MainWindow.WIDTH - menuButton.width * 4);
        menuButton.y = (int) (menuButton.height * 0.3);

        objects.add(menuButton);
    }

    protected void minigameWon() {
        SceneStack.INSTANCE.push(new MinigameEndScene(this.MINIGAME_TYPE, true, LEVEL, REWARD));

        PlayerObject.INSTANCE.inventar.add(REWARD);
    }

    protected void minigameLost() {
        SceneStack.INSTANCE.push(new MinigameEndScene(this.MINIGAME_TYPE, false, LEVEL, REWARD));
    }
}
