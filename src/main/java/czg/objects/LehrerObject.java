package czg.objects;

import czg.scenes.BaseScene;
import czg.objects.ItemObject;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class LehrerObject extends BaseObject{

    public final int LEVEL;
    public int hp;
    public final String FACHSCHAFT;
    public final List<ItemObject> lehrer_items;

    public LehrerObject(Image sprite, int x, int y, String FACHSCHAFT, int hp, int LEVEL, List<ItemObject> lehrer_items) {
        super(sprite, x, y);
        this.LEVEL = LEVEL;
        this.hp = hp;
        this.FACHSCHAFT = FACHSCHAFT;
        this.lehrer_items = lehrer_items;
    }

    public void verteidigung(int level) {
        // Es wird random ausgewählt, ob ein Item gewählt wird (und welches), oder ob der Lehrer nichts macht.
        Random rand = new Random();
        int move = rand.nextInt(5);
        int schaden;
        ItemObject item_lehrer;

        if (move == 0) {
            schaden = level;
            item_lehrer = null;
        }
        else {
            item_lehrer = lehrer_items.get(move - 1);
            schaden = level - item_lehrer.LEVEL;
            if (schaden <= 0) {
                schaden = 0;
            }

        }

        hp -= schaden;

    }
    
    public void angriff() {
        Random rand = new Random();
        int move = rand.nextInt(4);
        int level;
        ItemObject item_lehrer;
        
        item_lehrer = lehrer_items.get(move);
        level = item_lehrer.LEVEL;
        
        PlayerObject.INSTANCE.verteidigung(level);
    }

    @Override
    public void update(BaseScene scene) {
    }
}
