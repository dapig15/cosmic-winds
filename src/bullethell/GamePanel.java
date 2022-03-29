package bullethell;

import bullethell.entity.*;
import utility.Hitbox;

import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private BulletPanel bulletPanel;
    private ArrayList<Ship> ships = new ArrayList<>();

    private PlayerShip player;

    public GamePanel() {
        player = new PlayerShip(new Coords(0, 0), 4, 4, "shipTest.png");
        ships.add(player);
        this.addKeyListener(player.getPKA());
        this.setFocusable(true);

        this.setBackground(Color.RED);
        this.setLayout(null);

        bulletPanel = new BulletPanel(this);
        this.add(bulletPanel);
    }

    public BulletPanel getBulletPanel() {
        return bulletPanel;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void update() {
        if (player.isMoveRight()) {
            player.shiftCoords(player.getMoveSpeed(), 0);
        }
        if (player.isMoveLeft()) {
            player.shiftCoords(-player.getMoveSpeed(), 0);
        }
        if (player.isMoveDown()) {
            player.shiftCoords(0, player.getMoveSpeed());
        }
        if (player.isMoveUp()) {
            player.shiftCoords(0, -player.getMoveSpeed());
        }
    }
}
