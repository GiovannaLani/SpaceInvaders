package gui.statistics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import db.DBException;
import db.DatabaseController;
import domain.Game;


public class Top10Panel extends JPanel {

	private static final long serialVersionUID = 1L;

	public Top10Panel(){
		List<Game> playersData = new ArrayList<Game>();
		try {
			playersData = DatabaseController.getInstance().selectTop10();
		} catch (DBException e) {
			e.printStackTrace();
		}

		/////////
		setLayout(new BorderLayout());
		
		// COMPONENTES
		
		Border empty = BorderFactory.createEmptyBorder(10, 20, 10, 20);
		JPanel panel = new JPanel(new BorderLayout());

		// Tabla
		TopTableModel tableModel = new TopTableModel(playersData);
		JTable jTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(jTable);
		scrollPane.setBackground(Color.BLACK);
		scrollPane.getViewport().setBackground(Color.BLACK);
		scrollPane.setBorder(empty);
		panel.add(scrollPane);;
		add(panel);

		// Renderer
		jTable.setDefaultRenderer(Object.class, new TableRenderer()); 
		jTable.getTableHeader().setDefaultRenderer(new HeaderRenderer()); 
		jTable.getTableHeader().setPreferredSize(new Dimension(15,20));	 
		
	}
}
