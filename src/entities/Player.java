package entities;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import game.Main;
import resources.Time;
import resources.Constants;

public class Player extends Entity {
	
	/**** VARIABLES ****/
	private int counter = 0;
	
	/**** CONSTRUCTEUR ****/
		
		public Player() {
			
			// Initialization of the variables of the super class
			super.xPos = Constants.INIT_POS_X_PLAYER;
			super.yPos = Constants.POS_Y_PLAYER;
			super.width = Constants.PLAYER_WIDTH;
			super.height = Constants.PLAYER_HEIGHT;
			super.dx = 0;
			super.dy = 0;
			// Address of the images of the Player
			super.strImg1 = "/images/rsz_cell.png";
			super.strImg2 = "/images/ancur1.png";
			super.strImg3 = "/images/ancur2.png";
			// Loading the Player image
			super.ico = new ImageIcon(getClass().getResource(super.strImg1));
			super.img = this.ico.getImage();
			super.life = true;
		}
		
		
	/**** METHODS ****/
	public int DisplacementPlayer() {
		// Returns the new position of the Player after eventual displacement
		if(this.dx < 0){if(this.xPos > Constants.LEFT_PLAYER_LIMIT) {this.xPos = this.xPos + this.dx;}
		}else if(dx > 0) {if(this.xPos + this.dx < Constants.RIGHT_PLAYER_LIMIT) {this.xPos = this.xPos + this.dx;}}
		return this.xPos;
	}
	
	public void PlayerDesign(Graphics g) {
		if(this.life == false) {this.PlayerDestruction();}
		g.drawImage(this.img, this.DisplacementPlayer(), this.yPos, null);
	}
	
	public void PlayerDestruction() {
		if(counter < 300) {
			if(Time.Tachometer % 2 == 0) {super.ico = new ImageIcon(getClass().getResource(super.strImg2));}
			else {super.ico = new ImageIcon(getClass().getResource(super.strImg3));}
			counter++;
		}else {Main.game = false;}		
		super.img = this.ico.getImage();
	}
}
