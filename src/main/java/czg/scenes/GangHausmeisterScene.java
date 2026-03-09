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
public class GangHausmeisterScene extends BaseScene{
    public GangHausmeisterScene(){
        //Einfügen des Hintergrunds
        objects.add(new  BackdropObject(Images.get("/assets/background/GangHausmeister.png")));
        
        //Buttons zum Klicken in andere Gangszenen
        ButtonObject links = new ButtonObject(
                Images.get("/assets/background/PfeilLinks.png"),
                () -> {
                    FoyerScene foyer = new FoyerScene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    foyer.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, foyer);
                });

        links.x = 9;
        links.y = (HEIGHT/2) - (links.height/2);
        objects.add(links);
        
        ButtonObject rechts = new ButtonObject(
                Images.get("/assets/background/PfeilRechts.png"),
                () -> {
                    TreppeRechts1Scene tr1 = new TreppeRechts1Scene();
                    /*
                    this.objects.remove(ExamplePlayerObject.INSTANCE);
                    tr1.objects.add(ExamplePlayerObject.INSTANCE);
                    */
                    SceneStack.INSTANCE.replace(this, tr1);
                });

        rechts.x = 880;
        rechts.y = (HEIGHT/2) - (rechts.height/2);
        objects.add(rechts);
    }
}
