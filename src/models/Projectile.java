package models;

import java.awt.Color;
import java.awt.Graphics;

import virusinvaders.WindowManager;

public class Projectile extends Model {

	public static final byte SIZE_X = 1;
	public static final byte SIZE_Y = 5;
	
	//Determine how fast and in what direction it's moving
	private int motion;
	
	public Projectile(int x, int y, int motion) {
		super("projectiles", x, y, SIZE_X * WindowManager.ZOOM, SIZE_Y * WindowManager.ZOOM);
		this.motion = motion;
	}

	public void move() {
		super.model.y += motion * WindowManager.ZOOM;
	}
	
	@Override
	public void draw(Graphics graphics) {
		//Projectiles don't use a texture, just a white rectangle
		graphics.setColor(Color.WHITE);
		graphics.fillRect(model.x, model.y, model.width, model.height);
	}
}
