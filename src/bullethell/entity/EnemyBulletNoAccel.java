package bullethell.entity;

import bullethell.Coords;

public class EnemyBulletNoAccel extends EnemyBullet {

    float xVel, yVel;

    public EnemyBulletNoAccel(Coords coords, int hitboxRadius, float xVel, float yVel) {
        super(coords, hitboxRadius);
        this.xVel = xVel;
        this.yVel = yVel;
    }

    @Override
    public float findXDisp() {
        return xVel;
    }

    @Override
    public float findYDisp() {
        return yVel;
    }

}
