package bullethell.entity.bullet.patterns;

import java.util.ArrayList;

import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;

public interface EnemyBulletPattern {
    public void create(ArrayList<EnemyBullet> toReturn);
}