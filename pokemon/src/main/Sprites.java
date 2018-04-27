package main;

import java.awt.image.BufferedImage;
/**
 * Fairly simple, just allows for a subsection of an image to be loaded in as a buffered image.
 * In this case it is used to pull one sprite from a sheet of sprites.
 * @author Cody Fetterman
 */
public class Sprites {
	private BufferedImage spriteSheet;
	/**
	 * Constructor.
	 * @param bi Uncut sprite sheet
	 */
	public Sprites(BufferedImage bi) {
		this.spriteSheet = bi;
	}
	
	/**
	 * Returns the sprite at the given dimensions of the sprite sheet
	 * @param x X coordinate to grab from
	 * @param y Y coordinate to grab from
	 * @param width Width of sprite being grabbed
	 * @param height Height of sprite being grabbed
	 * @return Sprite from sprite sheet
	 */
	public BufferedImage grabSprite(int x, int y, int width, int height) {
		BufferedImage sprite = spriteSheet.getSubimage(x, y, width, height);
		return sprite;
	}
}
