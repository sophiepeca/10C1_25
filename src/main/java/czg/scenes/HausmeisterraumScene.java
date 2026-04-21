
package czg.scenes;

import czg.objects.*;
import czg.util.Images;

import static czg.MainWindow.PIXEL_SCALE;

public class HausmeisterraumScene extends BaseScene{
    public HausmeisterraumScene() {
        //Einfügen des Hintergrunds
        objects.add(new BackdropObject(Images.get("/assets/background/Hausmeisterraum.png")));
        
        //Pfeilobjekt für den Wechsel in die Gangszene
        objects.add(new PfeilObject(this, GangHausmeisterScene::new, PfeilObject.UNTEN));

        objects.add(new ButtonObject(null, 105 * PIXEL_SCALE, 64 * PIXEL_SCALE, 16, 16, () -> {
            if(! PlayerObject.INSTANCE.inventar.containsKey(ItemType.TEXT))
                PlayerObject.INSTANCE.addItem(ItemType.TEXT);
        }));

        objects.add(new ButtonObject(null, 31 * PIXEL_SCALE, 52 * PIXEL_SCALE, 12, 12, () -> {
            if(! PlayerObject.INSTANCE.inventar.containsKey(ItemType.PAPIER))
                PlayerObject.INSTANCE.addItem(ItemType.PAPIER);
        }));

        //Einfügen der Spieler-Figur
        this.objects.add(PlayerObject.INSTANCE);
        PlayerObject.INSTANCE.x = 230;
        PlayerObject.INSTANCE.y = 290;
        
    }
}