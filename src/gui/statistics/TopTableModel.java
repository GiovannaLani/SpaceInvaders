package gui.statistics;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.Game;

public class TopTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<Game> data;
	private String[] columnName = { "Nombre", "Puntuación", "Fecha", "Tiempo", "Pais" };

	public TopTableModel(List<Game> data) {
		this.data = data;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return data.get(rowIndex).getPlayer().getName();
		case 1:
			return data.get(rowIndex).getScore();
		case 2:
			return data.get(rowIndex).getDate();
		case 3:
			return data.get(rowIndex).getTime();
		case 4:
			return data.get(rowIndex).getPlayer().getCountry();
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}

}
