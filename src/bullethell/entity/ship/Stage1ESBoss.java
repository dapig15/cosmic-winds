package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularAccel;
import bullethell.entity.bullet.EnemyBulletBomb;
import bullethell.entity.bullet.EnemyBulletLaser;
import bullethell.entity.bullet.EnemyBulletShape;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BPWave;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage1ESBoss extends EnemyShipBoss {

    private Coords originalCoords;

    private BPArc arcPattern = new BPArc(getCoords(), 3, 6, 8, 0, 0);
    private BPArc circlePattern = new BPArc(getCoords(), 36, 6, 3, 0);
    private BPWave waveAttack = new BPWave(getCoords(), 5, 6, 0, 15, 2, 0);
    private BPWave homingWaveAttack = new BPWave(getCoords(), 10, 6, 1, 6, 1, 0);
    private BPArc newArcPattern = new BPArc(getCoords(), 5, 6, 5, 0, 0);

    private float laserAngle;

    public Stage1ESBoss(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 116, 128, "images/sprites/ships/stage_1_boss.png", 300, targetCoords, psRef, 5,
                100);
        originalCoords = targetCoords.deepClone();
        homingWaveAttack.primeHoming(getPsRef(), 20, 0.2f);
        homingWaveAttack.setBulletType(BPWave.HOMING);
        newArcPattern.setAccelFactor(0.3f);
        newArcPattern.setBulletType(BPArc.ACCEL);
    }

    @Override
    public int findXDisp() {
        if (getCurrentPhase() == 3 && getFramesAliveThisPhase() >= 100) {
            return (int) (6 * Math.sin((((getFramesAliveThisPhase() - 100) / 8f) * Math.PI) / 10 + (Math.PI / 2)));
        }
        return super.findXDisp();
    }

    @Override
    public void update() {
        super.update();
        if (getCurrentPhase() == 1) {
            setTargetCoords(new Coords(getPsRef().getCoords().getX(), originalCoords.getY()));
        } else {
            setTargetCoords(originalCoords);
        }
    }

    private ArrayList<EnemyBulletBomb> ebbs = new ArrayList<>();

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        final Coords leftGun = new Coords(getCoords().getX() - 42, getCoords().getY() + 45);
        final Coords rightGun = new Coords(getCoords().getX() + 42, getCoords().getY() + 45);
        final float leftAngleToPlayer = leftGun.angleTo(getPsRef().getCoords());
        final float rightAngleToPlayer = rightGun.angleTo(getPsRef().getCoords());
        for (int i = 0; i < ebbs.size(); i++) {
            EnemyBulletBomb ebb = ebbs.get(i);
            if (ebb.isKillMe()) {
                ebbs.remove(i);
                i--;
            } else if (ebb.isDeflating() && ebb.getFramesAlive() == 51) {
                circlePattern.setOrigin(ebb.getCoords());
                circlePattern.create(toReturn);
            }
        }
        if (isRecharging()) {
            return toReturn;
        }
        switch (getCurrentPhase()) {
            case 0: // solar eruption
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 50, 0, 20)) {
                    float tiltAngle = leftAngleToPlayer;
                    tiltAngle += (float) (Math.PI / 2) * ((getFramesAliveThisPhase() % 2 == 0) ? 1 : -1);
                    toReturn.add(
                            new EnemyBulletAngular(new Coords((int) (leftGun.getX() + 5 * Math.cos(tiltAngle)),
                                    (int) (leftGun.getY() + 5 * Math.sin(tiltAngle))), 6, 15, leftAngleToPlayer));
                }
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 10)) {
                    arcPattern.setOrigin(rightGun);
                    arcPattern.setStartAngle(rightAngleToPlayer - 0.3f);
                    arcPattern.setEndAngle(rightAngleToPlayer + 0.3f);
                    arcPattern.create(toReturn);
                }
                break;
            case 1: // moon's melancholy
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 3)) {
                    toReturn.add(new EnemyBulletAngularAccel(leftGun, 6, 5,
                            (float) (Math.random() * 2 - 1 + Math.PI / 2), 0.1f));
                    toReturn.add(new EnemyBulletAngularAccel(rightGun, 6, 5,
                            (float) (Math.random() * 2 - 1 + Math.PI / 2), 0.1f));
                }
                BulletPatternCaller.processPattern(toReturn, circlePattern, getFramesAliveThisPhase(), 100, 25);
                break;
            case 2:
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 50)) {
                    for (int i = 0; i < 16; i++) {
                        toReturn.add(
                                new EnemyBulletShape(leftGun.deepClone(), 6, 0.02f, (float) (Math.PI * i / 8),
                                        (float) (4 * Math.cos(leftAngleToPlayer)),
                                        (float) (4 * Math.sin(leftAngleToPlayer)),
                                        60, 20, true));
                    }
                }
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 30)) {
                    laserAngle = rightAngleToPlayer;
                    for (float i = -0.4f; i <= 0.4f; i += 0.2f) {
                        toReturn.add(new EnemyBulletLaser(rightGun.deepClone(), 6, 1500, laserAngle + i, 15, -1));
                    }
                }
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 30, 15, 15)) {
                    waveAttack.setOrigin(rightGun.deepClone());
                    for (float i = -0.4f; i <= 0.4f; i += 0.2f) {
                        waveAttack.setAngle(laserAngle + i);
                        waveAttack.create(toReturn);
                    }
                }
                break;
            case 3: // do the homing thing
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 50)) {
                    homingWaveAttack.setOrigin(leftGun);
                    homingWaveAttack.setAngle(leftAngleToPlayer);
                    homingWaveAttack.create(toReturn);
                    homingWaveAttack.setOrigin(rightGun);
                    homingWaveAttack.setAngle(rightAngleToPlayer);
                    homingWaveAttack.create(toReturn);
                }
                break;
            case 4: // sorrowful supernova
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 20)) {
                    newArcPattern.setOrigin(leftGun);
                    newArcPattern.setStartAngle(leftAngleToPlayer - 0.6f);
                    newArcPattern.setEndAngle(leftAngleToPlayer + 0.6f);
                    newArcPattern.create(toReturn);
                    newArcPattern.setOrigin(rightGun);
                    newArcPattern.setStartAngle(rightAngleToPlayer - 0.6f);
                    newArcPattern.setEndAngle(rightAngleToPlayer + 0.6f);
                    newArcPattern.create(toReturn);
                }
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 150, 100)) {
                    EnemyBulletBomb ebb = new EnemyBulletBomb(getCoords().deepClone(), 6, 20, getPsRef().getCoords(),
                            0.02f, 50, 20);
                    toReturn.add(ebb);
                    ebbs.add(ebb);
                }
                break;
        }
        return toReturn;
    }

}
