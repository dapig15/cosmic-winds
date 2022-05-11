package bullethell.entity.bullet;

import bullethell.Coords;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyBulletAngularRotational extends EnemyBulletAngular {

    private float accelFactor;
    private float rotation;

    public EnemyBulletAngularRotational(Coords coords, int spriteRadius, float velocity, float angle,
            float accelFactor, float rotation) {
        super(coords, spriteRadius, velocity, angle);
        this.accelFactor = accelFactor;
        this.rotation = rotation;
    }

    @Override
    public void process() {
        super.process();
        setVelocity(getVelocity() + accelFactor);
        setAngle(getAngle() + rotation);
    }

    @Override
    public void paintMe(Graphics g) {
        g.setColor(new Color(Math.max(0, Math.min(255, (int) getVelocity() * 10)), 255,
                Math.max(0, Math.min(255, (int) getVelocity() * 10))));
        g.fillOval(getCoords().getX() - getHitboxRadius(),
                getCoords().getY() - getHitboxRadius(), getHitboxRadius() * 2, getHitboxRadius() * 2);
    }

}
