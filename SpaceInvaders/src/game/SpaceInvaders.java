package game;

import javax.swing.JFrame;

import domain.Player;
import gui.Menu.Menu;


public class SpaceInvaders extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public SpaceInvaders(Player player, boolean customLevel, Menu menu) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		GamePanel gamePanel= new GamePanel(player, customLevel, menu, this);
		add(gamePanel.layeredPane);
		pack();
				
		setLocationRelativeTo(null);
		setVisible(true);
		
		gamePanel.startGameThread();
	}
}
