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
import bullethell.entity.bullet.EnemyBulletLaser;
import bullethell.entity.bullet.EnemyBulletNoAccel;

public class EnemyShipBossGreen extends EnemyShipBoss {

    private Coords centerCoords;
    private Coords targetCoords;

    public EnemyShipBossGreen(Coords coords, PlayerShip psRef, Coords centerCoords) {
        super(coords, 100, 120, "images/sprites/ships/stage_5_boss.png", 20, psRef, 10, 5, 20);
        this.centerCoords = centerCoords;
        this.targetCoords = centerCoords;
    }

    @Override
    public int findXDisp() {
        if (getCurrentPhase() == 2) {
            return (int) (5 * Math.sin(((getFramesAliveThisPhase() / 8f) * Math.PI) / 10 + (Math.PI / 2)));
        }
        if (getCoords().getX() == targetCoords.getX())
            return 0;
        else
            return (int) Math.ceil(-(getCoords().getX() - targetCoords.getX()) * 0.05);
    }

    @Override
    public int findYDisp() {
        if (getCoords().getY() == targetCoords.getY())
            return 0;
        else
            return (int) Math.ceil(-(getCoords().getY() - targetCoords.getY()) * 0.05);
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
            } else if (ebb.isDeflating() && ebb.getFramesAlive() == 151) {
                // float angle = ebb.getCoords().angleTo(getPsRef().getCoords());
                for (float j = 0; j <= (float) (2 * Math.PI); j += (float) (Math.PI / 12)) {
                    toReturn.add(new EnemyBulletAngular(ebb.getCoords().deepClone(), 6, 5, j));
                }
            }
        }
        if (isRecharging()) {
            return toReturn;
        }
        switch (getCurrentPhase()) {
            case 0: // solar eruption
                for (int i = 0; i < 24; i++) {
                    if (getFramesAliveThisPhase() % 30 < 25) {
                        float tilt = (float) (getFramesAliveThisPhase() % 30 * Math.PI / 360);
                        if (getFramesAliveThisPhase() % 60 > 30)
                            tilt *= -1;
                        toReturn.add(new EnemyBulletAngularRotational(getCoords().deepClone(), 6, 2,
                                tilt + (float) (i * Math.PI / 12), 0.2f, 0.04f));
                        toReturn.add(new EnemyBulletAngularRotational(getCoords().deepClone(), 6, 2,
                                tilt + (float) (i * Math.PI / 12), 0.2f, -0.04f));
                    }
                }
                break;
            case 1: // moon's melancholy
                if (getFramesAliveThisPhase() % 2 == 0) {
                    EnemyBulletBomb ebb = new EnemyBulletBomb(getCoords().deepClone(), 6, 30,
                            new Coords(
                                    getCoords().getX()
                                            + (int) (100 * (float) Math.cos(getFramesAliveThisPhase() / 20f)),
                                    getCoords().getY()
                                            + (int) (100 * (float) Math.sin(getFramesAliveThisPhase() / 20f))),
                            0.04f, 150, 20);
                    toReturn.add(ebb);
                    ebbs.add(ebb);
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
                        EnemyBulletBomb ebbtemp = new EnemyBulletBomb(getCoords().deepClone(), 6, 30,
                                new Coords(getCoords().getX() + (int) (distance * (float) Math.cos(newAngle)),
                                        getCoords().getY() + (int) (distance * (float) Math.sin(newAngle))),
                                0.02f, 50, 20);
                        toReturn.add(ebbtemp);
                        ebbs.add(ebbtemp);
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
