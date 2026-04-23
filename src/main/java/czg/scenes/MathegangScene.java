package czg.scenes;

import czg.MainWindow;
import czg.objects.*;
import czg.scenes.minigame.Minigames;
import czg.util.Images;

public class MathegangScene extends BaseScene{
    public MathegangScene(){
        //Einfügen des Hintergrunds
        objects.add(new  BackdropObject(Images.get("/assets/background/Mathegang.png")));
        
        //Einfügen des unsichtbaren Tür-Objektes auf Position der Tür
        objects.add(new InvisibleDoorObject(MainWindow.PIXEL_SCALE * 177, MainWindow.PIXEL_SCALE * 45,this, MatheraumScene::new));
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, TreppeRechts2Scene::new, PfeilObject.RECHTS));
        objects.add(new PfeilObject(this, ErstesOGScene::new, PfeilObject.LINKS));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
    }
}
