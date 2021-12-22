package models;

import java.awt.Graphics;

import virusinvaders.WindowManager;
import utils.Audio;

public class Shield extends Model {

	public static final byte SIZE_X = 5;
	public static final byte SIZE_Y = 5;
	
	//Determine what texture should be used for shape
	private byte shieldX;
	private byte shieldY;

	//Determine what texture should be used for damage level
	private byte damageLevel;
	
	public Shield(int x, int y, int shieldX, int shieldY) {
		super("shields", x, y, SIZE_X * WindowManager.ZOOM, SIZE_Y * WindowManager.ZOOM);
		this.shieldX = (byte) shieldX;
		this.shieldY = (byte) shieldY;
		this.damageLevel = 0;
	}

	public void damage() {
		//Increase damage level by 1
		if(damageLevel < 3)
			this.damageLevel++;
			Audio.playSound("/sound/BrickBroken.wav");
	}
	
	public boolean isDestroyed() {
		//If damage level is equal to 3 shield is not drawn on screen
		return damageLevel == 3;
	}
	
	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics, shieldX + 20 * damageLevel, shieldY, SIZE_X, SIZE_Y);
	}
}
