package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Player extends GameObject{

	Random rand = new Random();
	private Handler handler;
	private Image ship;
	private int fireCounter = 0;
	public static boolean spacePressed = false;
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		try{
                    File pathToFile = new File("./src/Resources/Ship.png");
                    ship = ImageIO.read(pathToFile);
		}catch(IOException e){
			e.printStackTrace();
		}		
	}

	@Override
	public void tick() {
		if(checkFire()){
			Laser laser1 = new Laser(x + 36, y + 30, ID.Laser, handler, true);
			Laser laser2 = new Laser(x + 75, y + 30, ID.Laser, handler, false);
			
			laser1.setPartner(laser2);
			laser2.setPartner(laser1);
			
			handler.addObject(laser1);
			handler.addObject(laser2);
		}
		
		
		if(HUD.STAMINA == 0){
			if(velX > 0){
				velX = 7;
			}else if(velX < 0){
				velX = -7;
			}else{
				velX = 0;
			}
		}
		
		x += velX;
		y += velY;
		
		x = Game.clamp(x, 0, Game.WIDTH - 120);
		y = Game.clamp(y, 350, Game.HEIGHT - 120);
		
		collision();
	}
	
	private boolean checkFire() {
		if(!spacePressed){
			fireCounter = 0;
			return false;
		}else{
			if(fireCounter % 10 == 0){
				fireCounter++;
				return true;
			}else{
				fireCounter++;
				return false;
			}	
		}
	}

	public void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Asteroid){
				if(collisionCheck(tempObject)){
					HUD.HEALTH -= 2;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		/*g.setColor(Color.white);
		g.fillRect(x + 48, y, 18, 35);
		g.fillRect(x + 42, y + 14, 30, 20);
		g.fillRect(x + 35, y + 29, 44, 40);
		g.fillRect(x + 20, y + 55, 74, 15);
		g.fillRect(x, y + 69, 114, 20);
		*/
		g.drawImage(ship, x, y, 114, 120, null);
	}

	@Override
	public boolean collisionCheck(GameObject tempObject) {
		Rectangle[] rectArray = tempObject.getBounds();
		
		Rectangle rect1 = new Rectangle(x + 48, y, 18, 35);
		Rectangle rect2 = new Rectangle(x + 42, y + 14, 30, 20);
		Rectangle rect3 = new Rectangle(x + 35, y + 29, 44, 40);
		Rectangle rect4 = new Rectangle(x + 20, y + 55, 74, 15);
		Rectangle rect5 = new Rectangle(x, y + 69, 114, 20);
		for(int i = 0; i < rectArray.length; i++){
			if(rect1.intersects(rectArray[i])) return true;
			if(rect2.intersects(rectArray[i])) return true;
			if(rect3.intersects(rectArray[i])) return true;
			if(rect4.intersects(rectArray[i])) return true;
			if(rect5.intersects(rectArray[i])) return true;		
		}

		return false;
	}

}
