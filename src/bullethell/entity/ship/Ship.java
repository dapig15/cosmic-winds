package bullethell.entity.ship;

import bullethell.*;
import bullethell.entity.bullet.Bullet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Ship {
    private Coords coords;
    private int hitboxWidth, hitboxHeight;
    private BufferedImage ship;
    private int health;

    public Ship(Coords coords, int hitboxWidth, int hitboxHeight, String imagePath, int health) {
        this.coords = coords;
        this.hitboxWidth = hitboxHeight;
        this.hitboxHeight = hitboxHeight;
        try {
            ship = ImageIO.read(new File(imagePath));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        this.health = health;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public void shiftCoords(int xInc, int yInc) {
        coords.shiftCoords(xInc, yInc);
    }

    public int getHitboxWidth() {
        return hitboxWidth;
    }

    public void setHitboxWidth(int hitboxWidth) {
        this.hitboxWidth = hitboxWidth;
    }

    public int getHitboxHeight() {
        return hitboxHeight;
    }

    public void setHitboxHeight(int hitboxHeight) {
        this.hitboxHeight = hitboxHeight;
    }

    public BufferedImage getShipImage() {
        return ship;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public abstract void update();

    public abstract void getHit(Bullet b);

    public void paintMe(Graphics g) {
        g.drawImage(ship, coords.getX() - ship.getWidth() / 2, coords.getY() - ship.getHeight() / 2, null);
    }
}
