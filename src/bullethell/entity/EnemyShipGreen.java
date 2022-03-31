package bullethell.entity;

import java.util.ArrayList;

import bullethell.Coords;

public class EnemyShipGreen extends EnemyShipBasic {

    public EnemyShipGreen(Coords coords, PlayerShip psRef, int initialYVel) {
        super(coords, 26, 26, "ship_1.png", 30, psRef, initialYVel);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        // for (int i = 1; i < 6; i++) {
        if ((getFramesAlive() + 1) % 1 == 0) {
            /*
             * float angle = angleToPlayer();
             * float rotation = 0.02f * (((i * 10) % 20 == 0) ? 1 : -1);
             * for (int j = 0; j < 8; j++) {
             * toReturn.add(
             * new EnemyBulletAngularRotational(new Coords(this.getCoords().getX(),
             * this.getCoords().getY()),
             * 6, 3, angle + j / 4f * (float) Math.PI, 6, rotation));
             * }
             */
            float angle = angleToPlayer();
            float xVel = 5 * (float) Math.cos(angle);
            float yVel = 5 * (float) Math.sin(angle);
            int max = 16;
            final float smallAngle = (float) Math.atan2(1, 2);
            for (int i = 0; i < max; i++) {
                float tempThing = (max / 8 - Math.abs((i % (max / 4)) - max / 8)) * 1f / (max
                        / 8);
                float maxDist = 150 * (float) Math.sqrt(1 + tempThing * tempThing);
                toReturn.add(
                        new EnemyBulletShape(new Coords(this.getCoords().getX(),
                                this.getCoords().getY()), 6, 0.04f,
                                angle, xVel, yVel, maxDist, 150, false));
                if (i % 4 == 0 || i % 4 == 3)
                    angle += smallAngle;
                else
                    angle += (Math.PI / 4f - smallAngle);
            }
        }
        // }
        return toReturn;
    }

}
