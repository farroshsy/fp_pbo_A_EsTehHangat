package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entities.Wall;
import entities.VirusesGroups;
import entities.Bonus;
import entities.VirusBullet;
import entities.PlayerBullet;
import entities.Player;
import resources.Time;
import resources.Keyboard;
import resources.Constants;

@SuppressWarnings("serial")
public class View extends JPanel {
	
/**** VARIABLES ****/	
	
	public Player player = new Player();
	public VirusesGroups virusesGroups = new VirusesGroups();
	public PlayerBullet playerBullet = new PlayerBullet();
	
	public Wall tabWall[] = new Wall[4]; // Cr�ation d'un tableau contenant les 4 ch�teaux
	
	public VirusBullet virusBullet1, virusBullet2, virusBullet3;
	
	public Bonus bonus;
	
	private Font scoreAppearence = new Font("Arial", Font.PLAIN, 20);
	private Font textAppearence1 = new Font("Arial", Font.PLAIN, 50);
	private Font textAppearence2 = new Font("Arial", Font.PLAIN, 80);
	
	public int score = 0;
	
/**** CONSTRUCTEUR ****/
	
	public View() {
		super();
		
		// Instanciation des ch�teaux		
		for(int column=0; column<4; column++) {
			this.tabWall[column] = new Wall(Constants.FRAME_MARGIN + 
					Constants.INIT_POS_X_WALL + column * (Constants.WALL_WIDTH + Constants.WALL_GAP));
		}
		
		
		// Instanciation du clavier
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(new Keyboard());
		
		// Instanciation du Time (� la fin du constructeur)
		Thread TimeEcran = new Thread(new Time());
		TimeEcran.start();
	}

		
/**** METHODES ****/
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2 = (Graphics2D) g;
		
		// Dessin du fond d''�cran
		g2.setColor(Color.PINK);
		g2.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		
		// Dessin ligne verte en bas de l'�cran
		g2.setColor(Color.RED);
		g2.fillRect(30, 530, 535, 5);
		
		// Affichage du score
		g.setFont(scoreAppearence);
		g.drawString("SCORE : " + score, 400, 25);
		
		// Dessin du player
		this.player.PlayerDesign(g2);
		
		// Dessin des aliens
		this.virusesGroups.VirusesDesign(g2);
		
		// Dessin du tir player
		this.playerBullet.designPlayerBullet(g2);
		
		// D�tection contact playerBullet avec alien
		this.virusesGroups.PlayerBulletTouchVirus(this.playerBullet);
		
		// Dessin des ch�teaux
		for(int column=0; column<4; column++) {this.tabWall[column].WallDesign(g2);}
		
		// Message de d�but du jeu
		if(Time.Tachometer < 500) {
			g.setFont(textAppearence1);
		    g.drawString("Shoot The Virus!", 100, 100);
		}
		
		// Affichage de la fin du jeu
		if(this.player.isLife() == false) {
			g.setFont(textAppearence2);
			g.drawString("GAME OVER", 50, 100);
		}
		
		// D�tection contact playerBullet avec ch�teau
		this.playerBullet.PlayerBulletDestroyedWall(tabWall);	
		
		// Dessin des tirs des aliens
		if(Time.Tachometer % 500 == 0) {
			virusBullet1 = new VirusBullet(this.virusesGroups.VirusBulletChoice());}
		if(this.virusBullet1 != null) {
			this.virusBullet1.VirusBulletDesign(g2);
			this.virusBullet1.VirusBulletDestroyWall(tabWall); // D�tection contact virusBullet1 avec ch�teau
			if(this.virusBullet1.TouchPlayer(player) == true) {this.player.setLife(false);}
		}
		if(Time.Tachometer % 750 == 0) {
			virusBullet2 = new VirusBullet(this.virusesGroups.VirusBulletChoice());}
		if(this.virusBullet2 != null) {
			this.virusBullet2.VirusBulletDesign(g2);
			this.virusBullet2.VirusBulletDestroyWall(tabWall); // D�tection contact virusBullet2 avec ch�teau
			if(this.virusBullet2.TouchPlayer(player) == true) {this.player.setLife(false);}
		}
		if(Time.Tachometer % 900 == 0) {
			virusBullet3 = new VirusBullet(this.virusesGroups.VirusBulletChoice());}
		if(this.virusBullet3 != null) {
			this.virusBullet3.VirusBulletDesign(g2);
			this.virusBullet3.VirusBulletDestroyWall(tabWall); // D�tection contact virusBullet3 avec ch�teau
			if(this.virusBullet3.TouchPlayer(player) == true) {this.player.setLife(false);}
		}
		// Dessin de la soucoupe		
		if(Time.Tachometer % 2500 == 0) {bonus = new Bonus();}		
		if(this.bonus != null) {
			if(this.bonus.getxPos()>0) {	
				// D�tection contact tir player avec soucoupe	
				if(this.playerBullet.destroyBonus(this.bonus) == true) {
					if(this.bonus.getDx() != 0) {this.score = this.score + Constants.BONUS_VALUE;}
					this.bonus.setDx(0);
					this.bonus.setLife(false);
					this.bonus.Bonusmusic.stop();
					this.bonus.BonusmusicDestruction.play();
				}
				this.bonus.BonusDesign(g2);
			}else {this.bonus = null;}
		}
		
		if(this.virusesGroups.getvirusNumber() == 0) {virusesGroups = new VirusesGroups();}
	
		if(this.virusesGroups.VirusLowestPosition() > Constants.POS_Y_PLAYER) {this.player.PlayerDestruction();}			
	}	
}
