package tiles;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gamePanel;
	Tiles[] tilesTerrain;
	
	int[][] tileGenerator;
	int[][] trees;
	
	

	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		tilesTerrain = new Tiles[20];
		tileGenerator = new int[12][16];
		trees = new int[12][16];
		
		initializeTilesContainer();
		
	}
	
	
	public void draw(Graphics2D graphics) {
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		int tile = 0;
		
		
			
		for(int i = 0; i<tileGenerator.length; i++) {
			
			for(int j = 0; j<tileGenerator[i].length; j++) {
				graphics.drawImage(tilesTerrain[tileGenerator[i][j]].tile, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
				
				if(trees[i][j]==1) {
					graphics.drawImage(tilesTerrain[3].tile, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
				}
				
				x += 48;
			}
			y +=48;
			x = 0;
		}
		
		
	}
	
	private void initializeTilesContainer() {
		tilesTerrain[0] = new Tiles();
		tilesTerrain[1] = new Tiles();
		tilesTerrain[2] = new Tiles();
		tilesTerrain[3] = new Tiles();
		tilesTerrain[4] = new Tiles();
		
	}
	
	
	public Thread getTileImage() {
		//Display loading message on Console
		System.out.println("Loading Tile Images");
		
		return new Thread(new LoadingImage());
	}
	
	
	
	
	
	class LoadingImage implements Runnable{

		@Override
		public void run() {
			
			try {
				tilesTerrain[0].tile = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
				tilesTerrain[1].tile = ImageIO.read(getClass().getResourceAsStream("/tiles/soil.png"));
				tilesTerrain[2].tile = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
				tilesTerrain[3].tile = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
				tilesTerrain[4].tile = ImageIO.read(getClass().getResourceAsStream("/tiles/overgrown_path.png"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Load tileGenerator
			Scanner scanMap = new Scanner(getClass().getResourceAsStream("/maps/map.txt"));
			for(int i = 0; scanMap.hasNextLine(); i++) {
				String line = scanMap.nextLine();
				String nums[] = line.split(" ");
				
				
				for(int j = 0; j<nums.length; j++) {
					tileGenerator[i][j] = Integer.parseInt(nums[j]);
				}
				
			}
			scanMap.close();
			
			//Load Trees
			Scanner scanTrees = new Scanner(getClass().getResourceAsStream("/maps/trees.txt"));
			for(int i = 0; scanTrees.hasNextLine(); i++) {
				String line = scanTrees.nextLine();
				String nums[] = line.split(" ");
				
				
				for(int j = 0; j<nums.length; j++) {
					trees[i][j] = Integer.parseInt(nums[j]);
				}
			}
			
			
			
			
		}
	}
}
