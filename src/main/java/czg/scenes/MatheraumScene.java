package czg.scenes;

import czg.objects.*;
import czg.scenes.minigame.Minigames;
import czg.util.Images;

import static czg.MainWindow.PIXEL_SCALE;

public class MatheraumScene extends BaseScene{
    public MatheraumScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Matheraum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, MathegangScene::new, PfeilObject.UNTEN));

        LehrerObject.addButtonObject(this, Department.MATHEMATICS, 140 * PIXEL_SCALE, 70 * PIXEL_SCALE);

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/button_play.png"), 175 * PIXEL_SCALE, 65 * PIXEL_SCALE,
                () -> SceneStack.INSTANCE.push(Minigames.generateMinigame(Department.MATHEMATICS))));

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 270;
        PlayerObject.INSTANCE.y = 295;
    }
}