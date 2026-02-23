/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes;

import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
<<<<<<< HEAD
import czg.objects.minigame_objects.InfoPuzzleObject;
import czg.objects.minigame_objects.LogicGateObject;
=======
>>>>>>> ashydashy/main
import czg.scenes.minigame_scenes.InformaticsLevelScene;
import czg.scenes.minigame_scenes.MinigameScene;
import czg.util.Images;

<<<<<<< HEAD
import java.util.List;
=======
import java.util.ArrayList;
import java.util.Arrays;
>>>>>>> ashydashy/main

/**
 *
 * @author guest-zmpzia
 */
public class InfogangScene extends BaseScene{
    public InfogangScene(){
<<<<<<< HEAD
        objects.add(new BackdropObject(Images.get("/assets/background/Infogang.png")));

        MinigameScene informaticsTest = new MinigameScene(
                new InformaticsLevelScene(InfoPuzzleObject.getPuzzle(0)),
                new InformaticsLevelScene(InfoPuzzleObject.getPuzzle(0)),
                new InformaticsLevelScene(InfoPuzzleObject.getPuzzle(0))
        );

        objects.add(new ButtonObject(Images.get("/assets/minigames/general/menu_button.png"), informaticsTest::startMinigame));
=======
        objects.add(new  BackdropObject(Images.get("/assets/background/Infogang.png")));

        MinigameScene informaticsTest = new MinigameScene(
                new InformaticsLevelScene(null, new ArrayList<>(Arrays.asList("and_gate", "or_gate", "nand_gate", "not_gate"))),
                new InformaticsLevelScene(null, new ArrayList<>(Arrays.asList("or_gate", "not_gate", "xnor_gate", "not_gate"))),
                new InformaticsLevelScene(null, new ArrayList<>(Arrays.asList("nand_gate", "xor_gate", "nand_gate", "not_gate")))
        );

        ButtonObject startMinigameTest = new ButtonObject(Images.get("/assets/minigames/informatics/button.png"), 10, 10, informaticsTest::startMinigame);
        objects.add(startMinigameTest);
>>>>>>> ashydashy/main
    }
}
