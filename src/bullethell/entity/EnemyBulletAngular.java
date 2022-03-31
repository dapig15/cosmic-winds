package bullethell.entity;

import bullethell.Coords;

public class EnemyBulletAngular extends EnemyBullet {

    private int velocity;
    private float angle;

    public EnemyBulletAngular(Coords coords, int spriteRadius, int velocity, float angle) {
        super(coords, spriteRadius);
        this.velocity = velocity;
        this.angle = angle;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
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
