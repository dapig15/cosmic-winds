package bullethell.entity;

import bullethell.*;

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

    private int sqr(int x) {
        return x * x;
    }

    private int dist2(int x1, int y1, int x2, int y2) {
        return sqr(x1 - x2) + sqr(y1 - y2);
    }

    // code credited from:
    // https://stackoverflow.com/questions/849211/shortest-distance-between-a-point-and-a-line-segment
    private int distToSegmentSquared(int xp, int yp, int x1, int y1, int x2, int y2) {
        var l2 = dist2(x1, y1, x2, y2);
        if (l2 == 0)
            return dist2(x1, y1, xp, yp);
        var t = ((xp - x1) * (x2 - x1) + (yp - y1) * (y2 - y1)) / l2;
        t = Math.max(0, Math.min(1, t));
        return dist2(xp, yp, x1 + t * (x2 - x1),
                y1 + t * (y2 - y1));
    }

    private boolean hasCollidedWithPlayer(int xInc, int yInc) {
        Coords p = playerReference.getCoords();
        return distToSegmentSquared(p.getX(), p.getY(), getCoords().getX(), getCoords().getY(),
                getCoords().getX() + xInc,
                getCoords().getY() + yInc) < sqr(playerReference.getHitboxWidth() / 2 + hitboxRadius);
    }

    @Override
    public void process() {
        if (hasCollidedWithPlayer((int) -findXDisp(), (int) -findYDisp())) {
            playerReference.getHit(this);
            setKillMe(true);
        }
    }

    public void paintMe(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.red);
        g.fillOval(getCoords().getX() - hitboxRadius,
                getCoords().getY() - hitboxRadius, hitboxRadius * 2, hitboxRadius * 2);
        g.setColor(c);
    }
}
