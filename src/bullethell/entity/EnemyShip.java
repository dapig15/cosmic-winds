package bullethell.entity;

import java.util.ArrayList;

import bullethell.*;
import bullethell.entity.bullet.EnemyBullet;

import utility.Hitbox;

public abstract class EnemyShip extends Ship {

    private Hitbox hitbox;
    private int framesAlive = 0;
    private boolean killMe = false;

    public EnemyShip(Coords coords, int hitboxWidth, int hitboxHeight, String imagePath, int health) {
        super(coords, hitboxWidth, hitboxHeight, imagePath, health);
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public int getFramesAlive() {
        return framesAlive;
    }

    public boolean isKillMe() {
        return killMe;
    }

    public void setKillMe(boolean killMe) {
        this.killMe = killMe;
    }

    @Override
    public void update() {
        int xInc = findXDisp();
        int yInc = findYDisp();
        shiftCoords(xInc, yInc);
        this.hitbox = new Hitbox(getCoords().getX() - getHitboxWidth() / 2, getCoords().getY() - getHitboxHeight() / 2,
                getCoords().getX() + getHitboxWidth() / 2, getCoords().getY() + getHitboxHeight() / 2);
        spawnBullets();
        framesAlive++;
    }

    public abstract int findXDisp();

    public abstract int findYDisp();

    public abstract ArrayList<EnemyBullet> spawnBullets();

}
