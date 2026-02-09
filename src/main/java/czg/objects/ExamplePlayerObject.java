package czg.objects;

import czg.scenes.BaseScene;
import czg.sound.BaseSound;
import czg.sound.ClipSound;
import czg.sound.EndOfFileBehaviour;
import czg.util.Images;
import czg.util.Input;

import java.awt.*;
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

    private final BaseSound sound = new ClipSound("/assets/sound/oink.ogg", false, EndOfFileBehaviour.RESTART_AND_PAUSE);
    {
        sound.setEndOfFileBehaviour(EndOfFileBehaviour.RESTART_AND_PAUSE);
    }

    private ExamplePlayerObject() {
        super(Images.get("/assets/characters/bre.png"));
    }

    @Override
    public void update(BaseScene scene) {
        if(isClicked())
            sound.setPlaying(true);


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
/*
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        // Hitbox in rot zeichnen
        g.setColor(Color.RED);
        g.drawRect(x, y, width, height);
    }
*/
}
