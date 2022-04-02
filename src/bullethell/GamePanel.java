package bullethell;

import bullethell.entity.*;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;

import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private BulletPanel bulletPanel;
    private JLabel label;

    private PlayerShip player;
    private ArrayList<EnemyShip> enemyShips = new ArrayList<>();
    private ArrayList<PlayerBullet> playerBullets = new ArrayList<>();
    private ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();

    public GamePanel() {
        player = new PlayerShip(
                new Coords(BulletPanel.getBulletPanelWidth() / 2, BulletPanel.getBulletPanelHeight() * 3 / 4), 4, 4,
                "shipTest.png");
        this.addKeyListener(player.getPKA());
        this.setFocusable(true);
        PlayerBullet.setEnemyShipReference(enemyShips);
        EnemyBullet.setPlayerReference(player);

        label = new JLabel();
        label.setBounds(600, 40, 100, 100);
        this.add(label);
        label.setText("hp: " + player.getHealth());

        this.setBackground(Color.RED);
        this.setLayout(null);

        bulletPanel = new BulletPanel(this);
        this.add(bulletPanel);

        counter--;
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

    private int counter = 13;
    private int cooldown = 50;
    private final int WAVES = 14, COOLDOWN_MAX = 50;

    // removing items from arraylist moved to bulletpanel
    public void update() {
        if (enemyShips.isEmpty()) {
            cooldown++;
            if (cooldown >= COOLDOWN_MAX) {
                counter++;
                cooldown = 0;
                switch (counter % WAVES) {
                    case 0:
                        enemyShips
                                .add(new EnemyShipRed(new Coords(BulletPanel.getBulletPanelWidth() / 2, 0), player,
                                        10));
                        break;
                    case 1:
                        enemyShips
                                .add(new EnemyShipRed(new Coords(BulletPanel.getBulletPanelWidth() / 2 - 100, 0),
                                        player,
                                        10));
                        enemyShips
                                .add(new EnemyShipRed(new Coords(BulletPanel.getBulletPanelWidth() / 2, 0), player,
                                        10));
                        enemyShips
                                .add(new EnemyShipRed(new Coords(BulletPanel.getBulletPanelWidth() / 2 + 100, 0),
                                        player,
                                        10));
                        break;
                    case 2:
                        enemyShips
                                .add(new EnemyShipYellow(new Coords(BulletPanel.getBulletPanelWidth() / 2, 0), player,
                                        10));
                        break;
                    case 3:
                        enemyShips
                                .add(new EnemyShipRed(new Coords(BulletPanel.getBulletPanelWidth() / 2 - 100, 0),
                                        player,
                                        10));
                        enemyShips
                                .add(new EnemyShipYellow(new Coords(BulletPanel.getBulletPanelWidth() / 2, 0), player,
                                        10));
                        enemyShips
                                .add(new EnemyShipRed(new Coords(BulletPanel.getBulletPanelWidth() / 2 + 100, 0),
                                        player,
                                        10));
                        break;
                    case 4:
                        enemyShips
                                .add(new EnemyShipGreen(new Coords(BulletPanel.getBulletPanelWidth() / 2, 0), player,
                                        10));
                        break;
                    case 5:
                        enemyShips
                                .add(new EnemyShipRed(new Coords(BulletPanel.getBulletPanelWidth() / 2 - 100, 0),
                                        player,
                                        10));
                        enemyShips
                                .add(new EnemyShipGreen(new Coords(BulletPanel.getBulletPanelWidth() / 2, 0), player,
                                        10));
                        enemyShips
                                .add(new EnemyShipRed(new Coords(BulletPanel.getBulletPanelWidth() / 2 + 100, 0),
                                        player,
                                        10));
                        break;
                    case 6:
                        for (int i = 90; i <= BulletPanel.getBulletPanelWidth() - 90; i += 40) {
                            enemyShips.add(new EnemyShipRed(new Coords(i, 0), player, 5 + Math.abs(i - 270) / 40));
                        }
                        break;
                    case 7:
                        for (int i = -40; i <= 40; i += 40) {
                            for (int j = 0; j >= -80; j -= 40) {
                                enemyShips.add(
                                        new EnemyShipYellow(new Coords(BulletPanel.getBulletPanelWidth() / 2 + i, j),
                                                player, 9));
                            }
                        }
                        break;
                    case 8:
                        for (int i = 90; i <= BulletPanel.getBulletPanelWidth() - 90; i += 60) {
                            enemyShips
                                    .add(new EnemyShipGreen(new Coords(i, 0), player, 5 + 2 * Math.abs(i - 270) / 60));
                        }
                        break;
                    case 9:
                        for (int i = 5; i <= 7; i++) {
                            enemyShips.add(new EnemyShipYellow(new Coords(40, 0), player, i));
                            enemyShips.add(new EnemyShipYellow(
                                    new Coords(BulletPanel.getBulletPanelWidth() - 40, 0), player, i));
                            enemyShips.add(new EnemyShipGreen(new Coords(250, 0), player, i));
                            enemyShips.add(new EnemyShipGreen(
                                    new Coords(BulletPanel.getBulletPanelWidth() - 250, 0), player, i));
                            enemyShips.add(new EnemyShipRed(new Coords(210, 0), player, i));
                            enemyShips.add(new EnemyShipRed(
                                    new Coords(BulletPanel.getBulletPanelWidth() - 210, 0), player, i));
                            enemyShips.add(new EnemyShipRed(new Coords(170, 0), player, i));
                            enemyShips.add(new EnemyShipRed(
                                    new Coords(BulletPanel.getBulletPanelWidth() - 170, 0), player, i));
                        }
                        break;
                    case 10:
                        enemyShips.add(new EnemyShipBigRed(new Coords(150, 0), player));
                        enemyShips.add(
                                new EnemyShipBigRed(new Coords(BulletPanel.getBulletPanelWidth() - 150, 0), player));
                        break;
                    case 11:
                        enemyShips.add(new EnemyShipBigYellow(new Coords(150, 0), player));
                        enemyShips.add(
                                new EnemyShipBigYellow(new Coords(BulletPanel.getBulletPanelWidth() - 150, 0), player));
                        break;
                    case 12:
                        enemyShips.add(new EnemyShipBigGreen(new Coords(150, 0), player));
                        enemyShips.add(
                                new EnemyShipBigGreen(new Coords(BulletPanel.getBulletPanelWidth() - 150, 0), player));
                        break;
                    case 13:
                        enemyShips.add(
                                new EnemyShipBossRed(new Coords(BulletPanel.getBulletPanelWidth() / 2, 0), player,
                                        new Coords(BulletPanel.getBulletPanelWidth() / 2,
                                                BulletPanel.getBulletPanelHeight() / 4)));
                        break;
                }
            }
        }
        player.update();
        playerBullets.addAll(player.spawnBullets());
        for (EnemyShip es : enemyShips) {
            es.update();
            enemyBullets.addAll(es.spawnBullets());
        }
        for (int i = 0; i < playerBullets.size(); i++) {
            PlayerBullet pb = playerBullets.get(i);
            pb.update();
        }
        for (int i = 0; i < enemyBullets.size(); i++) {
            EnemyBullet eb = enemyBullets.get(i);
            eb.update();
            if (eb.isKillMe()) {
                label.setText("hp: " + player.getHealth());
            }
        }
        bulletPanel.repaint();
    }
}
