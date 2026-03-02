package czg.objects;

import czg.scenes.BaseScene;
import czg.scenes.InventarScene;
import czg.scenes.SceneStack;
import czg.util.Images;

import java.awt.*;

/**
 *
 * @author guest-0wphjm
 */
public class PlayerObject extends BaseObject{

    // Festlegen der benötigten Variablen (Lebenspunkte, veränderliche Charaktereigenschaften)
    public String name;
    public int[] inventar;
    
    public static final PlayerObject INSTANCE = new PlayerObject();


    public PlayerObject() {
        super(Images.get("/assets/characters/PlayerBase.png"));
    }
    
    public PlayerObject(Image sprite, int x, int y, String name, int[] inventar){
          super (sprite, x, y);
          this.name = name;
          this.inventar = inventar;
    }

    @Override
    public void update(BaseScene scene) {
        if(isClicked())
            SceneStack.INSTANCE.push(new InventarScene());
    }
    
}
