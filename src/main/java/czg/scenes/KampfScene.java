package czg.scenes;
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
    public static boolean imKampf = false;
    public static int timer = 0;
    public static int lehrerSchaden = 0;

    public KampfScene(String FACHSCHAFT){
        super();
        coverSettings.setRules(new Rules(Setting.KEEP, Setting.OFF, Setting.KEEP), "inventar");

        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Kampfgang.png")));

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/button_exit.png"), 30, 30, SceneStack.INSTANCE::pop));
        
        int LehrerLeben = 10;
        int PlayerLeben = 10;

        imKampf = true;

        LehrerObject Lehrer = new LehrerObject(600, 300, FACHSCHAFT, LehrerLeben, 2);
        this.objects.add(Lehrer);
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 330;
        PlayerObject.INSTANCE.y = 295;

        /*
        while (LehrerLeben > 0 && PlayerLeben > 0) {
            // Zuerst der Schüler
            int SchadenPlayer = PlayerObject.INSTANCE.angriff();
            int SchadenGesamt = Lehrer.verteidigung(SchadenPlayer);
            LehrerLeben -= SchadenGesamt;

            // Dann der Lehrer
            int SchadenLehrer = Lehrer.angriff();
            SchadenGesamt = PlayerObject.INSTANCE.verteidigung(SchadenPlayer);
            PlayerLeben -= SchadenGesamt;
        }
        */

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
        super.update();
        ItemType clicked = InventarScene.getClickedItem();
        if(clicked != null)
            System.out.println(clicked);
    }

    @Override
    public void unload() {
        super.unload();
        Sounds.HALLWAY_MUSIC.setPlaying(true);
        PlayerObject.INSTANCE.allowInventory = true;
    }
}


