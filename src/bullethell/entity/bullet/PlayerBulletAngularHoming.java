package bullethell.entity.bullet;

import bullethell.Coords;
import bullethell.entity.ship.EnemyShip;
import bullethell.entity.ship.PlayerShip;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerBulletAngularHoming extends PlayerBulletAngular {

    private EnemyShip esRef;
    private int homingFrames;
    private float homingFactor;

    public PlayerBulletAngularHoming(Coords coords, int spriteRadius, int velocity, float angle,
            EnemyShip esRef,
            int homingFrames, float homingFactor) {
        super(coords, spriteRadius, velocity, angle);
        this.esRef = esRef;
        this.homingFrames = homingFrames;
        this.homingFactor = homingFactor;
    }

    @Override
    public void process() {
        super.process();
        if (getFramesAlive() <= homingFrames) {
            float x1 = getX(), y1 = getY();
            float x2 = getX() + (float) Math.cos(getAngle()), y2 = getY() + (float) Math.sin(getAngle());
            float x3 = esRef.getCoords().getX(), y3 = esRef.getCoords().getY();
            float val = (y2 - y1) * (x3 - x2) - (y3 - y2) * (x2 - x1);
            if (val < 0)
                setAngle(getAngle() + homingFactor);
            else if (val > 0)
                setAngle(getAngle() - homingFactor);
        }
    }

    @Override
    public void paintMe(Graphics g) {
        g.setColor(Color.PINK);
        g.fillOval(getCoords().getX() - getSpriteRadius(),
                getCoords().getY() - getSpriteRadius(), getSpriteRadius() * 2, getSpriteRadius() * 2);
    }
}