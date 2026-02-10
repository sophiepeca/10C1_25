package czg.scenes.cover_settings;

/**
 * Eine Einstellung für {@link Rules}
 */
public enum Setting {
    /**
     * Einstellung behält ihren Standardwert bzw. ihren Wert von einer anderen Regel
     */
    KEEP,

    /**
     * Einstellung wird überschrieben und auf "An" gesetzt
     */
    ON,

    /**
     * Einstellung wird überschrieben und auf "An" gesetzt
     */
    OFF;

    /**
     * In einen Boolean-Wert umwandeln
     *
     * @return {@code this == ON}
     */
    public boolean toBoolean() {
        return this == ON;
    }

    /**
     * Aus einem Boolean-Wert erstellen
     *
     * @param value Boolean-Wert
     * @return {@link #ON}, wenn {@code true}, sonst {@code OFF}
     */
    public static Setting fromBoolean(boolean value) {
        return value ? ON : OFF;
    }
}
