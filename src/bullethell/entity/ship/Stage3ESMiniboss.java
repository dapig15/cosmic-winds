package bullethell.entity.ship;

import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.ArrayList;

import bullethell.BulletPanel;
import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularAccel;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletAngularRotational;
import bullethell.entity.bullet.EnemyBulletBomb;
import bullethell.entity.bullet.EnemyBulletLaser;
import bullethell.entity.bullet.EnemyBulletNoAccel;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BPWave;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage3ESMiniboss extends EnemyShipBoss {

    static class LaserEndpoints {

        private Coords startCoords;
        private float angle;

        public LaserEndpoints(Coords startCoords, float angle) {
            this.startCoords = startCoords;
            this.angle = angle;
        }

        public void createBullets(ArrayList<EnemyBullet> toReturn, int bulletCount, float velocity, float velocityInc) {
            BPWave startWave = new BPWave(startCoords, bulletCount, 6, 0, velocity, velocityInc, angle);
            startWave.create(toReturn);
        }

    }

    private ArrayDeque<LaserEndpoints> lasers = new ArrayDeque<>();
    private Coords spawnBombCoords;
    private float spawnAngle;

    public Stage3ESMiniboss(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 64, 64, "images/sprites/ships/stage_3_miniboss.png", 400, targetCoords, psRef, 3, 100);
    }

    @Override
    public int findXDisp() {
        return super.findXDisp();
    }

    @Override
    public int findYDisp() {
        return super.findYDisp();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (isRecharging()) {
            lasers = new ArrayDeque<>();
            return toReturn;
        }
        switch (getCurrentPhase()) {
            case 0:
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 20)) {
                    if (lasers.size() == 40) {
                        for (int i = 0; i < 8; i++) {
                            lasers.poll().createBullets(toReturn, 10, 3, 1);
                        }
                    }
                    float angleToPlayer = angleToPlayer();
                    for (int i = 0; i < 8; i++) {
                        float angle = angleToPlayer + (float) (Math.PI * i / 4);
                        lasers.add(new LaserEndpoints(getPsRef().getCoords().deepClone(), angle));
                    }
                    toReturn.add(new EnemyBulletLaser(getPsRef().getCoords().deepClone(), 0, 0, 0, 100, -1));
                }
                break;
            case 1:
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 100)) {
                    if (lasers.size() == 48) {
                        for (int i = 0; i < 48; i++) {
                            lasers.poll().createBullets(toReturn, 10, 2.5f + ((i % 2 == 0) ? 0.5f : 0), 1);
                        }
                    }
                    float angleToPlayer = angleToPlayer();
                    for (int i = 0; i < 24; i++) {
                        float angle = angleToPlayer + (float) (Math.PI * i / 12);
                        lasers.add(new LaserEndpoints(getPsRef().getCoords().deepClone(), angle));
                        lasers.add(new LaserEndpoints(getPsRef().getCoords().deepClone(),
                                angle + (float) (Math.PI / 24)));
                    }
                    toReturn.add(new EnemyBulletLaser(getPsRef().getCoords().deepClone(), 0, 0, 0, 100, -1));
                }
                break;
            case 2:
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 50)) {
                    spawnBombCoords = getPsRef().getCoords().deepClone();
                    spawnAngle = angleToPlayer();
                }
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 60, 0, 10)) {
                    if (lasers.size() == 44) {
                        for (int i = 0; i < 4; i++) {
                            lasers.poll().createBullets(toReturn, 3, 4, 4);
                        }
                    }
                    float angle = spawnAngle + (float) (Math.PI * ((getFramesAliveThisPhase() - 100) % 60) / 20);
                    for (int i = 0; i < 4; i++) {
                        lasers.add(new LaserEndpoints(spawnBombCoords, angle + (float) (Math.PI * i / 2)));
                    }
                    toReturn.add(new EnemyBulletLaser(spawnBombCoords, 0, 0, 0, 60, -1));
                }
                break;
        }
        return toReturn;
    }

}
