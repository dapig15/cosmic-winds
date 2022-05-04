package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletAngularRotational;
import bullethell.entity.bullet.EnemyBulletBomb;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class EnemyShipGreen extends EnemyShipBasic {

    public EnemyShipGreen(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 26, 26, "images/sprites/ships/stage_5_ship_green.png", 30, targetCoords, psRef);
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
            } else if (ebb.isDeflating() && ebb.getFramesAlive() == 51) {
                // float angle = ebb.getCoords().angleTo(getPsRef().getCoords());
                for (int j = 0; j < 16; j++) {
                    toReturn.add(new EnemyBulletAngularRotational(ebb.getCoords().deepClone(), 6, 2,
                            (float) (j * Math.PI / 8), 0.1f, 0.02f));
                    toReturn.add(new EnemyBulletAngularRotational(ebb.getCoords().deepClone(), 6, 2,
                            (float) (j * Math.PI / 8), 0.1f, -0.02f));
                }
            }
        }
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 50)) {
            EnemyBulletBomb ebb = new EnemyBulletBomb(getCoords().deepClone(), 6, 20,
                    getPsRef().getCoords().deepClone(), 0.04f,
                    50, 10);
            toReturn.add(ebb);
            ebbs.add(ebb);
        }
        return toReturn;
    }

}
