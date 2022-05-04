package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage1ESRed extends EnemyShipBasic {

    private BPArc circleAttack = new BPArc(getCoords(), 6, 6, 3, 0);

    public Stage1ESRed(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 32, 32, "images/sprites/ships/stage_1_ship_red.png", 30, targetCoords, psRef);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 50)) {
            toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 5,
                    angleToPlayer()));
        }
        BulletPatternCaller.processPattern(toReturn, circleAttack, getFramesAlive(), 50, 80);
        return toReturn;
    }

}
