package czg;

import czg.scenes.SceneStack;
import czg.scenes.intro.TitleScreenScene;
import czg.util.Console;
import czg.util.Input;
import czg.util.Sounds;
import czg.util.character_creator.CharacterCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        // Anti-Aliasing-Einstellung für Text vom System übernehmen
        System.setProperty("awt.useSystemAAFontSettings","on");

        // Fenster zeigen
        // In die Mitte des Bildschirms platzieren
        INSTANCE.setLocationRelativeTo(null);
        // Zeigen
        INSTANCE.setVisible(true);

        //Startszene
        SceneStack.INSTANCE.push(new TitleScreenScene());

        // Fenstergröße beheben
        SwingUtilities.invokeLater(() -> {
            Insets insets = INSTANCE.getInsets();
            INSTANCE.setSize(WIDTH+insets.left+insets.right, HEIGHT+insets.top+insets.bottom);
        });

        // Evtl. Befehlsskript lesen
        if(args.length == 1 && args[0].startsWith("@")) {
            try {
                Console.queue.addAll(Files.readAllLines(Path.of(args[0].substring(1))));
            } catch (IOException e) {
                System.err.println("Konsole: Konnte Skript nicht lesen!");
            }
        }

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
                if(CharacterCreator.enabled && Input.INSTANCE.getKeyState(KeyEvent.VK_P) == Input.KeyState.PRESSED)
                    CharacterCreator.INSTANCE.get().setVisible(true);
                if(Input.INSTANCE.getKeyState(KeyEvent.VK_M) == Input.KeyState.PRESSED) {
                    Sounds.HALLWAY_MUSIC.setPlaying(!Sounds.HALLWAY_MUSIC.isPlaying());
                }
                if(Input.INSTANCE.getKeyState(KeyEvent.VK_D) == Input.KeyState.PRESSED)
                    Input.debugDrawMode = (Input.debugDrawMode + 1)%3;
                if(Input.INSTANCE.getKeyState(KeyEvent.VK_C) == Input.KeyState.PRESSED) {
                    Console.queue.add(JOptionPane.showInputDialog(MainWindow.INSTANCE, "Console"));
                }

                // Ausstehende Befehle verarbeiten
                Console.processQueue();

                // Grafik
                SceneStack.INSTANCE.repaint();

                // Zusätzliche Aktualisierung
                // für die Eingabeverarbeitung
                Input.INSTANCE.update();

                // Durchlauf abgeschlossen, Zähler kann um 1 verringert werden
                delta--;
            }

        }

    }
}
