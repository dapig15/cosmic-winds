package bullethell.entity.ship;

import bullethell.BulletPanel;
import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.PlayerBullet;
import bullethell.entity.bullet.PlayerBulletAngular;
import bullethell.entity.bullet.PlayerBulletAngularHoming;
import bullethell.entity.bullet.PlayerBulletBomb;
import bullethell.entity.bullet.PlayerBulletNoAccel;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PlayerShip extends Ship {

    private int right = KeyEvent.VK_RIGHT, altRight = KeyEvent.VK_D;
    private int left = KeyEvent.VK_LEFT, altLeft = KeyEvent.VK_A;
    private int down = KeyEvent.VK_DOWN, altDown = KeyEvent.VK_S;
    private int up = KeyEvent.VK_UP, altUp = KeyEvent.VK_W;
    private int slow = KeyEvent.VK_SHIFT;
    private int shoot = KeyEvent.VK_Z;
    private int moveSpeed = 12;
    private int defaultMoveSpeed = 12, slowMoveSpeed = 4;
    private boolean moveRight, moveLeft, moveDown, moveUp, isShooting;
    private PlayerKeyAdapter pka = new PlayerKeyAdapter();
    private ArrayList<EnemyShip> esListRef;

    public PlayerShip(Coords coords, int hitboxWidth, int hitboxHeight, String imagePath,
            ArrayList<EnemyShip> esListRef) {
        super(coords, hitboxWidth, hitboxHeight, imagePath, 100);
        this.esListRef = esListRef;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public PlayerKeyAdapter getPKA() {
        return pka;
    }

    @Override
    public void shiftCoords(int xInc, int yInc) {
        super.shiftCoords(xInc, yInc);
        getCoords().setX(Math.max(0, Math.min(getCoords().getX(), BulletPanel.getBulletPanelWidth())));
        getCoords().setY(Math.max(0, Math.min(getCoords().getY(), BulletPanel.getBulletPanelHeight())));
    }

    class PlayerKeyAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == right || key == altRight) {
                moveRight = true;
            }
            if (key == left || key == altLeft) {
                moveLeft = true;
            }
            if (key == down || key == altDown) {
                moveDown = true;
            }
            if (key == up || key == altUp) {
                moveUp = true;
            }
            if (key == slow) {
                moveSpeed = slowMoveSpeed;
            }
            if (key == shoot) {
                isShooting = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == right || key == altRight) {
                moveRight = false;
            }
            if (key == left || key == altLeft) {
                moveLeft = false;
            }
            if (key == down || key == altDown) {
                moveDown = false;
            }
            if (key == up || key == altUp) {
                moveUp = false;
            }
            if (key == slow) {
                moveSpeed = defaultMoveSpeed;
            }
            if (key == shoot) {
                isShooting = false;
            }
        }
    }

    @Override
    public void update() {
        if (moveRight) {
            shiftCoords(moveSpeed, 0);
        }
        if (moveLeft) {
            shiftCoords(-moveSpeed, 0);
        }
        if (moveDown) {
            shiftCoords(0, moveSpeed);
        }
        if (moveUp) {
            shiftCoords(0, -moveSpeed);
        }
    }

    int normalShotCooldown = 0, normalShotMaxCooldown = 4;
    int bombShotCooldown = 0, bombShotMaxCooldown = 100;
    int waveCooldown = 0, waveMaxCooldown = 40;

    private ArrayList<PlayerBulletBomb> pbbs = new ArrayList<>();
    boolean hasFoundClosestEnemyThisFrame = false;
    EnemyShip closestShip;
    float angleToClosestShip;

    public ArrayList<PlayerBullet> spawnBullets() {
        ArrayList<PlayerBullet> toReturn = new ArrayList<>();
        hasFoundClosestEnemyThisFrame = false;
        for (int i = 0; i < pbbs.size(); i++) {
            PlayerBulletBomb pbb = pbbs.get(i);
            if (pbb.isKillMe()) {
                pbbs.remove(i);
                i--;
            } else if (pbb.isDeflating() && pbb.getFramesAlive() % 3 == 0) {
                if (!hasFoundClosestEnemyThisFrame) {
                    float distanceTo = Float.MAX_VALUE;
                    for (EnemyShip eShip : esListRef) {
                        float temp;
                        if ((temp = eShip.getCoords().distanceTo(getCoords())) < distanceTo) {
                            distanceTo = temp;
                            closestShip = eShip;
                        }
                    }
                    hasFoundClosestEnemyThisFrame = true;
                    angleToClosestShip = getCoords().angleTo(closestShip.getCoords());
                }
                for (float j = angleToClosestShip - 1.5f; j <= angleToClosestShip + 1.5f; j += 0.75f) {
                    toReturn.add(new PlayerBulletAngularHoming(pbb.getCoords().deepClone(), 6, 12, j, closestShip, 37,
                            0.1f));
                }
            }
        }
        if (isShooting) {
            if (normalShotCooldown == 0) {
                int myX = getCoords().getX(), myY = getCoords().getY();
                if (moveSpeed == slowMoveSpeed) {
                    toReturn.add(new PlayerBulletNoAccel(new Coords(myX - 10, myY), 4, 0, -10));
                    toReturn.add(new PlayerBulletNoAccel(new Coords(myX + 10, myY), 4, 0, -10));
                } else {
                    toReturn.add(new PlayerBulletNoAccel(new Coords(myX - 10, myY), 4, -2, -10));
                    toReturn.add(new PlayerBulletNoAccel(new Coords(myX + 10, myY), 4, 2, -10));
                }
                normalShotCooldown = normalShotMaxCooldown;
            }
            if (bombShotCooldown == 0) {
                int myX = getCoords().getX(), myY = getCoords().getY();
                PlayerBulletBomb pbb = new PlayerBulletBomb(getCoords().deepClone(), 5, 20, new Coords(myX, myY - 100),
                        0.04f, 50, 20);
                toReturn.add(pbb);
                pbbs.add(pbb);
                bombShotCooldown = bombShotMaxCooldown;
            }
            if (waveCooldown == 0) {
                for (int i = 5; i <= 10; i++) {
                    for (float j = (float) Math.PI * 11 / 8; j <= (float) Math.PI * 13.1 / 8; j += (float) Math.PI
                            / 16) {
                        PlayerBulletAngular pbna = new PlayerBulletAngular(getCoords().deepClone(), i, i, j,
                                new Color(255 - (i * 10), 0, i * 20));
                        toReturn.add(pbna);
                    }
                }
                waveCooldown = waveMaxCooldown;
            }
        }
        normalShotCooldown = Math.max(0, normalShotCooldown - 1);
        bombShotCooldown = Math.max(0, bombShotCooldown - 1);
        waveCooldown = Math.max(0, waveCooldown - 1);
        return toReturn;
    }

    @Override
    public void getHit(Bullet eb) {
        setHealth(getHealth() - eb.getDamage());
    }
}
