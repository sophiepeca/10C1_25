package czg.scenes.cover_settings;

/**
 * Regelsatz
 *
 * @param coverDisablesDrawing Ob die Szene ausgeblendet werden sollte, wenn sie verdeckt ist
 * @param coverPausesLogic     Ob die Szene noch ihren Code ausführen sollte, wenn sie verdeckt ist
 * @param coverPausesAudio     Ob die Szene ihre Musik oder Effekte pausieren sollte, wenn sie verdeckt ist
 */
public record Rules(Setting coverDisablesDrawing, Setting coverPausesLogic,
                    Setting coverPausesAudio) {

    /**
     * Ermittelt den Regelsatz, welcher entsteht, wenn {@code other} <b>über</b> diesen
     * angewendet wird.
     *
     * @param other Anderer Regelsatz
     * @return Kombinierter Regelsatz
     */
    public Rules combineWith(Rules other) {
        return new Rules(
                other.coverDisablesDrawing == Setting.KEEP ? coverDisablesDrawing : other.coverDisablesDrawing,
                other.coverPausesLogic == Setting.KEEP ? coverPausesLogic : other.coverPausesLogic,
                other.coverPausesAudio == Setting.KEEP ? coverPausesAudio : other.coverPausesAudio
        );
    }
}
