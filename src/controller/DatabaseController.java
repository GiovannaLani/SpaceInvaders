package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import domain.Game;
import domain.Player;

public class DatabaseController {
	private static DatabaseController instance = new DatabaseController();

	private DatabaseController() {
		initializeDB();
	}

	private void initializeDB() {
		//id,username,password,country,game_date, game_duration, game_score, game_level
		String sql = "CREATE TABLE IF NOT EXISTS game (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, password TEXT NOT NULL, country TEXT NOT NULL, date TEXT NOT NULL, game_duration REAL, game_score INTEGER, game_level INTEGER);";
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();){
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Connection connect() {
		String url = "jdbc:sqlite:" + SettingsController.DATABASE_NAME;
		Connection conn = null;
		try {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				System.out.println("No se ha podido cargar el driver de la base de datos");
			}
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static DatabaseController getInstance() {
		return instance;
	}

	public void insertRecord(Game game) throws DBException {
		String sql = "INSERT INTO game(username, password, country, date, game_duration, game_score, game_level) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try(Connection conn = this.connect(); 
				PreparedStatement prepstmt = conn.prepareStatement(sql);){
			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			prepstmt.setString(1, game.getPlayer().getName());
			prepstmt.setString(2, game.getPlayer().getPassword());
			prepstmt.setString(3, game.getPlayer().getCountry());
			prepstmt.setString(4, LocalDateTime.now().format(date));
			prepstmt.setDouble(5, 0);
			prepstmt.setInt(6, game.getScore());
			prepstmt.setInt(7, game.getLevel());

			prepstmt.executeUpdate();
		}catch (SQLException e) {
			throw new DBException("No se pudo guardar el usuario en la BD", e);
		}
	}

	public boolean checkUserByUsernameAndPassword(String username, String password) throws DBException{
		String sql = "SELECT * FROM game WHERE username = ? AND password = ? ";
		try(Connection conn = this.connect(); 
				PreparedStatement prepstmt = conn.prepareStatement(sql);){
			prepstmt.setString(1, username);
			prepstmt.setString(2, password);

			try(ResultSet rs = prepstmt.executeQuery();){
				if (rs.next()) {
					return true;
				}
			}

		}catch (SQLException e) {
			throw new DBException("Fallo en la autenticacion del usuario: " + username , e);
		}
		return false;
	}

	public boolean userExists(String username) throws DBException {
		String sql = "SELECT * FROM game WHERE username = ?";
		try(Connection conn = this.connect(); 
				PreparedStatement prepstmt = conn.prepareStatement(sql);){
			prepstmt.setString(1, username);
			try(ResultSet rs = prepstmt.executeQuery();){
				if (rs.next()) {
					return true;
				}
			}
		}catch (SQLException e) {
			throw new DBException("Fallo en la autenticacion del usuario: " + username , e);
		}
		return false;
	}
	
	public List<Game> selectTop10() throws DBException{
		String sql = "SELECT * FROM game ORDER BY game_score DESC, game_level DESC, game_duration ASC LIMIT 10";
		List<Game> lGame = new ArrayList<Game>();
		try(Connection conn = this.connect(); 
				PreparedStatement prepstmt = conn.prepareStatement(sql);
				ResultSet rs = prepstmt.executeQuery();){
			while(rs.next()) {
				Game g = new Game(new Player(
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("country")),
						rs.getInt("game_score"),
						rs.getLong("game_duration"),
						LocalDate.parse(rs.getString("date")),
						rs.getInt("game_level"));

				//TODO add duration
				lGame.add(g);
			}
			
		}catch (SQLException e) {
			throw new DBException("Fallo en la obtencion del Top 10" , e);
		}
		return lGame;
	}
	
	public List<Game> selectTop10Country(String country) throws DBException{
		String sql = "SELECT * FROM game WHERE country = ? ORDER BY game_score DESC, game_level DESC, game_duration ASC LIMIT 10";
		List<Game> lGame = new ArrayList<Game>();
		try(Connection conn = this.connect(); 
				PreparedStatement prepstmt = conn.prepareStatement(sql);){
			prepstmt.setString(1, country);
			ResultSet rs = prepstmt.executeQuery();
			while(rs.next()) {
				Game g = new Game(new Player(
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("country")),
						rs.getInt("game_score"),
						rs.getLong("game_duration"),
						LocalDate.parse(rs.getString("date")),
						rs.getInt("game_level"));

				//TODO add duration
				lGame.add(g);
			}
			rs.close();
			
		}catch (SQLException e) {
			throw new DBException("Fallo en la obtencion del Top 10" , e);
		}
		return lGame;
	}
	
	public List<Player> loadPlayer() throws DBException {
		String sql = "SELECT username, MAX(password) AS password, MAX(country) AS country FROM game GROUP BY username";
		List<Player> lPlayers = new ArrayList<Player>();
		try(Connection conn = this.connect(); 
				PreparedStatement prepstmt = conn.prepareStatement(sql);
				ResultSet rs = prepstmt.executeQuery();){
			while(rs.next()) {
				Player p = new Player(
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("country"));
				lPlayers.add(p);
			}
		}catch (SQLException e) {
			throw new DBException("Fallo en la obtencion del Top 10" , e);
		}
		return lPlayers;
	}
	public List<Game> selectPlayerTop(String username) throws DBException{
		String sql = "SELECT * FROM game WHERE username = ?";
		List<Game> lGame = new ArrayList<Game>();
		try(Connection conn = this.connect(); 
				PreparedStatement prepstmt = conn.prepareStatement(sql);){
			prepstmt.setString(1, username);
			ResultSet rs = prepstmt.executeQuery();
			while(rs.next()) {
				Game g = new Game(new Player(
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("country")),
						rs.getInt("game_score"),
						rs.getLong("game_duration"),
						LocalDate.parse(rs.getString("date")),
						rs.getInt("game_level"));

				//TODO add duration
				lGame.add(g);
			}
			rs.close();
			
		}catch (SQLException e) {
			throw new DBException("Fallo en la obtencion del Top 10" , e);
		}
		return lGame;
	}
}
