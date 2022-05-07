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

public class Stage1ESMiniboss extends EnemyShipBoss {

    private Coords originalCoords;
    private BPWave waveAttack = new BPWave(getCoords(), 5, 6, 0, 15, 2, (float) (Math.PI / 2));
    private BPArc circleAttack = new BPArc(getCoords(), 16, 6, 4, 0);
    private BPArc arcAttack = new BPArc(getCoords(), 3, 6, 6, -0.5f, 0.5f);
    private BPArc backAttack = new BPArc(getCoords(), 5, 6, 8, (float) (-Math.PI), 0);

    public Stage1ESMiniboss(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 64, 64, "images/sprites/ships/stage_1_miniboss.png", 200, targetCoords, psRef, 3, 100);
        originalCoords = targetCoords;
        arcAttack.primeHoming(getPsRef(), 40, 0.05f);
        arcAttack.setBulletType(BPArc.HOMING);
    }

    @Override
    public int findXDisp() {
        if (getCurrentPhase() == 1 && !isRecharging()) {
            return (int) (5 * Math.sin((((getFramesAliveThisPhase() - 100) / 8f) * Math.PI) / 10 + (Math.PI / 2)));
        }
        if (getCurrentPhase() == 2 && BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 200, 0, 130)) {
            return 0;
        }
        return super.findXDisp();
    }

    @Override
    public int findYDisp() {
        if (getCurrentPhase() == 2 && BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 200, 30, 130)) {
            return (int) (((getFramesAliveThisPhase() - 100) % 200 - 30) / 2f);
        } else {
            return super.findYDisp();
        }
    }

    @Override
    public void update() {
        super.update();
        if (getCurrentPhase() == 0 || (getCurrentPhase() == 2
                && BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 200, 130, 200))) {
            setTargetCoords(new Coords(getPsRef().getCoords().getX(), originalCoords.getY()));
        } else {
            setTargetCoords(originalCoords);
        }
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (isRecharging()) {
            return toReturn;
        }
        switch (getCurrentPhase()) {
            case 0:
                if (BulletPatternCaller.canSpawn(getFramesAlive(), 125, 50)) {
                    toReturn.add(new EnemyBulletLaser(getCoords(), 6, 1500, (float) (Math.PI / 2), 30, -1));
                }
                if (BulletPatternCaller.canSpawn(getFramesAlive(), 125, 50, 30, 30)) {
                    waveAttack.create(toReturn);
                }
                BulletPatternCaller.processPattern(toReturn, circleAttack, getFramesAliveThisPhase(), 100, 80);
                break;
            case 1:
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 15)) {
                    for (int i = 0; i <= 6; i++) {
                        Coords tempCoords = new Coords(getCoords().getX() + (int) (15 * Math.cos(i * Math.PI / 6)),
                                getCoords().getY() + (int) (15 * Math.sin(i * Math.PI / 6)));
                        toReturn.add(
                                new EnemyBulletAngularAccel(tempCoords, 6, 3, (float) (Math.PI / 2), 0.3f));
                    }
                }
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 160, 60)) {
                    arcAttack.create(toReturn);
                    arcAttack.shiftAngle((float) Math.PI);
                    arcAttack.create(toReturn);
                    arcAttack.shiftAngle((float) -Math.PI);
                }
                break;
            case 2:
                BulletPatternCaller.processPattern(toReturn, backAttack, getFramesAliveThisPhase(), 100, 5);
                if (BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 200, 131, 131)) {
                    setCoords(new Coords(BulletPanel.getBulletPanelWidth() / 2, -25));
                    backAttack.setOrigin(getCoords());
                }
                break;
        }
        return toReturn;
    }

    @Override
    public void paintMe(Graphics g) {
        if (getCurrentPhase() == 2 && BulletPatternCaller.canSpawn(getFramesAliveThisPhase(), 100, 200, 0, 30)) {
            int shiftX = (int) (Math.random() * 6);
            int shiftY = (int) (Math.random() * 6);
            g.translate(shiftX, shiftY);
            super.paintMe(g);
            g.translate(-shiftX, -shiftY);
        } else {
            super.paintMe(g);
        }
    }

}
