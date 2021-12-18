package nico.spaceinvaders.gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import nico.spaceinvaders.utils.Audio;
import nico.spaceinvaders.utils.Backgrounds;

public class MainMenu extends GameState {

	private String[] optionsMenu;
	private int selected;
	
	public MainMenu(GameStateManager gameStateManager) {
		super(gameStateManager);
		Audio.playSound("/sound/Ambulance.wav");
	}
	
	@Override
	public void init() {
		this.optionsMenu = new String[] {"Play", "Highscores", "Quit"};
		this.selected = 0;
	}
	
	@Override
	public void loop() {}

	@Override
	public void draw(Graphics graphics) {
		//Background drawing moved in another class to be more organized
		Backgrounds.drawMainMenu(graphics, optionsMenu, selected);
	}

	@Override
	public void keyPressed(int keyCode) {
		//Move between menu options with arrow keys and select with enter
		if(keyCode == KeyEvent.VK_UP) {
			this.selected--; //Go up in the selection
			if(selected < 0)
				this.selected = this.optionsMenu.length - 1;
		}
		else if(keyCode == KeyEvent.VK_DOWN) {
			this.selected++; //Go down in the selection
			if(selected == optionsMenu.length)
				this.selected = 0;
		}
		else if(keyCode == KeyEvent.VK_ENTER) {
			switch(selected) {
			case 0: //Start game
				super.gameStateManager.changeState(new PlayingState(gameStateManager));
				break;
			case 1: //High scores
				super.gameStateManager.changeState(new HighScoresMenu(gameStateManager));
				break;
			case 2: //Quit
				System.exit(0);
				break;
			}
		}
		else if(keyCode == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(int keyCode) {}

}
