package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletNoAccel;

public class EnemyShipYellow extends EnemyShipBasic {

    public EnemyShipYellow(Coords coords, PlayerShip psRef, int initialYVel) {
        super(coords, 26, 26, "ship_1.png", 30, psRef, initialYVel);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        int[] multiply = new int[] { 1, -1 };
        if ((getFramesAlive() - 20) % 60 == 0) {
            float angle = angleToPlayer();
            for (int i : multiply) {
                toReturn.add(
                        new EnemyBulletAngularHoming(getCoords().deepClone(), 6, 10, angle + 0.5f * i, getPsRef(), 40,
                                0.04f));
            }
        }
        if ((getFramesAlive() - 10) % 25 == 0) {
            for (int i : multiply) {
                toReturn.add(
                        new EnemyBulletNoAccel(new Coords(getCoords().getX() + 10 * i, getCoords().getY()), 6, 0, 7));
            }
        }
        /*
         * for (int i = 1; i <= 13; i++) {
         * if ((getFramesAlive() + i) % 60 == 0) {
         * if ((i - 1) % 3 == 0) {
         * toReturn.add(
         * new EnemyBulletAngularHoming(new Coords(this.getCoords().getX(),
         * this.getCoords().getY()),
         * 6, 10, angle - 0.5f, getPsRef(), 40, 0.015f));
         * toReturn.add(
         * new EnemyBulletAngularHoming(new Coords(this.getCoords().getX(),
         * this.getCoords().getY()),
         * 6, 10, angle + 0.5f, getPsRef(), 40, 0.015f));
         * }
         * toReturn.add(new EnemyBulletAngular(new Coords(this.getCoords().getX(),
         * this.getCoords().getY()),
         * 6, 10, angle - 0.5f - (i * 0.05f)));
         * toReturn.add(new EnemyBulletAngular(new Coords(this.getCoords().getX(),
         * this.getCoords().getY()),
         * 6, 10, angle + 0.5f + (i * 0.05f)));
         * }
         * }
         */
        return toReturn;
    }

}
