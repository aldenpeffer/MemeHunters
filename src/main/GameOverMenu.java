package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOverMenu{

	private MenuButton menu, exit;
	private static MenuButton[] buttonArray;
	public static boolean menuHover = false;
	public static boolean exitHover = false;
	private Handler handler;
	private Font font1, font2;
	private boolean font1Checked = false;
	private boolean font2Checked = false;
	private int width, scoreWidth;
	private int score;
	
	public GameOverMenu(Handler handler){
		this.handler = handler;
		int heightBuffer = 85;
		
		Menu.lock = false;
		
		menu = new MenuButton((Game.WIDTH/2) - 140, (10*Game.HEIGHT/16) - 33, 280, 60, "Menu", 35);
		exit = new MenuButton((Game.WIDTH/2) - 140, (10*Game.HEIGHT/16) - 33 + heightBuffer, 280, 60, "Exit", 35);
		
		font1 = Game.font.deriveFont(Font.PLAIN, 80);
		font2 = Game.font.deriveFont(Font.PLAIN, 40);
		
		buttonArray = new MenuButton[2];
		buttonArray[0] = menu;
		buttonArray[1] = exit;		
	}
	
	public void tick(){	
		score = HUD.SCORE;
	}

	public void render(Graphics g){
		g.setFont(font1);
		g.setColor(Color.white);
		if(!font1Checked){
			width = g.getFontMetrics().stringWidth("Game Over");
			font1Checked = true;
		}
		g.drawString("Game Over", Game.WIDTH/2 - width/2, 400);
		
		g.setFont(font2);
		if(!font2Checked){
			scoreWidth = g.getFontMetrics().stringWidth("Score: " + HUD.SCORE);
			font2Checked = true;
		}
		g.drawString("Score: " + score, Game.WIDTH/2 - scoreWidth/2, 500);
		
		menu.render(g, menuHover);
		exit.render(g, exitHover);	    
		
	}

	public static MenuButton[] getButtonArray() {
		return buttonArray;
	}
}

