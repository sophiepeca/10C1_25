package czg.scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.InvisibleDoorObject;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;
import czg.util.Images;

public class InfogangScene extends BaseScene{
    public InfogangScene(){
        //Einfügen des Hintergrunds
        objects.add(new  BackdropObject(Images.get("/assets/background/Infogang.png")));
        
        //Einfügen des unsichtbaren Tür-Objektes auf Position der Tür
        objects.add(new InvisibleDoorObject(MainWindow.PIXEL_SCALE * 36, MainWindow.PIXEL_SCALE * 45,this, InforaumScene::new));
  
        //Pfeilobjekt für den Wechsel in die nebenliegende Szene
        objects.add(new PfeilObject(this, TreppeRechts2Scene::new, PfeilObject.LINKS));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
    }
}
