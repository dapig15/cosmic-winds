package bullethell.entity;

import java.util.ArrayList;

import bullethell.Coords;

public class EnemyShipYellow extends EnemyShipBasic {

    public EnemyShipYellow(Coords coords, PlayerShip psRef, int initialYVel) {
        super(coords, 26, 26, "ship_1.png", 30, psRef, initialYVel);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if ((getFramesAlive() + 1) % 60 == 0) {
            float angle = angleToPlayer();
            toReturn.add(
                    new EnemyBulletAngularHoming(new Coords(this.getCoords().getX(), this.getCoords().getY()),
                            6, 10, angle - 0.5f, getPsRef(), 40, 0.04f));
            toReturn.add(
                    new EnemyBulletAngularHoming(new Coords(this.getCoords().getX(), this.getCoords().getY()),
                            6, 10, angle + 0.5f, getPsRef(), 40, 0.04f));
        }
        if ((getFramesAlive() + 1) % 25 == 0) {
            toReturn.add(
                    new EnemyBulletNoAccel(new Coords(this.getCoords().getX() - 10, this.getCoords().getY()),
                            6, 0, 7));
            toReturn.add(
                    new EnemyBulletNoAccel(new Coords(this.getCoords().getX() + 10, this.getCoords().getY()),
                            6, 0, 7));
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
