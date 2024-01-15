package gui.customComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		setFocusPainted(false);
		setContentAreaFilled(false);
		setOpaque(true);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(Color.WHITE);
				setForeground(Color.BLACK);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(Color.BLACK);
				setForeground(Color.WHITE);
			}

		});
		
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border lineBorder = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);

		setBorder(compoundBorder);
	}

}
