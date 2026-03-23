/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.MainWindow;
import static czg.MainWindow.HEIGHT;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.InvisibleDoorObject;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;
import czg.util.Images;

/**
 *
 * @author guest-zmpzia
 */
public class ChemiegangScene extends BaseScene{
    public ChemiegangScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Chemiegang.png")));
        
        //Einfügen des unsichtbaren Tür-Objektes auf Position der Tür
        objects.add(new InvisibleDoorObject(MainWindow.PIXEL_SCALE * 144, MainWindow.PIXEL_SCALE * 45,this, ChemieraumScene::new));
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, ZweitesOGScene::new, 1));
        objects.add(new PfeilObject(this, TreppeLinks3Scene::new, 2));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
    }
}
