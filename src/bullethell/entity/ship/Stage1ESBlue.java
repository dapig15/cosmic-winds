package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletLaser;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BPWave;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage1ESBlue extends EnemyShipBasic {

    private float angle;
    private BPWave waveAttack = new BPWave(getCoords(), 5, 6, 0, 15, 2, 0);

    public Stage1ESBlue(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 32, 32, "images/sprites/ships/stage_1_ship_blue.png", 30, targetCoords, psRef);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 100)) {
            angle = angleToPlayer();
            waveAttack.setAngle(angle);
            toReturn.add(new EnemyBulletLaser(getCoords(), 6, 1500, angle, 50, -1));
        }
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 100, 50, 50)) {
            waveAttack.create(toReturn);
        }
        return toReturn;
    }

}
