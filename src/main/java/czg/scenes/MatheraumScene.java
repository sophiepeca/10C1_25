/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.music_object.MusicLoopObject;
import czg.objects.music_object.SegmentChangeMarker;
import czg.sound.BaseSound;
import czg.sound.EndOfFileBehaviour;
import czg.sound.SoundGroup;
import czg.sound.StreamSound;
import czg.util.Images;

import static czg.MainWindow.WIDTH;
/**
 *
 * @author guest-rwl69f
 */
public class MatheraumScene extends BaseScene{
    public MatheraumScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Matheraum.png")));
        

        ButtonObject unten = new ButtonObject(
                Images.get("/assets/background/PfeilUnten.png"),
                () -> {
                    MathegangScene hausm = new MathegangScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    tr1.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, hausm);
                });

        unten.x = (WIDTH/2) - (unten.width/2);
        unten.y = 440;
        objects.add(unten);

        // TEST
        SoundGroup.GLOBAL_SOUNDS.pause();

        BaseSound intro = sounds.get().addSound(new StreamSound("/assets/sound/fight_intro.ogg", false, EndOfFileBehaviour.STOP));
        BaseSound loop1 = sounds.get().addSound(new StreamSound("/assets/sound/fight_loop.ogg", false, EndOfFileBehaviour.RESTART_AND_PAUSE));
        BaseSound loop2 = sounds.get().addSound(new StreamSound("/assets/sound/fight_loop.ogg", false, EndOfFileBehaviour.RESTART_AND_PAUSE));

        MusicLoopObject music = new MusicLoopObject()
                .addTrackSegment(intro, new SegmentChangeMarker(0.928560587d, loop1))
                .addTrackSegment(loop1, new SegmentChangeMarker(0.972987461d, loop2))
                .addTrackSegment(loop2, new SegmentChangeMarker(0.972987461d, loop1))
                .start();

        objects.add(music);
    }

    @Override
    public void unload() {
        super.unload();
        SoundGroup.GLOBAL_SOUNDS.resume();
    }
}