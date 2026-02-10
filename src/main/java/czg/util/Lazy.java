package czg.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Speichert einen Wert, der erst bei der ersten Verwendung
 * generiert und dann gespeichert und wiederverwendet wird.
 * @param <T> Datentyp
 */
public class Lazy<T> {

    /**
     * Funktion, die den Wert erstellt
     */
    private final Supplier<T> supplier;

    /**
     * Cache für den erstellten Wert
     */
    private T value;

    /**
     * Ob der Wert bereits erstellt wurde. Es könnte stattdessen auch
     * {@code value == null} verwendet werden, allerdings ist dann {@code null}
     * kein akzeptierter Rückgabewert des {@link #supplier}s.
     */
    private boolean hasValue = false;

    /**
     * Lazy-Objekt erstellen
     * @param supplier Liefert den Wert, wenn er gebraucht wird
     */
    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Generiert ggf. den Wert und gibt ihn zurück
     * @return Den Wert
     */
    public T get() {
        if(hasValue)
            return value;
        else {
            hasValue = true;
            return value = supplier.get();
        }
    }

    /**
     * Gibt den generierten Wert zurück, falls vorhanden, andernfalls
     * den gegebenen Standardwert.
     * @param value Standardwert
     * @return Generierter Wert oder Standardwert
     */
    public T getOrDefault(T value) {
        return hasValue ? this.value : value;
    }

    /**
     * Führt eine Funktion aus, wenn bereits ein Wert generiert wurde
     * @param function Die Funktion
     */
    public void ifPresent(Consumer<T> function) {
        if(hasValue)
            function.accept(value);
    }

}
