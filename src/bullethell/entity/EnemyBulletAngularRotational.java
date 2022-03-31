package bullethell.entity;

import bullethell.Coords;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyBulletAngularRotational extends EnemyBulletAngular {

    private int accelFactor;
    private float rotation;

    public EnemyBulletAngularRotational(Coords coords, int spriteRadius, int velocity, float angle, int accelFactor,
            float rotation) {
        super(coords, spriteRadius, velocity, angle);
        this.accelFactor = accelFactor;
        this.rotation = rotation;
    }

    @Override
    public void process() {
        super.process();
        setVelocity(getVelocity() + ((getFramesAlive() % accelFactor == 0) ? 1 : 0));
        setAngle(getAngle() + rotation);
    }

    @Override
    public void paintMe(Graphics g) {
        Color c = g.getColor();
        g.setColor(new Color(Math.max(0, Math.min(255, getVelocity() * 10)), 255,
                Math.max(0, Math.min(255, getVelocity() * 10))));
        g.fillOval(getCoords().getX() - getHitboxRadius(),
                getCoords().getY() - getHitboxRadius(), getHitboxRadius() * 2, getHitboxRadius() * 2);
        g.setColor(c);
    }

}
