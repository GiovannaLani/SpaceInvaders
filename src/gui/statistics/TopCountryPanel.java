package gui.statistics;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import db.DBException;
import db.DatabaseController;
import domain.Game;
import gui.Menu.InfoWindow;
import gui.customComponents.ComboBoxPixel;
import gui.customComponents.LabelPixel;

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
		ComboBoxPixel cbCountryList = new ComboBoxPixel(arrayOfCountries, 10);
		LabelPixel text = new LabelPixel("Selecciona país:", 10);

		cbCountryList.setAlignmentX(Component.LEFT_ALIGNMENT);
		cbCountryList.setMaximumSize(cbCountryList.getPreferredSize());

		Border empty = BorderFactory.createEmptyBorder(10, 20, 10, 20);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.BLACK);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(empty);
		panel.add(text);
		panel.add(cbCountryList);
		
		loadData(cbCountryList.getSelectedItem().toString());

		// Tabla
		TopCountryModel tableModel = new TopCountryModel(playersData);
		JTable jTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(jTable);
		scrollPane.setBackground(Color.BLACK);
		scrollPane.getViewport().setBackground(Color.BLACK);

		scrollPane.setBorder(empty);
		panel.add(scrollPane);
		add(panel);
		
		// Renderer
		jTable.setDefaultRenderer(Object.class, new TableRenderer()); 
		jTable.getTableHeader().setDefaultRenderer(new HeaderRenderer());
		jTable.getTableHeader().setPreferredSize(new Dimension(15,20));	 
		
		
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
