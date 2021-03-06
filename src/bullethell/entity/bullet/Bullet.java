package bullethell.entity.bullet;

import bullethell.BulletPanel;
import bullethell.Coords;

/* FLOWCHART:
1) get xVel, yVel
2) increment position by said values
3) run process()
4) add one to frames
*/
public abstract class Bullet {
    private int damage = 1;
    private Coords coords;
    private float x, y;
    private int framesAlive = 0;
    private boolean killMe = false;
    public static final int KILL_OFFSET = 100;
    private boolean ignoreKillOffset = false;
    private boolean dieWhenCollide = true;

    public Bullet(Coords coords) {
        this.coords = coords;
        this.x = coords.getX();
        this.y = coords.getY();
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getFramesAlive() {
        return framesAlive;
    }

    public void setFramesAlive(int framesAlive) {
        this.framesAlive = framesAlive;
    }

    public boolean isKillMe() {
        return killMe;
    }

    public void setKillMe(boolean killMe) {
        this.killMe = killMe;
    }

    public boolean isIgnoreKillOffset() {
        return ignoreKillOffset;
    }

    public void setIgnoreKillOffset(boolean ignoreKillOffset) {
        this.ignoreKillOffset = ignoreKillOffset;
    }

    public boolean isDieWhenCollide() {
        return dieWhenCollide;
    }

    public void setDieWhenCollide(boolean dieWhenCollide) {
        this.dieWhenCollide = dieWhenCollide;
    }

    public void update() {
        float xInc = findXDisp();
        float yInc = findYDisp();
        x += xInc;
        y += yInc;
        coords.shiftCoords((int) x - coords.getX(), (int) y - coords.getY());
        process();
        if (!ignoreKillOffset && (getCoords().getX() < -KILL_OFFSET || getCoords().getY() < -KILL_OFFSET ||
                getCoords().getX() > BulletPanel.getBulletPanelWidth() + KILL_OFFSET ||
                getCoords().getY() > BulletPanel.getBulletPanelHeight() + KILL_OFFSET)) {
            setKillMe(true);
        }
        framesAlive++;
    }

    // make sure this and findYDisp() depend only on framesAlive, nothing else
    public abstract float findXDisp();

    public abstract float findYDisp();

    public abstract void process();
}
