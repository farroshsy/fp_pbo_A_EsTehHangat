package virusinvaders;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import gamestates.GameStateManager;
import utils.Resources;
public class Game extends JPanel implements ActionListener, KeyListener {

	private final GameStateManager stateManager;
	
	public Game() {
		//Load resources
		Resources.readTextures();
		Resources.readHighScores();
		
		this.stateManager = new GameStateManager();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Inherited from ActionListener
		//Called by the timer in main class
		this.stateManager.loop();
	}

	@Override
	protected void paintComponent(Graphics g) {
		//Draw thing on the panel
		super.paintComponent(g);
		this.stateManager.draw(g);
		//Recursive function is called repetitively
		super.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//Called whenever a key is pressed
		this.stateManager.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Called whenever a key is released
		this.stateManager.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}
