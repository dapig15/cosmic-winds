package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularRotational;
import bullethell.entity.bullet.EnemyBulletBomb;
import bullethell.entity.bullet.EnemyBulletLaser;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage3ESBlue extends EnemyShipBasic {

    BPArc circleAttack = new BPArc(getCoords(), 16, 6, 5, 0);
    float angle = 0;

    public Stage3ESBlue(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 32, 32, "images/sprites/ships/stage_3_ship_blue.png", 100, targetCoords, psRef);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 200)) {
            angle = angleToPlayer();
            for (int i = -1; i <= 1; i += 2) {
                toReturn.add(
                        new EnemyBulletLaser(getCoords().deepClone(), 6, 1000, angleToPlayer() + i * 0.3f, 50, -1));
            }
        }
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 200, 50, 150)) {
            for (int i = 0; i < 6; i++) {
                toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 15, (int) (Math.random() * 11) + 5,
                        angle + (float) (Math.random() * 0.5f - 0.25f)));
            }
        }
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 25)) {
            circleAttack.create(toReturn);
            circleAttack.shiftAngle((float) (Math.PI / 32));
        }
        return toReturn;
    }

}
