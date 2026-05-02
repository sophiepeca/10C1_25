package czg.scenes.intro;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.util.Images;

import static czg.MainWindow.PIXEL_SCALE;
import static czg.util.Sounds.HALLWAY_MUSIC;

public class TitleScreenScene extends BaseScene {

    public TitleScreenScene() {
        objects.add(new BackdropObject(Images.get("/assets/intro/title_screen.png")));
        objects.add(new ButtonObject(
                null, 98 * PIXEL_SCALE, 80 * PIXEL_SCALE, 55 * PIXEL_SCALE, 14 * PIXEL_SCALE,
                () -> SceneStack.INSTANCE.replace(this, new StorylineScene()))
        );

        HALLWAY_MUSIC.getVolumeControl().setValue(-24f);
        HALLWAY_MUSIC.setPlaying(true);
    }

}
