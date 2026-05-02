package czg.scenes;

import czg.objects.*;
import czg.scenes.minigame.Minigames;
import czg.util.Images;

import static czg.MainWindow.PIXEL_SCALE;

/**
 * @author Jonas648
 */
public class BioraumScene extends BaseScene{
    public BioraumScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Bio-Raum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, BiogangScene::new, PfeilObject.UNTEN));

        LehrerObject.addButtonObject(this, Department.BIOLOGY, 195 * PIXEL_SCALE, 60 * PIXEL_SCALE);

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 150;
        PlayerObject.INSTANCE.y = 290;

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/button_play.png"), 165 * PIXEL_SCALE, 28 * PIXEL_SCALE,
                () -> SceneStack.INSTANCE.push(Minigames.generateMinigame(Department.BIOLOGY))));
    }
}
