package platform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ProjectileHit extends ProjectileAngular {

	public ProjectileHit(double x, double y, double angle) {
		super(x, y, angle, 0, 10, 10, 100, new boolean[] {false, true}, 100);
		setImgs(new BufferedImage[] {});
	}

	@Override
	void process() {
		super.process();
    }
}
