package czg.sound;

import java.util.function.Consumer;

public enum EndOfFileBehaviour {

    /**
     * Von Anfang an abspielen
     */
    LOOP(sound -> sound.seek(0)),

    /**
     * Zurück zum Anfang spulen und pausieren
     */
    RESTART_AND_PAUSE(sound -> {
        sound.setPlaying(false);
        LOOP.function.accept(sound);
    }),

    /**
     * Stoppen
     */
    STOP(BaseSound::stop);

    /**
     * Auszuführende Funktion
     */
    public final Consumer<BaseSound> function;

    EndOfFileBehaviour(Consumer<BaseSound> function) {
        this.function = function;
    }

}
