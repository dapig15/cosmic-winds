package bullethell;

import bullethell.entity.*;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.PlayerBullet;
import bullethell.entity.ship.EnemyShip;
import bullethell.entity.ship.EnemyShipBigGreen;
import bullethell.entity.ship.EnemyShipBigRed;
import bullethell.entity.ship.EnemyShipBigYellow;
import bullethell.entity.ship.EnemyShipBlue;
import bullethell.entity.ship.EnemyShipBossGreen;
import bullethell.entity.ship.EnemyShipBossRed;
import bullethell.entity.ship.EnemyShipGreen;
import bullethell.entity.ship.EnemyShipRed;
import bullethell.entity.ship.EnemyShipYellow;
import bullethell.entity.ship.PlayerShip;
import bullethell.entity.ship.ShipDeathAnimation;
import bullethell.entity.ship.Stage1ESBlue;
import bullethell.entity.ship.Stage1ESMiniboss;
import bullethell.entity.ship.Stage1ESRed;
import bullethell.entity.ship.Stage1ESYellow;
import utility.FontGenerator;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.Graphics;

import javax.imageio.ImageIO;

public class GamePanel extends JPanel {

    private BulletPanel bulletPanel;

    private PlayerShip player;
    private ArrayList<EnemyShip> enemyShips = new ArrayList<>();
    private ArrayList<PlayerBullet> playerBullets = new ArrayList<>();
    private ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    private ArrayList<ShipDeathAnimation> shipDeaths = new ArrayList<>();
    private BufferedImage background;

    private final int halfWidth;

    public GamePanel() {
        player = new PlayerShip(
                new Coords(BulletPanel.getBulletPanelWidth() / 2, BulletPanel.getBulletPanelHeight() * 3 / 4), 4, 4,
                "images/sprites/ships/shipTest.png",
                enemyShips);
        this.addKeyListener(player.getPKA());
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(1080, 720));
        PlayerBullet.setEnemyShipReference(enemyShips);
        EnemyBullet.setPlayerReference(player);

