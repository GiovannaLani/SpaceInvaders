package game;

import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import controller.DBException;
import controller.DatabaseController;

public class World {
	private static final int[] ALIEN_ROW = { 100, 130, 160, 190, 220 };
	private static final int ALIEN_CRAB_WIDTH = 11 * 3;
	private static final int ALIEN_OCTOPUS_WIDTH = 12 * 3;
	private static final int ALIEN_SQUID_WIDTH = 8 * 3;
	private static final int ALIEN_HEIGHT = 8 * 3;

	private static Random random = new Random();

	private GamePanel p;
	private List<GameObject> lGameObject;
	private List<Alien> lAlien;
	private List<Shield> lShield;
	private float alienSpeed = 40;
	private float maxSpeed = 50;
	private int elapsedTimeToAlienShipCreation = 10;

	private PlayerShoot playerShoot;
	private AlienShoot alienShoot;
	private PlayerShip player;
	private Shield shield;
	
	public World(GamePanel p, boolean customized, int points, int lives) {
		super();
		this.p = p;
		lGameObject = new ArrayList<>();
		lAlien = new ArrayList<>();
		lShield = new ArrayList<>();
		alienShoot = null;
		playerShoot = null;
		player = new PlayerShip(320, 590, 8 * 4, 13 * 4, p);
		player.setPoints(points);
		player.setLives(lives);
		lGameObject.add(player);
		//creacion escudos
		for(int i=0; i<4;i++) {
			createShield(60 + 150*i);
		}
		
		String[][] lEnemies = loadLevel("res/data/level.dat");
		if (customized && lEnemies != null) {
			for (int i = 0; i < lEnemies.length; i++) {
				for (int j = 0; j < lEnemies[0].length; j++) {
					String alienType = lEnemies[i][j];
					if (alienType != null) {
						if (alienType.equals(AlienCrab.class.getSimpleName())) {
							lAlien.add(new AlienCrab( 20 + j * 50 - ALIEN_CRAB_WIDTH / 2, 100 + 30 * i,
									ALIEN_HEIGHT, ALIEN_CRAB_WIDTH, p));
						} else if (alienType.equals(AlienOctopus.class.getSimpleName())) {
							lAlien.add(new AlienOctopus( 20 + j * 50 - ALIEN_OCTOPUS_WIDTH / 2,
									100 + 30 * i, ALIEN_HEIGHT, ALIEN_OCTOPUS_WIDTH, p));
						} else if (alienType.equals(AlienSquid.class.getSimpleName())) {
							lAlien.add(new AlienSquid( 20 + j * 50 - ALIEN_SQUID_WIDTH / 2, 100 + 30 * i,
									ALIEN_HEIGHT, ALIEN_SQUID_WIDTH, p));
						}

					}
				}
			}

		} else {

			for (int i = 0; i < 11; i++) {
				lAlien.add(new AlienSquid( 20 + i * 50 - ALIEN_SQUID_WIDTH / 2, ALIEN_ROW[0],
						ALIEN_HEIGHT, ALIEN_SQUID_WIDTH, p));
				lAlien.add(new AlienCrab( 20 + i * 50 - ALIEN_CRAB_WIDTH / 2, ALIEN_ROW[1], ALIEN_HEIGHT,
						ALIEN_CRAB_WIDTH, p));
				lAlien.add(new AlienCrab( 20 + i * 50 - ALIEN_CRAB_WIDTH / 2, ALIEN_ROW[2], ALIEN_HEIGHT,
						ALIEN_CRAB_WIDTH, p));
				lAlien.add(new AlienOctopus( 20 + i * 50 - ALIEN_OCTOPUS_WIDTH / 2, ALIEN_ROW[3],
						ALIEN_HEIGHT, ALIEN_OCTOPUS_WIDTH, p));
				lAlien.add(new AlienOctopus( 20 + i * 50 - ALIEN_OCTOPUS_WIDTH / 2, ALIEN_ROW[4],
						ALIEN_HEIGHT, ALIEN_OCTOPUS_WIDTH, p));
			}
		}
		lGameObject.addAll(lAlien);
	}

