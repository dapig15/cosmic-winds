package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage1ESYellow extends EnemyShipBasic {

    public Stage1ESYellow(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 32, 32, "images/sprites/ships/stage_1_ship_yellow.png", 30, targetCoords, psRef);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 40)) {
            for (int i = getCoords().getX() - 10; i <= getCoords().getX() + 10; i += 20) {
                toReturn.add(
                        new EnemyBulletAngular(new Coords(i, getCoords().getY()), 6, 5, (float) (Math.PI / 2)));
            }
        }
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 100)) {
            for (int i = -1; i <= 1; i += 2) {
                toReturn.add(new EnemyBulletAngularHoming(getCoords().deepClone(), 6, 6, angleToPlayer() + i,
                        getPsRef(), 60, 0.05f));
            }
        }
        return toReturn;
    }

}
