package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.PlayerObject;
import czg.sound.EndOfFileBehaviour;
import czg.sound.StreamSound;
import czg.util.Images;
import czg.util.Sounds;
import czg.util.character_creator.CharacterCreator;

import static czg.MainWindow.PIXEL_SCALE;

public class EndScene extends BaseScene {

    public EndScene() {
        objects.add(new BackdropObject(Images.get("/assets/background/Endbildschirm.png")));

        objects.add(PlayerObject.INSTANCE);
        CharacterCreator.enabled = false;
        PlayerObject.INSTANCE.allowInventory = false;
        PlayerObject.INSTANCE.x = 56 * PIXEL_SCALE;
        PlayerObject.INSTANCE.y = 72 * PIXEL_SCALE;

        Sounds.HALLWAY_MUSIC.stop();
        sounds.get().addSound(new StreamSound("/assets/sound/mensa.ogg", true, EndOfFileBehaviour.LOOP));
    }

}
