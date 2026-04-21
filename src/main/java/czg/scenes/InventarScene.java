package czg.scenes;

import czg.objects.*;
import czg.util.Images;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static czg.MainWindow.*;

public class InventarScene extends BaseScene {

    /**
     * X-Koordinate der linken Seite
     */
    private static final int iLeft = (int)(WIDTH * 0.03);
    /**
     * Y-Koordinate der oberen Seite
     */
    private static final int iTop = (int)(HEIGHT * 0.03);
    /**
     * Breite des Inventars
     */
    private static final int iWidth = WIDTH - 2 * iLeft;
    /**
     * Höhe des Inventars
     */
    private static final int iHeight = 65 * PIXEL_SCALE; // 39
    /**
     * Abstand zwischen Items
     */
    private static final int iPadding = (int)(WIDTH * 0.025);


    private final ButtonObject arrowUp;

    private final ButtonObject arrowDown;

    private int currentRow = 0;

    private List<List<ItemObject>> rows = new ArrayList<>();


    public InventarScene() {
        // Hintergrund
        objects.add(new BaseObject(Images.get("/assets/items/inventar.png"), iLeft, iTop, iWidth, iHeight));

        // Button zum Schließen
        ButtonObject exit = new ButtonObject (
                Images.get("/assets/minigames/general/button_exit.png"),
                SceneStack.INSTANCE::pop
        );

        exit.x = iLeft + iWidth - iPadding * 2 - exit.sprite.getWidth(null) * PIXEL_SCALE;
        exit.y = iTop + (iHeight / 2) - (exit.sprite.getHeight(null) * PIXEL_SCALE )/2;
        objects.add(exit);

        // Reihe nach oben
        arrowUp = new ButtonObject(Images.cropTransparency((BufferedImage) Images.get("/assets/background/PfeilOben.png")), () -> changeRow(-1));
        arrowDown = new ButtonObject(Images.cropTransparency((BufferedImage) Images.get("/assets/background/PfeilUnten.png")), () -> changeRow(1));

        arrowUp.x = exit.x;
        arrowUp.y = iTop + 40;

        arrowDown.x = arrowUp.x;
        arrowDown.y = iTop + iHeight - arrowDown.height - 40;

        objects.add(arrowUp);
        objects.add(arrowDown);

        generateRows();
        setArrowVisibility();
        changeRow(0);
    }

    private void generateRows() {
        final int x0 = iLeft + (int)(iPadding * 3);
        final int pad = iPadding * 4;
        int x = x0;

        List<ItemObject> currentRow = null;

        List<ItemType> items = List.copyOf(PlayerObject.INSTANCE.inventar.sequencedKeySet());
        for(int i = 0; i < items.size(); i++) {
            ItemType type = items.get(i);
            currentRow = currentRow == null ? new ArrayList<>() : currentRow;

            ItemObject object = new ItemObject(type, 1, x, 0);
            object.y = type == ItemType.PAPIER ? iTop : iTop + (iHeight / 2) - (object.height / 2) - PIXEL_SCALE * 3;
            object.visible = false;
            objects.add(object);

            if(x + object.width > arrowUp.x - iPadding) {
                // Zeile abschließen
                rows.add(currentRow);
                // Item schon mal in die nächste Zeile
                object.x = x0;
                currentRow = new ArrayList<>();
                currentRow.add(object);
                // Wieder bei links
                x = x0 + object.width + pad;
            } else {
                currentRow.add(object);
                x += object.width + pad;
            }

            if (x > arrowUp.x - iPadding || x == -1) {
                // Nächste Zeile
                rows.add(currentRow);
                currentRow = null;
                x = x0;
            }
        }

        if(currentRow != null)
            rows.add(currentRow);
    }

    private void changeRow(int by) {
        if(rows.isEmpty())
            return;

        // Aktuelle Items verstecken
        if(by != 0) {
            for (ItemObject obj : rows.get(this.currentRow))
                obj.visible = false;
        }

        currentRow += by;

        // Neue Items zeigen
        for(ItemObject obj : rows.get(currentRow))
            obj.visible = true;

        setArrowVisibility();
    }


    private void setArrowVisibility() {
        if(rows.size() <= 1) {
            arrowUp.visible = false;
            arrowDown.visible = false;
        } else if(currentRow == 0) {
            arrowUp.visible = false;
            arrowDown.visible = true;
        } else if(currentRow == rows.size()-1) {
            arrowUp.visible = true;
            arrowDown.visible = false;
        } else {
            arrowUp.visible = true;
            arrowDown.visible = true;
        }
    }
    
}
