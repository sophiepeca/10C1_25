package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;
import czg.util.Images;

public class ErstesOGScene extends BaseScene{
    public ErstesOGScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/1_OG.png")));
         
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 290;
        PlayerObject.INSTANCE.y = 295;
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, MathegangScene::new, PfeilObject.RECHTS));
        objects.add(new PfeilObject(this, BiogangScene::new, PfeilObject.LINKS));
        objects.add(new PfeilObject(this, ZweitesOGScene::new, PfeilObject.OBEN));
        objects.add(new PfeilObject(this, FoyerScene::new, PfeilObject.UNTEN));
       
    }
}
