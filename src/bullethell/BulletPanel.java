package bullethell;

import bullethell.entity.*;
import bullethell.entity.bullet.EnemyBullet;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BulletPanel extends JPanel {

    private GamePanel gpReference;
    private static int xOffset = 50, bulletPanelWidth = 540, bulletPanelHeight = 720;

    public BulletPanel(GamePanel gpReference) {
        this.gpReference = gpReference;
        this.setLayout(null);
        this.setBounds(xOffset, 0, bulletPanelWidth, bulletPanelHeight);
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

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        gpReference.getPlayerShip().paintMe(g);
        ArrayList<PlayerBullet> playerBullets = gpReference.getPlayerBullets();
        ArrayList<EnemyBullet> enemyBullets = gpReference.getEnemyBullets();
        ArrayList<EnemyShip> enemyShips = gpReference.getEnemyShips();
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
                enemyShips.remove(i);
                i--;
            } else {
                es.paintMe(g);
            }
        }
    }

}