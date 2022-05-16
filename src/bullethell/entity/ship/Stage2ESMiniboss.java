package bullethell.entity.ship;

import java.awt.Graphics;
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

public class Stage2ESMiniboss extends EnemyShipBoss {

    private BPArc circleAttack = new BPArc(getCoords(), 12, 6, 4, 0);

    public Stage2ESMiniboss(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 62, 62, "images/sprites/ships/stage_2_miniboss.png", 300, targetCoords, psRef, 3, 100);
    }

    @Override
    public int findXDisp() {
        if (getCurrentPhase() == 0 && !isRecharging()) {
            return (int) (5 * Math.sin((((getFramesAliveThisPhase() - 100) / 8f) * Math.PI) / 10 + (Math.PI / 2)));
        }
        return super.findXDisp();
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (isRecharging()) {
            return toReturn;
        }
        switch (getCurrentPhase()) {
            case 0: // cosmic corridor
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 2)) {
                    for (int i = 0; i < 36; i++) {
                        toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 4, 3,
                                i / 18f * (float) Math.PI));
                    }
                }
                break;
            case 1: // starheart
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 2)
                        && BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 50, 0, 30)) {
                    float inc = getFramesAlive() / (75f);
                    for (int i = 0; i < 36; i++) {
                        toReturn.add(new EnemyBulletAngularAccel(getCoords().deepClone(), 6, 6,
                                inc + i / 18f * (float) Math.PI, -0.1f));
                    }
                }
                break;
            case 2:
                float toAdd = ((getFramesAliveThisPhase() - 100) / 10f) % ((float) (Math.PI * 2));
                circleAttack.shiftAngle(toAdd);
                circleAttack.create(toReturn);
                break;
        }
        return toReturn;
    }

}
