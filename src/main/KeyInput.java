package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.Game.STATE;

public class KeyInput extends KeyAdapter{

	private Handler handler;
	public boolean w = false, a = false, s = false, d = false;
	public static boolean paused = false;
	
	public KeyInput(Handler handler){
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player){
				if(key == KeyEvent.VK_W){
					w = true;
					tempObject.setVelY(-7);
				}
				if(key == KeyEvent.VK_S){
					s = true;
					tempObject.setVelY(7);
				}
				if(key == KeyEvent.VK_A){
					a = true;
					if(HUD.SHIFT_PRESSED){
						tempObject.setVelX(-20);
					}else{
						tempObject.setVelX(-7);
					}
				}
				if(key == KeyEvent.VK_D){
					d = true;
					if(HUD.SHIFT_PRESSED){
						tempObject.setVelX(20);
					}else{
						tempObject.setVelX(7);	
					}
				}
				if(key == KeyEvent.VK_SHIFT){
					HUD.SHIFT_PRESSED = true;
					if(tempObject.getVelX() > 0) tempObject.setVelX(20);
					if(tempObject.getVelX() < 0) tempObject.setVelX(-20);
				}		
				if(key == KeyEvent.VK_SPACE){
					Player.spacePressed = true;
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Player){
				if(key == KeyEvent.VK_W){
					w = false;
					if(s)tempObject.setVelY(7);
					if(!s)tempObject.setVelY(0);
				}
				if(key == KeyEvent.VK_S){
					s = false;
					if(w)tempObject.setVelY(-7);
					if(!w)tempObject.setVelY(0);
				}
				if(key == KeyEvent.VK_A){
					a = false;
					if(d && HUD.SHIFT_PRESSED){
						tempObject.setVelX(20);
					}else if(d){
						tempObject.setVelX(7);
					}
					if(!d)tempObject.setVelX(0);
				}
				if(key == KeyEvent.VK_D){
					d = false;
					if(a && HUD.SHIFT_PRESSED){
						tempObject.setVelX(-20);
					}else if(a){
						tempObject.setVelX(-7);
					}
					if(!a)tempObject.setVelX(0);			
				}
				if(key == KeyEvent.VK_SHIFT){
					HUD.SHIFT_PRESSED = false;
					if(a)tempObject.setVelX(-7);
					if(d)tempObject.setVelX(7);
				}
				if(key == KeyEvent.VK_SPACE){
					Player.spacePressed = false;
				}
			}
		}
		
		if(key == KeyEvent.VK_ESCAPE){
			if(!(Game.gameState == STATE.Game)){
				System.exit(1);
			}else{	
				paused = !paused;
			}
		}
		
	}
	
	public boolean getPaused(){
		return paused;
	}
	
}
