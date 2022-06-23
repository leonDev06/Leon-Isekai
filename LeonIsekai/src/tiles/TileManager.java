package tiles;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gamePanel;
	Tiles[] tiles;

	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		tiles = new Tiles[20];
		
		tiles[0] = new Tiles();
		
		loadTilesFromRes();
		
		gamePanel.requestFocusInWindow();
		
	}
	
	
	public void draw(Graphics2D graphics) {
		
		graphics.drawImage(tiles[0].tile, 0, 0, gamePanel.tileSize, gamePanel.tileSize, null);
		
	}
	
	
	public void loadTilesFromRes() {
		
		
		try {
			tiles[0].tile = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gamePanel.requestFocus();
		gamePanel.grabFocus();
	}
	
}
