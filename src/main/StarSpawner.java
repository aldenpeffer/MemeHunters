package main;

import java.util.Random;

public class StarSpawner {

	private Handler handler;
	public static int starTicks = 0;
	public int maxStars;
	public static int STARCOUNT;
	private Random rand = new Random();
	private Star[] starArray;
	private boolean startUp = true;
	private int starSize;
	
	public StarSpawner(Handler handler, int maxStars){
		this.handler = handler;
		this.maxStars = maxStars;
		starArray = new Star[maxStars];
		
		for(int i = 0; i < maxStars; i++){
			starSize = rand.nextInt(2) + 2;
			starArray[i] = new Star(rand.nextInt(Game.WIDTH - starSize), rand.nextInt(Game.HEIGHT - starSize), ID.Star, starSize);
		}
	}
	
	public void tick(){
		starTicks++;
		
		//First stars spawn
		if(startUp){
			STARCOUNT = maxStars;
			for(int i = 0; i < starArray.length; i++){
				handler.addObject(starArray[i]);
			}
			
			startUp = false;
		}
		
		//Star Spawning
		if(starTicks % 5 == 0){
			if(STARCOUNT < maxStars){
				starSize = rand.nextInt(3) + 2;
				handler.addObject(new Star(rand.nextInt(Game.WIDTH - starSize), -starSize, ID.Star, starSize));
				STARCOUNT++;
				starTicks = 0;
			}
		}
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Star){
				Star star = (Star) tempObject;
				if(star.getRemove()){
					handler.removeObject(tempObject);
				}else if(tempObject.getY() > Game.HEIGHT && tempObject.getId() == ID.Star){
					handler.removeObject(tempObject);
					STARCOUNT--;
				}
			}
		}	
		
	}
	
}