	public PlayerShip getPlayer() {
		return player;
	}

	public void setPlayer(PlayerShip player) {
		this.player = player;
	}
	
	public Shield getShield() {
		return shield;
	}

	public void setShield(Shield shield) {
		this.shield = shield;
	}

	private void updateLives() {
		if (alienShoot != null && alienShoot.collidesWith(player) && !alienShoot.hasCollided) {
			alienShoot.setLives(alienShoot.getLives() - 1);
			player.setLives(player.getLives() - 1);
			alienShoot.hasCollided = true;
		}
		if (alienShoot != null && playerShoot != null && alienShoot.collidesWith(playerShoot)
				&& !alienShoot.hasCollided) {
			alienShoot.setLives(alienShoot.getLives() - 1);
			playerShoot.setLives(playerShoot.getLives() - 1);
			alienShoot.hasCollided = true;
			playerShoot.hasCollided = true;
			playerShoot = null;
		}
	
		for(Shield shield : lShield) {
			if (alienShoot != null && alienShoot.collidesWith(shield) && !alienShoot.hasCollided) {
				alienShoot.setLives(alienShoot.getLives() - 1);
				shield.setLives(shield.getLives() - 1);
				alienShoot.hasCollided = true;
			}
			if (playerShoot != null && playerShoot.collidesWith(shield) && !playerShoot.hasCollided) {
				playerShoot.setLives(playerShoot.getLives() - 1);
				shield.setLives(shield.getLives() - 1);
				playerShoot.hasCollided = true;
				playerShoot = null;
			}
		}
		for (Alien alien : lAlien) {
			if (playerShoot != null && playerShoot.collidesWith(alien) && !playerShoot.hasCollided) {
				playerShoot.setLives(playerShoot.getLives() - 1);
				alien.setLives(alien.getLives() - 1);
				playerShoot.hasCollided = true;
				playerShoot = null;
				if(alien instanceof AlienShip) {
					((AlienShip) alien).setKilled(true);
				}
			}
		}
	}

