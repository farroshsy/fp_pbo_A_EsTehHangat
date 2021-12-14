package resources;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Main;

public class Keyboard implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		if(Main.view.player.isLife() == true) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){Main.view.player.setDx(Constants.DX_PLAYER);}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT){Main.view.player.setDx(-Constants.DX_PLAYER);}
			else if(e.getKeyCode() == KeyEvent.VK_SPACE){
				if(Main.view.playerBullet.isPlayerBullets() == false) {	
					Audio.playSound("/sound/PlayerShoot.wav");
					Main.view.playerBullet.setyPos(Constants.POS_Y_PLAYER - Constants.PLAYER_BULLET_HEIGHT);
					Main.view.playerBullet.setxPos(Main.view.player.getxPos() + Constants.PLAYER_WIDTH/2 - 1);	
					Main.view.playerBullet.setPlayerBullets(true);
				}
		    }
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {Main.view.player.setDx(0);}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
