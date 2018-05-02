package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

/**
 * Controls game logic for the PokemonFrame
 * @author Ethan Turner, Stephen Saxman, Cody Fetterman, Lloyd Shakespeare, Domanik Johnson
 *
 */
public class GameController {
	private Graphics2D g2;
	private BufferedImage bg;
	private ArrayList<BufferedImage> idleSpritesCharmander;
	private ArrayList<BufferedImage> walkingSpritesCharmander;
	private ArrayList<BufferedImage> attackingSpritesCharmander;
	
	private ArrayList<BufferedImage> walkingSpritesBulbasaur;
	private ArrayList<BufferedImage> attackingSpritesBulbasaur; 
	
	private ArrayList<BufferedImage> idleSpritesZubat;
	private ArrayList<BufferedImage> walkingSpritesZubat;
	
	private int playerX = 50, playerY = 50; //player position
	private int playerVelX = 0, playerVelY = 0; //player velocity
	private final int CHARACTER_CHARMANDER = 0, CHARACTER_BULBASAUR = 1, CHARACTER_ZUBAT = 2;
	private int currentCharacter = CHARACTER_CHARMANDER;
	//private double tickCounter = 0;
	private int idleCycleIndex = 0;
	private int walkCycleIndex = 0;
	private boolean LDirection = false;
	private boolean walking = false; 
	private boolean attacking = false;
	private int attackCycleIndex = 0;
	
	private final int GRAVITY = -1;
	
	private PokemonFrame frame;
	/**
	 * Constructor
	 * Runs initialization procedure and all that good stuff
	 * @param frame Target game frame to be controlled
	 */
	public GameController(PokemonFrame frame) {
		this.frame = frame;
		init();
	}
	
	public void loadGeometry() {
		
	}
	
