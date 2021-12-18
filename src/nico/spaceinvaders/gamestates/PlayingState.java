package nico.spaceinvaders.gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import nico.spaceinvaders.gamelogic.InvaderManager;
import nico.spaceinvaders.gamelogic.ProjectilesManager;
import nico.spaceinvaders.gamelogic.ShieldArray;
import nico.spaceinvaders.models.PlayerShip;
import nico.spaceinvaders.utils.Audio;
import nico.spaceinvaders.utils.Backgrounds;

public class PlayingState extends GameState {

	public PlayingState(GameStateManager gameStateManager) {
		super(gameStateManager);
	}

	private PlayerShip ship;
	private InvaderManager invaders;
	private ProjectilesManager projectiles;
	private ShieldArray shields;
	
	private Random random;
	
	@Override
	public void init() {
		this.ship = new PlayerShip();
		this.invaders = new InvaderManager();
		this.projectiles = new ProjectilesManager();
		this.shields = new ShieldArray();
		this.random = new Random();
		Audio.playSound("/sound/sansundertale.wav");

	}

	@Override
	public void loop() {
		//Player ship logic
		this.ship.move();
		this.ship.adjustDelay();
		this.ship.checkCollision(projectiles);

		//Check collision between shields and projectiles
		this.shields.checkCollision(projectiles);
		
		//The game temporarily stops if the ship was hit
		if(!ship.isDying()) {
			//Aliens logic
			this.invaders.move();
			this.invaders.shoot(random, projectiles);
			this.invaders.checkCollision(projectiles, random);
			
			//Red ship
			this.invaders.createRedShip(random);
			
			//Projectiles logic
			this.projectiles.move();
			this.projectiles.clearIfOffScreen();
			
			//Check if the player has lost
			if(ship.getLives() == 0 || invaders.haveLanded()) {
				super.gameStateManager.changeState(new GameOverState(gameStateManager, invaders.getScore()));
			}
		}
	}

	@Override
	public void draw(Graphics graphics) {
		//Background drawing moved in another class to be more organized
		Backgrounds.drawPlayingBackground(graphics, invaders.getScore(), ship.getLives());
		
		//Draw all models
		this.ship.draw(graphics);
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
			//Tell the ship it should move left
			this.ship.setLeft(true);
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			//Tell the ship it should move right
			this.ship.setRight(true);
		}
		else if(keyCode == KeyEvent.VK_SPACE) {
			//When the player shoots
			this.ship.shoot(projectiles);
		}
	}

	@Override
	public void keyReleased(int keyCode) {
		if(keyCode == KeyEvent.VK_LEFT) {
			//Ship stops moving when key is released
			this.ship.setLeft(false);
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			//Ship stops moving when key is released
			this.ship.setRight(false);
		}
	}
}
