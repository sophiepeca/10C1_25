package czg.objects;

import czg.util.Draw;

import java.awt.*;

import static czg.MainWindow.PIXEL_SCALE;

public class ItemObject extends BaseObject {

    private final ItemType item;
    private final int count;

    public ItemObject(ItemType type, int count, int x, int y) {
        super(type.SPRITE, x, y);

        switch(type) {
            case TEXT -> {
                width = 80 * 4;
                height = 61 * 4;
            }
            case PAPIER -> {
                width = 80 * 4;
                height = 96 * 4;
            }
        }

        this.item = type;
        this.count = count;
    }

    @Override
    public void draw(Graphics2D g) {
        if(! visible)
            return;

        super.draw(g);

        g.setColor(Color.WHITE);
        g.setFont(Draw.FONT_TITLE.deriveFont(18f));

        if(! (item == ItemType.TEXT || item == ItemType.PAPIER))
            Draw.drawTextCentered(g, item.NAME, x + width / 2, y + height + 8 * PIXEL_SCALE);

        Draw.drawTextCentered(g, "x%d".formatted(count), x + width - PIXEL_SCALE, y + height + PIXEL_SCALE * 2);
    }
}
