package main;
import javax.swing.SwingUtilities;

import controller.SettingsController;
import gui.Menu.Menu;

public class Main {

	public static void main(String[] args) {
		SettingsController.loadSettingsFile();
		SwingUtilities.invokeLater(() -> {
			new Menu();
		});
	}

}
