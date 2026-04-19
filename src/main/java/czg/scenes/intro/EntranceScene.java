package czg.scenes.intro;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.util.Images;

import static czg.MainWindow.HEIGHT;
import static czg.MainWindow.WIDTH;

public class EntranceScene extends BaseScene {

    public EntranceScene() {
        objects.add(new BackdropObject(Images.get("/assets/intro/entrance.png")));
        objects.add(new ButtonObject(null, 0, 0, WIDTH, HEIGHT, SceneStack.INSTANCE::pop));
    }

}
