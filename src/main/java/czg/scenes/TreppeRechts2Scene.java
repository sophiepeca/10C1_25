package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;
import czg.util.Images;

public class TreppeRechts2Scene extends BaseScene{
    public TreppeRechts2Scene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/treppeR2.png")));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, InfogangScene::new, PfeilObject.RECHTS));
        objects.add(new PfeilObject(this, MathegangScene::new, PfeilObject.LINKS));
        objects.add(new PfeilObject(this, TreppeRechts3Scene::new, PfeilObject.OBEN));
        objects.add(new PfeilObject(this, TreppeRechts1Scene::new, PfeilObject.UNTEN));
        
    }    
}
