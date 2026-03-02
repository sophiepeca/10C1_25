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
public class TreppeRechts2Scene extends BaseScene{
    public TreppeRechts2Scene(){
        objects.add(new BackdropObject(Images.get("/assets/background/treppeR2.png")));
        
        ButtonObject links = new ButtonObject(
                Images.get("/assets/background/PfeilLinks.png"),
                () -> {
                    MathegangScene mathe = new MathegangScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    mathe.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, mathe);
                });

        links.x = 9;
        links.y = (HEIGHT/2) - (links.height/2);
        objects.add(links);
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    InfogangScene info = new InfogangScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    info.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, info);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
        
        ButtonObject unten = new ButtonObject(
                Images.get("/assets/background/PfeilUnten.png"),
                () -> {
                    TreppeRechts1Scene tr1 = new TreppeRechts1Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    tr1.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tr1);
                });

        unten.x = (WIDTH/2) - (unten.width/2);
        unten.y = 440;
        objects.add(unten);
        
        ButtonObject oben = new ButtonObject(
                Images.get("/assets/background/PfeilOben.png"),
                () -> {
                    TreppeRechts3Scene tr3 = new TreppeRechts3Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    tr3.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tr3);
                });

        oben.x = (WIDTH/2) - (oben.width/2);
        oben.y = 4;
        objects.add(oben);
    }    
}
