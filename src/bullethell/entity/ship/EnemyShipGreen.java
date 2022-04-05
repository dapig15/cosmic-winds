package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngularRotational;

public class EnemyShipGreen extends EnemyShipBasic {

    public EnemyShipGreen(Coords coords, PlayerShip psRef, int initialYVel) {
        super(coords, 26, 26, "ship_2.png", 30, psRef, initialYVel);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            if ((getFramesAlive() + i * 10 + 1) % 100 == 0) {
                float angle = angleToPlayer();
                float rotation = 0.02f * (((i * 10) % 20 == 0) ? 1 : -1);
                for (int j = 0; j < 8; j++) {
                    toReturn.add(
                            new EnemyBulletAngularRotational(new Coords(this.getCoords().getX(),
                                    this.getCoords().getY()),
                                    6, 3, angle + j / 4f * (float) Math.PI, 0.2f, rotation));
                }
            }
        }
        return toReturn;
    }

}
