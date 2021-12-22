package models;

import java.awt.Graphics;

import virusinvaders.WindowManager;
import gamelogic.ProjectilesManager;

public class Virus extends Model {

	public static final byte SIZE_X = 12;
	public static final byte SIZE_Y = 8;
	
	//Determine wich sprite should be used
	private Type type;
	private byte animation;
	
	//Used to show death animation
	private byte deathTime;
	private byte deathDelay;
	
	public Virus(int x, int y, Type type) {
		super("Virus", x, y, SIZE_X * WindowManager.ZOOM, SIZE_Y * WindowManager.ZOOM);
		this.type = type;
		this.animation = 0;
		this.deathTime = -1;
		this.deathDelay = 20;
	}
	
	public Type getType() {
		return type;
	}
	
	public boolean isDead() {
		//Check if death animation is over
		return deathTime == deathDelay;
	}
	
	public void die() {
		//Start death animation
		this.deathTime = 0;
	}
	
	public void changeAnimation() {
		//Change movement animation by changing between frame 0 and frame 1
		this.animation = (byte) (1 - this.animation);
	}
	
	public void updateDeathAnimation() {
		//Updates death time if its between 0 and 20
		if(deathTime >= 0 && deathTime < deathDelay)
			this.deathTime++;
		//If deathTime is less than 0 death animation isn't displayed
	}
	
	public void move(int x, int y) {
		super.model.x += x;
		super.model.y += y;
	}
	
	public void shoot(ProjectilesManager projectilesManager) {
		//Add projectile to manager
		projectilesManager.add(model.x + model.width / 2, model.y + model.height, 2);
	}
	
	@Override
	public void draw(Graphics graphics) {
		if(deathTime < 0) //Virus is not dead, show movement animation
			super.draw(graphics, SIZE_X * animation, SIZE_Y * type.index, SIZE_X, SIZE_Y);
		else //Virus is dead, show death animation
			super.draw(graphics, SIZE_X * animation, SIZE_Y * 3, SIZE_X, SIZE_Y);
	}

	public enum Type {
		VIRUS1(0),
		VIRUS2(1),
		VIRUS3(2);
		
		public byte index;
		
		Type(int index) {
			this.index = (byte) index;
		}
	}
}
