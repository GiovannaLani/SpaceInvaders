package game;

import java.io.IOException;

import javax.imageio.ImageIO;

public class AlienCrab extends Alien {

	public AlienCrab(double x, double y, int height, int width, GamePanel p) {
		super(x, y, height, width, p);
		try {
			alienImg1 = ImageIO.read(getClass().getResourceAsStream("/images/crab1.png"));
			alienImg2 = ImageIO.read(getClass().getResourceAsStream("/images/crab2.png"));
			killImg = ImageIO.read(getClass().getResourceAsStream("/images/alien_kill.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
