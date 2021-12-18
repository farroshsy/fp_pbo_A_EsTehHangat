package nico.spaceinvaders.gamestates;

import java.awt.Graphics;

public abstract class GameState {

	//Can be used to change state
	protected GameStateManager gameStateManager;
	
	public GameState(GameStateManager gameStateManager) {
		this.gameStateManager = gameStateManager;
		this.init();
	}
	
	//Initialize state //Called in game state contructor
	public abstract void init();
	
	//Handles game logic //Called in game class in actionPerformed
	public abstract void loop();

	//Handles rendering //Called in game class in paintComponent
	public abstract void draw(Graphics graphics);
	
	//Handles keyboard input
	public abstract void keyPressed(int keyCode);

	//Handles keyboard input
	public abstract void keyReleased(int keyCode);
}
