package main;

import java.util.Random;

public class AsteroidSpawner {

	private Handler handler;
	public static int asteroidTicks = 0;
	public static int SPAWNCOUNT = 0; //This is for asteroids
	private Random rand = new Random();
	private Asteroid asteroid;
	
	public AsteroidSpawner(Handler handler){
		this.handler = handler;	
	}
	
	public void tick(){
	//Asteroid Spawning
		asteroidTicks++;
		if(asteroidTicks % 45 == 0){
			if(SPAWNCOUNT < 15){
				int dimension = rand.nextInt(240) + 80;
				asteroid = new Asteroid(rand.nextInt(Game.WIDTH - dimension), -dimension, ID.Asteroid, handler, dimension);
				handler.addObject(asteroid);
				SPAWNCOUNT ++;
				asteroidTicks = 0;
			}
		}
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getY() > Game.HEIGHT && tempObject.getId() == ID.Asteroid){
				handler.removeObject(tempObject);
				SPAWNCOUNT--;
				}
		}
	}

	public void deleteAsteroids(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Asteroid){
				handler.removeObject(tempObject);

			}
		}
	}

}

