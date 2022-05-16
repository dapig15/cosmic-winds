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
import bullethell.entity.bullet.patterns.EnemyBulletPattern;

public class Stage2ESGreen extends EnemyShipBasic {

    private BPArc bigCircleAttack = new BPArc(getCoords(), 12, 6, 5, 0);
    private BPArc smallCircleAttack = new BPArc(getCoords(), 12, 6, 3, 0);

    public Stage2ESGreen(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 32, 32, "images/sprites/ships/stage_2_ship_green.png", 60, targetCoords, psRef);
        bigCircleAttack.setAccelFactor(0.1f);
        bigCircleAttack.setBulletType(EnemyBulletPattern.ROTATIONAL);
        smallCircleAttack.setAccelFactor(0.1f);
        smallCircleAttack.setBulletType(EnemyBulletPattern.ROTATIONAL);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 65)) {
            bigCircleAttack.setRotation(0.02f);
            smallCircleAttack.setRotation(-0.02f);
            if (getPsRef().getCoords().getX() < getCoords().getX()) {
                bigCircleAttack.setRotation(-0.02f);
                smallCircleAttack.setRotation(0.02f);
            }
            bigCircleAttack.create(toReturn);
            smallCircleAttack.create(toReturn);
        }
        return toReturn;
    }

}
