package gui.Menu;

import javax.swing.table.AbstractTableModel;

public class LevelTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[][] lEnemies;

	public LevelTableModel(String[][] lEnemies) {
		this.lEnemies = lEnemies;
	}

	@Override
	public int getRowCount() {
		return CustomLevelWindow.getNumRow();
	}

	@Override
	public int getColumnCount() {
		return CustomLevelWindow.getNumColumn();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return lEnemies[rowIndex][columnIndex];
	}

}
