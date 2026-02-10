package czg.scenes;

import czg.scenes.cover_settings.CoverSettings;
import czg.scenes.cover_settings.Rules;
import czg.scenes.cover_settings.Setting;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoverRulesTest {

    private final Rules base = new Rules(Setting.ON, Setting.OFF, Setting.ON);
    private final Rules add1 = new Rules(Setting.KEEP, Setting.ON, Setting.KEEP);
    private final Rules add2 = new Rules(Setting.ON, Setting.KEEP, Setting.OFF);
    private final CoverSettings coverSettings = new CoverSettings(
            base.coverDisablesDrawing().toBoolean(),
            base.coverPausesLogic().toBoolean(),
            base.coverPausesAudio().toBoolean()
    )
            .setRules(add1, "add1")
            .setRules(add2, "add2");

    @Test
    public void testManualRuleCombination() {
        assertEquals(
                new Rules(Setting.ON, Setting.ON, Setting.ON),
                base.combineWith(add1)
        );

        assertEquals(
                new Rules(Setting.ON, Setting.OFF, Setting.OFF),
                base.combineWith(add2)
        );

        assertEquals(
                new Rules(Setting.ON, Setting.ON, Setting.OFF),
                base.combineWith(add1).combineWith(add2)
        );

        assertEquals(
                new Rules(Setting.ON, Setting.ON, Setting.OFF),
                base.combineWith(add2).combineWith(add1)
        );

    }

    @Test
    public void testTagBasedRuleCombination() {
        assertEquals(base.combineWith(add1).combineWith(add2), coverSettings.getEffectiveRules(new LinkedHashSet<>(Set.of("add1","add2"))));

        assertEquals(base.combineWith(add2).combineWith(add1), coverSettings.getEffectiveRules(new LinkedHashSet<>(Set.of("add2","add1"))));
    }

}
