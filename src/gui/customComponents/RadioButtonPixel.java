package gui.customComponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JRadioButton;

public class RadioButtonPixel extends JRadioButton {

	private static final long serialVersionUID = 1L;

	public RadioButtonPixel(String text, int size) {
		super(text);
		Font font = new FontLoader().getCustomFont(size);

		setFont(font);
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
	}
}
