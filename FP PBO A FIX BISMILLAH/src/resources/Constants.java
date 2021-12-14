package resources;

public abstract class Constants {
	
	/************************************* FRAME *************************************/	
	// FRAME DIMENSION
	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 600;
	public static final int FRAME_MARGIN = 50;
	
	/************************************* PLAYER *************************************/	
	// PLAYER DIMENSION
	public static final int PLAYER_WIDTH = 39;
	public static final int PLAYER_HEIGHT = 24;
	
	// Initial position of the Player
	public final static int INIT_POS_X_PLAYER = (FRAME_WIDTH - PLAYER_WIDTH)/ 2;
	public final static int POS_Y_PLAYER = 490;	
	
	// Displacement Unit of the Player
	public final static int DX_PLAYER = 1;
	
	// Player Movement Limit
	public final static int LEFT_PLAYER_LIMIT = 60;
	public final static int RIGHT_PLAYER_LIMIT = 500;	
	
	/************************************* VIRUS  ***************************************/	
	// VIRUS DIMENSION
	public static final int VIRUS_WIDTH  = 33;
	public static final int VIRUS_HEIGHT = 25;	
	
	// Virus Position Settings
	public final static int VIRUS_ALT_INIT = 120;
	public final static int INIT_POS_X_VIRUS = 29 + FRAME_MARGIN;
	public final static int VIRUS_LINE_GAP = 40;
	public final static int VIRUS_COLUMN_GAP = 10;
	
	// Virus Movement Unit
	public final static int DX_VIRUS = 2;
	public final static int DY_VIRUS = 20;
	public final static int VIRUS_SPEED = 1;	
	
	// Total number of Virus
	public final static int VIRUS_NUMBER = 50;
	
	/************************************ Player Bullet **********************************/	
	// Bullet Dimensions
	public static final int PLAYER_BULLET_WIDTH = 3;
	public static final int PLAYER_BULLET_HEIGHT = 13;	
	
	// Displacement Unit of the Bullet
	public final static int DY_PLAYER_BULLET = 2;
	
	/************************************* WALL *************************************/
	// BRICK DIMENSION
	public static final int BRICK_DIMENSION = 2;
	
	// Dimensions of the wall (multiple dimension of the brick)
	public static final int WALL_WIDTH = 72;
	public static final int WALL_HEIGHT = 54;
		
	// Wall Position Settings
	public final static int POS_Y_WALL = 400;
	public final static int INIT_POS_X_WALL = 39;
	public final static int WALL_GAP = 42;
	
	/************************************ VIRUS BULLET ************************************/
	// Bullet Dimensions
	public static final int VIRUS_BULLET_WIDTH = 5;
	public static final int VIRUS_BULLET_HEIGHT = 15;	
	
	// Displacement Unit of the Virus
	public final static int DY_VIRUS_BULLET = 3;

	/************************************* Bonus *************************************/	
	// Bonus Dimensions
	public static final int BONUS_WIDTH = 42;
	public static final int BONUS_HEIGHT = 22;

	// Initial position of the Bonus
	public final static int INIT_POS_X_BONUS = FRAME_WIDTH;
	public final static int POS_Y_BONUS = 50;	

	// Displacement Unit of the Bonus
	public final static int DX_BONUS = 1;
	
	/************************************* POINTS *************************************/	
	// Points awarded for destroying virus
	public static final int HIGH_VIRUS_VALUE = 50;
	public static final int MIDDLE_VIRUS_VALUE = 40;
	public static final int LOW_VIRUS_VALUE = 20;
	public static final int BONUS_VALUE = 100;
}


