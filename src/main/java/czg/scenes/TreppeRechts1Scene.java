/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import static czg.MainWindow.HEIGHT;
import static czg.MainWindow.WIDTH;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.PfeilObject;
import czg.util.Images;

/**
 *
 * @author guest-19szge
 */
public class TreppeRechts1Scene extends BaseScene{
    public TreppeRechts1Scene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/treppeR1.png")));
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, GangHausmeisterScene::new, 2));
        objects.add(new PfeilObject(this, TreppeRechts2Scene::new, 3));
        
    }    
}
