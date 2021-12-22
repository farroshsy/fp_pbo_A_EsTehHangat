package virusinvaders;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Main implements Runnable {

	private final Game game;
	private final WindowManager window;
	private final Timer timer;
	
	public static void main(String[] args) {
		//Launch the application
		SwingUtilities.invokeLater(new Main());
	}
	
	public Main() {
		//Initialize game
		this.game = new Game();
		
		//Create window
		this.window = new WindowManager(game);
		
		//The game loop will be run by this timer
		this.timer = new Timer(20, game);
	}

	@Override
	public void run() {
		//Set window visible and start the timer
		this.window.setVisible();
		this.timer.start();
	}
}
