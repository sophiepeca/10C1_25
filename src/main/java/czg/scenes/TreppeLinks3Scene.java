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
 * @author guest-19szge
 */
public class TreppeLinks3Scene extends BaseScene{
    public TreppeLinks3Scene(){
        objects.add(new BackdropObject(Images.get("/assets/background/treppeL3.png")));
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    ChemiegangScene chemie = new ChemiegangScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    chemie.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, chemie);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
        
        ButtonObject unten = new ButtonObject(
                Images.get("/assets/background/PfeilUnten.png"),
                () -> {
                    TreppeLinks2Scene tl2 = new TreppeLinks2Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    treppel.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tl2);
                });

        unten.x = (WIDTH/2) - (unten.width/2);
        unten.y = 440;
        objects.add(unten);
    }    
}
