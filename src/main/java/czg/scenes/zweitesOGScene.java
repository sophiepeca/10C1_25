/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import static czg.MainWindow.HEIGHT;
import static czg.MainWindow.WIDTH;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.util.Images;

/**
 *
 * @author guest-ku1dtt
 */
public class zweitesOGScene extends BaseScene{
    public zweitesOGScene(){
        objects.add(new BackdropObject(Images.get("/assets/background/2_OG.png")));
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    GangObenScene oben = new GangObenScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    oben.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, oben);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
        
        ButtonObject links = new ButtonObject(
                Images.get("/assets/background/PfeilLinks.png"),
                () -> {
                    ChemiegangScene chemie = new ChemiegangScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    chemie.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, chemie);
                });

        links.x = 9;
        links.y = (HEIGHT/2) - (links.height/2);
        objects.add(links);
        
        ButtonObject unten = new ButtonObject(
                Images.get("/assets/background/PfeilUnten.png"),
                () -> {
                   erstesOGScene erstes = new erstesOGScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    erstes.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, erstes);
                });

        unten.x = (WIDTH/2) - (unten.width/2);
        unten.y = 440;
        objects.add(unten);
    }
}
