package platform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ProjectileTest extends ProjectileAngular {

	public ProjectileTest(double x, double y, double angle, double velocity, PGamePanel pgpRef) {
		super(x, y, angle, velocity, 10, 10, 100, new boolean[] {false, true}, 50, pgpRef);
		try {
			setImgs(new BufferedImage[] {
					ImageIO.read(new File("images/arrow.png"))
			});
		} catch (IOException e) {}
	}

	@Override
	void process() {
		super.process();
	}

}
