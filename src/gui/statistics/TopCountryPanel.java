package gui.statistics;


import java.awt.BorderLayout;
import java.awt.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import domain.Game;
import domain.Player;
import gui.Menu.InfoWindow;

public class TopCountryPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public TopCountryPanel(){
		// ELIMINAR (datos prueba)
		Player p1 = new Player("Nombre1", "x", "Alemania");
		Player p2 = new Player("Nombre2", "x", "España");
		Player p3 = new Player("Nombre3", "x", "Canadá");
		Player p4 = new Player("Nombre4", "x", "Alemania");
		Player p5 = new Player("Nombre5", "x", "Japón");
		Player p6 = new Player("Nombre6", "x", "Egipto");
		Player p7 = new Player("Nombre7", "x", "Argentina");
		Player p8 = new Player("Nombre8", "x", "Alemania");
		Player p9 = new Player("Nombre9", "x", "Canadá");
		Player p10 = new Player("Nombre10", "x", "Alemania");
		Player p11 = new Player("Nombre11", "x", "España");

		Player[] names = { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10 };
		ArrayList<Game> data = new ArrayList<>();
		data.add(new Game(p1, 5000, 1000000, LocalDate.of(2023, 11, 10), 1));
		data.add(new Game(p2, 4000, 1000000, LocalDate.of(2023, 11, 9), 1));
		data.add(new Game(p3, 3000, 1000000, LocalDate.of(2023, 11, 8), 1));
		data.add(new Game(p4, 3000, 2000000, LocalDate.of(2023, 11, 9), 2));
		data.add(new Game(p5, 6000, 3000000, LocalDate.of(2023, 11, 8), 3));
		data.add(new Game(p6, 9000, 1000000, LocalDate.of(2023, 11, 10), 1));
		data.add(new Game(p7, 2500, 1000000, LocalDate.of(2023, 11, 9), 1));
		data.add(new Game(p8, 1050, 1000000, LocalDate.of(2023, 11, 8), 1));
		data.add(new Game(p9, 3250, 2000000, LocalDate.of(2023, 11, 9), 2));
		data.add(new Game(p10, 5900, 3000000, LocalDate.of(2023, 11, 8), 3));
		data.add(new Game(p11, 8900, 3000000, LocalDate.of(2023, 11, 8), 3));
		ArrayList<Game> playersData = new ArrayList<>();

		setLayout(new BorderLayout());

		// COMPONENTES
		// JList de los jugadores
		DefaultListModel<String> model = new DefaultListModel<>();
		for (Player p : names) {
			model.addElement(p.toString());
		}
		// Lista de paises en JComboBox
		List<String> countryList = InfoWindow.createCountryList();
		String[] arrayOfCountries = countryList.toArray(new String[0]); 
		JComboBox<String> cbCountryList = new JComboBox<>(arrayOfCountries);
		JLabel text = new JLabel("Selecciona país:");
		
		cbCountryList.setAlignmentX(Component.LEFT_ALIGNMENT);
		cbCountryList.setMaximumSize(cbCountryList.getPreferredSize());
		
		Border empty = BorderFactory.createEmptyBorder(10, 20, 10, 20);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(empty);
		panel.add(text);
		panel.add(cbCountryList);
		
		// Tabla
		TopCountryModel tableModel = new TopCountryModel(playersData);
		JTable jTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(jTable);
		scrollPane.setBorder(empty);
		panel.add(scrollPane);
		add(panel);
		
		//listener
		cbCountryList.addActionListener((e)-> {
			playersData.clear();
			for (Game g : data) {
				if(g.getPlayer().getCountry().equals(cbCountryList.getSelectedItem())) {
					playersData.add(g);
				}
			}
			playersData.sort(new TopComparator());
			tableModel.fireTableDataChanged();
		});
	}
}
