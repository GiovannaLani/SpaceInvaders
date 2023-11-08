package gui.Menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import gui.customComponents.ButtonPixel;
import gui.customComponents.LabelPixel;
import gui.statistics.StatisticsWindow;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;

	public Menu() {
		// Conficuración ventana
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("Menú Principal");
		setBackground(Color.BLACK);

		// Border
		Border emptyBorder = BorderFactory.createEmptyBorder(20, 30, 20, 30);

		// Panel central
		JPanel pCenter = new JPanel();
		pCenter.setBackground(Color.BLACK);
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
		add(pCenter);

		// Título
		LabelPixel lTitle = new LabelPixel("SPACE INVADERS", 35);
		lTitle.setBorder(emptyBorder);
		lTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Botones
		ButtonPixel bPlay = new ButtonPixel("Jugar", 15);
		ButtonPixel bCustomLevel = new ButtonPixel("Nivel personalizado", 15);
		ButtonPixel bStatistics = new ButtonPixel("Estadísticas", 15);
		ButtonPixel bControl = new ButtonPixel("Controles", 15);
		ButtonPixel bExit = new ButtonPixel("Salir", 15);

		ButtonPixel[] lButtons = { bPlay, bCustomLevel, bStatistics, bControl, bExit };

		pCenter.add(Box.createVerticalGlue());
		pCenter.add(lTitle);
		// Añade los botones alineados y con espacio entre ellos
		for (ButtonPixel button : lButtons) {
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			pCenter.add(button);
			pCenter.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		pCenter.add(Box.createVerticalGlue());

		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		// Listeners
		bPlay.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				new InfoWindow(Menu.this);
			});
			Menu.this.setVisible(false);
		});
		bCustomLevel.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				new CustomLevelWindow(Menu.this);
			});
			Menu.this.setVisible(false);
		});
		bStatistics.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				new StatisticsWindow(Menu.this);
			});
			Menu.this.setVisible(false);
		});
		bControl.addActionListener(e -> {
			new ControlWindow(Menu.this);
		});

		bExit.addActionListener(e -> {
			int input = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir?", "Salir",
					JOptionPane.YES_NO_OPTION);
			if (input == JOptionPane.YES_OPTION) {
				this.dispose();
			}
		});

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				int input = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir?", "Salir",
						JOptionPane.YES_NO_OPTION);
				if (input == JOptionPane.YES_OPTION) {
					Menu.this.dispose();
				}
			}

		});
	}
}
