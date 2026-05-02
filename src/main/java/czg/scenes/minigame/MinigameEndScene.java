package czg.scenes.minigame;

import czg.MainWindow;
import czg.objects.*;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.util.Images;

/**
 * "Gewonnen"- bzw. "Game Over"-Szene, die am Ende eines
 * Minigame-Levels angezeigt wird
 * @see LevelScene
 */
public class MinigameEndScene extends BaseScene {

    /**
     * End-Szene erstellen
     * @param won Ob das Minigame gewonnen wurde. Wenn {@code true}, wird die {@code reward} angezeigt
     * @param level Welches Level des Minigames gespielt wurde. Siehe {@code department} für Begründung.
     * @param reward {@link ItemType}, welches die Belohnung für ein gewonnenes Minigame darstellt
     */
    public MinigameEndScene(boolean won, int level, ItemType reward) {
        objects.add(new BackdropObject(Images.get("/assets/minigames/general/background_overlay.png")));

        if(won) {
            BaseObject banner = new BaseObject(Images.get("/assets/minigames/general/banner_won.png"));
            banner.x = (MainWindow.WIDTH - banner.width) / 2;
            banner.y = (MainWindow.HEIGHT - banner.height) / 3;

            objects.add(banner);

            BaseObject rewardContainer = new BaseObject(Images.get("/assets/minigames/general/reward_container.png"));
            rewardContainer.x = (MainWindow.WIDTH - rewardContainer.width) / 2;
            rewardContainer.y = (int)((MainWindow.HEIGHT - rewardContainer.height) * 0.8);

            objects.add(rewardContainer);

            BaseObject rewardItem = new ItemObject(reward, 0, 0, 0);
            rewardItem.x = MainWindow.WIDTH / 2 - rewardItem.width / 2;
            rewardItem.y = rewardContainer.y + rewardContainer.height / 2 - rewardItem.height / 2;

            objects.add(rewardItem);
        } else {
            BaseObject banner = new BaseObject(Images.get("/assets/minigames/general/banner_lost.png"));
            banner.x = (MainWindow.WIDTH - banner.width) / 2;
            banner.y = (MainWindow.HEIGHT - banner.height) / 3;

            objects.add(banner);
        }

        ButtonObject retryButton = new ButtonObject(Images.get("/assets/minigames/general/button_retry.png"), () -> Minigames.resetMinigameLevel(level, true));

        retryButton.x = (int)((MainWindow.WIDTH - retryButton.width) * 0.4);
        retryButton.y = (int)((MainWindow.HEIGHT - retryButton.height) * 0.6);

        objects.add(retryButton);

        ButtonObject menuButton = new ButtonObject(Images.get("/assets/minigames/general/button_menu.png"), () -> Minigames.resetMinigameLevel(level, false));

        menuButton.x = (int)((MainWindow.WIDTH - menuButton.width) * 0.5);
        menuButton.y = (int)((MainWindow.HEIGHT - menuButton.height) * 0.6);

        objects.add(menuButton);

        ButtonObject exitButton = new ButtonObject(Images.get("/assets/minigames/general/button_exit.png"), () -> {
            Minigames.resetMinigameLevel(level, false);
            // LevelSelectorScene
            SceneStack.INSTANCE.pop();
        });

        exitButton.x = (int)((MainWindow.WIDTH - exitButton.width) * 0.6);
        exitButton.y = (int)((MainWindow.HEIGHT - exitButton.height) * 0.6);

        objects.add(exitButton);
    }
}
