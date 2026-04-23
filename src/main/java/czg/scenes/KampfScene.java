package czg.scenes;

import czg.objects.*;
import czg.objects.music_loop_object.MusicLoopObject;
import czg.objects.music_loop_object.SegmentChangeMarker;
import czg.scenes.cover_settings.Rules;
import czg.scenes.cover_settings.Setting;
import czg.sound.BaseSound;
import czg.sound.EndOfFileBehaviour;
import czg.sound.StreamSound;
import czg.util.Draw;
import czg.util.Images;
import czg.util.Sounds;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static czg.MainWindow.*;

/**
 * @author Sophie
 */
public class KampfScene extends BaseScene{

    public enum Turn { PLAYER_ATTACK, PLAYER_DEFEND, LEHRER_ATTACK, LEHRER_DEFEND }

    public static Turn turn;
    public static boolean imKampf;
    public static int timer;
    public static int Zwischenschaden;
    public static int Endschaden;
    public static int LehrerLeben;
    public static int PlayerLeben;
    public static ItemType clicked;
    public static Department FACHSCHAFT;
    public static Set<Department> uebrigeLehrer = Arrays.stream(Department.values()).collect(Collectors.toCollection(HashSet::new));

    public static ItemObject currentItem;
    public static LehrerObject lehrerObject;
    public static KampfScene instance;

    public KampfScene(Department FACHSCHAFT){
        super();
        KampfScene.FACHSCHAFT = FACHSCHAFT;

        coverSettings.setRules(new Rules(Setting.KEEP, Setting.OFF, Setting.KEEP), "inventar");

        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Kampfgang.png")));

        instance = this;
        imKampf = true;
        turn = Turn.PLAYER_ATTACK;
        timer = 0;
        Zwischenschaden = 0;
        Endschaden = 0;
        LehrerLeben = 10;
        PlayerLeben = 10;
        currentItem = null;

        lehrerObject = new LehrerObject(700, 280, FACHSCHAFT);
        this.objects.add(lehrerObject);

        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 120;
        PlayerObject.INSTANCE.y = 295;

        objects.add(new PfeilObject(null, null, PfeilObject.UNTEN) {
            @Override
            public void update(BaseScene scene) {
               if(isClicked(false)) {
                   InventarScene.close();
                   SceneStack.INSTANCE.push(new KampfEndScene(false, false));
               }
            }
        });

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

        if(PlayerLeben <= 0 || (PlayerObject.INSTANCE.inventar.isEmpty() && turn == Turn.PLAYER_ATTACK)) {
            SceneStack.INSTANCE.push(new KampfEndScene(false, !PlayerObject.INSTANCE.inventar.isEmpty()));
        } else if(LehrerLeben <= 0) {
            uebrigeLehrer.remove(FACHSCHAFT);
            SceneStack.INSTANCE.push(new KampfEndScene(true, false));
        }
    }


    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        if(KampfScene.timer != 0) {
            g.setColor(Color.GRAY);
            g.setFont(Draw.FONT_INFO.deriveFont(30f));
            Draw.drawTextCentered(g, "%.2fs".formatted((KampfScene.timer * 1d) / FPS), WIDTH / 2, HEIGHT - 40, true);
        }
    }

    @Override
    public void unload() {
        super.unload();
        Sounds.HALLWAY_MUSIC.setPlaying(true);
        PlayerObject.INSTANCE.allowInventory = true;
        KampfScene.imKampf = false;
        KampfScene.PlayerLeben = 10;
        KampfScene.LehrerLeben = 10;
        instance = null;
    }
}


