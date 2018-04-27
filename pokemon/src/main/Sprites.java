package main;

import java.awt.image.BufferedImage;
/**
 * Fairly simple, just allows for a subsection of an image to be loaded in as a buffered image.
 * In this case it is used to pull one sprite from a sheet of sprites.
 * 
 */
public class Sprites {
	
	public BufferedImage spriteSheet;
	
	public Sprites(BufferedImage bi) {
		this.spriteSheet = bi;
	}
	
	public BufferedImage grabSprite(int x, int y, int width, int height) {
		BufferedImage sprite = spriteSheet.getSubimage(x, y, width, height);
		return sprite;
	}
}
