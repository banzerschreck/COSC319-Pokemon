package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PokemonFrame extends JFrame {

	private JPanel contentPane;
	private final String LOCATION = System.getProperty("user.dir");
	
	/**
	 * Initializes frame and content pane, adds key listener, and calls 
	 */
	private void init(){
		JLayeredPane panel = new JLayeredPane(){
			/**
			 * Creates a Graphics2D object used to draw all objects in the game
			 */
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				
				//g2.draw(image, xpos, ypos, null)
				
				g2.dispose();
			}
		};
		this.addKeyListener(new KeyListener(){
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
				}
			}
			/**
			 * Not used
			 */
			public void keyReleased(KeyEvent arg0) {				
			}
		});
		this.add(panel);
		run();
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
	/**
	 * Repaints the frame to update sprite positions
	 */
	private void render() {
		this.repaint();
	}
	/**
	 * Runs game logic that doesn't rely on user input (we might not have any since we're not doing enemies)
	 */
	private static void tick() {
		
	}

	/**
	 * Create the frame.
	 */
	public PokemonFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		init();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			/**
			 * Builds the JFrame and imports assets
			 */
			public void run() {
				try {
					//imagename = ImageIO.read(new File(LOCATION+"/stringpathto.png"));
					PokemonFrame frame = new PokemonFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
