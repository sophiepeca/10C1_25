package czg.sound;

import javax.sound.sampled.*;

/**
 * Abstrakte Grundklasse für Sounds
 */
public abstract class BaseSound {

    /**
     * Ob der Sound gestoppt wurde
     */
    protected boolean isStopped = false;

    /**
     * Regelt, was passieren soll, wenn die Datei fertig abgespielt ist
     */
    protected EndOfFileBehaviour endOfFileBehaviour = EndOfFileBehaviour.RESTART_AND_PAUSE;

    /**
     * Zugriff auf die verwendete {@link DataLine}, z.B. ein {@link Clip}
     * oder eine {@link SourceDataLine}.
     * @return Die von diesem Sound verwendete {@link DataLine}
     */
    protected abstract DataLine getLine();

    /**
     * Steuert die Wiedergabe des Sounds
     * @param playing Ob der Sound angehalten oder abgespielt werden soll
     */
    public void setPlaying(boolean playing) {
        if(isStopped()) {
            System.err.println("Kann bereits gestoppten Sound nicht anhalten/fortsetzen: "+this);
            return;
        }

        setPlayingActual(playing);
    }

    /**
     * Eigentliche Implementierung; {@link #setPlaying(boolean)} ist nur ein Wrapper, der
     * {@link #isStopped()} überprüft.
     * @param playing Ob der Sound angehalten oder abgespielt werden soll
     */
    protected abstract void setPlayingActual(boolean playing);

    /**
     * Abfragen, ob dieser Sound gerade abgespielt wird
     * @return Ob der Sound abspielt oder angehalten wurde
     */
    public boolean isPlaying() {
        if(isStopped()) {
            System.err.println("Achtung: isPlaying()-Aufruf bei bereits gestopptem Sound: "+this);
            return false;
        }

        return isPlayingActual();
    }

    /**
     * Eigentliche Implementierung; {@link #isPlaying()} ist nur ein Wrapper, der
     * {@link #isStopped()} überprüft.
     * @return Ob der Sound abspielt oder angehalten wurde
     */
    protected abstract boolean isPlayingActual();

    /**
     * Spult zu der angegebenen Position vor oder zurück
     * @param position Position zwischen 0 (Anfang) und 1 (Ende)
     */
    public void seek(float position) {
        if(isStopped()) {
            System.err.println("Kann bereits gestoppten Sound nicht vor-/zurückspulen: "+this);
            return;
        }

        seekActual(position);
    }

    /**
     * Eigentliche Implementierung; {@link #seek(float)} ist nur ein Wrapper, der
     * {@link #isStopped()} überprüft.
     * @param position Position zwischen 0 (Anfang) und 1 (Ende)
     */
    protected abstract void seekActual(float position);

    /**
     * Stoppt den Sound. <b>Danach kann er nicht mehr verwendet werden.</b>
     */
    public void stop() {
        if(isStopped) {
            System.err.println("Sound bereits gestoppt: "+this);
            return;
        }

        setPlaying(false);
        isStopped = true;
        getLine().close();
    }

    /**
     * Abfragen, ob dieser Sound bereits endgültig gestoppt wurde
     * @return Ob {@link #stop()} bereits aufgerufen wurde
     */
    public boolean isStopped() {
        return isStopped;
    }

    /**
     * Ändert, was passieren soll, wenn das Ende der Sound-Datei erreicht ist.
     * @implNote Die {@link EndOfFileBehaviour#function} der Enum-Konstante muss nicht unbedingt verwendet werden!
     * @param behaviour Enum-Konstante
     */
    public void setEndOfFileBehaviour(EndOfFileBehaviour behaviour) {
        endOfFileBehaviour = behaviour;
    }

    /**
     * Schneller Zugriff auf die Lautstärkesteuerung
     * @return Lautstärkesteuerung
     */
    public FloatControl getVolumeControl() {
        return (FloatControl) getLine().getControl(FloatControl.Type.VOLUME);
    }

    /**
     * Schneller Zugriff auf die Pan-Steuerung
     * @return Pan-Steuerung
     */
    public FloatControl getPanControl() {
        return (FloatControl) getLine().getControl(FloatControl.Type.PAN);
    }

    /**
     * Schneller Zugriff auf die Stummschaltung
     * @return Stummschaltungssteuerung
     */
    public BooleanControl getMuteControl() {
        return (BooleanControl) getLine().getControl(BooleanControl.Type.MUTE);
    }

    @Override
    public abstract String toString();

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
