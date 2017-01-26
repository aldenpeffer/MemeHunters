package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class BonusPoints extends GameObject{
	
	public int x, y, points, velX, velY;
	public double diffX, diffY, distance;
	public Handler handler;
	public ID id;
	public Font font;
	public float alpha = 1.0f;
	public int ticks = 0;
	private boolean fade = false;
	
	public BonusPoints(int x, int y, ID id, int velY, int points, Handler handler){
		super(x, y, id);
		this.x = x;
		this.y = y;
		this.id = id;
		this.velY = velY;
		this.points = points;
		this.handler = handler;
		font = Game.font;
	}

	@Override
	public void tick() {
		ticks++;
		velY = 3;
		y += velY;
		
		if(ticks == 45) fade = true;
		if(ticks % 3 == 0 && alpha > 0 && fade){
		    alpha = alpha - 0.05f;
		    if(alpha < 0) alpha = 0;
		}     
		
		if(alpha == 0){
			handler.removeObject(this);
		}
		    
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	    
		g2d.setFont(font);
		g2d.setColor(new Color(0xfce236));
		g2d.drawString("+" + points, x, y);
	    
	    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
	
}
