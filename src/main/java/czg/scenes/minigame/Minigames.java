package czg.scenes.minigame;

import czg.objects.Department;
import czg.scenes.BaseScene;
import czg.scenes.SceneStack;

/**
 * Statische Methoden zum Starten von Minigames
 */
public class Minigames {

    /**
     * Soll nicht instantiiert werden
     */
    private Minigames() {}

    /**
     * Den Level Selector für ein Minigame generieren.
     * @param department Die Fachschaft des Minigames
     */
    public static LevelSelectorScene generateMinigame(Department department) {
        switch(department) {
            case COMPUTER_SCIENCE -> {
                return new LevelSelectorScene(
                    new ComputerScienceLevelScene(0),
                    new ComputerScienceLevelScene(1),
                    new ComputerScienceLevelScene(2)
                );
            } case MATHEMATICS -> {
                return new LevelSelectorScene(
                        new MathematicsLevelScene(0),
                        new MathematicsLevelScene(1),
                        new MathematicsLevelScene(2)
                );
            } case CHEMISTRY -> {
                return new LevelSelectorScene(
                    new ChemieLevelScene(0),
                    new ChemieLevelScene(1),
                    new ChemieLevelScene(2)
                );
            }
            case PHYSICS -> {
                return new LevelSelectorScene(
                    new PhysikLevelScene(0),
                    new PhysikLevelScene(1),
                    new PhysikLevelScene(2)
                );
            }
            case BIOLOGY -> {
                return new LevelSelectorScene(
                        new BiologyLevelScene(0),
                        new BiologyLevelScene(1),
                        new BiologyLevelScene(2)
                );
            }
            
            default -> throw new UnsupportedOperationException("Minigame für Fachschaft "+department+" noch nicht implementiert");
        }
    }

    /**
     * Setzt ein Level zurück und aktualisiert den entsprechenden Button
     * in der {@link LevelSelectorScene}. Startet das zurückgesetzte Level
     * ggf. auch gleich.
     * @param level Level-Nummer. Entweder {@code 0}, {@code 1} oder {@code 2}
     * @param autoRetry Ob das zurückgesetzte Level sofort wieder gestartet ({@link SceneStack#push(BaseScene)}) werden soll
     */
    static void resetMinigameLevel(int level, boolean autoRetry) {
        // MinigameEndScene entfernen
        SceneStack.INSTANCE.pop();
        // LevelScene entfernen
        SceneStack.INSTANCE.pop();

        // LevelSelectorScene holen
        LevelSelectorScene levelSelector = (LevelSelectorScene) SceneStack.INSTANCE.getTop();
        // Aktuelles Level zurücksetzen
        LevelScene resetLevel = levelSelector.levels[level].reset();
        // Button im Level Selector führt zu zurückgesetztem Level
        levelSelector.levels[level] = resetLevel;

        if(autoRetry) {
            // Zurückgesetztes Level anzeigen
            SceneStack.INSTANCE.push(resetLevel);
        }
    }
}
