package czg.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
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
     * Wiedergabezustand
     */
    private boolean isPlaying;

    /**
     * Lädt die angegebene Datei
     * @param audioPath Pfad zur Audio-Datei
     * @throws RuntimeException Wenn ein Fehler auftritt
     */
    public ClipSound(String audioPath) {
        try {
            clip = AudioSystem.getClip();
            clip.open(Sounds.getInputStream(audioPath));
        } catch (IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected DataLine getLine() {
        return clip;
    }

    @Override
    public void setPlaying(boolean playing) {
        if(isPlaying()) {
            clip.start();
        } else {
            clip.stop();
        }
    }

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public void seek(float position) {
        clip.setMicrosecondPosition((long) (clip.getMicrosecondLength() * position));
    }
}
