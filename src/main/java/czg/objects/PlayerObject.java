package czg.objects;

import czg.scenes.BaseScene;
import czg.scenes.InventarScene;
import czg.scenes.KampfScene;
import czg.util.Capsule;
import czg.util.Draw;
import czg.util.Images;
import czg.util.character_creator.SaveFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import static czg.MainWindow.FPS;
import static czg.MainWindow.PIXEL_SCALE;

/**
 * Die Spielfigur
 */
public class PlayerObject extends BaseObject{

    //Anlegen einer Reihung "Inventar", in welchem die Items, auf die der Spieler zugreifen kann, gespeichert werden
    public final LinkedHashMap<ItemType,Integer> inventar = new LinkedHashMap<>();

    // Ob das Inventar geöffnet werden darf
    public boolean allowInventory = true;

    // Standardfarben
    public static final SaveFile defaultColors = new SaveFile(
            new Color(57, 37, 35),  // Haare: Braun
            new Color(241, 241, 44),// Haut: Gelb
            new Color(45, 96, 145), // Hoodie: Blau
            new Color(97, 105, 112) // Hose: Grau
    );

    /**
     * Farbe der Haare
     */
    public final Capsule<Color> haare = new Capsule<>(defaultColors.haare());
    /**
     * Farbe des Hoodies
     */
    public final Capsule<Color> hoodie = new Capsule<>(defaultColors.hoodie());
    /**
     * Farbe der Hose
     */
    public final Capsule<Color> hose = new Capsule<>(defaultColors.hose());
    /**
     * Farbe der Haut
     */
    public final Capsule<Color> haut = new Capsule<>(defaultColors.haut());

    /**
     * Zuordnung [Farbe im Original-Sprite] → [Speicherort der Farbe, durch die sie ersetzt werden soll]
     */
    private static final Map<Integer, Function<PlayerObject,Capsule<Color>>> spriteColorMap = new HashMap<>();
    static {
        spriteColorMap.put(0xFF75FB4C, p->p.haare);
        spriteColorMap.put(0xFFEA33F7, p->p.hoodie);
        spriteColorMap.put(0xFF0000F5, p->p.hose);
        spriteColorMap.put(0xFFEA3323, p->p.haut);
    }

    /**
     * Singleton-Instanz
     */
    public static final PlayerObject INSTANCE = new PlayerObject();
    
    // Timer, der nach dem Verteidigen startet
    private int postDefendDelay = 0;

    public int inventoryLockTimer = 0;


    /**
     * Privater Konstruktor, der nur für {@link #INSTANCE} verwendet wird
     */
    private PlayerObject() {
        // Vorläufig ohne Bild initialisieren
        super(null);

        // Vorlage laden und Größe bestimmen
        Image baseSprite = Images.get("/assets/characters/PlayerBase.png");
        width = baseSprite.getWidth(null) * PIXEL_SCALE;
        height = baseSprite.getHeight(null) * PIXEL_SCALE;

        // Tatsächlichen Sprite als zunächst leeres Bild erstellen
        sprite = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Farben anwenden
        updateSprite();
    }

    //Funktion zum Festlegen einer zufälligen x-Koordinate für die Spieler-Figur
    public static int GetRandomX(){
        Random r = new Random();
        int min = 35;
        int max = 790;
        return r.nextInt((max - min) + 1) + min;
    }

    public void addItem(ItemType item) {
        inventar.put(item, inventar.getOrDefault(item, 0) + 1);
    }

    public void removeItem(ItemType item) {
        inventar.put(item, inventar.getOrDefault(item, 0) - 1);
        if(inventar.get(item) < 1)
            inventar.remove(item);
    }

    public int verteidigung(int schaden, ItemType item) {
        schaden -= item.LEVEL + 1;
        if (schaden < 0) {
            schaden = 0;
        }

        return schaden;
    }

    public int angriff(ItemType item) {
        return item.LEVEL + 1;
    }


