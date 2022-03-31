package bullethell.entity;

import java.util.ArrayList;

import bullethell.*;
import utility.Hitbox;

public abstract class EnemyShip extends Ship {

    int health;
    private Hitbox hitbox;
    private int framesAlive = 0;

    public EnemyShip(Coords coords, int hitboxWidth, int hitboxHeight, String imagePath, int health) {
        super(coords, hitboxWidth, hitboxHeight, imagePath);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public int getFramesAlive() {
        return framesAlive;
    }

    @Override
    public void update() {
        int xInc = findXDisp();
        int yInc = findYDisp();
        shiftCoords(xInc, yInc);
        this.hitbox = new Hitbox(getCoords().getX() - getHitboxWidth() / 2, getCoords().getY() - getHitboxHeight() / 2,
                getCoords().getX() + getHitboxWidth() / 2, getCoords().getY() + getHitboxHeight() / 2);
        spawnBullets();
        framesAlive++;
    }

    public abstract int findXDisp();

    public abstract int findYDisp();

    public abstract ArrayList<EnemyBullet> spawnBullets();

    public abstract void getHit(PlayerBullet pb);

}
