package bullethell.entity.ship;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;
import bullethell.entity.bullet.patterns.BulletPatternCaller;

public class EnemyShipRed extends EnemyShipBasic {

    public EnemyShipRed(Coords coords, PlayerShip psRef, Coords targetCoords) {
        super(coords, 32, 32, "images/sprites/ships/stage_5_ship_red.png", 30, targetCoords, psRef);
    }

    @Override
    public ArrayList<EnemyBullet> spawnBullets() {
        ArrayList<EnemyBullet> toReturn = new ArrayList<>();
        if (BulletPatternCaller.canSpawn(getFramesAlive(), 50, 1)) {
            toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 8,
                    angleToPlayer() + (float) (Math.sin(getFramesAlive() * 0.05f) * Math.PI)));
            toReturn.add(new EnemyBulletAngular(getCoords().deepClone(), 6, 8,
                    angleToPlayer() - (float) (Math.sin(getFramesAlive() * 0.05f) * Math.PI)));
        }
        return toReturn;
    }

}
