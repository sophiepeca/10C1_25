package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;
import czg.util.Images;

public class FoyerScene extends BaseScene{
    public FoyerScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Foyer.png")));
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, GangHausmeisterScene::new, PfeilObject.RECHTS));
        objects.add(new PfeilObject(this, PhysikgangScene::new, PfeilObject.LINKS));
        objects.add(new PfeilObject(this, ErstesOGScene::new, PfeilObject.OBEN));

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 330;
        PlayerObject.INSTANCE.y = 295;
        
    }
}
