package gui.customComponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class ButtonPixel extends JButton {

	private static final long serialVersionUID = 1L;

	public ButtonPixel(String text, int size) {
		super(text);
		Font font = new FontLoader().getCustomFont(size);

		setFont(font);
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);

		setBorder(compoundBorder);
	}

}
