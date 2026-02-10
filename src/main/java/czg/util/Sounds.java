package czg.util;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;

/**
 * Statische Methoden und Konstanten für Sounds
 */
public class Sounds {

    /**
     * Ausgabeformat für Ton. Muss von Hand angegeben werden, weil Java das Format
     * von alleine nicht erkennt. (Warum auch immer)
     */
    public static final AudioFormat TARGET_AUDIO_FORMAT = new AudioFormat(
            48000f, // Sample rate
            16, // Sample size in bits
            2,  // Channels
            true,   // Signed
            true    // Big endian
    );

    /**
     * @param audioPath Pfad zu der Audio-Datei. Format siehe {@link czg.util.Images#get}.
     * @return Einen {@link AudioInputStream} im {@link #TARGET_AUDIO_FORMAT}
     * @throws RuntimeException Wenn ein Fehler auftritt
     */
    public static AudioInputStream getInputStream(String audioPath) {
        try {
            return AudioSystem.getAudioInputStream(
                    TARGET_AUDIO_FORMAT,
                    AudioSystem.getAudioInputStream(Objects.requireNonNull(Sounds.class.getResource(audioPath)))
            );
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
