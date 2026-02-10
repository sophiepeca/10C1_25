package czg.sound;

import czg.util.Sounds;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * {@link BaseSound}-Implementierung mithilfe von {@link Clip}
 */
public class ClipSound extends BaseSound {

    /**
     * Der als Backend verwendete {@code Clip}
     */
    private final Clip clip;

    /**
     * Pfad zur Audio-Datei. Wird von {@link #toString()} verwendet.
     */
    private final String audioFilePath;

    /**
     * Lädt die angegebene Datei
     * @param audioPath Pfad zur Audio-Datei
     * @throws RuntimeException Wenn ein Fehler auftritt
     */
    public ClipSound(String audioPath, boolean autoPlay, EndOfFileBehaviour endOfFileBehaviour) {
        audioFilePath = audioPath;
        try {
            clip = AudioSystem.getClip();
            clip.addLineListener(this::lineListener);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        if(autoPlay)
            setPlaying(true);

        setEndOfFileBehaviour(endOfFileBehaviour);
    }

    @Override
    protected DataLine getLine() {
        return clip;
    }

    @Override
    protected void setPlayingActual(boolean playing) {
        if(playing) {
            // Daten laden, wenn noch nicht geschehen
            try {
                if (!clip.isOpen())
                    clip.open(Sounds.getInputStream(audioFilePath));
            } catch (IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }

            clip.start();
        } else {
            clip.stop();
        }
    }

    @Override
    protected boolean isPlayingActual() {
        return clip.isActive();
    }

    @Override
    protected void seekActual(float position) {
        clip.setMicrosecondPosition((long) (clip.getMicrosecondLength() * position));
    }

    @Override
    public void setEndOfFileBehaviour(EndOfFileBehaviour behaviour) {
        // loop()-Methode verwenden
        if(behaviour == EndOfFileBehaviour.LOOP) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            endOfFileBehaviour = null;
            return;
        }

        // Eingebaute loop()-Methode deaktivieren
        clip.loop(0);

        // Variable setzen & damit an lineListener() übergeben
        endOfFileBehaviour = behaviour;
    }

    /**
     * {@link LineListener}, der verwendet wird, wenn {@link #endOfFileBehaviour} nicht {@link EndOfFileBehaviour#LOOP} ist.
     * @param event {@link LineEvent}
     */
    private void lineListener(LineEvent event) {
        if(endOfFileBehaviour != null && event.getType() == LineEvent.Type.STOP) {
            endOfFileBehaviour.function.accept(this);
        }
    }

    @Override
    public String toString() {
        return getClass().getTypeName()+"["+audioFilePath+"]";
    }
}
