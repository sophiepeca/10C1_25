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
public class InfogangScene extends BaseScene{
    public InfogangScene(){
        objects.add(new  BackdropObject(Images.get("/assets/background/Infogang.png")));
        
        ButtonObject links = new ButtonObject(
                Images.get("/assets/background/PfeilLinks.png"),
                () -> {
                    TreppeRechts2Scene tr2 = new TreppeRechts2Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    tr2.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tr2);
                });

        links.x = 9;
        links.y = (HEIGHT/2) - (links.height/2);
        objects.add(links);
    }
}
