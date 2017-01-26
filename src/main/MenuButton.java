package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuButton {
	
	private static final long serialVersionUID = 1L;
	private Font largeFont;
	private String buttonText;
	private int x, y, width, height;
	private boolean hoverCheck;
	private int fontSize;
	private Color fontColor, normalColor, hoverColor;

	
	public MenuButton(int x, int y, int width, int height, String buttonText, int fontSize, Color fontColor, Color normalColor, Color hoverColor){
		this.buttonText = buttonText;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;	
		this.fontColor = fontColor;
		this.normalColor = normalColor;
		this.hoverColor = hoverColor;
		largeFont = Game.font.deriveFont(Font.PLAIN, fontSize);
	}
	
	public MenuButton(int x, int y, int width, int height, String buttonText, int fontSize){
		this.buttonText = buttonText;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;	
		fontColor = new Color(0xffd733);
		normalColor = new Color(0x36364a);
		hoverColor = new Color(0x616184);
		largeFont = Game.font.deriveFont(Font.PLAIN, fontSize);
	}

	
	public void tick(){
	}

	public void render(Graphics g, boolean hoverCheck){
		this.hoverCheck = hoverCheck;
		g.setColor(Color.white);
		g.drawRect(x, y, width, height);
		
		if(!hoverCheck){
			g.setColor(normalColor);
		}else{
			g.setColor(hoverColor);
		}
		
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		g.setColor(fontColor);
		g.setFont(largeFont);
		g.drawString(buttonText, getTextXCoord(g), y + 3*height/4 - 3);	
	}
	
	public int getTextXCoord(Graphics g){
		g.setFont(largeFont);
		int stringWidth = g.getFontMetrics().stringWidth(buttonText);
		int textX = x + (width - stringWidth)/2;
		return textX;
	}
	
	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	public int getWidth() {
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public String getText(){
		return buttonText;
	}
	
}
