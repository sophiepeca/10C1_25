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
        sceneStack.push(backgroundScene);
        sceneStack.push(fullScreenScene);
        sceneStack.push(partiallyCoveringScene);

        assertEquals(new LinkedHashSet<>(List.of(fullscreenTag, partiallyCoveringTag)), sceneStack.getOverlyingTags(0));
        assertEquals(new LinkedHashSet<>(List.of(partiallyCoveringTag)), sceneStack.getOverlyingTags(1));
        assertEquals(new LinkedHashSet<>(), sceneStack.getOverlyingTags(2));
    }

    @Test
    public void testEffectiveRules() {
        sceneStack.push(backgroundScene);
        sceneStack.push(fullScreenScene);
        sceneStack.push(partiallyCoveringScene);

        sceneStack.draw(null);
        sceneStack.update();

        assertFalse(hasDrawn.getOrDefault(backgroundScene, false));
        assertTrue(hasDrawn.getOrDefault(fullScreenScene, false));
        assertTrue(hasDrawn.getOrDefault(partiallyCoveringScene, false));

        assertFalse(hasUpdated.getOrDefault(backgroundScene, false));
        assertFalse(hasUpdated.getOrDefault(fullScreenScene, false));
        assertTrue(hasUpdated.getOrDefault(partiallyCoveringScene, false));
    }

}
