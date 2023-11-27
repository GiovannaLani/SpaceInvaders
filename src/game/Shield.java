package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Shield extends GameObject {
	
	private BufferedImage shield1, shield2, shield3, shield4;

	public Shield(double x, double y, int height, int width, GamePanel p, ShieldType type) {
		super(x, y, height, width, p);
		lives = 4;
		try {
			switch (type) {
			case NORMAL: {
				shield1 = ImageIO.read(getClass().getResourceAsStream("/images/shield1.png"));
				shield2 = ImageIO.read(getClass().getResourceAsStream("/images/shield2.png"));
				shield3 = ImageIO.read(getClass().getResourceAsStream("/images/shield3.png"));
				shield4 = ImageIO.read(getClass().getResourceAsStream("/images/shield4.png"));
				break;
			}
			case DOWNLEFT:{
				shield1 = ImageIO.read(getClass().getResourceAsStream("/images/shield_down_left1.png"));
				shield2 = ImageIO.read(getClass().getResourceAsStream("/images/shield_down_left2.png"));
				shield3 = ImageIO.read(getClass().getResourceAsStream("/images/shield_down_left3.png"));
				shield4 = ImageIO.read(getClass().getResourceAsStream("/images/shield_down_left4.png"));
				break;
			}case DOWNRIGHT:{
				shield1 = ImageIO.read(getClass().getResourceAsStream("/images/shield_down_right1.png"));
				shield2 = ImageIO.read(getClass().getResourceAsStream("/images/shield_down_right2.png"));
				shield3 = ImageIO.read(getClass().getResourceAsStream("/images/shield_down_right3.png"));
				shield4 = ImageIO.read(getClass().getResourceAsStream("/images/shield_down_right4.png"));
				break;
			}case UPLEFT:{
				shield1 = ImageIO.read(getClass().getResourceAsStream("/images/shield_up_left1.png"));
				shield2 = ImageIO.read(getClass().getResourceAsStream("/images/shield_up_left2.png"));
				shield3 = ImageIO.read(getClass().getResourceAsStream("/images/shield_up_left3.png"));
				shield4 = ImageIO.read(getClass().getResourceAsStream("/images/shield_up_left4.png"));
				break;
			}case UPRIGHT:{
				shield1 = ImageIO.read(getClass().getResourceAsStream("/images/shield_up_right1.png"));
				shield2 = ImageIO.read(getClass().getResourceAsStream("/images/shield_up_right2.png"));
				shield3 = ImageIO.read(getClass().getResourceAsStream("/images/shield_up_right3.png"));
				shield4 = ImageIO.read(getClass().getResourceAsStream("/images/shield_up_right4.png"));
				break;
			}
			default:
				shield1 = null;;
				shield2 = null;
				shield3 = null;
				shield4 = null;
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image1 = null;
		if (lives == 4) {
			image1=shield1;
		} else if(lives == 3){
			image1=shield2;

		}else if(lives == 2) {
			image1=shield3;

		}else if(lives == 1) {
			image1=shield4;
			
		}else {
			image1=null;

		}
		
		g2.drawImage(image1, (int) x, (int) y, width, height, null);
	}


	@Override
	public void update(long millis) {
		
	}
}
