package czg;

import czg.objects.ExamplePlayerObject;
import czg.scenes.BiogangScene;
import czg.scenes.GangTestScene;
import czg.scenes.PhysikgangScene;
import czg.scenes.MathegangScene;
import czg.scenes.InfogangScene;
import czg.scenes.ChemiegangScene;
import czg.scenes.GangHausmeisterScene;
import czg.scenes.GangObenScene;
import czg.scenes.ExampleScene2;
import czg.scenes.SceneStack;
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

    public static final MainWindow INSTANCE = new MainWindow();


    /**
     * Soll nicht von außerhalb instanziiert werden
     */
    private MainWindow() {
        super("CZGame");

        // Feste Größe
        setSize(new Dimension(WIDTH,HEIGHT));
        setResizable(false);

        // Manuelles platzieren von Elementen
        setLayout(null);

        // Szenen-Stapel hinzufügen
        setContentPane(SceneStack.INSTANCE);
        SceneStack.INSTANCE.setBounds(0,0, WIDTH, HEIGHT);

        // Tastatur- und Maus-Eingaben empfangen
        addKeyListener(Input.INSTANCE);
        addMouseListener(Input.INSTANCE);
        addFocusListener(Input.INSTANCE);

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

        // Haupt-Schleife in einem neuen Thread starten
        new Thread(INSTANCE).start();

        //WICHTIG!!!!!!
        BiogangScene start = new BiogangScene();
        SceneStack.INSTANCE.push(start);
        
        /*
        PhysikgangScene physik = new PhysikgangScene();
        start.objects.add(ExamplePlayerObject.INSTANCE);
        INSTANCE.SCENE_STACK.push(physik);
        */

        // BEISPIEL-SZENE (nur zur Referenz, später entfernen!)
        SoundGroup.GLOBAL_SOUNDS.addSound(
                new StreamSound("/assets/sound/hallway.ogg", true, EndOfFileBehaviour.LOOP)
        );

        ExampleScene1 s1 = new ExampleScene1();
        s1.objects.add(ExamplePlayerObject.INSTANCE);
        SceneStack.INSTANCE.push(s1);

        // Haupt-Schleife in einem neuen Thread starten
        new Thread(INSTANCE).start();

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
                // Code für Szenen und Objekte ausführen
                SceneStack.INSTANCE.update();
                // Zuvor nur als KeyState.PRESSED eingetragene Tasten
                // jetzt als KeyState.HELD behandeln
                Input.INSTANCE.updatePressedToHeld();
                // Grafik
                SceneStack.INSTANCE.repaint();

                // Durchlauf abgeschlossen, Zähler kann um 1 verringert werden
                delta--;
            }

        }

    }
}
