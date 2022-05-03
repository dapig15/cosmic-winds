package bullethell.entity.ship;

import java.io.File;
import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularAccel;
import bullethell.entity.bullet.EnemyBulletLaser;
import bullethell.entity.bullet.EnemyBulletNoAccel;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BPWave;
import bullethell.entity.bullet.patterns.BulletPatternCaller;
import bullethell.entity.bullet.patterns.BulletPatternCaller.IntervalCalculator;

public class EnemyShipRed extends EnemyShipBasic {

    public EnemyShipRed(Coords coords, PlayerShip psRef, int initialYVel) {
        super(coords, 32, 32, "images/sprites/ships/stage_5_ship_red.png", 30, psRef, initialYVel);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 1)) {
            toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 8,
                    angleToPlayer() + (float) (Math.sin(getFramesAlive() * 0.05f) * Math.PI)));
            toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 8,
                    angleToPlayer() - (float) (Math.sin(getFramesAlive() * 0.05f) * Math.PI)));
        }
        return toReturn;
    }

}
