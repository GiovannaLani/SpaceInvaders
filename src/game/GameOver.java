package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import gui.Menu.Menu;
import gui.customComponents.ButtonPixel;
import gui.customComponents.LabelPixel;

public class GameOver extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public GameOver(GamePanel p, Menu menu) {
		setPreferredSize(new Dimension(400, 350));
		setBackground(Color.BLACK);
		ButtonPixel bRestart = new ButtonPixel("REINICIAR",10);
		ButtonPixel bMenu = new ButtonPixel("MENU",10);
		buttonStyle(bMenu);
		buttonStyle(bRestart);
		
		// Border
		Border emptyBorder = BorderFactory.createEmptyBorder(20, 30, 20, 30);
		
		JPanel gameOv = new JPanel();
		gameOv.setLayout(new BoxLayout(gameOv, BoxLayout.Y_AXIS));
		gameOv.setBackground(Color.BLACK);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,2));
		buttons.setPreferredSize(new Dimension(320,60));
		buttons.add(bRestart);
		buttons.add(bMenu);
		
		LabelPixel lGameOver = new LabelPixel("GAME OVER", 35);
		lGameOver.setBorder(emptyBorder);
		lGameOver.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		gameOv.add(Box.createVerticalGlue());
		gameOv.add(lGameOver);
		
		add(gameOv);
		add(buttons);
		
		bRestart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p.restartGame(0,null);
			}
		});

		bMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p.disposeWindow();
				menu.setVisible(true);
			}
		});	
	}
	
	private void buttonStyle(JButton b) {
		b.setBackground(Color.BLACK);
		b.setForeground(Color.GREEN);
		b.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		clickButtonStyle(b);
		b.setFocusPainted(false);
		b.setContentAreaFilled(false);
		b.setOpaque(true);
	}

	private void clickButtonStyle(JButton b) {
		b.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				b.setBackground(Color.GREEN);
				b.setForeground(Color.BLACK);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				b.setBackground(Color.BLACK);
				b.setForeground(Color.GREEN);
			}

		});
	}
}


