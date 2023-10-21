package game;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame v= new JFrame();
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GamePanel gamePanel= new GamePanel();
		v.add(gamePanel);
		v.pack();
				
		v.setLocationRelativeTo(null);
		v.setVisible(true);
		
		gamePanel.startGameThread();
	}
}
