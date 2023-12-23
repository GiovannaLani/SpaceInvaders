package gui.Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import db.DBException;
import db.DatabaseController;
import domain.Game;
import domain.Player;
import game.Chronometer;
import game.LevelType;
import game.SpaceInvaders;
import gui.customComponents.ButtonPixel;
import gui.customComponents.ComboBoxPixel;
import gui.customComponents.LabelPixel;
import gui.customComponents.TextFieldPixel;

public class InfoWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel pCenter, pSouthRight;
	private LabelPixel lName, lCountry, lPassword, lLevelType;
	private ButtonPixel bCancel, bOK;
	private JPasswordField pfPassword;
	private TextFieldPixel txtName;
	private ComboBoxPixel jbCountries;
	private ComboBoxPixel jbLevelType;
	private LevelType customLevel;
	private Menu menu;

	private static Logger logger = Logger.getLogger(InfoWindow.class.getName());

	public InfoWindow(Menu menu) {
		super();
		this.menu = menu;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//logger
		try (FileInputStream fis = new FileInputStream("res/logger.properties")) {
			LogManager.getLogManager().readConfiguration(fis);
		} catch (IOException e) {
			logger.severe( "No se pudo leer el fichero de configuración del logger");
		}

	}

	// creacion lista de paises
	public static List<String> createCountryList() {
		Locale[] local = Locale.getAvailableLocales();
		List<String> countries = new ArrayList<String>();
		// añade paises a lista
		for (Locale localeObject : local) {
			if (!localeObject.getDisplayCountry().isEmpty()) {
				countries.add(localeObject.getDisplayCountry());
			}
		}
		countries.sort(Comparator.naturalOrder());// orden alfabético

		// elimina elementos duplicados y los devuelve en el mismo orden
		Set<String> setCountries = new LinkedHashSet<>(countries);
		logger.fine("Se ha creado la lista de paises");
		return new ArrayList<String>(setCountries);
	}

	// JComboBox
	List<String> countryList = createCountryList();
	{
		// convierte countryList a un array de strings
		String[] arrayOfCountries = countryList.toArray(new String[0]);
		jbCountries = new ComboBoxPixel(arrayOfCountries, 10);

		// orden y colocacion de los paneles
		pCenter = new JPanel();
		pCenter.setBackground(Color.BLACK);
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
		pSouthRight = new JPanel(new FlowLayout());
		pSouthRight.setBackground(Color.BLACK);
		getContentPane().add(pCenter, BorderLayout.CENTER);
		getContentPane().add(pSouthRight, BorderLayout.SOUTH);

		// label
		Border emptyBorder = BorderFactory.createEmptyBorder(5, 0, 5, 0);
		lName = new LabelPixel("Introduce tu nombre: ", 10);
		lName.setBorder(emptyBorder);
		lCountry = new LabelPixel("País", 10);
		lCountry.setBorder(emptyBorder);
		lPassword = new LabelPixel("Contraseña: ", 10);
		lPassword.setBorder(emptyBorder);
		lLevelType = new LabelPixel("Tipo de nivel: ", 10);
		lLevelType.setBorder(emptyBorder);
		lName.setAlignmentX(Component.LEFT_ALIGNMENT);
		lPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
		lCountry.setAlignmentX(Component.LEFT_ALIGNMENT);
		lLevelType.setAlignmentX(Component.LEFT_ALIGNMENT);

		// combobox
		jbCountries.setAlignmentX(Component.LEFT_ALIGNMENT);
		jbCountries.setMaximumSize(jbCountries.getPreferredSize());
		jbLevelType = new ComboBoxPixel(new String[] { "Normal", "Personalizado", "Drops" }, 10);
		jbLevelType.setAlignmentX(Component.LEFT_ALIGNMENT);
		jbLevelType.setMaximumSize(jbCountries.getPreferredSize());
		// botones
		bCancel = new ButtonPixel("Cancelar", 10);
		bOK = new ButtonPixel("OK", 10);
		txtName = new TextFieldPixel(10);
		pfPassword = new JPasswordField(40);
		pCenter.add(lName);
		pCenter.add(txtName);
		pCenter.add(lPassword);
		pCenter.add(pfPassword);
		pCenter.add(lCountry);
		pCenter.add(jbCountries);
		pCenter.add(lLevelType);
		pCenter.add(jbLevelType);
		pSouthRight.add(bOK);
		pSouthRight.add(bCancel);

		Border empty = BorderFactory.createEmptyBorder(10, 20, 10, 20);
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
		pCenter.setBorder(empty);

		// Listeners
		bOK.addActionListener((e) -> {
			try {
				if(DatabaseController.getInstance().userExists(txtName.getText())) {
					if(DatabaseController.getInstance().checkUserByUsernameAndPassword(txtName.getText(), String.valueOf(pfPassword.getPassword()))) {
						startGame();
					}
					else {
						JOptionPane.showMessageDialog(null, "La contraseña introducida es incorrecta. Inténtelo de nuevo.", "Error contraseña", JOptionPane.ERROR_MESSAGE);					}
				}else {
					startGame();
				}
			} catch (DBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
		bCancel.addActionListener((e) -> {
			this.dispose();
			menu.setVisible(true);
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				menu.setVisible(true);
			}
		});
		this.pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public void startGame() {
		if (jbLevelType.getSelectedIndex() == 0) {
			customLevel = LevelType.NORMAL;
		}else if(jbLevelType.getSelectedIndex() == 1) {
			customLevel = LevelType.CUSTOM;
		}else {
			customLevel = LevelType.DROPS;
		}
		if (txtName.getText().isEmpty() || pfPassword.getPassword().length == 0) {
			JOptionPane.showMessageDialog(null,"Hay campos sin rellenar");
		}else {
			Player player= new Player(txtName.getText(), String.valueOf(pfPassword.getPassword()), jbCountries.getSelectedItem().toString());
			SwingUtilities.invokeLater(() -> new SpaceInvaders(player, new Game(player,0,new Chronometer(),null,1), customLevel, menu));
			this.dispose();
		}
	}


}
