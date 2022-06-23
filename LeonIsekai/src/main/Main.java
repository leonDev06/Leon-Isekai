package main;

import javax.swing.JFrame;

public class Main {
	
	private static final String gameTitle = "Leon Isekai";

	public static void main(String[] args) {
		
		//Creating a window
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle(gameTitle);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		
		//Creating a gamePanel component and adding it to the main window
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		//Set window size to wrap content
		window.pack();
		
		
		gamePanel.startGameLoop();
		gamePanel.setFocusable(true);
		
		
		
	}

}
