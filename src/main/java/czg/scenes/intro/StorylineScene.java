package czg.scenes.intro;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.util.Images;

import static czg.MainWindow.HEIGHT;
import static czg.MainWindow.WIDTH;
import static czg.util.Sounds.HALLWAY_MUSIC;

public class StorylineScene extends BaseScene {
    public StorylineScene() {
        objects.add(new BackdropObject(Images.get("/assets/intro/story.png")));
        objects.add(new ButtonObject(null, 0, 0, WIDTH, HEIGHT, () -> {
            SceneStack.INSTANCE.replace(this, new ChoosingScene());
        }));

        HALLWAY_MUSIC.getVolumeControl().setValue(-18f);
    }
}
