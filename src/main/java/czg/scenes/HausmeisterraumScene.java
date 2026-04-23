
package czg.scenes;

import czg.objects.*;
import czg.util.Images;
import czg.util.Input;

import java.awt.*;
import java.awt.event.MouseEvent;

import static czg.MainWindow.PIXEL_SCALE;

public class HausmeisterraumScene extends BaseScene{
    public HausmeisterraumScene() {
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Hausmeisterraum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, GangHausmeisterScene::new, PfeilObject.UNTEN));

        objects.add(new BaseObject(null, 105 * PIXEL_SCALE, 64 * PIXEL_SCALE, 16, 16) {
            @Override
            public void update(BaseScene scene) {
                super.update(scene);
                Point mousePos = Input.INSTANCE.getMousePosition();
                if(mousePos != null && getHitbox().contains(mousePos) && Input.INSTANCE.getMouseState(MouseEvent.BUTTON1).isDown())
                    PlayerObject.INSTANCE.addItem(ItemType.TEXT);
            }
        });

        objects.add(new BaseObject(null, 31 * PIXEL_SCALE, 52 * PIXEL_SCALE, 12, 12) {
            @Override
            public void update(BaseScene scene) {
                super.update(scene);
                Point mousePos = Input.INSTANCE.getMousePosition();
                if(mousePos != null && getHitbox().contains(mousePos) && Input.INSTANCE.getMouseState(MouseEvent.BUTTON1).isDown())
                    PlayerObject.INSTANCE.addItem(ItemType.PAPIER);
            }
        });

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 230;
        PlayerObject.INSTANCE.y = 290;
        
    }
}