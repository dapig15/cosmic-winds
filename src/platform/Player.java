package platform;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player extends Entity {

	public Player(int x, int y, int xVel, int yVel, int hitboxWidth, int hitboxHeight, PGamePanel pgpRef) {
		super(x, y, xVel, yVel, hitboxWidth, hitboxHeight, 0, 200, pgpRef);
		/*
		 * this.setImgPaths(new String[] {
		 * "player_walking_0.png",
		 * "player_walking_1.png",
		 * "player_walking_2.png",
		 * });
		 */
		try {
			this.setImgs(new BufferedImage[] {
					PictureFixer.removeBackground(ImageIO.read(new File("images/idle1.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/idle2.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/idle3.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/idle4.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/idle5.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/idle6.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/idle7.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/idle8.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/run1.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/run2.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/run3.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/run4.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/run5.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/run6.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/run7.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/run8.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/dash12.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/dash11.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/dash10.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/dash9.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/dash8.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/dash7.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/shoot14.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/shoot13.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/shoot12.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/shoot11.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/shoot10.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/shoot9.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/shoot8.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/shoot7.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/shoot6.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/28.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/27.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/26.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/25.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/24.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/23.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/22.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/21.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/20.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/19.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/18.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/17.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/16.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/15.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/14.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/13.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/12.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/11.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/10.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/9.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/8.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/7.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/6.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/5.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/4.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/3.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/2.png"))),
					PictureFixer.removeBackground(ImageIO.read(new File("images/1.png")))
			});
		} catch (IOException e) {
		}
	}

	private int jumpsLeft = 0, maxJumps = 2;
	private int cycle = 0, dashing = 0, dashFrames = 12, shooting = 0, shootFrames = 18, hitting = 0, hitFrames = 56;
	private boolean shootDelay = true;
	private char indicator = 'N';
	private char currentDirection = 'N';
	private PlayerKeyAdapter pka = new PlayerKeyAdapter();
	private int screenX = 0, screenY = 0;

	public void updateScreenCoords(int screenX, int screenY) {
		this.screenX = screenX;
		this.screenY = screenY;
	}

	public PlayerKeyAdapter getPKA() {
		return pka;
	}

	private PlayerMouseListener pml = new PlayerMouseListener();

	public PlayerMouseListener getPML() {
		return pml;
	}

	private int dashWindow = 0, dashCooldown = 0;
	private boolean isDashRight = true;
	private boolean isPressContinue = false;

	public boolean isPressContinue() {
		return isPressContinue;
	}

	class PlayerKeyAdapter extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_W) {
				boolean againstWall = false;
				for (Platform plat : getPgpRef().platforms) {
					// TODO dont wall jum pfirst
					if (plat.getPolygon().contains(getLeft())) {
						setyVel(-16);
						setY(getY() - 16);
						setxVel(5);
						againstWall = true;
						break;
					}
					if (plat.getPolygon().contains(getRight())) {
						setyVel(-16);
						setY(getY() - 16);
						setxVel(-5);
						againstWall = true;
						break;
					}
				}
				if (jumpsLeft > 0 && !againstWall) {
					setyVel(-16);
					setY(getY() - 16);
					jumpsLeft--;
				}
			} else if (key == KeyEvent.VK_D) {
				currentDirection = 'D';
				setHasTurnedLeft(false);
				if (dashWindow > 0 && isDashRight) {
					System.out.println("dshf");
					setxVel(superMaxSpeed);
					dashWindow = 0;
					setInvincibility(8);
					dashCooldown = 20;
					dashing = dashFrames;
				}
			} else if (key == KeyEvent.VK_A) {
				currentDirection = 'A';
				setHasTurnedLeft(true);
				if (dashWindow > 0 && !isDashRight) {
					setxVel(-superMaxSpeed);
					dashWindow = 0;
					setInvincibility(8);
					dashCooldown = 20;
					dashing = dashFrames;
				}
			} else if (key == KeyEvent.VK_J) {
				if (hitting == 0) {
					hitting = hitFrames;
				}
			} else if (key == KeyEvent.VK_K) {
				if (shooting == 0) {
					spawnProjectile = true;
				}
			} else if (key == KeyEvent.VK_B) {
				isPressContinue = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_D && currentDirection == 'D') {
				currentDirection = 'N';
				if (dashCooldown == 0) {
					dashWindow = 5;
					isDashRight = true;
				}
			} else if (key == KeyEvent.VK_A && currentDirection == 'A') {
				currentDirection = 'N';
				if (dashCooldown == 0) {
					dashWindow = 5;
					isDashRight = false;
				}
			} else if (key == KeyEvent.VK_J) {

			} else if (key == KeyEvent.VK_K) {
				spawnProjectile = false;
			} else if (key == KeyEvent.VK_B) {
				isPressContinue = false;
			}
		}
	}

	static int projectileCooldown = 0;
	static boolean spawnProjectile = false;

	class PlayerMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			spawnProjectile = false;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
			spawnProjectile = false;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			spawnProjectile = true;
		}

	}

	private double maxSpeed = 10, superMaxSpeed = 20, slipFactor = 0.5;

	private int projectilesLeft = 1;

	public void addAProjectile() {
		projectilesLeft++;
	}

	private int frameCooldown = 25;

	@Override
	void process() {
		if (shootDelay && shooting == 0) {
			Point mousePosition = MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(mousePosition, getPgpRef());
			double angle = Math.atan2(mousePosition.getY() - (screenY), mousePosition.getX() - (screenX));
			ProjectileTest testArrow = new ProjectileTest(getX(), getY() - 19, (getHasTurnedLeft() ? Math.PI : 0), 20,
					getPgpRef());
			// ProjectileTestBoomerang2 testBoomerang = new ProjectileTestBoomerang2(getX(),
			// getY(), angle, 20, this);
			if (testArrow.shouldKill() < 0 /* testBoomerang.shouldKill() < 0 */) {
				getPgpRef().projectiles.add(testArrow);
				// Main.projectiles.add(testBoomerang);
			}
			projectileCooldown = 17;
			// projectilesLeft--;
			shootDelay = false;
		}
		if (spawnProjectile && projectileCooldown == 0 && projectilesLeft > 0) {
			shootDelay = true;
			if (shooting == 0) {
				shooting = shootFrames;
			}
		}

		projectileCooldown = Math.max(0, projectileCooldown - 1);

		// movement
		if (currentDirection == 'D' && getxVel() < maxSpeed) {
			setxVel(Math.min(maxSpeed, getxVel() + slipFactor));
			frameCooldown--;
			if (frameCooldown <= 0) {
				// setPathToDisplay((getPathToDisplay()+1)%this.imgs.length);
				frameCooldown = (int) (maxSpeed + 1 - Math.abs(getxVel()));
			}
		} else if (currentDirection == 'A' && getxVel() > -maxSpeed) {
			setxVel(Math.max(-maxSpeed, getxVel() - slipFactor));
			frameCooldown--;
			if (frameCooldown <= 0) {
				// setPathToDisplay((getPathToDisplay()+1)%this.imgs.length);
				frameCooldown = (int) (maxSpeed + 1 - Math.abs(getxVel()));
			}
		} else {
			if (getxVel() > 0) {
				setxVel(Math.max(0, getxVel() - slipFactor));
			} else if (getxVel() < 0) {
				setxVel(Math.min(0, getxVel() + slipFactor));
			}
		}
		setxVel(Math.min(superMaxSpeed, Math.max(-superMaxSpeed, getxVel())));
		if (getxVel() > maxSpeed) {
			setxVel(Math.max(maxSpeed, getxVel() - slipFactor));
		}
		if (getxVel() < -maxSpeed) {
			setxVel(Math.min(-maxSpeed, getxVel() + slipFactor));
		}
		dashCooldown = Math.max(0, dashCooldown - 1);
		dashWindow = Math.max(0, dashWindow - 1);

		if (hitting > 0) {
			setPathToDisplay((28 - hitting / 2) + 30);
			hitting--;
		} else if (shooting > 0) {
			setPathToDisplay((9 - shooting / 2) + 21);
			shooting--;
		} else if (dashing > 0) {
			setPathToDisplay((6 - dashing / 2) + 15);
			dashing--;
		} else {
			if (currentDirection != indicator) {
				cycle = 0;
				indicator = currentDirection;
			} else
				cycle++;
			if (currentDirection == 'N') {
				setPathToDisplay(cycle / 3 % 8);
			} else {
				setPathToDisplay(cycle / 2 % 8 + 8);
			}
		}

		super.process();

		for (Platform plat : getPgpRef().platforms) {
			if (plat.getPolygon().contains(getBottom())) {
				jumpsLeft = maxJumps;
				break;
			}
		}
		for (Entity e : getPgpRef().entities) {
			Rectangle poly = e.getHitbox();
			if (e.getHitbox() != null && poly.contains(getBottom())) {
				jumpsLeft = maxJumps;
				break;
			}
		}
	}

	@Override
	boolean hitByProjectile(Projectile p) {
		// setxVel(getxVel()+(p.getxVel()/3));
		// setyVel(-10);
		setHp(getHp() - p.getDamage());
		setInvincibility(p.getInvincibilityFrames());
		return true;
	}
}