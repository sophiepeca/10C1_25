/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.Department;
import czg.scenes.minigame_scenes.MinigameScene;
import czg.util.Images;

import static czg.MainWindow.HEIGHT;
import czg.objects.InvisibleDoorObject;
import czg.objects.PfeilObject;
import czg.objects.PlayerObject;

/**
 *
 * @author guest-zmpzia
 */
public class InfogangScene extends BaseScene{
    public InfogangScene(){
        //Einfügen des Hintergrunds
        objects.add(new  BackdropObject(Images.get("/assets/background/Infogang.png")));
        
        //Einfügen des unsichtbaren Tür-Objektes auf Position der Tür
        objects.add(new InvisibleDoorObject(MainWindow.PIXEL_SCALE * 36, MainWindow.PIXEL_SCALE * 45,this, InforaumScene::new));
  
        //Pfeilobjekt für den Wechsel in die nebenliegende Szene
        objects.add(new PfeilObject(this, TreppeRechts2Scene::new, 2));
        
        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = PlayerObject.GetRandomX();
        PlayerObject.INSTANCE.y = 295;
        
    }
}
