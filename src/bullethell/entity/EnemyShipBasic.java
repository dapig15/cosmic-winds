package bullethell.entity;

import bullethell.Coords;

public abstract class EnemyShipBasic extends EnemyShip {

    private PlayerShip psRef;
    private int initialYVel;

    public EnemyShipBasic(Coords coords, int hitboxWidth, int hitboxHeight, String imagePath, int health,
            PlayerShip psRef, int initialYVel) {
        super(coords, hitboxWidth, hitboxHeight, imagePath, health);
        this.psRef = psRef;
        this.initialYVel = initialYVel;
    }

    @Override
    public int findXDisp() {
        return 0;
    }

    @Override
    public int findYDisp() {
        return Math.max(initialYVel - (getFramesAlive() / 3), 0);
    }

    public PlayerShip getPsRef() {
        return psRef;
    }

    public void setPsRef(PlayerShip psRef) {
        this.psRef = psRef;
    }

    public int getInitialYVel() {
        return initialYVel;
    }

    public void setInitialYVel(int initialYVel) {
        this.initialYVel = initialYVel;
    }

    public float angleToPlayer() {
        return (float) Math.atan2(psRef.getCoords().getY() - getCoords().getY(),
                psRef.getCoords().getX() - getCoords().getX());
    }

    @Override
    public void getHit(PlayerBullet pb) {
        health -= 1;
    }

}
