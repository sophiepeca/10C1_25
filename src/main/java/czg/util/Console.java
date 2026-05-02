package czg.util;

import czg.objects.ItemType;
import czg.objects.PlayerObject;
import czg.scenes.BaseScene;
import czg.scenes.InventarScene;
import czg.scenes.KampfScene;
import czg.scenes.SceneStack;
import czg.util.character_creator.SaveAndLoad;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Console {

    public static final List<String> queue = new ArrayList<>();

    public static void processQueue() {
        for(String command : queue) {
            switch(command) {
                case "impulse 101" -> {
                    Arrays.stream(ItemType.values()).forEach(i -> PlayerObject.INSTANCE.inventar.put(i, 999));
                    InventarScene.rebuild();
                }
                case "maimais" -> {
                    PlayerObject.INSTANCE.addItem(ItemType.TEXT);
                    PlayerObject.INSTANCE.addItem(ItemType.PAPIER);
                    InventarScene.rebuild();
                }
                case "clear" -> {
                    PlayerObject.INSTANCE.inventar.clear();
                    InventarScene.rebuild();
                }
                case String s when s.matches("give .+ \\d+") -> {
                    String[] parts = s.split(" ");
                    try {
                        ItemType type = ItemType.valueOf(parts[1]);
                        int count = Math.max(1, Integer.parseInt(parts[2]));

                        PlayerObject.INSTANCE.inventar.put(type, PlayerObject.INSTANCE.inventar.getOrDefault(type,0)+count);
                        InventarScene.rebuild();
                    } catch (IllegalStateException | NumberFormatException e) {
                        System.err.println("Konsole: Unbekanntes Item bzw. ungültige Anzahl");
                    }
                }
                case String s when s.matches("scene .+") -> {
                    String sceneName = s.split(" ")[1];
                    Class<?> sceneClass = null;
                    try {
                        sceneClass = Class.forName("czg.scenes."+sceneName);
                    } catch(ClassNotFoundException e) {
                        try {
                            sceneClass = Class.forName(sceneName);
                        } catch (ClassNotFoundException e2) {
                            System.err.println("Konsole: Unbekannte Szene '"+sceneName+"'");
                        }
                    }
                    if(sceneClass != null && BaseScene.class.isAssignableFrom(sceneClass)) {
                        try {
                            Constructor<?> constructor = sceneClass.getConstructor();
                            BaseScene sceneInstance = (BaseScene) constructor.newInstance();
                            SceneStack.INSTANCE.push(sceneInstance);
                        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                                 InvocationTargetException e3) {
                            System.err.println("Konsole: Konnte Szene '"+sceneName+"' nicht erstellen");
                        }
                    }
                }
                case String s when s.matches("chr .+") -> {
                    String fileName = s.substring("chr".length()+1);
                    try {
                        SaveAndLoad.load(new File(fileName));
                    } catch (Exception e) {
                        System.err.println("Unable to load: '"+fileName+"': "+e);
                    }
                }
                case String s when s.matches("hp [pl] \\d+") -> {
                    try {
                        int hp = Integer.parseInt(s.split(" ")[2]);
                        if(s.split(" ")[1].equals("p"))
                            KampfScene.PlayerLeben = hp;
                        else
                            KampfScene.LehrerLeben = hp;
                    } catch (NumberFormatException x) {
                        System.err.println("Konsole: Ungültige Zahl");
                    }
                }
                case "pop" -> SceneStack.INSTANCE.pop();
                case null, default -> {}
            }
        }

        queue.clear();
    }

}