	/**
	 * Reads in a Buffered Image taken from a png image. The whole sprite sheet isn't loaded in
	 * instead just a component specified by an x and y coordinate, and the width and height of the image. 
	 */
	private void loadSprites() {
		//TODO: Add sprites for other characters
		BufferedImageLoader loader = new BufferedImageLoader();
		BufferedImage spriteSheetCI = null;;
		BufferedImage spriteSheetCW = null;;
		BufferedImage spriteSheetCA = null;;
		BufferedImage spriteSheetBI = null;;
		BufferedImage spriteSheetBA = null;;
		BufferedImage spriteSheetBW = null;;
		BufferedImage spriteSheetZI = null;;
		BufferedImage spriteSheetZW = null;;
		
		
		try {
			spriteSheetCI = loader.loadImage("IdleCharmander.png");
			spriteSheetCW = loader.loadImage("Walking.png");
			spriteSheetCA = loader.loadImage("CharAttack.png");
			spriteSheetBW = loader.loadImage("walkingBulbasaurSprites.png");
			spriteSheetBA = loader.loadImage("attackingBulbasaurSprites.png");
			spriteSheetZI = loader.loadImage("idleZubatSprites.png");
			spriteSheetZW = loader.loadImage("movingZubatSprites.png");
			bg = loader.loadImage("bg.png");
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Sprites spritesCI = new Sprites(spriteSheetCI);
		//Array containing Charmander's idle sprites.
		idleSpritesCharmander = new ArrayList<BufferedImage>();
		idleSpritesCharmander.add(spritesCI.grabSprite(0, 0, 27, 27));
		idleSpritesCharmander.add(spritesCI.grabSprite(27, 0, 27, 27));
		idleSpritesCharmander.add(spritesCI.grabSprite(54, 0, 27, 27));
		idleSpritesCharmander.add(spritesCI.grabSprite(81, 0, 27, 27));
		idleSpritesCharmander.add(spritesCI.grabSprite(108, 0, 27, 27));
		idleSpritesCharmander.add(spritesCI.grabSprite(135, 0, 27, 27));
		idleSpritesCharmander.add(spritesCI.grabSprite(162, 0, 27, 27));
		idleSpritesCharmander.add(spritesCI.grabSprite(189, 0, 25, 27));
		
		Sprites spritesCW= new Sprites(spriteSheetCW);
		//Array containing Charmander's walking sprites. 
		walkingSpritesCharmander = new ArrayList<BufferedImage>();
		walkingSpritesCharmander.add(spritesCW.grabSprite(0, 0, 30, 26));
		walkingSpritesCharmander.add(spritesCW.grabSprite(30, 0, 30, 26));
		walkingSpritesCharmander.add(spritesCW.grabSprite(60, 0, 30, 26));
		walkingSpritesCharmander.add(spritesCW.grabSprite(90, 0, 30, 26));
		walkingSpritesCharmander.add(spritesCW.grabSprite(120, 0, 29, 26));
		walkingSpritesCharmander.add(spritesCW.grabSprite(149, 0, 30, 26));
		walkingSpritesCharmander.add(spritesCW.grabSprite(179, 0, 28, 26));
		walkingSpritesCharmander.add(spritesCW.grabSprite(207, 0, 29, 26));
		
		Sprites spritesCA = new Sprites(spriteSheetCA);
		//Array containing Charmander attaking sprites.
		attackingSpritesCharmander = new ArrayList<BufferedImage>();
		attackingSpritesCharmander.add(spritesCA.grabSprite(0, 0, 30, 25));
		attackingSpritesCharmander.add(spritesCA.grabSprite(30, 0, 30, 25));
		attackingSpritesCharmander.add(spritesCA.grabSprite(60, 0, 30, 25));
		attackingSpritesCharmander.add(spritesCA.grabSprite(90, 0, 30, 25));
		attackingSpritesCharmander.add(spritesCA.grabSprite(120, 0, 34, 25));
		attackingSpritesCharmander.add(spritesCA.grabSprite(154, 0, 31, 25));
		attackingSpritesCharmander.add(spritesCA.grabSprite(185, 0, 31, 25));
		attackingSpritesCharmander.add(spritesCA.grabSprite(216, 0, 31, 25));
		attackingSpritesCharmander.add(spritesCA.grabSprite(247, 0, 31, 25));
		attackingSpritesCharmander.add(spritesCA.grabSprite(278, 0, 31, 25));
		
		Sprites spritesBW = new Sprites(spriteSheetBW);
		//walk sprites for Bulbasaur
		walkingSpritesBulbasaur = new ArrayList<BufferedImage>();
		walkingSpritesBulbasaur.add(spritesBW.grabSprite(0, 0, 21, 21));
		walkingSpritesBulbasaur.add(spritesBW.grabSprite(21, 0, 25, 21));
		walkingSpritesBulbasaur.add(spritesBW.grabSprite(46, 0, 23, 21));
		
		Sprites spritesBA = new Sprites(spriteSheetBA);
		//attack sprites for Bulbasaur 
		attackingSpritesBulbasaur = new ArrayList<BufferedImage>();
		attackingSpritesBulbasaur.add(spritesBA.grabSprite(0, 0, 21, 19));
		attackingSpritesBulbasaur.add(spritesBA.grabSprite(21, 0, 23, 19));
		attackingSpritesBulbasaur.add(spritesBA.grabSprite(44, 0, 25, 19));
		attackingSpritesBulbasaur.add(spritesBA.grabSprite(69, 0, 26, 19));
		attackingSpritesBulbasaur.add(spritesBA.grabSprite(95, 0, 26, 19));
		attackingSpritesBulbasaur.add(spritesBA.grabSprite(121, 0, 22, 19));


		
		
		Sprites spritesZI = new Sprites(spriteSheetZI);
		//Idle Zubat sprites
		idleSpritesZubat = new ArrayList<BufferedImage>();
		idleSpritesZubat.add(spritesZI.grabSprite(0, 0, 17, 25));
		idleSpritesZubat.add(spritesZI.grabSprite(17, 0, 20, 25));
		idleSpritesZubat.add(spritesZI.grabSprite(37, 0, 20, 25));

		
		
		Sprites spritesZW = new Sprites(spriteSheetZW);
		//walk sprites for Zubat? idk
		walkingSpritesZubat = new ArrayList<BufferedImage>();
		walkingSpritesZubat.add(spritesZW.grabSprite(0, 0, 20, 25));
		walkingSpritesZubat.add(spritesZW.grabSprite(20, 0, 20, 25));
		walkingSpritesZubat.add(spritesZW.grabSprite(40, 0, 17, 25));



	}
	
	/**
	 * Initializes frame and content pane, adds key listener, and starts game engine 
	 */
	private void init(){
		loadSprites();
		loadGeometry();
		JLayeredPane panel = new JLayeredPane(){
			private static final long serialVersionUID = 1L;
			//draw stuff!
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g2 = (Graphics2D) g;
				//USAGE: g2.draw(image, xpos, ypos, null)
				g2.drawImage(bg, 0, 0, null);
				
				if(currentCharacter == CHARACTER_CHARMANDER) {
					//Probably more convoluted than need be. Reverses the image when player is facing left. Long variable names aren't fun... 
					if(LDirection) {
						if(!walking && !attacking) g2.drawImage(idleSpritesCharmander.get(idleCycleIndex), playerX + idleSpritesCharmander.get(idleCycleIndex).getWidth(), 
								playerY, -idleSpritesCharmander.get(idleCycleIndex).getWidth(), idleSpritesCharmander.get(idleCycleIndex).getHeight(), null);			
						if(walking) g2.drawImage(walkingSpritesCharmander.get(walkCycleIndex), playerX + walkingSpritesCharmander.get(walkCycleIndex).getWidth(), playerY, 
								-walkingSpritesCharmander.get(walkCycleIndex).getWidth(), walkingSpritesCharmander.get(walkCycleIndex).getHeight(), null);
						if(attacking) g2.drawImage(attackingSpritesCharmander.get(attackCycleIndex), playerX + attackingSpritesCharmander.get(attackCycleIndex).getHeight(), playerY, 
								-attackingSpritesCharmander.get(attackCycleIndex).getWidth(), attackingSpritesCharmander.get(attackCycleIndex).getHeight(), null);
						g2.dispose();
					}
					
					if(!walking && !attacking) g2.drawImage(idleSpritesCharmander.get(idleCycleIndex), playerX, playerY, null);			
					if(walking) g2.drawImage(walkingSpritesCharmander.get(walkCycleIndex), playerX, playerY, null);
					if(attacking) g2.drawImage(attackingSpritesCharmander.get(attackCycleIndex), playerX, playerY, null);
				}
				
				if(currentCharacter == CHARACTER_BULBASAUR) {
					if(LDirection) {
						//Bulbasaur attacks? 
						//Also no separate sprites for idle Bulbasaur, justing using the first image from walking sprites as a place holder.
						if(!walking && !attacking) g2.drawImage(walkingSpritesBulbasaur.get(0), playerX + walkingSpritesBulbasaur.get(0).getWidth(), playerY,
								-walkingSpritesBulbasaur.get(0).getWidth(), walkingSpritesBulbasaur.get(0).getHeight(), null);
						if(walking) g2.drawImage(walkingSpritesBulbasaur.get(walkCycleIndex), playerX + walkingSpritesBulbasaur.get(walkCycleIndex).getWidth(), playerY,
								-walkingSpritesBulbasaur.get(walkCycleIndex).getWidth(), walkingSpritesBulbasaur.get(walkCycleIndex).getHeight(), null);
						if(attacking) g2.drawImage(attackingSpritesBulbasaur.get(attackCycleIndex), playerX + attackingSpritesBulbasaur.get(attackCycleIndex).getWidth(), playerY, 
								-attackingSpritesBulbasaur.get(attackCycleIndex).getWidth(), attackingSpritesBulbasaur.get(attackCycleIndex).getHeight(), null);

						g2.dispose();
					}
					if(!walking && !attacking) g2.drawImage(walkingSpritesBulbasaur.get(0), playerX, playerY, null);
					if(walking) g2.drawImage(walkingSpritesBulbasaur.get(walkCycleIndex), playerX, playerY, null);
					if(attacking) g2.drawImage(attackingSpritesBulbasaur.get(attackCycleIndex), playerX, playerY, null);
				}
				
				if(currentCharacter == CHARACTER_ZUBAT) {
					if(LDirection) {
						//Going to add !flying too
						if(!walking) g2.drawImage(idleSpritesZubat.get(idleCycleIndex), playerX + idleSpritesZubat.get(idleCycleIndex).getWidth(), playerY,
								-idleSpritesZubat.get(idleCycleIndex).getWidth(), idleSpritesZubat.get(idleCycleIndex).getHeight(), null);
						if(walking) g2.drawImage(walkingSpritesZubat.get(walkCycleIndex), playerX + walkingSpritesZubat.get(walkCycleIndex).getWidth(), playerY,
								-walkingSpritesZubat.get(walkCycleIndex).getWidth(), walkingSpritesZubat.get(walkCycleIndex).getHeight(), null);
						g2.dispose();
					}
					if(!walking) g2.drawImage(idleSpritesZubat.get(2), playerX, playerY, null);
					if(walking) g2.drawImage(walkingSpritesZubat.get(walkCycleIndex), playerX, playerY, null);
				}
				g2.dispose();
			}
		};
		frame.setContentPane(panel);
		frame.addKeyListener(new KeyListener(){
			/**
			 * Not used
			 */
			public void keyTyped(KeyEvent e) {
				//Change character?
				if(e.getKeyCode()==KeyEvent.VK_C) {
					
				}
			}
			/**
			 * Runs game logic when the player presses a button on the keyboard
			 */
			public void keyPressed(KeyEvent e) {
				//	if the player presses the key that corresponds to VK_SPACE
				//  in this example, runs code written here (calls to other methods for example)
				if(e.getKeyCode()==KeyEvent.VK_SPACE){
					//TODO: Finish special moves. 
					//Special move: Charmander has fire ball, Zubat flies, Bulbasaur??
					int max = -1;
					if(currentCharacter == CHARACTER_CHARMANDER) max = attackingSpritesCharmander.size();
					else if(currentCharacter == CHARACTER_BULBASAUR) max = attackingSpritesBulbasaur.size();
					//else if(currentCharacter == CHARACTER_ZUBAT) max = walkingSpritesZubat.size();
					if (!walking && currentCharacter == CHARACTER_CHARMANDER || currentCharacter == CHARACTER_BULBASAUR) {
						attacking = true;
						attackCycleIndex++;
			
						if(attackCycleIndex > max-1) {
						attackCycleIndex = 0; 
						}
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_W){
					//probably won't be used
				}
				if(e.getKeyCode()==KeyEvent.VK_A){
					//move the player left (move the stage right)
					walkCycleIndex++;
					LDirection = true;
					walking = true;
					int max = -1;
					if(currentCharacter == CHARACTER_CHARMANDER) max = walkingSpritesCharmander.size();
					else if(currentCharacter == CHARACTER_BULBASAUR) max = walkingSpritesBulbasaur.size();
					else if(currentCharacter == CHARACTER_ZUBAT) max = walkingSpritesZubat.size();
					if(walkCycleIndex > max-1) {
						walkCycleIndex = 0;
					}
					playerX -= 3;
				}
				if(e.getKeyCode()==KeyEvent.VK_S){
					//make the player duck? (we might not use this)
				}
				if(e.getKeyCode()==KeyEvent.VK_D){
					//move the player right (move the stage left)
					walkCycleIndex++;
					LDirection = false;
					walking = true;
					int max = -1;
					if(currentCharacter == CHARACTER_CHARMANDER) max = walkingSpritesCharmander.size();
					else if(currentCharacter == CHARACTER_BULBASAUR) max = walkingSpritesBulbasaur.size();
					else if(currentCharacter == CHARACTER_ZUBAT) max = walkingSpritesZubat.size();
					if(walkCycleIndex > max-1) {
						walkCycleIndex = 0;
					}
					playerX += 3;
				} 
			}
			
			public void keyReleased(KeyEvent arg0) {
				walking = false;
				attacking = false;
				walkCycleIndex = 0;
				attackCycleIndex = 0;
				idleCycleIndex = 0;
			}
		});
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		run();
	}
	
	/**
	 * Repaints the frame to update sprite positions
	 */
	public void render() {
		frame.repaint();
	}
	
	/**
	 * Runs game logic that doesn't rely on user input, mostly geometry collisions
	 */
	public void tick() {
		//TODO: Implement gravity correctly
		//playerVelY -= GRAVITY; //add gravity
		playerY += playerVelY;
		if(playerY >= frame.getHeight() - walkingSpritesCharmander.get(0).getHeight()) { //don't move the sprite off the bottom of the screen
			playerY = frame.getHeight() - walkingSpritesCharmander.get(0).getHeight();
			playerVelY = 0;
		}
		//System.out.println("Vel: " + playerVelY + ", ");
		//System.out.println("Pos: " + playerX + ", " + playerY);
		
		//TODO: Idle animations 
		//Possible idle animation? Not sure where to put this. Works if you drag mouse cursor across the JFrame. Otherwise it is really laggy.
		//int max = -1;
		//if(currentCharacter == CHARACTER_CHARMANDER) max = idleSpritesCharmander.size();
		//else if(currentCharacter == CHARACTER_BULBASAUR) max = walkingSpritesBulbasaur.size();
		//else if(currentCharacter == CHARACTER_ZUBAT) max = walkingSpritesZubat.size();
		//tickCounter+=0.25;
		//if(tickCounter % 1 == 0)idleCycleIndex++;
		//if(idleCycleIndex > max-1) {
			//idleCycleIndex = 0;
			//tickCounter = 0;
		//}
	}
	
	/**
	 * Engine that runs the game logic
	 * Renders 60 frames per second (pc master race)
	 * Updates game logic at a rate of 60 ticks per second
	 */
	private void run(){
		long lastTick=System.nanoTime();
		long lastTimer=System.currentTimeMillis();
		double nsPerTick=1000000000D/60D;
		int frames=0;
		int ticks=0;
		double delta=0;
		while(true){
			long now=System.nanoTime();
			boolean shouldRender=false;
			delta+=(now-lastTick)/nsPerTick;
			lastTick=now;
			while(delta>=1){
				ticks++;
				tick();
				//System.out.println("Tick");
				delta-=1;
				shouldRender=true;
			}
			if(shouldRender){//render a new frame
				render();
				frames++;
			}
			if(System.currentTimeMillis()-lastTimer>=1000){//reset after 1 second
				lastTimer+=1000;
				System.out.println("Frames: "+frames+" Ticks: "+ticks);
				frames=0;
				ticks=0;
			}
		}
	}
}
