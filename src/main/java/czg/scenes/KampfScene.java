package czg.scenes;
import czg.objects.BackdropObject;
import czg.util.Images;

/**
 * @author Sophie
 */
public class KampfScene extends BaseScene{
    public KampfScene(){
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Kampfgang.png")));
        
        SceneStack.INSTANCE.push(new InventarScene());
    }
}


