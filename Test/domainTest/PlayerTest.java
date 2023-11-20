package domainTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domain.Player;

public class PlayerTest {
	private Player player, player2,player3;
	
	@Before
	public void setUp() {
		player = new Player("player1", "password1", "country1");
	}
	
	@Test
	public void testSetName() {
		player.setName("nombre");
		assertEquals("nombre", player.getName());
	}
	
	@Test
	public void testSetPassword() {
		player.setPassword("contrasena");
		assertEquals("contrasena", player.getPassword());
	}
	
	@Test
	public void testSetCountry() {
		player.setCountry("pais");
		assertEquals("pais", player.getCountry());
	}
	
	@Test
	public void testToString() {
		assertEquals("player1", player.toString());
	}
	
	@Test
	public void testEqualsSameObject() {
		player2 = new Player("player2", "password2", "country2");
		player3 = new Player("player1", "password1", "country1");
		
		assertFalse(player.equals(player2));
		assertTrue(player.equals(player3));
		assertTrue(player.equals(player));
		assertFalse(player.equals(null));
	}
	
}
