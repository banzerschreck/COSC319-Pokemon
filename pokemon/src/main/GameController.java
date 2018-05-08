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
	//animation variables
	private Graphics2D g2;
	private BufferedImage bg;
	private ArrayList<BufferedImage> idleSpritesCharmander;
	private ArrayList<BufferedImage> walkingSpritesCharmander;
	private ArrayList<BufferedImage> attackingSpritesCharmander;
	
	private ArrayList<BufferedImage> idleSpritesBulbasaur;
	private ArrayList<BufferedImage> walkingSpritesBulbasaur;
	private ArrayList<BufferedImage> attackingSpritesBulbasaur; 
	
	private ArrayList<BufferedImage> idleSpritesZubat;
	private ArrayList<BufferedImage> walkingSpritesZubat;
	private ArrayList<BufferedImage> attackingSpritesZubat;
	
	private ArrayList<BufferedImage> playerSprites;
	private int playerSpritesIndex = 1;
	
	private int characterIndex = 0;
	private int tickCounter = 0;
	private int jumpCounter = 0;
	private boolean LDirection = false;
	private boolean walking = false; 
	private boolean attacking = false;
	private boolean flying = false;
	
	//these are kinda used for both so they're in the middle
	private Character charmander;
	private Character bulbasaur;
	private Character zubat;
	private Character currentChar;
	
	//movement variables
	private final int GRAVITY = -1;
	private final double ZUGRAVITY = -0.5;
	private int playerX = 50, playerY = 50; //player position
	private double playerVelY = 0; //player velocity
	private static final int PLAYER_MAX_VEL_Y = 30;
	
	//system variables
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
	
	/**
	 * Loads map data and boundary coordinates for collision detection
	 */
	public void loadGeometry() {
		//TODO: implement
	}
	
	/**
	 * Reads in a Buffered Image taken from a png image. The whole sprite sheet isn't loaded in
	 * instead just a component specified by an x and y coordinate, and the width and height of the image. 
	 */
	private void loadSprites() {
		//TODO: Add idle sprites for bulbasaur (placeholder is available, currently)
		//TODO: Add attack sprites for zubat (or design acceptable alternative sprites since maybe he doesn't attack idk)
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
		
		
		idleSpritesBulbasaur = new ArrayList<BufferedImage>();
		idleSpritesBulbasaur.add(walkingSpritesBulbasaur.get(0));
		attackingSpritesZubat = new ArrayList<BufferedImage>();
		attackingSpritesZubat.add(idleSpritesZubat.get(0));
	}
	
	/**
	 * Initializes frame, content pane, Character data, loads sprites, geometry info, adds key listener, and starts game engine 
	 */
	private void init(){
		loadSprites();
		
		charmander = new Character(idleSpritesCharmander, walkingSpritesCharmander, attackingSpritesCharmander, 5, 0);
		bulbasaur = new Character(idleSpritesBulbasaur, walkingSpritesBulbasaur, attackingSpritesBulbasaur, 3, 0);
		zubat = new Character(idleSpritesZubat, walkingSpritesZubat, attackingSpritesZubat, 7, 10);
		currentChar = charmander;
		playerSprites = currentChar.idleSprites;
		
		loadGeometry();
		JLayeredPane panel = new JLayeredPane(){
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g2 = (Graphics2D) g;
				g2.drawImage(bg, 0, 0, null);
				if(LDirection) {
					g2.drawImage(playerSprites.get(playerSpritesIndex), 
							playerX + playerSprites.get(playerSpritesIndex).getWidth(), 
							playerY, 
							-playerSprites.get(playerSpritesIndex).getHeight(),
							playerSprites.get(playerSpritesIndex).getHeight(),
							null);
				} else {
					g2.drawImage(playerSprites.get(playerSpritesIndex), playerX, playerY, null);
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
			}
			/**
			 * Runs game logic when the player presses a button on the keyboard
			 */
			public void keyPressed(KeyEvent e) {
				//TODO: Move all logic into tick()
				//		tick handles some animation advancing and does it without that weird pause when you type a key
				//      so if we move things like movement in there too, there shouldn't be any dumb pauses
				if(e.getKeyCode()==KeyEvent.VK_W){
					//TODO: Finish special moves. 
					//Special move: Charmander has fire ball, Zubat flies, Bulbasaur??
					attacking = true;
				}
				if(e.getKeyCode()==KeyEvent.VK_SPACE){
					if(currentChar== zubat) {
						flying = true;
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_A){
					LDirection = true;
					walking = true;
				}
				if(e.getKeyCode()==KeyEvent.VK_D){
					//move the player right
					LDirection = false;
					walking = true;
				} 
	
				if(e.getKeyCode()==KeyEvent.VK_S){
					
				}
				
				if(e.getKeyCode() == KeyEvent.VK_1) {
					currentChar = charmander;
				} else if(e.getKeyCode() == KeyEvent.VK_2) {
					currentChar = bulbasaur;
				} else if(e.getKeyCode() == KeyEvent.VK_3) {
					currentChar = zubat;
				}
			}
			
			public void keyReleased(KeyEvent arg0) {
				walking = false;
				attacking = false;
				flying = false;
				jumpCounter = 0;
				playerSpritesIndex = 0;
				playerSprites = currentChar.idleSprites;
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
	 * Runs game logic
	 */
	public void tick() {
		//TODO: move sprite and movement logic out of keyboard inputs and into here! Set bools that turn on/off based on input that activates code held here
		//TODO: implement attacks
		//TODO: implement geometry collision detection stuff
		if(currentChar != zubat) {
			playerVelY -= GRAVITY; //add gravity
		} else {
			playerVelY -= ZUGRAVITY;
		}
		if(playerVelY > PLAYER_MAX_VEL_Y) {
			playerVelY = PLAYER_MAX_VEL_Y;
		}
		int bottom = frame.getHeight() - walkingSpritesCharmander.get(0).getHeight();
		if(playerY + playerVelY + 50 >= bottom) { //don't move the sprite off the bottom of the screen
			playerY = bottom - 50;        
			playerVelY = 0;
		} else {
			playerY += playerVelY;
		}
		//advance animations
		int max = currentChar.idleSprites.size();
		tickCounter++;
		if(tickCounter > 4) {
			playerSpritesIndex++;
			tickCounter = 0;
		}
		if (playerSpritesIndex > max - 1) {
			playerSpritesIndex = 0;
		}
		if (walking && !LDirection) {
			playerSprites = currentChar.walkingSprites;
			tickCounter++;
			if (tickCounter > 4) {
				playerSpritesIndex++;
				tickCounter = 0; 
			}
			max = currentChar.walkingSprites.size();
			if(playerSpritesIndex > max-1) {
				playerSpritesIndex = 0;
			}
			//move the player
			playerX += currentChar.walkSpeed;
		}
		if (walking && LDirection) {
			//move the player left
			playerSprites = currentChar.walkingSprites;
			tickCounter++;
			if (tickCounter > 4) {
				playerSpritesIndex++;
				tickCounter = 0; 
			}
			max = currentChar.walkingSprites.size();
			if(playerSpritesIndex > max-1) {
				playerSpritesIndex = 0;
			}
			//move the player
			playerX -= currentChar.walkSpeed;
		}
		if(attacking && !walking) {
			playerSprites = currentChar.attackingSprites;
			max = currentChar.attackingSprites.size();
			if (!walking && (currentChar == charmander || currentChar == bulbasaur)) {
				tickCounter++;
				if (tickCounter > 6) {
					playerSpritesIndex++;
					tickCounter = 0; 
				}
				if(playerSpritesIndex > max-1) {
					playerSpritesIndex = 0; 
				}
			}
		}
		if(flying == true) {
			if(jumpCounter == 0) {
				if(currentChar == zubat) {
					jumpCounter++;
					playerVelY -= currentChar.jumpSpeed;
				}
			}
		}
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
