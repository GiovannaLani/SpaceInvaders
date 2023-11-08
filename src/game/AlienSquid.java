package game;

import java.io.IOException;

import javax.imageio.ImageIO;

public class AlienSquid extends Alien {
	public AlienSquid(double x, double y, int height, int width, GamePanel p) {
		super(x, y, height, width, p);
		try {
			alienImg1 = ImageIO.read(getClass().getResourceAsStream("/images/squid1.png"));
			alienImg2 = ImageIO.read(getClass().getResourceAsStream("/images/squid2.png"));
			killImg = ImageIO.read(getClass().getResourceAsStream("/images/alien_kill.png"));
			setPoints(30);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
