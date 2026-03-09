package czg.scenes.minigame_scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.BaseObject;
import czg.objects.ButtonObject;
import czg.objects.ItemObject;
import czg.objects.minigame_objects.InfoPuzzleObject;
import czg.objects.minigame_objects.MinigameNameObject;
import czg.scenes.InfogangScene;
import czg.scenes.SceneStack;
import czg.util.Images;

public class InformaticsLevelScene extends LevelScene {
    public InformaticsLevelScene(int level) {
        super(MinigameNameObject.INFORMATICS, level);

        InfoPuzzleObject puzzle = InfoPuzzleObject.getPuzzle(level);

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
                            minigameWon();
                        else
                            minigameLost();
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
