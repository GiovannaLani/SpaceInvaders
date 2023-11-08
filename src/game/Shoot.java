package game;

import java.awt.image.BufferedImage;

public abstract class Shoot extends GameObject{
	protected int speed;
	protected BufferedImage imgShoot;
	protected boolean hasCollided;
	
	public Shoot(double x, double y, int height, int width, GamePanel p) {
		super(x, y, height, width, p);
		lives = 1;
	}


}
