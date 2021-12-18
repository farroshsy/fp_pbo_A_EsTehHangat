package nico.spaceinvaders.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Resources {

	private static final HashMap<String, BufferedImage> TEXTURES = new HashMap<>();
	public static final ArrayList<Integer> HIGHSCORES = new ArrayList<>();
	
	public static void readTextures() {
		//Read all textures
		//Called before game starts
		File folder = new File("res/textures");
		for(File file : folder.listFiles()) {
			try {
				TEXTURES.put(file.getName(), ImageIO.read(file));
			} catch (IOException e) {
				System.err.println("Couldn't read file " + file.getName());
			}
		}
	}
	
	public static BufferedImage getTexture(String name) {
		//Retrieve a texture
		return TEXTURES.get(name+".png");
	}
	
	public static BufferedImage getTexture(String name, int x, int y, int w, int h) {
		//Retrieve a texture and get a subimage //Used for animations
		return TEXTURES.get(name+".png").getSubimage(x, y, w, h);
	}
	
	public static void registerNewScore(int score) {
		//Add a new score to the list, sort the list and remove the lowest score if there are more than 10
		HIGHSCORES.add(score);
		Collections.sort(HIGHSCORES);
		Collections.reverse(HIGHSCORES);
		if(HIGHSCORES.size() == 10)
			HIGHSCORES.remove(9);
	}
	
	public static void readHighScores() {
		//Read high scores file
		//Called before game starts
		try {
			BufferedReader reader = new BufferedReader(new FileReader("res/highscores.txt"));
			
			String line = reader.readLine();
			while(line != null) {
				HIGHSCORES.add(Integer.parseInt(line));
				line = reader.readLine();
			}
			
			reader.close();
		} catch (IOException e) {
			System.err.println("Couldn't read file res/highscores.txt");
		}
	}
	
	public static void writeHighScores() {
		//Save high scores file
		//Called when there is a game over
		try {
			FileWriter writer = new FileWriter(new File("res/highscores.txt"));
			
			for(int i = 0; i < HIGHSCORES.size(); i++) {
				writer.write(HIGHSCORES.get(i)+"\n");
			}
			
			writer.close();
		} catch (IOException e) {
			System.err.println("Couldn't write file res/highscores.txt");
		}
	}
}
