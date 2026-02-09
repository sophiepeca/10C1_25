/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes.minigame_scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.scenes.*;
import czg.util.Images;

import java.awt.*;

/**
 *
 * @author guest-kaafgt
 */
public class LevelSelectorScene extends BaseScene {
    public LevelSelectorScene(BaseScene level1, BaseScene level2, BaseScene level3) {
        objects.add(new BackdropObject(Images.get("/assets/minigames/general/level_selector_background.png")));

        ButtonObject buttonLevel1 = new ButtonObject(Images.get("/assets/minigames/general/button_level_1.png"), () -> SceneStack.INSTANCE.push(level1));
        ButtonObject buttonLevel2 = new ButtonObject(Images.get("/assets/minigames/general/button_level_2.png"), () -> SceneStack.INSTANCE.push(level2));
        ButtonObject buttonLevel3 = new ButtonObject(Images.get("/assets/minigames/general/button_level_3.png"), () -> SceneStack.INSTANCE.push(level3));
        ButtonObject buttonExit = new ButtonObject(Images.get("/assets/minigames/informatics/button.png"), SceneStack.INSTANCE::pop);

        buttonLevel1.x = MainWindow.WIDTH / 2 - buttonLevel1.width / 2;
        buttonLevel1.y = (int) (MainWindow.HEIGHT * 0.35);

        buttonLevel2.x = MainWindow.WIDTH / 2 - buttonLevel2.width / 2;
        buttonLevel2.y = (int) (MainWindow.HEIGHT * 0.55);

        buttonLevel3.x = MainWindow.WIDTH / 2 - buttonLevel3.width / 2;
        buttonLevel3.y = (int) (MainWindow.HEIGHT * 0.75);

        buttonExit.x = (int) (MainWindow.WIDTH - buttonExit.width * 1.5);
        buttonExit.y = (int) (buttonExit.height * 0.2);

        objects.add(buttonLevel1);
        objects.add(buttonLevel2);
        objects.add(buttonLevel3);
        objects.add(buttonExit);
    }
}