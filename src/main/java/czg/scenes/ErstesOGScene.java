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
public class ErstesOGScene extends BaseScene{
    public ErstesOGScene(){
        objects.add(new BackdropObject(Images.get("/assets/background/1_OG.png")));
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    MathegangScene mathe = new MathegangScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    mathe.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, mathe);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
        
        ButtonObject links = new ButtonObject(
                Images.get("/assets/background/PfeilLinks.png"),
                () -> {
                    BiogangScene bio = new BiogangScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    mathe.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, bio);
                });

        links.x = 9;
        links.y = (HEIGHT/2) - (links.height/2);
        objects.add(links);
        
        ButtonObject unten = new ButtonObject(
                Images.get("/assets/background/PfeilUnten.png"),
                () -> {
                   FoyerScene foyer = new FoyerScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    foyer.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, foyer);
                });

        unten.x = (WIDTH/2) - (unten.width/2);
        unten.y = 440;
        objects.add(unten);
        
        ButtonObject oben = new ButtonObject(
                Images.get("/assets/background/PfeilOben.png"),
                () -> {
                   ZweitesOGScene zweites = new ZweitesOGScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    zweites.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, zweites);
                });

        oben.x = (WIDTH/2) - (oben.width/2);
        oben.y = 4;
        objects.add(oben);
    }
}
