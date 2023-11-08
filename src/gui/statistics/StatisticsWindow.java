package gui.statistics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import gui.Menu.Menu;

public class StatisticsWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	public StatisticsWindow(Menu menu) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(640, 480);
		setTitle("Estadísticas");
		setLocationRelativeTo(null);

		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.addTab("TOP 10", new Top10Panel());
		jTabbedPane.addTab("Jugador", new PlayerPanel());
		jTabbedPane.addTab("País", new TopCountryPanel());
		add(jTabbedPane);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				menu.setVisible(true);
			}
		});

		setVisible(true);
	}
}
