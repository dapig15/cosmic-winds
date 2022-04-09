package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletLaser;
import bullethell.entity.bullet.EnemyBulletNoAccel;

public class EnemyShipRed extends EnemyShipBasic {
    public EnemyShipRed(Coords coords, PlayerShip psRef, int initialYVel) {
        super(coords, 26, 26, "ship_0.png", 30, psRef, initialYVel);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if ((getFramesAlive() - 20) % 30 == 0) {
            toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 10, angleToPlayer()));
        }
        if (getFramesAlive() % 80 == 0) {
            int[] multiply = new int[] { 1, -1 };
            for (int i : multiply) {
                for (int j : multiply) {
                    toReturn.add(new EnemyBulletNoAccel(getCoords().deepClone(), 6, 5 * i, 5 * j));
                }
            }
        }
        return toReturn;
    }

}
