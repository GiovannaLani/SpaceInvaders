package game;

import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	private float alienSpeed = 40;
	private float maxSpeed = 50;

	private PlayerShoot playerShoot;
	private AlienShoot alienShoot;
	private PlayerShip player;

	public World(GamePanel p, boolean personalizado) {
		super();
		this.p = p;
		lGameObject = new ArrayList<>();
		lAlien = new ArrayList<>();
		alienShoot = null;
		playerShoot = null;
		player = new PlayerShip(320, 590, 8 * 4, 13 * 4, p);
		lGameObject.add(player);
		String[][] lEnemies = loadLevel("res/data/level.dat");
		if (personalizado && lEnemies != null) {
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

	private void updateLives(long milis) {
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
		for (Alien alien : lAlien) {
			if (playerShoot != null && playerShoot.collidesWith(alien) && !playerShoot.hasCollided) {
				playerShoot.setLives(playerShoot.getLives() - 1);
				alien.setLives(alien.getLives() - 1);
				playerShoot.hasCollided = true;
				playerShoot = null;
			}
		}
	}

	public void update(long milis) {
		List<GameObject> deadObjects = new ArrayList<GameObject>();
		updateLives(milis);
		for (GameObject go : lGameObject) {
			go.update(milis);
			if (go.isDead() && !(go instanceof PlayerShip)) {
				deadObjects.add(go);
				if (go instanceof Alien) {
					player.setPoints(player.getPoints() + ((Alien) go).getPoints());
					lAlien.remove(go);
				}
			}
		}
		alienSpeed = -(2f / 11f) * lAlien.size() + maxSpeed;
		Alien.setSpeed(alienSpeed);

		changeAlienDirection();
		shootAlien();
		if (alienShoot.collidesDownBorder()) {
			alienShoot = null;
			lGameObject.remove(alienShoot);
		}
		if (playerShoot != null) {
			if (playerShoot.collidesTopBorder()) {
				playerShoot = null;
				lGameObject.remove(playerShoot);
			}
		}
		lGameObject.removeAll(deadObjects);

	}

	public void changeAlienDirection() {
		Alien.setChangeDirection(false);

		for (Alien alien : lAlien) {
			if (alien.collidesBorder()) {
				Alien.setChangeDirection(true);
				break;
			}
		}
		if (Alien.isChangeDirection()) {
			Alien.direction *= -1;
			for (Alien alien : lAlien) {
				alien.setY(alien.getY() + 10);
			}
		}
	}

	public void draw(Graphics2D g2) {
		for (GameObject go : lGameObject) {
			// if(!go.isDead() || go instanceof PlayerShip) {
			go.draw(g2);
			// }
		}
	}

	public void shootAlien() {
		if (alienShoot == null || alienShoot.isDead()) {
			Alien alien = lAlien.get(random.nextInt(lAlien.size()));
			alienShoot = new AlienShoot(alien.getX() + alien.getWidth() / 2, alien.getY() + alien.getHeight(), 7 * 3,
					3 * 3, p);
			lGameObject.add(alienShoot);
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
}