	public void update(long millis) {
		if(p.getTimeCounter() > 0 &&!(alienShipExists()) && p.getTimeCounter() % elapsedTimeToAlienShipCreation == 0  ) {
			AlienShip alienShip = new AlienShip( 630, 90, 7*3, 16*3, p);
			lAlien.add(alienShip);
			lGameObject.add(alienShip);
		}
		List<GameObject> deadObjects = new ArrayList<GameObject>();
		List<Alien> aliensRemove = new ArrayList<Alien>();
		updateLives();
		Iterator<GameObject> iter = lGameObject.iterator();
		while(iter.hasNext()) {
			GameObject go = iter.next();
			go.update(millis);
			if (go.isDead() && !(go instanceof PlayerShip)) {
				deadObjects.add(go);
				if (go instanceof Alien) {
					if(!(go instanceof AlienShip) || ((AlienShip)go).isKilled()) {
						player.setPoints(player.getPoints() + ((Alien) go).getPoints());
					}
					aliensRemove.add((Alien)go);
				}
				if (go instanceof Shield) {
					lShield.remove(go);
				}
			}
		}
		if(!lAlien.isEmpty() && !aliensRemove.isEmpty()) {
			lAlien.removeAll(aliensRemove);
		}
		alienSpeed = -(2f / 11f) * lAlien.size() + maxSpeed;
		Alien.setSpeed(alienSpeed);

		changeAlienDirection();
		shootAlien();
		if(alienShoot != null) {
			if (alienShoot.collidesDownBorder()) {
				alienShoot = null;
				lGameObject.remove(alienShoot);
			}
		}
		if (playerShoot != null) {
			if (playerShoot.collidesTopBorder()) {
				playerShoot = null;
				if(!lGameObject.isEmpty()) {
					lGameObject.remove(playerShoot);
				}
			}
		}
		if(!lGameObject.isEmpty() && !deadObjects.isEmpty()) {
			lGameObject.removeAll(deadObjects);
		}
		if(lAlien.isEmpty()) {
			p.shouldRestart = true;
		}
		if(player.isDead()) {
			p.getGame().setScore(player.getPoints());
			try {
				DatabaseController.getInstance().insertRecord(p.getGame());
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
	}

	public void changeAlienDirection() {
		Alien.setChangeDirection(false);

		for (Alien alien : lAlien) {
			if (alien.collidesBorder()) {
				if(!(alien instanceof AlienShip)) {
					Alien.setChangeDirection(true);
					break;
				}else {
					alien.setLives(0);
				}

			}
		}
		if (Alien.isChangeDirection()) {
			Alien.setDirection(Alien.getDirection()*-1);
			for (Alien alien : lAlien) {
				if(!(alien instanceof AlienShip)) {
					alien.setY(alien.getY() + 10);
				}
			}
		}
	}

	public void draw(Graphics2D g2) {
		List<GameObject>l2GameObject = new ArrayList<>(lGameObject);
		for (GameObject go : l2GameObject) {
			if(go != null) {
				go.draw(g2);
			}
		}
	}

	public void shootAlien() {
		if (alienShoot == null || alienShoot.isDead()) {
			if(!(lAlien.isEmpty())) {
				Alien alien = lAlien.get(random.nextInt(lAlien.size()));
				if(!(alien instanceof AlienShip)) {
					alienShoot = new AlienShoot(alien.getX() + alien.getWidth() / 2, alien.getY() + alien.getHeight(), 7 * 3,
							3 * 3, p);
					lGameObject.add(alienShoot);
				}
			}
		}
	}

	public void shootPlayer() {
		if (playerShoot == null) {
			playerShoot = new PlayerShoot(player.getX() + player.getWidth() / 2 - (2 * 3) / 2, player.getY(), 6 * 3,
					1 * 3, p);
			lGameObject.add(playerShoot);
		}
	}

	public String[][] loadLevel(String file) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			String[][] lEnemies = (String[][]) ois.readObject();
			return lEnemies;
		} catch (IOException e) {
			System.out.println("Error al leer el fichero");
		} catch (ClassNotFoundException e) {
			System.out.println("Error en tipo de dato");
		}
		return new String[10][12];
	}

	public boolean alienShipExists () {
		for (Alien alien:lAlien) {
			if (alien instanceof AlienShip) {
				return true;
			}
		}
		return false;

	}
	
	public void createShield(int posX) {
		for(int i = 0; i < 4; i++) {
			if(i==0) {
				lShield.add(new Shield(posX + i * 18, 490, 6*3, 6*3, p, ShieldType.UPLEFT));
			}else if(i==3) {
				lShield.add(new Shield(posX + i * 18, 490, 6*3, 6*3, p, ShieldType.UPRIGHT));
			}else {
				lShield.add(new Shield(posX + i * 18, 490, 6*3, 6*3, p, ShieldType.NORMAL));
			}

			if(i==1) {
				lShield.add(new Shield(posX + i * 18, 490+18, 6*3, 6*3, p, ShieldType.DOWNLEFT));
			}else if(i==2) {
				lShield.add(new Shield(posX + i * 18, 490+18, 6*3, 6*3, p, ShieldType.DOWNRIGHT));
			}else {
				lShield.add(new Shield(posX + i * 18, 490+18, 6*3, 6*3, p, ShieldType.NORMAL));
			}
			if(i==0 || i==3) {
				lShield.add(new Shield(posX + i * 18, 490+18*2, 6*3, 6*3, p, ShieldType.NORMAL));

			}

		}lGameObject.addAll(lShield);
	}
}
