package streetfighter;

import utility.*;

public class Fighter {
    //public int health = 0, specialMeter = 0;
    private Hitbox body; // (hitbox) (320 tall, 160 wide)
    /*
    private Move currMove = null; // (move currently being used)
    private int frameCount = 0; // (frame of move currently in)
    private boolean vulnerable = false; // (if vulnerable to attacks)
    private int comboCount = 0; // (how many consecutive moves opp has registered on self)
    public int vx, vy; //
    private final int defaultSpeed;
    private final Move[] moves; // (possible moves)
    private final Move special;
    private final int g = 10; // (gravitational acceleration)
    */

    public Fighter(Hitbox body/*, int defaultSpeed, double vy, Move[] moves, Move special*/) {
        this.body = body;
        /*
        this.defaultSpeed = defaultSpeed;
        this.moves = moves;
        this.special = special;
        */
    }

    public Hitbox getBody() {
        return body;
    }

    public void setBody(Hitbox body) {
        this.body = body;
    }
    /*
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

    public int getDefaultSpeed() {
        return defaultSpeed;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public Move[] getMoves() {
        return moves;
    }

    public Move getSpecial() {
        return special;
    }

    public double getG() {
        return g;
    }
    */
}