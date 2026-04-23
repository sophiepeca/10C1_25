package czg.scenes;

import czg.objects.*;
import czg.scenes.minigame.Minigames;
import czg.util.Images;

import static czg.MainWindow.PIXEL_SCALE;

public class PhysikraumScene extends BaseScene{
    public PhysikraumScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Physik-Raum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, PhysikgangScene::new, PfeilObject.UNTEN));

        LehrerObject.addButtonObject(this, Department.PHYSICS, 140 * PIXEL_SCALE, 60 * PIXEL_SCALE);

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 210;
        PlayerObject.INSTANCE.y = 290;

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/button_play.png"), 172 * PIXEL_SCALE, 28  * PIXEL_SCALE,
                () -> SceneStack.INSTANCE.push(Minigames.generateMinigame(Department.PHYSICS))));
    }
}
