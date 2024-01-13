package gui.customComponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;

import gui.statistics.TableTitle;

public class ComboBoxPixelTT extends JComboBox<TableTitle> {

	private static final long serialVersionUID = 1L;

	public ComboBoxPixelTT(TableTitle[] text, int size) {
		super(text);
		Font font = new FontLoader().getCustomFont(size);

		setFont(font);
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);

	}
}
