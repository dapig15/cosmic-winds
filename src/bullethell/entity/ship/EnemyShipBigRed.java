package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngularAccel;
import bullethell.entity.bullet.EnemyBulletNoAccel;

public class EnemyShipBigRed extends EnemyShipBasic {

    public EnemyShipBigRed(Coords coords, PlayerShip psRef) {
        super(coords, 26 * 2, 26 * 2, "images/sprites/ships/ship_big_0.png", 250, psRef, 10);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if ((getFramesAlive() + 1) % 25 == 0) {
            float angle = angleToPlayer();
            for (int i = -2; i <= 2; i++) {
                toReturn.add(new EnemyBulletAngularAccel(new Coords(this.getCoords().getX(), this.getCoords().getY()),
                        6, -7,
                        i / 12.0f * (float) Math.PI + angle, 0.2f));
            }
        }
        if ((getFramesAlive() + 1) % 90 == 0) {
            for (int i = -5; i <= 5; i++) {
                toReturn.add(
                        new EnemyBulletNoAccel(new Coords(this.getCoords().getX(), this.getCoords().getY()), 6, 5, i));
                toReturn.add(
                        new EnemyBulletNoAccel(new Coords(this.getCoords().getX(), this.getCoords().getY()), 6, -5, i));
                if (i * i != 25) {
                    toReturn.add(
                            new EnemyBulletNoAccel(new Coords(this.getCoords().getX(), this.getCoords().getY()), 6, i,
                                    5));
                    toReturn.add(
                            new EnemyBulletNoAccel(new Coords(this.getCoords().getX(), this.getCoords().getY()), 6, i,
                                    -5));
                }
            }
        }
        return toReturn;
    }

}
