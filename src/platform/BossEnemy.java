package platform;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BossEnemy extends Entity {

	public BossEnemy(int x, int y, int xVel, int yVel, int hitboxWidth, int hitboxHeight, Player playerReference,
			PGamePanel pgpRef, boolean tough) {
		super(x, y, xVel, yVel, hitboxWidth, hitboxHeight, 1, 150, pgpRef);
		this.playerReference = playerReference;
		this.tough = tough;
		try {
			this.setImgs(new BufferedImage[] {
					ImageIO.read(new File("images/enemy_walking_0.png")),
					ImageIO.read(new File("images/enemy_walking_1.png")),
					ImageIO.read(new File("images/enemy_walking_2.png"))
			});
		} catch (IOException e) {
		}
	}

	@Override
	boolean hitByProjectile(Projectile p) {
		setxVel(getxVel() + (p.getxVel() / 3));
		// setyVel(-10);
		setHp(getHp() - p.getDamage());
		setInvincibility(p.getInvincibilityFrames());
		return true;
	}

	private int projectileCooldown = 20;
	private boolean tough;
	private Player playerReference;

	private int jumpCooldown = 100;

	private int speed = 2;
	private int frameCooldown = 100;

	@Override
	public void process() {
		frameCooldown--;
		if (frameCooldown <= 0) {
			this.setPathToDisplay((getPathToDisplay() + 1) % 3);
			frameCooldown = 15 - (13 * (getMaxHp() - getHp()) / getMaxHp());
		}
		speed = 2 + ((getMaxHp() - getHp()) * 3 / getMaxHp());
		if (playerReference.getX() < getX()) {
			setHasTurnedLeft(true);
			if (getxVel() > -speed) {
				setxVel(Math.max(-speed, getxVel() - 0.5));
			} else if (getxVel() < -speed) {
				setxVel(Math.min(-speed, getxVel() + 0.5));
			}
		} else {
			setHasTurnedLeft(false);
			if (getxVel() > speed) {
				setxVel(Math.max(speed, getxVel() - 0.5));
			} else if (getxVel() < speed) {
				setxVel(Math.min(speed, getxVel() + 0.5));
			}
		}
		if (getyVel() > 3) {
			setyVel(Math.max(3, getyVel() + 0.2));
		} else if (getxVel() < 3) {
			setyVel(Math.min(3, getyVel() + 0.2));
		}
		projectileCooldown--;
		if (projectileCooldown <= 0) {
			double angle = Math.atan2(playerReference.getY() - getY(), playerReference.getX() - getX());
			/*
			 * if (getHp() < (getMaxHp()/2)) {
			 * ProjectileTestEnemyHoming testArrow = new
			 * ProjectileTestEnemyHoming((int)getX(), (int)getY(), angle, 10,
			 * playerReference);
			 * Main.projectiles.add(testArrow);
			 * if (getHp() < (getMaxHp()/4)) {
			 * angle += Math.PI/4;
			 * ProjectileTestEnemyHoming testArrow2 = new
			 * ProjectileTestEnemyHoming((int)getX(), (int)getY(), angle, 10,
			 * playerReference);
			 * Main.projectiles.add(testArrow2);
			 * angle -= Math.PI/2;
			 * ProjectileTestEnemyHoming testArrow3 = new
			 * ProjectileTestEnemyHoming((int)getX(), (int)getY(), angle, 10,
			 * playerReference);
			 * Main.projectiles.add(testArrow3);
			 * angle += Math.PI/4;
			 * }
			 * }
			 */
			if (tough) {
				ProjectileTestEnemyHomingExploding testArrow = new ProjectileTestEnemyHomingExploding(getX(), getY(), angle,
					10, playerReference, getPgpRef());
				getPgpRef().projectiles.add(testArrow);
			}
			if (getHp() <= (getMaxHp() * 3 / 4)) {
				angle += Math.PI / 8;
				ProjectileTestEnemy testArrow2 = new ProjectileTestEnemy(getX(), getY(), angle, 10, getPgpRef());
				getPgpRef().projectiles.add(testArrow2);
				angle -= Math.PI / 4;
				ProjectileTestEnemy testArrow3 = new ProjectileTestEnemy(getX(), getY(), angle, 10, getPgpRef());
				getPgpRef().projectiles.add(testArrow3);
				if (getHp() <= (getMaxHp() / 2)) {
					angle += Math.PI / 16;
					ProjectileTestEnemy testArrow4 = new ProjectileTestEnemy(getX(), getY(), angle, 10, getPgpRef());
					getPgpRef().projectiles.add(testArrow4);
					angle += Math.PI / 8;
					ProjectileTestEnemy testArrow5 = new ProjectileTestEnemy(getX(), getY(), angle, 10, getPgpRef());
					getPgpRef().projectiles.add(testArrow5);
				}
			}
			if (tough) {
				projectileCooldown = (int) (20 * Math.pow(1.005, getHp()));
			} else {
				projectileCooldown = (int) (50 * Math.pow(1.005, getHp()));
			}
		}
		super.process();
		jumpCooldown--;
		if (playerReference.getY() + 50 < getY() || jumpCooldown <= 0) {
			for (Platform p : getPgpRef().platforms) {
				Polygon poly = p.getPolygon();
				if (poly.contains(getBottom())) {
					setyVel(-20 - (getMaxHp() - getHp()) * 20 / getMaxHp());
					jumpCooldown = 100;
					break;
				}
			}
			for (Entity e : getPgpRef().entities) {
				Rectangle poly = e.getHitbox();
				if (poly.contains(getBottom())) {
					setyVel(-20);
					jumpCooldown = 100;
					break;
				}
			}
		}
	}

}
