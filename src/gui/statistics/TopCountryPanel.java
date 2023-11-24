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

import controller.DBException;
import controller.DatabaseController;
import domain.Game;
import domain.Player;
import gui.Menu.InfoWindow;

public class TopCountryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<Game> playersData;

	public TopCountryPanel(){
		
		playersData = new ArrayList<>();

		setLayout(new BorderLayout());

		// COMPONENTES
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
		
		loadData(cbCountryList.getSelectedItem().toString());

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
			loadData(cbCountryList.getSelectedItem().toString());
			tableModel.setData(playersData);
			tableModel.fireTableDataChanged();
		});
	}
	
	private void loadData(String country) {
		try {
			playersData = DatabaseController.getInstance().selectTop10Country(country);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