    /**
     * Farben {@link #haare}, {@link #haut}, {@link #hoodie} und {@link #hose} anwenden
     */
    public void updateSprite() {
        // Vorlage laden
        BufferedImage template = (BufferedImage) Images.get("/assets/characters/PlayerBase.png");
        // Grafik-Objekt zum Zeichnen erstellen
        Graphics2D g = (Graphics2D) sprite.getGraphics();

        // Alles löschen
        g.setColor(Draw.TRANSPARENCY);
        g.fillRect(0,0,template.getWidth(null), template.getHeight(null));

        for(int x = 0; x < template.getWidth(null); x++) {
            for(int y = 0; y < template.getHeight(null); y++) {
                // Farbe aus der Vorlage abfragen
                int templateColor = template.getRGB(x,y);

                // Die eingestellte Farbe auswählen ...
                if(spriteColorMap.containsKey(templateColor))
                    g.setColor(spriteColorMap.get(templateColor).apply(this).get());
                // ... oder, wenn es sich nicht um ein Pixel handelt,
                // welches nicht umgefärbt werden soll, die Farbe aus
                // der Vorlage beibehalten
                else
                    g.setColor(new Color(templateColor, true));

                // Skaliertes Pixel zeichnen
                g.fillRect(x*PIXEL_SCALE,y*PIXEL_SCALE,PIXEL_SCALE,PIXEL_SCALE);
            }
        }
    }


    @Override
    public void update(BaseScene scene) {
        if(inventoryLockTimer > 0)
            inventoryLockTimer--;

        // Inventar öffnen, wenn die Figur angeklickt wird
        if(allowInventory && inventoryLockTimer == 0 && isClicked())
            InventarScene.open(true);

        if(KampfScene.imKampf) {
            if(postDefendDelay > 0) {
                postDefendDelay--;
                if(postDefendDelay == 0) {
                    InventarScene.open(false);
                    KampfScene.turn = KampfScene.Turn.PLAYER_ATTACK;
                } else {
                    return;
                }
            }
            
            if(KampfScene.turn == KampfScene.Turn.PLAYER_DEFEND) {
                InventarScene.open(false);

                // Timer: Wie viel Zeit wir noch zum Verteidigen gegen den Angriff haben
                if(KampfScene.timer == 0) {
                    KampfScene.Endschaden = KampfScene.Zwischenschaden;
                    KampfScene.PlayerLeben -= KampfScene.Endschaden;
                    KampfScene.lehrerObject.displayItem(null);
                    postDefendDelay = 2 * FPS;
                    InventarScene.close();
                } else {
                    if(KampfScene.clicked != null) {
                        KampfScene.Endschaden = verteidigung(KampfScene.Zwischenschaden, KampfScene.clicked);
                        removeItem(KampfScene.clicked);
                        InventarScene.rebuild();
                        KampfScene.PlayerLeben -= KampfScene.Endschaden;
                        KampfScene.timer = 0;
                        KampfScene.lehrerObject.displayItem(null);
                        postDefendDelay = 2 * FPS;
                        InventarScene.close();
                    }
                }
            } else if (KampfScene.turn == KampfScene.Turn.PLAYER_ATTACK) {
                InventarScene.open(false);

                if(KampfScene.clicked != null) {
                    KampfScene.Zwischenschaden = angriff(KampfScene.clicked);
                    removeItem(KampfScene.clicked);
                    InventarScene.close();
                    KampfScene.turn = KampfScene.Turn.LEHRER_DEFEND;
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        g.setFont(Draw.FONT_INFO);
        if(KampfScene.imKampf) {
            boolean attack = (KampfScene.turn == KampfScene.Turn.PLAYER_ATTACK || KampfScene.turn == KampfScene.Turn.LEHRER_DEFEND);

            String text = attack ? "ANGRIFF" : "VERTEIDIGUNG";
            g.setColor(attack ? new Color(223, 52, 22) : new Color(83, 159, 234));
            Draw.drawTextCentered(g, text, x + width / 2, y + height + 5, true);

            g.setColor(Color.WHITE);
            Draw.drawTextCentered(g, "HP: "+(KampfScene.PlayerLeben > 0 ? KampfScene.PlayerLeben : "--"), x + width  / 2, y + height -20, true);
        }
    }
}
