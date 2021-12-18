package game;

import javax.swing.JFrame;

import resources.Constants;

public class Main {
	
/**** VARIABLES ****/
	
	public static View view;
	public static boolean game = true;
	
	
/**** METHODES ****/
	public static void main(String[] args) {
		// Cr�ation de la fen�tre de l'application
		JFrame frame = new JFrame("Virus Invaders");
		frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);	

		// Association du panneau Scene � la fen�tre
		view = new View();		
		frame.setContentPane(view);
		
		frame.setVisible(true);
	}

}
