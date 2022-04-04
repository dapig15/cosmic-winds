package bullethell.entity.bullet;

import bullethell.Coords;

import java.awt.Color;
import java.awt.Graphics;

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

    @Override
    public void paintMe(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(getCoords().getX() - getHitboxRadius(),
                getCoords().getY() - getHitboxRadius(), getHitboxRadius() * 2, getHitboxRadius() * 2);
    }

}
