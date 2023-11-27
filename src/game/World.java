package game;

import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import controller.DBException;
import controller.DatabaseController;

public class World {
	private static final int[] ALIEN_ROW = { 100, 130, 160, 190, 220 };
	private static final int ALIEN_CRAB_WIDTH = 11 * 3;
	private static final int ALIEN_OCTOPUS_WIDTH = 12 * 3;
	private static final int ALIEN_SQUID_WIDTH = 8 * 3;
	private static final int ALIEN_HEIGHT = 8 * 3;

	private static Logger logger = Logger.getLogger(World.class.getName());

	private static Random random = new Random();

	private GamePanel p;
	private List<GameObject> lGameObject;
	private List<Alien> lAlien;
	private List<Shield> lShield;
	private float alienSpeed = 20;
	private float maxSpeed = 30;
	private int elapsedTimeToAlienShipCreation = 10; 

	private PlayerShoot playerShoot;
	private AlienShoot alienShoot;
	private PlayerShip player;
	private Shield shield;

	public World(GamePanel p, boolean customized, int points, int lives, List<Shield> lShield) {
		super();

		//logger
		try (FileInputStream fis = new FileInputStream("res/logger.properties")) {
			LogManager.getLogManager().readConfiguration(fis);
		} catch (IOException e) {
			logger.severe( "No se pudo leer el fichero de configuración del logger");
		}
		logger.info("Se ha creado un nuevo Mundo");


		this.p = p;
		lGameObject = new ArrayList<>();
		lAlien = new ArrayList<>();
		this.lShield = lShield;
		if(this.lShield == null) {
			this.lShield = new ArrayList<>();
			//creacion escudos
			for(int i=0; i<4;i++) {
				createShield(60 + 150*i);
			}
		}else {
			lGameObject.addAll(lShield);
		}
		alienShoot = null;
		playerShoot = null;
		player = new PlayerShip(320, 590, 8 * 4, 13 * 4, p);
		player.setPoints(points);
		player.setLives(lives);
		lGameObject.add(player);

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
	
	public List<Shield> getlShield() {
		return lShield;
	}

	public void setlShield(List<Shield> lShield) {
		this.lShield = lShield;
	}

	public void setShield(Shield shield) {
		this.shield = shield;
	}

	private void updateLives() {
		if (alienShoot != null && alienShoot.collidesWith(player) && !alienShoot.hasCollided) {
			alienShoot.setLives(alienShoot.getLives() - 1);
			player.setLives(player.getLives() - 1);
			alienShoot.hasCollided = true;
			logger.fine("La vida de un escudo ha disminuido por el disparo de un alien");
		}
		if (alienShoot != null && playerShoot != null && alienShoot.collidesWith(playerShoot)
				&& !alienShoot.hasCollided) {
			alienShoot.setLives(alienShoot.getLives() - 1);
			playerShoot.setLives(playerShoot.getLives() - 1);
			alienShoot.hasCollided = true;
			playerShoot.hasCollided = true;
			playerShoot = null;
			logger.fine("Los disparos han colisionado");
		}

		for(Shield shield : lShield) {
			if (alienShoot != null && alienShoot.collidesWith(shield) && !alienShoot.hasCollided) {
				alienShoot.setLives(alienShoot.getLives() - 1);
				shield.setLives(shield.getLives() - 1);
				alienShoot.hasCollided = true;
				logger.fine("La vida de un escudo ha disminuido por el disparo de un alien");
			}
			if (playerShoot != null && playerShoot.collidesWith(shield) && !playerShoot.hasCollided) {
				playerShoot.setLives(playerShoot.getLives() - 1);
				shield.setLives(shield.getLives() - 1);
				playerShoot.hasCollided = true;
				playerShoot = null;
				logger.fine("La vida de un escudo ha disminuido por el disparo del jugador");
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
					logger.fine("El jugador ha matado a un alien");
				}
			}
		}
	}

	public void update(long millis) {
		if(p.getTimeCounter() > 0 &&!(alienShipExists()) && p.getTimeCounter() % elapsedTimeToAlienShipCreation == 0  ) {
			int alienShipDirection = random.nextBoolean()?1:-1;
			int x = alienShipDirection == 1 ? 10 : 630;
			AlienShip alienShip = new AlienShip( x, 90, 7*3, 16*3, p, alienShipDirection);
			synchronized (lAlien) {
				lAlien.add(alienShip);
			}
			synchronized (lGameObject) {
				lGameObject.add(alienShip);
			}
		}
		List<GameObject> deadObjects = new ArrayList<GameObject>();
		List<Alien> aliensRemove = new ArrayList<Alien>();
		List<Shield> shieldRemove = new ArrayList<Shield>();
		updateLives();
		synchronized (lGameObject) {
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
						shieldRemove.add((Shield) go);
					}
				}
			}
		}
		if(!lAlien.isEmpty() && !aliensRemove.isEmpty()) {
			synchronized (lAlien) {
				lAlien.removeAll(aliensRemove);
			}
		}
		if(!lShield.isEmpty() && !shieldRemove.isEmpty()) {
			synchronized (lShield) {
				lShield.removeAll(shieldRemove);
			}
		}
		alienSpeed = -(2f / 11f) * lAlien.size() + maxSpeed;
		Alien.setSpeed(alienSpeed);

		changeAlienDirection();
		shootAlien();
		if(alienShoot != null) {
			if (alienShoot.collidesDownBorder()) {
				synchronized (lGameObject) {
		             if (!lGameObject.isEmpty()) {
		                lGameObject.remove(alienShoot);
		             }
		          }
				alienShoot = null;
			}
		}
		if (playerShoot != null) {
			if (playerShoot.collidesTopBorder()) {
				synchronized (lGameObject) {
		             if (!lGameObject.isEmpty()) {
		                lGameObject.remove(playerShoot);
		             }
		          }
				playerShoot = null;
			}
		}
		synchronized (lGameObject) {
		       if (!lGameObject.isEmpty() && !deadObjects.isEmpty()) {
		          lGameObject.removeAll(deadObjects);
		       }
		    }
		if(lAlien.isEmpty()) {
			p.shouldRestart = true;
		}
		if(isAlienInLimit()) {
			player.setLives(0);
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
					logger.fine("Disparo de un alien generado");
				}
			}
		}
	}

	public void shootPlayer() {
		if (playerShoot == null) {
			playerShoot = new PlayerShoot(player.getX() + player.getWidth() / 2 - (2 * 3) / 2, player.getY(), 6 * 3,
					1 * 3, p);
			lGameObject.add(playerShoot);
			logger.fine("Disparo del jugador generado");
		}
	}

	public String[][] loadLevel(String file) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			String[][] lEnemies = (String[][]) ois.readObject();
			logger.info("Fichero cargado correctamente");
			return lEnemies;
		} catch (IOException e) {
			logger.warning("Error al leer el fichero");
		} catch (ClassNotFoundException e) {
			logger.warning("Error en tipo de dato");
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
	
	public boolean isAlienInLimit() {
		for(Alien alien: lAlien) {
			if(alien.isCollidesDownLimit()) {
				return true;
			}
		}
		return false;
	}
}
