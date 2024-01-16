package db;

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
import game.Chronometer;

public class DatabaseController {
	private static DatabaseController instance = new DatabaseController();

	private DatabaseController() {
		try {
			initializeDB();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}

	public void initializeDB() throws DBException {
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS game (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, date TEXT NOT NULL, game_duration REAL, game_score INTEGER, game_level INTEGER);";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE IF NOT EXISTS player (username TEXT PRIMARY KEY, password TEXT NOT NULL, country TEXT NOT NULL);";
			stmt.executeUpdate(sql);
			if (!userExists("a")) {
                data();
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void data() {
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement();) {
			stmt.executeUpdate("INSERT INTO player VALUES ('a','a','España')");
			stmt.executeUpdate(
					"INSERT INTO game (username, date,game_duration, game_score, game_level)VALUES ('a','2023-01-01',10000,1000,1)");
			stmt.executeUpdate(
					"INSERT INTO game (username, date,game_duration, game_score, game_level)VALUES ('a','2023-01-02',30000,500,1)");
			stmt.executeUpdate(
					"INSERT INTO game (username, date,game_duration, game_score, game_level)VALUES ('a','2023-01-03',30000,1000,1)");
			stmt.executeUpdate("INSERT INTO player VALUES ('b','b','España')");
			stmt.executeUpdate(
					"INSERT INTO game (username, date,game_duration, game_score, game_level)VALUES ('b','2023-01-02',40000,2000,3)");
			stmt.executeUpdate(
					"INSERT INTO game (username, date,game_duration, game_score, game_level)VALUES ('b','2023-01-02',20000,500,1)");
			stmt.executeUpdate(
					"INSERT INTO game (username, date,game_duration, game_score, game_level)VALUES ('b','2023-01-03',30000,1300,2)");
		} catch (SQLException e) {
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
			e.printStackTrace();
		}
		return conn;
	}

	public static DatabaseController getInstance() {
		return instance;
	}

	public void insertRecord(Game game) throws DBException {
		String sql = "INSERT INTO game(username, date, game_duration, game_score, game_level) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = this.connect(); PreparedStatement prepstmt = conn.prepareStatement(sql);) {
			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			prepstmt.setString(1, game.getPlayer().getName());
			prepstmt.setString(2, LocalDateTime.now().format(date));
			prepstmt.setDouble(3, game.getTime().getMilliSeconds());
			prepstmt.setInt(4, game.getScore());
			prepstmt.setInt(5, game.getLevel());

			prepstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException("No se pudo guardar el usuario en la BD", e);
		}
	}

	public void insertPlayer(Player player) throws DBException {
		String sql = "INSERT INTO player VALUES (?, ?, ?)";
		try (Connection conn = this.connect(); PreparedStatement prepstmt = conn.prepareStatement(sql);) {
			prepstmt.setString(1, player.getName());
			prepstmt.setString(2, player.getPassword());
			prepstmt.setString(3, player.getCountry());

			prepstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException("No se pudo guardar el usuario en la BD", e);
		}
	}

	public void updatePlayer(Player player) throws DBException {
		String sql = "UPDATE player SET country = ? where username = ?";
		try (Connection conn = this.connect(); PreparedStatement prepstmt = conn.prepareStatement(sql);) {
			prepstmt.setString(1, player.getCountry());
			prepstmt.setString(2, player.getName());

			prepstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException("No se pudo guardar el usuario en la BD", e);
		}
	}

	public boolean checkUserByUsernameAndPassword(String username, String password) throws DBException {
		String sql = "SELECT * FROM player WHERE username = ? AND password = ? ";
		try (Connection conn = this.connect(); PreparedStatement prepstmt = conn.prepareStatement(sql);) {
			prepstmt.setString(1, username);
			prepstmt.setString(2, password);

			try (ResultSet rs = prepstmt.executeQuery();) {
				if (rs.next()) {
					return true;
				}
			}

		} catch (SQLException e) {
			throw new DBException("Fallo en la autenticacion del usuario: " + username, e);
		}
		return false;
	}

	public boolean userExists(String username) throws DBException {
		String sql = "SELECT * FROM player WHERE username = ?";
		try (Connection conn = this.connect(); PreparedStatement prepstmt = conn.prepareStatement(sql);) {
			prepstmt.setString(1, username);
			try (ResultSet rs = prepstmt.executeQuery();) {
				if (rs.next()) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw new DBException("Fallo en la autenticacion del usuario: " + username, e);
		}
		return false;
	}

	public List<Game> selectTop10() throws DBException {
		String sql = "SELECT * FROM game ORDER BY game_score DESC, game_level DESC, game_duration ASC LIMIT 10";
		List<Game> lGame = new ArrayList<Game>();
		try (Connection conn = this.connect();
				PreparedStatement prepstmt = conn.prepareStatement(sql);
				ResultSet rs = prepstmt.executeQuery();) {
			while (rs.next()) {
				try (Statement stmt2 = conn.createStatement();
						ResultSet rs2 = stmt2.executeQuery(
								"SELECT * FROM player WHERE username = '" + rs.getString("username") + "'")) {

					Game g = new Game(
							new Player(rs.getString("username"), rs2.getString("password"), rs2.getString("country")),
							rs.getInt("game_score"), new Chronometer(rs.getLong("game_duration")),
							LocalDate.parse(rs.getString("date")), rs.getInt("game_level"));

					lGame.add(g);
				}

			}

		} catch (SQLException e) {
			throw new DBException("Fallo en la obtencion del Top 10", e);
		}
		return lGame;
	}

	public List<Game> selectTop10Country(String country) throws DBException {
		String sql = "SELECT game.*,password,country FROM game,player WHERE country = ? AND game.username=player.username ORDER BY game_score DESC, game_level DESC, game_duration ASC LIMIT 10";
		List<Game> lGame = new ArrayList<Game>();
		try (Connection conn = this.connect(); PreparedStatement prepstmt = conn.prepareStatement(sql);) {
			prepstmt.setString(1, country);
			ResultSet rs = prepstmt.executeQuery();
			while (rs.next()) {
				try (Statement stmt2 = conn.createStatement();
						ResultSet rs2 = stmt2.executeQuery(
								"SELECT * FROM player WHERE username = '" + rs.getString("username") + "'")) {

					Game g = new Game(
							new Player(rs.getString("username"), rs2.getString("password"), rs2.getString("country")),
							rs.getInt("game_score"), new Chronometer(rs.getLong("game_duration")),
							LocalDate.parse(rs.getString("date")), rs.getInt("game_level"));

					lGame.add(g);
				}
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Fallo en la obtencion del Top 10", e);
		}
		return lGame;
	}

	public List<Player> loadPlayer() throws DBException {
		String sql = "SELECT * FROM player";
		List<Player> lPlayers = new ArrayList<Player>();
		try (Connection conn = this.connect();
				PreparedStatement prepstmt = conn.prepareStatement(sql);
				ResultSet rs = prepstmt.executeQuery();) {
			while (rs.next()) {
				Player p = new Player(rs.getString("username"), rs.getString("password"), rs.getString("country"));
				lPlayers.add(p);
			}
		} catch (SQLException e) {
			throw new DBException("Fallo en la obtencion del Top 10", e);
		}
		return lPlayers;
	}

	public List<Game> selectPlayerTop(String username) throws DBException {
		String sql = "SELECT * FROM game WHERE username = ?";
		List<Game> lGame = new ArrayList<Game>();
		try (Connection conn = this.connect(); PreparedStatement prepstmt = conn.prepareStatement(sql);) {
			prepstmt.setString(1, username);
			ResultSet rs = prepstmt.executeQuery();
			while (rs.next()) {
				try (Statement stmt2 = conn.createStatement();
						ResultSet rs2 = stmt2.executeQuery(
								"SELECT * FROM player WHERE username = '" + rs.getString("username") + "'")) {
					Game g = new Game(
							new Player(rs.getString("username"), rs2.getString("password"), rs2.getString("country")),
							rs.getInt("game_score"), new Chronometer(rs.getLong("game_duration")),
							LocalDate.parse(rs.getString("date")), rs.getInt("game_level"));

					lGame.add(g);
				}

			}
			rs.close();

		} catch (SQLException e) {
			throw new DBException("Fallo en la obtencion del Top 10", e);
		}
		return lGame;
	}
}
