package bullethell.entity.bullet;

import bullethell.*;
import bullethell.entity.ship.PlayerShip;

import java.awt.Color;
import java.awt.Graphics;

public abstract class EnemyBullet extends Bullet {
    private int hitboxRadius;

    private static PlayerShip playerReference;

    public EnemyBullet(Coords coords, int hitboxRadius) {
        super(coords);
        this.hitboxRadius = hitboxRadius;
    }

    public int getHitboxRadius() {
        return hitboxRadius;
    }

    public void setHitboxRadius(int hitboxRadius) {
        this.hitboxRadius = hitboxRadius;
    }

    public static void setPlayerReference(PlayerShip p) {
        playerReference = p;
    }

    private float sqr(float x) {
        return x * x;
    }

    private float dist2(float x1, float y1, float x2, float y2) {
        return sqr(x1 - x2) + sqr(y1 - y2);
    }

    // code credited from:
    // https://stackoverflow.com/questions/849211/shortest-distance-between-a-point-and-a-line-segment
    private float distToSegmentSquared(float xp, float yp, float x1, float y1, float x2, float y2) {
        float l2 = dist2(x1, y1, x2, y2);
        if (l2 == 0)
            return dist2(x1, y1, xp, yp);
        float t = ((xp - x1) * (x2 - x1) + (yp - y1) * (y2 - y1)) / l2;
        t = Math.max(0, Math.min(1, t));
        return dist2(xp, yp, x1 + t * (x2 - x1),
                y1 + t * (y2 - y1));
    }

    private boolean hasCollidedWithPlayer(float xInc, float yInc) {
        Coords p = playerReference.getCoords();
        return distToSegmentSquared(p.getX(), p.getY(), getCoords().getX(), getCoords().getY(),
                getCoords().getX() + xInc,
                getCoords().getY() + yInc) < sqr(playerReference.getHitboxWidth() / 2 + hitboxRadius);
    }

    @Override
    public void process() {
        if (hasCollidedWithPlayer(-findXDisp(), -findYDisp())) {
            playerReference.getHit(this);
            if (isDieWhenCollide())
                setKillMe(true);
        }
    }

    public void paintMe(Graphics g) {
        g.fillOval(getCoords().getX() - hitboxRadius,
                getCoords().getY() - hitboxRadius, hitboxRadius * 2, hitboxRadius * 2);
    }
}
