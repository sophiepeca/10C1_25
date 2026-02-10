/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package czg.scenes;

import static czg.MainWindow.HEIGHT;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.util.Images;

/**
 *
 * @author user
 */
public class PhysikgangScene extends BaseScene{
    public PhysikgangScene(){
        objects.add(new BackdropObject(Images.get("/assets/background/Physikgang.png")));
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    FoyerScene foyer = new FoyerScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    foyer.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, foyer);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
    }
}
