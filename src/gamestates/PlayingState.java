package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import gamelogic.InvaderManager;
import gamelogic.ProjectilesManager;
import gamelogic.ShieldArray;
import models.Player;
import utils.Audio;
import utils.Backgrounds;

public class PlayingState extends GameState {

	public PlayingState(GameStateManager gameStateManager) {
		super(gameStateManager);
	}

	private Player player;
	private InvaderManager invaders;
	private ProjectilesManager projectiles;
	private ShieldArray shields;
	
	private Random random;
	
	@Override
	public void init() {
		this.player = new Player();
		this.invaders = new InvaderManager();
		this.projectiles = new ProjectilesManager();
		this.shields = new ShieldArray();
		this.random = new Random();
		Audio.playSound("/sound/sansundertale.wav");

	}

	@Override
	public void loop() {
		//Player player logic
		this.player.move();
		this.player.adjustDelay();
		this.player.checkCollision(projectiles);

		//Check collision between shields and projectiles
		this.shields.checkCollision(projectiles);
		
		//The game temporarily stops if the player was hit
		if(!player.isDying()) {
			//virus logic
			this.invaders.move();
			this.invaders.shoot(random, projectiles);
			this.invaders.checkCollision(projectiles, random);
			
			//Red corona
			this.invaders.createCorona(random);
			
			//Projectiles logic
			this.projectiles.move();
			this.projectiles.clearIfOffScreen();
			
			//Check if the player has lost
			if(player.getLives() == 0 || invaders.haveLanded()) {
				super.gameStateManager.changeState(new GameOverState(gameStateManager, invaders.getScore()));
			}
		}
	}

	@Override
	public void draw(Graphics graphics) {
		//Background drawing moved in another class to be more organized
		Backgrounds.drawPlayingBackground(graphics, invaders.getScore(), player.getLives());
		
		//Draw all models
		this.player.draw(graphics);
		this.invaders.draw(graphics);
		this.projectiles.draw(graphics);
		this.shields.draw(graphics);
	}

	@Override
	public void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_ESCAPE) {
			//Go back to main menu
			super.gameStateManager.backToPrevious();
		}
		else if(keyCode == KeyEvent.VK_LEFT) {
			//Tell the player it should move left
			this.player.setLeft(true);
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			//Tell the player it should move right
			this.player.setRight(true);
		}
		else if(keyCode == KeyEvent.VK_SPACE) {
			//When the player shoots
			this.player.shoot(projectiles);
		}
	}

	@Override
	public void keyReleased(int keyCode) {
		if(keyCode == KeyEvent.VK_LEFT) {
			//player stops moving when key is released
			this.player.setLeft(false);
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			//player stops moving when key is released
			this.player.setRight(false);
		}
	}
}
