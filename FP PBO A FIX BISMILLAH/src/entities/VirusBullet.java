package entities;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import game.Main;
import resources.Audio;
import resources.Time;
import resources.Constants;

public class VirusBullet extends Entity {

/**** VARIABLES ****/	
	
	Random chance = new Random();


/**** CONSTRUCTUR ****/	
	
	public VirusBullet(int [] tabPositionVirus) {
		
		// Initialisation des variables de la super classe
		super.xPos = tabPositionVirus[0] + Constants.VIRUS_WIDTH /2 - 1;
		super.yPos = tabPositionVirus[1] + Constants.VIRUS_HEIGHT;
		super.width = Constants.VIRUS_BULLET_WIDTH;
		super.height = Constants.VIRUS_BULLET_HEIGHT;
		super.dx = 0;
		super.dy = Constants.DY_VIRUS_BULLET;
		// Adresse des images du vaisseau
		super.strImg1 = "/images/VirusBullet1.png";
		super.strImg2 = "/images/VirusBullet2.png";
		super.strImg3 = "";
		// Chargement de l'image du tir de l'alien
		if(chance.nextInt(2) == 0) {
			super.ico = new ImageIcon(getClass().getResource(super.strImg1));}
		else {super.ico = new ImageIcon(getClass().getResource(super.strImg2));}
		super.img = this.ico.getImage();
	}
	
	
/**** METHODES ****/
	
	public int displacementVirusBullet() {
		if(Time.Tachometer % 4 == 0) {
			if(this.yPos < 600) {this.yPos = this.yPos + Constants.DY_VIRUS_BULLET;}			
		}
		return yPos;
	}	
	
	public void VirusBulletDesign(Graphics g) {
		g.drawImage(this.img, this.xPos, this.displacementVirusBullet(), null);
	}		
	
	private boolean VirusBulletWallHeight() { 
		// Renvoie vrai si le tir du vaisseau est à hauteur des châteaux
		if(this.yPos < Constants.POS_Y_WALL + Constants.WALL_HEIGHT && this.yPos + this.height > Constants.POS_Y_WALL) {return true;}
		else {return false;}	
	}
	
	private int nearWall() {
		// Renvoie le numéro du château (0,1,2 ou 3) dans la zone de tir du vaisseau
		int numberWall = -1;
		int colonne = -1;
		while (numberWall == -1 && colonne < 4) {
			colonne++;			
			if(this.xPos + this.width - 1 > Constants.FRAME_MARGIN + Constants.INIT_POS_X_WALL + colonne * (Constants.WALL_WIDTH + 
			Constants.WALL_GAP) 
			   && this.xPos + 1 < Constants.FRAME_MARGIN + Constants.INIT_POS_X_WALL + Constants.WALL_WIDTH + 
			   colonne * (Constants.WALL_WIDTH + Constants.WALL_GAP)) {	
				numberWall = colonne;
			}
		}
		return numberWall;
	}
	
	private int absiscontactVirusBulletWall(Wall wall) {		
		int xPosVirusBullet = -1;
		if(this.xPos + this.width > wall.getxPos() && this.xPos < wall.getxPos() + Constants.WALL_WIDTH){
			xPosVirusBullet = this.xPos;}					
		return xPosVirusBullet;
	}
	
	public int[] BulletVirusTouchWall() { // Renvoie numéro château touché et abscisse du tir
		int[] tabRep = {-1,-1}; 
		if(this.VirusBulletWallHeight() == true) { // Le tir alien est à hauteur du château		
			tabRep[0] = this.nearWall(); // enregistre le numéro du château touché dans tabRep[0]
			if(tabRep[0] != -1) {
				tabRep[1] = this.absiscontactVirusBulletWall(
				Main.view.tabWall[tabRep[0]]);}		 
		}		
		return tabRep;
	}	
	
	public void VirusBulletDestroyWall(Wall tabWall[]) {
		int[] tab = this.BulletVirusTouchWall(); // Contient (-1,-1) ou le numéro du château touché et l'abscisse du tir
		if(tab[0] != -1) { // Un château est touché
			if(tabWall[tab[0]].findHighBrick(tabWall[tab[0]].FindWallColumn(tab[1])) != -1
				&& tabWall[tab[0]].findHighBrick(tabWall[tab[0]].FindWallColumn(tab[1])) != 27) {
				tabWall[tab[0]].brokenHighBrick(tab[1]); // Détruit les briques du château touché									
				this.yPos = 700; // On tue le tir de l'alien
			}
		}
	}
	
	public boolean TouchPlayer(Player player) {
		// Renvoie vrai si un tirAlien touche le vaisseau
		if(this.yPos < player.getyPos() + player.getHeight() && this.yPos + this.height > player.getyPos() 
			&& this.xPos + this.width > player.getxPos() && this.xPos < player.getxPos() + player.getWidth()){
			    this.yPos = 700;
			    Audio.playSound("/sound/PlayerDestroyed.wav");
				return true;
			} 
		else{return false;}
	}
}
