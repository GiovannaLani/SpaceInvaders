package gui.customComponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;

public class ComboBoxPixel extends JComboBox<String> {

	private static final long serialVersionUID = 1L;

	public ComboBoxPixel(String[] text, int size) {
		super(text);
		Font font = new FontLoader().getCustomFont(size);

		setFont(font);
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);

	}
}
