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
public class TreppeRechts1Scene extends BaseScene{
    public TreppeRechts1Scene(){
        objects.add(new BackdropObject(Images.get("/assets/background/treppeR1.png")));
        
        ButtonObject links = new ButtonObject(
                Images.get("/assets/background/PfeilLinks.png"),
                () -> {
                    GangHausmeisterScene hausm = new GangHausmeisterScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    hausm.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, hausm);
                });

        links.x = 9;
        links.y = (HEIGHT/2) - (links.height/2);
        objects.add(links);
        
        ButtonObject oben = new ButtonObject(
                Images.get("/assets/background/PfeilOben.png"),
                () -> {
                    TreppeRechts2Scene tr2 = new TreppeRechts2Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    tr2.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tr2);
                });

        oben.x = (WIDTH/2) - (oben.width/2);
        oben.y = 4;
        objects.add(oben);
    }    
}
