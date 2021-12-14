package entities;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import game.Main;
import resources.Audio;
import resources.Constants;

public class PlayerBullet extends Entity {
	
/**** VARIABLES ****/	
	
	private boolean PlayerBullets = false;
	

	/**** CONSTRUCTUR ****/	
	
	public PlayerBullet() {
		
		// Initialisation des variables de la super classe
		super.xPos = 0;
		super.yPos = Constants.POS_Y_PLAYER - Constants.PLAYER_BULLET_HEIGHT;
		super.width = Constants.PLAYER_BULLET_WIDTH;
		super.height = Constants.PLAYER_BULLET_HEIGHT;
		super.dx = 0;
		super.dy = Constants.DY_PLAYER_BULLET;
		// Adresse des images du vaisseau
		super.strImg1 = "/images/PlayerBullet.png";
		super.strImg2 = "";
		super.strImg3 = "";
		// Chargement de l'image du vaisseau
		super.ico = new ImageIcon(getClass().getResource(super.strImg1));
		super.img = this.ico.getImage();
	}
	
	/**** METHODES ****/
	public boolean isPlayerBullets() {return PlayerBullets;}

	public void setPlayerBullets(boolean PlayerBullets) {this.PlayerBullets = PlayerBullets;}

	public int deplacementPlayerBullet() {
		if(this.PlayerBullets == true) {
			if(this.yPos > 0) {this.yPos = this.yPos - Constants.DY_PLAYER_BULLET;}
			else {this.PlayerBullets = false;}
		}		
		return yPos;
	}
	
	public void designPlayerBullet(Graphics g) {
		if(this.PlayerBullets == true) {
			g.drawImage(this.img, this.xPos, this.deplacementPlayerBullet(), null);}	
	}
	
	public boolean killVirus(Virus virus) {
		// le tir du vaisseau détruit un alien
		if(this.yPos < virus.getyPos() + virus.getHeight() 
			&& this.yPos + this.height > virus.getyPos() 
			&& this.xPos + this.width > virus.getxPos() 
			&& this.xPos < virus.getxPos() + virus.getWidth()){
			Audio.playSound("/sound/VirusKilled.wav");
				return true;
			} 
		else{return false;}
	}
	
	private boolean BulletPlayerWallHeight() { 
		// Renvoie vrai si le tir du vaisseau est à hauteur des châteaux
		if(this.yPos < Constants.POS_Y_WALL + Constants.WALL_HEIGHT && this.yPos + this.height > Constants.POS_Y_WALL) {return true;}
		else {return false;}	
	}
	
	private int nearWall() {
		// Renvoie le numéro du château (0,1,2 ou 3) dans la zone de tir du vaisseau
		int numberWall = -1;
		int column = -1;
		while (numberWall == -1 && column < 4) {
			column++;			
			if(this.xPos + this.width > Constants.FRAME_MARGIN + Constants.INIT_POS_X_WALL + column * 
					(Constants.WALL_WIDTH + Constants.WALL_GAP) 
			   && this.xPos < Constants.FRAME_MARGIN + Constants.INIT_POS_X_WALL + Constants.WALL_WIDTH + column * 
			   (Constants.WALL_WIDTH + Constants.WALL_GAP)) {	
				numberWall = column;
			}
		}
		return numberWall;
	}
		
	private int absiscontactBulletWall(Wall wall) {
		// Renvoie l'abscisse du tir du vaisseau lors du contact avec un château
		int xPosPlayerBullet = -1;
		if(this.xPos + this.width > wall.getxPos() && this.xPos < wall.getxPos() + Constants.WALL_WIDTH){xPosPlayerBullet = this.xPos;}	
		return xPosPlayerBullet;
	}
	
	public int[] WallBulletTouch() {
		// Renvoie numéro château touché et abscisse du tir
		int[] tabRep = {-1, -1}; 
		if(this.BulletPlayerWallHeight() == true) { // Le tir du vaisseau est à hauteur du château		
			tabRep[0] = this.nearWall(); // enregistre le numéro du château touché dans tabRep[0]
			if(tabRep[0] != -1) {
				//enregistre l'abscisse du tir du vaisseau lors du contact avec le château dans tabRep[1]
				tabRep[1] = this.absiscontactBulletWall(Main.view.tabWall[tabRep[0]]);
			}		 
		}		
		return tabRep;
	}
	
	public void PlayerBulletDestroyedWall(Wall tabWall[]) {
		int[] tab = this.WallBulletTouch(); // Contient (-1,-1) ou le numéro du château touché et l'abscisse du contact tirVaisseau et château
		if(tab[0] != -1) { // Un château est touché
			if(tabWall[tab[0]].findBrick(tabWall[tab[0]].FindWallColumn(tab[1])) != -1) {
				tabWall[tab[0]].brokenBrick(tab[1]); // Détruit les briques du château touché									
				this.yPos = -1; // On tue le tir et on réactive le canon du vaisseau
			}
		}
	}
	
	public boolean destroyBonus(Bonus bonus) { 
		// Contact missile avec la soucoupe
		if(this.yPos < bonus.getyPos() + bonus.getHeight() && this.yPos + this.height > bonus.getyPos() 
			&& this.xPos + this.width > bonus.getxPos() && this.xPos < bonus.getxPos() + bonus.getWidth()){
				this.PlayerBullets = false; // On tue le tir
				return true;
			} 
		else{return false;}
	}
}
