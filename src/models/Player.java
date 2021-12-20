package models;

import java.awt.Graphics;

import virusinvaders.WindowManager;
import gamelogic.ProjectilesManager;

public class Player extends Model {
	
	public static final byte SIZE_X = 13;
	public static final byte SIZE_Y = 8;
	
	//Determine where ship should move
	private boolean left;
	private boolean right;
	
	//Give a delay to the shooting
	private byte shootTime;
	private byte shootDelay;
	
	//Lives
	private byte lives;

	//Used to show death animation
	private byte deathTime;
	private byte deathDelay;
	
	public Player() {
		super("Player", WindowManager.LIMIT_LEFT, WindowManager.LIMIT_DOWN - SIZE_Y * WindowManager.ZOOM, SIZE_X * WindowManager.ZOOM, SIZE_Y * WindowManager.ZOOM);
		this.left = false;
		this.right = false;
		this.shootTime = 0;
		this.shootDelay = 30;
		this.lives = 3;
		this.deathTime = -1;
		this.deathDelay = 50;
	}

	public void setLeft(boolean left) {
		//Tells the ship it should move left
		this.left = left;
	}
	
	public void setRight(boolean right) {
		//Tells the ship it should move right
		this.right = right;
	}
	
	public byte getLives() {
		return lives;
	}
	
	public void move() {
		//Ship can only move if it hasn't been hit
		if(deathTime < 0) {
			//Check if left or right are true and move ship accordingly
			//Also checks if ship is not going off screen
			if(left && model.x > WindowManager.LIMIT_LEFT) super.model.x -= 1 * WindowManager.ZOOM;
			if(right && model.x + model.width < WindowManager.LIMIT_RIGHT) super.model.x += 1 * WindowManager.ZOOM;
		}
	}
	
	public void adjustDelay() {
		//Update shoot delay if it's less than 30
		if(shootTime < shootDelay)
			this.shootTime++;
		
		//Updates death time if it's between 0 and 50
		if(deathTime >= 0 && deathTime < deathDelay)
			this.deathTime++;
		else
			this.deathTime = -1;
		//If deathTime is less than 0 death animation isn't displayed
	}
	
	public void shoot(ProjectilesManager projectilesManager) {
		//Player shoots if it's not in delay and if it hasn't been hit
		if(shootTime == shootDelay && deathTime < 0) {
			//Add a projectile to manager
			projectilesManager.add(model.x + model.width / 2, model.y - model.height, -3);
			this.shootTime = 0;
		}
	}
	
	public void checkCollision(ProjectilesManager projectilesManager) {
		//Check if player was hit by a projectile
		if(projectilesManager.checkCollision(this)) {
			//If so, remove one life and start death animation
			this.lives--;
			this.deathTime = 0;
		}
	}
	
	public void addLife() {
		this.lives++;
	}
	
	public boolean isDying() {
		return deathTime >= 0;
	}

	
	@Override
	public void draw(Graphics graphics) {
		if(deathTime < 0) //Ship is not dead, show regular sprite
			super.draw(graphics, 0, 0, SIZE_X, SIZE_Y);
		else if(deathTime % 2 == 0) //Alien is dead, show death animation (frame 0)
			super.draw(graphics, SIZE_X, 0, SIZE_X, SIZE_Y);
		else //Alien is dead, show death animation (frame 1)
			super.draw(graphics, SIZE_X * 2, 0, SIZE_X, SIZE_Y);
	}
}
