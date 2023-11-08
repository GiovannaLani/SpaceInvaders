package domain;

import java.time.LocalDate;

public class Game {
	private Player player;
	private int score;
	private long time;
	private LocalDate date;
	private int level;

	public Game(Player player, int score, long time, LocalDate date, int level) {
		super();
		this.player = player;
		this.score = score;
		this.time = time;
		this.date = date;
		this.level = level;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Game [player=" + player + ", score=" + score + ", time=" + time + ", date=" + date + ", level=" + level
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Game) {
			Game g = (Game) obj;
			return this.player.equals(g.getPlayer()) && this.score == g.getScore() && this.time == g.getTime()
					&& this.date.equals(g.getDate()) && this.level == g.getLevel();
		}
		return false;
	}
}
