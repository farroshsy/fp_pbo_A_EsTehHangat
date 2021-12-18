package nico.spaceinvaders.gamelogic;

import java.awt.Graphics;
import java.util.Random;

import nico.spaceinvaders.WindowManager;
import nico.spaceinvaders.models.Alien;
import nico.spaceinvaders.models.RedShip;
import nico.spaceinvaders.utils.Audio;

public class InvaderManager {

	private Alien[][] aliens;
	private RedShip ship;
	
	//Determine of how many pixel at once the aliens move
	private int motionX = WindowManager.ZOOM * 3;
	private int motionY = WindowManager.ZOOM * 5;
	
	//Determine when aliens should make a 'step'
	private int moveDelay = 50;
	private int moveTime = 0;
	
	//Determine when an alien is chosen to shoot
	private int shootDelay = 30;
	private int shotTime = 0;
	
	//Player's score
	private int score = 0;
	
	//Number of aliens defeated
	private int defeated = 0;
	
	public InvaderManager() {
		this.aliens = new Alien[5][11];
		this.ship = new RedShip();
		this.resetAliens();
	}
	
	//Reinitialize aliens
	private void resetAliens() {
		for(int i = 0; i < aliens.length; i++) {
			for(int j = 0; j < aliens[i].length; j++) {
				if(i == 0)
					this.aliens[i][j] = new Alien(WindowManager.LIMIT_LEFT + j * (Alien.SIZE_X + 5) * WindowManager.ZOOM, WindowManager.LIMIT_UP + i * (Alien.SIZE_Y + 5) * WindowManager.ZOOM, Alien.Type.SQUID);
				else if(i == 1 || i == 2)
					this.aliens[i][j] = new Alien(WindowManager.LIMIT_LEFT + j * (Alien.SIZE_X + 5) * WindowManager.ZOOM, WindowManager.LIMIT_UP + i * (Alien.SIZE_Y + 5) * WindowManager.ZOOM, Alien.Type.CRAB);
				else
					this.aliens[i][j] = new Alien(WindowManager.LIMIT_LEFT + j * (Alien.SIZE_X + 5) * WindowManager.ZOOM, WindowManager.LIMIT_UP + i * (Alien.SIZE_Y + 5) * WindowManager.ZOOM, Alien.Type.OCTOPUS);
			}
		}
	}
	
	public void move() {
		this.moveTime++; //Every frame time increased by 1
		if(moveTime >= moveDelay) {
			this.moveAll(); //When time reaches 50 aliens are moved
			this.moveTime = 0; //And time is reset
			
			//If the aliens moved all the way across the screen they descend and invert their motion
			if(aliens[4][10].getPosX() + aliens[4][10].getWidth() > WindowManager.LIMIT_RIGHT || aliens[0][0].getPosX() < WindowManager.LIMIT_LEFT) {
				this.descend();
				this.motionX = - this.motionX;
			}
		}
		//Move red UFO
		this.ship.move();
	}
	
	//Moves all aliens sideways
	private void moveAll() {
		for(int i = 0; i < aliens.length; i++) {
			for(int j = 0; j < aliens[i].length; j++) {
				this.aliens[i][j].changeAnimation();
				this.aliens[i][j].move(motionX, 0);
			}
		}
	}
	
	//Moves all aliens down
	private void descend() {
		for(int i = 0; i < aliens.length; i++) {
			for(int j = 0; j < aliens[i].length; j++) {
				this.aliens[i][j].move(0, motionY);
			}
		}
	}
	
	public void shoot(Random random, ProjectilesManager projectilesManager) {
		if(shotTime < shootDelay) {
			//Every frame time is increased by 1
			this.shotTime++;
		} else {
			//When time reaches value of delay we pick a random alien and set a random delay
			this.chooseRandom(random, projectilesManager);
			this.shootDelay = random.nextInt(30) + 10;
			this.shotTime = 0;
			
		}
	}
	
	private void chooseRandom(Random random, ProjectilesManager projectilesManager) {
		//Choose a random alien
		int choiceI = random.nextInt(5);
		int choiceJ = random.nextInt(11);
		
		//If that alien is the last of the column or the last alive the alien can shoot
		if(choiceI == 4) {
			if(!aliens[choiceI][choiceJ].isDead())
				this.aliens[choiceI][choiceJ].shoot(projectilesManager);
		}
		else {
			if(aliens[choiceI+1][choiceJ].isDead() && !aliens[choiceI][choiceJ].isDead()) {
				this.aliens[choiceI][choiceJ].shoot(projectilesManager);
			}
			else {
				//If the alien can't shoot another one is chosen
				this.chooseRandom(random, projectilesManager);
			}
		}
	}
	
	public void checkCollision(ProjectilesManager projectilesManager, Random random) {
		for(int i = 0; i < aliens.length; i++) {
			for(int j = 0; j < aliens[i].length; j++) {
				if(!aliens[i][j].isDead()) {
					//If an alien is not dead we check if it collides with a projectile
					if(projectilesManager.checkCollision(aliens[i][j])) {
						//If it collides, the alien is set dead and score is increased
						this.aliens[i][j].die();
						this.defeated++;
						this.assignScore(aliens[i][j].getType());
						Audio.playSound("/sound/VirusKilled.wav");
					}
				}
			}
		}
		//Then we check if the red UFO collides with a projectile
		if(ship.checkCollision(projectilesManager)) {
			int[] randomBonus = new int[] {50, 100, 150};
			this.score += randomBonus[random.nextInt(randomBonus.length)];
		}
	}
	
	public int getScore() {
		return score;
	}
	
	private void assignScore(Alien.Type alienType) {
		//Assign a score depending on the type of alien defeated
		switch(alienType) {
		case OCTOPUS:
			this.score += 10;
			break;
		case CRAB:
			this.score += 20;
			break;
		case SQUID:
			this.score += 30;
			break;
		}
		//Every three aliens defeated the move delay is decreased so aliens become faster
		if(defeated % 3 == 0 && defeated != 0) {
			this.moveDelay--;
		}
		//When all aliens are defeated they are reset
		if(defeated % 55 == 0 && defeated != 0) {
			this.defeated = 0;
			this.resetAliens();
		}
	}
	
	public boolean haveLanded() {
		//Check if the lowest alien has landed
		return aliens[4][10].getPosY() >= 150 * WindowManager.ZOOM;
	}

	public void createRedShip(Random random) {
		//Every frame there is a 1 in 500 chances that the red UFO appears
		if(random.nextInt(500) == 0) {
			//Choose randomly if it should move from left to right or from right to left
			if(random.nextBoolean())
				this.ship.setMotion(3);
			else
				this.ship.setMotion(-3);
		}
	}
	
	public void draw(Graphics graphics) {
		//Draw all aliens
		for(int i = 0; i < aliens.length; i++) {
			for(int j = 0; j < aliens[i].length; j++) {
				if(!aliens[i][j].isDead()) {
					//Aliens are only drawn if they're not dead
					this.aliens[i][j].updateDeathAnimation();
					this.aliens[i][j].draw(graphics);
				}
			}
		}
		//Draw red UFO
		this.ship.draw(graphics);
	}
}
