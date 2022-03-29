package bullethell;

import bullethell.entity.*;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BulletPanel extends JPanel {

    private GamePanel gpReference;

    public BulletPanel(GamePanel gpReference) {
        this.gpReference = gpReference;
        this.setLayout(null);
        this.setBounds(50, 0, 540, 720);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (Ship s : gpReference.getShips()) {
            s.paintMe(g);
        }
    }

}