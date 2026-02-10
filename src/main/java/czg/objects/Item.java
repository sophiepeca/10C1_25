/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.objects;

import czg.scenes.BaseScene;

import java.awt.*;

/**
 * Klasse für Items. Jedes Item sollte als <b>eine</b> {@code public static final}-Instanz
 * (Singleton) in dieser Klasse angelegt werden.
 */
public class Item extends BaseObject{

    public final int level;
    public final String name;
    public final int ID;

    private Item(Image sprite, int x, int y, int level, String name, int ID) {
        super(sprite, x, y);
        
        this.level = level;
        this.name = name;
        this.ID = ID;
    }
    
    @Override
    public void update(BaseScene scene) {
       
    }
    
}
