package bullethell.entity.bullet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import bullethell.Coords;

public class EnemyBulletLaser extends EnemyBulletAngular {

    private int length, chargeFrames, fireFrames;
    private Polygon laserPolygon;

    public EnemyBulletLaser(Coords coords, int spriteRadius, int length, float angle, int chargeFrames,
            int fireFrames) {
        super(coords, spriteRadius, 0, angle);
        this.length = length;
        this.chargeFrames = chargeFrames;
        this.fireFrames = fireFrames;
        setIgnoreKillOffset(true);
        setDieWhenCollide(false);
        laserPolygon = generateLaserPolygon();
    }

    private Polygon generateLaserPolygon() {
        return new Polygon(new int[] {
                (int) (getCoords().getX() + (Math.cos(getAngle() + (Math.PI / 2)) * getHitboxRadius())),
                (int) (getCoords().getX() + (Math.cos(getAngle() - (Math.PI / 2)) * getHitboxRadius())),
                (int) (getCoords().getX() + (Math.cos(getAngle()) * length)
                        + (Math.cos(getAngle() - (Math.PI / 2)) * getHitboxRadius())),
                (int) (getCoords().getX() + (Math.cos(getAngle()) * length)
                        + (Math.cos(getAngle() + (Math.PI / 2)) * getHitboxRadius())),
        }, new int[] {
                (int) (getCoords().getY() + (Math.sin(getAngle() + (Math.PI / 2)) * getHitboxRadius())),
                (int) (getCoords().getY() + (Math.sin(getAngle() - (Math.PI / 2)) * getHitboxRadius())),
                (int) (getCoords().getY() + (Math.sin(getAngle()) * length)
                        + (Math.sin(getAngle() - (Math.PI / 2)) * getHitboxRadius())),
                (int) (getCoords().getY() + (Math.sin(getAngle()) * length)
                        + (Math.sin(getAngle() + (Math.PI / 2)) * getHitboxRadius())),
        }, 4);
    }

    @Override
    public void process() {
        super.process();
        if (getFramesAlive() > chargeFrames) {
            if (getVelocity() <= 0)
                setVelocity(length);
            else
                setVelocity(-length);
        }
        if (getFramesAlive() > (chargeFrames + fireFrames)) {
            setKillMe(true);
        }
    }

    @Override
    public void paintMe(Graphics g) {
        g.setColor(Color.RED);
        if (getFramesAlive() <= chargeFrames) {
            g.drawOval(getCoords().getX() - (chargeFrames - getFramesAlive()), getCoords().getY() - (chargeFrames
                    - getFramesAlive()), (chargeFrames - getFramesAlive()) * 2, (chargeFrames - getFramesAlive()) * 2);
            g.drawPolygon(laserPolygon);
        } else {
            g.fillPolygon(laserPolygon);
        }
    }

}
