package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.EnemyBulletNoAccel;

public class EnemyShipRed extends EnemyShipBasic {

        public EnemyShipRed(Coords coords, PlayerShip psRef, int initialYVel) {
                super(coords, 26, 26, "ship_0.png", 30, psRef, initialYVel);
        }

        @Override
        public ArrayList<EnemyBullet> spawnBullets() {
                ArrayList<EnemyBullet> toReturn = new ArrayList<>();
                if ((getFramesAlive() + 1) % 30 == 0) {
                        float angle = angleToPlayer();
                        toReturn.add(new EnemyBulletAngular(
                                        new Coords(this.getCoords().getX(), this.getCoords().getY()),
                                        6, 10, angle));
                }
                if ((getFramesAlive() + 1) % 80 == 0) {
                        toReturn.add(
                                        new EnemyBulletNoAccel(
                                                        new Coords(this.getCoords().getX(), this.getCoords().getY()), 6,
                                                        5, 5));
                        toReturn.add(
                                        new EnemyBulletNoAccel(
                                                        new Coords(this.getCoords().getX(), this.getCoords().getY()), 6,
                                                        -5, 5));
                        toReturn.add(
                                        new EnemyBulletNoAccel(
                                                        new Coords(this.getCoords().getX(), this.getCoords().getY()), 6,
                                                        5,
                                                        -5));
                        toReturn.add(
                                        new EnemyBulletNoAccel(
                                                        new Coords(this.getCoords().getX(), this.getCoords().getY()), 6,
                                                        -5,
                                                        -5));
                }
                return toReturn;
        }

}
