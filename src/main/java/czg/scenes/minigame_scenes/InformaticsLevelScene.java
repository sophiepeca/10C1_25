package czg.scenes.minigame_scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.BaseObject;
import czg.objects.ButtonObject;
import czg.objects.minigame_objects.InfoPuzzleObject;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.util.Images;

public class InformaticsLevelScene extends BaseScene {

    public InformaticsLevelScene(InfoPuzzleObject puzzle) {
        objects.add(new BackdropObject(Images.get("/assets/minigames/informatics/background.png")));

        ButtonObject exitButton = new ButtonObject(Images.get("/assets/minigames/general/exit_button.png"),
                () -> {
                    /*SceneStack.INSTANCE.pop();*/SceneStack.INSTANCE.pop();
                }
        );
        exitButton.width /= 2;
        exitButton.height /= 2;
        exitButton.x = (int) (MainWindow.WIDTH - exitButton.width * 2);
        exitButton.y = (int) (exitButton.height * 0.5);

        objects.add(exitButton);

        ButtonObject menuButton = new ButtonObject(Images.get("/assets/minigames/general/menu_button.png"), SceneStack.INSTANCE::pop);
        menuButton.width /= 2;
        menuButton.height /= 2;
        menuButton.x = (int) (MainWindow.WIDTH - menuButton.width * 4);
        menuButton.y = (int) (menuButton.height * 0.5);

        objects.add(menuButton);

        int availableHeight = (int) (MainWindow.HEIGHT * 0.7);
        int gateHeight = availableHeight / puzzle.answers.length;

        for (int i = 0; i < puzzle.answers.length; i++) {
            int finalI = i;
            objects.add(new ButtonObject(
                    puzzle.answers[i].sprite,
                    (int) (MainWindow.WIDTH * 0.125),
                    (MainWindow.HEIGHT - availableHeight) / 2 + i * gateHeight,
                    () -> {
                        if (puzzle.answers[finalI] == puzzle.solution)
                            System.out.println("CORRECT!");
                        else
                            System.out.println("WRONG!");
                    })
            );
        }

        objects.add(new BaseObject(
                puzzle.sprite,
                (int) (MainWindow.WIDTH * 0.3),
                (MainWindow.HEIGHT - availableHeight) / 2,
                (int) (MainWindow.WIDTH * 0.6),
                availableHeight
        ));
    }
}
