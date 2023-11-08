package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import gui.Menu.Menu;
import gui.customComponents.ButtonPixel;

public class PausePanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public PausePanel(GamePanel p, Menu menu) {
		setPreferredSize(new Dimension(320, 350));
		ButtonPixel bContinue = new ButtonPixel("CONTINUAR",10);
		ButtonPixel bRestart = new ButtonPixel("REINICIAR",10);
		ButtonPixel bMenu = new ButtonPixel("MENU",10);
		buttonStyle(bMenu);
		buttonStyle(bContinue);
		buttonStyle(bRestart);
		setLayout(new GridLayout(3,1));
		add(bContinue);
		add(bRestart);
		add(bMenu);

		bContinue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p.resumeGame();
				setVisible(false);
			}
		});

		bRestart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p.restartGame(0,3);
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
