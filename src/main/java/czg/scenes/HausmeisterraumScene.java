/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.util.Images;

import static czg.MainWindow.WIDTH;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;

/**
 *
 * @author guest-rwl69f
 */
public class HausmeisterraumScene extends BaseScene{
    public HausmeisterraumScene() {
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Hausmeisterraum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, GangHausmeisterScene::new, 4));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 230;
        PlayerObject.INSTANCE.y = 290;
        
    }
}