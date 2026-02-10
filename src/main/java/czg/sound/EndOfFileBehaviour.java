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
        LOOP.function.accept(sound);
        sound.setPlaying(false);
    }),

    /**
     * Stoppen
     */
    STOP(BaseSound::stop);

    public final Consumer<BaseSound> function;

    EndOfFileBehaviour(Consumer<BaseSound> function) {
        this.function = function;
    }

}
