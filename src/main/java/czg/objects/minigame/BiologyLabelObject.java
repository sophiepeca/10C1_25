package czg.objects.minigame;

import czg.objects.BaseObject;
import czg.objects.Department;
import czg.util.Draw;

import java.awt.*;

public class BiologyLabelObject extends BaseObject {

    public String name;
    public boolean selected;

    public BiologyLabelObject(String name, int x, int y, int width, int height) {
        super(null, x, y, width, height);
        this.name = name;
        this.selected = false;
    }

    @Override
    public void draw(Graphics2D g) {
        if (selected) {
            g.setColor(Color.LIGHT_GRAY);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.setFont(Draw.FONT_INFO.deriveFont(13f));
        g.drawString(name, x + 5, y + (height / 2)+3 );
    }
}
