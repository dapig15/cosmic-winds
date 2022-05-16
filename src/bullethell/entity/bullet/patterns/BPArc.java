package bullethell.entity.bullet.patterns;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularAccel;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletAngularRotational;
import bullethell.entity.ship.PlayerShip;

public class BPArc extends EnemyBulletPattern {
    private Coords origin;
    private int bulletCount, bulletRadius;
    private float velocity, startAngle, endAngle;

    public Coords getOrigin() {
        return origin;
    }

    public void setOrigin(Coords origin) {
        this.origin = origin;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    public int getBulletRadius() {
        return bulletRadius;
    }

    public void setBulletRadius(int bulletRadius) {
        this.bulletRadius = bulletRadius;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(float endAngle) {
        this.endAngle = endAngle;
    }

    public void shiftAngle(float inc) {
        this.startAngle += inc;
        this.endAngle += inc;
    }

    public BPArc(Coords origin, int bulletCount, int bulletRadius, float velocity, float startAngle,
            float endAngle) {
        this.origin = origin;
        this.bulletCount = bulletCount;
        this.bulletRadius = bulletRadius;
        this.velocity = velocity;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    public BPArc(Coords origin, int bulletCount, int bulletRadius, float velocity, float startAngle) {
        this.origin = origin;
        this.bulletCount = bulletCount;
        this.bulletRadius = bulletRadius;
        this.velocity = velocity;
        this.startAngle = startAngle;
        this.endAngle = startAngle + (float) (2 * Math.PI * (bulletCount - 1) / bulletCount);
    }

    @Override
    public void create(ArrayList<EnemyBullet> toReturn) {
        for (int i = 0; i < bulletCount; i++) {
            switch (getBulletType()) {
                case NORMAL:
                    toReturn.add(new EnemyBulletAngular(origin.deepClone(), bulletRadius, velocity,
                            startAngle + (endAngle - startAngle) * i / (bulletCount - 1)));
                    break;
                case HOMING:
                    toReturn.add(new EnemyBulletAngularHoming(origin.deepClone(), bulletRadius, velocity,
                            startAngle + (endAngle - startAngle) * i / (bulletCount - 1), getPsRef(), getHomingFrames(),
                            getHomingFactor()));
                    break;
                case ACCEL:
                    toReturn.add(new EnemyBulletAngularAccel(origin.deepClone(), bulletRadius, velocity,
                            startAngle + (endAngle - startAngle) * i / (bulletCount - 1), getAccelFactor()));
                    break;
                case ROTATIONAL:
                    toReturn.add(new EnemyBulletAngularRotational(origin.deepClone(), bulletRadius, velocity,
                            startAngle + (endAngle - startAngle) * i / (bulletCount - 1), getAccelFactor(),
                            getRotation()));
            }
        }
    }
}