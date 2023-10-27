package gui.statistics;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class StatisticsWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new StatisticsWindow();
		});
	}

	public StatisticsWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 480);
		setTitle("Estadísticas");
		setLocationRelativeTo(null);

		JTabbedPane jTabbedPane = new JTabbedPane();
		jTabbedPane.addTab("TOP 10", new Top10Panel());
		jTabbedPane.addTab("Jugador", new PlayerPanel());
		jTabbedPane.addTab("País", new TopCountryPanel());
		add(jTabbedPane);

		setVisible(true);
	}
}
