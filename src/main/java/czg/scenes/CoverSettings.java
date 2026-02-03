package czg.scenes;

import java.util.*;

/**
 * Einstellungen für Szenen: <i>Was</i> soll aktiviert/deaktiviert werden,
 * wenn <i>welche</i> Szene(n) darüber liegen?
 */
public final class CoverSettings {

    /**
     * Wie viele Einträge {@link #effectiveRulesCache} maximal haben darf
     */
    private static final int MAX_CACHE_SIZE = 256;


    /**
     * Alle gespeicherten Regeln. Zuordnung {@code Tag -> Regeln}
     */
    private final Map<String, Rules> rules = new HashMap<>();

    /**
     * Cache für {@link #getEffectiveRules(SequencedSet)}
     */
    private final SequencedMap<Integer, Rules> effectiveRulesCache = new LinkedHashMap<>();

    /**
     * Standardeinstellungen
     */
    private final Rules defaultValues;


    /**
     * Neue Einstellungen erstellen und Standardwerte setzen
     * @param coverDisablesDrawing Siehe {@link Rules}
     * @param coverPausesLogic Siehe {@link Rules}
     * @param coverPausesAudio Siehe {@link Rules}
     */
    public CoverSettings(boolean coverDisablesDrawing, boolean coverPausesLogic, boolean coverPausesAudio) {
        defaultValues = new Rules(
                Setting.fromBoolean(coverDisablesDrawing),
                Setting.fromBoolean(coverPausesLogic),
                Setting.fromBoolean(coverPausesAudio)
        );
    }

    /**
     * Einen neuen Satz Regeln hinzufügen, welcher angewendet wird, wenn Szenen mit
     * den gegebenen Tags über dieser liegen
     * @param rules Regelsatz
     * @param tags Szenen-Tags, bei denen diese Einstellungen angewendet werden sollen
     * @return Das {@code CoverSettings}-Objekt selbst, sodass weitere {@code addRule()}-Aufrufe verkettet werden können
     */
    public CoverSettings addRules(Rules rules, String... tags) {
        // Regeln eintragen
        Arrays.stream(tags).forEach(tag -> this.rules.put(tag, rules));
        // Cache leeren, da sich der effektive Regelsatz verändert haben kann
        effectiveRulesCache.clear();
        // Das CoverSettings-Objekt zurückgeben, um verkettete addRules()-Aufrufe zu erlauben
        return this;
    }


    /**
     * Bestimmt den effektiven Regelsatz
     * @param tags Die Gesamtmenge der Tags aller Szenen, die über dieser liegen
     * @return Den effektiven Regelsatz
     */
    public Rules getEffectiveRules(SequencedSet<String> tags) {
        int cacheKey = Arrays.hashCode(tags.toArray(String[]::new));

        // Ggf. Cache-Eintrag zurückgeben
        if(effectiveRulesCache.containsKey(cacheKey)) {
            return effectiveRulesCache.get(cacheKey);
        }


        // Andernfalls Ergebnis ermitteln
        Rules result = defaultValues;

        for(String tag : tags) {
            Rules tagRules = rules.getOrDefault(tag, null);
            if(tagRules == null) continue;
            result = result.combineWith(tagRules);
        }


        // Cache-Eintrag hinzufügen
        effectiveRulesCache.put(cacheKey, result);
        // Cache-Größe einhalten
        while(effectiveRulesCache.size() > MAX_CACHE_SIZE) {
            effectiveRulesCache.pollFirstEntry();
        }

        return result;
    }

    /**
     * Regelsatz
     * @param coverDisablesDrawing Ob die Szene ausgeblendet werden sollte, wenn sie verdeckt ist
     * @param coverPausesLogic Ob die Szene noch ihren Code ausführen sollte, wenn sie verdeckt ist
     * @param coverPausesAudio Ob die Szene ihre Musik oder Effekte pausieren sollte, wenn sie verdeckt ist
     */
    public record Rules(Setting coverDisablesDrawing, Setting coverPausesLogic, Setting coverPausesAudio) {

        /**
         * Ermittelt den Regelsatz, welcher entsteht, wenn {@code other} <b>über</b> diesen
         * angewendet wird.
         * @param other Anderer Regelsatz
         * @return Kombinierter Regelsatz
         */
        public Rules combineWith(Rules other) {
            return new Rules(
                    other.coverDisablesDrawing == Setting.KEEP ? coverDisablesDrawing : other.coverDisablesDrawing,
                    other.coverPausesLogic == Setting.KEEP ? coverPausesLogic : other.coverPausesLogic,
                    other.coverPausesAudio == Setting.KEEP ? coverPausesAudio : other.coverPausesAudio
            );
        }
    }

    /**
     * Eine Einstellung für {@link Rules}
     */
    public enum Setting {
        /**
         * Einstellung behält ihren Standardwert bzw. ihren Wert von einer anderen Regel
         */
        KEEP,

        /**
         * Einstellung wird überschrieben und auf "An" gesetzt
         */
        ON,

        /**
         * Einstellung wird überschrieben und auf "An" gesetzt
         */
        OFF;

        /**
         * In einen Boolean-Wert umwandeln
         * @return {@code this == ON}
         */
        public boolean toBoolean() {
            return this == ON;
        }

        /**
         * Aus einem Boolean-Wert erstellen
         * @param value Boolean-Wert
         * @return {@link #ON}, wenn {@code true}, sonst {@code OFF}
         */
        public static Setting fromBoolean(boolean value) {
            return value ? ON : OFF;
        }
    }
}
