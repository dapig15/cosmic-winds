package platform;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TimerTask;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PGamePanel extends JPanel implements ActionListener {

	private final long serialVersionUID = 1L;
	Player mainPlayer;

	ArrayList<Platform> platforms = new ArrayList<>();
	ArrayList<Entity> entities = new ArrayList<>();
	ArrayList<Projectile> projectiles = new ArrayList<>();
	ArrayList<Projectile> projectilestoAdd = new ArrayList<>();

	Timer timer;

	int hitShots = 0, totalShots = 0;

	public BufferedImage bi;
	public BufferedImage pointer;

	private boolean shouldTerminate, isGameOver;

	public boolean shouldTerminate() {
		return shouldTerminate;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	private int[][] generatedMaze;
	private int killCount = 0, maxKills;

	public PGamePanel(int maxKills, int width) {
		mainPlayer = new Player(128, 128, 0, 0, 32, 48, this);
		entities.add(mainPlayer);
		this.setPreferredSize(new Dimension(1024, 512));
		this.addKeyListener(mainPlayer.getPKA());
		this.addMouseListener(mainPlayer.getPML());
		this.setFocusable(true);
		try {
			pointer = ImageIO.read(new File("images/pointer.png"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		generatedMaze = generateMaze(width);
		this.maxKills = maxKills;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString(hitShots + "/" + totalShots, 0, 20);
		int playerXInc = (this.getWidth() / 2) - (int) mainPlayer.getX();
		int playerYInc = (this.getHeight() / 2) - (int) mainPlayer.getY();
		g.translate(playerXInc, playerYInc);
		g.drawImage(bi.getSubimage(
				Math.max(0, Math.min(bi.getWidth() - getWidth(), (int) mainPlayer.getX() - getWidth() / 2)),
				Math.max(0, Math.min(bi.getHeight() - getHeight(), (int) mainPlayer.getY() - getHeight() / 2)),
				getWidth(), getHeight()), (int) mainPlayer.getX() - getWidth() / 2,
				(int) mainPlayer.getY() - getHeight() / 2, null);
		double xInc = ((mainPlayer.getX() < getWidth() / 2) ? -(getWidth() / 2 - mainPlayer.getX())
				: (mainPlayer.getX() > bi.getWidth() - getWidth() / 2)
						? (mainPlayer.getX() - bi.getWidth() + getWidth() / 2)
						: 0);
		double yInc = ((mainPlayer.getY() < getHeight() / 2) ? -(getHeight() / 2 - mainPlayer.getY())
				: (mainPlayer.getY() > bi.getHeight() - getHeight() / 2)
						? (mainPlayer.getY() - bi.getHeight() + getHeight() / 2)
						: 0);
		mainPlayer.updateScreenCoords((int) (getWidth() / 2 + xInc), (int) (getHeight() / 2 + yInc));
		for (Entity e : entities) {
			e.drawObject(g, xInc, yInc);
			g.setColor(Color.red);
			g.fillRect((int) (e.getX() - e.getHitboxWidth() / 2 + xInc),
					(int) (e.getY() - e.getHitboxHeight() / 2 - 15 + yInc),
					(int) (e.getHitboxWidth()), 5);
			g.setColor(Color.green);
			g.fillRect((int) (e.getX() - e.getHitboxWidth() / 2 + xInc),
					(int) (e.getY() - e.getHitboxHeight() / 2 - 15 + yInc),
					(int) (e.getHitboxWidth() * e.getHp() / e.getMaxHp()), 5);
			g.setColor(Color.black);
		}
		for (Projectile p : projectiles) {
			p.drawObject(g, xInc, yInc);
		}

		for (int i = 1; i < entities.size(); i++) {
			Graphics2D g2d = (Graphics2D) g.create();
			double angle = Math.atan2(entities.get(i).getY() - mainPlayer.getY(),
					entities.get(i).getX() - mainPlayer.getX());
			g2d.rotate(angle, mainPlayer.getX() + xInc, mainPlayer.getY() + yInc);
			g2d.drawImage(pointer,
					(int) (mainPlayer.getX() - pointer.getWidth() / 2 + xInc + 130 * Math.sin(Math.PI / 2)),
					(int) (mainPlayer.getY() - pointer.getHeight() / 2 + yInc + 130 * Math.cos(Math.PI / 2)), null);
			g2d.dispose();
		}
		if (killCount >= maxKills) {
			Graphics2D g2d = (Graphics2D) g.create();
			double angle = Math.atan2(((generatedMaze.length - 2) * 128) + 64 - mainPlayer.getY(),
					((generatedMaze.length - 2) * 128) + 64 - mainPlayer.getX());
			g2d.rotate(angle, mainPlayer.getX() + xInc, mainPlayer.getY() + yInc);
			g2d.drawImage(pointer,
					(int) (mainPlayer.getX() - pointer.getWidth() / 2 + xInc + 130 * Math.sin(Math.PI / 2)),
					(int) (mainPlayer.getY() - pointer.getHeight() / 2 + yInc + 130 * Math.cos(Math.PI / 2)), null);
			g2d.dispose();
		}

		if (fadeTimer != 0) {
			g.setColor(new Color(0, 0, 0, Math.max(0, Math.min(1, fadeTimer / 100f))));
			g.fillRect((int) (mainPlayer.getX() - getWidth() + xInc),
					(int) (mainPlayer.getY() - getHeight() + yInc), getWidth() * 2, getHeight() * 2);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.repaint();
	}

	private int cooldown = 0;
	private final int maxCooldown = 100;

	private boolean inBounds(int[] coords) {
		return coords[0] > 0 && coords[0] < generatedMaze[0].length - 1 && coords[1] > 0
				&& coords[1] < generatedMaze.length - 1;
	}

	private boolean isValid(int[] coords) {
		return (generatedMaze[coords[1]][coords[0]] == 0 && generatedMaze[coords[1] + 1][coords[0]] == 1);
	}

	private int fadeTimer = 100;
	private boolean ending = false;

	public void update() {
		if (fadeTimer > 0) {
			if (ending) {
				fadeTimer++;
				if (fadeTimer >= 100) {
					shouldTerminate = true;
					isGameOver = mainPlayer.getHp() <= 0;
				}
				return;
			} else {
				fadeTimer--;
				if (fadeTimer < 0) {
					fadeTimer = 0;
				}
			}
		}
		if (mainPlayer.getHp() <= 0) {
			Thread.currentThread().interrupt();
			return;
		}

		projectilestoAdd = new ArrayList<>();
		for (Projectile proj : projectiles) {
			proj.process();
			int shouldKill = proj.shouldKill();
			if (shouldKill < 0) {
				projectilestoAdd.add(proj);
			} else if (shouldKill >= 0 && proj.canHitType(1)) {
				totalShots++;
				if (shouldKill == 2)
					hitShots++;
			}
		}
		projectiles = projectilestoAdd;

		ArrayList<Entity> newEntities = new ArrayList<Entity>();
		for (Entity entity : entities) {
			entity.process();
			if (!(entity.getHp() <= 0 && !(entity instanceof Player))) {
				newEntities.add(entity);
			} else if (entity.getHp() <= 0 && !(entity instanceof Player)) {
				killCount++;
			}
		}
		if (killCount < maxKills && entities.size() == 1) {
			cooldown++;
			if (cooldown == maxCooldown) {
				cooldown = 0;
				int dist = 8;
				int myX = (int) (mainPlayer.getX() / 128), myY = (int) (mainPlayer.getY() / 128);
				int spawnX = -1, spawnY = -1;
				while (spawnX == -1 || spawnY == -1) {
					boolean isValid = false;
					ArrayList<int[]> validSpots = new ArrayList<>();
					for (int i = -dist; i <= dist; i++) {
						ArrayList<int[]> tempSpots = new ArrayList<>();

						tempSpots.add(new int[] { myX + i, myY + dist });
						tempSpots.add(new int[] { myX + i, myY - dist });
						if (Math.abs(i) != dist) {
							tempSpots.add(new int[] { myX + dist, myY + i });
							tempSpots.add(new int[] { myX - dist, myY + i });
						}
						for (int[] spot : tempSpots) {
							if (inBounds(spot)) {
								isValid = true;
								if (isValid(spot)) {
									validSpots.add(spot);
								}
							}
						}
					}
					if (!isValid) {
						spawnX = myX;
						spawnY = myY;
					} else {
						if (validSpots.size() > 0) {
							int[] chosen = validSpots.get((int) (Math.random() * validSpots.size()));
							spawnX = chosen[0];
							spawnY = chosen[1];
						} else {
							dist++;
						}
					}
				}
				newEntities.add(new BossEnemy(spawnX * 128 + 64, spawnY * 128 + 64, 0, 0, 50, 50, mainPlayer, this));
			}
		}
		entities = newEntities;

		if ((killCount >= maxKills && (int) (mainPlayer.getX() / 128) == generatedMaze[0].length - 2
				&& (int) (mainPlayer.getY() / 128) == generatedMaze.length - 2) || mainPlayer.getHp() <= 0) {
			ending = true;
			fadeTimer++;
		}
	}

	private int[][] generateMaze(int width) {
		int[][] maze = new int[width][width];
		boolean[][] visited = new boolean[maze.length][maze[0].length];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				maze[i][j] = 1;
				if (i == 0 || i == maze.length - 1 || j == 0 || j == maze[0].length - 1) {
					visited[i][j] = true;
				}
			}
		}
		Stack<int[]> positions = new Stack<>();
		positions.add(new int[] { 1, 1 });
		final int[] yincr = new int[] { -1, 0, 1, 0 };
		final int[] xincr = new int[] { 0, 1, 0, -1 };
		while (!positions.isEmpty()) {
			int[] next = positions.peek();
			// System.out.println(Arrays.toString(next));
			int y = next[0], x = next[1];
			visited[y][x] = true;
			maze[y][x] = 0;
			boolean[] open = new boolean[4];
			int vis = 0;
			for (int i = 0; i < yincr.length; i++) {
				visited[y + yincr[i]][x + xincr[i]] = true;
			}
			for (int i = 0; i < yincr.length; i++) {
				boolean newVis = false;
				if (y + yincr[i] == 0 || y + yincr[i] == maze.length - 1 || x + xincr[i] == 0
						|| x + xincr[i] == maze[0].length - 1) {
					continue;
				}
				for (int j = 0; j < yincr.length; j++) {
					if (!visited[y + yincr[i] + yincr[j]][x + xincr[i] + xincr[j]]) {
						newVis = true;
					}
				}
				if (newVis) {
					open[i] = true;
					vis++;
				}
			}
			if (vis == 0) {
				positions.pop();
			} else {
				int chosen = (int) (Math.random() * vis);
				/*
				 * if (open[1]) {
				 * positions.add(new int[] { y, x+1 });
				 * continue;
				 * }
				 * if (open[3]) {
				 * positions.add(new int[] { y, x-1 });
				 * continue;
				 * }
				 */
				for (int i = 0; i < open.length; i++) {
					if (open[i]) {
						if (chosen == 0) {
							positions.add(new int[] { y + yincr[i], x + xincr[i] });
							break;
						} else {
							chosen--;
						}
					}
				}
			}
		}
		maze[maze.length - 2][maze[0].length - 2] = 0;
		bi = new BufferedImage(maze.length * 128, maze[0].length * 128, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		g.setColor(new Color(100, 100, 100, 255));
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				Graphics2D g2d = (Graphics2D) g.create();
				try {
					BufferedImage image = ImageIO.read(new File("images/bricksbkgd.png"));
					// g2d.rotate(Math.atan2(proj.getyVel(), proj.getxVel())-(Math.PI/2),
					// proj.getX()-(proj.getHitboxWidth()/2)+playerXInc,
					// proj.getY()-(proj.getHitboxHeight()/2)+playerYInc);
					g2d.drawImage(image, i * 128, j * 128, null);
					g2d.dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				if (maze[i][j] == 1) {
					PlatformStoneBrick p = new PlatformStoneBrick(
							new Polygon(new int[] { j * 128, j * 128 + 128, j * 128 + 128, j * 128 },
									new int[] { i * 128, i * 128, i * 128 + 128, i * 128 + 128 }, 4));
					platforms.add(p);
					Graphics2D g2d = (Graphics2D) g.create();
					try {
						BufferedImage image = ImageIO.read(new File("images/bricks.png"));
						// g2d.rotate(Math.atan2(proj.getyVel(), proj.getxVel())-(Math.PI/2),
						// proj.getX()-(proj.getHitboxWidth()/2)+playerXInc,
						// proj.getY()-(proj.getHitboxHeight()/2)+playerYInc);
						g2d.drawImage(image, j * 128, i * 128, null);
						g2d.dispose();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return maze;
	}
}
