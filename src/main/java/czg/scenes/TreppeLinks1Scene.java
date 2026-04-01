package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;
import czg.util.Images;

public class TreppeLinks1Scene extends BaseScene{
    public TreppeLinks1Scene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/treppeL1.png")));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, PhysikgangScene::new, PfeilObject.RECHTS));
        objects.add(new PfeilObject(this, TreppeLinks2Scene::new, PfeilObject.OBEN));
        
    }    
}
