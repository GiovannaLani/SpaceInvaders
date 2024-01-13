package gui.statistics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import gui.customComponents.FontLoader;

public class HeaderRenderer implements TableCellRenderer{
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel jlabel = new JLabel(value.toString());
		jlabel.setOpaque(true);
		jlabel.setBackground(Color.BLACK);
		jlabel.setForeground(Color.GREEN);
		Font font = new FontLoader().getCustomFont(8);
		jlabel.setFont(font);
		jlabel.setHorizontalAlignment(JLabel.CENTER);
		jlabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		
		return jlabel;
	}
	
}
