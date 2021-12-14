package entities;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import resources.Audio;
import resources.Time;
import resources.Constants;

public class Bonus extends Entity {

	/**** VARIABLES ****/

	public Audio Bonusmusic = new Audio("/sound/Ambulance.wav");
	public Audio BonusmusicDestruction = new Audio("/sound/Destroyed.wav");

	private int counter = 0;
	
	
/**** CONSTRUCTEUR ****/
	
	public Bonus() {		
		// Initialisation des variables de la classe mère
		super.xPos = Constants.INIT_POS_X_BONUS;
		super.yPos = Constants.POS_Y_BONUS;
		super.width = Constants.BONUS_WIDTH;
		super.height = Constants.BONUS_HEIGHT;
		super.dx = Constants.DX_BONUS;
		super.dy = 0;
		// Adresse des images du vaisseau
		this.strImg1 = "/images/rsz_coronavirus_1.png";
		this.strImg2 = "/images/score.png";
		this.strImg3 = "";
		// Chargement de l'image du vaisseau
		super.ico = new ImageIcon(getClass().getResource(strImg1));
		super.img = this.ico.getImage();
		super.life = true;

		this.Bonusmusic.play();
		this.BonusmusicDestruction.stop();
		this.counter = 0;
	}
	
/**** METHODS ****/	
	
	public int Bonusdisplacement() {
		// Renvoie la nouvelle position de la soucoupe après déplacement éventuel
		if(this.life && Time.Tachometer % 2 == 0) {
			if (this.xPos > 0) {this.xPos = this.xPos - this.dx;}
			else {this.xPos = Constants.INIT_POS_X_BONUS;}	
		}
		return this.xPos;
	}
		
	public void BonusDesign(Graphics g) {	
		if(this.life == false) {this.Bonusdestruction();}
		g.drawImage(this.img, this.Bonusdisplacement(), this.yPos, null);			
	}	
	
	public void Bonusdestruction() {
		if(counter < 300) {
			super.ico = new ImageIcon(getClass().getResource(super.strImg2));
			super.img = this.ico.getImage();
			counter++;
		}else {this.xPos = Constants.INIT_POS_X_BONUS;}		
		
	}
}
