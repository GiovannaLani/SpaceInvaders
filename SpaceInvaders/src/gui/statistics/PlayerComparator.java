package gui.statistics;

import java.util.Comparator;

import domain.Game;

public class PlayerComparator implements Comparator<Game> {

	private TableTitle comparator;

	public PlayerComparator(TableTitle comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public int compare(Game o1, Game o2) {
		switch (comparator) {
		case PUNTUACION:
			return o2.getScore() - o1.getScore();
		case FECHA:
			return o2.getDate().compareTo(o1.getDate());
		case TIEMPO:
			return (int) (o1.getTime() - o2.getTime());
		case NIVEL:
			return o2.getLevel() - o1.getLevel();
		default:
			return 0;
		}
	}

}
