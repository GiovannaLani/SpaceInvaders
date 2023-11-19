package gui.statistics;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import controller.DBException;
import controller.DatabaseController;
import domain.Game;


public class Top10Panel extends JPanel {

	private static final long serialVersionUID = 1L;

	public Top10Panel(){
		List<Game> playersData = new ArrayList<Game>();
		try {
			playersData = DatabaseController.getInstance().selectTop10();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/////////
		setLayout(new BorderLayout());

		// COMPONENTES
		// JList de los jugadores
		
		Border empty = BorderFactory.createEmptyBorder(10, 20, 10, 20);
		JPanel panel = new JPanel(new BorderLayout());

		// Tabla
		TopTableModel tableModel = new TopTableModel(playersData);
		JTable jTable = new JTable(tableModel);
		JScrollPane scrollPain = new JScrollPane(jTable);
		scrollPain.setBorder(empty);
		panel.add(scrollPain);
		add(panel);


	}
}
