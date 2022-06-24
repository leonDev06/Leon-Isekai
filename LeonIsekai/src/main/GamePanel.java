package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tiles.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{
	/*
	 * This gamePanel extends to JPanel.
	 * 
	 */
	// GAME SCREEN SETTINGS
	
	//SIZE OF EACH TILE
	final int originalTileSize = 16;	//Standard for 2d Retro Games. 16x16 pixels. However, screens today have 1920x1080 res. So, it'll look small.
	final int scale = 3; 				//Common workaround nowadays is to scale it. Setting originalTileSize to respect.
	public final int tileSize = originalTileSize * scale;	//Actual TileSize of the game	48*48 px
	
	//SIZE OF THE GAME SCREEN
	public final int maxScreenCol = 16; //of tileSize
	public final int maxScreenRow = 12; //of tileSize
	public final int screenWidth = tileSize * maxScreenCol;	//768px
	public final int screenHeight = tileSize *maxScreenRow;	//576px
	
	//DIMENSION OF THE SCREEN (576x768)
	final Dimension screenDimension = new Dimension (screenWidth, screenHeight);
	
	//The GameLoop (Thread that will serve as the gameLoop.)
	private Thread gameLoop;
	
	//Key Handler to handle all key input
	KeyHandler keyH = new KeyHandler();
	
	//Player Entity
	Player player = new Player(this, keyH);
	
	//Background Tiles
	TileManager tileManager = new TileManager(this);
	
	//THE FPS
	private int FPS = 60;
	private boolean isFPSVisible;
	
	//Constructor
	public GamePanel() {
		//Creating the GamePanel
		this.setPreferredSize(screenDimension);
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.grabFocus();
		
		//Use double buffering to improve rendering performance
		this.setDoubleBuffered(true);
		
		//Connecting the keyHandler to this Panel
		this.addKeyListener(keyH);
		this.grabFocus();
		
	}
	
	public void startGameLoop() {
		//Load Player Resources
		Thread loadingPlayerRes = player.getPlayerImage();
		loadingPlayerRes.start();
		
		//Load Tile Resources
		Thread loadingTileRes = tileManager.getTileImage();
		loadingTileRes.start();
		
		//Wait until resources have finished loading
		waitForThread(loadingPlayerRes);
		waitForThread(loadingTileRes);
		
		gameLoop = new Thread(this);
		gameLoop.start();
	}

	
	@Override
	public void run() {
		// The game loop
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		System.out.println("Beginning GameLoop");
		while(gameLoop != null) {
			
			currentTime = System.nanoTime();
			timer += (currentTime - lastTime);
			delta += (currentTime - lastTime)/drawInterval;
			lastTime = System.nanoTime();
			
			if(delta >= 1 ) {
				//Update the information
				update();
				//Repaint the screen based on the updated information. This is called instead of paintComponent();
				repaint();
				delta--;
				drawCount++;
			}
			
			showFPS(timer, drawCount);
			
			if(timer >= 1000000000 && isFPSVisible) {
				System.out.println("FPS: "+drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
		}
		System.out.println("GameLoop ended");
	}
	
	
	public void update() {
		player.update();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Standard way of repainting the screen. Above calls are standard.
		
		//Converting Graphics g into a Graphics2D object
		Graphics2D graphics = (Graphics2D)g;	//The paint brush
		
		tileManager.draw(graphics);
		player.draw(graphics);
		
		//Garbage collect
		graphics.dispose();
		
	}
	
	private void showFPS(long timer, int drawCount) {
		if(keyH.zeroPressed) {
			if(isFPSVisible) {
				isFPSVisible = false;
				System.out.println("Hide FPS");
			}else {
				isFPSVisible = true;
				System.out.println("Show FPS");
			}
		}
		
		
		
		keyH.zeroPressed=false;
		
	}
	private void waitForThread(Thread thread) {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
