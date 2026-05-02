package czg.scenes.minigame;

import czg.objects.BackdropObject;
import czg.objects.Department;
import czg.objects.minigame.PhysikGameObject;
import czg.util.Images;

/**
 * Die LevelScene für das Physik-Minigame.
 * <p>
 * Wird von LevelSelectorScene gestartet, genau wie ChemieLevelScene.
 * Enthält nur den Hintergrund und das PhysikGameObject –
 * Buttons und Gewinn-/Verlier-Logik werden von LevelScene übernommen.
 */
public class PhysikLevelScene extends LevelScene {
    /**
     * Erstellt eine neue Physik-LevelScene.
     *
     * @param level Das zu spielende Level (0, 1 oder 2)
     */
    public PhysikLevelScene(int level) {
        super(Department.PHYSICS, level);
        // Spielobjekt hinzufügen, enthältSpiellogik
        objects.add(new PhysikGameObject(level, this));
    }

    /**
     * Setzt das Level zurück, indem eine neue PhysikLevelScene erstellt wird.
     * Wird von Minigames.resetMinigameLevel() aufgerufen.
     */
    @Override
    public LevelScene reset() {
        return new PhysikLevelScene(LEVEL);
    }
}
