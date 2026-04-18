package czg.scenes.minigame;
import czg.objects.Department;
import czg.objects.minigame.ChemieGameObject;

public class ChemieLevelScene extends LevelScene {
    /**
     * @param level Das zu spielende Level (1–3)
     */
    public ChemieLevelScene(int level) {
        super(Department.CHEMISTRY,level);
        objects.add(new ChemieGameObject(level, this));
    }
    
    @Override
    public LevelScene reset() {
        return new ChemieLevelScene(LEVEL);
    }
}