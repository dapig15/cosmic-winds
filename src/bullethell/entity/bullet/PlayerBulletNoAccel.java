package bullethell.entity.bullet;

import bullethell.Coords;

public class PlayerBulletNoAccel extends PlayerBullet {

    private int xVel, yVel;

    public PlayerBulletNoAccel(Coords coords, int spriteRadius, int xVel, int yVel) {
        super(coords, spriteRadius);
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
