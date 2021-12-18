package nico.spaceinvaders.gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import nico.spaceinvaders.utils.Backgrounds;

public class HighScoresMenu extends GameState {

	public HighScoresMenu(GameStateManager gameStateManager) {
		super(gameStateManager);
	}

	@Override
	public void init() {}

	@Override
	public void loop() {}

	@Override
	public void draw(Graphics graphics) {
		//Background drawing moved in another class to be more organized
		//This is what draws the high score list
		Backgrounds.drawHighScoresMenu(graphics);
	}

	@Override
	public void keyPressed(int keyCode) {
		//Go back to main menu
		if(keyCode == KeyEvent.VK_ESCAPE) {
			super.gameStateManager.backToPrevious();
		}
	}

	@Override
	public void keyReleased(int keyCode) {}
	
}
