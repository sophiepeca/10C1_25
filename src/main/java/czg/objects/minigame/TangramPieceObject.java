package czg.objects.minigame;

import czg.objects.BaseObject;
import czg.scenes.BaseScene;
import czg.scenes.minigame.MathematicsLevelScene;
import czg.util.Images;
import czg.util.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Ein Tangram-Teil, welches für das Mathematik-Minigame
 * verwendet wird
 */
public class TangramPieceObject extends BaseObject {

    /**
     * Dateipfad des ursprünglichen Bildes
     */
    private final String SPRITE_PATH;

    public int originalWidth;
    public int originalHeight;

    /**
     * Drehung in Grad
     */
    public double rotation;
    public MathematicsLevelScene levelScene;

    private boolean isDragged = false;

    /**
     * Ein neues Tangram-Teil erstellen
     * @param id Bestimmt die zu ladende Bilddatei
     */
    private TangramPieceObject(int id) {
       super(Images.get("/assets/minigames/mathematics/tangram_piece_%02d.png".formatted(id)));
       this.SPRITE_PATH = "/assets/minigames/mathematics/tangram_piece_%02d.png".formatted(id);
       this.originalWidth = sprite.getWidth(null);
       this.originalHeight = sprite.getHeight(null);
       this.rotation = 0;
       this.levelScene = null;
    }

    public static TangramPieceObject[] generatePieces() {
        return new TangramPieceObject[] {
            new TangramPieceObject(0),
            new TangramPieceObject(1),
            new TangramPieceObject(2),
            new TangramPieceObject(3),
            new TangramPieceObject(4),
            new TangramPieceObject(5),
            new TangramPieceObject(6)
        };
    }

    public static void setLevelScene(TangramPieceObject[] pieces, MathematicsLevelScene levelScene) {
        for (TangramPieceObject piece : pieces) {
            piece.levelScene = levelScene;
        }
    }

    public void rotate(double degree) {
        rotation = (rotation + degree) % 360;
        if (rotation < 0) rotation += 360;

        double scaleX = (double) width / sprite.getWidth(null);
        double scaleY = (double) height / sprite.getHeight(null);

        Image rotatedSprite = Images.rotateImage(Images.get(SPRITE_PATH), rotation);

        Point imageCenter = new Point(x + width/2, y + height/2);

        width = rotation != 0 ? (int) (rotatedSprite.getWidth(null) * scaleX) : originalWidth;
        height = rotation != 0 ? (int) (rotatedSprite.getHeight(null) * scaleY) : originalHeight;

        sprite = rotatedSprite;

        x = imageCenter.x - width/2;
        y = imageCenter.y - height/2;
    }

    public void setRotation(double degree) {
        double currentRotation = rotation;

        rotate(degree - currentRotation);
    }

    @Override
    public void update(BaseScene scene) {
        // Aktuelle und vorherige Maus-Position abfragen
        Point mousePos = Input.INSTANCE.getMousePosition();
        Point lastMousePos = Input.INSTANCE.getLastMousePosition();
        // Diese *können* (technisch gesehen) null sein
        if(mousePos == null || lastMousePos == null)
            return;

        if(! isDragged && isClicked(false)) {
            // Wenn das Objekt angeklickt wird, verschieben wir es an oberste Stelle (z-Achse) und beginnen, es zu ziehen
            scene.objects.remove(this);
            scene.objects.add(this);

            isDragged = true;
        } else if (isDragged && !Input.INSTANCE.getMouseState(MouseEvent.BUTTON1).isDown()) {
            // Wenn die linke Maustaste losgelassen wird, wird das Objekt nicht mehr gezogen
            isDragged = false;
            // Und überprüft, ob das Puzzle gelöst ist
            levelScene.checkPuzzle();
        }

        if (isDragged) {
            // Aktualisierung der Position
            this.x += mousePos.x - lastMousePos.x;
            this.y += mousePos.y - lastMousePos.y;

            // Rotieren des Objektes
            if(Input.INSTANCE.getKeyState(KeyEvent.VK_R) == Input.KeyState.PRESSED) {
                rotate(45);
            }
        }
    }
}
