package bullethell.entity.ship;

import java.util.ArrayDeque;
import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletShape;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage2ESYellow extends EnemyShipBasic {

    public Stage2ESYellow(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 32, 32, "images/sprites/ships/stage_2_ship_yellow.png", 60, targetCoords, psRef);
    }

    ArrayDeque<EnemyBulletShape> ebss = new ArrayDeque<>();

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 80)) {
            for (int i = 0; i < 12; i++) {
                EnemyBulletShape ebs = new EnemyBulletShape(getCoords().deepClone(), 6, 0.02f,
                        (float) (Math.PI * i / 6),
                        (float) (4 * Math.cos(angleToPlayer())),
                        (float) (4 * Math.sin(angleToPlayer())),
                        60, 30, true);
                toReturn.add(ebs);
                ebss.add(ebs);
            }
        }
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 80, 80)) {
            if (ebss.size() == 12) {
                for (int i = 0; i < 12; i++) {
                    EnemyBulletShape ebs = ebss.poll();
                    if (i % 2 == 0) {
                        toReturn.add(new EnemyBulletAngular(ebs.getCoords().deepClone(), 6, 6,
                                ebs.getCoords().angleTo(getPsRef().getCoords())));
                    } else {
                        toReturn.add(new EnemyBulletAngularHoming(ebs.getCoords().deepClone(), 6, 10,
                                ebs.getCoords().angleTo(getPsRef().getCoords()), getPsRef(), 15, 0.2f));
                    }
                    ebs.setKillMe(true);
                }
            }
        }
        return toReturn;
    }

}
