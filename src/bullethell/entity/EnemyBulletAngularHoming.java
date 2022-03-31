package bullethell.entity;

import bullethell.Coords;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyBulletAngularHoming extends EnemyBulletAngular {

    private PlayerShip psRef;
    private int homingFrames;
    private float homingFactor;

    public EnemyBulletAngularHoming(Coords coords, int spriteRadius, int velocity, float angle, PlayerShip psRef,
            int homingFrames, float homingFactor) {
        super(coords, spriteRadius, velocity, angle);
        this.psRef = psRef;
        this.homingFrames = homingFrames;
        this.homingFactor = homingFactor;
    }

    @Override
    public void process() {
        super.process();
        if (getFramesAlive() <= homingFrames) {
            float x1 = getX(), y1 = getY();
            float x2 = getX() + (float) Math.cos(getAngle()), y2 = getY() + (float) Math.sin(getAngle());
            float x3 = psRef.getCoords().getX(), y3 = psRef.getCoords().getY();
            float val = (y2 - y1) * (x3 - x2) - (y3 - y2) * (x2 - x1);
            if (val < 0)
                setAngle(getAngle() + homingFactor);
            else if (val > 0)
                setAngle(getAngle() - homingFactor);
        }
    }

    @Override
    public void paintMe(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.orange);
        g.fillOval(getCoords().getX() - getHitboxRadius(),
                getCoords().getY() - getHitboxRadius(), getHitboxRadius() * 2, getHitboxRadius() * 2);
        g.setColor(c);
    }
}
