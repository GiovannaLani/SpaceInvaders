package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PausePanel extends JPanel{
	public PausePanel(GamePanel p) {
		setPreferredSize(new Dimension(320, 350));
		JButton bContinue = new JButton("CONTINUAR");
		JButton bRestart = new JButton("REINICIAR");
		JButton bMenu = new JButton("MENÚ");
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
				p.setGamePaused(false);
				if(!(p.isGamePaused())) {
					synchronized (p.getGameThread()) {
						p.getGameThread().notify();
					}
					setVisible(false);
				}
				
			}
		});
		
		bRestart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				p.restartGame();
			}
		});
		
		bMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {		
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
