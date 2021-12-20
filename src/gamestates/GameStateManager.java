package gamestates;

import java.awt.Graphics;
import java.util.Stack;

public class GameStateManager {

	private final Stack<GameState> states;
	
	public GameStateManager() {
		//Initialize stack and create main menu
		this.states = new Stack<GameState>();
		this.states.add(new MainMenu(this));
	}
	
	public void changeState(GameState newState) {
		//Change state //Add a new state to the stack
		this.states.add(newState);
	}
	
	public void backToPrevious() {
		//Removes a state from the top of the stack (if it's not the only state in the stack)
		if(states.size() > 1)
			this.states.pop();
	}
	
	public void clearStack(GameState newState) {
		//Clears stack before adding a new state
		this.states.clear();
		this.states.add(newState);
	}
	
	public void loop() {
		//Calls 'loop' for the state on top of the stack
		this.states.peek().loop();
	}
	
	public void draw(Graphics g) {
		//Calls 'draw' for the state on top of the stack
		this.states.peek().draw(g);
	}
	
	public void keyPressed(int keyCode) {
		//Calls 'keyPressed' for the state on top of the stack
		this.states.peek().keyPressed(keyCode);
	}
	
	public void keyReleased(int keyCode) {
		//Calls 'keyReleased' for the state on top of the stack
		this.states.peek().keyReleased(keyCode);
	}
}
