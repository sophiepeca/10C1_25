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
 * @author guest-x4t0rl
 */
public class TreppeLinks2Scene extends BaseScene{
    public TreppeLinks2Scene(){
        objects.add(new BackdropObject(Images.get("/assets/background/treppeL2.png")));
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    BiogangScene bio = new BiogangScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    bio.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, bio);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
        
        ButtonObject unten = new ButtonObject(
                Images.get("/assets/background/PfeilUnten.png"),
                () -> {
                    TreppeLinks1Scene tl1 = new TreppeLinks1Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    treppel.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tl1);
                });

        unten.x = (WIDTH/2) - (unten.width/2);
        unten.y = 440;
        objects.add(unten);
        
        ButtonObject oben = new ButtonObject(
                Images.get("/assets/background/PfeilOben.png"),
                () -> {
                    TreppeLinks3Scene tl3 = new TreppeLinks3Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    tl3.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tl3);
                });

        oben.x = (WIDTH/2) - (oben.width/2);
        oben.y = 4;
        objects.add(oben);
    }    
}
