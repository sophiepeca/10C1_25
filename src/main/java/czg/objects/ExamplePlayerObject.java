package czg.objects;

import czg.scenes.BaseScene;
import czg.util.Images;
import czg.util.Input;

import java.awt.event.KeyEvent;

/**
 * Beispiel-Objekt mit einem Sprite, welches auf Maus- und Tasteneingaben reagiert sowie
 * die draw()-Funktion über das Zeichnen des Sprites hinaus erweitert.
 */
public class ExamplePlayerObject extends BaseObject {

    /**
     * Eine von überall verfügbare Instanz der Spielfigur.
     */
    public static final ExamplePlayerObject INSTANCE = new ExamplePlayerObject();

    private ExamplePlayerObject() {
        super(Images.get("/assets/characters/bre.png"));
    }

    @Override
    public void update(BaseScene scene) {

        if(Input.INSTANCE.getKeyState(KeyEvent.VK_UP).isDown()) {
            y -= 10;
        }

        if (Input.INSTANCE.getKeyState(KeyEvent.VK_DOWN).isDown()) {
            y += 10;
        }

        if (Input.INSTANCE.getKeyState(KeyEvent.VK_LEFT).isDown()) {
            x -= 10;
        }

        if (Input.INSTANCE.getKeyState(KeyEvent.VK_RIGHT).isDown()) {
            x += 10;
        }
    }

}
