package gameTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import game.AlienShip;
import game.GamePanel;

public class AlienShipTest {
	private AlienShip alienShip;
	private GamePanel panel;

	@Before
	public void setUp() {
		alienShip = new AlienShip(30, 30, 10, 10, panel, -1);
	}

	@Test
	public void testCollidesBorder() {
		alienShip.setX(-10);
		assertTrue(alienShip.collidesBorder());
		alienShip.setX(1000);
		assertFalse(alienShip.collidesBorder());
		alienShip.setAlienShipDirection(1);
		assertTrue(alienShip.collidesBorder());
		alienShip.setX(0);
		assertFalse(alienShip.collidesBorder());
	}

	@Test
	public void testSetKilled() {
		alienShip.setKilled(true);
		assertTrue(alienShip.isKilled());
	}

	@Test
	public void testSetAlienShipSpeed() {
		alienShip.setAlienShipSpeed(100);
		assertEquals(100, alienShip.getAlienShipSpeed(), 0.01);
	}

	@Test
	public void testSetAlienShipDirection() {
		alienShip.setAlienShipDirection(1);
		assertEquals(1, alienShip.getAlienShipDirection());
	}

	@Test
	public void testUpdate() {
		alienShip.update(100);
		assertEquals(200, alienShip.getAlienShipSpeed(), 0.01);
		alienShip.setLives(0);
		alienShip.update(100);
		assertEquals(0, alienShip.getAlienShipSpeed(), 0.01);
	}

}