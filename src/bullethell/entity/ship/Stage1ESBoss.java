package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.BulletPanel;
import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularAccel;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletAngularRotational;
import bullethell.entity.bullet.EnemyBulletBomb;
import bullethell.entity.bullet.EnemyBulletNoAccel;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage1ESBoss extends EnemyShipBoss {

    private Coords originalCoords;

    private BPArc arcPattern = new BPArc(getCoords(), 3, 6, 8, 0, 0);

    public Stage1ESBoss(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 116, 128, "images/sprites/ships/stage_1_boss.png", 200, targetCoords, psRef, 5,
                100);
        originalCoords = targetCoords.deepClone();
    }

    @Override
    public int findXDisp() {
        if (getCurrentPhase() == 2) {
            return (int) (5 * Math.sin(((getFramesAliveThisPhase() / 8f) * Math.PI) / 10 + (Math.PI / 2)));
        }
        return super.findXDisp();
    }

    @Override
    public void update() {
        super.update();
        /*
         * if (getCurrentPhase() == 0) {
         * setTargetCoords(new Coords(getPsRef().getCoords().getX(),
         * originalCoords.getY()));
         * } else {
         * setTargetCoords(originalCoords);
         * }
         */
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
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 50, 0, 20)) {
                    float angle = new Coords(getCoords().getX() - 42, getCoords().getY() + 45)
                            .angleTo(getPsRef().getCoords());
                    float tiltAngle = angle;
                    tiltAngle += (float) (Math.PI / 2) * ((getFramesAliveThisPhase() % 2 == 0) ? 1 : -1);
                    toReturn.add(
                            new EnemyBulletAngular(new Coords((int) (getCoords().getX() - 42 + 5 * Math.cos(tiltAngle)),
                                    (int) (getCoords().getY() + 45 + 5 * Math.sin(tiltAngle))), 6, 15, angle));
                }
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 10)) {
                    arcPattern.setOrigin(new Coords(getCoords().getX() + 42, getCoords().getY() + 45));
                    arcPattern.setStartAngle(angleToPlayer() - 0.3f);
                    arcPattern.setEndAngle(angleToPlayer() + 0.3f);
                    arcPattern.create(toReturn);
                }
                break;
            case 1: // moon's melancholy
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
            case 2: // cosmic corridor
                if (getFramesAliveThisPhase() % 2 == 0) {
                    for (int i = 0; i < 36; i++) {
                        toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 4, 3,
                                i / 18f * (float) Math.PI));
                    }
                }
                break;
            case 3: // starheart
                if (getFramesAliveThisPhase() % 2 == 0 && (getFramesAliveThisPhase() % 50) <= 30) {
                    float inc = getFramesAlive() / (75f);
                    for (int i = 0; i < 36; i++) {
                        toReturn.add(new EnemyBulletAngularAccel(getCoords().deepClone(), 6, 6,
                                inc + i / 18f * (float) Math.PI, -0.1f));
                    }
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
