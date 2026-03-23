/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.objects.minigame_objects;

import czg.objects.BaseObject;
import czg.scenes.BaseScene;
import czg.util.Images;
import czg.util.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TangramPieceObject extends BaseObject {
    public double rotation;

    private boolean isDragged = false;

    public static final TangramPieceObject[] PIECES = new TangramPieceObject[] {
        new TangramPieceObject("/assets/minigames/mathematics/tangram_piece_00.png"),
        new TangramPieceObject("/assets/minigames/mathematics/tangram_piece_01.png"),
        new TangramPieceObject("/assets/minigames/mathematics/tangram_piece_02.png"),
        new TangramPieceObject("/assets/minigames/mathematics/tangram_piece_03.png"),
        new TangramPieceObject("/assets/minigames/mathematics/tangram_piece_04.png"),
        new TangramPieceObject("/assets/minigames/mathematics/tangram_piece_05.png"),
        new TangramPieceObject("/assets/minigames/mathematics/tangram_piece_06.png"),
    };

    private TangramPieceObject(String path) {
       super(Images.get(path));
       this.rotation = 0;
    }
    
    public static void generatePacked(int x, int y, int width, int height) {
        PIECES[0].x = x;
        PIECES[0].y = y;
        PIECES[0].width = width;
        PIECES[0].height = height/2;
        
        PIECES[1].x = x;
        PIECES[1].y = y;
        PIECES[1].width = width/2;
        PIECES[1].height = height;
        
        PIECES[2].x = x + width/2;
        PIECES[2].y = y + height/2;
        PIECES[2].width = width/2;
        PIECES[2].height = height/2;
        
        PIECES[3].x = x;
        PIECES[3].y = y + (int) (height*0.75);
        PIECES[3].width = width/2;
        PIECES[3].height = height/4;
        
        PIECES[4].x = x + width/2;
        PIECES[4].y = y + height/4;
        PIECES[4].width = width/4;
        PIECES[4].height = height/2;
        
        PIECES[5].x = x + (int) (width*0.75);
        PIECES[5].y = y;
        PIECES[5].width = width/4;
        PIECES[5].height = (int) (height*0.75);
        
        PIECES[6].x = x + width/4;
        PIECES[6].y = y + height/2;
        PIECES[6].width = width/2;
        PIECES[6].height = height/2;
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
        } else if (! Input.INSTANCE.getMouseState(MouseEvent.BUTTON1).isDown()) {
            // Wenn die linke Maustaste losgelassen wird, wird das Objekt nicht mehr gezogen
            isDragged = false;
        }

        if (isDragged) {
            // Aktualisierung der Position
            this.x += mousePos.x - lastMousePos.x;
            this.y += mousePos.y - lastMousePos.y;

            // Rotieren des Objektes
            if(Input.INSTANCE.getKeyState(KeyEvent.VK_R) == Input.KeyState.PRESSED) {
                this.rotation += 90;
                this.rotate(90);
            }
        }
    }
}
