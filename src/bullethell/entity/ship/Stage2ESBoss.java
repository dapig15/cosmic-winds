package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.BulletPanel;
import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularAccel;
import bullethell.entity.bullet.EnemyBulletBomb;
import bullethell.entity.bullet.EnemyBulletLaser;
import bullethell.entity.bullet.EnemyBulletNoAccel;
import bullethell.entity.bullet.EnemyBulletShape;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BPWave;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage2ESBoss extends EnemyShipBoss {

    private Coords originalCoords;

    private BPArc arcPattern = new BPArc(getCoords(), 3, 6, 8, 0, 0);
    private BPArc circlePattern = new BPArc(getCoords(), 36, 6, 3, 0);
    private BPWave waveAttack = new BPWave(getCoords(), 5, 6, 0, 15, 2, 0);
    private BPWave homingWaveAttack = new BPWave(getCoords(), 10, 6, 1, 6, 1, 0);
    private BPArc newArcPattern = new BPArc(getCoords(), 5, 6, 5, 0, 0);

    private float laserAngle;

    public Stage2ESBoss(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 100, 120, "images/sprites/ships/stage_2_boss.png", 400, targetCoords, psRef, 5,
                100);
        originalCoords = targetCoords.deepClone();
        homingWaveAttack.primeHoming(getPsRef(), 20, 0.2f);
        homingWaveAttack.setBulletType(BPWave.HOMING);
        newArcPattern.setAccelFactor(0.3f);
        newArcPattern.setBulletType(BPArc.ACCEL);
    }

    @Override
    public void update() {
        super.update();
        if (getCurrentPhase() == 4) {
            setTargetCoords(new Coords(BulletPanel.getBulletPanelWidth() / 2, BulletPanel.getBulletPanelHeight() / 2));
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
                for (int j = -5; j <= 5; j++) {
                    toReturn.add(
                            new EnemyBulletNoAccel(new Coords(ebb.getCoords().getX(), ebb.getCoords().getY()), 6,
                                    5, j));
                    toReturn.add(
                            new EnemyBulletNoAccel(new Coords(ebb.getCoords().getX(), ebb.getCoords().getY()), 6,
                                    -5, j));
                    if (j * j != 25) {
                        toReturn.add(
                                new EnemyBulletNoAccel(new Coords(ebb.getCoords().getX(), ebb.getCoords().getY()),
                                        6, j,
                                        5));
                        toReturn.add(
                                new EnemyBulletNoAccel(new Coords(ebb.getCoords().getX(), ebb.getCoords().getY()),
                                        6, j,
                                        -5));
                    }
                }
            }
        }
        if (isRecharging()) {
            return toReturn;
        }
        switch (getCurrentPhase()) {
            case 0: // solar eruption
                float tempAngle = (getFramesAliveThisPhase() - 100) / 5f + (float) (Math.PI * 3 / 2);
                for (int i = -2; i <= 2; i++) {
                    toReturn.add(new EnemyBulletAngularAccel(
                            new Coords(this.getCoords().getX(), this.getCoords().getY()),
                            6, -7,
                            i / 6.0f * (float) Math.PI + tempAngle, 0.2f));
                }
                return toReturn;
            case 1: // moon's melancholy
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 35)) {
                    float angle = angleToPlayer();
                    for (int i = 3; i < 142; i++) {
                        toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 3,
                                angle + i / 72f * (float) Math.PI));
                    }
                    float xVel = 3 * (float) Math.cos(angle);
                    float yVel = 3 * (float) Math.sin(angle);
                    int max = 16;
                    final float smallAngle = (float) Math.atan2(1, 2);
                    for (int i = 0; i < max; i++) {
                        float tempThing = (max / 8 - Math.abs((i % (max / 4)) - max / 8)) * 1f / (max
                                / 8);
                        float maxDist = 30 * (float) Math.sqrt(1 + tempThing * tempThing);
                        toReturn.add(
                                new EnemyBulletShape(new Coords(this.getCoords().getX(),
                                        this.getCoords().getY()), 6, 0.04f,
                                        angle, xVel, yVel, maxDist, 30, false));
                        if (i % 4 == 0 || i % 4 == 3)
                            angle += smallAngle;
                        else
                            angle += (Math.PI / 4f - smallAngle);
                    }
                }
                break;
            case 2:
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 7)) {
                    float newAngle = (getFramesAliveThisPhase() - 100) / 30f + (float) (Math.PI / 2);
                    EnemyBulletBomb ebb = new EnemyBulletBomb(getCoords().deepClone(), 6, 30,
                            new Coords(getCoords().getX() + (int) (200 * Math.cos(newAngle)),
                                    getCoords().getY() + (int) (200 * Math.sin(newAngle))),
                            0.02f, 50, 20);
                    toReturn.add(ebb);
                    ebbs.add(ebb);
                }
                break;
            case 3:
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 30)) {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            EnemyBulletBomb ebb = new EnemyBulletBomb(getCoords().deepClone(), 6, 30,
                                    new Coords(getCoords().getX() + (int) (200 * i),
                                            getCoords().getY() + (int) (200 * j)),
                                    0.02f, 50, 20);
                            toReturn.add(ebb);
                            ebbs.add(ebb);
                        }
                    }
                }
                break;
            case 4:
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 10)) {
                    float angleInc = (getFramesAliveThisPhase() - 100) / 100f;
                    for (int i = 0; i < 48; i++) {
                        toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6,
                                2 + (float) Math.abs(4 * Math.sin(Math.PI * i / 24)),
                                angleInc + (float) (Math.PI * i / 24)));
                    }
                }
                break;
        }
        return toReturn;
    }

}
