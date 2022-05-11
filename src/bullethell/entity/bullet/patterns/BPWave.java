package bullethell.entity.bullet.patterns;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularAccel;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletAngularRotational;

public class BPWave extends EnemyBulletPattern {
    private Coords origin;
    private int bulletCount, startRadius, radiusInc;
    private float velocity, velocityInc, angle;

    public void setOrigin(Coords origin) {
        this.origin = origin;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public BPWave(Coords origin, int bulletCount, int startRadius, int radiusInc, float velocity, float velocityInc,
            float angle) {
        this.origin = origin;
        this.bulletCount = bulletCount;
        this.startRadius = startRadius;
        this.radiusInc = radiusInc;
        this.velocity = velocity;
        this.velocityInc = velocityInc;
        this.angle = angle;
    }

    @Override
    public void create(ArrayList<EnemyBullet> toReturn) {
        for (int i = 0; i < bulletCount; i++) {
            switch (getBulletType()) {
                case NORMAL:
                    toReturn.add(new EnemyBulletAngular(origin.deepClone(), startRadius + i * radiusInc,
                            velocity + i * velocityInc, angle));
                    break;
                case HOMING:
                    toReturn.add(new EnemyBulletAngularHoming(origin.deepClone(), startRadius + i * radiusInc,
                            velocity + i * velocityInc, angle, getPsRef(), getHomingFrames(), getHomingFactor()));
                    break;
                case ACCEL:
                    toReturn.add(new EnemyBulletAngularAccel(origin.deepClone(), startRadius + i * radiusInc,
                            velocity + i * velocityInc, angle, getAccelFactor()));
                    break;
                case ROTATIONAL:
                    toReturn.add(new EnemyBulletAngularRotational(origin.deepClone(), startRadius + i * radiusInc,
                            velocity + i * velocityInc, angle, getAccelFactor(), getRotation()));
            }
        }
    }
}
