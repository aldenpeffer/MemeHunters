package main;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

import main.Game.STATE;

public class Handler {

	LinkedList<GameObject> object = new LinkedList<GameObject>();

	
	public void tick(){
		for(int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			tempObject.tick();
		}
	}
	
	public void render(Graphics g){
		//Game render
		if(Game.gameState == STATE.Game){
			for(int i = 0; i < object.size(); i++){			
				GameObject tempObject = object.get(i);
				if(tempObject.getId() == ID.Star) tempObject.render(g);
			}		
			for(int i = 0; i < object.size(); i++){			
				GameObject tempObject = object.get(i);
				if(tempObject.getId() == ID.Asteroid){
					Asteroid asteroid = (Asteroid) tempObject;
					if(asteroid.getHP() == 0) tempObject.render(g);
				}
			}
			for(int i = 0; i < object.size(); i++){			
				GameObject tempObject = object.get(i);
				if(tempObject.getId() == ID.Laser) tempObject.render(g);
			}
			for(int i = 0; i < object.size(); i++){			
				GameObject tempObject = object.get(i);
				if(tempObject.getId() == ID.Player) tempObject.render(g);
			}
			for(int i = 0; i < object.size(); i++){			
				GameObject tempObject = object.get(i);
				if(tempObject.getId() == ID.Enemy){
					//tempObject.render(g);
				}
			}
			for(int i = 0; i < object.size(); i++){			
				GameObject tempObject = object.get(i);
				if(tempObject.getId() == ID.Asteroid){
					Asteroid asteroid = (Asteroid) tempObject;
					if(!(asteroid.getHP() == 0)) tempObject.render(g);
				}
			}
			for(int i = 0; i < object.size(); i++){			
				GameObject tempObject = object.get(i);
				if(tempObject.getId() == ID.BonusPoints) tempObject.render(g);
			}
		//Menu render
		}else if(Game.gameState == STATE.Menu){
			for(int i = 0; i < object.size(); i++){			
				GameObject tempObject = object.get(i);
				if(tempObject.getId() == ID.Star) tempObject.render(g);
			}
		//Gameover render
		}else if(Game.gameState == STATE.Gameover){
			for(int i = 0; i < object.size(); i++){			
				GameObject tempObject = object.get(i);
				if(tempObject.getId() == ID.Star) tempObject.render(g);
			}
		//Help menu render
		}else if(Game.gameState == STATE.Help){
			for(int i = 0; i < object.size(); i++){			
				GameObject tempObject = object.get(i);
				if(tempObject.getId() == ID.Star) tempObject.render(g);
			}
		}
		
	}
	
	public void addObject(GameObject object){
		this.object.add(object);
	}
	
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
}
