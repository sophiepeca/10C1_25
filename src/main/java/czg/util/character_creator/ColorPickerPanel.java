package czg.util.character_creator;

import czg.objects.PlayerObject;
import czg.util.Capsule;

import javax.swing.*;
import java.awt.*;

/**
 * Ein einzelnes Panel, welches einen Text, eine Farbvorschau (einfarbiges
 * Rechteck) und einen Button zum Andern der Farbe enthält
 */
final class ColorPickerPanel extends JPanel {

    /**
     * Panel, welches die gewählte Farbe anzeigt
     */
    final JPanel previewPanel = new JPanel();

    /**
     * Neues Farbwahl-Panel erstellen
     * @param title Angezeigter Text
     * @param capsule {@link Capsule} aus der {@link PlayerObject}-Klasse, in welcher die gewählte Farbe gespeichert wird
     */
    ColorPickerPanel(String title, Capsule<Color> capsule) {
        setLayout(new BorderLayout());

        // Text nach links
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(80, 50));
        add(titleLabel, BorderLayout.WEST);

        // Vorschau in die Mitte
        previewPanel.setBackground(capsule.get());
        add(previewPanel, BorderLayout.CENTER);

        // Button nach rechts
        JButton button = new JButton("Ändern");
        add(button, BorderLayout.EAST);
        button.addActionListener(e -> {
                    // Farbe des Vorschau-Panels ändern
                    previewPanel.setBackground(
                            // Neue Farbe in der Player-Klasse speichern
                            capsule.set(JColorChooser.showDialog(
                                    this, "Farbe wählen für " + title, previewPanel.getBackground(), false
                            ))
                    );

                    // Spielfigur-Sprite aktualisieren
                    PlayerObject.INSTANCE.updateSprite();

                    // Vorschaubild aktualisieren
                    CharacterCreator.INSTANCE.get().updatePreview();
                }
        );

    }

}
