package czg.scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.ButtonObject;
import czg.objects.ExampleDoorObject;
import czg.scenes.minigame_scenes.InformaticsLevelScene;
import czg.scenes.minigame_scenes.MinigameScene;
import czg.util.Draw;
import czg.util.Images;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static czg.MainWindow.HEIGHT;
import static czg.MainWindow.WIDTH;

public class ExampleScene1 extends BaseScene {

    public ExampleScene1() {
        objects.add(new BackdropObject(new Color(130, 149, 163)));

        ExampleDoorObject door = new ExampleDoorObject(0, 0, ExampleScene2::new);

        door.x = WIDTH - Math.round(door.width * 1.5f);
        door.y = HEIGHT / 2 - door.height / 2;
        objects.add(door);

        MinigameScene informaticsTest = new MinigameScene(
            new InformaticsLevelScene(null, new ArrayList<>(Arrays.asList("and_gate", "or_gate", "nand_gate", "not_gate"))),
            new InformaticsLevelScene(null, new ArrayList<>(Arrays.asList("or_gate", "not_gate", "xnor_gate", "not_gate"))),
            new InformaticsLevelScene(null, new ArrayList<>(Arrays.asList("nand_gate", "xor_gate", "nand_gate", "not_gate")))
        );

        ButtonObject startMinigameTest = new ButtonObject(Images.get("/assets/minigames/informatics/button.png"), 10, 10, informaticsTest::startMinigame);
        objects.add(startMinigameTest);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        g.setFont(Draw.FONT_TITLE);
        g.setColor(Color.BLACK);
        Draw.drawTextCentered(g, "Szene 1", MainWindow.WIDTH / 2, 60);
    }
}
