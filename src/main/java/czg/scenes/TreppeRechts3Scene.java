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
public class TreppeRechts3Scene extends BaseScene{
    public TreppeRechts3Scene(){
        objects.add(new BackdropObject(Images.get("/assets/background/treppeR3.png")));
        
        ButtonObject links = new ButtonObject(
                Images.get("/assets/background/PfeilLinks.png"),
                () -> {
                    GangObenScene gango = new GangObenScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    gango.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, gango);
                });

        links.x = 9;
        links.y = (HEIGHT/2) - (links.height/2);
        objects.add(links);
        
        ButtonObject unten = new ButtonObject(
                Images.get("/assets/background/PfeilUnten.png"),
                () -> {
                    TreppeRechts2Scene tr2 = new TreppeRechts2Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    tr2.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tr2);
                });

        unten.x = (WIDTH/2) - (unten.width/2);
        unten.y = 440;
        objects.add(unten);
    }    
}
