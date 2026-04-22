package czg.scenes;

import czg.MainWindow;
import czg.objects.*;
import czg.objects.music_loop_object.MusicLoopObject;
import czg.objects.music_loop_object.SegmentChangeMarker;
import czg.scenes.cover_settings.Rules;
import czg.scenes.cover_settings.Setting;
import czg.sound.BaseSound;
import czg.sound.EndOfFileBehaviour;
import czg.sound.StreamSound;
import czg.util.Images;
import czg.util.Sounds;

/**
 * @author Sophie
 */
public class KampfScene extends BaseScene{
    public static boolean lehrerTurn = false;
    public static boolean lehrerVerteidigung = false;
    public static boolean PlayerTurn = true;
    public static boolean PlayerVerteidigung = false;
    public static boolean imKampf = false;
    public static int timer = 0;
    public static int Zwischenschaden = 0;
    public static int Endschaden = 0;
    public static int LehrerLeben = 10;
    public static int PlayerLeben = 10;
    public static ItemType clicked;
    public static Department FACHSCHAFT;

    public KampfScene(Department FACHSCHAFT){
        super();
        this.FACHSCHAFT = FACHSCHAFT;

        coverSettings.setRules(new Rules(Setting.KEEP, Setting.OFF, Setting.KEEP), "inventar");

        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Kampfgang.png")));

        imKampf = true;

        LehrerObject Lehrer = new LehrerObject(700, 200, FACHSCHAFT);
        this.objects.add(Lehrer);
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 330;
        PlayerObject.INSTANCE.y = 295;

        Sounds.HALLWAY_MUSIC.setPlaying(false);

        BaseSound intro = sounds.get().addSound(new StreamSound("/assets/sound/fight_intro.ogg", false, EndOfFileBehaviour.STOP));
        BaseSound loop1 = sounds.get().addSound(new StreamSound("/assets/sound/fight_loop.ogg", false, EndOfFileBehaviour.RESTART_AND_PAUSE));
        BaseSound loop2 = sounds.get().addSound(new StreamSound("/assets/sound/fight_loop.ogg", false, EndOfFileBehaviour.RESTART_AND_PAUSE));

        MusicLoopObject music = new MusicLoopObject()
                .addTrackSegment(intro, new SegmentChangeMarker(18_353, loop1))
                .addTrackSegment(loop1, new SegmentChangeMarker(50_854, loop2))
                .addTrackSegment(loop2, new SegmentChangeMarker(50_854, loop1))
                .start();

        objects.add(music);
    }

    @Override
    public void update() {
        clicked = InventarScene.getClickedItem();
        super.update();

        if(timer > 0) {
            timer -= 1;
        }

        // Das unloaden funktioniert noch nicht
        if(PlayerLeben <= 0) {
            unload();
        }

        if(LehrerLeben <= 0) {
            MainWindow.UebrigeLehrer -= 1;
            unload();
        }
    }

    @Override
    public void unload() {
        super.unload();
        Sounds.HALLWAY_MUSIC.setPlaying(true);
        PlayerObject.INSTANCE.allowInventory = true;
    }
}


