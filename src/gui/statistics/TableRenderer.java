package gui.statistics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import gui.customComponents.FontLoader;

public class TableRenderer implements TableCellRenderer{
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		table.setFillsViewportHeight(true);
		table.setBackground(Color.BLACK);
		
		JLabel jlabel = new JLabel(value.toString());
		jlabel.setOpaque(true);
		jlabel.setBackground(Color.BLACK);
		jlabel.setForeground(Color.WHITE);
		Font font = new FontLoader().getCustomFont(8);
		jlabel.setFont(font);
		jlabel.setHorizontalAlignment(JLabel.CENTER);
		jlabel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		
		return jlabel;
	}

	
}
