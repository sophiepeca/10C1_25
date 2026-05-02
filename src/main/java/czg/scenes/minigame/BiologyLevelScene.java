package czg.scenes.minigame;

import czg.minigame.BiologyData;
import czg.minigame.BiologyPlant;
import czg.objects.BaseObject;
import czg.objects.Department;
import czg.objects.minigame.BiologyLabelObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static czg.MainWindow.PIXEL_SCALE;

public class BiologyLevelScene extends LevelScene {

    private BiologyLabelObject selectedLabel = null;
    private BaseObject[] imagePlaceholders = new BaseObject[4];
    int[] imageX = {11, 67, 124, 180};
    int[] imageY = {17, 17, 17, 17};
    private List<BiologyPlant> chosen;


    public BiologyLevelScene(int level) {
        super(Department.BIOLOGY, level);

        BiologyPlant[] pool;
        if (LEVEL == 0) {
            pool = BiologyData.LEVEL_1_PLANTS;
            level = 1;
        } else if (LEVEL == 1) {
            pool = BiologyData.LEVEL_2_PLANTS;
            level = 2;
        } else {
            pool = BiologyData.LEVEL_3_PLANTS;
            level = 3;
        }

        List<BiologyPlant> shuffled = new ArrayList<>(Arrays.asList(pool));
        Collections.shuffle(shuffled);
        chosen = shuffled.subList(0, 4);
        List<BiologyPlant> remaining = shuffled.subList(4, shuffled.size());

        List<String> labels = new ArrayList<>();
        for (BiologyPlant plant : chosen) {
            labels.add(plant.name);
        }
        if (LEVEL == 1) {
            labels.add(remaining.get(0).name);
        } else if (LEVEL == 2) {
            labels.add(remaining.get(0).name);
            labels.add(remaining.get(1).name);
        }
        Collections.shuffle(labels);

        for (int i = 0; i < 4; i++) {
            int slotW = 49;
            int slotH = 49;

            BufferedImage original = (BufferedImage) chosen.get(i).image;

            double scale = Math.max((double) slotW / original.getWidth(), (double) slotH / original.getHeight());
            int scaledW = (int) (original.getWidth() * scale);
            int scaledH = (int) (original.getHeight() * scale);

            BufferedImage scaled = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = scaled.createGraphics();
            g2d.drawImage(original, 0, 0, scaledW, scaledH, null);
            g2d.dispose();

            int cropX = Math.max(0, (scaledW - slotW) / 2);
            int cropY = Math.max(0, (scaledH - slotH) / 2);
            int cropW = Math.min(slotW, scaledW);
            int cropH = Math.min(slotH, scaledH);
            Image cropped = scaled.getSubimage(cropX, cropY, cropW, cropH);

            BaseObject plantImage = new BaseObject(cropped, imageX[i] * PIXEL_SCALE, imageY[i] * PIXEL_SCALE);
            plantImage.width = cropW * PIXEL_SCALE;
            plantImage.height = cropH * PIXEL_SCALE;
            imagePlaceholders[i] = plantImage;
            objects.add(plantImage);
        }


        for (int i = 0; i < labels.size(); i++) {
            int col = i % 3;
            int row = i / 3;

            int labelX = (10 + col * 55) * PIXEL_SCALE;
            int labelY = (105 + row * 16) * PIXEL_SCALE;

            objects.add(new BiologyLabelObject(labels.get(i), labelX, labelY, 49 * PIXEL_SCALE, 12 * PIXEL_SCALE));
        }

    }

    private BiologyLabelObject getLabelAtSlot(int slot) {
        for (BaseObject obj : objects) {
            if (obj instanceof BiologyLabelObject label) {
                if (label.x == imageX[slot] * PIXEL_SCALE && label.y == 71 * PIXEL_SCALE) {
                    return label;
                }
            }
        }
        return null;
    }

    @Override
    public LevelScene reset() {
    return new BiologyLevelScene(LEVEL);
        }

    @Override
    public void update() {
        super.update();

        if (selectedLabel == null) {
            for (BaseObject obj : objects) {
                if (obj instanceof BiologyLabelObject label) {
                    if (label.isClicked()) {
                        selectedLabel = label;
                        label.selected = true;
                    }
                }
            }
        } else {
            if (selectedLabel.isClicked()) {
                selectedLabel.selected = false;
                selectedLabel = null;
                return;
            }

            for (int i = 0; i < 4; i++) {
                if (imagePlaceholders[i].isClicked()) {
                    BiologyLabelObject existing = getLabelAtSlot(i);
                    if (existing != null) {
                        int idx = 0;
                        for (BaseObject obj : objects) {
                            if (obj instanceof BiologyLabelObject lbl) {
                                if (lbl == existing) break;
                                idx++;
                            }
                        }
                        existing.x = (10 + (idx % 3) * 55) * PIXEL_SCALE;
                        existing.y = (105 + (idx / 3) * 16) * PIXEL_SCALE;
                    }
                    selectedLabel.x = imageX[i] * PIXEL_SCALE;
                    selectedLabel.y = 71 * PIXEL_SCALE;
                    selectedLabel.selected = false;
                    selectedLabel = null;
                    if (allPlaced()) {
                        if (checkWin()) {
                            levelWon();
                        } else {
                            levelLost();
                        }
                    }

                }
            }
        }
}
    private boolean checkWin() {
        for (int i = 0; i < 4; i++) {
            boolean found = false;
            for (BaseObject obj : objects) {
                if (obj instanceof BiologyLabelObject label) {
                    if (label.x == imageX[i] * PIXEL_SCALE) {
                        if (!label.name.equals(chosen.get(i).name)) {
                            return false;
                        }
                        found = true;
                    }
                }
            }
            if (!found) return false;
        }
        return true;
    }
    private boolean allPlaced() {
        for (int i = 0; i < 4; i++) {
            boolean found = false;
            for (BaseObject obj : objects) {
                if (obj instanceof BiologyLabelObject label) {
                    if (label.x == imageX[i] * PIXEL_SCALE) {
                        found = true;
                    }
                }
            }
            if (!found) return false;
        }
        return true;
    }

@Override
public void draw(Graphics2D g2) {
    super.draw(g2);
    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Monospaced", Font.BOLD, 16));
    g2.drawString("Biologie Minigame Level " + (LEVEL+1), 100, 90*PIXEL_SCALE);

    g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
    g2.setColor(new Color(200, 200, 255));
    g2.drawString("Klicke ein Label an und dann auf ein Pflanzenbild um das Label zuzuordnen.", 100, 95*PIXEL_SCALE);
}
}