package nico.spaceinvaders;

import java.awt.Dimension;

import javax.swing.JFrame;

public class WindowManager {

	public static final byte ZOOM = 3;
	
	public static final int WIDTH = 256 * ZOOM;
	public static final int HEIGHT = 224 * ZOOM;
	
	public static final int LIMIT_LEFT = 15 * ZOOM;
	public static final int LIMIT_RIGHT = WIDTH - 15 * ZOOM;
	
	public static final int LIMIT_UP = 30 * ZOOM;
	public static final int LIMIT_DOWN = HEIGHT - 20 * ZOOM;
	
	private final JFrame window;
	
	public WindowManager(Game gamePanel) {
		//Create the window
		this.window = new JFrame("Space Invaders");
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setResizable(false);
		
		//Set the game panel's size
		gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		//Add key listener to panel
		gamePanel.setFocusable(true);
		gamePanel.addKeyListener(gamePanel);
		
		//Add game panel to window
		this.window.setContentPane(gamePanel);
		
		//Needed for key listener
		gamePanel.requestFocusInWindow();
		
		//Set the window size as the size of its content
		this.window.pack();
	}
	
	public void setVisible() {
		this.window.setVisible(true);
	}
}