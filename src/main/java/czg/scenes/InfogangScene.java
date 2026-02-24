/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.minigame_objects.InfoPuzzleObject;
import czg.objects.minigame_objects.LogicGateObject;
import czg.scenes.minigame_scenes.InformaticsLevelScene;
import czg.scenes.minigame_scenes.MinigameScene;
import czg.util.Images;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author guest-zmpzia
 */
public class InfogangScene extends BaseScene{
    public InfogangScene(){
        objects.add(new BackdropObject(Images.get("/assets/background/Infogang.png")));

        MinigameScene informaticsTest = new MinigameScene(
                new InformaticsLevelScene(InfoPuzzleObject.getPuzzle(0)),
                new InformaticsLevelScene(InfoPuzzleObject.getPuzzle(0)),
                new InformaticsLevelScene(InfoPuzzleObject.getPuzzle(0))
        );

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/menu_button.png"), informaticsTest::startMinigame));
    }
}
