package bullethell.entity;

import bullethell.Coords;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyBulletShape extends EnemyBullet {

    private float xVel = 0, yVel = 0;
    private float centerX, centerY;
    private float shapeRotation, angleOffset, shapeXVel, shapeYVel, finalRadius;
    private int enlargeFrames;
    private boolean stayWhileEnlarging = true;

    public EnemyBulletShape(Coords coords, int spriteRadius, float shapeRotation, float angleOffset, float shapeXVel,
            float shapeYVel, float finalRadius, int enlargeFrames, boolean stayWhileEnlarging) {
        super(coords, spriteRadius);
        centerX = coords.getX();
        centerY = coords.getY();
        this.shapeRotation = shapeRotation;
        this.angleOffset = angleOffset;
        this.shapeXVel = shapeXVel;
        this.shapeYVel = shapeYVel;
        this.finalRadius = finalRadius;
        this.enlargeFrames = enlargeFrames;
        this.stayWhileEnlarging = stayWhileEnlarging;
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
    public void process() {
        super.process();
        if (!stayWhileEnlarging || getFramesAlive() >= enlargeFrames) {
            centerX += shapeXVel;
            centerY += shapeYVel;
        }
        float tempVal = Math.min(getFramesAlive(), enlargeFrames) * finalRadius / enlargeFrames;
        float newX = centerX + tempVal * (float) Math.cos(angleOffset + shapeRotation * getFramesAlive());
        float newY = centerY + tempVal * (float) Math.sin(angleOffset + shapeRotation * getFramesAlive());
        xVel = newX - getX();
        yVel = newY - getY();
    }

    @Override
    public void paintMe(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GREEN);
        g.fillOval(getCoords().getX() - getHitboxRadius(),
                getCoords().getY() - getHitboxRadius(), getHitboxRadius() * 2, getHitboxRadius() * 2);
        g.setColor(c);
    }

}
