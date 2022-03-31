package bullethell.entity;

import bullethell.BulletPanel;
import bullethell.Coords;

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
    private int shootCooldown = 0, shootCooldownMax = 1;
    private PlayerKeyAdapter pka = new PlayerKeyAdapter();

    private int health = 100;

    public PlayerShip(Coords coords, int hitboxWidth, int hitboxHeight, String imagePath) {
        super(coords, hitboxWidth, hitboxHeight, imagePath);
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

    public int getHealth() {
        return health;
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

    public ArrayList<PlayerBullet> spawnBullets() {
        ArrayList<PlayerBullet> toReturn = new ArrayList<>();
        if (isShooting) {
            if (shootCooldown == 0) {
                int myX = getCoords().getX(), myY = getCoords().getY();
                if (moveSpeed == slowMoveSpeed) {
                    toReturn.add(new PlayerBulletNoAccel(new Coords(myX - 10, myY), 4, 0, -10));
                    toReturn.add(new PlayerBulletNoAccel(new Coords(myX + 10, myY), 4, 0, -10));
                } else {
                    toReturn.add(new PlayerBulletNoAccel(new Coords(myX - 10, myY), 4, -2, -10));
                    toReturn.add(new PlayerBulletNoAccel(new Coords(myX + 10, myY), 4, 2, -10));
                }
                shootCooldown = shootCooldownMax + 1;
            }
            shootCooldown--;
        } else {
            shootCooldown = 0;
        }
        return toReturn;
    }

    public void getHit(EnemyBullet eb) {
        health--;
    }
}
