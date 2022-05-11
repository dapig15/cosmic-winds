package bullethell.entity.bullet.patterns;

import java.util.ArrayList;

import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.ship.PlayerShip;

public abstract class EnemyBulletPattern {
    
    public static final int NORMAL = 0, HOMING = 1, ACCEL = 2, ROTATIONAL = 3;

    private int bulletType;
    private PlayerShip psRef;
    private int homingFrames;
    private float homingFactor;
    private float accelFactor;
    private float rotation;

    public int getBulletType() {
        return bulletType;
    }

    public void setBulletType(int bulletType) {
        this.bulletType = bulletType;
    }

    public int getHomingFrames() {
        return homingFrames;
    }

    public PlayerShip getPsRef() {
        return psRef;
    }

    public void setPsRef(PlayerShip psRef) {
        this.psRef = psRef;
    }

    public void setHomingFrames(int homingFrames) {
        this.homingFrames = homingFrames;
    }

    public float getAccelFactor() {
        return accelFactor;
    }

    public void setAccelFactor(float accelFactor) {
        this.accelFactor = accelFactor;
    }

    public float getHomingFactor() {
        return homingFactor;
    }

    public void setHomingFactor(float homingFactor) {
        this.homingFactor = homingFactor;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void primeHoming(PlayerShip psRef, int homingFrames, float homingFactor) {
        this.psRef = psRef;
        this.homingFrames = homingFrames;
        this.homingFactor = homingFactor;
    }

    public abstract void create(ArrayList<EnemyBullet> toReturn);
}