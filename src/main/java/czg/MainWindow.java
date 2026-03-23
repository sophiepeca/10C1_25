package czg;

import czg.objects.PlayerObject;
import czg.scenes.FoyerScene;
import czg.scenes.SceneStack;
import czg.sound.EndOfFileBehaviour;
import czg.sound.SoundGroup;
import czg.sound.StreamSound;
import czg.util.Input;
import czg.util.character_creator.CharacterCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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

        // Key- und FocusListener müssen hier sein, warum auch immer.
        // Die anderen Listener sind beim SceneStack.
        addKeyListener(Input.INSTANCE);
        addFocusListener(Input.INSTANCE);

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

        // Musik
        StreamSound music = new StreamSound("/assets/sound/hallway.ogg", true, EndOfFileBehaviour.LOOP);
        music.getVolumeControl().setValue(-16f);
        SoundGroup.GLOBAL_SOUNDS.addSound(music);

        //Startszene
        FoyerScene foyer = new FoyerScene();
        SceneStack.INSTANCE.push(foyer);

        // Fenstergröße beheben
        SwingUtilities.invokeLater(() -> {
            Insets insets = INSTANCE.getInsets();
            INSTANCE.setSize(WIDTH+insets.left+insets.right, HEIGHT+insets.top+insets.bottom);
        });

        // Haupt-Schleife in einem neuen Thread starten
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

                // Spezielle Tasten
                if(Input.INSTANCE.getKeyState(KeyEvent.VK_P) == Input.KeyState.PRESSED)
                    CharacterCreator.INSTANCE.get().setVisible(true);
                if(Input.INSTANCE.getKeyState(KeyEvent.VK_M) == Input.KeyState.PRESSED) {
                    if(SoundGroup.GLOBAL_SOUNDS.isPlaying())
                        SoundGroup.GLOBAL_SOUNDS.pause();
                    else
                        SoundGroup.GLOBAL_SOUNDS.resume();
                }

                // Grafik
                SceneStack.INSTANCE.repaint();

                Input.INSTANCE.update();

                // Durchlauf abgeschlossen, Zähler kann um 1 verringert werden
                delta--;
            }

        }

    }
}
