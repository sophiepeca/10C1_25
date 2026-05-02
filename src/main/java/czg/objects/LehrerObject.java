package czg.objects;

import czg.scenes.BaseScene;
import czg.scenes.KampfScene;
import czg.scenes.SceneStack;
import czg.util.Draw;
import czg.util.Images;

import java.awt.*;
import java.util.List;
import java.util.Random;

import static czg.MainWindow.FPS;

public class LehrerObject extends BaseObject{

    public final Department fachschaft;
    public static List<ItemType> lehrerItems;
    private int delayTimer = 0;
    private Runnable postDelay = null;

    public LehrerObject(int x, int y, Department fachschaft) {
        super(getImage(fachschaft), x, y);
        this.fachschaft = fachschaft;
        lehrerItems = ItemType.getItems(fachschaft);
    }

    // Je nach dem welcher Fachschaft der Lehrer angehört, kriegt er sein richtiges Bild.
    public static Image getImage(Department fachschaft) { // ordnet den Fachschaften die Lehrer mit Bild zu
        if (fachschaft == Department.COMPUTER_SCIENCE) {

            return Images.get("/assets/characters/bre.png");

        } else if (fachschaft == Department.PHYSICS) {

            return Images.get("/assets/characters/tno.png");

        } else if (fachschaft == Department.MATHEMATICS) {

            return Images.get("/assets/characters/gei.png");

        } else if (fachschaft == Department.BIOLOGY) {

            return Images.get("/assets/characters/kho.png");

        } else if (fachschaft == Department.CHEMISTRY) {

            return Images.get("/assets/characters/kko.png");
        }

        throw new IllegalArgumentException("Konnte der Fachschaft "+fachschaft+" kein Foto zuordnen!");
    }

    // Der Lehrer in den normalen Räumen ist nur ein Button. Dieser wird hier gebaut.
    public static void addButtonObject(BaseScene scene, Department department, int x, int y) {
        if(KampfScene.uebrigeLehrer.contains(department)) {
            ButtonObject button = new ButtonObject(LehrerObject.getImage(department),
                    () -> {
                        if(PlayerObject.INSTANCE.inventar.isEmpty())
                            return;

                        SceneStack.INSTANCE.push(new KampfScene(department));
                        PlayerObject.INSTANCE.allowInventory = false;
                    });
            button.x = x;
            button.y = y;
            button.includeTransparency = false;
            scene.objects.add(button);
        }
    }

    // Für den Fall, dass der Lehrer sich verteidigen muss.
    public int verteidigung(int zwischenSchaden) {
        // Es wird random ausgewählt, ob ein Item gewählt wird (und welches), oder ob der Lehrer nichts macht.
        Random rand = new Random();
        int move = rand.nextInt(7);
        int schaden;
        ItemType itemLehrer;

        // Falls die Null genommen wurde, wird kein Item vom Lehrer benutzt.
        if (move == 0) {
            schaden = zwischenSchaden;
        }
        // Sonst wird der Schaden abgezogen und ggf. die Leben des Lehrers dekrementiert.
        else {
            itemLehrer = lehrerItems.get(move - 1);
            displayItem(itemLehrer);
            schaden = zwischenSchaden - (itemLehrer.LEVEL + 1);
            if (schaden <= 0) {
                schaden = 0;
            }

        }

        return schaden;

    }

    // Falls der Lehrer den Player angreift (würde er natürlich nie...)
    public int angriff() {
        // Der Lehrer wählt random, welches der Items er zum Angreifen benutzt.
        Random rand = new Random();
        int move = rand.nextInt(6);
        ItemType itemLehrer;

        // Das Item wird angezeigt
        itemLehrer = lehrerItems.get(move);
        displayItem(itemLehrer);

        // Es wird der vom Item verursachte Schaden zurück gegeben.
        return itemLehrer.LEVEL + 1;
    }

    // Eine Funktion, um ein Item an der richtigen Stelle angezeigt zu kriegen.
    public void displayItem(ItemType type) {
        KampfScene.instance.objects.remove(KampfScene.currentItem);
        if(type != null) {
            KampfScene.currentItem = new ItemObject(type, 0, x - width, y + height / 2);
            KampfScene.currentItem.y -= KampfScene.currentItem.height / 2;
            KampfScene.instance.objects.add(KampfScene.currentItem);
        } else {
            KampfScene.currentItem = null;
        }
    }

    @Override
    public void update(BaseScene scene) {
        super.update(scene);

        // Der Timer, um das Spielgeschehen zu entschleunigen, wird hier dekrementiert.
        if(delayTimer > 0) {
            delayTimer--;
            return;
        }

        if(postDelay != null) {
            Runnable ogPostDelay = postDelay;
            postDelay.run();

            // Nicht auf null setzen, wenn ein neues
            // postDelay erstellt wurde
            if(postDelay == ogPostDelay)
                postDelay = null;

            return;
        }

        // Hier verteidigt sich der Lehrer
        if(KampfScene.turn == KampfScene.Turn.LEHRER_DEFEND) {
            KampfScene.Endschaden = verteidigung(KampfScene.Zwischenschaden);

            if(KampfScene.Zwischenschaden == KampfScene.Endschaden) {
                // Wenn kein Item eingesetzt wurde, Leben jetzt schon anzeigen und 2s bis zum Angriff warten
                KampfScene.LehrerLeben -= KampfScene.Endschaden;
                delayTimer = FPS * 2;
                postDelay = () -> KampfScene.turn = KampfScene.Turn.LEHRER_ATTACK;
            } else {
                // Es wurde ein Item eingesetzt
                delayTimer = FPS * 2;
                postDelay = () -> {
                    displayItem(null);
                    KampfScene.LehrerLeben -= KampfScene.Endschaden;

                    // Noch 2s warten
                    delayTimer = FPS * 2;
                    postDelay = () -> KampfScene.turn = KampfScene.Turn.LEHRER_ATTACK;
                };
            }
        }

        // Falls der Lehrer am angreifen ist.
        else if(KampfScene.turn == KampfScene.Turn.LEHRER_ATTACK) {
            KampfScene.Zwischenschaden = angriff();

            // Der Schüler kriegt 4s Zeit zum verteidigen.
            KampfScene.timer = 4 * FPS;
            KampfScene.turn = KampfScene.Turn.PLAYER_DEFEND;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        g.setFont(Draw.FONT_INFO);
        if(KampfScene.imKampf) {
            boolean attack = (KampfScene.turn == KampfScene.Turn.PLAYER_DEFEND || KampfScene.turn == KampfScene.Turn.LEHRER_ATTACK);

            // Je nach dem, wie die Situation ist, steht unter dem Lehrer Angriff oder Verteidigung.
            String text = attack ? "ANGRIFF" : "VERTEIDIGUNG";
            g.setColor(attack ? new Color(223, 52, 22) : new Color(83, 159, 234));
            Draw.drawTextCentered(g, text, x + width / 2, y + height + 5, true);

            // Hiermit werden die übrigen Leben des Lehrers angezeigt.
            g.setColor(Color.WHITE);
            Draw.drawTextCentered(g, "HP: "+(KampfScene.LehrerLeben > 0 ? KampfScene.LehrerLeben : "--"), x + width  / 2, y + height - 20, true);
        }
    }
}
