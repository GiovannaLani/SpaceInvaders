package game;

import java.io.IOException;

import javax.imageio.ImageIO;

public class AlienSquid extends Alien{
	public AlienSquid(double x, double y, int height, int width, GamePanel p) {
		super(x, y, height, width, p);
		try {
			alienImg1=ImageIO.read(getClass().getResourceAsStream("/player/calamar1.png"));
			alienImg2=ImageIO.read(getClass().getResourceAsStream("/player/calamar2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
