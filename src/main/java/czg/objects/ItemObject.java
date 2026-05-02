package czg.objects;

import czg.util.Draw;

import java.awt.*;

import static czg.MainWindow.PIXEL_SCALE;

public class ItemObject extends BaseObject {

    public final ItemType item;
    private final int count;

    private static final int MIN_LV_COUNT_DISTANCE = PIXEL_SCALE * 12;

    private static final Color[] LEVEL_COLORS = {
            Color.ORANGE,
            new Color(72, 194, 194),
            new Color(221, 98, 159)
    };

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

        g.setFont(Draw.FONT_INFO.deriveFont(18f));

        if(! (item == ItemType.TEXT || item == ItemType.PAPIER)) {
            g.setColor(Color.WHITE);
            Draw.drawTextCentered(g, item.NAME, x + width / 2 - 1, y + height + 8 * PIXEL_SCALE - 1, true);
        }

        if(count > 0) {
            int left = x + PIXEL_SCALE;
            int right = x + width - PIXEL_SCALE;
            final int distance = right - left;
            if (distance < MIN_LV_COUNT_DISTANCE) {
                final int diff = (MIN_LV_COUNT_DISTANCE - distance) / 2;
                left -= diff;
                right += diff;
            }

            g.setColor(item.LEVEL < LEVEL_COLORS.length ? LEVEL_COLORS[item.LEVEL] : Color.MAGENTA);
            Draw.drawTextCentered(g, "Lv "+(item.LEVEL+1), left, y + height + PIXEL_SCALE * 2, true);

            g.setColor(Color.WHITE);
            Draw.drawTextCentered(g, "x%d".formatted(count), right, y + height + PIXEL_SCALE * 2, true);
        } else {
            g.setColor(LEVEL_COLORS[item.LEVEL]);
            Draw.drawTextCentered(g, "Lv "+(item.LEVEL+1), x + width / 2, y + height + PIXEL_SCALE * 2, true);
        }

    }
}
