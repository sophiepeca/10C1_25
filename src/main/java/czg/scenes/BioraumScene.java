package czg.scenes;

import czg.objects.*;
import czg.scenes.minigame.Minigames;
import czg.util.Images;

/**
 * @author Jonas648
 */
public class BioraumScene extends BaseScene{
    public BioraumScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Bio-Raum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, BiogangScene::new, PfeilObject.UNTEN));

        objects.add(new ButtonObject(LehrerObject.getImage(Department.BIOLOGY),
                () -> {
                    SceneStack.INSTANCE.push(new KampfScene(Department.COMPUTER_SCIENCE));
                    SceneStack.INSTANCE.push(new InventarScene(false));
                    PlayerObject.INSTANCE.allowInventory = false;
                }));

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 150;
        PlayerObject.INSTANCE.y = 290;

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/button_menu.png"),
                () -> SceneStack.INSTANCE.push(Minigames.generateMinigame(Department.BIOLOGY))));
    }
}
