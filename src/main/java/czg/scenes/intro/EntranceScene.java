package czg.scenes.intro;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.PlayerObject;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.util.Images;
import czg.util.Sounds;
import czg.util.character_creator.CharacterCreator;

import static czg.MainWindow.*;

public class EntranceScene extends BaseScene {

    public EntranceScene() {
        final int foyerPlayerX = PlayerObject.INSTANCE.x;
        final int foyerPlayerY = PlayerObject.INSTANCE.y;
        PlayerObject.INSTANCE.x = 35 * PIXEL_SCALE;
        PlayerObject.INSTANCE.y = HEIGHT - 14 * PIXEL_SCALE - PlayerObject.INSTANCE.height;

        objects.add(new BackdropObject(Images.get("/assets/intro/entrance.png")));
        objects.add(new ButtonObject(null, 118 * PIXEL_SCALE, 38 * PIXEL_SCALE, 98 * PIXEL_SCALE, 72 * PIXEL_SCALE , () -> {
            SceneStack.INSTANCE.pop();
            PlayerObject.INSTANCE.allowInventory = true;
            PlayerObject.INSTANCE.x = foyerPlayerX;
            PlayerObject.INSTANCE.y = foyerPlayerY;
            Sounds.HALLWAY_MUSIC.getVolumeControl().setValue(-4f);
        }));

        PlayerObject.INSTANCE.allowInventory = false;
        objects.add(PlayerObject.INSTANCE);

        CharacterCreator.enabled = true;

        Sounds.HALLWAY_MUSIC.getVolumeControl().setValue(-8f);

    }

}
