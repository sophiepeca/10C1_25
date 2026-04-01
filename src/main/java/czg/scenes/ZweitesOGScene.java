package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;
import czg.util.Images;

public class ZweitesOGScene extends BaseScene{
    public ZweitesOGScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/2_OG.png")));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, GangObenScene::new, PfeilObject.RECHTS));
        objects.add(new PfeilObject(this, ChemiegangScene::new, PfeilObject.LINKS));
        objects.add(new PfeilObject(this, ErstesOGScene::new, PfeilObject.UNTEN));
        
    }
}
