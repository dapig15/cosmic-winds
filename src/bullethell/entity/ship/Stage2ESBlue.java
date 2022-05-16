package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.BulletPanel;
import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletLaser;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BPWave;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage2ESBlue extends EnemyShipBasic {

    private int delay;
    private BPWave leftWaveAttack = new BPWave(getCoords(), 10, 6, 0, 3, 1, 0);
    private BPWave downWaveAttack = new BPWave(getCoords(), 10, 6, 0, 3, 1, (float) (Math.PI / 2));
    private BPWave rightWaveAttack = new BPWave(getCoords(), 10, 6, 0, 3, 1, (float) (Math.PI));
    private BPWave upWaveAttack = new BPWave(getCoords(), 10, 6, 0, 3, 1, (float) (Math.PI * 3 / 2));

    public Stage2ESBlue(Coords coords, PlayerShip psRef, Coords targetCoords, int delay) {
        super(coords, 32, 32, "images/sprites/ships/stage_2_ship_blue.png", 60, targetCoords, psRef);
        this.delay = delay;
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50 + delay, 75)) {
            final Coords pc = getPsRef().getCoords();
            final Coords leftOrigin = new Coords(-30, pc.getY());
            final Coords downOrigin = new Coords(pc.getX(), -30);
            final Coords rightOrigin = new Coords(BulletPanel.getBulletPanelWidth() + 30, pc.getY());
            final Coords upOrigin = new Coords(pc.getX(), BulletPanel.getBulletPanelHeight() + 30);
            leftWaveAttack.setOrigin(leftOrigin);
            downWaveAttack.setOrigin(downOrigin);
            rightWaveAttack.setOrigin(rightOrigin);
            upWaveAttack.setOrigin(upOrigin);
            toReturn.add(new EnemyBulletLaser(leftOrigin, 6, 1000, 0, 40, -1));
            toReturn.add(new EnemyBulletLaser(downOrigin, 6, 1000, (float) (Math.PI / 2), 40, -1));
            toReturn.add(new EnemyBulletLaser(rightOrigin, 6, 1000, (float) (Math.PI), 40, -1));
            toReturn.add(new EnemyBulletLaser(upOrigin, 6, 1000, (float) (Math.PI * 3 / 2), 40, -1));
        }
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 90 + delay, 75)) {
            leftWaveAttack.create(toReturn);
            downWaveAttack.create(toReturn);
            rightWaveAttack.create(toReturn);
            upWaveAttack.create(toReturn);
        }
        return toReturn;
    }

}
