package czg.objects.music_object;

import czg.objects.BaseObject;
import czg.scenes.BaseScene;
import czg.sound.BaseSound;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

    private static final Set<MusicLoopObject> instances = ConcurrentHashMap.newKeySet();


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
        if(instances.contains(this)) {
            throw new IllegalStateException("Zu einem bereits gestarteten MusicLoopObject können keine Segmente mehr hinzugefügt werden");
        }

        if(currentTrack == null) {
            currentTrack = sound;
            currentTrack.setPlaying(true);
        }

        segmentChangeMarkers.put(sound, marker);

        return this;
    }

    public MusicLoopObject start() {
        // Nichts weiter machen, wenn das Objekt bereits
        // in der Instanzen-Liste ist
        if(! instances.add(this))
            return this;

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
        instances.remove(this);
    }


    private static void segmentChangeThreadLoop() {
        while(true) {
            while(! instances.isEmpty()) {
                for(MusicLoopObject o : instances) {
                    SegmentChangeMarker marker = o.segmentChangeMarkers.get(o.currentTrack);

                    // Wenn die angegebene Zeit erreicht ist, wird das nächste
                    // Segment gestartet. Das aktuelle Segment könnte dabei noch
                    // weiter spielen.
                    if(o.currentTrack.getPosition() >= marker.time()) {
                        o.currentTrack = marker.next();
                        o.currentTrack.setPlaying(true);
                    }
                }
            }

            try {
                // Gibt es keine Objekte mehr zu verarbeiten, wartet der Thread
                synchronized (segmentChangeThread) {
                    segmentChangeThread.wait();
                }
            } catch (InterruptedException e) {
                System.err.println("Konnte nicht auf das Fortsetzungs-Signal für den "+segmentChangeThread.getName()+" warten: " + e);
            }
        }
    }
}
