package czg.objects;

import czg.scenes.BaseScene;
import czg.scenes.InventarScene;
import czg.scenes.KampfScene;
import czg.scenes.SceneStack;
import czg.util.Capsule;
import czg.util.Draw;
import czg.util.Images;
import czg.util.character_creator.SaveFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.function.Function;

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

    public void addItem(ItemType item) {
        inventar.put(item, inventar.getOrDefault(item, 0) + 1);
    }

    public void removeItem(ItemType item) {
        inventar.put(item, inventar.getOrDefault(item, 0) - 1);
        if(inventar.get(item) < 1)
            inventar.remove(item);
    }
    
    //Funktion zum Festlegen einer zufälligen x-Koordinate für die Spieler-Figur
    public static int GetRandomX(){
        Random r = new Random();
        int min = 35;
        int max = 790;
        return r.nextInt((max - min) + 1) + min;
    }

    /*
    Bitti bitti nicht noch mal löschen...
    */
    public int angriff(ItemType welchesItem) {
        int level = welchesItem.LEVEL;
        return level;
    }
    
    public int verteidigung(int schaden, ItemType welchesItem) {
        int level = welchesItem.LEVEL;
        
        schaden -= level;
        if (schaden < 0) {
            schaden = 0;
        }

        return schaden;
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
        // Inventar öffnen, wenn die Figur angeklickt wird
        if(allowInventory && isClicked())
            SceneStack.INSTANCE.push(new InventarScene());

        if(KampfScene.imKampf) {
            if(KampfScene.PlayerVerteidigung) {
                if(KampfScene.timer == 0) {
                    KampfScene.Endschaden = KampfScene.Zwischenschaden;
                    KampfScene.PlayerVerteidigung = false;
                    KampfScene.PlayerTurn = true;
                    return;
                }
                else {
                    ItemType clicked = InventarScene.getClickedItem();
                    if(clicked != null) {
                        KampfScene.Endschaden = verteidigung(KampfScene.Zwischenschaden, clicked);
                        removeItem(clicked);
                        KampfScene.PlayerVerteidigung = false;
                        KampfScene.PlayerTurn = true;
                        return;
                    }
                }
            }
            if (KampfScene.PlayerTurn) {
                ItemType clicked = InventarScene.getClickedItem();
                if(clicked != null) {
                    System.out.println("Du bist am Angreifen");
                    KampfScene.Zwischenschaden = angriff(clicked);
                    removeItem(clicked);
                    KampfScene.PlayerTurn = false;
                    KampfScene.lehrerVerteidigung = true;
                }
            }
        }
    }
    
}
