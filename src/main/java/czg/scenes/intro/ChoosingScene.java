package czg.scenes.intro;

import czg.objects.*;
import czg.scenes.BaseScene;
import czg.scenes.FoyerScene;
import czg.scenes.SceneStack;
import czg.util.Images;

import static czg.MainWindow.PIXEL_SCALE;
import static czg.MainWindow.WIDTH;
import static czg.util.Sounds.HALLWAY_MUSIC;


public class ChoosingScene extends BaseScene {
    public ChoosingScene() {
        objects.add(new BackdropObject(Images.get("/assets/intro/choosingbackground.png")));

        int y = 30 * PIXEL_SCALE;

        for(Department dep : Department.values()) {
            ButtonObject button = new ButtonObject(Images.get("/assets/intro/"+dep.name().toLowerCase()+"_button.png"), 0, y, () -> {
                PlayerObject.INSTANCE.addItem(ItemType.getMinigameReward(dep, 0));
                SceneStack.INSTANCE.pop();
                SceneStack.INSTANCE.push(new FoyerScene());
                SceneStack.INSTANCE.push(new EntranceScene());
            });

            button.x = WIDTH / 2 - button.width / 2;
            y += 6 * PIXEL_SCALE + button.height;

            objects.add(button);
        }

        HALLWAY_MUSIC.getVolumeControl().setValue(-12f);
    }

}
