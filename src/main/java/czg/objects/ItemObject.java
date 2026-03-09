/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package czg.objects;

import czg.objects.minigame_objects.MinigameNameObject;
import czg.util.Images;

import java.awt.*;

/**
 * Enum für Items. Jedes Item sollte als <b>eine</b> {@code public static final}-Instanz
 * (Singleton) in dieser Klasse angelegt werden.
 */
public enum ItemObject{
    ATOM("Atom", "/assets/items/Atom.png", 0),
    BSOD("Error-Screen", "/assets/items/BSOD.png", 2),
    BRENNER("Brenner", "/assets/items/Brenner.png", 2),
    CAS("Taschenrechner", "/assets/items/CAS.png", 2),
    CD("CD", "/assets/items/CD.png", 0),
    CHROME("Chrome", "/assets/items/Chrome.png", 1),
    DNA("DNA", "/assets/items/DNA.png", 1),
    FELDSTECHER("Feldstecher", "/assets/items/Feldstecher.png", 1),
    GLÜHBIRNE("Glühbirne", "/assets/items/Glühbirne.png", 2),
    IKOSAEDER("Ikosaeder", "/assets/items/Ikosaeder.png", 1),
    KRAFTMESSER("Federkraftmesser", "/assets/items/Kraftmesser.png", 0),
    LAUTSPRECHER("Lautsprecher", "/assets/items/Lautsprecher.png", 0),
    LINEAL("Lineal", "/assets/items/Lineal.png", 0),
    MAGNET("Magnet", "/assets/items/Magnet.png", 1),
    MIKROSKOP("Mikroskop", "/assets/items/Mikroskop.png", 2),
    NERV("Nervenzelle", "/assets/items/Nerv.png", 0),
    NEWTONSAPFEL("Newtons Apfel", "/assets/iterms/NJewtons_Apfel.png", 0),
    THALES("Satz des Thales", "/assets/items/Satz_des_Thales.png", 1),
    SCHUTZBRILLE("Schutzbrille", "/assets/items/Schutzbrille.png", 1),
    SCHÄDEL("Schädel", "/assets/items/Schädel.png", 1),
    SEIZUREDFROG("Sezierter Frosch", "/assets/items/Seizured_Frog.png", 2),
    SPRITZFLASCHE("Spritzflasche", "/assets/items/Spritzflasche.png", 0),
    SÄURE("Säure", "/assets/items/Säure.png", 2),
    TASTATUR("Tastatur", "/assets/items/Tastatur.png", 2),
    THERMOMETER("Thermometer", "/assets/items/Thermometer.png", 2),
    VIRUS("Virus", "/assets/items/Virus.png", 0),
    WLAN("Wlan", "/assets/items/WLAN.png", 1),
    WUNDERKERZE("Wunderkerze", "/assets/items/Wunderkerze.png", 1),
    ZETTEL("Zettel", "/assets/items/Zettel.png", 2),
    ZIRKEL("Zirkel", "/assets/items/Zirkel.png", 0);
        
    public final String name;
    public final Image sprite;
    public final int level;
    
    private ItemObject(String name, String imagePath, int level) {
        this.name = name;
        this.sprite = Images.get(imagePath);
        this.level = level;
    }

    /**
     * Gibt ein Item als Belohnung für das Beenden eines Minispiels zurück.
     * @param minigame Das Minispiel, welches beendet wurde
     * @param level Das Level, welches beendet wurde
     */
    public static ItemObject getMinigameReward(MinigameNameObject minigame, int level) {
        switch(minigame) {
            case INFORMATICS -> {
                switch(level) {
                    case 0 -> {
                        return ItemObject.CD;
                    } case 1 -> {
                        return ItemObject.CHROME;
                    } case 2 -> {
                        return ItemObject.TASTATUR;
                    }
                }
            } case PHYSICS -> {
                switch(level) {
                    case 0 -> {
                        return ItemObject.KRAFTMESSER;
                    } case 1 -> {
                        return ItemObject.MAGNET;
                    } case 2 -> {
                        return ItemObject.GLÜHBIRNE;
                    }
                }
            } case MATHEMATICS -> {
                switch(level) {
                    case 0 -> {
                        return ItemObject.ZIRKEL;
                    } case 1 -> {
                        return ItemObject.THALES;
                    } case 2 -> {
                        return ItemObject.CAS;
                    }
                }
            } case BIOLOGY -> {
                switch(level) {
                    case 0 -> {
                        return ItemObject.VIRUS;
                    } case 1 -> {
                        return ItemObject.SCHÄDEL;
                    } case 2 -> {
                        return ItemObject.SEIZUREDFROG;
                    }
                }
            } case CHEMISTRY -> {
                switch(level) {
                    case 0 -> {
                        return ItemObject.SPRITZFLASCHE;
                    } case 1 -> {
                        return ItemObject.SCHUTZBRILLE;
                    } case 2 -> {
                        return ItemObject.SÄURE;
                    }
                }
            }  
        }
        return null;
    }
}
