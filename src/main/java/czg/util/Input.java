package czg.util;

import czg.MainWindow;
import czg.scenes.SceneStack;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Zentraler zugriff auf gedrückte Tasten der Tastatur und Maus
 */
public class Input implements KeyListener, MouseListener, FocusListener {

    /**
     * Singleton der Klasse
     */
    public static final Input INSTANCE = new Input();
    /**
     * Soll nicht von außerhalb instanziiert werden
     */
    private Input() {}


    /**
     * Wie lange eine Taste gedrückt sein muss, um als {@link KeyState#HELD}
     * und nicht mehr als {@link KeyState#PRESSED} gesehen zu werden.
     */
    private static final long HELD_THRESHOLD = 50_000_000;

    /**
     * Ob Debugging-Informationen angezeigt werden sollen.
     * <br> {@code 0}: Nichts anzeigen
     * <br> {@code 1}: Szenen-Stapel in Textform anzeigen
     * <br> {@code 2}: Objekte mit Hitboxen und Namen anzeigen
     */
    public static int debugDrawMode = 0;

    /**
     * Zustände einer Taste
     */
    public enum KeyState {
        /**
         * Taste nicht gedrückt
         */
        NOT_PRESSED,
        /**
         * Taste seit diesem Frame gedrückt
         */
        PRESSED,
        /**
         * Taste seit mehr als einem Frame gedrückt
         */
        HELD;


        /**
         * Abfragen, ob eine Taste gedrückt ist, egal wie lange schon
         * @return Siehe Beschreibung
         */
        public boolean isDown() {
            return this == PRESSED || this == HELD;
        }

        /**
         * Von dem Zeitstempel, seit dem eine Taste gedrückt wurde, ihren {@link KeyState} ableiten
         * @param time Wert von {@link System#nanoTime()}, als die Taste gedrückt wurde
         * @return Entsprechenden {@link KeyState}
         */
        public static KeyState fromTimePressed(long time) {
            if(time == -1)
                return NOT_PRESSED;
            if(MainWindow.INSTANCE.TIME_AT_UPDATE_START - time >= HELD_THRESHOLD)
                return HELD;
            else
                return PRESSED;
        }
    }

    /**
     * Zustände der Tastatur-Tasten. {@code keycode -> value of System.nanoTime() when pressed}
     */
    private final Map<Integer, Long> keyStates = new ConcurrentHashMap<>();

    /**
     * Tasten, die am Ende dieses Frames als {@link KeyState#HELD} angesehen werden, da sie
     * in diesem Frame durch einen Aufruf von {@link #getKeyState(int)} als {@link KeyState#PRESSED}
     * befunden wurden
     */
    private final List<Integer> keyButtonsToUpdateToHeld = new ArrayList<>();

    /**
     * Zustände der Maus-Tasten. {@code keycode -> value of System.nanoTime() when pressed}
     */
    private final Map<Integer, Long> mouseStates = new ConcurrentHashMap<>();

    /**
     * Maustasten, die am Ende dieses Frames als {@link KeyState#HELD} angesehen werden, da sie
     * in diesem Frame durch einen Aufruf von {@link #getKeyState(int)} als {@link KeyState#PRESSED}
     * befunden wurden
     */
    private final List<Integer> mouseButtonsToUpdateToHeld = new ArrayList<>();

    /**
     * Maus-Position
     */
    private Point mousePosition = null;

    /**
     * Maus-Position im vorherigen Frame
     */
    private Point lastMousePosition = null;

    /**
     * Abfrage des Zustandes einer Tastatur-Taste
     * @param keyCode Siehe {@link KeyEvent}-Klasse. Z.B. {@link KeyEvent#VK_SPACE} für Leertaste.
     * @return Zustand der Taste. Siehe {@link KeyState}.
     */
    public KeyState getKeyState(int keyCode) {
        // Aus der Dauer, die die Taste gedrückt wurde, einen KeyState ableiten
        KeyState result = KeyState.fromTimePressed(keyStates.getOrDefault(keyCode, -1L));
        
        // Wenn die Taste PRESSED ist, wird sie ab dem nächsten Frame als HELD angesehen
        if(result == KeyState.PRESSED)
            keyButtonsToUpdateToHeld.add(keyCode);
        
        return result;
    }

    /**
     * Abfrage des Zustandes einer Maus-Taste
     * @param button Siehe {@link MouseEvent}-Klasse. Z.B. {@link MouseEvent#BUTTON1} für die linke Maustaste.
     * @return Zustand der Taste. Siehe {@link KeyState}.
     */
    public KeyState getMouseState(int button) {
        // Aus der Dauer, die die Maustaste gedrückt wurde, einen KeyState ableiten
        KeyState result = KeyState.fromTimePressed(mouseStates.getOrDefault(button, -1L));

        // Wenn die Maustaste PRESSED ist, wird sie ab dem nächsten Frame als HELD angesehen
        if(result == KeyState.PRESSED)
            mouseButtonsToUpdateToHeld.add(button);
        
        return result;
    }

    /**
     * Maus-Position abfragen. Gibt {@code null} zurück, wenn die Maus
     * noch nicht bewegt wurde.
     * @return Die Maus-Positon in Form eines {@link Point}
     */
    @Nullable
    public Point getMousePosition() {
        return mousePosition;
    }

    /**
     * Maus-Position im vorherigen Frame abfragen. Gibt eventuell {@code null} zurück.
     * @return Die Maus-Positon im vorherigen Frame in Form eines {@link Point}
     */
    @Nullable
    public Point getLastMousePosition() { return lastMousePosition; }

    /**
     * Siehe {@link #keyButtonsToUpdateToHeld}, {@link #getKeyState(int)}
     */
    public void update() {
        keyButtonsToUpdateToHeld.forEach(code -> keyStates.computeIfPresent(code, (k,v) -> MainWindow.INSTANCE.TIME_AT_UPDATE_START - HELD_THRESHOLD));
        mouseButtonsToUpdateToHeld.forEach(code -> mouseStates.computeIfPresent(code, (k,v) -> MainWindow.INSTANCE.TIME_AT_UPDATE_START - HELD_THRESHOLD));
        keyButtonsToUpdateToHeld.clear();
        mouseButtonsToUpdateToHeld.clear();

        lastMousePosition = mousePosition;
        mousePosition = SceneStack.INSTANCE.getMousePosition();
    }


    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keyStates.putIfAbsent(keyEvent.getKeyCode(), System.nanoTime());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mouseStates.putIfAbsent(mouseEvent.getButton(), System.nanoTime());
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mouseStates.remove(mouseEvent.getButton());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keyStates.remove(keyEvent.getKeyCode());
    }

    @Override
    public void focusLost(FocusEvent e) {
        // Beim Fokuswechsel sofort alle Tasten nicht mehr drücken
        keyStates.clear();
        mouseStates.clear();
    }


    // Nicht verwendet

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void focusGained(FocusEvent e) {

    }

}
