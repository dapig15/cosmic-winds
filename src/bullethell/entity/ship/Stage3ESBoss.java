package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.BulletPanel;
import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularAccel;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletAngularRotational;
import bullethell.entity.bullet.EnemyBulletBomb;
import bullethell.entity.bullet.EnemyBulletLaser;
import bullethell.entity.bullet.EnemyBulletNoAccel;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage3ESBoss extends EnemyShipBoss {

    private BPArc circleArc = new BPArc(getCoords(), 24, 6, 4, 0);
    private Coords spawnBombCoords, lastBombCoords;

    public Stage3ESBoss(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 128, 128, "images/sprites/ships/stage_3_boss.png", 600, targetCoords, psRef, 5,
                100);
    }

    @Override
    public int findXDisp() {
        if (getCurrentPhase() == 1) {
            return (int) (5 * Math.sin(((getFramesAliveThisPhase() / 8f) * Math.PI) / 10 + (Math.PI / 2)));
        }
        return super.findXDisp();
    }

    private ArrayList<EnemyBulletBomb> ebbs = new ArrayList<>();

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        for (int i = 0; i < ebbs.size(); i++) {
            EnemyBulletBomb ebb = ebbs.get(i);
            if (ebb.isKillMe()) {
                ebbs.remove(i);
                i--;
            } else if (ebb.isDeflating() && ebb.getFramesAlive() % 2 == 0) {
                float angle = ebb.getCoords().angleTo(getPsRef().getCoords());
                for (float j = -0.5f; j <= 0.5f; j += 0.5) {
                    toReturn.add(new EnemyBulletAngularAccel(ebb.getCoords().deepClone(), 6, 5, angle + j, 0.5f));
                }
            }
        }
        if (isRecharging()) {
            return toReturn;
        }
        switch (getCurrentPhase()) {
            case 0: // solar eruption
                for (int i = 0; i < 24; i++) {
                    if ((getFramesAliveThisPhase() - 100) % 30 < 25) {
                        float tilt = (float) ((getFramesAliveThisPhase() - 100) % 30 * Math.PI / 360);
                        if ((getFramesAliveThisPhase() - 100) % 60 > 30)
                            tilt *= -1;
                        toReturn.add(new EnemyBulletAngularRotational(getCoords().deepClone(), 6, 2,
                                tilt + (float) (i * Math.PI / 12), 0.2f, 0.04f));
                        toReturn.add(new EnemyBulletAngularRotational(getCoords().deepClone(), 6, 2,
                                tilt + (float) (i * Math.PI / 12), 0.2f, -0.04f));
                    }
                }
                break;
            case 1: // moon's melancholy
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 10)) {
                    circleArc.shiftAngle(angleToPlayer() - circleArc.getStartAngle());
                    circleArc.create(toReturn);
                }
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 25)) {
                    for (int j = 0; j < 16; j++) {
                        toReturn.add(new EnemyBulletAngularRotational(getCoords().deepClone(), 6, 2,
                                (float) (j * Math.PI / 8), 0.1f, 0.02f));
                        toReturn.add(new EnemyBulletAngularRotational(getCoords().deepClone(), 6, 2,
                                (float) (j * Math.PI / 8), 0.1f, -0.02f));
                    }
                }
                toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 8,
                        (float) (-Math.PI / 2) + (float) (Math.sin(getFramesAlive() * 0.05f) * Math.PI)));
                toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 8,
                        (float) (-Math.PI / 2) - (float) (Math.sin(getFramesAlive() * 0.05f) * Math.PI)));
                break;
            case 2: // moon's melancholy
                for (int i = 1; i < 8; i += 2) {
                    float newAngle = ((getFramesAliveThisPhase() % 100) / 100f + i / 4f) * (float) Math.PI;
                    toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 5, newAngle));
                }
                if ((getFramesAliveThisPhase() + 25) % 50 == 0) {
                    float distance = 250;
                    for (int i = 1; i < 8; i += 2) {
                        float newAngle = i / 4f * (float) Math.PI;
                        EnemyBulletBomb ebb = new EnemyBulletBomb(getCoords().deepClone(), 6, 30,
                                new Coords(
                                        getCoords().getX() + (int) (distance * (float) Math.cos(newAngle)),
                                        getCoords().getY() + (int) (distance * (float) Math.sin(newAngle))),
                                0.04f, 50, 20);
                        toReturn.add(ebb);
                        ebbs.add(ebb);
                    }
                }
                break;
            case 3:
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 100)) {
                    lastBombCoords = spawnBombCoords;
                    spawnBombCoords = getPsRef().getCoords().deepClone();
                }
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 100, 0, 60)) {
                    if (lastBombCoords != null) {
                        for (int i = 0; i < 24; i++) {
                            float tilt = (float) ((getFramesAliveThisPhase() - 100) % 30 * Math.PI / 360);
                            toReturn.add(new EnemyBulletAngularRotational(lastBombCoords.deepClone(), 6, 2,
                                    tilt + (float) (i * Math.PI / 12), 0.2f, 0.04f));
                            toReturn.add(new EnemyBulletAngularRotational(lastBombCoords.deepClone(), 6, 2,
                                    tilt + (float) (i * Math.PI / 12), 0.2f, -0.04f));
                        }
                    }
                    toReturn.add(new EnemyBulletLaser(spawnBombCoords, 0, 0, 0, 100, -1));
                }
                break;
            case 4: // sorrowful supernova
                if ((getFramesAliveThisPhase() + 25) % 50 == 0) {
                    float distance = 250;
                    for (int i = 0; i < 24; i += 1) {
                        float newAngle = i / 12f * (float) Math.PI;
                        EnemyBulletBomb ebb = new EnemyBulletBomb(getCoords().deepClone(), 6, 30,
                                new Coords(getCoords().getX() + (int) (distance * (float) Math.cos(newAngle)),
                                        getCoords().getY() + (int) (distance * (float) Math.sin(newAngle))),
                                0.02f, 50, 20);
                        toReturn.add(ebb);
                        ebbs.add(ebb);
                    }
                }
                if (getFramesAliveThisPhase() % 5 == 0) {
                    for (float i = -5 + (getFramesAlive() % 10) / 10f; i <= 5; i++) {
                        toReturn.add(
                                new EnemyBulletNoAccel(this.getCoords().deepClone(), 6, 5, i));
                        toReturn.add(
                                new EnemyBulletNoAccel(new Coords(this.getCoords().getX(), this.getCoords().getY()), 6,
                                        -5, -i));
                        if (i * i != 25) {
                            toReturn.add(
                                    new EnemyBulletNoAccel(new Coords(this.getCoords().getX(), this.getCoords().getY()),
                                            6, i,
                                            5));
                            toReturn.add(
                                    new EnemyBulletNoAccel(new Coords(this.getCoords().getX(), this.getCoords().getY()),
                                            6, i,
                                            -5));
                        }
                    }
                }
                break;
        }
        return toReturn;
    }

}
