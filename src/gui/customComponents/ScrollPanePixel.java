package gui.customComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JScrollPane;

public class ScrollPanePixel extends JScrollPane{

	private static final long serialVersionUID = 1L;

	public ScrollPanePixel(Component text, int size) {
		super(text);
		Font font = new FontLoader().getCustomFont(size);

		setFont(font);
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
	}
}
