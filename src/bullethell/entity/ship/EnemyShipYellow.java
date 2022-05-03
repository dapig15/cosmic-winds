package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngularHoming;
import bullethell.entity.bullet.EnemyBulletNoAccel;
import bullethell.entity.bullet.patterns.BPArc;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class EnemyShipYellow extends EnemyShipBasic {
    private BPArc arcAttackRight = new BPArc(getCoords(), 12, 6, 8, (float) (-Math.PI / 32), (float) (Math.PI / 32));
    private BPArc arcAttackLeft = new BPArc(getCoords(), 12, 6, 8, (float) (Math.PI * 31 / 32),
            (float) (Math.PI * 33 / 32));

    public EnemyShipYellow(Coords coords, PlayerShip psRef, int initialYVel) {
        super(coords, 26, 26, "images/sprites/ships/stage_5_ship_yellow.png", 30, psRef, initialYVel);
        arcAttackRight.setBulletType(BPArc.ACCEL);
        arcAttackRight.setAccelFactor(-0.15f);
        arcAttackLeft.setBulletType(BPArc.ACCEL);
        arcAttackLeft.setAccelFactor(-0.15f);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 5)) {
            arcAttackRight.create(toReturn);
            arcAttackRight.shiftAngle((float) (Math.PI / 32));
            arcAttackLeft.create(toReturn);
            arcAttackLeft.shiftAngle(-(float) (Math.PI / 32));
        }
        return toReturn;
    }

}
