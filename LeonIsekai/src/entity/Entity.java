package entity;

import java.awt.image.BufferedImage;

public class Entity {
	//Entity's attributes with regards to movement
	public int x, y;
	public int speed;
	
	//Images Array. Images relative to the direction the entity is facing
	//Images are instantiated and loaded in each subclass. This allows each entity to have different number of animations per preference.
	public BufferedImage[] upImages;
	public BufferedImage[] leftImages;
	public BufferedImage[] downImages;
	public BufferedImage[] rightImages;
	
	//Direction the entity is facing
	public String direction;
	public final String FACING_UP = "up";
 	public final String FACING_DOWN = "down";
 	public final String FACING_LEFT = "left";
 	public final String FACING_RIGHT = "right";
 	
 	//Sprite changer Attributes
 	public int spriteNum = 0;
 	public int spriteCount = 0;
 	
 	//Entity Status
 	public boolean isIdle;
	
}
