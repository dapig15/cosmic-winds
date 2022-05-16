package bullethell;

import bullethell.entity.*;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.PlayerBullet;
import bullethell.entity.ship.EnemyShip;
import bullethell.entity.ship.EnemyShipBigGreen;
import bullethell.entity.ship.EnemyShipBigRed;
import bullethell.entity.ship.EnemyShipBigYellow;
import bullethell.entity.ship.Stage3ESBlue;
import bullethell.entity.ship.Stage3ESBoss;
import bullethell.entity.ship.EnemyShipBossGreen;
import bullethell.entity.ship.EnemyShipBossRed;
import bullethell.entity.ship.Stage3ESGreen;
import bullethell.entity.ship.Stage3ESMiniboss;
import bullethell.entity.ship.Stage3ESRed;
import bullethell.entity.ship.Stage3ESYellow;
import bullethell.entity.ship.PlayerShip;
import bullethell.entity.ship.ShipDeathAnimation;
import bullethell.entity.ship.Stage1ESBlue;
import bullethell.entity.ship.Stage1ESBoss;
import bullethell.entity.ship.Stage1ESGreen;
import bullethell.entity.ship.Stage1ESMiniboss;
import bullethell.entity.ship.Stage1ESRed;
import bullethell.entity.ship.Stage1ESYellow;
import bullethell.entity.ship.Stage2ESBlue;
import bullethell.entity.ship.Stage2ESBoss;
import bullethell.entity.ship.Stage2ESGreen;
import bullethell.entity.ship.Stage2ESMiniboss;
import bullethell.entity.ship.Stage2ESRed;
import bullethell.entity.ship.Stage2ESYellow;
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
    private int waveToSpawn = 0, cooldown = -25;
    public final int cooldownMax = 50;
    private int currentStage = 1;
    private final int[] wavesPerStage = new int[] { 0, 15, 15, 15 };
    private float opacity = 0;
    private int termCount = 0;
    private boolean shouldTerminate = false, isGameOver = false;

    public GamePanel(int currentStage) {
        player = new PlayerShip(
                new Coords(BulletPanel.getBulletPanelWidth() / 2, BulletPanel.getBulletPanelHeight() * 3 / 4), 4, 4,
                "images/sprites/ships/shipTest.png",
                enemyShips, currentStage);
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

        this.currentStage = currentStage;

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

    public int getWaveToSpawn() {
        return waveToSpawn;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public boolean isInBetweenWaves() {
        return enemyShips.size() == 0;
    }

    public float getOpacity() {
        return opacity;
    }

    public int getTermCount() {
        return termCount;
    }

    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, null);
        FontGenerator.writeCenteredText(g, "STAGE " + currentStage,
                (50 + halfWidth * 2) + (getWidth() - (50 + halfWidth * 2)) / 2, 45, 3, Color.WHITE);
        g.setColor(new Color(50, 150, 155));
        g.fillRoundRect(90 + BulletPanel.getBulletPanelWidth(), 90, 410, 60, 20, 20);
        g.setColor(new Color(150, 250, 255));
        g.fillRoundRect(90 + BulletPanel.getBulletPanelWidth(), 90,
                Math.max(0, (int) (410f * (getPlayerShip().getHealth()) / 100)), 60, 20, 20);
        g.setColor(new Color(200, 200, 200));
        g.fillRoundRect(95 + BulletPanel.getBulletPanelWidth(), 95,
                Math.max(0, (int) (400f * getPlayerShip().getShield() / 5)), 50, 15, 15);
        if (player.getShield() > 0) {
            FontGenerator.writeText(g, "Shield: " + player.getShield(), 110 + BulletPanel.getBulletPanelWidth(), 108,
                    2.5f);
        } else {
            FontGenerator.writeText(g, "Armor: " + player.getHealth(), 110 + BulletPanel.getBulletPanelWidth(), 108,
                    2.5f);
        }
        g.setColor(new Color(150, 50, 50));
        g.fillRoundRect(90 + BulletPanel.getBulletPanelWidth(), 160, 410, 60, 20, 20);
        g.setColor(new Color(225, 100, 100));
        g.fillRoundRect(95 + BulletPanel.getBulletPanelWidth(), 165,
                Math.max(0, (int) (400f * (waveToSpawn) / 15)), 50, 15, 15);
        if (waveToSpawn == 15) {
            FontGenerator.writeText(g, "BOSS", 110 + BulletPanel.getBulletPanelWidth(), 178, 2.5f);
        } else if (waveToSpawn == 0) {
            FontGenerator.writeText(g, "Loading...", 110 + BulletPanel.getBulletPanelWidth(), 178, 2.5f);
        } else if ((waveToSpawn == 8 && currentStage == 1) || (waveToSpawn == 9 && currentStage != 1)) {
            FontGenerator.writeText(g, "MINIBOSS", 110 + BulletPanel.getBulletPanelWidth(), 178, 2.5f);
        } else {
            FontGenerator.writeText(g, "Wave " + waveToSpawn + "/15", 110 + BulletPanel.getBulletPanelWidth(), 178,
                    2.5f);
        }
        g.setColor(Color.gray);
        g.fillRoundRect(50 + BulletPanel.getBulletPanelWidth() + 40, 230,
                getWidth() - BulletPanel.getBulletPanelWidth() - 130, getHeight() - 280, 20, 20);
        g.setColor(Color.white);
        g.fillRoundRect(50 + BulletPanel.getBulletPanelWidth() + 50, 240,
                getWidth() - BulletPanel.getBulletPanelWidth() - 150, getHeight() - 300, 20, 20);
        final int textStartX = 50 + BulletPanel.getBulletPanelWidth() + 70;
        final int textStartY = 260;
        switch (currentStage * 100 + waveToSpawn) {
            case 101:
                FontGenerator.writeText(g,
                        "Use the arrow keys to move.\nDo not get hit by the red circles.\nShoot with Z.\n\n(For this stage only, getting hit\nwill not decrease your shield.)",
                        textStartX,
                        textStartY, 1.25f);
                break;
            case 102:
                FontGenerator.writeText(g,
                        "Hold down LEFT SHIFT to slow your ship!\nThis also focuses your fire.", textStartX,
                        textStartY, 1.25f);
                break;
            case 103:
                FontGenerator.writeText(g,
                        "Different bullets do different things.\nWatch out for yellow bullets!\nThey will come after you.",
                        textStartX,
                        textStartY, 1.25f);
                break;
            case 104:
                FontGenerator.writeText(g,
                        "When your shield is depleted,\nyour ship's armor is exposed.\nYou are prone to taking lots of damage then.\n\nYou regenerate one shield after a wave.\nTry not to lose all your shield!",
                        textStartX,
                        textStartY, 1.25f);
                break;
            case 105:
                FontGenerator.writeText(g,
                        "You can stay in the red rectangles!\nBut try not to for too long.",
                        textStartX,
                        textStartY, 1.25f);
                break;
            case 106:
                FontGenerator.writeText(g,
                        "Press C to auto-fire.\nNo more holding down Z!",
                        textStartX,
                        textStartY, 1.25f);
                break;
            default:
                FontGenerator.writeText(g, "Do not die!", textStartX,
                        textStartY, 1.25f);
        }
        if (waveToSpawn == 0) {
            float val = Math.min(1, (cooldown + 25) / 50f);
            g.setColor(new Color(0, 0, 0, 1 - val));
            opacity = 1 - val;
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (termCount > 0) {
            float val = 1 - (Math.max(0, (50 - termCount) / 50f));
            g.setColor(new Color(0, 0, 0, val));
            opacity = val;
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void updateStage1(int waveToSpawn) {
        switch (waveToSpawn) {
            case 0:
                player.setIsTutorial(true);
                enemyShips.add(new Stage1ESRed(new Coords(halfWidth, -50), player, new Coords(halfWidth, 100)));
                break;
            case 1:
                player.setIsTutorial(false);
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
                for (int i = halfWidth - 200; i <= halfWidth + 200; i += 50) {
                    enemyShips.add(new Stage1ESRed(new Coords(i, -50), player, new Coords(i, 100)));
                }
                break;
            case 7:
                enemyShips.add(new Stage1ESMiniboss(new Coords(halfWidth, -50), player, new Coords(halfWidth, 150)));
                break;
            case 8:
                enemyShips.add(new Stage1ESGreen(new Coords(halfWidth, -50), player, new Coords(halfWidth, 100)));
                break;
            case 9:
                for (int i = halfWidth - 200; i <= halfWidth + 200; i += 100) {
                    enemyShips
                            .add(new Stage1ESGreen(new Coords(i, -50), player,
                                    new Coords(i, 100 + Math.abs(halfWidth - i) / 2)));
                }
                break;
            case 10:
                for (int i = halfWidth - 200; i <= halfWidth + 200; i += 50) {
                    enemyShips
                            .add(new Stage1ESBlue(new Coords(i, -50), player, new Coords(i,
                                    50 + Math.abs(halfWidth - i) / 2)));
                }
                break;
            case 11:
                for (int i = halfWidth - 50; i <= halfWidth + 50; i += 50) {
                    for (int j = 75; j <= 175; j += 50) {
                        enemyShips.add(new Stage1ESYellow(new Coords(i, -50), player, new Coords(i, j)));
                    }
                }
                break;
            case 12:
                for (int i = 0; i < 8; i++) {
                    Coords targetCoords = new Coords((int) (halfWidth + 200 * Math.cos(i * Math.PI / 4)),
                            (int) (125 + 50 * Math.sin(i * Math.PI / 4)));
                    if (i % 2 == 0) {
                        enemyShips.add(new Stage1ESRed(new Coords(halfWidth, -50), player, targetCoords));
                    } else {
                        enemyShips.add(new Stage1ESGreen(new Coords(halfWidth, -50), player, targetCoords));
                    }
                }
                break;
            case 13:
                for (int i = 100; i <= 400; i += 50) {
                    for (int j = 50; j <= halfWidth * 2 - 50; j += halfWidth * 2 - 100) {
                        if (Math.abs(250 - i) == 150) {
                            enemyShips.add(new Stage1ESYellow(new Coords(j, -50), player, new Coords(j, i)));
                        } else if ((i / 10) % 2 == 1) {
                            enemyShips.add(new Stage1ESBlue(new Coords(j, -50), player, new Coords(j, i)));
                        } else {
                            enemyShips.add(new Stage1ESGreen(new Coords(j, -50), player, new Coords(j, i)));
                        }
                    }
                }
                break;
            case 14:
                enemyShips.add(new Stage1ESBoss(new Coords(halfWidth, -50), player, new Coords(halfWidth, 150)));
                break;
        }
    }

    private void updateStage2(int waveToSpawn) {
        switch (waveToSpawn) {
            case 0:
                enemyShips.add(new Stage2ESRed(new Coords(halfWidth, -50), player, new Coords(halfWidth, 100)));
                break;
            case 1:
                enemyShips
                        .add(new Stage2ESRed(new Coords(halfWidth, -50), player, new Coords(halfWidth - 100, 100)));
                enemyShips
                        .add(new Stage2ESRed(new Coords(halfWidth, -50), player, new Coords(halfWidth, 100)));
                enemyShips
                        .add(new Stage2ESRed(new Coords(halfWidth, -50), player, new Coords(halfWidth + 100, 100)));
                break;
            case 2:
                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 2; j++) {
                        if (Math.abs(i) + Math.abs(j) == 2) {
                            enemyShips.add(new Stage2ESRed(new Coords(halfWidth, -50), player,
                                    new Coords(halfWidth + 35 * i, 150 + 35 * j)));
                        }
                    }
                }
                break;
            case 3:
                for (int i = -1; i <= 1; i += 2) {
                    enemyShips.add(new Stage2ESYellow(new Coords(halfWidth + i * 180, -50), player,
                            new Coords(halfWidth + i * 180, 150)));
                }
                break;
            case 4:
                enemyShips.add(new Stage2ESGreen(new Coords(halfWidth, -50), player,
                        new Coords(halfWidth, BulletPanel.getBulletPanelHeight() / 2)));
                break;
            case 5:
                enemyShips.add(new Stage2ESGreen(new Coords(halfWidth, -50), player,
                        new Coords(halfWidth, BulletPanel.getBulletPanelHeight() / 2)));
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (Math.abs(i) + Math.abs(j) == 1) {
                            enemyShips.add(new Stage2ESRed(new Coords(halfWidth, -50), player,
                                    new Coords(halfWidth + 35 * i, BulletPanel.getBulletPanelHeight() / 2 + 35 * j)));
                        }
                    }
                }
                break;
            case 6:
                enemyShips.add(new Stage2ESGreen(new Coords(halfWidth, -50), player,
                        new Coords(halfWidth, BulletPanel.getBulletPanelHeight() / 2)));
                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 2; j++) {
                        if (Math.abs(i) + Math.abs(j) == 1) {
                            enemyShips.add(new Stage2ESYellow(new Coords(halfWidth, -50), player,
                                    new Coords(halfWidth + 35 * i, BulletPanel.getBulletPanelHeight() / 2 + 35 * j)));
                        }
                        if (Math.abs(i) + Math.abs(j) == 2) {
                            enemyShips.add(new Stage2ESRed(new Coords(halfWidth, -50), player,
                                    new Coords(halfWidth + 35 * i, BulletPanel.getBulletPanelHeight() / 2 + 35 * j)));
                        }
                    }
                }
                break;
            case 7:
                enemyShips.add(new Stage2ESBlue(new Coords(halfWidth, -50), player, new Coords(halfWidth, 150), 0));
                break;
            case 8:
                enemyShips.add(new Stage2ESMiniboss(new Coords(halfWidth, -50), player, new Coords(halfWidth, 150)));
                break;
            case 9:
                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 0; j++) {
                        if (Math.abs(i) + Math.abs(j) == 2) {
                            enemyShips.add(new Stage2ESRed(new Coords(halfWidth, -50), player,
                                    new Coords(halfWidth + i * ((halfWidth - 50) / 2),
                                            BulletPanel.getBulletPanelHeight() / 2
                                                    + j * ((BulletPanel.getBulletPanelHeight() / 2 - 50) / 2))));
                        }
                    }
                }
                break;
            case 10:
                for (int i = -2; i <= 2; i++) {
                    enemyShips.add(new Stage2ESBlue(new Coords(halfWidth, -50), player,
                            new Coords(halfWidth + i * 50, 150), (i + 2) * 5));
                }
                break;
            case 11:
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (Math.abs(i) + Math.abs(j) > 1) {
                            enemyShips.add(new Stage2ESGreen(new Coords(halfWidth, -50), player,
                                    new Coords(halfWidth + i * (halfWidth - 50), BulletPanel.getBulletPanelHeight() / 2
                                            + j * (BulletPanel.getBulletPanelHeight() / 2 - 50))));
                        }
                    }
                }
                break;
            case 12:
                for (int i = -3; i <= 3; i++) {
                    if (i == 0) {
                        enemyShips.add(new Stage2ESBlue(new Coords(halfWidth + i * 60, -50), player,
                                new Coords(halfWidth + i * 60, 150), 0));
                    } else if (Math.abs(i) == 1) {
                        enemyShips.add(new Stage2ESGreen(new Coords(halfWidth + i * 60, -50), player,
                                new Coords(halfWidth + i * 60, 150)));
                    } else {
                        enemyShips.add(new Stage2ESYellow(new Coords(halfWidth + i * 60, -50), player,
                                new Coords(halfWidth + i * 60, 150)));
                    }
                }
                break;
            case 13:
                for (int i = 80; i <= 140; i += 30) {
                    for (int j = -1; j <= 1; j += 2) {
                        enemyShips.add(new Stage2ESYellow(new Coords(halfWidth + 190 * j, -50), player,
                                new Coords(halfWidth + 190 * j, i)));
                        enemyShips.add(new Stage2ESRed(new Coords(halfWidth + 45 * j, -50), player,
                                new Coords(halfWidth + 75 * j, i)));
                        enemyShips.add(new Stage2ESGreen(new Coords(halfWidth + 15 * j, -50), player,
                                new Coords(halfWidth + 25 * j, i)));
                    }
                }
                break;
            case 14:
                enemyShips.add(new Stage2ESBoss(new Coords(halfWidth, -50), player, new Coords(halfWidth, 150)));
                break;
        }
    }

    private void updateStage3(int waveToSpawn) {
        switch (waveToSpawn) {
            case 0:
                enemyShips.add(new Stage3ESRed(new Coords(halfWidth, -50), player, new Coords(halfWidth, 100)));
                break;
            case 1:
                enemyShips
                        .add(new Stage3ESRed(new Coords(halfWidth - 100, -50), player,
                                new Coords(halfWidth - 100, 80)));
                enemyShips
                        .add(new Stage3ESRed(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 100)));
                enemyShips
                        .add(new Stage3ESRed(new Coords(halfWidth + 100, -50), player,
                                new Coords(halfWidth + 100, 80)));
                break;
            case 2:
                enemyShips
                        .add(new Stage3ESYellow(new Coords(halfWidth - 30, -50), player,
                                new Coords(halfWidth - 30, 100)));
                enemyShips
                        .add(new Stage3ESYellow(new Coords(halfWidth + 30, -50), player,
                                new Coords(halfWidth + 30, 100)));
                break;
            case 3:
                enemyShips
                        .add(new Stage3ESRed(new Coords(halfWidth - 100, -50), player,
                                new Coords(halfWidth - 100, 80)));
                enemyShips
                        .add(new Stage3ESYellow(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 100)));
                enemyShips
                        .add(new Stage3ESRed(new Coords(halfWidth + 100, -50), player,
                                new Coords(halfWidth + 100, 80)));
                break;
            case 4:
                enemyShips
                        .add(new Stage3ESGreen(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 100)));
                break;
            case 5:
                enemyShips
                        .add(new Stage3ESRed(new Coords(halfWidth - 100, -50), player,
                                new Coords(halfWidth - 100, 80)));
                enemyShips
                        .add(new Stage3ESGreen(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 80)));
                enemyShips
                        .add(new Stage3ESRed(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 120)));
                enemyShips
                        .add(new Stage3ESRed(new Coords(halfWidth + 100, -50), player,
                                new Coords(halfWidth + 100, 80)));
                break;
            case 6:
                enemyShips
                        .add(new Stage3ESBlue(new Coords(halfWidth, -50), player,
                                new Coords(halfWidth, 100)));
                break;
            case 7:
                enemyShips
                        .add(new Stage3ESRed(new Coords(halfWidth - 50, -50), player,
                                new Coords(halfWidth - 50, 70)));
                enemyShips
                        .add(new Stage3ESRed(new Coords(halfWidth + 50, -50), player,
                                new Coords(halfWidth + 50, 70)));
                enemyShips
                        .add(new Stage3ESBlue(new Coords(halfWidth - 200, -50), player,
                                new Coords(halfWidth - 200, 170)));
                enemyShips
                        .add(new Stage3ESBlue(new Coords(halfWidth + 200, -50), player,
                                new Coords(halfWidth + 200, 170)));
                break;
            case 8:
                enemyShips.add(new Stage3ESMiniboss(new Coords(halfWidth, -50), player, new Coords(halfWidth, 150)));
                break;
            case 9:
                for (int i = 90; i <= BulletPanel.getBulletPanelWidth() - 90; i += 90) {
                    enemyShips
                            .add(new Stage3ESBlue(new Coords(i, -50), player,
                                    new Coords(i, 100 + 10 * (5 + 2 * Math.abs(i - 270) / 60))));
                }
                break;
            case 10:
                for (int i = -100; i <= 0; i += 50) {
                    enemyShips
                            .add(new Stage3ESYellow(new Coords(50, i), player, new Coords(50, i + 200)));
                    enemyShips
                            .add(new Stage3ESYellow(new Coords(halfWidth * 2 - 50, i), player, new Coords(
                                    halfWidth * 2 - 50, i + 200)));
                }
                break;
            case 11:
                for (int i = 0; i < 8; i++) {
                    enemyShips
                            .add(new Stage3ESGreen(new Coords(halfWidth, -50), player,
                                    new Coords(halfWidth + (int) (Math.cos(Math.PI * i / 4) * 100), 200 + (int) (Math
                                            .sin(Math.PI * i / 4) * 100))));
                }
                break;
            case 12:
                for (int i = halfWidth - 200; i <= halfWidth + 200; i += 50) {
                    enemyShips.add(new Stage3ESRed(new Coords(i, -50), player,
                            new Coords(i, 100 + Math.abs(halfWidth - i) / 4)));
                }
                break;
            case 13:
                enemyShips
                        .add(new Stage3ESYellow(new Coords(halfWidth - 100, -50), player,
                                new Coords(halfWidth - 100, 100)));
                enemyShips
                        .add(new Stage3ESBlue(new Coords(halfWidth, -50), player, new Coords(halfWidth, 60)));
                enemyShips
                        .add(new Stage3ESRed(new Coords(halfWidth, -50), player, new Coords(halfWidth, 140)));
                enemyShips
                        .add(new Stage3ESGreen(new Coords(halfWidth + 100, -50), player,
                                new Coords(halfWidth + 100, 100)));
                break;
            case 14:
                enemyShips.add(new Stage3ESBoss(new Coords(halfWidth, -50), player, new Coords(halfWidth, 250)));
                break;
        }
    }

    // removing items from arraylist moved to bulletpanel
    void update() {
        if (termCount > 0) {
            termCount++;
            if (player.getHealth() == 0) {
                isGameOver = true;
            }
            if (termCount > 50) {
                shouldTerminate = true;
            }
            return;
        }
        if (enemyShips.isEmpty() && player.getHealth() > 0) {
            cooldown++;
            if (cooldown >= cooldownMax) {
                cooldown = 0;
                player.setShield(Math.min(5, player.getShield() + 1));
                int temp = waveToSpawn % wavesPerStage[currentStage];
                switch (currentStage) {
                    case 1:
                        updateStage1(temp);
                        break;
                    case 2:
                        updateStage2(temp);
                        break;
                    case 3:
                        updateStage3(temp);
                        break;
                }
                waveToSpawn++;
                if (waveToSpawn == 1) {
                    cooldown -= 150;
                } else if (waveToSpawn == 15) {
                    cooldown = -1000000000;
                }
            }
        }

        if (player.isPressingSpace() && ((waveToSpawn == 15 && cooldown > -999999900) || player.getHealth() == 0)) {
            termCount++;
        }
        if (player.getHealth() > 0) {
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
}
