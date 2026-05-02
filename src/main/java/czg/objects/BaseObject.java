package czg.objects;

import czg.scenes.BaseScene;
import czg.util.Draw;
import czg.util.Input;
import czg.util.Input.KeyState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static czg.MainWindow.*;

/**
 * Ein minimales Spiel-Objekt, bestehend aus einer Position und einem Bild.
 */
public class BaseObject {

    /**
     * Position
     */
    public int x, y;
    /**
     * Größe
     */
    public int width, height;
    /**
     * Angezeigtes Bild. Kann {@code null} sein, wenn das Objekt
     * unsichtbar sein soll.
     */
    public Image sprite;

    /**
     * Ob das Objekt gezeichnet und geupdated wird
     */
    public boolean visible = true;

    /**
     * Ein neues Objekt erstellen und in die Mitte des Bildschirms platzieren.
     * Die Größe des Objekts entspricht der Größe des Bildes.
     * @param sprite Bild
     */
    public BaseObject(Image sprite) {
        this(sprite, WIDTH / 2, HEIGHT / 2);
    }

    /**
     * Ein neues Objekt erstellen und an die angegebene Stelle platzieren.
     * Die Größe des Objekts entspricht der Größe des Bildes.
     * @param sprite Bild
     * @param x X-Position
     * @param y Y-Position
     */
    public BaseObject(Image sprite, int x, int y) {
        this(sprite, x, y,
                sprite != null ? sprite.getWidth(null) * PIXEL_SCALE : 0,
                sprite != null ? sprite.getHeight(null) * PIXEL_SCALE : 0);
    }

    /**
     * Ein neues Objekt erstellen.
     * @param sprite Bild
     * @param x X-Position
     * @param y Y-Position
     * @param width Breite
     * @param height Höhe
     */
    public BaseObject(Image sprite, int x, int y, int width, int height) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Die {@link Rectangle2D}-Klasse bietet viele nützliche Funktionen wie {@link Rectangle2D#contains(Point2D)} und
     * {@link Rectangle2D#contains(Rectangle2D)}. So kann geprüft werden, ob ein Objekt angeklickt wurde oder ob
     * es sich auf einem anderen Objekt befindet (dieses "berührt").
     * Unterklassen von {@code BaseObject} können diese Methode auch überschreiben, <b>also auch {@code null} zurückgeben.</b>
     * @return Die Position sowie Größe des Objektes verpackt in ein {@link Rectangle2D}
     */
    public Rectangle2D getHitbox() {
        return new Rectangle2D.Float(x, y, width, height);
    }

    /**
     * Ein Objekt gilt als angeklickt, wenn sich der Mauszeiger über diesem befindet
     * und die linke Maustaste geklickt wurde ({@link KeyState#PRESSED}). Transparenz wird mit zur Hitbox gezählt.
     * @return Ob das Objekt angeklickt wurde
     */
    public boolean isClicked() {
        return isClicked(true);
    }
    
    /**
     * Ein Objekt gilt als angeklickt, wenn sich der Mauszeiger über diesem befindet
     * und die linke Maustaste geklickt wurde ({@link KeyState#PRESSED}).
     * @param includeTransparency Ob Transparenz zur Hitbox gezählt werden soll (True ist equivalent zum Aufruf ohne Parameter)
     * @return Ob das Objekt angeklickt wurde
     */
    public boolean isClicked(boolean includeTransparency) {
        if(! visible)
            return false;

        Point mousePos = Input.INSTANCE.getMousePosition();
        if(mousePos == null)
            return false;

        // Überprüfung, ob sich die Maus in der Hitbox befindet
        if(getHitbox().contains(mousePos) && Input.INSTANCE.getMouseState(MouseEvent.BUTTON1) == Input.KeyState.PRESSED) {
            // Überprüfung, ob der Pixel an der Mausposition transparent ist, falls includeTransparency = true
            if(!includeTransparency) {
                BufferedImage bufferedSprite = (BufferedImage) this.sprite;
                int alpha = (bufferedSprite.getRGB((int)((mousePos.x - this.x)/(this.width/(double)bufferedSprite.getWidth())), (int)((mousePos.y - this.y)/(this.height/(double)bufferedSprite.getHeight()))) & 0xff000000) >>> 24;
                return alpha > 0;
            }

            return true;
        }

        return false;
    }

    /**
     * Zeichnet das Bild des Objektes
     * @param g Grafik-Objekt. Von der Szene bereitgestellt.
     */
    public void draw(Graphics2D g) {
        if(! visible)
            return;

        // drawImage tut nichts, wenn sprite == null
        g.drawImage(
                sprite,
                x, y,
                width, height,
                null
        );

        if(Input.debugDrawMode == 2) {
            // Position & hitbox
            g.setColor(Color.RED);
            g.fillRect(x-1, y-1, 3, 3);
            g.setColor(Color.BLUE);
            g.drawRect(x,y,width,height);
            // Name
            g.setColor(Color.BLACK);
            g.setFont(Draw.FONT_INFO);
            g.drawString(super.toString(), x, y+g.getFont().getSize());
        }
    }

    /**
     * Logik des Objektes. Muss von einer Unterklasse implementiert werden.
     * @param scene Die Szene, in welcher sich das Objekt befindet
     */
    public void update(BaseScene scene) {}

    /**
     * Wird aufgerufen, wenn die Szenen vom Szenen-Stapel
     * entfernt wird
     * @param scene Die Szene, in welcher sich das Objekt befindet
     * @see BaseScene#unload()
     */
    public void unload(BaseScene scene) {}

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
