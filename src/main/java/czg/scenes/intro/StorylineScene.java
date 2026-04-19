package czg.scenes.intro;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.scenes.BaseScene;
import czg.scenes.FoyerScene;
import czg.scenes.SceneStack;
import czg.util.Images;

import static czg.MainWindow.HEIGHT;
import static czg.MainWindow.WIDTH;

public class StorylineScene extends BaseScene {
    public StorylineScene() {
        objects.add(new BackdropObject(Images.get("/assets/intro/story.png")));
        objects.add(new ButtonObject(null, 0, 0, WIDTH, HEIGHT, () -> {
            SceneStack.INSTANCE.pop();
            SceneStack.INSTANCE.push(new FoyerScene());
            SceneStack.INSTANCE.push(new EntranceScene());
        }));
    }
}
