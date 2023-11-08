package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AlienShoot extends Shoot {

	public AlienShoot(double x, double y, int height, int width, GamePanel p) {

		super(x, y, height, width, p);
		lives = 1;
		speed = 300;
		hasCollided = false;
		try {
			imgShoot = ImageIO.read(getClass().getResourceAsStream("/images/shoot_alien.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(long millis) {
		y += speed * millis * 0.001;
	}

	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = imgShoot;
		g2.drawImage(image, (int) x, (int) y, width, height, null);
	}

	public boolean collidesDownBorder() {
		if (y + height > p.getHeight()) {
			return true;
		}
		return false;
	}
}
