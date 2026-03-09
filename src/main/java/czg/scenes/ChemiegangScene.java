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
public class ChemiegangScene extends BaseScene{
    public ChemiegangScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Chemiegang.png")));
        
        //Buttons zum Klicken in andere Gangszenen
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    ZweitesOGScene zweites = new ZweitesOGScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    zweites.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, zweites);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
        
        ButtonObject links = new ButtonObject(
                Images.get("/assets/background/PfeilLinks.png"),
                () -> {
                    TreppeLinks3Scene tl3 = new TreppeLinks3Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    tl2.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tl3);
                });

        links.x = 9;
        links.y = (HEIGHT/2) - (links.height/2);
        objects.add(links);
    }
}
