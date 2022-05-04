package bullethell.entity.ship;

import bullethell.Coords;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ShipDeathAnimation {
    private Coords coords;
    private BufferedImage ship, toDraw;
    private boolean isBoss;
    private int framesAlive = 0, maxFrames;

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public ShipDeathAnimation(Coords coords, String filePath, boolean isBoss, int maxFrames) {
        this.coords = coords;
        try {
            ship = ImageIO.read(new File(filePath));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        this.isBoss = isBoss;
        this.maxFrames = maxFrames;
    }

    public ShipDeathAnimation(Coords coords, BufferedImage ship, boolean isBoss, int maxFrames) {
        this.coords = coords;
        this.ship = ship;
        this.isBoss = isBoss;
        this.maxFrames = maxFrames;
    }

    public boolean shouldKill() {
        return framesAlive >= maxFrames;
    }

    public void update() {
        if (isBoss) {
            int add = Math.min(framesAlive, maxFrames * 3 / 4) + Math.max(0, framesAlive - (maxFrames * 3 / 4)) * 5;
            int newWidth = (int) (ship.getWidth() * (100f + add) / 100);
            int newHeight = (int) (ship.getHeight() * (100f + add) / 100);
            BufferedImage nbim = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D createGraphics = nbim.createGraphics();
            createGraphics.drawImage(ship, 0, 0, newWidth, newHeight, null);
            RescaleOp r = new RescaleOp(
                    new float[] { 1f + framesAlive / 10f, 1f + framesAlive / 10f, 1f + framesAlive / 10f,
                            Math.max(0,
                                    (float) (1
                                            - Math.max(0, framesAlive - (maxFrames * 3 / 4)) * 1f / (maxFrames / 4))) },
                    new float[] { 0, 0, 0, 0 }, null);
            toDraw = r.filter(nbim, null);
        } else {
            int newWidth = (int) (ship.getWidth() * (100f + framesAlive * 5) / 100);
            int newHeight = (int) (ship.getHeight() * (100f + framesAlive * 5) / 100);
            BufferedImage nbim = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D createGraphics = nbim.createGraphics();
            createGraphics.drawImage(ship, 0, 0, newWidth, newHeight, null);
            RescaleOp r = new RescaleOp(
                    new float[] { 1f, 1f, 1f, Math.max(0, (float) (1 - framesAlive * 1f / maxFrames)) },
                    new float[] { 0, 0, 0, 0 }, null);
            toDraw = r.filter(nbim, null);
        }

        framesAlive++;
    }

    public void paintMe(Graphics g) {
        if (toDraw == null) {
            toDraw = ship;
        }
        g.drawImage(toDraw, coords.getX() - toDraw.getWidth() / 2, coords.getY() - toDraw.getHeight() / 2,
                toDraw.getWidth(), toDraw.getHeight(), null);
    }
}
