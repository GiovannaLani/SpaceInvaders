package game;

import java.awt.Graphics2D;

public class Bomb extends GameObject {
	protected int spriteCounter;
	protected double xCenter, yCenter;

	public Bomb(double x, double y, GamePanel p) {
		super(x, y, 10, 10, p);
		lives = 1;
		spriteCounter = 0;
		xCenter = x;
		yCenter = y;
	}

	@Override
	public void update(long millis) {
		spriteCounter++;
		if (spriteCounter >= 10)
			lives = 0;
	}

	@Override
	public void draw(Graphics2D g2) {
		width = (int) (width + spriteCounter * 1.5);
		height = width;
		x = xCenter - width / 2;
		y = yCenter - width / 2;

		g2.drawOval((int) x, (int) y, width, height);
	}

}
