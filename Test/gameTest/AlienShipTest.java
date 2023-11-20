package gameTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domain.Game;
import domain.Player;
import game.AlienShip;
import game.GamePanel;
import game.SpaceInvaders;

public class AlienShipTest {
	
	private AlienShip alienShip;
	private GamePanel panel;
	private Player player;
	private Game game;
	private gui.Menu.Menu menu;
	private SpaceInvaders window;
	
	@Before
	public void setUp() {
		alienShip = new AlienShip(30, 30, 10, 10, panel);
		panel = new GamePanel(player, game, false, menu, window);
	}
	
	@Test
	public void testCollidesBorder() {
		alienShip.setX(-alienShip.getWidth());;
		assertTrue(alienShip.collidesBorder());
		alienShip.setX(alienShip.getWidth()/2);;
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
		assertEquals(100, alienShip.getAlienShipSpeed(),0.01);
	}
	
}
