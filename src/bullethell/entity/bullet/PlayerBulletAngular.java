package bullethell.entity.bullet;

import java.awt.Color;
import java.awt.Graphics;

import bullethell.Coords;

public class PlayerBulletAngular extends PlayerBullet {

    private float velocity;
    private float angle;
    private Color color = Color.MAGENTA;

    public PlayerBulletAngular(Coords coords, int spriteRadius, float velocity, float angle) {
        super(coords, spriteRadius);
        this.velocity = velocity;
        this.angle = angle;
    }

    public PlayerBulletAngular(Coords coords, int spriteRadius, float velocity, float angle, Color color) {
        super(coords, spriteRadius);
        this.velocity = velocity;
        this.angle = angle;
        this.color = color;
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
        g.setColor(color);
        g.fillOval(getCoords().getX() - getSpriteRadius(),
                getCoords().getY() - getSpriteRadius(), getSpriteRadius() * 2, getSpriteRadius() * 2);
    }
}