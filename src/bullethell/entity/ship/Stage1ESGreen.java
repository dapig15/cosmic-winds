package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletShape;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage1ESGreen extends EnemyShipBasic {

    public Stage1ESGreen(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 32, 32, "images/sprites/ships/stage_1_ship_green.png", 30, targetCoords, psRef);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 70)) {
            for (int i = 0; i < 8; i++) {
                toReturn.add(
                        new EnemyBulletShape(getCoords().deepClone(), 6, 0.02f, (float) (Math.PI * i / 4),
                                (float) (4 * Math.cos(angleToPlayer())),
                                (float) (4 * Math.sin(
                                        angleToPlayer())),
                                30,
                                30, true));
            }
        }
        return toReturn;
    }

}
