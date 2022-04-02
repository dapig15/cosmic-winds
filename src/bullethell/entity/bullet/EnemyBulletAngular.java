package bullethell.entity.bullet;

import bullethell.Coords;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyBulletAngular extends EnemyBullet {

    private float velocity;
    private float angle;

    public EnemyBulletAngular(Coords coords, int spriteRadius, float velocity, float angle) {
        super(coords, spriteRadius);
        this.velocity = velocity;
        this.angle = angle;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public float findXDisp() {
        return (float) Math.cos(angle) * velocity;
    }

    @Override
    public float findYDisp() {
        return (float) Math.sin(angle) * velocity;
    }

    @Override
    public void paintMe(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(getCoords().getX() - getHitboxRadius(),
                getCoords().getY() - getHitboxRadius(), getHitboxRadius() * 2, getHitboxRadius() * 2);
    }

}
