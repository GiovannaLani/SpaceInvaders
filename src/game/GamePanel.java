package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import domain.Game;
import domain.Player;
import gui.Menu.Menu;


public class GamePanel extends JPanel implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	public volatile boolean shouldRestart = false;

	private final Object gameLock = new Object();


	private Thread gameThread;
	private long millis = 40;
	private World world;
	private volatile boolean gamePaused;
	private PausePanel pausePanel;
	private GameOver gameOverPanel;
	public JLayeredPane layeredPane;
	private LevelType customLevel;
	private SpaceInvaders window;
	private int timeCounter = 0;
	private final Object pauseLock = new Object();
	private Game game;


	public GamePanel(Player player, Game game, LevelType customLevel, Menu menu , SpaceInvaders window) {
		this.customLevel=customLevel;
		this.window=window;
		this.game = game;
		setBackground(Color.black);
		setAlignmentY(Component.CENTER_ALIGNMENT);
		setPreferredSize(new Dimension(640, 700));
		setFocusable(true);
		addKeyListener(this);
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(640,700));
		world = new World(this, customLevel,0,3,null);
		gamePaused = false;
		pausePanel = new PausePanel(this, menu);
		pausePanel.setBounds(160,175,(int)pausePanel.getPreferredSize().getWidth(),(int)pausePanel.getPreferredSize().getHeight());
		pausePanel.setVisible(false);
		
		gameOverPanel = new GameOver(this, menu);
		gameOverPanel.setBounds(120,175,(int)gameOverPanel.getPreferredSize().getWidth(),(int)gameOverPanel.getPreferredSize().getHeight());
		gameOverPanel.setVisible(false);
		
		layeredPane.add(this,JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(pausePanel,JLayeredPane.POPUP_LAYER);
		layeredPane.add(gameOverPanel,JLayeredPane.POPUP_LAYER);
		this.setBounds(0,0,640,700);
	}

	public void startGameThread() {
		if(gameThread == null || !gameThread.isAlive()) {
			gameThread = new Thread(this);
			gameThread.start();
		}else {
			System.out.println("Attemped to start new thread while thread is alive");
		}
	}

	@Override
	public void run() {
		long timeInc = System.currentTimeMillis();
		long timeEnd = System.currentTimeMillis();
		long startTime = System.currentTimeMillis();
		long millisEnd;
		try {
			while (!world.getPlayer().isDead() && gameThread!=null && !Thread.currentThread().isInterrupted() && !shouldRestart) {
				if(!(gamePaused)) {
					timeInc= System.currentTimeMillis();
					update();
					repaint();
					timeEnd=System.currentTimeMillis();
					millisEnd=millis-(timeEnd-timeInc);
					timeCounter = (int) ((timeEnd-startTime)/1000);
					if(millisEnd>0) {
						Thread.sleep(millisEnd);
					}
				}else {
					synchronized (pauseLock) {
						while(gamePaused && !Thread.currentThread().isInterrupted()) { }
					}
				}
			}
			if(world.getPlayer().isDead()) {
				gameOverPanel.setVisible(true);
			}
		} catch (InterruptedException e) {

			System.out.println("Thread Interrupted");
		}
		finally {
			cleanThread();
			synchronized (this) {
				this.notifyAll();
			}
		}
		if (shouldRestart) {
			synchronized (gameLock) {
				if (Thread.currentThread().isInterrupted()) {
					return; // No intentar reiniciar si el hilo actual está interrumpido
				}
				restartGame(world.getPlayer().getPoints(), world.getlShield());
			}
		}

	}
	public void update() {
		synchronized (pauseLock) {
			if(!gamePaused)world.update(millis);
		}
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
		g2.setColor(Color.WHITE);
		g2.drawString("SCORE", 10, 50);
		g2.drawString(world.getPlayer().getPoints() + "",70,90);
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
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			pauseGame();
			if(!(gamePaused)) {
				pausePanel.setVisible(false);
			}else {
				pausePanel.setVisible(true);
			}
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
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public void setGameThread(Thread gameThread) {
		this.gameThread = gameThread;
	}

	public int getTimeCounter() {
		return timeCounter;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}


	public void restartGame(int points, List<Shield> lShield) {
		synchronized (gameLock) {
			float alienSpeed;
			System.out.println("Restarting Game!");
			if (gameThread != null) {
				gameThread.interrupt();
				try {
					gameThread.join(1000); 
					if (gameThread != null && gameThread.isAlive()) {
						System.out.println("Warning: Game thread did not terminate in the expected time.");
					}
				} catch (InterruptedException exc) {

					System.out.println("Main thread interrupted while waiting for the game thread to finish.");
				}
				gameThread = null;
			}
			shouldRestart = false;
			pausePanel.setVisible(false);
			gameOverPanel.setVisible(false);
			this.requestFocus();
			if(points > 0) {
				game.setLevel(game.getLevel() + 1);
				alienSpeed = Alien.getSpeed();
			}else {
				game.setLevel(1);
				alienSpeed = 20;
			}
			world = new World(this, customLevel, points, 3, lShield);
			Drop.restartDrops();
			resumeGame();
			startGameThread();

		}
	}
	public void disposeWindow() {
		window.dispose();
	}
	public void pauseGame() {
		synchronized (pauseLock) {
			gamePaused = true;
		}
	}

	public void resumeGame() {
		gamePaused = false;
		synchronized (pauseLock) {
			pauseLock.notifyAll();
		}
	}
	private void cleanThread() {
		gameThread = null;
		gamePaused = false;
	}
}
