package czg.scenes.minigame_scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.scenes.BaseScene;
import czg.util.Images;

import java.awt.*;
import java.util.List;

public class InformaticsLevelScene extends BaseScene {
    public InformaticsLevelScene(Image puzzle, List<String> answers) {
        objects.add(new BackdropObject(Images.get("/assets/minigames/informatics/mg_inf_bg.png")));

        int availableHeight = (int) (MainWindow.HEIGHT*0.7);
        int gateHeight = availableHeight/answers.size();

        for (int i = 0; i < answers.size(); i++) {
            objects.add(new ButtonObject(Images.get("/assets/minigames/informatics/" + answers.get(i) + ".png"),
                    (int) (MainWindow.WIDTH*0.05) + 100,
                    (MainWindow.HEIGHT-availableHeight) / 2 + i*gateHeight,
                    () -> System.out.println("WRONG!"))
            );
        }
    }
}
