/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.InvisibleDoorObject;
import czg.util.Images;

import static czg.MainWindow.HEIGHT;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;

/**
 *
 * @author guest-nie2d3
 */
public class BiogangScene extends BaseScene{
    public BiogangScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Biogang.png")));
        
        //Einfügen des unsichtbaren Tür-Objektes auf Position der Tür
        objects.add(new InvisibleDoorObject(656, 180,this, BioraumScene::new));
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, ErstesOGScene::new, 1));
        objects.add(new PfeilObject(this, TreppeLinks2Scene::new, 2));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
    }

}
