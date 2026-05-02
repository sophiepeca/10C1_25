package czg.objects.music_loop_object;

import czg.objects.BaseObject;
import czg.scenes.BaseScene;
import czg.sound.BaseSound;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Objekt zum nahtlosen Abspielen von Musik, die undefiniert oft
 * wiederholt werden soll. So kann zum Beispiel zuerst ein Intro
 * und danach wiederholt ein wiederholbarer Part gespielt werden.
 * Es ist auch möglich, das nächste Segment bereits zu starten,
 * während das aktuelle noch läuft, sodass sie ineinander übergehen.
 * <br> Ein {@link MusicLoopObject} hat keinen {@link BaseObject#sprite}
 * und seine Position und Größe sind irrelevant.
 */
public class MusicLoopObject extends BaseObject {

    private static final Thread segmentChangeThread = new Thread(MusicLoopObject::segmentChangeThreadLoop, "MusicLoopObject-SegmentChangeThread");

    private static final AtomicReference<MusicLoopObject> instance = new AtomicReference<>();


    /**
     * Speichert {@link SegmentChangeMarker}-Objekte, welche angeben,
     * wann das nächste Segment gespielt werden soll.
     */
    private final Map<BaseSound, SegmentChangeMarker> segmentChangeMarkers = new HashMap<>();

    /**
     * Aktuell spielendes Segment. Siehe {@link #update(BaseScene)}
     */
    private BaseSound currentTrack;

    /**
     * Ein neues {@link MusicLoopObject} erstellen. Siehe
     * {@link #addTrackSegment(BaseSound, SegmentChangeMarker)} zum
     * hinzufügen von Segmenten.
     */
    public MusicLoopObject() {
        super(null, 0, 0, 0, 0);
    }

    /**
     * Ein neues Segment hinzufügen. Das zuerst hinzugefügte
     * Segment wird automatisch abgespielt.
     * @param sound Sound
     * @param marker Information, wann das nächste Segment gestartet werden soll
     */
    public MusicLoopObject addTrackSegment(BaseSound sound, SegmentChangeMarker marker) {
        if(instance.get() != null)
            throw new IllegalStateException("Es kann nur ein MusicLoopObject auf einmal existieren!");

        if(currentTrack == null)
            currentTrack = sound;

        segmentChangeMarkers.put(sound, marker);

        return this;
    }

    public MusicLoopObject start() {
        if(instance.get() != null)
            throw new IllegalStateException("Es kann nur ein MusicLoopObject auf einmal existieren!");

        instance.set(this);

        // Erstes Segment starten
        currentTrack.setPlaying(true);

        // Ggf. den Thread starten bzw. aufwecken
        if (!segmentChangeThread.isAlive()) {
            segmentChangeThread.start();
        }

        synchronized (segmentChangeThread) {
            segmentChangeThread.notify();
        }

        return this;
    }

    @Override
    public void draw(Graphics2D g) {}

    @Override
    public void unload(BaseScene scene) {
        instance.set(null);
        segmentChangeThread.interrupt();
    }


    private static void segmentChangeThreadLoop() {
        MusicLoopObject currentInstance;
        while(true) {
            try {
                while((currentInstance = instance.get()) != null) {
                    SegmentChangeMarker marker = currentInstance.segmentChangeMarkers.get(currentInstance.currentTrack);
                    try {
                        Thread.sleep(marker.time());
                        currentInstance.currentTrack = marker.next();
                        currentInstance.currentTrack.setPlaying(true);
                    } catch (InterruptedException x) {
                        // sleep() wird ggf. mit interrupt() unterbrochen,
                        // wenn die Szene entladen wird
                        break;
                    }
                }

                // Gibt es gerade kein MusicLoopObject, wartet der Thread
                synchronized (segmentChangeThread) {
                    segmentChangeThread.wait();
                }
            } catch (InterruptedException e) {
                System.err.println("Konnte nicht auf das Fortsetzungs-Signal für den "+segmentChangeThread.getName()+" warten: " + e);
            }
        }
    }
}
