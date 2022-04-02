package bullethell;

public class Coords {
    private int x, y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void shiftCoords(int xInc, int yInc) {
        this.x += xInc;
        this.y += yInc;
    }

    public float distanceTo(Coords o) {
        return (float) Math.sqrt((x - o.x) * (x - o.x) + (y - o.y) * (y - o.y));
    }

    public float angleTo(Coords o) {
        return (float) Math.atan2(o.y - y, o.x - x);
    }

    public Coords deepClone() {
        return new Coords(x, y);
    }
}
