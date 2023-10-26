package game;

import java.io.IOException;

import javax.imageio.ImageIO;

public class AlienOctopus extends Alien {

	public AlienOctopus(double x, double y, int height, int width, GamePanel p) {
		super(x, y, height, width, p);
		try {
			alienImg1 = ImageIO.read(getClass().getResourceAsStream("/images/octopus1.png"));
			alienImg2 = ImageIO.read(getClass().getResourceAsStream("/images/octopus2.png"));
			killImg = ImageIO.read(getClass().getResourceAsStream("/images/alien_kill.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
