package frame;

import assets.GameAsset;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {

    int x = 0;
    int y = 0;
    Room r;
    
    public static List<GameAsset> visibleGameAssets = new ArrayList<>();
    
    public Canvas(int w, int h) {
        super();
        this.setPreferredSize(new Dimension(w,h));
        this.setSize(new Dimension(w,h));
        this.setOpaque(false);
        this.setBounds(0, 0, w, h);
        
        this.setVisible(true);
        
        r = new Room();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        r.draw(g);
        
        g.setColor(Color.yellow);
        g.fillOval(x++, y++, 20, 20);
        
        x = x % 500;
        y = y % 500;
    }
}
