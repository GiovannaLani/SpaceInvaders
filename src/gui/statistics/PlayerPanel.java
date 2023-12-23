package gui.statistics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import db.DBException;
import db.DatabaseController;
import domain.Game;
import domain.Player;

public class PlayerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<Game> playerData;

	public PlayerPanel() {
		List<Player> names = new ArrayList<Player>();
		try {
			names = DatabaseController.getInstance().loadPlayer();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playerData = new ArrayList<>();

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
				loadData(lPlayers.getSelectedValue());
				playerData.sort(new PlayerComparator((TableTitle) cbOrder.getSelectedItem()));
				tableModel.setData(playerData);
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
	
	private void loadData(String p) {
		try {
			playerData = DatabaseController.getInstance().selectPlayerTop(p);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}
