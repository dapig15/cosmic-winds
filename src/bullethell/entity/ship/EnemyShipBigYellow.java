package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.*;

public class EnemyShipBigYellow extends EnemyShipBasic {

    public EnemyShipBigYellow(Coords coords, PlayerShip psRef) {
        super(coords, 26 * 2, 26 * 2, "ship_big_1.png", 500, psRef, 10);
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
        if (getFramesAlive() >= 50 && getFramesAlive() % 50 == 0) {
            float distance = 250;
            float angle = angleToPlayer();
            for (int i = 0; i < 8; i += 1) {
                float newAngle = angle + i / 4f * (float) Math.PI;
                EnemyBulletBomb ebb = new EnemyBulletBomb(getCoords().deepClone(), 6, 30,
                        new Coords(getCoords().getX() + (int) (distance * (float) Math.cos(newAngle)),
                                getCoords().getY() + (int) (distance * (float) Math.sin(newAngle))),
                        0.02f, 50, 20);
                toReturn.add(ebb);
                ebbs.add(ebb);
            }
        }
        return toReturn;
    }

}
