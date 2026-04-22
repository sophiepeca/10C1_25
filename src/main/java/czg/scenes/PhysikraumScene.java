package czg.scenes;

import czg.objects.*;
import czg.scenes.minigame.Minigames;
import czg.util.Images;

public class PhysikraumScene extends BaseScene{
    public PhysikraumScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Physik-Raum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, PhysikgangScene::new, PfeilObject.UNTEN));

        objects.add(new ButtonObject(LehrerObject.getImage(Department.PHYSICS),
                () -> {
                    SceneStack.INSTANCE.push(new KampfScene(Department.COMPUTER_SCIENCE));
                    SceneStack.INSTANCE.push(new InventarScene(false));
                    PlayerObject.INSTANCE.allowInventory = false;
                }));

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 210;
        PlayerObject.INSTANCE.y = 290;

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/button_menu.png"),
                () -> SceneStack.INSTANCE.push(Minigames.generateMinigame(Department.PHYSICS))));
    }
}
