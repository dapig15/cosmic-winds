package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularAccel;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletAngularRotational;
import bullethell.entity.bullet.EnemyBulletNoAccel;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class EnemyShipYellow extends EnemyShipBasic {
    private BPArc arcAttackRight = new BPArc(getCoords(), 12, 6, 6, (float) (-Math.PI / 32), (float) (Math.PI / 32));
    private BPArc arcAttackLeft = new BPArc(getCoords(), 12, 6, 6, (float) (Math.PI * 31 / 32),
            (float) (Math.PI * 33 / 32));

    public EnemyShipYellow(Coords coords, PlayerShip psRef, int initialYVel) {
        super(coords, 26, 26, "images/sprites/ships/stage_5_ship_yellow.png", 30, psRef, initialYVel);
        arcAttackRight.setBulletType(BPArc.ACCEL);
        arcAttackRight.setAccelFactor(-0.1f);
        arcAttackLeft.setBulletType(BPArc.ACCEL);
        arcAttackLeft.setAccelFactor(-0.1f);
    }

    private ArrayList<EnemyBulletAngularAccel> ebaas = new ArrayList<>();

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        for (int i = 0; i < ebaas.size(); i++) {
            EnemyBulletAngularAccel ebaa = ebaas.get(i);
            if (ebaa.isKillMe()) {
                ebaas.remove(i);
                i--;
            } else if (ebaa.getFramesAlive() == 30) {
                toReturn.add(new EnemyBulletAngularHoming(ebaa.getCoords().deepClone(), 6, 5, ebaa.getAngle(),
                        getPsRef(), 50, 0.2f));
            } else if (ebaa.getFramesAlive() > 30) {
                ebaa.setKillMe(true);
            }
        }
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 10)) {
            for (int i = -5; i <= 5; i++) {
                float angle = angleToPlayer() + i * 0.1f;
                if (i % 2 == 1) {
                    EnemyBulletAngularAccel ebaa = new EnemyBulletAngularAccel(getCoords().deepClone(), 6, 10,
                            angle, -0.1f);
                    toReturn.add(ebaa);
                    ebaas.add(ebaa);
                } else {
                    toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 10, angle));
                }
            }
            EnemyBulletAngularAccel ebaa = new EnemyBulletAngularAccel(getCoords().deepClone(), 6, 10, angleToPlayer(),
                    -0.2f);
            toReturn.add(ebaa);
            ebaas.add(ebaa);
        }
        /*
         * if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 10)) {
         * arcAttackRight.create(toReturn);
         * arcAttackRight.shiftAngle(-(float) (Math.PI / 32));
         * arcAttackLeft.create(toReturn);
         * arcAttackLeft.shiftAngle((float) (Math.PI / 32));
         * }
         */
        return toReturn;
    }

}
