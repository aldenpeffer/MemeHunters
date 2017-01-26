package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PauseMenu{

	private MenuButton menu, exit;
	private static MenuButton[] buttonArray;
	public static boolean menuHover = false;
	public static boolean exitHover = false;
	private Handler handler;
	private Font font1;
	private boolean font1Checked = false;
	private boolean font2Checked = false;
	private int width, scoreWidth;
	
	public PauseMenu(Handler handler){
		this.handler = handler;
		int heightBuffer = 85;
		
		menu = new MenuButton((Game.WIDTH/2) - 140, (10*Game.HEIGHT/16) - 33, 280, 60, "Menu", 35);
		exit = new MenuButton((Game.WIDTH/2) - 140, (10*Game.HEIGHT/16) - 33 + heightBuffer, 280, 60, "Exit", 35);
		
		font1 = Game.font.deriveFont(Font.PLAIN, 48);
		
		buttonArray = new MenuButton[2];
		buttonArray[0] = menu;
		buttonArray[1] = exit;		
	}
	
	public void tick(){	
		
	}

	public void render(Graphics g){
		g.setColor(Color.white);
		menu.render(g, menuHover);
		exit.render(g, exitHover);
		g.setFont(font1);
		int pausedWidth = g.getFontMetrics().stringWidth("Paused");
		g.setColor(Color.WHITE);
		g.drawString("Paused", Game.WIDTH/2 - pausedWidth/2, Game.HEIGHT/2 - 150);
		
		//menu.render(g, menuHover);
		//exit.render(g, exitHover);	    
		
	}

	public static MenuButton[] getButtonArray() {
		return buttonArray;
	}
}
