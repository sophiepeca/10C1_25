package czg.objects;

import czg.scenes.BaseScene;

import java.awt.*;

public class LehrerObject extends BaseObject{

    public final int level, hp;
    public final String fachschaft;

    public LehrerObject(Image sprite, int x, int y, String fachschaft, int hp, int level) {
        super(sprite, x, y);
        this.level = level;
        this.hp = hp;
        this.fachschaft = fachschaft;
    }

    @Override
    public void update(BaseScene scene) {

    }
}
