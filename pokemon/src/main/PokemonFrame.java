package main;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Displays the game.
 * @author Ethan Turner
 */
public class PokemonFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final int WINDOW_X = 800, WINDOW_Y = 600;
	private JPanel contentPane;
	BufferedImage sprite;

	/**
	 * Constructor for a new PokemonFrame
	 */
	public PokemonFrame(int posX, int posY, int width, int height) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(posX, posY, width, height);
		setResizable(false);
		setPreferredSize(new Dimension(WINDOW_X, WINDOW_Y));
	}
}
