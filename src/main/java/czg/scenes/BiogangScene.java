package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.InvisibleDoorObject;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;
import czg.util.Images;

public class BiogangScene extends BaseScene{
    public BiogangScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Biogang.png")));
        
        //Einfügen des unsichtbaren Tür-Objektes auf Position der Tür
        objects.add(new InvisibleDoorObject(656, 180,this, BioraumScene::new));
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, ErstesOGScene::new, PfeilObject.RECHTS));
        objects.add(new PfeilObject(this, TreppeLinks2Scene::new, PfeilObject.LINKS));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
    }

}
