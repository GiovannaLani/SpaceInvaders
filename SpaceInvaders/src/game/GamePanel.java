package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	
	private Thread gameThread;
	private long millis = 40;
	private World world;

	public GamePanel() {
		setBackground(Color.black);
		setPreferredSize(new Dimension(640, 700));
		setFocusable(true);
		addKeyListener(this);
		world = new World(this);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		long timeInc = System.currentTimeMillis();
		long timeEnd = System.currentTimeMillis();
		long millisEnd;
		while (!world.getPlayer().isDead()) {
			timeInc = System.currentTimeMillis();
			update();
			repaint();
			timeEnd = System.currentTimeMillis();
			millisEnd = millis - (timeEnd - timeInc);
			try {
				Thread.sleep(millisEnd);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		world.update(millis);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.GREEN);
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(0, 640, 640, 640);
		g2.setFont(font(30));
		g2.drawString(world.getPlayer().getLives() + "", 10, 690);
		int[] coordLifes = { 50, 100, 150 };
		for (int i = 0; i < world.getPlayer().getLives(); i++) {
			try {
				g2.drawImage(ImageIO.read(getClass().getResourceAsStream("/images/player.png")), coordLifes[i], 650,
						13 * 3, 8 * 3, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		world.draw(g2);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			PlayerShip.movingLeft = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			PlayerShip.movingRight = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			world.shootPlayer();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			PlayerShip.movingLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			PlayerShip.movingRight = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public Font font(int size) {
		Font font = null;
		try {
			InputStream is = getClass().getResourceAsStream("/fuentes/PressStart2P-Regular.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			font = font.deriveFont(Font.PLAIN, size);
		} catch (Exception e) {
			font = new Font("Arial", Font.BOLD, size);
		}
		return font;
	}
}
