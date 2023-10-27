package gui.Menu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class InfoWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private JPanel pCenter, pSouthRight;
	private JLabel lName, lCountry,lPassword;
	private JButton bCancel, bOK;
	private JPasswordField pfPassword;
	private JTextField txtName;
	private JComboBox<String> jbCountries;

	public InfoWindow() {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}   
	//creacion lista de paises
	public static List<String> createCountryList(){
		Locale[] local = Locale.getAvailableLocales();
		List<String> countries = new ArrayList<String>();
		//añade paises a lista
		for(Locale localeObject: local ) { 
			if(!localeObject.getDisplayCountry().isEmpty()) { 
				countries.add(localeObject.getDisplayCountry());
			}
		}
		countries.sort(Comparator.naturalOrder());//orden alfabético 

		//elimina elementos duplicados y los devuelve en el mismo orden
		Set<String> setCountries = new LinkedHashSet<>(countries);
		return new ArrayList<String>(setCountries);
	}

	//JComboBox
	List<String> countryList = createCountryList();{
		//convierte countryList a un array de strings
		String[] arrayOfCountries = countryList.toArray(new String[0]); 
		jbCountries = new JComboBox<>(arrayOfCountries);


		//orden y colocacion de los paneles
		pCenter = new JPanel();
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
		pSouthRight = new JPanel(new FlowLayout());
		getContentPane().add(pCenter, BorderLayout.CENTER);
		getContentPane().add(pSouthRight, BorderLayout.SOUTH);

		//label
		lName = new JLabel("Introduce tu nombre: ");
		lCountry = new JLabel("País");
		lPassword = new JLabel("Contraseña: ");
		lName.setAlignmentX(Component.LEFT_ALIGNMENT);
		lPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
		lCountry.setAlignmentX(Component.LEFT_ALIGNMENT);

		jbCountries.setAlignmentX(Component.LEFT_ALIGNMENT);
		jbCountries.setMaximumSize(jbCountries.getPreferredSize());

		//botones
		bCancel = new JButton("Cancelar");
		bOK = new JButton("OK");
		txtName = new JTextField(40);
		pfPassword = new JPasswordField(40);
		pCenter.add(lName);
		pCenter.add(txtName);
		pCenter.add(lPassword);
		pCenter.add(pfPassword);
		pCenter.add(lCountry);
		pCenter.add(jbCountries);
		pSouthRight.add(bOK);
		pSouthRight.add(bCancel);


		Border empty = BorderFactory.createEmptyBorder(10, 20, 10, 20);
		pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
		pCenter.setBorder(empty);

		//Listeners
		bOK.addActionListener((e)->{				
		});
		bCancel.addActionListener((e)->{
		});

		this.pack();

		setVisible(true);}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new InfoWindow();
		});
	}
}
