package models;

import virusinvaders.WindowManager;
import gamelogic.ProjectilesManager;
import utils.Audio;

public class Corona extends Model {

	public static final byte SIZE_X = 18;
	public static final byte SIZE_Y = 10;

	//Determine how fast and in what direction it's moving
	private int motion;
	
	public Corona() {
		super("Corona", -SIZE_X * WindowManager.ZOOM, 3 * WindowManager.ZOOM, SIZE_X * WindowManager.ZOOM, SIZE_Y * WindowManager.ZOOM);
		this.motion = 0;
	}
	
	public void setMotion(int motion) {
		//Set the corona's motion for it to start moving
		this.motion = motion;
		
		//Also moves the corona to the left or to the right of the screen
		if(motion > 0) {
			super.model.x = -SIZE_X * WindowManager.ZOOM;
			Audio.playSound("/sound/Ambulance.wav");
		}
		else {
			super.model.x = WindowManager.WIDTH;
			Audio.playSound("/sound/Ambulance.wav");
		}
	}
	
	public void move() {
		//Move the corona
		super.model.x += this.motion * 2;
	}
	
	public boolean checkCollision(ProjectilesManager projectilesManager) {
		//Check if corona was hit
		if(projectilesManager.checkCollision(this)) {
			super.model.x = -SIZE_X * WindowManager.ZOOM;
			Audio.playSound("/sound/Destroyed.wav");
			return true;
		}
		return false;
	}
}
