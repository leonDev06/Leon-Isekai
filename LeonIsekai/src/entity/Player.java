package entity;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	//GamePanel and KeyHandler Access
	GamePanel gp;
	KeyHandler keyHandler;
	
	//Images
	public BufferedImage up0, up1, up2, up3; 
	public BufferedImage left0, left1, left2, left3;
	public BufferedImage down0, down1, down2, down3;
	public BufferedImage right0, right1, right2, right3;
	
	//Number of each animation of this entity
	private final int animationMax = 4;
	
	//Constructors
	public Player(GamePanel gp, KeyHandler keyHandler) {
		this.gp=gp;
		this.keyHandler=keyHandler;
		
		setDefaultValues();
		getPlayerImage();
		
	}
	
	public void setDefaultValues() {
		x = 100;
		y = 100;
		speed = 4;
		direction="right";
	}
	
	public void update() {
		//Update the Sprite displayed
		spriteCount++;
		if(spriteCount > 6) {
			spriteNum++;
			if(spriteNum==animationMax) {
				spriteNum=0;
			}
			spriteCount=0;
		}
		
		//Determine if the character is idling
		if(!keyHandler.upPressed && !keyHandler.leftPressed && !keyHandler.downPressed && !keyHandler.rightPressed) {
			isIdle=true;
		}else {
			isIdle=false;
		}
		
		//Update the player position as well as direction facing
		if(keyHandler.upPressed) {
			direction = FACING_UP;
			y = y - speed;
		}
		if(keyHandler.leftPressed) {
			direction = FACING_LEFT;
			x -= speed;
		}
		if(keyHandler.downPressed) {
			direction = FACING_DOWN;
			y += speed;
		}
		if(keyHandler.rightPressed) {
			direction = FACING_RIGHT;
			x += speed;
		}
	}
	public void draw(Graphics2D graphics) {
		//The image to be displayed. Will change depending on direction + animation
		BufferedImage image = null;
		
		//Determine which direction the character is facing.
		//Loops through the corresponding array.
		switch(direction) {
		case FACING_UP:
			if(isIdle) {
				image = upImages[0];
			}else {
				image = upImages[spriteNum];
			}
			break;
		case FACING_LEFT:
			if(isIdle) {
				image = leftImages[0];
			}else {
				image = leftImages[spriteNum];
			}
			break;
		case FACING_RIGHT:
			if(isIdle) {
				image = rightImages[0];
			}else {
				image = rightImages[spriteNum];
			}
			break;
		case FACING_DOWN:
			if(isIdle) {
				image = downImages[0];
			}else {
				image = downImages[spriteNum];
			}
			break;
			default:
				System.out.println("Defaulted");
				break;
		}
		
		//Draw image on the screen
		graphics.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
	}
	
	public Thread getPlayerImage() {
		//Initialize Array Containers of each image for the animation
		//These are loaded in the LoadingImages runnable class
		upImages = new BufferedImage[4];
		leftImages = new BufferedImage[4];
		downImages = new BufferedImage[4];
		rightImages = new BufferedImage[4];
		
		//Load the image using a new thread
		return new Thread(new LoadingImage());
	}
	 
	public class LoadingImage implements Runnable{

		@Override
		public void run() {
			try {
				//Facing up images
				up0 = ImageIO.read(getClass().getResource("/player/player_up_0.png"));
				up1 = ImageIO.read(getClass().getResource("/player/player_up_1.png"));
				up2 = ImageIO.read(getClass().getResource("/player/player_up_2.png"));
				up3 = ImageIO.read(getClass().getResource("/player/player_up_3.png"));
				//Facing down images
				down0 = ImageIO.read(getClass().getResource("/player/player_down_0.png"));
				down1 = ImageIO.read(getClass().getResource("/player/player_down_1.png"));
				down2 = ImageIO.read(getClass().getResource("/player/player_down_2.png"));
				down3 = ImageIO.read(getClass().getResource("/player/player_down_3.png"));
				//Facing left images
				left0 = ImageIO.read(getClass().getResource("/player/player_left_0.png"));
				left1 = ImageIO.read(getClass().getResource("/player/player_left_1.png"));
				left2 = ImageIO.read(getClass().getResource("/player/player_left_2.png"));
				left3 = ImageIO.read(getClass().getResource("/player/player_left_3.png"));
				//Facing right images
				right0 = ImageIO.read(getClass().getResource("/player/player_right_0.png"));
				right1 = ImageIO.read(getClass().getResource("/player/player_right_1.png"));
				right2 = ImageIO.read(getClass().getResource("/player/player_right_2.png"));
				right3 = ImageIO.read(getClass().getResource("/player/player_right_3.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
			
			//load the images in their respective arrays
			loadImagesToArray();
			
			
			
			
		}
		private void loadImagesToArray() {
			upImages = new BufferedImage [] {up0,up1,up2,up3};
			leftImages = new BufferedImage[] {left0,left1,left2,left3};
			downImages = new BufferedImage[] {down0,down1,down2,down3};
			rightImages = new BufferedImage[] {right0,right1,right2,right3};
		}
		
	}
	
	
}
