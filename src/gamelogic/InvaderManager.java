package gamelogic;

import java.awt.Graphics;
import java.util.Random;

import virusinvaders.WindowManager;
import models.Virus;
import models.Corona;
import utils.Audio;

public class InvaderManager {

	private Virus[][] viruses;
	private Corona corona;
	
	//Determine of how many pixel at once the viruses move
	private int motionX = WindowManager.ZOOM * 3;
	private int motionY = WindowManager.ZOOM * 5;
	
	//Determine when viruses should make a 'step'
	private int moveDelay = 50;
	private int moveTime = 0;
	
	//Determine when an virus is chosen to shoot
	private int shootDelay = 30;
	private int shotTime = 0;
	
	//Player's score
	private int score = 0;
	
	//Number of viruses defeated
	private int defeated = 0;
	
	public InvaderManager() {
		this.viruses = new Virus[5][11];
		this.corona = new Corona();
		this.resetVirus();
	}
	
	//Reinitialize viruses
	private void resetVirus() {
		for(int i = 0; i < viruses.length; i++) {
			for(int j = 0; j < viruses[i].length; j++) {
				if(i == 0)
					this.viruses[i][j] = new Virus(WindowManager.LIMIT_LEFT + j * (Virus.SIZE_X + 5) * WindowManager.ZOOM, WindowManager.LIMIT_UP + i * (Virus.SIZE_Y + 5) * WindowManager.ZOOM, Virus.Type.VIRUS3);
				else if(i == 1 || i == 2)
					this.viruses[i][j] = new Virus(WindowManager.LIMIT_LEFT + j * (Virus.SIZE_X + 5) * WindowManager.ZOOM, WindowManager.LIMIT_UP + i * (Virus.SIZE_Y + 5) * WindowManager.ZOOM, Virus.Type.VIRUS2);
				else
					this.viruses[i][j] = new Virus(WindowManager.LIMIT_LEFT + j * (Virus.SIZE_X + 5) * WindowManager.ZOOM, WindowManager.LIMIT_UP + i * (Virus.SIZE_Y + 5) * WindowManager.ZOOM, Virus.Type.VIRUS1);
			}
		}
	}
	
	public void move() {
		this.moveTime++; //Every frame time increased by 1
		if(moveTime >= moveDelay) {
			this.moveAll(); //When time reaches 50 viruses are moved
			this.moveTime = 0; //And time is reset
			
			//If the viruses moved all the way across the screen they descend and invert their motion
			if(viruses[4][10].getPosX() + viruses[4][10].getWidth() > WindowManager.LIMIT_RIGHT || viruses[0][0].getPosX() < WindowManager.LIMIT_LEFT) {
				this.descend();
				this.motionX = - this.motionX;
			}
		}
		//Move red corona
		this.corona.move();
	}
	
	//Moves all viruses sideways
	private void moveAll() {
		for(int i = 0; i < viruses.length; i++) {
			for(int j = 0; j < viruses[i].length; j++) {
				this.viruses[i][j].changeAnimation();
				this.viruses[i][j].move(motionX, 0);
			}
		}
	}
	
	//Moves all viruses down
	private void descend() {
		for(int i = 0; i < viruses.length; i++) {
			for(int j = 0; j < viruses[i].length; j++) {
				this.viruses[i][j].move(0, motionY);
			}
		}
	}
	
	public void shoot(Random random, ProjectilesManager projectilesManager) {
		if(shotTime < shootDelay) {
			//Every frame time is increased by 1
			this.shotTime++;
		} else {
			//When time reaches value of delay we pick a random virus and set a random delay
			this.chooseRandom(random, projectilesManager);
			this.shootDelay = random.nextInt(30) + 10;
			this.shotTime = 0;
			
		}
	}
	
	private void chooseRandom(Random random, ProjectilesManager projectilesManager) {
		//Choose a random virus
		int choiceI = random.nextInt(5);
		int choiceJ = random.nextInt(11);
		
		//If that virus is the last of the column or the last alive the virus can shoot
		if(choiceI == 4) {
			if(!viruses[choiceI][choiceJ].isDead())
				this.viruses[choiceI][choiceJ].shoot(projectilesManager);
		}
		else {
			if(viruses[choiceI+1][choiceJ].isDead() && !viruses[choiceI][choiceJ].isDead()) {
				this.viruses[choiceI][choiceJ].shoot(projectilesManager);
			}
			else {
				//If the virus can't shoot another one is chosen
				this.chooseRandom(random, projectilesManager);
			}
		}
	}
	
	public void checkCollision(ProjectilesManager projectilesManager, Random random) {
		for(int i = 0; i < viruses.length; i++) {
			for(int j = 0; j < viruses[i].length; j++) {
				if(!viruses[i][j].isDead()) {
					//If a virus is not dead we check if it collides with a projectile
					if(projectilesManager.checkCollision(viruses[i][j])) {
						//If it collides, the virus is set dead and score is increased
						this.viruses[i][j].die();
						this.defeated++;
						this.assignScore(viruses[i][j].getType());
						Audio.playSound("/sound/VirusKilled.wav");
					}
				}
			}
		}
		//Then we check if the red corona collides with a projectile
		if(corona.checkCollision(projectilesManager)) {
			int[] randomBonus = new int[] {50, 100, 150};
			this.score += randomBonus[random.nextInt(randomBonus.length)];
		}
	}
	
	public int getScore() {
		return score;
	}
	
	private void assignScore(Virus.Type virusType) {
		//Assign a score depending on the type of virus defeated
		switch(virusType) {
		case VIRUS1:
			this.score += 10;
			break;
		case VIRUS2:
			this.score += 20;
			break;
		case VIRUS3:
			this.score += 30;
			break;
		}
		//Every three viruses defeated the move delay is decreased so viruses become faster
		if(defeated % 3 == 0 && defeated != 0) {
			this.moveDelay--;
		}
		//When all viruses are defeated they are reset
		if(defeated % 55 == 0 && defeated != 0) {
			this.defeated = 0;
			this.resetVirus();
		}
	}
	
	public boolean haveLanded() {
		//Check if the lowest virus has landed
		return viruses[4][10].getPosY() >= 180 * WindowManager.ZOOM;
	}

	public void createCorona(Random random) {
		//Every frame there is a 1 in 500 chances that the red corona appears
		if(random.nextInt(500) == 0) {
			//Choose randomly if it should move from left to right or from right to left
			if(random.nextBoolean())
				this.corona.setMotion(3);
			else
				this.corona.setMotion(-3);
		}
	}
	
	public void draw(Graphics graphics) {
		//Draw all viruses
		for(int i = 0; i < viruses.length; i++) {
			for(int j = 0; j < viruses[i].length; j++) {
				if(!viruses[i][j].isDead()) {
					//viruses are only drawn if they're not dead
					this.viruses[i][j].updateDeathAnimation();
					this.viruses[i][j].draw(graphics);
				}
			}
		}
		//Draw red corona
		this.corona.draw(graphics);
	}
}
