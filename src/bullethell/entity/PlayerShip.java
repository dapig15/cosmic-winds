package bullethell.entity;

import bullethell.Coords;
import utility.Hitbox;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerShip extends Ship {

    private int right = KeyEvent.VK_RIGHT, altRight = KeyEvent.VK_D;
    private int left = KeyEvent.VK_LEFT, altLeft = KeyEvent.VK_A;
    private int down = KeyEvent.VK_DOWN, altDown = KeyEvent.VK_S;
    private int up = KeyEvent.VK_UP, altUp = KeyEvent.VK_W;
    private int moveSpeed = 5;
    private boolean moveRight, moveLeft, moveDown, moveUp;
    private PlayerKeyAdapter pka = new PlayerKeyAdapter();

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
        }
    }
}
