/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import static czg.MainWindow.HEIGHT;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.util.Images;

/**
 *
 * @author guest-ku1dtt
 */
public class TreppeLinksScene extends BaseScene{
    public TreppeLinksScene(){
        objects.add(new BackdropObject(Images.get("/assets/background/kleines_treppenhaus.png")));
        
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
                    TreppeLinksScene treppel = new TreppeLinksScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    treppel.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, treppel);
                });

        unten.x = 300;
        unten.y = 300;
        objects.add(unten);
        
    }
}
