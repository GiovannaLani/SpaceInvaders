package dbTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.DBException;
import db.DatabaseController;
import db.SettingsController;
import domain.Game;
import domain.Player;
import game.Chronometer;

public class DatabaseControllerTest {
	
	private DatabaseController dbController;
	private Connection conn;
	String usuario = "a";
	
	@Before
	public void setUp() throws SQLException {

		conn = DriverManager.getConnection("jdbc:sqlite:" + SettingsController.DATABASE_NAME);
		dbController = DatabaseController.getInstance();
		dbController.initializeDB();
		DatabaseController.getInstance();
	}
	
	@Test
	public void testInsertRecord() throws DBException, SQLException {
		Player player = new Player("testName", "testPassword", "Alemania");
		Chronometer chronometer = new Chronometer();
		Game game = new Game(player, 1000, chronometer, LocalDate.now(), 1);
		
		dbController.insertRecord(game);
		
		String sql = "SELECT * FROM game WHERE username = ?";
		try(PreparedStatement prepstmt = conn.prepareStatement(sql)){
			prepstmt.setString(1, "testName");
			ResultSet rs = prepstmt.executeQuery();
			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			assertTrue(rs.next());
			assertEquals("testName", rs.getString("username"));
			assertEquals("testPassword", rs.getString("password"));
			assertEquals("Alemania", rs.getString("country"));
			assertEquals("testName", rs.getString("username"));
			assertEquals(LocalDate.now().format(date), rs.getString("date"));
			chronometer.setMilliSeconds((long) rs.getDouble("game_duration"));
			assertEquals(chronometer.getMilliSeconds(), rs.getDouble("game_duration"),0.01 );
			assertEquals(1000, rs.getInt("game_score"));
			assertEquals(1, rs.getInt("game_level"));
		}
	}
	@Test
	public void testCheckByUsernameAndPassword() throws DBException, SQLException {
		//usuario de prueba
		String sql = "INSERT INTO game(username, password, country, date, game_duration, game_score, game_level) VALUES (?, ?, ?, ?, ?, ?, ?)"; 
		try(PreparedStatement prepstmt = conn.prepareStatement(sql)){
			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			prepstmt.setString(1, "testName");
			prepstmt.setString(2, "testPassword");
			prepstmt.setString(3, "Alemania");
			prepstmt.setString(4, LocalDateTime.now().format(date));
			prepstmt.setDouble(5, 10000000.01);
			prepstmt.setInt(6, 1000);
			prepstmt.setInt(7, 1);	
			
			prepstmt.executeUpdate();
		}
		assertTrue(dbController.checkUserByUsernameAndPassword("testName", "testPassword"));
		assertFalse(dbController.checkUserByUsernameAndPassword("qwerty", "asdf"));
	}

	@Test
	public void testUserExists() throws SQLException, DBException {
		String sql = "INSERT INTO game(username, password, country, date, game_duration, game_score, game_level) VALUES (?, ?, ?, ?, ?, ?, ?)"; 
		try(PreparedStatement prepstmt = conn.prepareStatement(sql)){
			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			prepstmt.setString(1, "testName");
			prepstmt.setString(2, "testPassword");
			prepstmt.setString(3, "Alemania");
			prepstmt.setString(4, LocalDateTime.now().format(date));
			prepstmt.setDouble(5, 10000000.01);
			prepstmt.setInt(6, 1000);
			prepstmt.setInt(7, 1);

			prepstmt.executeUpdate();
		}
		assertTrue(dbController.userExists("testName"));
		assertFalse(dbController.userExists("qwerty"));
	}
	

	public void top10Data(int count) throws SQLException, DBException {
		String sql = "INSERT INTO game(username, password, country, date, game_duration, game_score, game_level) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement prepstmt = conn.prepareStatement(sql)){
			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			for(int i = 0;i < count;i++) {
				prepstmt.setString(1, "user" + i);
                prepstmt.setString(2, "pass" + i);
                prepstmt.setString(3, "country" + i);
                prepstmt.setString(4, LocalDate.now().format(date));
                prepstmt.setDouble(5, i * 1000);
                prepstmt.setInt(6, i * 100);
                prepstmt.setInt(7, i);
                prepstmt.executeUpdate();
			}
		}
	}
	
	@Test
	public void testSelectTop10() throws SQLException, DBException {
		top10Data(12);
		List<Game> top10 = dbController.selectTop10();
		assertEquals(10, top10.size());
	}
	
	public void top10CountryData(int count, String country) throws SQLException {
		String sql = "INSERT INTO game(username, password, country, date, game_duration, game_score, game_level) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement prepstmt = conn.prepareStatement(sql)){
			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			for(int i = 0;i < count;i++) {
				prepstmt.setString(1, "user" + i);
                prepstmt.setString(2, "pass" + i);
                prepstmt.setString(3, country);
                prepstmt.setString(4, LocalDate.now().format(date));
                prepstmt.setDouble(5, i * 1000);
                prepstmt.setInt(6, i * 100);
                prepstmt.setInt(7, i);
                prepstmt.executeUpdate();
			}
		}
	}
	
	@Test
	public void testSelectTop10Country() throws SQLException, DBException {
		top10CountryData(13, "Alemania");
		List<Game> top10 = dbController.selectTop10Country("Alemania");
		assertEquals(10, top10.size());
	}
	
	public void loadPLayerData(int count) throws SQLException {
		String sql = "INSERT INTO game(username, password, country, date, game_duration, game_score, game_level) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement prepstmt = conn.prepareStatement(sql)){
			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			for(int i = 0;i < count;i++) {
				prepstmt.setString(1, "user" + i);
                prepstmt.setString(2, "pass" + i);
                prepstmt.setString(3, "Alemania");
                prepstmt.setString(4, LocalDate.now().format(date));
                prepstmt.setDouble(5, i * 1000);
                prepstmt.setInt(6, i * 100);
                prepstmt.setInt(7, i);
                prepstmt.executeUpdate();
			}
		}
	}
	
	@Test
	public void testLoadPlayerWithData() throws SQLException, DBException {
		loadPLayerData(14);
		List<Player> players = dbController.loadPlayer();
		for (Player player : players) {
            assertNotNull(player.getName());
            assertNotNull(player.getPassword());
            assertNotNull(player.getCountry());
        }
	}
	
	public void selectPlayerTopData(int count, String username) throws SQLException {
		String sql = "INSERT INTO game(username, password, country, date, game_duration, game_score, game_level) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement prepstmt = conn.prepareStatement(sql)){
			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			for(int i = 0;i < count;i++) {
				prepstmt.setString(1, username);
                prepstmt.setString(2, "pass" + i);
                prepstmt.setString(3, "country" + i);
                prepstmt.setString(4, LocalDate.now().format(date));
                prepstmt.setDouble(5, i * 1000);
                prepstmt.setInt(6, i * 100);
                prepstmt.setInt(7, i);
                prepstmt.executeUpdate();
			}
		}
	}
	
	
	@Test
	public void testSelectPlayerTop() throws SQLException, DBException {
		selectPlayerTopData(11, usuario);
		List<Game> playerTop = dbController.selectPlayerTop(usuario);
		
		System.out.println(playerTop);
		assertEquals(11, playerTop.size());

	}

	@After
    public void tearDown() throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DROP TABLE game;")) {
            stmt.execute();
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }
	
	
}
