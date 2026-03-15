package czg.sound;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SoundGroup {

    /**
     * Globale Sound-Gruppe
     */
    public static final SoundGroup GLOBAL_SOUNDS = new SoundGroup();


    /**
     * Wird von {@link #pause()} befüllt und von {@link #resume()} ausgelesen.
     * Dient gleichzeitig als Liste aller Sounds in der Gruppe.
     */
    private final Map<BaseSound, Boolean> soundsAndResumePlaybackStates = new HashMap<>();

    /**
     * Ob die Sounds gerade pausiert sind
     */
    private boolean paused = false;

    /**
     * Einen neuen Sound zur Gruppe hinzufügen. Tut nichts, wenn der Sound
     * bereits in der Gruppe ist.
     * @param sound Sound-Objekt
     * @return Das übergebene Sound-Objekt
     */
    public BaseSound addSound(BaseSound sound) {
        if(sound.isStopped) {
            System.err.println("Kann gestoppten Sound nicht zur Szene hinzufügen: "+sound);
            return sound;
        }

        // Ggf. aus der vorherigen SoundGroup entfernen
        if(sound.soundGroup != null && sound.soundGroup != this) {
            System.err.println("Sound "+sound+" befindet sich bereits in einer SoundGroup und wird jetzt von dieser in "+this+" verschoben");
            sound.soundGroup.soundsAndResumePlaybackStates.remove(sound);
        }

        sound.soundGroup = this;

        soundsAndResumePlaybackStates.putIfAbsent(sound, true);

        return sound;
    }

    /**
     * Fügt mehrere Sounds auf einmal zur Gruppe hinzu
     * @param sounds {@link Set} von Sounds.
     */
    public void addAllSounds(Set<BaseSound> sounds) {
        sounds.forEach(this::addSound);
    }

    /**
     * {@link BaseSound#stop()}t einen Sound, wenn gewünscht, und entfernt ihn aus der Sound-Gruppe
     * @param sound Der zu entfernende Sound
     */
    public void removeSound(BaseSound sound, boolean stop) {
        if(! soundsAndResumePlaybackStates.containsKey(sound)) {
            System.err.println("Kann nicht aus der Sound-Gruppe entfernt werden (nicht enthalten): "+sound);
            return;
        }

        if(! sound.isStopped()) {
            if(stop) {
                // Wenn der Sound nicht gestoppt ist, es
                // aber werden soll, geschieht dies hier
                sound.stop();
            } else if(! isPlaying()) {
                // Wenn der Sound nicht gestoppt werden soll und
                // die Gruppe gerade pausiert ist, wird der Wieder-
                // gabezustand wiederhergestellt
                sound.setPlaying(soundsAndResumePlaybackStates.get(sound));
            }
        }

        soundsAndResumePlaybackStates.remove(sound);
        sound.soundGroup = null;
    }


    /**
     * Stoppt alle Sounds und entfernt sie aus der Gruppe
     */
    public void removeAndStopAllSounds() {
        // Alle Sounds stoppen. Sie entfernen sich dann auch selbst
        // aus der Gruppe, setzen also ihr soundGroup-Feld auf null
        // und werden aus der soundsAndResumePlaybackStates-Map gelöscht.
        Set.copyOf(soundsAndResumePlaybackStates.keySet()).forEach(BaseSound::stop);
    }

    /**
     * Pausiert alle Sounds
     */
    public void pause() {
        if(paused)
            return;

        for(BaseSound sound : Set.copyOf(soundsAndResumePlaybackStates.keySet())) {
            soundsAndResumePlaybackStates.put(sound, sound.isPlaying());
            sound.setPlaying(false);
        }

        paused = true;
    }

    /**
     * Setzt die Wiedergabezustände aller Sounds so zurück,
     * wie sie vor dem Aufruf von {@link #pause()} waren
     */
    public void resume() {
        if(! paused)
            return;

        for(BaseSound sound : soundsAndResumePlaybackStates.keySet()) {
            sound.setPlaying(soundsAndResumePlaybackStates.get(sound));
        }

        paused = false;
    }

    /**
     * Abfragen, ob die Sound-Gruppe pausiert ist
     * @return Ob die Sound-Gruppe pausiert ist
     */
    public boolean isPlaying() {
        return ! paused;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
