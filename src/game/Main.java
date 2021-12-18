package game;

import javax.swing.JFrame;

import resources.Constants;

public class Main {
	
/**** VARIABLES ****/
	
	public static View view;
	public static boolean game = true;
	
	
/**** METHODES ****/
	public static void main(String[] args) {
		// Création de la fenêtre de l'application
		JFrame frame = new JFrame("Virus Invaders");
		frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);	

		// Association du panneau Scene à la fenêtre
		view = new View();		
		frame.setContentPane(view);
		
		frame.setVisible(true);
	}

}
