package gui.statistics;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import domain.Game;
import domain.Player;

public class Top10Panel extends JPanel {

	private static final long serialVersionUID = 1L;

	public Top10Panel(){
		// ELIMINAR (datos prueba)
		Player p1 = new Player("Nombre1", "x", "Pais1");
		Player p2 = new Player("Nombre2", "x", "Pais2");
		Player p3 = new Player("Nombre3", "x", "Pais3");
		Player p4 = new Player("Nombre4", "x", "Pais2");
		Player p5 = new Player("Nombre5", "x", "Pais2");
		Player p6 = new Player("Nombre6", "x", "Pais3");
		Player p7 = new Player("Nombre7", "x", "Pais4");
		Player p8 = new Player("Nombre8", "x", "Pais1");
		Player p9 = new Player("Nombre9", "x", "Pais4");
		Player p10 = new Player("Nombre10", "x", "Pais5");

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
		ArrayList<Game> playersData = new ArrayList<>();

		/////////
		setLayout(new BorderLayout());

		// COMPONENTES
		// JList de los jugadores
		DefaultListModel<String> model = new DefaultListModel<>();
		for (Player p : names) {
			model.addElement(p.toString());
		}
		
		Border empty = BorderFactory.createEmptyBorder(10, 20, 10, 20);
		JPanel panel = new JPanel(new BorderLayout());

		// Tabla
		TopTableModel tableModel = new TopTableModel(playersData);
		JTable jTable = new JTable(tableModel);
		JScrollPane scrollPain = new JScrollPane(jTable);
		scrollPain.setBorder(empty);
		panel.add(scrollPain);
		add(panel);
		
		data.sort(new TopComparator());
		
		for (int i = 0 ; i < 10; i++) {
			playersData.add(data.get(i));
		}

	}
}
