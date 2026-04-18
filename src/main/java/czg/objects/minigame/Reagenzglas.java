package czg.objects.minigame;

import czg.objects.BaseObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;

public class Reagenzglas extends BaseObject {

    public Reagenzglas(Image glas, int x, int y, int width, int height, Map<String, Image> blockBilder) {
        super(glas, x, y, width, height);
        this.blockBilder = blockBilder;
    }
    private static final int SCALE = 4;
    private static final int BLOCK_W_ORIG = 7;
    private static final int BLOCK_H_ORIG = 5;
    private static final int BLOCK_W = BLOCK_W_ORIG * SCALE;
    private static final int BLOCK_H = BLOCK_H_ORIG * SCALE;
    public static final int GLAS_BREITE = 32 * SCALE;
    public static final int GLAS_HOEHE = 32 * SCALE;
    private static final int BLOCK_X_OFFSET = 11 * SCALE;
    private static final int BLOCK_BODEN_Y = 31 * SCALE;
    public static final int MAX_KAPAZITAET = 4;

    private final List<String> bloecke = new ArrayList<>();
    private final Map<String, Image> blockBilder;

    public void blockHinzufuegen(String farbe) {
        if (bloecke.size() < MAX_KAPAZITAET) {
            bloecke.add(farbe);
        }
    }

    public String oberstenBlockAnschauen() {
        return bloecke.isEmpty() ? null : bloecke.getLast();
    }

    public String oberstenBlockEntfernen() {
        return bloecke.isEmpty() ? null : bloecke.removeLast();
    }

    public boolean istLeer() {
        return bloecke.isEmpty();
    }

    public boolean istVoll() {
        return bloecke.size() >= MAX_KAPAZITAET;
    }

    public boolean istGeloest() {
        if (bloecke.size() != MAX_KAPAZITAET) return false;
        String f = bloecke.getFirst();
        for (String b : bloecke) if (!b.equals(f)) return false;
        return true;
    }

    public boolean kannAblegen(String farbe) {
        if (istVoll()) return false;
        return istLeer() || oberstenBlockAnschauen().equals(farbe);
    }

    public java.util.List<String> getBloecke() {
        return Collections.unmodifiableList(bloecke);
    }

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

    private boolean ausgewaehlt = false;

    public void setAusgewaehlt(boolean a) {
        y += ausgewaehlt == a ? 0 : (a ? -10 : 10);
        ausgewaehlt = a;
    }

}
