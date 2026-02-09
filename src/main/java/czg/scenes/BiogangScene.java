/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.MainWindow;
import static czg.MainWindow.HEIGHT;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.ExamplePlayerObject;
import czg.util.Draw;
import czg.util.Images;
import java.awt.*;

/**
 *
 * @author guest-nie2d3
 */
public class BiogangScene extends BaseScene{
    public BiogangScene(){
        objects.add(new BackdropObject(Images.get("/assets/background/Biogang.png")));
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    MathegangScene mathe = new MathegangScene();
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    mathe.objects.add(ExamplePlayerObject.INSTANCE);
                    SceneStack.INSTANCE.replace(this, mathe);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
        
    }

}
