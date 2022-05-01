package bullethell.entity.bullet.patterns;

import java.util.ArrayList;

import bullethell.Coords;
import bullethell.entity.bullet.Bullet;
import bullethell.entity.bullet.EnemyBullet;
import bullethell.entity.bullet.EnemyBulletAngular;

public class BulletPatternCaller {
    public static interface IntervalCalculator {
        boolean canSpawn(int frames);
    }

    public static boolean canSpawn(int frames, int delay, int interval) {
        return (frames >= delay && ((frames - delay) % interval == 0));
    }

    public static void spawnPattern(ArrayList<EnemyBullet> toReturn, EnemyBulletPattern pattern) {
        pattern.create(toReturn);
    }

    public static void processPattern(ArrayList<EnemyBullet> toReturn, EnemyBulletPattern pattern, int frames,
            int delay, int interval) {
        if (frames >= delay && ((frames - delay) % interval == 0)) {
            pattern.create(toReturn);
        }
    }

    public static void processPattern(ArrayList<EnemyBullet> toReturn, EnemyBulletPattern pattern, int frames,
            IntervalCalculator calc) {
        if (calc.canSpawn(frames)) {
            pattern.create(toReturn);
        }
    }
}