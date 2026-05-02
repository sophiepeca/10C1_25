
package czg.objects;

import czg.scenes.BaseScene;
import czg.scenes.SceneStack;
import czg.util.Images;

import java.awt.*;
import java.util.function.Supplier;

import static czg.MainWindow.*;

public class PfeilObject extends BaseObject{
    
    public static final int RECHTS = 1, LINKS = 2, OBEN = 3, UNTEN = 4;
    
    //Initialisieren eines Ziels (Wohin soll der Pfeil führen?)
    public final Supplier<BaseScene> target;
    //Initialisieren eines Ursprungs
    public final BaseScene origin;
    
    
    public PfeilObject(BaseScene origin, Supplier<BaseScene> target, int richtung){
        //Festlegen, welches Bild genutzt wird (siehe weiter unten)
        super(getSprite(richtung));
        
        //Festlegen der Koordinaten des Objektes abhängig davon, welche Richtung angegeben wird
        switch(richtung){
            case RECHTS:
                x = 880;
                y = (HEIGHT/2) - 60;
                break;
            case LINKS:
                x = 9;
                y = (HEIGHT/2) - 60;
                break;
            case OBEN:
                x = (WIDTH/2) - 40;
                y = 4;
                break;
            case UNTEN:
                x = (WIDTH/2) - 40;
                y = 452;
                break;
        }
          
        this.target = target;
        this.origin = origin;
        
        
    }
    
    /*
    Funktion für die Bilder, je nachdem, welche Richtung beim PfeilObject 
    angegeben wird, wird ein anderes Bild für das PfeilObject genutzt
    */
    public static Image getSprite(int richtung) {
        return switch (richtung) {
            case RECHTS -> Images.get("/assets/background/PfeilRechts.png");
            case LINKS -> Images.get("/assets/background/PfeilLinks.png");
            case OBEN -> Images.get("/assets/background/PfeilOben.png");
            case UNTEN -> Images.get("/assets/background/PfeilUnten.png");
            default -> null;
        };
    }
    
    @Override
    public void update(BaseScene scene) {
        if(isClicked(false)){
            BaseScene neu = target.get();
            SceneStack.INSTANCE.replace(origin, neu);
            PlayerObject.INSTANCE.inventoryLockTimer = FPS / 4;
        }
    }
}
