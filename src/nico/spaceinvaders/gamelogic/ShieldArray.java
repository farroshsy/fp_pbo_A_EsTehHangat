package nico.spaceinvaders.gamelogic;

import java.awt.Graphics;

import nico.spaceinvaders.WindowManager;
import nico.spaceinvaders.models.ShieldTile;

public class ShieldArray {

	private ShieldTile[][][] shields;
	
	public ShieldArray() {
		//The 4 shields are made by 4x4 shield tiles
		this.shields = new ShieldTile[4][4][4];
		for(int i = 0; i < shields.length; i++) {
			for(int j = 0; j < shields[i].length; j++) {
				for(int k = 0; k < shields[i][j].length; k++) {
					this.shields[i][j][k] = new ShieldTile(WindowManager.LIMIT_LEFT + (i * ShieldTile.SIZE_X + k * 50 + 25) * WindowManager.ZOOM, (160 + j * ShieldTile.SIZE_Y) * WindowManager.ZOOM, i * ShieldTile.SIZE_X, j * ShieldTile.SIZE_Y);
				}
			}
		}
	}
	
	public void checkCollision(ProjectilesManager projectilesManager) {
		//Iterate through all shields
		for(int i = 0; i < shields.length; i++) {
			for(int j = 0; j < shields[i].length; j++) {
				for(int k = 0; k < shields[i][j].length; k++) {
					//If the shield tile has not been destroyed yet, check if it collides with a projectile
					if(!shields[i][j][k].isDestroyed()) {
						if(projectilesManager.checkCollision(shields[i][j][k])) {
							//If there is a collision the shield is damaged
							this.shields[i][j][k].damage();
						}
					}
				}
			}
		}
	}
	
	public void draw(Graphics graphics) {
		//Draw all shield tiles
		for(int i = 0; i < shields.length; i++) {
			for(int j = 0; j < shields[i].length; j++) {
				for(int k = 0; k < shields[i][j].length; k++) {
					//Shields are only drawn if they have not been destroyed
					if(!shields[i][j][k].isDestroyed()) {
						this.shields[i][j][k].draw(graphics);
					}
				}
			}
		}
	}
}
