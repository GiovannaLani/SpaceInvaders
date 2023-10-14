package gui.statistics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import domain.Game;
import domain.Player;

public class PlayerPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public PlayerPanel() {
		// ELIMINAR (datos prueba)
		Player p1 = new Player("Nombre1", "x", "Pais1");
		Player p2 = new Player("Nombre2", "x", "Pais2");
		Player p3 = new Player("Nombre3", "x", "Pais3");

		Player[] names = { p1, p2, p3 };
		ArrayList<Game> data = new ArrayList<>();
		data.add(new Game(p1, 5000, 1000000, LocalDate.of(2023, 11, 10), 1));
		data.add(new Game(p1, 3000, 2000000, LocalDate.of(2023, 11, 9), 2));
		data.add(new Game(p1, 6000, 3000000, LocalDate.of(2023, 11, 8), 3));

		data.add(new Game(p2, 4000, 1000000, LocalDate.of(2023, 11, 9), 1));
		data.add(new Game(p2, 6000, 3000000, LocalDate.of(2023, 11, 8), 3));

		data.add(new Game(p3, 3000, 1000000, LocalDate.of(2023, 11, 8), 1));
		data.add(new Game(p3, 6000, 3000000, LocalDate.of(2023, 11, 7), 3));
		ArrayList<Game> playerData = new ArrayList<>();

		/////////
		setLayout(new BorderLayout());

		// COMPONENTES
		// JList de los jugadores
		DefaultListModel<String> model = new DefaultListModel<>();
		for (Player p : names) {
			model.addElement(p.toString());
		}
		JList<String> lPlayers = new JList<>(model);

		// Borde vacío para separar componentes
		Border empty = BorderFactory.createEmptyBorder(10, 20, 10, 20);
		// Panel de la izquierda
		JPanel pRight = new JPanel(new BorderLayout());
		// Panel para ordenar la tabla con BoxLayout
		JPanel pOrder = new JPanel();
		pOrder.setLayout(new BoxLayout(pOrder, BoxLayout.Y_AXIS));
		pOrder.setBorder(empty);
		TableTitle[] cbTitles = { TableTitle.PUNTUACION, TableTitle.FECHA, TableTitle.TIEMPO, TableTitle.NIVEL };
		JComboBox<TableTitle> cbOrder = new JComboBox<>(cbTitles);
		JLabel text = new JLabel("Ordenar por:");
		text.setAlignmentX(Component.LEFT_ALIGNMENT);
		cbOrder.setAlignmentX(Component.LEFT_ALIGNMENT);
		pOrder.add(text);
		cbOrder.setMaximumSize(cbOrder.getPreferredSize());
		pOrder.add(cbOrder);
		pRight.add(pOrder, BorderLayout.NORTH);

		// Tabla

		PlayerTableModel tableModel = new PlayerTableModel(playerData);
		JTable jTable = new JTable(tableModel);
		JScrollPane scrollPain = new JScrollPane(jTable);
		scrollPain.setBorder(empty);
		pRight.add(scrollPain);

		// SplitPane
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(lPlayers), pRight);
		jSplitPane.setOneTouchExpandable(true);
		jSplitPane.setDividerLocation(150);
		add(jSplitPane);

		// LISTENERS
		lPlayers.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				playerData.clear();
				for (Game g : data) {
					if (g.getPlayer().getName().equals(lPlayers.getSelectedValue())) {
						playerData.add(g);
					}
				}
				playerData.sort(new PlayerComparator((TableTitle) cbOrder.getSelectedItem()));
				tableModel.fireTableDataChanged();

			}
		});

		cbOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playerData.sort(new PlayerComparator((TableTitle) cbOrder.getSelectedItem()));
				tableModel.fireTableDataChanged();
			}
		});
	}
}
