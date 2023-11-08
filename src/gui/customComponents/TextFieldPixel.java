package gui.customComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextField;

public class TextFieldPixel extends JTextField {

	private static final long serialVersionUID = 1L;

	public TextFieldPixel(int size) {
		super();
		Font font = new FontLoader().getCustomFont(size);
		setFont(font);
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		setMargin(new Insets(5, 0, 5, 0));
	}
}
