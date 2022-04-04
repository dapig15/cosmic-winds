package bullethell.entity.bullet;

import bullethell.Coords;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyBulletAngularAccel extends EnemyBulletAngular {

    private float accelFactor = 0;

    public EnemyBulletAngularAccel(Coords coords, int spriteRadius, int velocity, float angle, float accelFactor) {
        super(coords, spriteRadius, velocity, angle);
        this.accelFactor = accelFactor;
    }

    @Override
    public void process() {
        super.process();
        this.setVelocity(getVelocity() + accelFactor);
    }

    @Override
    public void paintMe(Graphics g) {
        g.setColor(new Color(255, Math.max(0, Math.min(255, (int) getVelocity() * 10)),
                Math.max(0, Math.min(255, (int) getVelocity() * 10))));
        g.fillOval(getCoords().getX() - getHitboxRadius(),
                getCoords().getY() - getHitboxRadius(), getHitboxRadius() * 2, getHitboxRadius() * 2);
    }

}
