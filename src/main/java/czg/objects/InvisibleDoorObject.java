package czg.objects;

import czg.scenes.BaseScene;
import czg.scenes.SceneStack;

import java.util.function.Supplier;

/**
 * @author Antonia, Ashley
 */
public class InvisibleDoorObject extends BaseObject{
    //Initialisieren eines Ziels (Wohin soll die Tür führen?)
    public final Supplier<BaseScene> target;
    //Initialisieren eines Ursprungs
    public final BaseScene origin;
   
    public InvisibleDoorObject(int x, int y, BaseScene origin, Supplier<BaseScene> target){
        //Einfügen des Objektes
        //kein Bild (Objekt ist unsichtbar)
        //x und y beschreiben die Position des Objektes, diese wird in den jeweiligen Szenen festgelegt
        //Breite und Höhe entsprechen den Werten der Tür in den Bildern
        super(null, x, y, 140, 240);
        this.target = target;
        this.origin = origin;
    }
    
    @Override
    public void update(BaseScene scene) {
         if(isClicked())
            SceneStack.INSTANCE.replace(origin, target.get());
    }
}
