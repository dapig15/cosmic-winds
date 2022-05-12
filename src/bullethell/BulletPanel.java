package bullethell;

import bullethell.entity.*;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.PlayerBullet;
import bullethell.entity.ship.EnemyShip;
import bullethell.entity.ship.EnemyShipBoss;
import bullethell.entity.ship.ShipDeathAnimation;
import utility.FontGenerator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class BulletPanel extends JPanel {

    private GamePanel gpReference;
    private static int xOffset = 50, bulletPanelWidth = 540, bulletPanelHeight = 720;
    private static BufferedImage[] backgrounds; // TODO remember to make this more efficient

    public BulletPanel(GamePanel gpReference) {
        this.gpReference = gpReference;
        this.setBounds(0, 0, bulletPanelWidth, bulletPanelHeight);
        this.setLayout(null);
        backgrounds = new BufferedImage[2];
        try {
            backgrounds[0] = ImageIO.read(new File("images/background.png"));
            backgrounds[1] = ImageIO.read(new File("images/backgroundReversed.png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static int getxOffset() {
        return xOffset;
    }

    public static void setxOffset(int xOffset) {
        BulletPanel.xOffset = xOffset;
    }

    public static int getBulletPanelWidth() {
        return bulletPanelWidth;
    }

    public static void setBulletPanelWidth(int bulletPanelWidth) {
        BulletPanel.bulletPanelWidth = bulletPanelWidth;
    }

    public static int getBulletPanelHeight() {
        return bulletPanelHeight;
    }

    public static void setBulletPanelHeight(int bulletPanelHeight) {
        BulletPanel.bulletPanelHeight = bulletPanelHeight;
    }

    private int panelFrameCount = 0;

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgrounds[(panelFrameCount / bulletPanelHeight) % 2], 0,
                panelFrameCount % bulletPanelHeight,
                null);
        g.drawImage(backgrounds[(panelFrameCount / bulletPanelHeight + 1) % 2], 0,
                panelFrameCount % bulletPanelHeight - 720, null);
        panelFrameCount++;
        if (gpReference.getPlayerShip().getInvincibleCounter() / 5 % 2 == 0) {
            gpReference.getPlayerShip().paintMe(g);
        }
        ArrayList<PlayerBullet> playerBullets = gpReference.getPlayerBullets();
        ArrayList<EnemyBullet> enemyBullets = gpReference.getEnemyBullets();
        ArrayList<EnemyShip> enemyShips = gpReference.getEnemyShips();
        ArrayList<ShipDeathAnimation> shipDeaths = gpReference.getShipDeaths();
        for (int i = 0; i < playerBullets.size(); i++) {
            PlayerBullet pb = playerBullets.get(i);
            if (pb.isKillMe()) {
                playerBullets.remove(i);
                i--;
            } else {
                pb.paintMe(g);
            }
        }
        for (int i = 0; i < enemyBullets.size(); i++) {
            EnemyBullet eb = enemyBullets.get(i);
            if (eb.isKillMe()) {
                enemyBullets.remove(i);
                i--;
            } else {
                eb.paintMe(g);
            }
        }
        for (int i = 0; i < enemyShips.size(); i++) {
            EnemyShip es = enemyShips.get(i);
            if (es.getHealth() <= 0) {
                shipDeaths.add(
                        new ShipDeathAnimation(es.getCoords(), es.getShipImage(), (es instanceof EnemyShipBoss),
                                (es instanceof EnemyShipBoss) ? 100 : 10));
                enemyShips.remove(i);
                i--;
            } else {
                es.paintMe(g);
            }
        }
        for (int i = 0; i < shipDeaths.size(); i++) {
            ShipDeathAnimation sda = shipDeaths.get(i);
            if (sda.shouldKill()) {
                shipDeaths.remove(sda);
                i--;
            } else {
                sda.paintMe(g);
            }
        }
        if (gpReference.getWaveToSpawn() == 1 && gpReference.isInBetweenWaves()) {
            float val = Math.min(1, 2 - Math.abs((gpReference.getCooldown() + 50)) * 0.02f);
            FontGenerator.writeCenteredText(g, "STAGE 1",
                    BulletPanel.getBulletPanelWidth() / 2, 150, 5, new Color(1, 1, 1, val));
        }
    }

}