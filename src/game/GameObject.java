package game;

import java.awt.Graphics2D;

public abstract class GameObject {
	protected double x;
	protected double y;
	protected int height;
	protected int width;
	protected int lives;
	protected GamePanel p;
	
	public GameObject(double x, double y, int height, int width, GamePanel p) {
		super();
		this.x = x;
		this.y = y;
		this.height=height;
		this.width=width;
		this.p=p;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public abstract void update(long millis);
	public abstract void draw(Graphics2D g2);
	
	public boolean collidesWith(GameObject gO2) {
		return  this.x<gO2.x+gO2.getWidth() && this.x + this.width >gO2.getX() && this.y< gO2.getY() +gO2.getHeight() && this.y +this.height>gO2.getY();
	}
	
	public boolean isDead() {
		return lives<=0;
	}
	
}
