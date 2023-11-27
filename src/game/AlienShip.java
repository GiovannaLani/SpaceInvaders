package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class AlienShip extends Alien {
	private boolean isKilled;
	private float alienShipSpeed;
	private int alienShipDirection;
	private Random random;

	public AlienShip(double x, double y, int height, int width, GamePanel p, int alienShipDirection) {
		super(x, y, height, width, p);
		try {
			alienImg1 = ImageIO.read(getClass().getResourceAsStream("/images/alien_ship.png"));
			killImg = ImageIO.read(getClass().getResourceAsStream("/images/alien_kill.png"));
			setKilled(false);
			random = new Random();
			this.alienShipDirection = alienShipDirection;
			setPoints(random.nextInt((300-50) + 1) + 50);
			setAlienShipSpeed(200);
			System.out.println(x);
			System.out.println(alienShipDirection);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		if (lives > 0 || (lives == 0 && !(isKilled))) {
			image = alienImg1;	
		}else if(lives <= 0 && isKilled){
			image = killImg;
		}
		g2.drawImage(image, (int) x, (int) y, width, height, null);
	}



	@Override
	public boolean collidesBorder() {
		if(alienShipDirection == -1) {
			if (x < -width/2) {
				return true;
			}
		}else {
			if (x > 640 + width/2) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void update(long millis) {
		// Movimiento
		x += alienShipSpeed * alienShipDirection * millis * 0.001;
		// Animación
		if (lives <= 0) {
			setAlienShipSpeed(0);
			timeAfterDying += millis;
		}
	}

	public boolean isKilled() {
		return isKilled;
	}

	public void setKilled(boolean isKilled) {
		this.isKilled = isKilled;
	}

	public void setAlienShipSpeed(float speed) {
		this.alienShipSpeed = speed;
	}
	
	public float getAlienShipSpeed() {
		return alienShipSpeed;
	}

	public int getAlienShipDirection() {
		return alienShipDirection;
	}

	public void setAlienShipDirection(int alienShipDirection) {
		this.alienShipDirection = alienShipDirection;
	}
	
}
