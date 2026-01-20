package czg.scenes;

import czg.MainWindow;
import czg.objects.BaseObject;
import czg.util.Images;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Eine Szene besteht aus einem Hintergrund und einer beliebigen Menge von
 * sich darauf bewegenden Objekten.
 */
public class BaseScene {

    /**
     * Ob ein einfarbiger Hintergrund gewünscht ist. Andernfalls
     * wird das {@link #backgroundImage} verwendet.
     */
    private boolean useBackgroundColor = true;
    /**
     * Hintergrundfarbe, falls {@link #useBackgroundColor} {@code true} ist.
     */
    private Color backgroundColor = Color.BLACK;
    /**
     * Hintergrundbild, falls {@link #useBackgroundColor} {@code false} ist.
     */
    private Image backgroundImage = null;

    /**
     * Liste der Objekte in diese Szene
     */
    public final List<BaseObject> objects = new ArrayList<>();

    /**
     * Ob die Szene verdeckt ist
     */
    public boolean isCovered = false;
    /**
     * Ob die Szene ausgeblendet werden sollte, wenn sie verdeckt ist
     */
    public boolean coverDisablesDrawing = false;
    /**
     * Ob die Szene noch ihren Code ausführen sollte, wenn sie verdeckt ist
     */
    public boolean coverPausesLogic = false;
    /**
     * Ob die Szene ihre Musik oder Effekte pausieren sollte, wenn sie verdeckt ist
     */
    public boolean coverPausesAudio = false;


    /**
     * Eine einfarbige Farbe als Hintergrund verwenden
     * @param c Die Hintergrundfarbe
     */
    public void setBackgroundColor(Color c) {
        useBackgroundColor = true;
        backgroundColor = c;
    }

    /**
     * Ein Hintergrundbild anstelle einer einzelnen Farbe verwenden
     * @param i Das Bild
     * @see Images#get(String)
     */
    public void setBackgroundImage(Image i) {
        useBackgroundColor = false;
        backgroundImage = i;
    }

    /**
     * Ruft {@link BaseObject#update()} für jedes Objekt in der {@link #objects}-Liste auf
     */
    public void update() {
        objects.forEach(BaseObject::update);
    }


    /**
     * Zeichnet den Hintergrund und alle Objekte der Szene
     * @param g Grafik-Objekt. Wird vom Szenen-Stapel bereitgestellt.
     * @see SceneStack#paintComponent(Graphics)
     */
    public void draw(Graphics2D g) {
        // Hintergrund zeichnen:
        if(useBackgroundColor) {
            // Einfarbig
            g.setColor(backgroundColor);
            g.fillRect(0,0,MainWindow.WIDTH,MainWindow.HEIGHT);
        } else {
            // Bild
            g.drawImage(backgroundImage, 0, 0, MainWindow.WIDTH, MainWindow.HEIGHT, null);
        }

        // Objekte zeichnen
        objects.forEach(o -> o.draw(g));
    }
}
