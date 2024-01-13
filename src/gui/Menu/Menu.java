package gui.Menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

import gui.customComponents.ButtonPixel;
import gui.customComponents.FontLoader;
import gui.customComponents.LabelPixel;
import gui.statistics.StatisticsWindow;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(Menu.class.getName());
	
	public Menu() {
		
		// Logger
		try (FileInputStream fis = new FileInputStream("res/logger.properties")) {
			LogManager.getLogManager().readConfiguration(fis);
		} catch (IOException e) {
			logger.severe( "No se pudo leer el fichero de configuración del logger");
		}

		// Configuración ventana
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

		// Visual de todos los JOptionPane
		UIManager.put("Panel.background", Color.BLACK);
		UIManager.put("OptionPane.background", Color.BLACK);
		UIManager.put("Button.background", Color.BLACK);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", new FontLoader().getCustomFont(8));
		UIManager.put("Button.border", BorderFactory.createLineBorder(Color.WHITE));
		Border emptyBorder2 = BorderFactory.createEmptyBorder(8, 8, 8, 8);
		Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		UIManager.put("Button.border", BorderFactory.createCompoundBorder(lineBorder, emptyBorder2));
				
		
		// Listeners
		bPlay.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				logger.info("Se ha visualizado la ventana InfoWindow");
				new InfoWindow(Menu.this);
			});
			Menu.this.setVisible(false);
		});
		bCustomLevel.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				logger.info("Se ha visualizado la ventana CustomLevelWindow");
				new CustomLevelWindow(Menu.this);
			});
			Menu.this.setVisible(false);
		});
		bStatistics.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				logger.info("Se ha visualizado la ventana StatisticsWindow");
				new StatisticsWindow(Menu.this);
			});
			Menu.this.setVisible(false);
		});
		bControl.addActionListener(e -> {
			logger.info("Se ha visualizado la ventana ControlWindow");
			new ControlWindow(Menu.this);
		});

		bExit.addActionListener(e -> {
			JLabel message = new LabelPixel("¿Seguro que quieres salir?",8);
			int input = JOptionPane.showConfirmDialog(null, message, "Salir",
					JOptionPane.YES_NO_OPTION);
			if (input == JOptionPane.YES_OPTION) {
				this.dispose();
			}
		});

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				JLabel message = new LabelPixel("¿Seguro que quieres salir?",8);
				int input = JOptionPane.showConfirmDialog(null, message, "Salir",
						JOptionPane.YES_NO_OPTION);
				if (input == JOptionPane.YES_OPTION) {
					Menu.this.dispose();
					logger.info("Se ha cerrado la ventana");
				}
			}

		});
	}
}
