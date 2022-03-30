package sf;
import utility.*;
public class Fighter {
    public int health = 0, specialMeter = 0;
    private Hitbox body; //(hitbox) (320 tall, 160 wide)
    private Move currMove = null; //(move currently being used)
    private int frameCount = 0; //(frame of move currently in)
    private boolean vulnerable = false; //(if vulnerable to attacks)
    private int comboCount = 0; //(how many consecutive moves opp has registered on self)
    public int vx = 0, vy = 0; //
    private final int defaultSpeed;
    private final Move[] moves; //(possible moves)
    private final Move special;
    public static final int g = 10; //(gravitational acceleration)
    public Fighter(Hitbox body, int defaultSpeed, Move[] moves, Move special) {
        this.body = body;
        this.defaultSpeed = defaultSpeed;
        this.moves = moves;
        this.special = special;
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
    public void updatePos(Fighter other, int direction) { // -1 = left, 0 = staying still, 1 = moving right
        int speed = 0, vspeed = 0;
        if (body.getY2() == Arena.boundy){
            if (direction == -1) 
                speed = -defaultSpeed;
            else if (direction ==  1)
                speed = defaultSpeed;
        } else {
            speed = vx;
            vspeed = vy;
        }
        if (speed < 0) { 
            Hitbox otherBody = other.getBody();
            int increment;
            if (otherBody.getX2() - speed >= body.getX1()) {
                increment = otherBody.getX2() - body.getX1() + 1;
            } else if (body.getX1() + speed < 0) {
                increment = body.getX1();
            } else {
                increment = speed;
            }
            body.setX1(body.getX1() + increment);
            body.setX2(body.getX2() + increment);
        } else if (speed > 0) {
            Hitbox otherBody = other.getBody();
            int increment;
            if (otherBody.getX1() - speed <= body.getX2()) {
                increment = otherBody.getX1() - body.getX2() - 1;
            } else if (body.getX2() + speed > 640) {
                increment = 640 - body.getX2();
            } else {
                increment = speed;
            }
            body.setX1(body.getX1() + increment);
            body.setX2(body.getX2() + increment);
        }
    }
}