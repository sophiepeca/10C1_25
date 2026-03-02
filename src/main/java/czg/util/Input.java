package czg.util;

import czg.MainWindow;
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
public class Input implements KeyListener, MouseListener, MouseMotionListener, FocusListener {

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
    private static final long HELD_THRESHOLD = 100_000_000;

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

    private final List<Integer> keyButtonsToUpdateToHeld = new ArrayList<>();

    /**
     * Zustände der Maus-Tasten. {@code keycode -> value of System.nanoTime() when pressed}
     */
    private final Map<Integer, Long> mouseStates = new ConcurrentHashMap<>();

    private final List<Integer> mouseButtonsToUpdateToHeld = new ArrayList<>();

    /**
     * Maus-Position
     */
    private Point mousePosition = null;

    /**
     * Abfrage des Zustandes einer Tastatur-Taste
     * @param keyCode Siehe {@link KeyEvent}-Klasse. Z.B. {@link KeyEvent#VK_SPACE} für Leertaste.
     * @return Zustand der Taste. Siehe {@link KeyState}.
     */
    public KeyState getKeyState(int keyCode) {
        KeyState result = KeyState.fromTimePressed(keyStates.getOrDefault(keyCode, -1L));
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
        KeyState result = KeyState.fromTimePressed(mouseStates.getOrDefault(button, -1L));
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


    public void updateToHeld() {
        keyButtonsToUpdateToHeld.forEach(code -> keyStates.put(code, HELD_THRESHOLD));
        mouseButtonsToUpdateToHeld.forEach(code -> mouseStates.put(code, HELD_THRESHOLD));
        keyButtonsToUpdateToHeld.clear();
        mouseButtonsToUpdateToHeld.clear();
    }


    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keyStates.put(keyEvent.getKeyCode(), System.nanoTime());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mouseStates.put(mouseEvent.getButton(), System.nanoTime());
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

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = e.getPoint();
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
