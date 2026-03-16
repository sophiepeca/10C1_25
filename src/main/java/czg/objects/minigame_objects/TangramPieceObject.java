/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.objects.minigame_objects;

import czg.objects.DragObject;
import czg.util.Images;

public class TangramPieceObject extends DragObject{
    public final int ID;
    public double rotation;
    
    public static final TangramPieceObject[] PIECES = new TangramPieceObject[] {
        new TangramPieceObject(0, "/assets/minigames/mathematics/tangram_piece_00.png", 0, 0),
        new TangramPieceObject(1, "/assets/minigames/mathematics/tangram_piece_01.png", 0, 0),
        new TangramPieceObject(2, "/assets/minigames/mathematics/tangram_piece_02.png", 0, 0),
        new TangramPieceObject(3, "/assets/minigames/mathematics/tangram_piece_03.png", 0, 0),
        new TangramPieceObject(4, "/assets/minigames/mathematics/tangram_piece_04.png", 0, 0),
        new TangramPieceObject(5, "/assets/minigames/mathematics/tangram_piece_05.png", 0, 0),
        new TangramPieceObject(6, "/assets/minigames/mathematics/tangram_piece_06.png", 0, 0),
    };

    private TangramPieceObject(int id, String path, int x, int y) {
       super(Images.get(path), x, y);
       this.ID = id;
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
}
