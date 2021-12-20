package models;

import java.awt.Graphics;
import java.awt.Rectangle;

import utils.Resources;

public class Model {

	//Determines the model's position and size
	protected Rectangle model;
	
	//Determines the model's texture
	protected String texture;
	
	public Model(String texture, int x, int y, int w, int h) {
		this.model = new Rectangle(x, y, w, h);
		this.texture = texture;
	}
	
	public int getPosX() {
		return model.x;
	}
	
	public int getPosY() {
		return model.y;
	}
	
	public int getWidth() {
		return model.width;
	}
	
	public int getHeight() {
		return model.height;
	}
	
	public boolean collidesWith(Model m) {
		//Check if two models (rectangles) intersect
		return model.intersects(m.model);
	}
	
	public void draw(Graphics graphics) {
		//Draw this model
		graphics.drawImage(Resources.getTexture(texture), model.x, model.y, model.width, model.height, null);
	}
	
	protected void draw(Graphics graphics, int uvx, int uvy, int uvw, int uvh) {
		//Draw this model //Used for animations
		graphics.drawImage(Resources.getTexture(texture, uvx, uvy, uvw, uvh), model.x, model.y, model.width, model.height, null);
	}
}