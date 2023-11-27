package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerShip extends GameObject {
	public static boolean movingRight;
	public static boolean movingLeft;
	
	private int points;
	private BufferedImage imgPlayer, imgPlayerKill;
	private int speed_x = 300;

	public PlayerShip(double x, double y, int height, int width, GamePanel p) {
		super(x, y, height, width, p);
		lives = 3;
		try {
			imgPlayer = ImageIO.read(getClass().getResourceAsStream("/images/player.png"));
			imgPlayerKill = ImageIO.read(getClass().getResourceAsStream("/images/player_kill.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		if (isDead()) {
			image = imgPlayerKill;
		} else {
			image = imgPlayer;
		}
		g2.drawImage(image, (int) x, (int) y, width, height, null);
	}

	@Override
	public void update(long millis) {
		if (!isDead()) {
			collidesBorder();
			if (movingRight) {
				x = x + speed_x * millis * 0.001;
			}

			if (movingLeft) {
				x = x - speed_x * millis * 0.001;
			}
		}
	}

	public boolean collidesBorder() {
		if (x + width >= p.getWidth()) {
			movingRight = false;
		} else if (x <= 0) {
			movingLeft = false;
		}
		return false;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
