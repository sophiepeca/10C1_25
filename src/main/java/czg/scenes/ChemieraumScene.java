package czg.scenes;

import czg.objects.*;
import czg.scenes.minigame.Minigames;
import czg.util.Images;

import static czg.MainWindow.PIXEL_SCALE;

public class ChemieraumScene extends BaseScene{
    public ChemieraumScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Chemieraum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, ChemiegangScene::new, PfeilObject.UNTEN));

        LehrerObject.addButtonObject(this, Department.CHEMISTRY, 120 * PIXEL_SCALE, 70 * PIXEL_SCALE);

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 170;
        PlayerObject.INSTANCE.y = 330;

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/button_play.png"), 155 * PIXEL_SCALE, 69 * PIXEL_SCALE,
                () -> SceneStack.INSTANCE.push(Minigames.generateMinigame(Department.CHEMISTRY))));
        }
}

