package czg.scenes;

import czg.objects.*;
import czg.scenes.minigame.Minigames;
import czg.util.Images;

public class ChemieraumScene extends BaseScene{
    public ChemieraumScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Chemieraum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, ChemiegangScene::new, PfeilObject.UNTEN));

        objects.add(new ButtonObject(LehrerObject.getImage(Department.CHEMISTRY),
                () -> {
                    SceneStack.INSTANCE.push(new KampfScene(Department.COMPUTER_SCIENCE));
                    SceneStack.INSTANCE.push(new InventarScene(false));
                    PlayerObject.INSTANCE.allowInventory = false;
                }));

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 170;
        PlayerObject.INSTANCE.y = 290;

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/button_menu.png"),
                () -> SceneStack.INSTANCE.push(Minigames.generateMinigame(Department.CHEMISTRY))));
        }
}

