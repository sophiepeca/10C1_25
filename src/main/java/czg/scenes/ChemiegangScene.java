package czg.scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.InvisibleDoorObject;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;
import czg.util.Images;

public class ChemiegangScene extends BaseScene{
    public ChemiegangScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Chemiegang.png")));
        
        //Einfügen des unsichtbaren Tür-Objektes auf Position der Tür
        objects.add(new InvisibleDoorObject(MainWindow.PIXEL_SCALE * 144, MainWindow.PIXEL_SCALE * 45,this, ChemieraumScene::new));
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, ZweitesOGScene::new, PfeilObject.RECHTS));
        objects.add(new PfeilObject(this, TreppeLinks3Scene::new, PfeilObject.LINKS));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
    }
}
