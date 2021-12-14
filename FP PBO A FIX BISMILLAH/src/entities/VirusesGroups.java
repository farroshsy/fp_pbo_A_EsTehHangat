package entities;

import java.awt.Graphics;
import java.util.Random;

import game.Main;
import resources.Audio;
import resources.Time;
import resources.Constants;

public class VirusesGroups {

/**** VARIABLES ****/	

	// Tableau contenant tous les aliens (50)
	private Virus tabVirus[][] = new Virus [5][10];
	private boolean goRight, pos1;
	private int speed;

	private int[] tabDeathVirus = {-1,-1}; // Emplacement alien mort dans tableau aliens
	
	Random chance = new Random();
	
	private int virusNumber = Constants.VIRUS_NUMBER;
	
	private int VirusSoundCounter = 0;
	
	
/**** CONSTRUCTEUR ****/
	
	public VirusesGroups() {	
		
		this.initArrayVirus();
		this.goRight = true;
		this.pos1 =true;
		this.speed = Constants.VIRUS_SPEED;
	}

	
/**** METHODES ****/
		
	private void initArrayVirus() {
		// Méthode qui remplit le tableau complet des aliens
		for(int column=0; column<10; column++) {
			this.tabVirus[0][column] = new Virus(Constants.INIT_POS_X_VIRUS + (Constants.VIRUS_WIDTH + Constants.VIRUS_COLUMN_GAP) * column, 
					Constants.VIRUS_ALT_INIT, "/images/rsz_bacteria_1.png", "/images/rsz_bacteria_1_rotate.png");
			for(int line=1; line<3; line++) {
				this.tabVirus[line][column] = new Virus(Constants.INIT_POS_X_VIRUS + (Constants.VIRUS_WIDTH + Constants.VIRUS_COLUMN_GAP) *
						column, Constants.VIRUS_ALT_INIT + Constants.VIRUS_LINE_GAP * line, "/images/rsz_virus_1_1.png", "/images/rsz_virus_1_rotate1.png");
			}
			for(int line=3; line<5; line++) {	
				this.tabVirus[line][column] = new Virus(Constants.INIT_POS_X_VIRUS + (Constants.VIRUS_WIDTH + Constants.VIRUS_COLUMN_GAP)
					* column, Constants.VIRUS_ALT_INIT + Constants.VIRUS_LINE_GAP * line, "/images/rsz_virus_4_1.png", "/images/rsz_virus_4_rotate.png");
			}	
		}
	}
	
	public void VirusesDesign(Graphics g) {
		if(Time.Tachometer % (100 - 10 * this.speed) == 0) {this.Virusesdisplacement();}
		// Dessin des aliens contenus dans le tableau tabVirus
		for(int column=0; column<10; column++) {
			for(int line=0; line<5; line++) {
				if(this.tabVirus[line][column] != null) {
					this.tabVirus[line][column].choiceImage(pos1);
					g.drawImage(this.tabVirus[line][column].getImg(), this.tabVirus[line][column].getxPos(), this.tabVirus[line][column].getyPos(),
						null);
				}
			}
		}		
	}
		
	private boolean leftEdgeKey() {
		// Méthode qui détecte le bord gauche de la fenêtre
		boolean response = false;
		for(int column=0; column<10; column++) {
			for(int line=0; line<5; line++) {
				if(this.tabVirus[line][column] != null) {
					if(this.tabVirus[line][column].getxPos() < Constants.FRAME_MARGIN){
						response = true;
						break;
					}
				}
			}
		}
		return response;
	}
	
	private boolean rightEdgeKey() {
		// Méthode qui détecte le bord droit de la fenêtre
		boolean response = false;
		for(int column=0; column<10; column++) {
			for(int line=0; line<5; line++) {
				if(this.tabVirus[line][column] != null) {
					if(this.tabVirus[line][column].getxPos() > 
					Constants.FRAME_WIDTH - Constants.VIRUS_WIDTH - Constants.DX_VIRUS - Constants.FRAME_MARGIN) {
						response = true;
						break;
					}
				}
			}
		}
		return response;
	}
	
	public void VirusTurnAndDescends() {
		// Méthode qui change le sens de déplacement de l'alien et le descend d'un cran
		if(this.rightEdgeKey() == true) {			
			for(int column=0; column<10; column++) {
				for(int line=0; line<5; line++) {
					if(this.tabVirus[line][column] != null) {
						this.tabVirus[line][column].setyPos(this.tabVirus[line][column].getyPos() + Constants.DY_VIRUS);
					}
				}
			}
			this.goRight = false;
			if(this.speed < 9) {this.speed++;}
		} else {
			if(this.leftEdgeKey() == true) {			
				for(int column=0; column<10; column++) {
					for(int line=0; line<5; line++) {
						if(this.tabVirus[line][column] != null) {
						this.tabVirus[line][column].setyPos(
							this.tabVirus[line][column].getyPos() + Constants.DY_VIRUS);
						}
					}
				}
				this.goRight = true;
				if(this.speed < 9) {this.speed++;}
			}
		}
	}
	
