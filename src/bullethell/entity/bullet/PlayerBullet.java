package bullethell.entity.bullet;

import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;

import bullethell.*;
import bullethell.entity.ship.EnemyShip;

public abstract class PlayerBullet extends Bullet {
    private int spriteRadius;

    private static ArrayList<EnemyShip> enemyShipReference;

    public PlayerBullet(Coords coords, int spriteRadius) {
        super(coords);
        this.spriteRadius = spriteRadius;
    }

    public int getSpriteRadius() {
        return spriteRadius;
    }

    public static void setEnemyShipReference(ArrayList<EnemyShip> es) {
        enemyShipReference = es;
    }

    public EnemyShip hasCollidedWithEnemyShip() {
        for (EnemyShip es : enemyShipReference) {
            if (es.getHitbox().containsCoord(getCoords()))
                return es;
        }
        return null;
    }

    @Override
    public void process() {
        EnemyShip collidedShip = hasCollidedWithEnemyShip();
        if (collidedShip != null) {
            collidedShip.getHit(this);
            setKillMe(true);
        }
    }

    public void paintMe(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.MAGENTA);
        g.fillOval(getCoords().getX() - spriteRadius,
                getCoords().getY() - spriteRadius, spriteRadius * 2, spriteRadius * 2);
        g.setColor(c);
    }
}
