package bullethell.entity;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.*;

public class EnemyShipBigGreen extends EnemyShipBasic {

    public EnemyShipBigGreen(Coords coords, PlayerShip psRef) {
        super(coords, 26 * 2, 26 * 2, "ship_big_2.png", 200, psRef, 10);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (getFramesAlive() > 50 && getFramesAlive() % 50 == 0) {
            float angle = angleToPlayer();
            for (int i = 5; i < 139; i++) {
                toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 3,
                        angle + i / 72f * (float) Math.PI));
            }
            float xVel = 3 * (float) Math.cos(angle);
            float yVel = 3 * (float) Math.sin(angle);
            int max = 16;
            final float smallAngle = (float) Math.atan2(1, 2);
            for (int i = 0; i < max; i++) {
                float tempThing = (max / 8 - Math.abs((i % (max / 4)) - max / 8)) * 1f / (max
                        / 8);
                float maxDist = 30 * (float) Math.sqrt(1 + tempThing * tempThing);
                toReturn.add(
                        new EnemyBulletShape(new Coords(this.getCoords().getX(),
                                this.getCoords().getY()), 6, 0.04f,
                                angle, xVel, yVel, maxDist, 30, false));
                if (i % 4 == 0 || i % 4 == 3)
                    angle += smallAngle;
                else
                    angle += (Math.PI / 4f - smallAngle);
            }
        }
        return toReturn;
    }

}
