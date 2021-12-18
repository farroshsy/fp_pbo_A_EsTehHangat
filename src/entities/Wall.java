package entities;

import java.awt.Color;
import java.awt.Graphics;

import resources.Audio;
import resources.Constants;

public class Wall extends Entity {

	/**** VARIABLES ****/
	
	private final int NUMBER_LINES = Constants.WALL_HEIGHT/Constants.BRICK_DIMENSION;
	private final int NUMBER_COLUMNS = Constants.WALL_WIDTH/Constants.BRICK_DIMENSION;
	
	// tableau contenant les briques du ch�teau (la case contient true pour une brique et false pour vide)
	boolean tabWall[][]= new boolean[NUMBER_LINES][NUMBER_COLUMNS]; 
	
/**** CONSTRUCTEUR ****/
	
	public Wall(int xPos) {
		super.xPos = xPos; // Abscisse du point le plus � gauche du ch�teau
		super.yPos = Constants.POS_Y_WALL; // Ordonn�e du sommet du ch�teau
		
		this.initTabWall();
	}
	
/**** METHODES ****/
	
	// Cr�ation du tableau initial associ� au ch�teau sans d�g�t
	public void initTabWall() {
		// On remplit toutes les cases du tableau avec true
		for(int line=0; line < NUMBER_LINES; line++) {
			for(int column=0; column < NUMBER_COLUMNS; column++) {
				tabWall[line][column]= true;
			}	
		}			
		// On remplit toutes les cases sans brique du tableau avec false	
		// Biseautage du haut du ch�teau
		for(int column=0; column < 6; column++) {
			for(int line=0; line < 2; line++) {
				tabWall[line][column]= false;
				tabWall[line][NUMBER_COLUMNS-column-1]= false;
			}
		}
		for(int column=0; column < 4; column++) {
			for(int line=2; line < 4; line++) {
				tabWall[line][column]= false;
				tabWall[line][NUMBER_COLUMNS-column-1]= false;
			}
		}
		for(int column=0; column < 2; column++) {
			for(int line=4; line < 6; line++) {
				tabWall[line][column]= false;
				tabWall[line][NUMBER_COLUMNS-column-1]= false;
			}
		}
		// Entr�e du ch�teau
		for(int line=18; line < NUMBER_LINES; line++) {
			for(int column=10; column < NUMBER_COLUMNS-10; column++) {
				tabWall[line][column]= false;
			}	
		}	
		// Biseautage entr�e du ch�teau
		for(int column=12; column < NUMBER_COLUMNS-12; column++) {
			for(int line=16; line < 18; line++) {
				tabWall[line][column]= false;
				tabWall[line][NUMBER_COLUMNS-column-1]= false;
			}
		}
		for(int column=14; column < NUMBER_COLUMNS-14; column++) {
			for(int line=14; line < 16; line++) {
				tabWall[line][column]= false;
				tabWall[line][NUMBER_COLUMNS-column-1]= false;
			}
		}
		for(int column=0; column < 2; column++) {
			for(int line=4; line < 6; line++) {
				tabWall[line][column]= false;
				tabWall[line][NUMBER_COLUMNS-column-1]= false;
			}
		}
	}
	
	// Dessin du ch�teau
	public void WallDesign(Graphics g2) {
		for(int line=0; line < NUMBER_LINES; line++) {
			for(int column=0; column < NUMBER_COLUMNS; column++) {
				if(tabWall[line][column]== true) {g2.setColor(Color.RED);
				} else {g2.setColor(Color.PINK);}
				g2.fillRect(this.xPos + Constants.BRICK_DIMENSION*column, this.yPos + Constants.BRICK_DIMENSION*line, Constants.BRICK_DIMENSION, Constants.BRICK_DIMENSION);
			}				
		}
	}
	
	public int FindWallColumn(int xMissile) {
		// Trouve la column du tableau associ� au ch�teau touch� par le tir
		int column = -1;
		column = (xMissile - this.xPos) / Constants.BRICK_DIMENSION;	
		return column;
	}
	
	public int findBrick(int column) {
		// Trouve la premi�re brique en paratnt du bas de la column du tableau associ� au ch�teau ou renvoie -1
		int line = NUMBER_LINES-1;
		while(line >= 0 && tabWall[line][column] == false) {line--;}		
		return line;		
	}
	
	private void removeBrick(int line, int column) {
		// Elimination des 6 premi�res briques de la column en partant du bas si elles existent
		for(int counter=0; counter < 6; counter++) {
			if(line - counter >= 0) {
				tabWall[line - counter][column] = false;
				if(column < NUMBER_COLUMNS - 1) {tabWall[line - counter][column + 1] = false;}
			}			
		}	
	}
	
	public void brokenBrick(int xTir) {
		// R�capitule les 3 m�thodes qui pr�c�dent
		Audio.playSound("/sound/BrickBroken.wav");
		int column = this.FindWallColumn(xTir);
		this.removeBrick(findBrick(column), column);
	}	
	
	public int findHighBrick(int column) {
		// Trouve la premi�re brique en partant du haut de la column du tableau 
		// associ� au ch�teau ou renvoie -1
		int line = 0;
		if(column != -1) {
		  while(line < NUMBER_LINES && tabWall[line][column] == false) {line++;}}			
		return line;		
	}
	
	private void removeHighBrick(int line, int column) {
		// Elimination des 6 premi�res briques de la column en partant du haut si elles existent
		for(int counter=0; counter < 6; counter++) {
			if(line + counter < NUMBER_LINES && column != -1) {
				tabWall[line + counter][column] = false;
				if(column < NUMBER_COLUMNS - 1) {
					tabWall[line + counter][column + 1] = false;}
			}			
		}	
	}
	
	public void brokenHighBrick(int xTir) {
		// R�capitule les 3 m�thodes qui pr�c�dent
		Audio.playSound("/sound/BrickBroken.wav");
		int column = this.FindWallColumn(xTir);
		this.removeHighBrick(findHighBrick(column), column);
	}	
}
