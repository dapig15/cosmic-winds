package bullethell.entity.bullet;

import bullethell.Coords;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerBulletBomb extends PlayerBulletAngular {

    private int startRadius, finalRadius;
    private float currentRadius;
    private Coords targetCoords;
    private float chaseFactor;
    private int chaseFrames, deflateFrames;
    private float colorPercent = 0;

    public PlayerBulletBomb(Coords coords, int spriteRadius, int finalRadius, Coords targetCoords,
            float chaseFactor, int chaseFrames, int deflateFrames) {
        super(coords, spriteRadius, coords.distanceTo(targetCoords) * chaseFactor,
                coords.angleTo(targetCoords));
        this.startRadius = spriteRadius;
        this.finalRadius = finalRadius;
        currentRadius = startRadius;
        this.targetCoords = targetCoords;
        this.chaseFactor = chaseFactor;
        this.chaseFrames = chaseFrames;
        this.deflateFrames = deflateFrames;
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
        setVelocity(getCoords().distanceTo(targetCoords) * chaseFactor);
        if (getFramesAlive() >= chaseFrames + deflateFrames) {
            setKillMe(true);
        }
    }

    @Override
    public void paintMe(Graphics g) {
        g.setColor(new Color(255, Math.max(0, Math.min(255, (int) colorPercent)),
                Math.max(0, Math.min(255, (int) colorPercent))));
        g.fillOval(
                (int) (getCoords().getX() - currentRadius),
                getCoords().getY() - (int) currentRadius, (int) (currentRadius * 2), (int) (currentRadius * 2));
    }

}