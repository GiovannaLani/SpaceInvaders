package gui.statistics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import gui.Menu.Menu;
import gui.customComponents.FontLoader;
import gui.customComponents.LabelPixel;

public class StatisticsWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	public StatisticsWindow(Menu menu) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(640, 480);
		setTitle("Estadísticas");
		setLocationRelativeTo(null);
		
		
		JTabbedPane jTabbedPane = new JTabbedPane();
		

		jTabbedPane.setBackground(Color.BLACK);
		jTabbedPane.setForeground(Color.WHITE);
		Font font = new FontLoader().getCustomFont(8);
		jTabbedPane.setFont(font);
		
		
		jTabbedPane.addTab("TOP 10", new Top10Panel());
		jTabbedPane.addTab("Jugador", new PlayerPanel());
		jTabbedPane.addTab("País", new TopCountryPanel());
		
		JLabel label = new LabelPixel("TOP 10", 8);
		label.setPreferredSize(new Dimension(50, 15));
		jTabbedPane.setTabComponentAt(0, label);
		
		
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
