package gamelogic;

import java.awt.Graphics;
import java.util.ArrayList;

import virusinvaders.WindowManager;
import models.Model;
import models.Projectile;
import utils.Audio;

public class ProjectilesManager {
	
	private ArrayList<Projectile> projectiles;
	
	public ProjectilesManager() {
		this.projectiles = new ArrayList<>();
	}
	
	public void add(int x, int y, int motion) {
		//Add a projectile to the list //Called when the player or an alien shoots
		this.projectiles.add(new Projectile(x, y, motion));
		Audio.playSound("/sound/PlayerShoot.wav");
	}
	
	public void move() {
		//Move all projectiles
		for(Projectile proj : projectiles) {
			proj.move();
		}
	}
	
	public void clearIfOffScreen() {
		//Remove projectiles from the list if they're off-screen
		for(int i = 0; i < projectiles.size(); i++) {
			if(projectiles.get(i).getPosY() < 0 || projectiles.get(i).getPosY() + Projectile.SIZE_Y > WindowManager.LIMIT_DOWN) {
				this.projectiles.remove(i);
			}
		}
	}
	
	public boolean checkCollision(Model model) {
		//Check if any of the projectiles in the list collides with given model
		for(int i = 0; i < projectiles.size(); i++) {
			if(projectiles.get(i).collidesWith(model)) {
				//If a collision has happened, projectile is removed from list
				this.projectiles.remove(i);
				return true;
			}
		}
		return false;
	}
	
	
	public void draw(Graphics graphics) {
		//Draw all projectiles in the list
		for(Projectile proj : projectiles) {
			proj.draw(graphics);
		}
	}
}
