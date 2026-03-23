/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.util.Images;

import static czg.MainWindow.HEIGHT;
import static czg.MainWindow.WIDTH;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;

/**
 *
 * @author guest-x4t0rl
 */
public class TreppeLinks2Scene extends BaseScene{
    public TreppeLinks2Scene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/treppeL2.png")));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, BiogangScene::new, 1));
        objects.add(new PfeilObject(this, TreppeLinks3Scene::new, 3));
        objects.add(new PfeilObject(this, TreppeLinks1Scene::new, 4));
        
    }    
}
