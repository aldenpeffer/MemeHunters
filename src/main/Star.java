package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Star extends GameObject{
	
	public int starSize;
	private int x, y;
	private ID id;
	private boolean shouldRemove = false;
	
	public Star(int x, int y, ID id, int starSize) {
		super(x, y, id);
		this.x = x;
		this.y = y;
		this.id = id;
		this.starSize = starSize;
		velY = 1;
		velX = 0;
	}

	@Override
	public void tick() {
		y += velY;
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, starSize, starSize);

	}
	
	public int getY(){
		return y;
	}
	
	public boolean getRemove(){
		return shouldRemove;
	}
	
	public void setRemove(boolean remove){
		shouldRemove = remove;
	}

}
