package bullethell.entity;

import bullethell.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Ship {
    private Coords coords;
    private int hitboxWidth, hitboxHeight;
    private String imagePath;

    public Ship(Coords coords, int hitboxWidth, int hitboxHeight, String imagePath) {
        this.coords = coords;
        this.hitboxWidth = hitboxHeight;
        this.hitboxHeight = hitboxHeight;
        this.imagePath = imagePath;
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

    public abstract void update();

    public void paintMe(Graphics g) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            g.drawImage(img, coords.getX() - img.getWidth() / 2, coords.getY() - img.getHeight() / 2, null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
