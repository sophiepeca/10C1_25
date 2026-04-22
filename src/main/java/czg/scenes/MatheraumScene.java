package czg.scenes;

import czg.objects.*;
import czg.objects.music_loop_object.MusicLoopObject;
import czg.objects.music_loop_object.SegmentChangeMarker;
import czg.sound.BaseSound;
import czg.sound.EndOfFileBehaviour;
import czg.sound.StreamSound;
import czg.util.Images;

public class MatheraumScene extends BaseScene{
    public MatheraumScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Matheraum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, MathegangScene::new, PfeilObject.UNTEN));

        objects.add(new ButtonObject(LehrerObject.getImage(Department.MATHEMATICS),
                () -> {
                    SceneStack.INSTANCE.push(new KampfScene(Department.COMPUTER_SCIENCE));
                    SceneStack.INSTANCE.push(new InventarScene(false));
                    PlayerObject.INSTANCE.allowInventory = false;
                }));

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 270;
        PlayerObject.INSTANCE.y = 295;

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
}