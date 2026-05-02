package czg.scenes;

import czg.MainWindow;
import czg.objects.BackdropObject;
import czg.objects.BaseObject;
import czg.objects.ButtonObject;
import czg.util.Images;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static czg.MainWindow.*;

public class KampfEndScene extends BaseScene {

    public KampfEndScene(boolean playerWon, boolean allowRetry) {
        InventarScene.close();

        objects.add(new BackdropObject(Images.get("/assets/minigames/general/background_overlay.png")));

        BaseObject banner = new BaseObject(Images.get("/assets/minigames/general/banner_" + (playerWon ? "won" : "lost") + ".png"));

        banner.x = (WIDTH - banner.width) / 2;
        banner.y = (MainWindow.HEIGHT - banner.height) / 3;

        objects.add(banner);

        BaseObject exitButton = new ButtonObject(Images.get("/assets/minigames/general/button_exit.png"), this::exit);
        exitButton.y = HEIGHT / 2;
        objects.add(exitButton);

        if(! allowRetry) {
            exitButton.x = WIDTH / 2 - exitButton.width / 2;
        } else {
            BaseObject retryButton = new ButtonObject(Images.get("/assets/minigames/general/button_retry.png"),
                    MainWindow.WIDTH / 2 + MainWindow.PIXEL_SCALE * 3, MainWindow.HEIGHT / 2, this::retry);
            objects.add(retryButton);
            exitButton.x = WIDTH / 2 - PIXEL_SCALE * 3 - exitButton.width;
        }
    }

    private void exit() {
        SceneStack.INSTANCE.pop(); // this
        SceneStack.INSTANCE.pop(); // KampfScene

        if(KampfScene.uebrigeLehrer.isEmpty()) {
            SceneStack.INSTANCE.pop(); // Gang
            SceneStack.INSTANCE.push(new EndScene());
        } else {
            Class<? extends BaseScene> raumClass = SceneStack.INSTANCE.getTop().getClass();
            try {
                Constructor<?> constructor = raumClass.getConstructor();
                SceneStack.INSTANCE.replace(SceneStack.INSTANCE.getTop(), (BaseScene) constructor.newInstance());
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException("wir sind so cooked");
            }
        }
    }

    private void retry() {
        SceneStack.INSTANCE.pop();
        SceneStack.INSTANCE.pop();
        SceneStack.INSTANCE.push(new KampfScene(KampfScene.FACHSCHAFT));
    }

}
