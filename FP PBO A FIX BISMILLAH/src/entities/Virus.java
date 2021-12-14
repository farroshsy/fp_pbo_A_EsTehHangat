package entities;

import javax.swing.ImageIcon;

import resources.Constants;

public class Virus extends Entity {
	
/**** VARIABLES ****/
	
	
/**** CONSTRUCTOR ****/
	
	public Virus(int xPos, int yPos, String strImg1, String strImg2) {
		
		// Initialization variables super class
		super.xPos = xPos;
		super.yPos = yPos;
		super.width = Constants.VIRUS_WIDTH;
		super.height = Constants.VIRUS_HEIGHT;
		super.dx = 0;
		super.dy = 0;
		super.life = true;
		// Image address
		super.strImg1 = strImg1;
		super.strImg2 = strImg2;
		super.strImg3 = "/images/VirusDestroyed.png";
		// Address of the images of the Virus
		super.ico = new ImageIcon(getClass().getResource(super.strImg1));
		super.img = this.ico.getImage();
	}
	
	
/**** METHODS ****/
	public void choiceImage(boolean pos1) {
		// Method which loads the image of the virus according to its state and position (1 or 2)
		if(this.life == true) {
		 if(pos1 == true) {super.ico = new ImageIcon(getClass().getResource(strImg1));} 
		  else {super.ico = new ImageIcon(getClass().getResource(strImg2));}
		}
		else {super.ico = new ImageIcon(getClass().getResource(strImg3));}		
		super.img = this.ico.getImage(); // reload the image
	}

}
