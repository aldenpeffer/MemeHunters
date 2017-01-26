package main;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;


public class Asteroid extends GameObject{

	private Image asteroid;
	private Image[] asteroidAnimation = new Image[5];
	private Handler handler;
	private int astHeight;
	private int astWidth;
	private int hitPoints;
	private int maxHP;
	private int pointValue;
	private Random rand = new Random();
	private boolean fade = false;
	private float alpha = 1.0f;
	private int ticks = 0;
	
	public Asteroid(int x, int y, ID id, Handler handler, int dimensions) {
		super(x, y, id);
		this.handler = handler;
		astWidth = dimensions;
		astHeight = dimensions;
		
                this.handler = handler;
		try{
                    asteroid = ImageIO.read(new File("./src/Resources/Asteroid.png"));
                    asteroidAnimation[0] = ImageIO.read(new File("./src/Resources/Asteroid_Animation1.png"));
                    asteroidAnimation[1] = ImageIO.read(new File("./src/Resources/Asteroid_Animation2.png"));
                    asteroidAnimation[2] = ImageIO.read(new File("./src/Resources/Asteroid_Animation3.png"));
                    asteroidAnimation[3] = ImageIO.read(new File("./src/Resources/Asteroid_Animation4.png"));
                    asteroidAnimation[4] = ImageIO.read(new File("./src/Resources/Asteroid_Animation5.png"));      
		}catch(IOException e){
			e.printStackTrace();
		}
                
		hitPoints = (int)((double)dimensions/80 * 3);
		maxHP = hitPoints;
		pointValue = hitPoints * 100;		                                                             
                
		velY = rand.nextInt(3) + 3;
                
	}
        
	@Override
	public void tick() {
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Laser){
				Laser laser = (Laser)tempObject;
				
				Rectangle[] laserArray = laser.getBounds();
				Rectangle laserRect = laserArray[0];
				
				Rectangle[] partnerArray = laser.getPartner().getBounds();
				Rectangle partnerRect = partnerArray[0];
				
				Rectangle[] bounds = this.getBounds();
				boolean check = false;
				boolean partnerCheck = false;
				for(int j = 0; j < bounds.length; j++){
					if(bounds[j].intersects(laserRect)){
						check = true;
					}
					if(partnerRect.intersects(bounds[j])) partnerCheck = true;
				}
				if(check && partnerCheck){
					handler.removeObject(laser);
					handler.removeObject(laser.getPartner());
					hitPoints -= 2;
					if(hitPoints <= 0){
						hitPoints = 0;
						AsteroidSpawner.SPAWNCOUNT--;
						if(HUD.HEALTH != 0){
							HUD.SCORE += pointValue; 
							handler.addObject(new BonusPoints(x + (int)(astWidth/4), y + (astHeight * 1/10), ID.BonusPoints, velY, pointValue, handler));
						}
						//handler.removeObject(this);
					}
				}else if(check){
					//THESE TWO LINES BELOW COULD CAUSE BUGS LATER
					if(laser.isMain) laser.getPartner().isMain = true;
					handler.removeObject(laser);
					hitPoints--;
					if(hitPoints <= 0){
						hitPoints = 0;
						AsteroidSpawner.SPAWNCOUNT--;
						if(HUD.HEALTH != 0){ 
							HUD.SCORE += pointValue; 
							handler.addObject(new BonusPoints(x + (int)(astWidth/4), y + (astHeight * 1/10), ID.BonusPoints, velY, pointValue, handler));
						}
						//handler.removeObject(this);
					}
				}
			}
		}
		
		
		//Damage Animation
		if(hitPoints == 0){
			asteroid = asteroidAnimation[4];
			fade = true;
			
		}else if(hitPoints < maxHP && hitPoints > (maxHP * 3/4)){
			//Lowest damage
			asteroid = asteroidAnimation[0];
			
		}else if(hitPoints <= (maxHP * 3/4) && hitPoints > (maxHP * 2/4)){
			
			asteroid = asteroidAnimation[1];
		
		}else if(hitPoints <= (maxHP * 2/4) && hitPoints > (maxHP * 1/4)){
		
			asteroid = asteroidAnimation[2];
		
		}else if(hitPoints <= (maxHP * 1/4) && hitPoints > 0){
			//Highest damage
			asteroid = asteroidAnimation[3];
		}
		
		ticks++;
		if(ticks % 3 == 0 && alpha > 0 && fade){
		    alpha = alpha - 0.05f;
		    if(alpha < 0) alpha = 0;
		    if(alpha == 0)handler.removeObject(this);
		} 
		
			
		y+= velY;	
	}

	@Override
	public void render(Graphics g) {
		//CLEAN THIS UP LATER, MAKE ASTWIDTH/200 A CONSTANT, MAKE THEM UNIFORM AS FUNCTIONS OF THAT CONSTANT
		/*g.setColor(Color.white);
		g.fillRect((int)(x + astWidth* 0.16), (int)(y + astHeight * 0.325), (int)(astWidth/9), (int)(astWidth*0.3));		
		g.fillRect((int)(x + 55* astWidth/200),(int)(y + 55 * astWidth/200), (int)(30 * astWidth/200),(int)(90 * astWidth/200));		
		g.fillRect((int)(x + (2*astWidth/5)),(int)(y + 48 * astWidth/200), (int)(astWidth/3),(int)(astHeight*11/20));		
		g.fillRect((int)(x + (2*astWidth/5 + astWidth/3)),(int)(y + 74 * astWidth/200), (int)(21 * astWidth/200), (int)astHeight/3);
		*/
		
		if(!fade){
			g.drawImage(asteroid, x, y, astWidth, astHeight, null);	
		}else{
			Graphics2D g2d = (Graphics2D) g;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g.drawImage(asteroid, x, y, astWidth, astHeight, null);		    
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}	
	    
	}

	@Override
	public Rectangle[] getBounds() {
		if(hitPoints != 0){ 
			Rectangle[] enemyBoundsArray = new Rectangle[4];
			enemyBoundsArray[0] = new Rectangle((int)(x + astWidth* 0.16), (int)(y + astHeight * 0.325), (int)(astWidth/9), (int)(astWidth*0.3));
			enemyBoundsArray[1] = new Rectangle((int)(x + 55* astWidth/200),(int)(y + 55 * astWidth/200), (int)(30 * astWidth/200),(int)(90 * astWidth/200));
			enemyBoundsArray[2] = new Rectangle((int)(x + (2*astWidth/5)),(int)(y + 48 * astWidth/200), (int)(astWidth/3),(int)(astHeight*11/20));
			enemyBoundsArray[3] = new Rectangle((int)(x + (2*astWidth/5 + astWidth/3)),(int)(y + 74 * astWidth/200), (int)(21 * astWidth/200), (int)astHeight/3);
			return enemyBoundsArray;
		}else{
			Rectangle[] emptyArray = new Rectangle[0];
			return emptyArray;
		}
	}
	
	public int getHP(){
		return hitPoints;
	}
	
	public int getDimensions(){
		return astHeight;
	}
	
}
