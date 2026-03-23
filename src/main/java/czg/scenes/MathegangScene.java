/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.Department;
import czg.objects.InvisibleDoorObject;
import czg.scenes.minigame_scenes.MinigameScene;
import czg.util.Images;

import static czg.MainWindow.HEIGHT;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;

/**
 *
 * @author guest-zmpzia
 */
public class MathegangScene extends BaseScene{
    public MathegangScene(){
        //Einfügen des Hintergrunds
        objects.add(new  BackdropObject(Images.get("/assets/background/Mathegang.png")));
        
        //Einfügen des unsichtbaren Tür-Objektes auf Position der Tür
        objects.add(new InvisibleDoorObject(MainWindow.PIXEL_SCALE * 177, MainWindow.PIXEL_SCALE * 45,this, MatheraumScene::new));
        
        //Pfeilobjekte für den Wechsel in nebenliegende Szenen
        objects.add(new PfeilObject(this, TreppeRechts2Scene::new, 1));
        objects.add(new PfeilObject(this, ErstesOGScene::new, 2));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;

        MinigameScene mathematicsTest = MinigameScene.generateMinigame(Department.MATHEMATICS);

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/button_menu.png"), mathematicsTest::startMinigame));
    }
}
