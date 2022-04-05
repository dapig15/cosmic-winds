package bullethell.entity.ship;

import bullethell.Coords;
import bullethell.entity.bullet.Bullet;

import java.awt.Color;
import java.awt.Graphics;

public abstract class EnemyShipBasic extends EnemyShip {

    private PlayerShip psRef;
    private int initialYVel;
    private int maxHealth, roofHealth, lastHitFrame;

    public EnemyShipBasic(Coords coords, int hitboxWidth, int hitboxHeight, String imagePath, int health,
            PlayerShip psRef, int initialYVel) {
        super(coords, hitboxWidth, hitboxHeight, imagePath, health);
        this.psRef = psRef;
        this.initialYVel = initialYVel;
        this.maxHealth = getHealth();
        this.roofHealth = getHealth();
        this.lastHitFrame = Integer.MIN_VALUE;
    }

    @Override
    public int findXDisp() {
        return 0;
    }

    @Override
    public int findYDisp() {
        return Math.max(initialYVel - (getFramesAlive() / 3), 0);
    }

    public PlayerShip getPsRef() {
        return psRef;
    }

    public void setPsRef(PlayerShip psRef) {
        this.psRef = psRef;
    }

    public int getInitialYVel() {
        return initialYVel;
    }

    public void setInitialYVel(int initialYVel) {
        this.initialYVel = initialYVel;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getRoofHealth() {
        return roofHealth;
    }

    public void setRoofHealth(int roofHealth) {
        this.roofHealth = roofHealth;
    }

    public int getLastHitFrame() {
        return lastHitFrame;
    }

    public void setLastHitFrame(int lastHitFrame) {
        this.lastHitFrame = lastHitFrame;
    }

    public float angleToPlayer() {
        return (float) Math.atan2(psRef.getCoords().getY() - getCoords().getY(),
                psRef.getCoords().getX() - getCoords().getX());
    }

    @Override
    public void update() {
        super.update();
        if (lastHitFrame < getFramesAlive() - 20) {
            roofHealth = Math.max(roofHealth - 3, getHealth());
        }
    }

    @Override
    public void getHit(Bullet pb) {
        setHealth(getHealth() - pb.getDamage());
        lastHitFrame = getFramesAlive();
        if (getHealth() <= 0) {
            setKillMe(true);
        }
    }

    public void paintMe(Graphics g) {
        super.paintMe(g);
        g.setColor(Color.RED);
        g.fillRect(getCoords().getX() - getHitboxWidth() / 2, getCoords().getY() - getHitboxHeight() * 2 / 3,
                getHitboxWidth() * roofHealth / maxHealth, getHitboxHeight() / 12);
        g.setColor(Color.GREEN);
        g.fillRect(getCoords().getX() - getHitboxWidth() / 2, getCoords().getY() - getHitboxHeight() * 2 / 3,
                getHitboxWidth() * getHealth() / maxHealth, getHitboxHeight() / 12);
    }

}
