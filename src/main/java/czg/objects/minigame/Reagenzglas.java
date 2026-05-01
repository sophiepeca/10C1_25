package czg.objects.minigame;

import czg.objects.BaseObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;

import static czg.MainWindow.PIXEL_SCALE;

/**
 * Ein einzelnes Reagenzglas als BaseObject
 */
public class Reagenzglas extends BaseObject {

    public Reagenzglas(Image glas, int x, int y, int width, int height, Map<String, Image> blockBilder) {
        super(glas, x, y, width, height);
        this.blockBilder = blockBilder;
    }
    //Konstanten
    private static final int BLOCK_W_ORIG = 7;
    private static final int BLOCK_H_ORIG = 5;
    private static final int BLOCK_W = BLOCK_W_ORIG * PIXEL_SCALE;
    private static final int BLOCK_H = BLOCK_H_ORIG * PIXEL_SCALE;
    public static final int GLAS_BREITE = 20 * PIXEL_SCALE;
    public static final int GLAS_HOEHE = 32 * PIXEL_SCALE;
    private static final int BLOCK_X_OFFSET = 5 * PIXEL_SCALE;
    private static final int BLOCK_BODEN_Y = 31 * PIXEL_SCALE;
    public static final int MAX_KAPAZITAET = 4;

    private final List<String> bloecke = new ArrayList<>();
    private final Map<String, Image> blockBilder;

    //Reagenzgläser befüllen
    public void blockHinzufuegen(String farbe) {
        if (bloecke.size() < MAX_KAPAZITAET) {
            bloecke.add(farbe);
        }
    }

    //Gibt oberste Farbe zurück, ohne zu entfernen (null wenn leer)
    public String oberstenBlockAnschauen() {
        return bloecke.isEmpty() ? null : bloecke.getLast();
    }

    //obere Farbe zurück und entfernen
    public String oberstenBlockEntfernen() {
        return bloecke.isEmpty() ? null : bloecke.removeLast();
    }

    //Glas leer?
    public boolean istLeer() {
        return bloecke.isEmpty();
    }

    //Glas voll? (Max_Kapazität erreicht)
    public boolean istVoll() {
        return bloecke.size() >= MAX_KAPAZITAET;
    }

    //Glas gelöst? (voll und 4mal die selbe Farbe)
    public boolean istGeloest() {
        if (bloecke.size() != MAX_KAPAZITAET) return false;
        String f = bloecke.getFirst();
        for (String b : bloecke) if (!b.equals(f)) return false;
        return true;
    }

    //Umschütten erlaubt? (Glas leer oder oberste Farbe gleich mit umzuschüttender Farbe)
    public boolean kannAblegen(String farbe) {
        if (istVoll()) return false;
        return istLeer() || oberstenBlockAnschauen().equals(farbe);
    }

    //schreibgeschützte Blockliste
    public java.util.List<String> getBloecke() {
        return Collections.unmodifiableList(bloecke);
    }

    //Glas mit Blöcken zeichnen
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        List<String> bloecke = getBloecke();
        for (int i = 0; i < bloecke.size(); i++) {
            String farbe = bloecke.get(i);
            int blockX = x + BLOCK_X_OFFSET;
            int blockY = y + BLOCK_BODEN_Y - (i + 1) * BLOCK_H;
            g.drawImage(blockBilder.get(farbe), blockX, blockY, BLOCK_W, BLOCK_H, null);
        }
    }

    //Reagenzglas ausgewählt? (zu Beginn immer false)
    private boolean ausgewaehlt = false;

    public void setAusgewaehlt(boolean a) {
        //falls ausgewählt hervorheben, falls abgewählt wieder zurück auf Anfangsposition
        y += ausgewaehlt == a ? 0 : (a ? -10 : 10);
        ausgewaehlt = a;
    }

}
