package gui.Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;

import game.AlienCrab;
import game.AlienOctopus;
import game.AlienSquid;
import gui.customComponents.ButtonPixel;
import gui.customComponents.LabelPixel;
import gui.customComponents.RadioButtonPixel;

public class CustomLevelWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JSpinner jsRow;
	private static JSpinner jsColumn;

	private String[][] lEnemies;
	private String selectedButton;

	public CustomLevelWindow(Menu menuWindow) {
		// cerrar ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Cargar lista de alienigenas
		lEnemies = loadLevel("res/data/level.dat");
		// Paneles
		JPanel pCenter = new JPanel(new BorderLayout());
		JPanel pLeft = new JPanel(new BorderLayout());
		pLeft.setBackground(Color.BLACK);
		JPanel pLeftCenter = new JPanel();
		pLeftCenter.setBackground(Color.BLACK);
		JPanel pLeftDown = new JPanel();
		pLeftDown.setBackground(Color.BLACK);

		// Borders
		Border empty = BorderFactory.createEmptyBorder(10, 20, 10, 20);
		Border emptyComponent = BorderFactory.createEmptyBorder(10, 0, 3, 20);
		Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		Border compoundBorder = BorderFactory.createCompoundBorder(emptyComponent, lineBorder);

		// Panel derecha
		pLeft.setBorder(empty);
		pLeftCenter.setLayout(new BoxLayout(pLeftCenter, BoxLayout.Y_AXIS));
		pLeft.add(pLeftCenter);
		pLeft.add(pLeftDown, BorderLayout.SOUTH);

		// JSPINNER
		// calculo inicial de filas y coumnas
		int rowNum = 1;
		int columnNum = 1;

		for (int i = 9; i >= 0; i--) {
			for (int j = 0; j <= 11; j++) {
				if (lEnemies[i][j] != null) {
					rowNum = i + 1;
					break;
				}
			}
			if (rowNum != 1) {
				break;
			}
		}
		for (int i = 11; i >= 0; i--) {
			for (int j = 0; j <= 9; j++) {
				if (lEnemies[j][i] != null) {
					columnNum = i + 1;
					System.out.println(columnNum);
					break;
				}
			}
			if (columnNum != 1) {
				break;
			}
		}
		// jSpinner para numero de columnas(1-12) y filas(1-10)
		SpinnerModel rowModel = new SpinnerNumberModel(rowNum, 1, 10, 1);
		SpinnerModel columnModel = new SpinnerNumberModel(columnNum, 1, 12, 1);
		jsRow = new JSpinner(rowModel);
		jsColumn = new JSpinner(columnModel);
		jsRow.setMaximumSize(jsRow.getPreferredSize());
		jsColumn.setMaximumSize(jsColumn.getPreferredSize());
		jsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
		jsColumn.setAlignmentX(Component.LEFT_ALIGNMENT);
		// label de los jSpinner
		LabelPixel lRow = new LabelPixel("Filas:", 10);
		LabelPixel lColumn = new LabelPixel("Columnas:", 10);
		lRow.setBorder(emptyComponent);
		lColumn.setBorder(emptyComponent);

		// RADIOBUTTTONS
		// panel para los radioButtons con un borde blanco
		JPanel pRadioButtons = new JPanel();
		pRadioButtons.setBackground(Color.BLACK);
		pRadioButtons.setLayout(new BoxLayout(pRadioButtons, BoxLayout.Y_AXIS));
		pRadioButtons.setBorder(compoundBorder);
		// Radio button personalizados y añadi al buttongroup
		RadioButtonPixel rbCrab = new RadioButtonPixel("Cangrejo", 10);
		RadioButtonPixel rbOctopus = new RadioButtonPixel("Pulpo", 10);
		RadioButtonPixel rbSquid = new RadioButtonPixel("Calamar", 10);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rbCrab);
		buttonGroup.add(rbOctopus);
		buttonGroup.add(rbSquid);

		// JLabel informativo
		LabelPixel lDelete = new LabelPixel("Borrar: ctr + click", 7);

		// añadir JSpinner, JRadioButton y JLabel al panel LeftCenter
		pLeftCenter.add(lRow);
		pLeftCenter.add(jsRow);
		pLeftCenter.add(lColumn);
		pLeftCenter.add(jsColumn);
		pRadioButtons.add(rbCrab);
		pRadioButtons.add(rbOctopus);
		pRadioButtons.add(rbSquid);
		pLeftCenter.add(pRadioButtons);
		pLeftCenter.add(lDelete);

		// JBUTTON guardado
		ButtonPixel bSave = new ButtonPixel("Guardar", 10);
		pLeftDown.add(bSave);

		// JTABLE
		LevelTableModel tableModel = new LevelTableModel(lEnemies);
		JTable jTable = new JTable(tableModel);
		jTable.setBorder(lineBorder);
		JScrollPane scrollPain = new JScrollPane(jTable);
		scrollPain.setBorder(empty);
		scrollPain.setBackground(Color.BLACK);
		scrollPain.getViewport().setBackground(Color.BLACK);
		jTable.setDefaultRenderer(Object.class, new LevelRender());
		jTable.setTableHeader(null);
		jTable.setCellEditor(null);
		jTable.setRowHeight(40);
		jTable.setGridColor(Color.WHITE);
		jTable.setBackground(Color.BLACK);
		pCenter.add(scrollPain);

		// Añadir paneles
		add(pCenter);
		add(pLeft, BorderLayout.WEST);

		// LISTENER
		// Listener al cambiar JSpinner
		ChangeListener changeListener = e -> {
			tableModel.fireTableStructureChanged();
			// elimina de la lista los enemigos que ya no se ven
			for (int i = 0; i < lEnemies.length; i++) {
				for (int j = 11; j >= Integer.parseInt(jsColumn.getValue().toString()); j--) {
					lEnemies[i][j] = null;
				}
			}
			for (int i = 9; i >= Integer.parseInt(jsRow.getValue().toString()); i--) {
				for (int j = 0; j < lEnemies[0].length; j++) {
					lEnemies[i][j] = null;
				}
			}
		};
		jsRow.addChangeListener(changeListener);
		jsColumn.addChangeListener(changeListener);
		// Listener de la tabla al hacer click
		jTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// busca la celda donde se ha hecho click y cambia su valor en el array
				Point point = e.getPoint();
				if (!e.isControlDown()) {
					lEnemies[jTable.rowAtPoint(point)][jTable.columnAtPoint(point)] = selectedButton;
				} else {
					lEnemies[jTable.rowAtPoint(point)][jTable.columnAtPoint(point)] = null;
				}
				tableModel.fireTableDataChanged();

			}
		});
		// Listener para los radio buttons
		ActionListener listenerRB = e -> {
			if (e.getSource().equals(rbCrab)) {
				selectedButton = AlienCrab.class.getSimpleName();
			} else if (e.getSource().equals(rbOctopus)) {
				selectedButton = AlienOctopus.class.getSimpleName();
			} else {
				selectedButton = AlienSquid.class.getSimpleName();
			}
		};
		rbCrab.addActionListener(listenerRB);
		rbOctopus.addActionListener(listenerRB);
		rbSquid.addActionListener(listenerRB);
		// Listenr botón guardar
		bSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isEmpty = true;
				for (int i = 0; i < lEnemies.length; i++) {
					for (int j = 0; j < lEnemies[0].length; j++) {
						if (lEnemies[i][j] != null) {
							isEmpty = false;
							break;
						}
					}
					if (!isEmpty) {
						break;
					}
				}
				if (isEmpty) {
					JOptionPane.showMessageDialog(pCenter, "Tiene que haber al menos un marciano.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/data/level.dat"))) {
						oos.writeObject(lEnemies);
						CustomLevelWindow.this.dispose();
						menuWindow.setVisible(true);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				menuWindow.setVisible(true);
			}

		});

		// tamaño y hacer visible
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public String[][] loadLevel(String file) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			lEnemies = (String[][]) ois.readObject();
			return lEnemies;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new String[10][12];
	}

	public static int getNumRow() {
		return Integer.parseInt(jsRow.getValue().toString());
	}

	public static int getNumColumn() {
		return Integer.parseInt(jsColumn.getValue().toString());
	}

}
