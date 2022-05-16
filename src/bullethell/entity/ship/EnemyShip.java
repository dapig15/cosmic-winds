package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.*;
import bullethell.entity.bullet.EnemyBullet;

import utility.Hitbox;

public abstract class EnemyShip extends Ship {

    private Hitbox hitbox;
    private int framesAlive = 0;
    private boolean killMe = false;

    private Coords targetCoords;
    private float shipSpeed = 0.05f;

    public EnemyShip(Coords coords, int hitboxWidth, int hitboxHeight, String imagePath, int health,
            Coords targetCoords) {
        super(coords, hitboxWidth, hitboxHeight, imagePath, health);
        this.targetCoords = targetCoords;
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
        framesAlive++;
    }

    public Coords getTargetCoords() {
        return targetCoords;
    }

    public void setTargetCoords(Coords targetCoords) {
        this.targetCoords = targetCoords;
    }

    public float getShipSpeed() {
        return shipSpeed;
    }

    public void setShipSpeed(float shipSpeed) {
        this.shipSpeed = shipSpeed;
    }

    public int findXDisp() {
        if (getCoords().getX() == targetCoords.getX()) {
            return 0;
        }
        float inc = -(getCoords().getX() - targetCoords.getX()) * shipSpeed;
        int newInc = (int) Math.ceil(Math.abs(inc));
        if (inc < 0) {
            newInc = -newInc;
        }
        return newInc;
    }

    public int findYDisp() {
        if (getCoords().getY() == targetCoords.getY()) {
            return 0;
        }
        float inc = -(getCoords().getY() - targetCoords.getY()) * shipSpeed;
        int newInc = (int) Math.ceil(Math.abs(inc));
        if (inc < 0) {
            newInc = -newInc;
        }
        return newInc;
    }

    public abstract ArrayList<EnemyBullet> spawnBullets();

}
