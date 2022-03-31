package bullethell.entity;

import bullethell.Coords;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyBulletAngularAccel extends EnemyBulletAngular {

    private int accelFactor = 1;

    public EnemyBulletAngularAccel(Coords coords, int spriteRadius, int velocity, float angle, int accelFactor) {
        super(coords, spriteRadius, velocity, angle);
        this.accelFactor = accelFactor;
    }

    @Override
    public void process() {
        super.process();
        if (this.getFramesAlive() % accelFactor == 0) {
            this.setVelocity(getVelocity() + ((accelFactor > 0) ? 1 : -1));
        }
    }

    @Override
    public void paintMe(Graphics g) {
        Color c = g.getColor();
        g.setColor(new Color(255, Math.max(0, Math.min(255, getVelocity() * 10)),
                Math.max(0, Math.min(255, getVelocity() * 10))));
        g.fillOval(getCoords().getX() - getHitboxRadius(),
                getCoords().getY() - getHitboxRadius(), getHitboxRadius() * 2, getHitboxRadius() * 2);
        g.setColor(c);
    }

}
