package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HUD {

	private Color myGreen = new Color(0X16e799);
	private Color myGreen2 = new Color(0Xbef1d3);
	private Color myBlue = new Color(0x2aeee8);
	
	public static int HEALTH = 100;
	public static int STAMINA = 100;
	public static boolean SHIFT_PRESSED = false;
	public static int SCORE = 0;
	
	public void tick(){
		if(HEALTH > 0)SCORE += 10;
		HEALTH = Game.clamp(HEALTH, 0, 100);
		STAMINA = Game.clamp(STAMINA, 0, 100);
		
		if(SHIFT_PRESSED){
			if(STAMINA > 0)STAMINA--;
		}else{ 
			if(STAMINA < 100)STAMINA++;
		}
		
		if(HEALTH == 0){
			Game.enterGameOverState();
		}
	}
	
	public void render(Graphics g){
		g.setFont(Game.font);
		g.setColor(Color.white);
		g.drawString("Score: " + getScore(), Game.WIDTH - 250, 40);
		
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 25);
		g.setColor(myGreen);
		g.fillRect(15, 15, (HEALTH*2), 25);
		g.setColor(myGreen2);
		g.drawRect(15, 15, 200, 25);
		g.setColor(Color.gray);
		g.fillRect(15, 45, 170, 8);
		g.setColor(myBlue);
		g.fillRect(15, 45, (int)(STAMINA * 1.7), 8);
		g.setColor(myGreen2);
		g.drawRect(15, 45, 170, 8);
	}
	
	public String getScore(){
		String newScore = SCORE + "";
		for(int i = (8 - newScore.length()); i > 0; i --){
			newScore = "0" + newScore;
		}
		if(SCORE >= 99999999) return "99999999";
		return newScore;
	}
	
}
