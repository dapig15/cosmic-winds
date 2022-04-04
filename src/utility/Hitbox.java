package utility;

import bullethell.Coords;

public class Hitbox {
    private int x1, y1, x2, y2;

    public Hitbox(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public boolean containsCoord(Coords c) {
        return (c.getX() >= x1 && c.getX() <= x2 && c.getY() >= y1 && c.getY() <= y2);
    }

    public boolean checkIfOverlaps(Hitbox o) {
        return (o.x1 >= x1 && o.x1 <= x2) || (o.x2 >= x1 && o.x2 <= x2) ||
                (o.y1 >= y1 && o.y1 <= y2) || (o.y2 >= y1 && o.y2 <= y2);
    }

    public void shiftHitbox(int xInc, int yInc) {
        x1 += xInc;
        x2 += xInc;
        y1 += yInc;
        y2 += yInc;
    }
}
