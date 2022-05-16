package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BPWave;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class Stage2ESRed extends EnemyShipBasic {

    private BPWave waveAttack = new BPWave(getCoords(), 4, 6, 1, 5, 1, 0);

    public Stage2ESRed(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 32, 32, "images/sprites/ships/stage_2_ship_red.png", 60, targetCoords, psRef);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 45)) {
            float angle = angleToPlayer();
            for (int i = -1; i <= 1; i++) {
                waveAttack.setAngle(angle + (i * 0.5f));
                waveAttack.create(toReturn);
            }
        }
        return toReturn;
    }

}
