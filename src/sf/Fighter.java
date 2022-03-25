package sf;

import utility.*;

public class Fighter {
    private Hitbox body; // (hitbox)
    private Move currMove = null; // (move currently being used)
    private int frameCount = 0; // (frame of move currently in)
    private boolean vulnerable = false; // (if vulnerable to attacks)
    private int comboCount = 0; // (how many consecutive moves opp has registered on self)
    public boolean movingLeft = false, movingRight = false;
    private final double vx, vy; // (x and y velocity)
    private final Move[] moves; // (possible moves)
    private final double g = 10; // (gravitational acceleration)

    public Fighter(Hitbox body, double vx, double vy, Move[] moves) {
        this.body = body;
        this.vx = vx;
        this.vy = vy;
        this.moves = moves;
    }

    public Hitbox getBody() {
        return body;
    }

    public void setBody(Hitbox body) {
        this.body = body;
    }

    public Move getCurrMove() {
        return currMove;
    }

    public void setCurrMove(Move currMove) {
        this.currMove = currMove;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public boolean isVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public int getComboCount() {
        return comboCount;
    }

    public void setComboCount(int comboCount) {
        this.comboCount = comboCount;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public Move[] getMoves() {
        return moves;
    }

    public double getG() {
        return g;
    }

}