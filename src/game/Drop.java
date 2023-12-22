package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Drop extends Shoot {

	private Random random;
	public static int ALIEN_LINE;
	public int shootThree, shootSpeed, largerShip, laser, personalShield;

	public Drop(double x, double y, int height, int width, GamePanel p) {

		super(x, y, height, width, p);
		lives = 1;
		speed = 150;
		hasCollided = false;
		random = new Random();
		shootThree = 0;
		largerShip = 0;
		laser = 0;
		ALIEN_LINE = 0;
		personalShield = 0;
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
			shootSpeed = p.getTimeCounter();
		}case 1: {
			personalShield = p.getTimeCounter();
		}case 2: {
			shootThree = p.getTimeCounter();
		}case 3: {
			largerShip = p.getTimeCounter();
		}case 4: {
//			ALIEN_LINE = 1;
		}case 5: {
			
		} 
		default:

		}


	}


	public int getShootThree() {
		return shootThree;
	}


	public void setShootThree(int shootThree) {
		this.shootThree = shootThree;
	}


	public int getShootSpeed() {
		return shootSpeed;
	}


	public void setShootSpeed(int shootSpeed) {
		this.shootSpeed = shootSpeed;
	}


	public int getLargerShip() {
		return largerShip;
	}


	public void setLargerShip(int largerShip) {
		this.largerShip = largerShip;
	}


	public int getLaser() {
		return laser;
	}


	public void setLaser(int laser) {
		this.laser = laser;
	}


	public int getPersonalShield() {
		return personalShield;
	}


	public void setPersonalShield(int personalShield) {
		this.personalShield = personalShield;
	}
	
	


}