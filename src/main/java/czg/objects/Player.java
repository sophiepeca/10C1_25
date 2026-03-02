/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author guest-0wphjm
 */
public class Player extends BaseObject{
    //Festlegen der benötigten Variablen (Lebenspunkte, veränderliche Charaktereigentschaften)
    public String name;
    public int[] inventar;
    
    public PlayerObject INSTANCE = new PlayerObject();

    public PlayerObject() {
        super(Images.get("/assets/characters/PlayerBase.png"));
    }
    
    
    public Player(Image sprite, int x, int y, String name, int[] inventar){
          super (sprite, x, y);
          this.name = name;
          this.inventar = inventar;
          
      
    }
    @Override
    public void update(BaseScene scene) {
        if(isClicked())
            SceneStack.INSTANCE.push(new InventarScene());
    }
    
}
