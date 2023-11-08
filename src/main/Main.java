package main;
import javax.swing.SwingUtilities;

import gui.Menu.Menu;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Menu();
		});
	}

}
