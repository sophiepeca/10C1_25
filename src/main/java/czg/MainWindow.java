package czg;

import czg.scenes.*;
import czg.sound.EndOfFileBehaviour;
import czg.sound.SoundGroup;
import czg.sound.StreamSound;
import czg.util.Input;

import javax.swing.*;
import java.awt.*;

/**
 * Das Haupt-Fenster. Hier wird die Grafik ausgegeben.
 */
public class MainWindow extends JFrame implements Runnable {

    /**
     * Wie viele Bildschirm-Pixel ein Textur-Pixel beansprucht
     */
    public static final int PIXEL_SCALE = 4;

    /**
     * Wie viele Bildschirm-Pixel das Fenster breit ist
     */
    public static final int WIDTH = 240 * PIXEL_SCALE;

    /**
     * Wie viele Bildschirm-Pixel das Fenster hoch ist
     */
    public static final int HEIGHT = 135 * PIXEL_SCALE;

    /**
     * Einzelbilder pro Sekunde
     */
    public static final int FPS = 60;

    /**
     * Singleton des Fensters
     */
    public static final MainWindow INSTANCE = new MainWindow();

    /**
     * Damit {@link Input.KeyState#fromTimePressed(long)} bei einem Durchlauf
     * auch immer denselben {@link System#nanoTime()}-Wert benutzt
     */
    public long TIME_AT_UPDATE_START;


    /**
     * Soll nicht von außerhalb instanziiert werden
     */
    private MainWindow() {
        super("CZGame");

        // Feste Größe
        setSize(WIDTH,HEIGHT);
        setResizable(false);

        // Manuelles platzieren von Elementen
        setLayout(null);

        // Szenen-Stapel hinzufügen
        setContentPane(SceneStack.INSTANCE);
        SceneStack.INSTANCE.setBounds(0,0, WIDTH, HEIGHT);

        // Gesamtes Programm wird beendet, wenn das Fenster geschlossen wird
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Die tatsächliche Main-Methode. Zeigt das Fenster, startet
     * die Haupt-Schleife und fügt die erste Szene hinzu.
     * @param args Unbenutzt
     */
    public static void main(String[] args) {
        // OpenGL-Grafikschnittstelle und damit (hoffentlich) die Grafikkarte verwenden
        System.setProperty("sun.java2d.opengl","true");

        // Fenster zeigen
        // In die Mitte des Bildschirms platzieren
        INSTANCE.setLocationRelativeTo(null);
        // Zeigen
        INSTANCE.setVisible(true);

        InfogangScene mgtest = new InfogangScene();
        SceneStack.INSTANCE.push(mgtest);
        /*
        BiogangScene start = new BiogangScene();
        SceneStack.INSTANCE.push(start);
        */
        /*
        PhysikgangScene physik = new PhysikgangScene();
        start.objects.add(ExamplePlayerObject.INSTANCE);
        INSTANCE.SCENE_STACK.push(physik);
        */

        // BEISPIEL-SZENE (nur zur Referenz, später entfernen!)
        SoundGroup.GLOBAL_SOUNDS.addSound(
                new StreamSound("/assets/sound/hallway.ogg", true, EndOfFileBehaviour.LOOP)
        );

        // Haupt-Schleife in einem neuen Thread starten

        SwingUtilities.invokeLater(() -> {
            Insets insets = INSTANCE.getInsets();
            INSTANCE.setSize(WIDTH+insets.left+insets.right, HEIGHT+insets.top+insets.bottom);
        });

        new Thread(INSTANCE, "GameLoop").start();

    }

    /**
     * Haupt-Logik
     */
    @Override
    public void run() {
        // In welchem Zeitintervall (in Nanosekunden) die Spiellogik ausgeführt werden soll
        final double interval = 1e9 / FPS;
        // Zählt, wie oft die Spiellogik durchlaufen werden sollte.
        double delta = 0;

        // Zeit seit dem letzten Durchlauf der while(true)-Schleife
        long lastTime = System.nanoTime();
        // Zweite Zeit-Variable. Wird zur neuen lastTime.
        long currentTime;

        System.out.println("Haupt-Schleife beginnt");

        //noinspection InfiniteLoopStatement
        while(true) {
            // Aktuelle Zeit messen
            currentTime = System.nanoTime();

            // Die Zeit berechnen, die seit dem letzten Durchlauf dieser Schleife vergangen ist.
            // Damit berechnen, wie oft die Spiellogik in dieser Zeitspanne hätte durchlaufen
            // werden sollen (normalerweise eine Anzahl <1).
            delta += (currentTime - lastTime) / interval;

            // Die currentTime wird wieder zur lastTime
            lastTime = currentTime;

            // Alle nötigen Durchläufe abarbeiten
            while(delta >= 1) {
                TIME_AT_UPDATE_START = System.nanoTime();

                // Code für Szenen und Objekte ausführen
                SceneStack.INSTANCE.update();
                // Grafik
                SceneStack.INSTANCE.repaint();

                Input.INSTANCE.updateToHeld();

                // Durchlauf abgeschlossen, Zähler kann um 1 verringert werden
                delta--;
            }

        }

    }
}
