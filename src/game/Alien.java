package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Alien extends GameObject {

	protected static float speed;
	protected static int direction = 1;
	protected static boolean changeDirection;
	protected static long timeMaxDying = 200;
	protected int points;
	protected boolean collidesDownLimit = false;

	protected BufferedImage alienImg1, alienImg2, killImg;
	protected int spriteCounter = 0;
	protected int spriteNumber = 1;
	protected long timeAfterDying = 0;
	
	private double alienLimitY = 535;

	public Alien(double x, double y, int height, int width, GamePanel p) {
		super(x, y, height, width, p);
		lives = 1;
		speed = 20;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public static float getSpeed() {
		return speed;
	}

	public static void setSpeed(float speed) {
		Alien.speed = speed;
	}

	public static boolean isChangeDirection() {
		return changeDirection;
	}

	public static void setChangeDirection(boolean changeDirection) {
		Alien.changeDirection = changeDirection;
	}
	
	public static void setDirection(int direction) {
		Alien.direction = direction;
		
	}
	
	public static int getDirection() {
		return direction;
	}

	public static long getTimeMaxDying() {
		return timeMaxDying;
	}

	public boolean isCollidesDownLimit() {
		return collidesDownLimit;
	}

	public void setCollidesDownLimit(boolean collidesDownLimit) {
		this.collidesDownLimit = collidesDownLimit;
	}

	@Override
	public void update(long millis) {
		// Movimiento

		x += speed * direction * millis * 0.001;
		// Animación
		spriteCounter++;
		if (spriteCounter == 10) {
			if (spriteNumber == 1) {
				spriteNumber = 2;
			} else {
				spriteNumber = 1;
			}
			spriteCounter = 0;
		}
		if (lives <= 0) {
			timeAfterDying += millis;
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		if (lives > 0) {
			if (spriteNumber == 1) {
				image = alienImg1;
			} else {
				image = alienImg2;
			}
		} else {
			image = killImg;
		}
		g2.drawImage(image, (int) x, (int) y, width, height, null);

	}

	public boolean collidesBorder() {
		if (x + width >= p.getWidth()) {
			if (direction > 0) {
				return true;
			}
		} else if (x <= 0) {
			if (direction < 0) {
				return true;
			}
		}
		if (y >= alienLimitY) {
			collidesDownLimit = true;
		}
		return false;
	}

	@Override
	public boolean isDead() {
		return super.isDead() && timeAfterDying >= timeMaxDying;
	}

}
