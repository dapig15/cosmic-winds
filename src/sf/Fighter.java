package sf;
import utility.*;
public class Fighter {
    private Hitbox body; //(hitbox)
    private Move currMove = null; //(move currently being used)
    private int frameCount = 0; //(frame of move currently in)
    private boolean vulnerable = false; //(if vulnerable to attacks)
    private int comboCount = 0; //(how many consecutive moves opp has registered on self)
    public double vx, vy; //
    private final double defaultSpeed;
    private final Move[] moves; //(possible moves)
    private final double g = 10; //(gravitational acceleration)
    public Fighter(Hitbox body, double defaultSpeed, double vy, Move[] moves) {
        this.body = body;
        this.defaultSpeed = defaultSpeed;
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
    public double getDefaultSpeed() {
        return defaultSpeed;
    }
    public double getVx() {
        return vx;
    }
    public void setVx(double vx) {
        this.vx = vx;
    }
    public double getVy() {
        return vy;
    }
    public void setVy(double vy) {
        this.vy = vy;
    }
    public Move[] getMoves() {
        return moves;
    }
    public double getG() {
        return g;
    }
}