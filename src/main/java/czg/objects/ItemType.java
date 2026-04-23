package czg.objects;

import czg.util.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Enum für Items. Jedes Item sollte als <b>eine</b> {@code public static final}-Instanz
 * (Singleton) in dieser Klasse angelegt werden.
 */
public enum ItemType {
    ATOM("Atom", "/assets/items/atom.png", 0),
    BSOD("Error-Screen", "/assets/items/blue_screen_of_death.png", 2),
    BRENNER("Brenner", "/assets/items/brenner.png", 2),
    CAS("Taschenrechner", "/assets/items/cas.png", 2),
    CD("CD", "/assets/items/cd.png", 0),
    CHROME("Chrome", "/assets/items/chrome.png", 1),
    DNA("DNA", "/assets/items/dna.png", 1),
    FELDSTECHER("Feldstecher", "/assets/items/feldstecher.png", 1),
    GLUEHBIRNE("Glühbirne", "/assets/items/glühbirne.png", 2),
    IKOSAEDER("Ikosaeder", "/assets/items/ikosaeder.png", 1),
    KRAFTMESSER("Feder\nkraftmesser", "/assets/items/kraftmesser.png", 0),
    LAUTSPRECHER("Lautsprecher", "/assets/items/lautsprecher.png", 0),
    LINEAL("Lineal", "/assets/items/lineal.png", 0),
    TEXT("FrauBreunig","/assets/items/wenn_sie_das_hier_wirklich.png", 1),
    MAGNET("Magnet", "/assets/items/magnet.png", 1),
    MIKROSKOP("Mikroskop", "/assets/items/mikroskop.png", 2),
    NERV("Nervenzelle", "/assets/items/nerv.png", 0),
    PAPIER("FrauBreunig","/assets/items/lesen_sagen_sie_bescheid.png", 2),
    NEWTONSAPFEL("Newtons Apfel", "/assets/items/newtons_apfel.png", 0),
    THALES("Satz des Thales", "/assets/items/satz_des_thales.png", 1),
    SCHUTZBRILLE("Schutzbrille", "/assets/items/schutzbrille.png", 1),
    SCHAEDEL("Schädel", "/assets/items/schädel.png", 1),
    SEIZUREDFROG("Sezierter Frosch", "/assets/items/seizured_frog.png", 2),
    SPRITZFLASCHE("Spritzflasche", "/assets/items/spritzflasche.png", 0),
    SAEURE("Säure", "/assets/items/säure.png", 2),
    TASTATUR("Tastatur", "/assets/items/tastatur.png", 2),
    THERMOMETER("Thermometer", "/assets/items/thermometer.png", 2),
    VIRUS("Virus", "/assets/items/virus.png", 0),
    WLAN("Wlan", "/assets/items/wlan.png", 1),
    WUNDERKERZE("Wunderkerze", "/assets/items/wunderkerze.png", 1),
    ZETTEL("Zettel", "/assets/items/zettel.png", 2),
    ZIRKEL("Zirkel", "/assets/items/zirkel.png", 0),
    DEBUG("Debug", "", 99);
        
    public final String NAME;
    public final Image SPRITE;
    public final int LEVEL;
    
    ItemType(String name, String imagePath, int level) {
        this.NAME = name;
        this.LEVEL = level;
        this.SPRITE = Images.cropTransparency((BufferedImage) Images.get(imagePath));
    }

    /**
     * Gibt ein Item als Belohnung für das Beenden eines Minispiels zurück.
     * @param minigame Das Minispiel, welches beendet wurde
     * @param level Das Level, welches beendet wurde
     */
    public static ItemType getMinigameReward(Department minigame, int level) {
        switch(minigame) {
            case COMPUTER_SCIENCE -> {
                switch(level) {
                    case 0 -> {
                        return ItemType.CD;
                    } case 1 -> {
                        return ItemType.CHROME;
                    } case 2 -> {
                        return ItemType.TASTATUR;
                    }
                }
            } case PHYSICS -> {
                switch(level) {
                    case 0 -> {
                        return ItemType.KRAFTMESSER;
                    } case 1 -> {
                        return ItemType.MAGNET;
                    } case 2 -> {
                        return ItemType.GLUEHBIRNE;
                    }
                }
            } case MATHEMATICS -> {
                switch(level) {
                    case 0 -> {
                        return ItemType.ZIRKEL;
                    } case 1 -> {
                        return ItemType.THALES;
                    } case 2 -> {
                        return ItemType.CAS;
                    }
                }
            } case BIOLOGY -> {
                switch(level) {
                    case 0 -> {
                        return ItemType.VIRUS;
                    } case 1 -> {
                        return ItemType.SCHAEDEL;
                    } case 2 -> {
                        return ItemType.SEIZUREDFROG;
                    }
                }
            } case CHEMISTRY -> {
                switch(level) {
                    case 0 -> {
                        return ItemType.SPRITZFLASCHE;
                    } case 1 -> {
                        return ItemType.SCHUTZBRILLE;
                    } case 2 -> {
                        return ItemType.SAEURE;
                    }
                }
            }
        }
        return null;
        
    }
    
    public static List<ItemType> getItems(Department fachschaft) { // Items mit jeweiligen Leveln an Lehrer mit jeweiligen Leveln  verteilen
        if (fachschaft == Department.COMPUTER_SCIENCE) {
            
            return List.of(  //die Items werden zurückgegeben
                    ItemType.CD, //level 1
                    ItemType.LAUTSPRECHER, //level 1
                    ItemType.WLAN, //level 1 (eigentlich level 2)
                    ItemType.CHROME//level 2
            ); 
        } else if (fachschaft == Department.PHYSICS) {
             
            return List.of(
                    ItemType.NEWTONSAPFEL, //level 1
                    ItemType.KRAFTMESSER, //level 1
                    ItemType.FELDSTECHER, //level 1 (eigentlich level 2)
                    ItemType.MAGNET//level 2
            );
        } else if (fachschaft == Department.MATHEMATICS) {
             
            return List.of(
                    ItemType.LINEAL, //level 1
                    ItemType.ZIRKEL, //level 1
                    ItemType.IKOSAEDER, //level 1 (eigentlich level 2)
                    ItemType.THALES//level 2
            );  
        } else if (fachschaft == Department.BIOLOGY) {

            return List.of(
                    ItemType.VIRUS, //level 1
                    ItemType.NERV, //level 1
                    ItemType.SCHAEDEL, //level 1 (eigentlich level 2)
                    ItemType.DNA//level 2
            );
        } else if (fachschaft == Department.CHEMISTRY) {
            
            return List.of(
                    ItemType.SPRITZFLASCHE, //level 1
                    ItemType.ATOM, //level 1
                    ItemType.SCHUTZBRILLE, //level 1 (eigentlich level 2)
                    ItemType.WUNDERKERZE//level 2
            );
        } else {
            throw new RuntimeException("BRO java is actually doch nicht so tuff");
        }
    }
}
