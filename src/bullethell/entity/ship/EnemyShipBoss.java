package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;

import java.awt.Color;
import java.awt.Graphics;

public abstract class EnemyShipBoss extends EnemyShipBasic {

    private int initialHealth;
    private int currentPhase = 0, totalPhases; // one-indexed
    private int rechargeFrames, rechargeCounter = -1;
    private boolean isRecharging = true;
    private int framesAliveThisPhase = -1;

    public EnemyShipBoss(Coords coords, int hitboxWidth, int hitboxHeight, String imagePath, int health,
            PlayerShip psRef, int initialYVel, int totalPhases, int rechargeFrames) {
        super(coords, hitboxWidth, hitboxHeight, imagePath, health, psRef, initialYVel);
        initialHealth = health;
        this.totalPhases = totalPhases;
        this.rechargeFrames = rechargeFrames;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }

    public boolean isRecharging() {
        return isRecharging;
    }

    public int getFramesAliveThisPhase() {
        return framesAliveThisPhase;
    }

    @Override
    public void getHit(Bullet pb) {
        if (!isRecharging) {
            super.getHit(pb);
            if (getHealth() <= 0) {
                currentPhase++;
                if (currentPhase >= totalPhases) {
                    setKillMe(true);
                } else {
                    setHealth(1);
                    rechargeCounter = 0;
                    isRecharging = true;
                    framesAliveThisPhase = -1;
                }
            }
        } else {
            pb.setKillMe(true);
        }
    }

    @Override
    public void update() {
        super.update();
        framesAliveThisPhase++;
        if (isRecharging) {
            rechargeCounter++;
            if (rechargeCounter == rechargeFrames) {
                rechargeCounter = 0;
                setHealth(initialHealth);
                setMaxHealth(initialHealth);
                setRoofHealth(initialHealth);
                setLastHitFrame(Integer.MIN_VALUE);
                isRecharging = false;
            }
        }
    }

    public void paintMe(Graphics g) {
        super.paintMe(g);
        if (isRecharging) {
            g.setColor(Color.yellow);
            g.fillRect(getCoords().getX() - getHitboxWidth() / 2, getCoords().getY() - getHitboxHeight() * 2 / 3,
                    getHitboxWidth() * rechargeCounter / rechargeFrames, getHitboxHeight() / 12);
        }
        g.setColor(Color.GREEN);
        for (int i = 0; i < (totalPhases - currentPhase - 1); i++) {
            g.fillRect(getCoords().getX() - getHitboxWidth() / 2 + i * (getHitboxHeight() / 6),
                    getCoords().getY() - getHitboxHeight() * 5 / 6, getHitboxHeight() / 12, getHitboxHeight() / 12);
        }
    }

}