        try {
            background = ImageIO.read(new File("images/gamePanelBackground.png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        this.setLayout(null);

        bulletPanel = new BulletPanel(this);
        bulletPanel.setBounds(50, 0, BulletPanel.getBulletPanelWidth(), BulletPanel.getBulletPanelHeight());
        this.add(bulletPanel);

        halfWidth = BulletPanel.getBulletPanelWidth() / 2;

    }

    public BulletPanel getBulletPanel() {
        return bulletPanel;
    }

    public PlayerShip getPlayerShip() {
        return player;
    }

    public ArrayList<EnemyShip> getEnemyShips() {
        return enemyShips;
    }

    public ArrayList<PlayerBullet> getPlayerBullets() {
        return playerBullets;
    }

    public ArrayList<EnemyBullet> getEnemyBullets() {
        return enemyBullets;
    }

    public ArrayList<ShipDeathAnimation> getShipDeaths() {
        return shipDeaths;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, null);
        FontGenerator.writeText(g, "hp: " + player.getHealth(), 50 + BulletPanel.getBulletPanelWidth() + 50, 50, 3,
                Color.white);
        g.setColor(Color.gray);
        g.fillRoundRect(50 + BulletPanel.getBulletPanelWidth() + 40, 390,
                getWidth() - BulletPanel.getBulletPanelWidth() - 130, getHeight() - 430, 50, 50);
        g.setColor(Color.white);
        g.fillRoundRect(50 + BulletPanel.getBulletPanelWidth() + 50, 400,
                getWidth() - BulletPanel.getBulletPanelWidth() - 150, getHeight() - 450, 50, 50);
        FontGenerator.writeText(g, "qwertyuiopasdfghjklzxcvbnm\nQWERTYUIOPASDFGHJKLZXCVBNM\n1234567890",
                50 + BulletPanel.getBulletPanelWidth() + 70, 400 + 20, 1.25f);
    }

    private int waveToSpawn = 7, cooldown = 50, cooldownMax = 50;
    private int currentStage = 1;
    private final int[] wavesPerStage = new int[] { 0, 8, 0, 0, 0, 13, 0 };

    private void updateStage1(int waveToSpawn) {
        switch (waveToSpawn) {
            case 0:
                enemyShips.add(new Stage1ESRed(new Coords(halfWidth, -50), player, new Coords(halfWidth, 100)));
                break;
            case 1:
                enemyShips
                        .add(new Stage1ESRed(new Coords(halfWidth - 150,
                                -50), player, new Coords(halfWidth - 150, 100)));
                enemyShips
                        .add(new Stage1ESRed(new Coords(halfWidth + 150,
                                -50), player, new Coords(halfWidth + 150, 100)));
                break;
            case 2:
                enemyShips.add(new Stage1ESYellow(new Coords(halfWidth, -50), player, new Coords(halfWidth, 60)));
                enemyShips.add(new Stage1ESRed(new Coords(halfWidth, -50), player, new Coords(halfWidth, 140)));
                break;
            case 3:
                for (int i = -1; i <= 1; i += 2) {
                    enemyShips.add(new Stage1ESYellow(new Coords(halfWidth + i * 200,
                            -50), player,
                            new Coords(halfWidth + i * 200, 200)));
                    enemyShips.add(new Stage1ESRed(new Coords(halfWidth + i * 50,
                            -50), player,
                            new Coords(halfWidth + i * 50, 80)));
                }
                break;
            case 4:
                enemyShips.add(new Stage1ESRed(new Coords(halfWidth - 80,
                        -50), player, new Coords(halfWidth - 80, 100)));
                enemyShips.add(new Stage1ESBlue(new Coords(halfWidth, -50), player, new Coords(halfWidth, 90)));
                enemyShips.add(new Stage1ESRed(new Coords(halfWidth + 80,
                        -50), player, new Coords(halfWidth + 80, 100)));
                break;
            case 5:
                for (int i = -1; i <= 1; i += 2) {
                    enemyShips.add(
                            new Stage1ESRed(new Coords(halfWidth + i * 200,
                                    -50), player,
                                    new Coords(halfWidth + i * 200, 100)));
                    enemyShips.add(
                            new Stage1ESYellow(new Coords(halfWidth + i * 100,
                                    -50), player,
                                    new Coords(halfWidth + i * 100, 90)));
                }
                enemyShips.add(new Stage1ESBlue(new Coords(halfWidth, -50), player, new Coords(halfWidth, 80)));
                break;
            case 6:
                int left = BulletPanel.getBulletPanelWidth() / 2 - 200;
                int right = BulletPanel.getBulletPanelWidth() / 2 + 200;
                for (int i = left; i <= right; i += 50) {
                    enemyShips.add(new Stage1ESRed(new Coords(i, -50), player, new Coords(i, 100)));
                }
                break;
            case 7:
                enemyShips.add(new Stage1ESMiniboss(new Coords(halfWidth, -50), player, new Coords(halfWidth, 150)));
                break;
        }
    }

    private void updateStage5(int waveToSpawn) {
        switch (waveToSpawn) {
            case 0:
                enemyShips.add(new EnemyShipRed(new Coords(halfWidth, -50), player, new Coords(halfWidth, 100)));
                break;
            case 1:
                enemyShips
                        .add(new EnemyShipRed(new Coords(halfWidth - 100, -50), player,
                                new Coords(halfWidth - 100, 80)));
                enemyShips
                        .add(new EnemyShipRed(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 100)));
                enemyShips
                        .add(new EnemyShipRed(new Coords(halfWidth + 100, -50), player,
                                new Coords(halfWidth + 100, 80)));
                break;
            case 2:
                enemyShips
                        .add(new EnemyShipYellow(new Coords(halfWidth - 30, -50), player,
                                new Coords(halfWidth - 30, 100)));
                enemyShips
                        .add(new EnemyShipYellow(new Coords(halfWidth + 30, -50), player,
                                new Coords(halfWidth + 30, 100)));
                break;
            case 3:
                enemyShips
                        .add(new EnemyShipRed(new Coords(halfWidth - 100, -50), player,
                                new Coords(halfWidth - 100, 80)));
                enemyShips
                        .add(new EnemyShipYellow(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 100)));
                enemyShips
                        .add(new EnemyShipRed(new Coords(halfWidth + 100, -50), player,
                                new Coords(halfWidth + 100, 80)));
                break;
            case 4:
                enemyShips
                        .add(new EnemyShipGreen(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 100)));
                break;
            case 5:
                enemyShips
                        .add(new EnemyShipRed(new Coords(halfWidth - 100, -50), player,
                                new Coords(halfWidth - 100, 80)));
                enemyShips
                        .add(new EnemyShipGreen(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 80)));
                enemyShips
                        .add(new EnemyShipRed(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 120)));
                enemyShips
                        .add(new EnemyShipRed(new Coords(halfWidth + 100, -50), player,
                                new Coords(halfWidth + 100, 80)));
                break;
            case 6:
                enemyShips
                        .add(new EnemyShipBlue(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 100)));
                break;
            case 7:
                enemyShips
                        .add(new EnemyShipRed(new Coords(halfWidth - 50, -50), player,
                                new Coords(halfWidth - 50, 70)));
                enemyShips
                        .add(new EnemyShipRed(new Coords(halfWidth + 50, -50), player,
                                new Coords(halfWidth + 50, 70)));
                enemyShips
                        .add(new EnemyShipBlue(new Coords(halfWidth - 200, -50), player,
                                new Coords(halfWidth - 200, 170)));
                enemyShips
                        .add(new EnemyShipBlue(new Coords(halfWidth + 200, -50), player,
                                new Coords(halfWidth + 200, 170)));
                break;
            case 8:
                /*
                 * for (int i = 90; i <= BulletPanel.getBulletPanelWidth() - 90; i += 40) {
                 * enemyShips.add(new EnemyShipBlue(new Coords(i, 0), player, 5 + Math.abs(i -
                 * 270) / 40));
                 * }
                 */
                for (int i = 90; i <= BulletPanel.getBulletPanelWidth() - 90; i += 90) {
                    enemyShips
                            .add(new EnemyShipBlue(new Coords(i, -50), player,
                                    new Coords(i, 100 + 10 * (5 + 2 * Math.abs(i - 270) / 60))));
                }
                break;
            case 9:
                for (int i = -100; i <= 0; i += 50) {
                    enemyShips
                            .add(new EnemyShipYellow(new Coords(50, i), player, new Coords(50, i + 200)));
                    enemyShips
                            .add(new EnemyShipYellow(new Coords(halfWidth * 2 - 50, i), player, new Coords(
                                    halfWidth * 2 - 50, i + 200)));
                }
                break;
            case 10:
                for (int i = 0; i < 8; i++) {
                    enemyShips
                            .add(new EnemyShipGreen(new Coords(halfWidth, -50), player,
                                    new Coords(halfWidth + (int) (Math.cos(Math.PI * i / 4) * 100), 200 + (int) (Math
                                            .sin(Math.PI * i / 4) * 100))));
                }
                break;
            case 11:
                /*
                 * for (int i = -40; i <= 40; i += 40) {
                 * for (int j = 0; j >= -80; j -= 40) {
                 * enemyShips.add(
                 * new EnemyShipYellow(new Coords(BulletPanel.getBulletPanelWidth() / 2 + i, j),
                 * player, 9));
                 * }
                 * }
                 * break;
                 */
                enemyShips
                        .add(new EnemyShipYellow(new Coords(halfWidth - 100, -50), player,
                                new Coords(halfWidth - 100, 100)));
                enemyShips
                        .add(new EnemyShipBlue(new Coords(halfWidth, -50), player, new Coords(halfWidth, 60)));
                enemyShips
                        .add(new EnemyShipRed(new Coords(halfWidth, -50), player, new Coords(halfWidth, 140)));
                enemyShips
                        .add(new EnemyShipGreen(new Coords(halfWidth + 100, -50), player,
                                new Coords(halfWidth + 100, 100)));
                break;
            /*
             * case 12:
             * for (int i = 5; i <= 7; i++) {
             * enemyShips.add(new EnemyShipYellow(new Coords(40, 0), player, i));
             * enemyShips.add(new EnemyShipYellow(
             * new Coords(BulletPanel.getBulletPanelWidth() - 40, 0), player, i));
             * enemyShips.add(new EnemyShipGreen(new Coords(250, 0), player, i));
             * enemyShips.add(new EnemyShipGreen(
             * new Coords(BulletPanel.getBulletPanelWidth() - 250, 0), player, i));
             * enemyShips.add(new EnemyShipRed(new Coords(210, 0), player, i));
             * enemyShips.add(new EnemyShipRed(
             * new Coords(BulletPanel.getBulletPanelWidth() - 210, 0), player, i));
             * enemyShips.add(new EnemyShipRed(new Coords(170, 0), player, i));
             * enemyShips.add(new EnemyShipRed(
             * new Coords(BulletPanel.getBulletPanelWidth() - 170, 0), player, i));
             * }
             * break;
             */
            case 12:
                enemyShips.add(
                        new EnemyShipBossRed(new Coords(BulletPanel.getBulletPanelWidth() / 2, 0), player,
                                new Coords(BulletPanel.getBulletPanelWidth() / 2,
                                        BulletPanel.getBulletPanelHeight() / 4)));
                break;
        }
    }

    // removing items from arraylist moved to bulletpanel
    void update() {
        if (enemyShips.isEmpty()) {
            cooldown++;
            if (cooldown >= cooldownMax) {
                cooldown = 0;
                int temp = waveToSpawn % wavesPerStage[currentStage];
                switch (currentStage) {
                    case 1:
                        updateStage1(temp);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        updateStage5(temp);
                        break;
                }
                waveToSpawn++;
            }
        }

        player.update();
        playerBullets.addAll(player.spawnBullets());
        for (EnemyShip es : enemyShips) {
            es.update();
            enemyBullets.addAll(es.spawnBullets());
        }
        for (PlayerBullet pb : playerBullets) {
            pb.update();
        }
        for (EnemyBullet eb : enemyBullets) {
            eb.update();
        }
        for (ShipDeathAnimation sd : shipDeaths) {
            sd.update();
        }
        bulletPanel.repaint();
    }
}
