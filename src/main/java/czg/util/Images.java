package czg.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static czg.MainWindow.PIXEL_SCALE;

/**
 * Zentraler Zugriff auf Bilddateien im "src/main/resources"-Ordner
 */
public class Images {

    /**
     * Cache für geladene Bilder
     */
    private static final Map<String, Image> loaded = new ConcurrentHashMap<>();

    /**
     * Bild, welches verwendet wird für den Fall, dass das eigentliche
     * Bild nicht geladen werden kann. Ein 2x2-Schachbrett-Muster in
     * Schwarz und Magenta (wie in z.B. Minecraft).
     */
    static final Image missingTexture;

    static {
        // missingTexture generieren
        missingTexture = new BufferedImage(PIXEL_SCALE, PIXEL_SCALE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) missingTexture.getGraphics();

        // Schwarzer hintergrund
        g.setColor(Color.BLACK);
        g.fillRect(0,0,PIXEL_SCALE,PIXEL_SCALE);

        g.setColor(Color.MAGENTA);
        // Obere rechte Ecke
        g.fillRect(PIXEL_SCALE/2,0,PIXEL_SCALE/2,PIXEL_SCALE/2);
        // Untere linke Ecke
        g.fillRect(0,PIXEL_SCALE/2,PIXEL_SCALE/2,PIXEL_SCALE/2);
    }

    /**
     * Das Bild "src/main/resources/[path]" ggf. laden und zurückgeben
     * @param path Dateipfad, z.B. "/assets/items/taschenrechner.png". Der Slash vorne ist wichtig!
     * @return Das geladene Bild
     */
    public static Image get(String path) {
        return loaded.computeIfAbsent(path, p -> {
            try(InputStream stream = Images.class.getResourceAsStream(p)) {
                // Datei nicht gefunden → missingTexture
                if(stream == null) {
                    System.err.printf("Datei nicht gefunden: %s%n", p);
                    return missingTexture;
                }

                // Versuchen, das Bild zu laden. Die mögliche IOException
                // wird abgefangen und resultiert in der missingTexture.
                Image result = ImageIO.read(stream);
                // Konnte das Bild nicht gelesen werden, wird ebenfalls die
                // missingTexture zurückgegeben.
                return result == null ? missingTexture : result;
            } catch (IOException x) {
                System.err.printf("Eingabe/Ausgabe-Fehler beim laden von %s%n", p);
                return missingTexture;
            }
        });

    }

    /**
     * Dreht ein Bild.
     * @param source Originalbild
     * @param degrees Drehung in Grad
     * @return Ein neues, gedrehtes Bild
     */
    public static Image rotateImage(Image source, double degrees) {
        BufferedImage bSource = (BufferedImage) source;

        // Transformations-Objekt erstellen
        AffineTransform rotate = AffineTransform.getRotateInstance(
                Math.toRadians(degrees),
                Math.ceil(source.getWidth(null) / 2d),
                Math.ceil(source.getHeight(null) / 2d)
        );

        // Neue Bounds ermitteln
        Rectangle2D bounds = new AffineTransformOp(rotate, AffineTransformOp.TYPE_BICUBIC).getBounds2D(bSource);

        // Neues Bild erstellen
        BufferedImage result = new BufferedImage((int) Math.ceil(bounds.getWidth()), (int) Math.ceil(bounds.getHeight()), BufferedImage.TYPE_INT_ARGB);

        // Grafik-Objekt für das Bild erhalten
        Graphics2D g = (Graphics2D) result.getGraphics();
        g.setRenderingHints(Map.of(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));

        // Gesamte Grafik verschieben
        g.translate(-bounds.getX(), -bounds.getY());
        // Gedrehtes Bild zeichnen
        g.drawImage(source, rotate, null);
        // Grafik-Objekt löschen
        g.dispose();

        return cropTransparency(result);
    }

    public static BufferedImage cropTransparency(BufferedImage image) {
        WritableRaster alpha = image.getAlphaRaster();
        if(alpha == null)
            return image;

        int minX = 0, minY = 0, maxX = image.getWidth()-1, maxY = image.getHeight()-1;

        while(minX < image.getWidth() && Arrays.stream(alpha.getPixels(minX, 0, 1, image.getHeight(), new int[image.getHeight()])).sum()==0)
            minX++;

        while(minY < image.getHeight() && Arrays.stream(alpha.getPixels(0, minY, image.getWidth(), 1, new int[image.getWidth()])).sum()==0)
            minY++;

        while(maxX > minX && Arrays.stream(alpha.getPixels(maxX, 0, 1, image.getHeight(), new int[image.getHeight()])).sum()==0)
            maxX--;

        while(maxY > minY && Arrays.stream(alpha.getPixels(0, maxY, image.getWidth(), 1, new int[image.getWidth()])).sum()==0)
            maxY--;

        return image.getSubimage(minX, minY, maxX-minX+1, maxY-minY+1);
    }


    /**
     * Soll nicht instanziiert werden
     */
    private Images() {}

}
