package bullethell.entity.bullet;

import bullethell.Coords;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyBulletBomb extends EnemyBulletAngular {

    private int startRadius, finalRadius;
    private float currentRadius;
    private Coords targetCoords;
    private float chaseFactor;
    private int chaseFrames, deflateFrames;
    private float colorPercent = 0;

    public EnemyBulletBomb(Coords coords, int hitboxRadius, int finalRadius, Coords targetCoords,
            float chaseFactor, int chaseFrames, int deflateFrames) {
        super(coords, hitboxRadius, coords.distanceTo(targetCoords) * chaseFactor,
                coords.angleTo(targetCoords));
        this.startRadius = hitboxRadius;
        this.finalRadius = finalRadius;
        currentRadius = startRadius;
        this.targetCoords = targetCoords;
        this.chaseFactor = chaseFactor;
        this.chaseFrames = chaseFrames;
        this.deflateFrames = deflateFrames;
        setDieWhenCollide(false);
    }

    public boolean isDeflating() {
        return getFramesAlive() > chaseFrames;
    }

    @Override
    public void process() {
        super.process();
        if (getFramesAlive() >= chaseFrames) {
            colorPercent -= 255f / deflateFrames;
            currentRadius -= (finalRadius) * 1f / deflateFrames;
        } else {
            colorPercent += 255f / chaseFrames;
            currentRadius += (finalRadius - startRadius) * 1f / chaseFrames;
        }
        setHitboxRadius((int) currentRadius);
        setVelocity(getCoords().distanceTo(targetCoords) * chaseFactor);
        if (getFramesAlive() >= chaseFrames + deflateFrames) {
            setKillMe(true);
        }
    }

    @Override
    public void paintMe(Graphics g) {
        g.setColor(new Color(255, Math.max(0, Math.min(255, (int) colorPercent)),
                Math.max(0, Math.min(255, (int) colorPercent))));
        g.fillOval(getCoords().getX() - getHitboxRadius(),
                getCoords().getY() - getHitboxRadius(), getHitboxRadius() * 2, getHitboxRadius() * 2);
    }

}
