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
    private String imagePath;
    private int health;

    public Ship(Coords coords, int hitboxWidth, int hitboxHeight, String imagePath, int health) {
        this.coords = coords;
        this.hitboxWidth = hitboxHeight;
        this.hitboxHeight = hitboxHeight;
        this.imagePath = imagePath;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public abstract void update();

    public abstract void getHit(Bullet b);

    public void paintMe(Graphics g) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            g.drawImage(img, coords.getX() - img.getWidth() / 2, coords.getY() - img.getHeight() / 2, null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
