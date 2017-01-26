package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Laser extends GameObject {

	private Handler handler;
	private Laser partner;
	public boolean isMain;
	
	public Laser(int x, int y, ID id, Handler handler, boolean isMain) {
		super(x, y, id);
		this.handler = handler;
		this.isMain = isMain;
		velY = 10;
	}

	@Override
	public void tick() {
		if(isMain){
			y -= velY;
		}else{
			y = partner.getY();
			x = partner.getX() + 39;
		}
		
		if(y <= 0) handler.removeObject(this);
		
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0x00ff99));
		g.fillRect(x, y, 2, 20);	
		
	}
	
	public Rectangle[] getBounds(){
		Rectangle[] rectArray = new Rectangle[1];
		rectArray[0] = new Rectangle(x, y, 2, 20);
		return rectArray;
	}

	public Laser getPartner() {
		return partner;
	}

	public void setPartner(Laser partner) {
		this.partner = partner;
	}
	
}
