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
	
	private int playerX = 50, playerY = 50;
	private static final int CHARACTER_CHARMANDER = 0, CHARACTER_BULBASAUR = 1, CHARACTER_ZUBAT = 2;
	private int currentCharacter = CHARACTER_CHARMANDER;
	private int walkCycleIndex = 0;
	
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
	 * Reads in a Buffered Image taken from Walking.png. The whole sprite sheet isn't loaded in
	 * instead just a component specified by an x and y coordinate, and the width and height of the image. 
	 */
	private void loadSprites() {
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
	}
	
	/**
	 * Initializes frame and content pane, adds key listener, and starts game engine 
	 */
	private void init(){
		loadSprites();
		JLayeredPane panel = new JLayeredPane(){
			private static final long serialVersionUID = 1L;
			//draw stuff!
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g2 = (Graphics2D) g;
				//USAGE: g2.draw(image, xpos, ypos, null)
				//Used this to test if the correct image would be drawn to the screen. Must comment out run() to see.
				g2.drawImage(bg, 0, 0, null);
				g2.drawImage(walkingSpritesCharmander.get(walkCycleIndex), playerX, playerY, null); 

				//g2.dispose();
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
				}
				if(e.getKeyCode()==KeyEvent.VK_W){
					//probably won't be used
				}
				if(e.getKeyCode()==KeyEvent.VK_A){
					//move the player left (move the stage right)
				}
				if(e.getKeyCode()==KeyEvent.VK_S){
					//make the player duck? (we might not use this)
				}
				if(e.getKeyCode()==KeyEvent.VK_D){
					//move the player right (move the stage left)
					System.out.println("D pressed");
					walkCycleIndex++;
					int max = -1;
					if(currentCharacter == CHARACTER_CHARMANDER) max = walkingSpritesCharmander.size();
					else if(currentCharacter == CHARACTER_BULBASAUR) max = walkingSpritesBulbasaur.size();
					else if(currentCharacter == CHARACTER_ZUBAT) max = walkingSpritesZubat.size();
					if(walkCycleIndex > max) {
						walkCycleIndex = 0;
					}
				}
			}
			/**
			 * Not used
			 */
			public void keyReleased(KeyEvent arg0) {
			}
		});
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//run();
	}
	
	/**
	 * Repaints the frame to update sprite positions
	 */
	public void render() {
		//frame.repaint();
	}
	
	/**
	 * Runs game logic that doesn't rely on user input (we might not have any since we're not doing enemies)
	 */
	public void tick() {
		
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
				//tick();
				//System.out.println("Tick");
				delta-=1;
				shouldRender=true;
			}
			if(shouldRender){//render a new frame
				//render();
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
