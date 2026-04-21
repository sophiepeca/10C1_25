package czg.scenes;

import czg.objects.*;
import czg.scenes.minigame.Minigames;
import czg.util.Images;

public class FoyerScene extends BaseScene{
    public FoyerScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Foyer.png")));
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, GangHausmeisterScene::new, PfeilObject.RECHTS));
        objects.add(new PfeilObject(this, PhysikgangScene::new, PfeilObject.LINKS));
        objects.add(new PfeilObject(this, ErstesOGScene::new, PfeilObject.OBEN));

        // Für Kampf debugging
        objects.add(new ButtonObject(Images.get("/assets/minigames/general/button_menu.png"),
                () -> {
                    SceneStack.INSTANCE.push(new KampfScene("PHYSICS"));
                    SceneStack.INSTANCE.push(new InventarScene());
                    PlayerObject.INSTANCE.allowInventory = false;
                }));


        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 330;
        PlayerObject.INSTANCE.y = 295;
        
    }
}
