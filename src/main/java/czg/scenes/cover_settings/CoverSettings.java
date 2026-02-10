package czg.scenes.cover_settings;

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
    private final SequencedMap<SequencedSet<String>, Rules> effectiveRulesCache = new LinkedHashMap<>();

    /**
     * Standardeinstellungen
     */
    private final Rules defaultValues;


    /**
     * Neue Einstellungen erstellen mit allen Standardwerten auf {@code false}
     */
    public CoverSettings() {
        this(false, false, false);
    }

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
    public CoverSettings setRules(Rules rules, String... tags) {
        // Regeln eintragen
        Arrays.stream(tags).forEach(tag -> this.rules.put(tag, rules));
        // Alle Cache-Einträge löschen, in deren Tag-Kombination einer der gegebenen Tags vorkommt
        Set<String> tagSet = Set.of(tags);
        effectiveRulesCache.keySet().stream()
                .filter(cachedTagSet -> cachedTagSet.stream().anyMatch(tagSet::contains))
                .toList()
                .forEach(effectiveRulesCache::remove);
        // Das CoverSettings-Objekt zurückgeben, um verkettete setRules()-Aufrufe zu erlauben
        return this;
    }


    /**
     * Bestimmt den effektiven Regelsatz
     * @param tags Die Gesamtmenge der Tags aller Szenen, die über dieser liegen
     * @return Den effektiven Regelsatz
     */
    public Rules getEffectiveRules(SequencedSet<String> tags) {
        // Ggf. Cache-Eintrag zurückgeben
        if(effectiveRulesCache.containsKey(tags)) {
            return effectiveRulesCache.get(tags);
        }


        // Andernfalls Ergebnis ermitteln
        Rules result = defaultValues;

        for(String tag : tags) {
            Rules tagRules = rules.getOrDefault(tag, null);
            if(tagRules == null) continue;
            result = result.combineWith(tagRules);
        }


        // Cache-Eintrag hinzufügen
        effectiveRulesCache.put(tags, result);
        // Cache-Größe einhalten
        while(effectiveRulesCache.size() > MAX_CACHE_SIZE) {
            effectiveRulesCache.pollFirstEntry();
        }

        return result;
    }

}
