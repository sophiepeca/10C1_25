package czg.scenes;

import czg.objects.*;
import czg.scenes.minigame.Minigames;
import czg.util.Images;

import static czg.MainWindow.PIXEL_SCALE;

public class InforaumScene extends BaseScene{
    public InforaumScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Info-Raum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, InfogangScene::new, PfeilObject.UNTEN));

        LehrerObject.addButtonObject(this, Department.COMPUTER_SCIENCE, 195 * PIXEL_SCALE, 60 * PIXEL_SCALE);

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 200;
        PlayerObject.INSTANCE.y = 290;

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/button_play.png"), 170 * PIXEL_SCALE, 28 * PIXEL_SCALE,
                () -> SceneStack.INSTANCE.push(Minigames.generateMinigame(Department.COMPUTER_SCIENCE))));
    }
}