	public void Virusesdisplacement() {
		// Méthode qui gère le déplacement des aliens
		if(this.tabDeathVirus[0] != -1) { // Elimination de l'alien mort si nécessaire
			eliminateDeathVirus(tabDeathVirus);
			tabDeathVirus[0] = -1; // Réinitialisation de tabDeathVirus
		}
		if(this.goRight == true) { // Déplacement vers la droite
			for(int column=0; column<10; column++) {
				for(int line=0; line<5; line++) {	
					if(this.tabVirus[line][column] != null) {
						this.tabVirus[line][column].setxPos(this.tabVirus[line][column].getxPos() + Constants.DX_VIRUS);
					}
				}
			}
		}else{ // Déplacement vers la gauche
			for(int column=0; column<10; column++) {
				for(int line=0; line<5; line++) {
					if(this.tabVirus[line][column] != null) {
						this.tabVirus[line][column].setxPos(this.tabVirus[line][column].getxPos() - Constants.DX_VIRUS);
					}
				}
			}
		}
		// les aliens émettent un son
		this.playVirusSound();
		// Incrémentation du counter de son
		this.VirusSoundCounter++;
		// Changement de l'image de l'alien
		if(this.pos1 == true) {this.pos1 = false;} 
		else {this.pos1 = true;}
		// Màj du sens de déplacement si un alien atteint le bord de la fenêtre
		this.VirusTurnAndDescends();
	}
	
	public void PlayerBulletTouchVirus(PlayerBullet playerBullet) {
		// Détection contact playerBullet avec alien
		for(int column=0; column<10; column++) {
			for(int line=0; line<5; line++) {
				if(this.tabVirus[line][column] != null) {
					if(playerBullet.killVirus(this.tabVirus[line][column]) == true) {
						this.tabVirus[line][column].life = false; // On tue l'alien
						playerBullet.yPos = -1; // On tue le tir
						// On enregistre la position de l'alien mort dans le tableau
						this.tabDeathVirus[0] = line;
						this.tabDeathVirus[1] = column; 
						if(line == 0) {
							Main.view.score = Main.view.score + Constants.HIGH_VIRUS_VALUE;}
						else if(line>0 && line<3) {
							Main.view.score = Main.view.score + Constants.MIDDLE_VIRUS_VALUE;}
						else {
							Main.view.score = Main.view.score + Constants.LOW_VIRUS_VALUE;}	
						break;
					}
				}					
			}					
		}
	}

	private void eliminateDeathVirus(int[] tabDeathVirus) {
		// Méthode qui enlève l'alien mort du tableau (case à null)
		this.tabVirus[tabDeathVirus[0]][tabDeathVirus[1]] = null;
		this.virusNumber--;
	}
	
	public int[] VirusBulletChoice() {
		// Renvoie la position d'un alien tiré au hasard dans tabVirus en bas de sa 
		// column (line, column)
		int virusPosition[] = {-1,-1};		
		if(this.virusNumber != 0) { // On vérifie qu'il reste des aliens lifes
			do {int column = chance.nextInt(10); // On tire au hasard une column du 
			// tableau des aliens		
				for(int line=4;line>=0;line--) { // On cherche le 1er alien life 
				// en partant du bas
					if(tabVirus[line][column]!= null) {
						virusPosition[0] = this.tabVirus[line][column].getxPos();
						virusPosition[1] = this.tabVirus[line][column].getyPos();
						break;
					}
				}
			} while(virusPosition[0] == -1);
		}	
		return virusPosition;
	}
	
	private void playVirusSound() { // Méthode qui joue le son de l'alien (4 sound possibles)
		int counter = this.VirusSoundCounter % 4;
		if(counter==0) {Audio.playSound("/sound/Virus1.wav");}
		else if(counter==1) {Audio.playSound("/sound/Virus2.wav");}
		else if(counter==2) {Audio.playSound("/sound/Virus3.wav");}
		else {Audio.playSound("/sound/Virus4.wav");}
	}
	
	public int getvirusNumber() {return virusNumber;}
	
	public int VirusLowestPosition() {
		// Renvoie l'altitude des pieds de l'alien le plus bas
		int lowPos = 0, lowPosFinal = 0;
		for(int column=1; column<10;column++) {
			for(int line=4; line>=0;line--) {
				if(this.tabVirus[line][column] != null) {
					lowPos = this.tabVirus[line][column].yPos + this.tabVirus[line][column].height;
					break;
				}			
			}
			if(lowPos > lowPosFinal) {lowPosFinal = lowPos;}
		}
		return lowPosFinal;
	}
}