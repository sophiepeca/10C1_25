package czg.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static czg.sound.Sounds.TARGET_AUDIO_FORMAT;

/**
 * {@link BaseSound}-Implementierung mithilfe von {@link SourceDataLine} und {@link AudioInputStream}
 */
public class StreamSound extends BaseSound {

    /**
     * Buffer für den {@link AudioInputStream}
     */
    private static final byte[] buffer = new byte[TARGET_AUDIO_FORMAT.getFrameSize() * 128];

    /**
     * Separater Thread, der alle {@code StreamSound}-Instanzen verwaltet und abspielt
     */
    private static final Thread playbackThread = new Thread(StreamSound::playback);

    /**
     * Set mit allen {@code StreamSound}-Instanzen
     */
    private static final Set<StreamSound> playbackInstances = ConcurrentHashMap.newKeySet();


    /**
     * {@link SourceDataLine}, in welche die gelesenen Audiodaten geschrieben werden
     */
    private final SourceDataLine dataLine;

    /**
     * {@link AudioInputStream}, über den die Audiodatei gelesen wird
     */
    private AudioInputStream inStream;

    /**
     * Pfad zur Audiodatei
     */
    private final String audioFilePath;


    /**
     * Größe von {@link #inStream} in Bytes
     */
    private int size = -1;

    /**
     * Wie viele Bytes bereits vom Stream gelesen wurden
     */
    private long bytesRead = 0;

    /**
     * Zu welcher Position gespult werden soll. {@code -1} wenn nicht gespult werden
     * soll, {@code >= 0} andernfalls. Wird vom {@link #playbackThread} gelesen, welcher
     * dann ggf. die Position im {@link #inStream} ändert.
     */
    private final AtomicInteger seekTo = new AtomicInteger(-1);

    /**
     * Ob der Sound weiter spielen soll oder angehalten ist
     */
    private final AtomicBoolean isPlaying = new AtomicBoolean(true);

    /**
     * Öffnet den internen {@link AudioInputStream} für die angegebene Datei
     * @param audioPath Pfad zur Audio-Datei
     * @throws RuntimeException Wenn ein Fehler auftritt
     */
    public StreamSound(String audioPath) {
        // Dateipfad speichern
        audioFilePath = audioPath;

        try {
            // SourceDataLine erstellen
            dataLine = AudioSystem.getSourceDataLine(Sounds.TARGET_AUDIO_FORMAT);
            dataLine.open(TARGET_AUDIO_FORMAT);

            // Länge ermitteln
            // TODO: Am besten irgendwie die Längen aller Audio-Dateien im Vorfeld ermitteln?
            Clip clip = AudioSystem.getClip();
            clip.open(Sounds.getInputStream(audioFilePath));
            size = clip.getFrameLength() * TARGET_AUDIO_FORMAT.getFrameSize();
            clip.close();
            System.gc();
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }

        // Zum Set von StreamSounds hinzufügen
        playbackInstances.add(this);

        // Ggf. den Wiedergabe-Thread starten bzw. aufwecken
        if(! playbackThread.isAlive()) {
            playbackThread.start();
        }

        synchronized (playbackThread) {
            playbackThread.notify();
        }
    }

    @Override
    protected DataLine getLine() {
        return dataLine;
    }

    @Override
    public void setPlaying(boolean playing) {
        isPlaying.set(playing);
    }

    @Override
    public boolean isPlaying() {
        return isPlaying.get();
    }

    @Override
    public void seek(float position) {
        seekTo.set(Float.floatToIntBits(position));
    }

    /**
     * Funktion, die vom {@link #playbackThread} ausgeführt wird
     */
    private static void playback() {
        while(true) {
            while(!playbackInstances.isEmpty()) {
                for (StreamSound sound : playbackInstances) {
                    // Überspringen, wenn dieser StreamSound nicht abspielen soll
                    if (!sound.isPlaying()) {
                        if(sound.dataLine.isRunning()) sound.dataLine.stop();
                        continue;
                    }

                    try {
                        // Ggf. Stream öffnen
                        if (sound.inStream == null) {
                            sound.inStream = Sounds.getInputStream(sound.audioFilePath);
                            sound.bytesRead = 0;
                            sound.dataLine.start();
                        }

                        // Ggf. vor- oder zurückspulen
                        int seekToIntBits = sound.seekTo.getAndSet(-1);
                        if (seekToIntBits != -1) {
                            int seekToByte = (int) (Float.intBitsToFloat(seekToIntBits) * sound.size);

                            if (seekToByte > sound.bytesRead) {
                                // Einfach die entsprechende Anzahl Bytes überspringen
                                sound.inStream.skip(seekToByte - sound.bytesRead);
                            } else {
                                // Alten Stream schließen
                                sound.inStream.close();
                                // Neuen Stream erstellen
                                sound.inStream = Sounds.getInputStream(sound.audioFilePath);
                                // Entsprechende Anzahl an Bytes überspringen
                                sound.inStream.skip(seekToByte);
                            }
                        }

                        // Ggf. SourceDataLine starten
                        sound.dataLine.start();

                        // Einen Buffer voll Daten lesen
                        int bytesRead = sound.inStream.read(buffer, 0, buffer.length);

                        // Stream-Ende erreicht:
                        if (bytesRead == -1) {
                            // Wiedergabe stoppen
                            sound.setPlaying(false);

                            // bytesRead-Zähler wird *noch nicht* zurückgesetzt

                            // Stream schließen & aus dem StreamSound-Objekt entfernen
                            sound.inStream.close();
                            sound.inStream = null;
                        } else {
                            // Zähler erhöhen
                            sound.bytesRead += bytesRead;
                            // Daten in die DataLine schreiben
                            sound.dataLine.write(buffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        System.err.println("IOException für StreamSound: " + e);
                        sound.setPlaying(false);
                    }
                }
            }

            try {
                // Gibt es keine Sounds mehr zum Abspielen, wartet der Thread
                synchronized (playbackThread) {
                    playbackThread.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
