package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Drop extends Shoot {

	private static Random random = new Random();
	public static int SHOOT_THREE, SHOOT_SPEED, LARGER_SHIP, BOMB, ALIEN_LINE, PERSONAL_SHIELD, PLAYER_SPEED;

	public Drop(double x, double y, int height, int width, GamePanel p) {

		super(x, y, height, width, p);
		lives = 1;
		speed = 150;
		hasCollided = false;
		try {
			imgShoot = ImageIO.read(getClass().getResourceAsStream("/images/drop.png"));
		} catch (IOException e) {
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

	public void dropFunction() {
		int type = random.nextInt(7);
		switch (type) {
		case 0: {
			SHOOT_SPEED = p.getTimeCounter();
			break;
		}
		case 1: {
			PERSONAL_SHIELD = p.getTimeCounter();
			break;

		}
		case 2: {
			SHOOT_THREE = p.getTimeCounter();
			break;

		}
		case 3: {
			LARGER_SHIP = p.getTimeCounter();
			break;

		}
		case 4: {
			ALIEN_LINE = 1;
			break;

		}
		case 5: {
			BOMB = p.getTimeCounter();
			break;
		}
		case 6: {
			PLAYER_SPEED = p.getTimeCounter();
			break;
		}
		}

	}
	
	public static void restartDrops() {
		SHOOT_SPEED = 0;
		SHOOT_THREE = 0;
		LARGER_SHIP = 0;
		BOMB = 0;
		ALIEN_LINE = 0;
		PERSONAL_SHIELD = 0;
		PLAYER_SPEED = 0;
	}

}