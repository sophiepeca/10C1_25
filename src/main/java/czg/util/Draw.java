package czg.util;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Hilfsfunktionen für Grafik
 */
public final class Draw {

    /**
     * Schriftart für Überschriften
     */
    public static final Font FONT_TITLE = new Font("Arial", Font.BOLD, 32);

    public static final Font FONT_INFO = new Font("Arial", Font.PLAIN, 18);

    /**
     * Völlig durchsichtige Farbe
     */
    public static final Color TRANSPARENCY = new Color(0,0,0,0);

    /**
     * Zeichnet Text, sodass dessen Mittelpunkt bei den angegebenen Koordinaten liegt. Farbe und Schrift
     * sollten vorher schon eingestellt sein.
     * @param g Graphics-Objekt. Sollte durch den entsprechenden Parameter einer draw()-Methode bereitgestellt werden.
     * @param text Der Text
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public static void drawTextCentered(Graphics2D g, String text, int x, int y, boolean outline) {
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
        int tx = (int) (x - bounds.getWidth() / 2);
        int ty = (int) (y - bounds.getHeight() / 2);
        if(outline)
            drawTextWithOutline(g, text, tx, ty);
        else
            g.drawString(text, tx, ty);
    }

    public static void drawTextWithOutline(Graphics2D g, String text, int x, int y) {
        Color mainColor = g.getColor();
        Color shadowColor = mainColor.darker().darker().darker();

        g.setColor(shadowColor);
        g.drawString(text, x - 1, y - 1);
        g.drawString(text, x - 1, y);
        g.drawString(text, x - 1, y + 1);

        g.drawString(text, x, y - 1);
        g.drawString(text, x, y + 1);

        g.drawString(text, x + 1, y - 1);
        g.drawString(text, x + 1, y);
        g.drawString(text, x + 1, y + 1);

        g.setColor(mainColor);
        g.drawString(text, x, y);
    }


    /**
     * Soll nicht instanziiert werden
     */
    private Draw() {}

}
