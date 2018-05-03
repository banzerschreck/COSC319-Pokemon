package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Holds information for each character in the game
 * @author Ethan Turner
 *
 */
public class Character {
	public ArrayList<BufferedImage> idleSprites;
	public ArrayList<BufferedImage> walkingSprites;
	public ArrayList<BufferedImage> attackingSprites;
	public int walkSpeed;
	public int jumpSpeed;
	
	public Character(ArrayList<BufferedImage> idleSprites, ArrayList<BufferedImage> walkingSprites, ArrayList<BufferedImage> attackingSprites, int walkSpeed, int jumpSpeed) {
		this.idleSprites = idleSprites;
		this.walkingSprites = walkingSprites;
		this.attackingSprites = attackingSprites;
		this.walkSpeed = walkSpeed;
		this.jumpSpeed = jumpSpeed;
	}
}
