package gameTest;

import static org.junit.Assert.*;

import game.GamePanel;

import org.junit.Before;
import org.junit.Test;

import game.Alien;
import game.AlienSquid;

public class AlienTest {
	private Alien alien;
	private GamePanel mockPanel;

	@Before
	public void setUp() {
		mockPanel = new GamePanel(null, null, null, null, null);
		alien = new AlienSquid(0, 0, 50, 50, mockPanel);
	}

	@Test
	public void testInitialConditions() {
		assertEquals("Initial speed should be 20", 20, Alien.getSpeed(), 0.01);
		Alien.setChangeDirection(true);
		assertTrue("Initial changeDirection should be true", Alien.isChangeDirection());
		assertEquals("Initial points should be 30", 30, alien.getPoints());
	}

	@Test
	public void testSetPoints() {
		alien.setPoints(150);
		assertEquals("Points should be set to 150", 150, alien.getPoints());
	}

	@Test
	public void testSetSpeed() {
		Alien.setSpeed(150);
		assertEquals("Points should be set to 150", 150.0, Alien.getSpeed(),0.01);
	}

	@Test
	public void testSetDirection() {
		Alien.setDirection(1);
		assertEquals("Direction should be set to 1", 1, Alien.getDirection(),0.01);
	}

	@Test
	public void testMovement() {
		double initialX = alien.getX();
		alien.update(1000); // Suponiendo que el millis es el tiempo transcurrido en milisegundos
		assertEquals("Alien should have moved", initialX + Alien.getSpeed(), alien.getX(), 0.01);
	}

	@Test
	public void testCollisionBorder() {
		alien.setX(mockPanel.getWidth() - alien.getWidth());
		Alien.setChangeDirection(false);
		Alien.setDirection(1);
		assertTrue("Alien should collide with right border", alien.collidesBorder());
		Alien.setDirection(-1);
		Alien.setChangeDirection(false);
		assertFalse("Alien shouldn't collide with right border", alien.collidesBorder());
		alien.setX(0);
		Alien.setChangeDirection(true);
		Alien.setDirection(-1);
		assertTrue("Alien should collide with left border", alien.collidesBorder());
		Alien.setChangeDirection(false);
		Alien.setDirection(1);
		assertFalse("Alien shouldn't collide with left border", alien.collidesBorder());
		alien.setX(100);
		Alien.setChangeDirection(false);
		assertFalse("Alien shouldn't collide with left border", alien.collidesBorder());
	
	}

	@Test
	public void testIsDead() {
		alien.setLives(0);
		assertFalse("Alien should not be dead immediately after lives reach 0", alien.isDead());
		alien.update(Alien.getTimeMaxDying() + 1);//deberías crear una función para obtener el timeMaxDying si lo quieres testear
		assertTrue("Alien should be dead after timeMaxDying has passed", alien.isDead());
		alien.setLives(1);
		assertFalse("Alien should not be dead because it has 1 live", alien.isDead());
	}

	@Test
	public void testCollidesBorder() {
		Alien.setDirection(1);
		alien.setX(1000);
		assertTrue(alien.collidesBorder());
		alien.setX(100);
		alien.setY(1000);
		assertFalse(alien.collidesBorder());
	}

}