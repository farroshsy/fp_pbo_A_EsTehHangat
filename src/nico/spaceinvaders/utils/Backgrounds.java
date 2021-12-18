package nico.spaceinvaders.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import nico.spaceinvaders.WindowManager;
import nico.spaceinvaders.models.PlayerShip;

public class Backgrounds {

	public static void drawMainMenu(Graphics graphics, String[] options, int selected) {
		graphics.setColor(Color.PINK);
		graphics.fillRect(0, 0, WindowManager.WIDTH, WindowManager.HEIGHT);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Arial", Font.BOLD, 22 * WindowManager.ZOOM));
		graphics.drawString("VIRUS", 30 * WindowManager.ZOOM, 30 * WindowManager.ZOOM);
		graphics.setColor(Color.RED);
		graphics.drawString("INVADERS", 115 * WindowManager.ZOOM, 30 * WindowManager.ZOOM);

		graphics.setFont(new Font("Arial", Font.PLAIN, 8 * WindowManager.ZOOM));
		for(int i = 0; i < options.length; i++) {
			if(i == selected)
				graphics.setColor(Color.RED);
			else
				graphics.setColor(Color.WHITE);
			graphics.drawString(options[i], WindowManager.WIDTH / 2 - 20 * WindowManager.ZOOM, (50 + i * 12) * WindowManager.ZOOM);
		}
		
		graphics.drawImage(Resources.getTexture("Group 15", 0, 0, 12, 8), WindowManager.WIDTH / 2 - 30 * WindowManager.ZOOM, 90 * WindowManager.ZOOM, 12 * WindowManager.ZOOM, 8 * WindowManager.ZOOM, null);
		graphics.drawImage(Resources.getTexture("Group 15", 0, 8, 12, 8), WindowManager.WIDTH / 2 - 30 * WindowManager.ZOOM, 105 * WindowManager.ZOOM, 12 * WindowManager.ZOOM, 8 * WindowManager.ZOOM, null);
		graphics.drawImage(Resources.getTexture("Group 15", 0, 16, 12, 8), WindowManager.WIDTH / 2 - 30 * WindowManager.ZOOM, 120 * WindowManager.ZOOM, 12 * WindowManager.ZOOM, 8 * WindowManager.ZOOM, null);
		graphics.drawImage(Resources.getTexture("rsz_coronavirus_1"), WindowManager.WIDTH / 2 - 30 * WindowManager.ZOOM, 135 * WindowManager.ZOOM, 13 * WindowManager.ZOOM, 8 * WindowManager.ZOOM, null);
		graphics.setColor(Color.WHITE);
		graphics.drawString("= 10 points", WindowManager.WIDTH / 2 - 10 * WindowManager.ZOOM, 97 * WindowManager.ZOOM);
		graphics.drawString("= 20 points", WindowManager.WIDTH / 2 - 10 * WindowManager.ZOOM, 112 * WindowManager.ZOOM);
		graphics.drawString("= 30 points", WindowManager.WIDTH / 2 - 10 * WindowManager.ZOOM, 127 * WindowManager.ZOOM);
		graphics.drawString("= ???", WindowManager.WIDTH / 2 - 10 * WindowManager.ZOOM, 142 * WindowManager.ZOOM);
	}
	
	public static void drawPlayingBackground(Graphics graphics, int score, byte lives) {
		graphics.setColor(Color.PINK);
		graphics.fillRect(0, 0, WindowManager.WIDTH, WindowManager.HEIGHT);
		
		graphics.setColor(Color.RED);
		graphics.fillRect(WindowManager.LIMIT_LEFT, WindowManager.LIMIT_DOWN + 5 * WindowManager.ZOOM, WindowManager.WIDTH - 2 * WindowManager.LIMIT_LEFT, 1 * WindowManager.ZOOM);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 8 * WindowManager.ZOOM));
		graphics.drawString("Score: " + score, WindowManager.LIMIT_LEFT, WindowManager.LIMIT_DOWN + 15 * WindowManager.ZOOM);
		graphics.drawString("Lives: ", WindowManager.LIMIT_LEFT + 75 * WindowManager.ZOOM, WindowManager.LIMIT_DOWN + 15 * WindowManager.ZOOM);
		
		for(int i = 0; i < lives; i++) {
			graphics.drawImage(Resources.getTexture("player", 0, 0, PlayerShip.SIZE_X, PlayerShip.SIZE_Y), WindowManager.LIMIT_LEFT + (100 + i * 10) * WindowManager.ZOOM, WindowManager.LIMIT_DOWN + 10 * WindowManager.ZOOM, PlayerShip.SIZE_X * WindowManager.ZOOM / 2, PlayerShip.SIZE_Y * WindowManager.ZOOM / 2, null);
		}
	}
	
	public static void drawGameOverScreen(Graphics graphics, int score) {
		graphics.setColor(Color.PINK);
		graphics.fillRect(0, 0, WindowManager.WIDTH, WindowManager.HEIGHT);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Arial", Font.BOLD, 22 * WindowManager.ZOOM));
		graphics.drawString("GAME", 45 * WindowManager.ZOOM, 30 * WindowManager.ZOOM);
		graphics.setColor(Color.RED);
		graphics.drawString("OVER", 123 * WindowManager.ZOOM, 30 * WindowManager.ZOOM);

		graphics.setFont(new Font("Arial", Font.PLAIN, 8 * WindowManager.ZOOM));
		graphics.setColor(Color.WHITE);
		graphics.drawString("Your score: " + score, WindowManager.WIDTH / 2 - 10 * WindowManager.ZOOM, 97 * WindowManager.ZOOM);
		graphics.drawString("Press ESC", WindowManager.WIDTH / 2 - 10 * WindowManager.ZOOM, 112 * WindowManager.ZOOM);
	}
	
	public static void drawHighScoresMenu(Graphics graphics) {
		graphics.setColor(Color.PINK);
		graphics.fillRect(0, 0, WindowManager.WIDTH, WindowManager.HEIGHT);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Arial", Font.BOLD, 18 * WindowManager.ZOOM));
		graphics.drawString("Highscores", 20 * WindowManager.ZOOM, 20 * WindowManager.ZOOM);

		graphics.setFont(new Font("Arial", Font.BOLD, 8 * WindowManager.ZOOM));
		for(int i = 0; i < Resources.HIGHSCORES.size(); i++) {
			graphics.drawString((i + 1) + " - " + Resources.HIGHSCORES.get(i), 20 * WindowManager.ZOOM, (i * 10 + 40) * WindowManager.ZOOM);
		}
	}
}
