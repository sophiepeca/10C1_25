package czg.objects;

import czg.MainWindow;
import czg.scenes.BaseScene;
import czg.scenes.KampfScene;
import czg.util.Images;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LehrerObject extends BaseObject{

    public final String FACHSCHAFT;
    public static List<ItemType> lehrer_items;

    public LehrerObject(int x, int y, String FACHSCHAFT) {
        super(Images.get("/assets/characters/bre.png"), x, y);
        this.FACHSCHAFT = FACHSCHAFT;
        this.lehrer_items = Arrays.asList(ItemType.NEWTONSAPFEL, ItemType.ATOM, ItemType.CHROME, ItemType.BSOD);
    }

    public int verteidigung(int level) {
        // Es wird random ausgewählt, ob ein Item gewählt wird (und welches), oder ob der Lehrer nichts macht.
        Random rand = new Random();
        int move = rand.nextInt(5);
        int schaden;
        ItemType item_lehrer;

        // Falls die Null genommen wurde, wird kein Item vom Lehrer benutzt.
        if (move == 0) {
            schaden = level;
        }
        else {
            item_lehrer = lehrer_items.get(move - 1);
            schaden = level - item_lehrer.LEVEL;
            if (schaden <= 0) {
                schaden = 0;
            }

        }

        return schaden;

    }
    
    public int angriff() {
        // Der Lehrer wählt random, welches der Items er zum Angreifen benutzt.
        Random rand = new Random();
        int move = rand.nextInt(4);
        int level;
        ItemType item_lehrer;
        
        item_lehrer = lehrer_items.get(move);
        level = item_lehrer.LEVEL;
        
        return level;
    }
    
    public static Image getImage(Department fachschaft) { // ordnet den Fachschaften die Lehrer mit Bild zu
        if (fachschaft == Department.COMPUTER_SCIENCE) {
            
            return Images.get("assets/characters/bre.png");
                    
        } else if (fachschaft == Department.PHYSICS) {
             
            return Images.get("assets/characters/tno.png");
            
        } else if (fachschaft == Department.MATHEMATICS) {
             
            return Images.get("assets/characters/gei.png");  
            
        } else if (fachschaft == Department.BIOLOGY) {

            return Images.get("assets/characters/kho.png");
            
        } else if (fachschaft == Department.CHEMISTRY) {
            
            return Images.get("assets/characters/kko.png");
        }
        
        throw new IllegalArgumentException("Konnte der Fachschaft "+fachschaft+", kein Foto zuordnen!");
    }

    @Override
    public void update(BaseScene scene) {
        super.update(scene);

        if(KampfScene.lehrerVerteidigung) {
            KampfScene.Endschaden = verteidigung(KampfScene.Zwischenschaden);
            KampfScene.lehrerVerteidigung = false;
            KampfScene.lehrerTurn = true;
        }
        else {
            KampfScene.Zwischenschaden = angriff();
            KampfScene.timer = 10 * MainWindow.FPS;
            KampfScene.lehrerTurn = false;
            KampfScene.PlayerVerteidigung = true;
        }




    }
}
