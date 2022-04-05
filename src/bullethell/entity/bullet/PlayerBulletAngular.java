package bullethell.entity.bullet;

import bullethell.Coords;

public class PlayerBulletAngular extends PlayerBullet {
    
    private float velocity;
    private float angle;

    public PlayerBulletAngular(Coords coords, int spriteRadius, float velocity, float angle) {
        super(coords, spriteRadius);
        this.velocity = velocity;
        this.angle = angle;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public float findXDisp() {
        return (float) Math.cos(angle) * velocity;
    }

    @Override
    public float findYDisp() {
        return (float) Math.sin(angle) * velocity;
    }
}