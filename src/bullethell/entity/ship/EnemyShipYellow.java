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

    public EnemyShipYellow(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 26, 26, "images/sprites/ships/stage_5_ship_yellow.png", 30, targetCoords, psRef);
        arcAttackRight.setBulletType(BPArc.ACCEL);
        arcAttackRight.setAccelFactor(-0.1f);
        arcAttackLeft.setBulletType(BPArc.ACCEL);
        arcAttackLeft.setAccelFactor(-0.1f);
    }

    private ArrayList<EnemyBulletAngularAccel> ebaas = new ArrayList<>();
    private ArrayList<EnemyBulletAngularAccel> normalEbaas = new ArrayList<>();

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
                        getPsRef(), 40, 0.2f));
            } else if (ebaa.getFramesAlive() > 30) {
                ebaa.setKillMe(true);
            }
        }
        for (int i = 0; i < normalEbaas.size(); i++) {
            EnemyBulletAngularAccel ebaa = normalEbaas.get(i);
            if (ebaa.isKillMe()) {
                normalEbaas.remove(i);
                i--;
            } else if (ebaa.getFramesAlive() > 150) {
                ebaa.setKillMe(true);
            }
        }
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 80)) {
            for (int i = -10; i <= 10; i++) {
                float angle = angleToPlayer() + i * 0.1f;
                EnemyBulletAngularAccel ebaa = new EnemyBulletAngularAccel(getCoords().deepClone(), 6, 12,
                        angle, -0.05f);
                toReturn.add(ebaa);
                if (Math.abs(i) % 2 == 0) {
                    ebaas.add(ebaa);
                } else {
                    normalEbaas.add(ebaa);
                }
            }
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
