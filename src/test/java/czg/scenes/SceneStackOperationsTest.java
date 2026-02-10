package czg.scenes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SceneStackOperationsTest extends BaseSceneStackTest {

    public void commonSetup() {
        SceneStack.INSTANCE.push(backgroundScene);
        SceneStack.INSTANCE.push(partiallyCoveringScene);

        assertFalse(hasUnloaded.getOrDefault(backgroundScene, false));
        assertFalse(hasUnloaded.getOrDefault(partiallyCoveringScene, false));

        assertEquals(0, backgroundScene.sceneStackPosition);
        assertEquals(1, partiallyCoveringScene.sceneStackPosition);
    }

    @Test
    public void testUnloadingAndPositionWhenRemoving() {
        commonSetup();

        SceneStack.INSTANCE.remove(backgroundScene);

        assertTrue(hasUnloaded.getOrDefault(backgroundScene, false));
        assertFalse(hasUnloaded.getOrDefault(partiallyCoveringScene, false));

    }

    @Test
    public void testUnloadingWhenReplacing() {
        commonSetup();

        SceneStack.INSTANCE.replace(partiallyCoveringScene, fullScreenScene);

        assertFalse(hasUnloaded.getOrDefault(backgroundScene, false));
        assertTrue(hasUnloaded.getOrDefault(partiallyCoveringScene, false));
    }

    @Test
    public void testUnloadingWhenPopping() {
        commonSetup();

        SceneStack.INSTANCE.pop();

        assertFalse(hasUnloaded.getOrDefault(backgroundScene, false));
        assertTrue(hasUnloaded.getOrDefault(partiallyCoveringScene, false));
    }

    @Test
    public void testCoveringStatus() {
        SceneStack.INSTANCE.push(backgroundScene);
        assertFalse(backgroundScene.isCovered());

        SceneStack.INSTANCE.push(fullScreenScene);
        assertTrue(backgroundScene.isCovered());
        assertFalse(fullScreenScene.isCovered());

        SceneStack.INSTANCE.push(partiallyCoveringScene);
        assertTrue(backgroundScene.isCovered());
        assertTrue(fullScreenScene.isCovered());
        assertFalse(partiallyCoveringScene.isCovered());

        SceneStack.INSTANCE.pop();
        assertTrue(backgroundScene.isCovered());
        assertFalse(fullScreenScene.isCovered());
    }

}
