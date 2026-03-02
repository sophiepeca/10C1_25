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
public class TreppeLinks1Scene extends BaseScene{
    public TreppeLinks1Scene(){
        objects.add(new BackdropObject(Images.get("/assets/background/treppeL1.png")));
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    PhysikgangScene füüüü = new PhysikgangScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    bio.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, füüüü);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
        
        ButtonObject oben = new ButtonObject(
                Images.get("/assets/background/PfeilOben.png"),
                () -> {
                    TreppeLinks2Scene tl2 = new TreppeLinks2Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    erstes.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tl2);
                });

        oben.x = (WIDTH/2) - (oben.width/2);
        oben.y = 4;
        objects.add(oben);
    }    
}
