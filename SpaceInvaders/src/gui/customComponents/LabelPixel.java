package gui.customComponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class LabelPixel extends JLabel {

	private static final long serialVersionUID = 1L;

	public LabelPixel(String text, int size) {
		super(text);
		Font font = new FontLoader().getCustomFont(size);
		setFont(font);
		setForeground(Color.WHITE);
	}

}
