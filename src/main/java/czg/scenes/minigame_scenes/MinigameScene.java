/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.scenes.minigame_scenes;

import czg.objects.ButtonObject;
import czg.objects.minigame_objects.MinigameNameObject;
import czg.scenes.BaseScene;
import czg.scenes.InfogangScene;
import czg.scenes.SceneStack;

/**
 * Ein Minigame Template bestehend aus einer {@code LevelSelectorScene}, welche die einzelnen Level verknüpft
 * und jeweils drei Leveln.
 */
public class MinigameScene extends BaseScene {
    final LevelSelectorScene levelSelector;
    final BaseScene level1;
    final BaseScene level2;
    final BaseScene level3;

    /**
     * Ein neues Minigame aus drei Leveln erstellen.
     * @param level1 Level 1
     * @param level2 Level 2
     * @param level3 Level 3
     */
    private MinigameScene(BaseScene level1, BaseScene level2, BaseScene level3) {
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;

        this.levelSelector = new LevelSelectorScene(level1, level2, level3);
    }

    /**
     * Ein Minigame starten.
     */
    public void startMinigame() {
        SceneStack.INSTANCE.push(levelSelector);
    }

    /**
     * Ein Minigame generieren.
     * @param minigameType Die Fachschaft des Minigames
     */
    public static MinigameScene generateMinigame(MinigameNameObject minigameType) {
        switch(minigameType) {
            case INFORMATICS -> {
                return new MinigameScene(
                    new InformaticsLevelScene(0),
                    new InformaticsLevelScene(1),
                    new InformaticsLevelScene(2)
                );
            } case MATHEMATICS -> {
                return new MinigameScene(
                        new MathematicsLevelScene(0),
                        new MathematicsLevelScene(1),
                        new MathematicsLevelScene(2)
                );
            } default -> {
                return null;
            }
        }
    }

    public static void resetMinigame(MinigameNameObject minigameType, BaseScene scene) {
        MinigameScene newMinigame = MinigameScene.generateMinigame(minigameType);

        if (scene.objects.getLast() instanceof ButtonObject) {
            ((ButtonObject) scene.objects.getLast()).method = newMinigame::startMinigame;
        }
    }

    protected static void resetAndStartMinigame(MinigameNameObject minigameType, BaseScene scene, int level) {
        MinigameScene newMinigame = MinigameScene.generateMinigame(minigameType);

        if (scene.objects.getLast() instanceof ButtonObject) {
            ((ButtonObject) scene.objects.getLast()).method = newMinigame::startMinigame;
            ((ButtonObject) scene.objects.getLast()).method.run();

            LevelSelectorScene levelSelectorScene = SceneStack.INSTANCE.getTop() instanceof LevelSelectorScene ? (LevelSelectorScene) SceneStack.INSTANCE.getTop() : null;

            if (levelSelectorScene.objects.getLast() instanceof ButtonObject &&
                    levelSelectorScene.objects.get(levelSelectorScene.objects.size()-2) instanceof ButtonObject &&
                    levelSelectorScene.objects.get(levelSelectorScene.objects.size()-3) instanceof ButtonObject
            ) {
                switch (level) {
                    case 0 ->
                            ((ButtonObject) levelSelectorScene.objects.get(levelSelectorScene.objects.size() - 3)).method.run();
                    case 1 ->
                            ((ButtonObject) levelSelectorScene.objects.get(levelSelectorScene.objects.size() - 2)).method.run();
                    case 2 ->
                            ((ButtonObject) levelSelectorScene.objects.getLast()).method.run();
                }
            }
        }
    }
}
