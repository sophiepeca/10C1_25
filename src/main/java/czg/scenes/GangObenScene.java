/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import static czg.MainWindow.HEIGHT;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;
import czg.util.Images;

/**
 *
 * @author guest-zmpzia
 */
public class GangObenScene extends BaseScene{
    public GangObenScene(){
        //Einfügen des Hintergrunds
        objects.add(new  BackdropObject(Images.get("/assets/background/GangOben.png")));
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, TreppeRechts3Scene::new, 1));
        objects.add(new PfeilObject(this, ZweitesOGScene::new, 2));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
    }
}
