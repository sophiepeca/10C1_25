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
 * @author guest-fqz0q0
 */
public class ErstesOGScene extends BaseScene{
    public ErstesOGScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/1_OG.png")));
         
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 290;
        PlayerObject.INSTANCE.y = 295;
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, MathegangScene::new, 1));
        objects.add(new PfeilObject(this, BiogangScene::new, 2));
        objects.add(new PfeilObject(this, ZweitesOGScene::new, 3));
        objects.add(new PfeilObject(this, FoyerScene::new, 4));
       
    }
}
