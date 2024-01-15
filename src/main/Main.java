package main;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import db.SettingsController;
import gui.Menu.Menu;

public class Main {

	public static void main(String[] args) {
		SettingsController.loadSettingsFile();
		SwingUtilities.invokeLater(() -> {
			new Menu();
		});
	}

}
