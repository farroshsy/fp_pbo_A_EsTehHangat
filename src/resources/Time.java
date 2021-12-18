package resources;

import game.Main;

public class Time implements Runnable {

/**** VARIABLES ****/
	
	private final int PAUSE = 5; // temps d'attente entre 2 tours de boucle : 5 ms
	public static int Tachometer = 0;
	
	
/**** METHODES ****/
	
	@Override
	public void run() {		
		while(Main.game == true){ 
			Tachometer++;
			Main.view.repaint(); // Appel de la méthode PaintComponent de l'objet scene
			try {Thread.sleep(PAUSE);} // temps de pause (5 ms)
			catch (InterruptedException e) {}
		}
	}	
		
}