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
	private ArrayList<BufferedImage> walkingSpritesCharmander;
	private ArrayList<BufferedImage> walkingSpritesBulbasaur;
	private ArrayList<BufferedImage> walkingSpritesZubat;
	private ArrayList<BufferedImage> characterSprites;
	
	private int playerX = 50, playerY = 600; //player position
	private int playerVelX = 0, playerVelY = 0; //player velocity
	private static final int PLAYER_VEL_X_MAX = 3, PLAYER_VEL_Y_MAX = 3;
	private static final int PLAYER_ACCEL_X = 1;
	private static final int JUMP_VEL = 5;
	private int walkCycleIndex = 0;
	
	private static final int GRAVITY = -1;
	
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
	 * Reads in a Buffered Image taken from Walking.png. The whole sprite sheet isn't loaded in
	 * instead just a component specified by an x and y coordinate, and the width and height of the image. 
	 */
	private void loadSprites() {
		//TODO: Add sprites for other characters
		BufferedImageLoader loader = new BufferedImageLoader();
		BufferedImage spriteSheet = null;;
		try {
			spriteSheet = loader.loadImage("Walking.png"); 
			bg = loader.loadImage("bg.png");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Sprites sprites = new Sprites(spriteSheet);
		
		//This array contains each image pulled from Charmander's sprite sheet. 
		walkingSpritesCharmander = new ArrayList<BufferedImage>();
		walkingSpritesCharmander.add(sprites.grabSprite(0, 0, 30, 26));
		walkingSpritesCharmander.add(sprites.grabSprite(30, 0, 30, 26));
		walkingSpritesCharmander.add(sprites.grabSprite(60, 0, 30, 26));
		walkingSpritesCharmander.add(sprites.grabSprite(90, 0, 30, 26));
		walkingSpritesCharmander.add(sprites.grabSprite(120, 0, 29, 26));
		walkingSpritesCharmander.add(sprites.grabSprite(149, 0, 30, 26));
		walkingSpritesCharmander.add(sprites.grabSprite(179, 0, 28, 26));
		walkingSpritesCharmander.add(sprites.grabSprite(207, 0, 29, 26));
		
		//walk sprites for Bulbasaur
		walkingSpritesBulbasaur = new ArrayList<BufferedImage>();
		
		//walk sprites for Zubat? idk
		walkingSpritesZubat = new ArrayList<BufferedImage>();
		
		characterSprites = walkingSpritesCharmander;
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
				//Used this to test if the correct image would be drawn to the screen. Must comment out run() to see.
				g2.drawImage(bg, 0, 0, null);
				g2.drawImage(characterSprites.get(walkCycleIndex), playerX, playerY, null);
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
				//	if the player presses the key that corresponds to VK_SPACE
				//  in this example, runs code written here (calls to other methods for example)
				if(e.getKeyCode()==KeyEvent.VK_SPACE){
					//Make the player jump
					playerVelY = JUMP_VEL;
				}
				if(e.getKeyCode()==KeyEvent.VK_W){
					//probably won't be used
				} else if(e.getKeyCode()==KeyEvent.VK_A){
					//move the player left (move the stage right)
					walkCycleIndex++;
					int max = characterSprites.size();
					if(walkCycleIndex > max-1) {
						walkCycleIndex = 0;
					}
					playerVelX -= PLAYER_ACCEL_X;
					if (playerVelX <= -PLAYER_VEL_X_MAX) {
						playerVelX = -PLAYER_VEL_X_MAX;
					}
				} else if(e.getKeyCode()==KeyEvent.VK_S){
					//make the player duck? (we might not use this)
				} else if(e.getKeyCode()==KeyEvent.VK_D){
					//move the player right (move the stage left)
					walkCycleIndex++;
					int max = characterSprites.size();
					if(walkCycleIndex > max-1) {
						walkCycleIndex = 0;
					}
					playerVelX += PLAYER_ACCEL_X;
					if (playerVelX >= PLAYER_VEL_X_MAX) {
						playerVelX = PLAYER_VEL_X_MAX;
					}
				}
				
				if(e.getKeyCode()==KeyEvent.VK_1) {
					//Change char to charmander
					characterSprites = walkingSpritesCharmander;
				} else if (e.getKeyCode()==KeyEvent.VK_2) {
					//change char to Bulbasaur
					characterSprites = walkingSpritesBulbasaur;
				} else if (e.getKeyCode()==KeyEvent.VK_3) {
					//Change char to zubat
					characterSprites = walkingSpritesZubat;
				}
			}
			/**
			 * Runs when user releases a key on the keyboard
			 */
			public void keyReleased(KeyEvent e) {
				//display standing sprite when not moving
				walkCycleIndex = 0;
				playerVelX = 0;
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
		playerVelY -= GRAVITY; //add gravity
		if(playerVelY >= PLAYER_VEL_Y_MAX) {
			playerVelY = PLAYER_VEL_Y_MAX;
		}
		playerX += playerVelX;
		playerY += playerVelY;
		int bottom = frame.getHeight() - walkingSpritesCharmander.get(0).getHeight() * 3;
		if(playerY >= bottom) { //don't move the sprite off the bottom of the screen
			playerY = bottom;
			playerVelY = 0;
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
