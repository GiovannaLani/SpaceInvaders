package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Drop extends Shoot {

	private Random random;
	public static int SHOOT_THREE, SHOOT_SPEED, LARGER_SHIP, LASER, ALIEN_LINE, PERSONAL_SHIELD;

	public Drop(double x, double y, int height, int width, GamePanel p) {

		super(x, y, height, width, p);
		lives = 1;
		speed = 150;
		hasCollided = false;
		random = new Random();
		SHOOT_THREE = 0;
		LARGER_SHIP = 0;
		LASER = 0;
		ALIEN_LINE = 0;
		PERSONAL_SHIELD = 0;
		try {
			imgShoot = ImageIO.read(getClass().getResourceAsStream("/images/drop.png"));
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

	public void dropFunction () {
		int type = random.nextInt(6);
		switch (type) {
		case 0: {
			//Cambia velocidad de disparo jugador
//			SHOOT_SPEED = p.getTimeCounter();
		}case 1: {
//			PERSONAL_SHIELD = p.getTimeCounter();
		}case 2: {
//			SHOOT_THREE=p.getTimeCounter();
		}case 3: {
//			LARGER_SHIP = p.getTimeCounter();
		}case 4: {
//			ALIEN_LINE = 1;
		}case 5: {
			
		} 
		default:

		}


	}


}