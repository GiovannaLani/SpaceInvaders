package game;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import domain.Game;
import domain.Player;
import gui.Menu.Menu;


public class SpaceInvaders extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public SpaceInvaders(Player player, Game game,  LevelType customLevel, Menu menu) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Border lineBorder = BorderFactory.createLineBorder(Color.GREEN, 3, false);
		
		GamePanel gamePanel= new GamePanel(player, game, customLevel, menu, this);
		gamePanel.setBorder(lineBorder);
		
		JPanel panelPrincipal = new JPanel(new GridBagLayout());
		panelPrincipal.setBackground(Color.BLACK);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.CENTER;
		panelPrincipal.add(gamePanel.layeredPane, gbc);

		add(panelPrincipal);
		pack();
				
		setLocationRelativeTo(null);
		setVisible(true);
		
		gamePanel.startGameThread();
	}
}
