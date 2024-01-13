package gui.customComponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ListPixel extends JList<String> {

	private static final long serialVersionUID = 1L;

	public ListPixel(DefaultListModel<String> model, int size) {
		super(model);
		Font font = new FontLoader().getCustomFont(size);
		setFont(font);
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
	}
}
