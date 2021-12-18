package nico.spaceinvaders.gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import nico.spaceinvaders.utils.Audio;
import nico.spaceinvaders.utils.Backgrounds;
import nico.spaceinvaders.utils.Resources;

public class GameOverState extends GameState {
	
	private int score;

	public GameOverState(GameStateManager gameStateManager, int score) {
		super(gameStateManager);
		//When game over state is created the new score is registered and saved to the file
		Resources.registerNewScore(score);
		Resources.writeHighScores();
		this.score = score;
		Audio.playSound("/sound/Ambulance.wav");
	}

	@Override
	public void init() {}

	@Override
	public void loop() {}

	@Override
	public void draw(Graphics graphics) {
		//Background drawing moved in another class to be more organized
		Backgrounds.drawGameOverScreen(graphics, score);
	}

	@Override
	public void keyPressed(int keyCode) {
		//Clear game state stack and go to main menu
		if(keyCode == KeyEvent.VK_ESCAPE) {
			super.gameStateManager.clearStack(new MainMenu(gameStateManager));
		}
	}

	@Override
	public void keyReleased(int keyCode) {}

}
