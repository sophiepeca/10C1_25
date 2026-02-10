package czg.scenes;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SceneStackCoveringTest extends BaseSceneStackTest {

        /*

        === Rules ===

        tag             disables...     drawing?    updating?       audio?

        (default)                       OFF         OFF             OFF
        fullscreen                      ON          ON              ON
        partially_covering              KEEP        ON              KEEP


        === Scene Stack ===

        #   name                        has tags                applies rules for tags (overlying tags)

        #2  partiallyCoveringScene      partially_covering
        #1  fullScreenScene             fullscreen              partially_covering
        #0  backgroundScene                                     fullscreen, partially_covering

         */


    @Test
    public void testOverlyingTags() {
        SceneStack.INSTANCE.push(backgroundScene);
        SceneStack.INSTANCE.push(fullScreenScene);
        SceneStack.INSTANCE.push(partiallyCoveringScene);

        assertEquals(new LinkedHashSet<>(List.of(fullscreenTag, partiallyCoveringTag)), SceneStack.INSTANCE.getOverlyingTags(0));
        assertEquals(new LinkedHashSet<>(List.of(partiallyCoveringTag)), SceneStack.INSTANCE.getOverlyingTags(1));
        assertEquals(new LinkedHashSet<>(), SceneStack.INSTANCE.getOverlyingTags(2));
    }

    @Test
    public void testEffectiveRules() {
        SceneStack.INSTANCE.push(backgroundScene);
        SceneStack.INSTANCE.push(fullScreenScene);
        SceneStack.INSTANCE.push(partiallyCoveringScene);

        SceneStack.INSTANCE.draw(null);
        SceneStack.INSTANCE.update();

        assertFalse(hasDrawn.getOrDefault(backgroundScene, false));
        assertTrue(hasDrawn.getOrDefault(fullScreenScene, false));
        assertTrue(hasDrawn.getOrDefault(partiallyCoveringScene, false));

        assertFalse(hasUpdated.getOrDefault(backgroundScene, false));
        assertFalse(hasUpdated.getOrDefault(fullScreenScene, false));
        assertTrue(hasUpdated.getOrDefault(partiallyCoveringScene, false));
    }

}
