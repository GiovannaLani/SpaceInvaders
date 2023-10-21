package game;

import java.awt.Color;
import java.awt.Graphics2D;

public class PlayerShoot extends Shoot {

	public PlayerShoot(double x, double y, int height, int width, GamePanel p) {
		super(x, y, height, width, p);
		lives=1;
		speed=300;
		hasCollided = false;
	}

	@Override
	public void update(long millis) {
		// TODO Auto-generated method stub
		y-=speed*millis*0.001;
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.setColor(Color.GREEN);
		g2.fillRect((int)x, (int)y, width, height);
	}
	
	public boolean collidesTopBorder(){
		if(y+height>0) {
			return false;
		}
		return true;
		
		
	}

}
