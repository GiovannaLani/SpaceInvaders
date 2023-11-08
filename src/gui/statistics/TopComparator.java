package gui.statistics;

import java.util.Comparator;

import domain.Game;

public class TopComparator implements Comparator<Game> {

	public TopComparator() {
		super();

	}

	@Override
	public int compare(Game o1, Game o2) {
			if(o1.getScore() == o2.getScore()) {
				return (int) o2.getTime() - (int) o1.getTime();
			}else {
				return o2.getScore() - o1.getScore();
			}
	}

}
