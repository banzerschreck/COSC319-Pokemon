package main;

import java.awt.EventQueue;

/**
 * Holds the main method for the program.
 * Builds a new PokemonFrame object to launch the game.
 * @author Ethan Turner
 *
 */
public class GameLauncher {
	private static final int posX = 100, posY = 100, width = 500, height = 500;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			/**
			 * Builds the JFrame
			 */
			public void run() {
				try {
					//imagename = ImageIO.read(new File(LOCATION+"/stringpathto.png"));
					PokemonFrame frame = new PokemonFrame(0, 0, width, height);
					GameController controller = new GameController(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
