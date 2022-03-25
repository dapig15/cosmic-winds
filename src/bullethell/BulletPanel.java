package bullethell;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import bullethell.entity.*;
import utility.Hitbox;

public class BulletPanel extends JPanel {
    private ArrayList<Ship> ships = new ArrayList<>();

    public BulletPanel() {
        this.setLayout(null);
        this.setBounds(50, 0, 540, 720);
        // TODO fix me
        ships.add(new PlayerShip(new Coords(0, 0), 4, 4, "shipTest.png"));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (Ship s : ships) {
            s.paintMe(g);
        }
    }
}