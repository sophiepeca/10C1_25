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
 * @author guest-zmpzia
 */
public class GangObenScene extends BaseScene{
    public GangObenScene(){
        objects.add(new  BackdropObject(Images.get("/assets/background/GangOben.png")));
        
        ButtonObject links = new ButtonObject(
                Images.get("/assets/background/PfeilLinks.png"),
                () -> {
                    zweitesOGScene zweites = new zweitesOGScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    zweites.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, zweites);
                });

        links.x = 9;
        links.y = (HEIGHT/2) - (links.height/2);
        objects.add(links);
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    TreppeRechts3Scene tr3 = new TreppeRechts3Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    tr3.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tr3);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
    }
}
