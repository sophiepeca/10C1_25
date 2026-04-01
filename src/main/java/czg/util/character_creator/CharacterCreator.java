package czg.util.character_creator;

import czg.MainWindow;
import czg.objects.PlayerObject;
import czg.util.Lazy;

import javax.swing.*;
import java.awt.*;

/**
 * Fenster für die Anpassung der Farben der Spielfigur
 */
public final class CharacterCreator extends JFrame {

    /**
     * Singleton-Instanz. Wird beim ersten Öffnen des Character Creators initialisiert.
     */
    public static final Lazy<CharacterCreator> INSTANCE = new Lazy<>(CharacterCreator::new);

    /**
     * JLabel, welches das Vorschaubild anzeigt.
     * @see #updatePreview()
     */
    private final JLabel previewImageLabel;

    /**
     * Wird von {@link SaveFile#apply()} gebraucht
     */
    final JPanel colorPickerPanel = getColorPickerPanel();


    /**
     * CharacterCreator initialisieren
     */
    private CharacterCreator() {
        super("Spielfigur-Einstellungen");

        // Haupt-Panel
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        // Farbauswahl-Panel in die Mitte
        content.add(colorPickerPanel, BorderLayout.CENTER);

        // Vorschaubild nach rechts
        previewImageLabel = new JLabel(new ImageIcon());
        previewImageLabel.setPreferredSize(new Dimension(150, 200));
        // Vorschaubild ein erstes Mal aktualisieren
        updatePreview();

        content.add(previewImageLabel, BorderLayout.EAST);

        // Speichern/Laden nach unten
        content.add(getSaveLoadPanel(), BorderLayout.SOUTH);


        // Abschließende Einstellungen
        setSize(500, 200);
        setResizable(false);
        setContentPane(content);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    /**
     * Panel mit den Speichern- und Lade-Buttons
     * @return Das fertige Panel
     */
    private JPanel getSaveLoadPanel() {
        JButton load = new JButton("Laden");
        load.addActionListener(SaveAndLoad::load);

        JButton save = new JButton("Speichern");
        save.addActionListener(SaveAndLoad::save);

        JButton reset = new JButton("Zurücksetzen");
        reset.addActionListener(a -> PlayerObject.defaultColors.apply());

        JPanel saveLoadPanel = new JPanel();
        saveLoadPanel.setLayout(new GridLayout(1, 3));
        saveLoadPanel.add(load);
        saveLoadPanel.add(save);
        saveLoadPanel.add(reset);

        return saveLoadPanel;
    }

    /**
     * Panel mit jeweils einem Text, einer Farbvorschau (einfarbiges Rechteck)
     * und einem Button zum Auswählen der einzelnen Farben
     * @return Das fertige Panel
     */
    private JPanel getColorPickerPanel() {
        JPanel colorPickersPanel = new JPanel();
        colorPickersPanel.setLayout(new GridLayout(4, 1));
        colorPickersPanel.add(new ColorPickerPanel("Haare", PlayerObject.INSTANCE.haare));
        colorPickersPanel.add(new ColorPickerPanel("Haut", PlayerObject.INSTANCE.haut));
        colorPickersPanel.add(new ColorPickerPanel("Hoodie", PlayerObject.INSTANCE.hoodie));
        colorPickersPanel.add(new ColorPickerPanel("Hose", PlayerObject.INSTANCE.hose));
        return colorPickersPanel;
    }

    /**
     * Vorschaubild aktualisieren
     */
    void updatePreview() {
        ((ImageIcon) previewImageLabel.getIcon()).setImage(PlayerObject.INSTANCE.sprite.getScaledInstance(
                -1,
                125,
                Image.SCALE_DEFAULT
        ));
        previewImageLabel.repaint();
    }

    @Override
    public void setVisible(boolean aFlag) {
        setLocationRelativeTo(MainWindow.INSTANCE);
        super.setVisible(aFlag);
    }

}
