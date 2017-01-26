package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HelpMenu {

	private StarSpawner menuStarSpawner;
	private Handler handler;
	private Font font1, font2, font3;
	private Menu menu;
	public static boolean backHover = false;
	public static MenuButton back;
	
	public HelpMenu(Handler handler, Menu menu){
		this.handler = handler;
		this.menu = menu;
		menuStarSpawner = menu.getMenuStarSpawner();
		font1 = Game.font.deriveFont(Font.PLAIN, 32);
		font2 = Game.font.deriveFont(Font.PLAIN, 16);
		font3 = Game.font.deriveFont(Font.PLAIN, 16);
		
		back = new MenuButton(2*Game.WIDTH/5, (10*Game.HEIGHT/16) + 120, Game.WIDTH/5, 75, "Back", 32, new Color(0x80dfff) , new Color(0x004c66), new Color(0x0085b3));
		
		
	}
	
	public void tick(){
		menuStarSpawner.tick();
	}
	
	public void render(Graphics g){
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH/3, Game.HEIGHT/5 - 100, Game.WIDTH/3, Game.HEIGHT*3/5);
		g.setColor(new Color(0x004c66));
		g.fillRect(Game.WIDTH/3 + 1, Game.HEIGHT/5 - 99, Game.WIDTH/3 - 1, Game.HEIGHT*3/5 - 1);
		g.setColor(Color.white);
		g.setFont(font1);
		int stringWidth = g.getFontMetrics().stringWidth("Controls");
		int textX = Game.WIDTH/3 + (Game.WIDTH/3 - stringWidth)/2;
		
		g.setColor(new Color(0x80dfff));
		g.drawString("Controls", textX, Game.HEIGHT/5 - 40);		
		g.setFont(font2);
		g.drawString("- Arrow keys", Game.WIDTH/3 + 15, Game.HEIGHT/5 + 30);
		g.drawString("- Space", Game.WIDTH/3 + 15, Game.HEIGHT/5 + 100);
		g.drawString("- Shift", Game.WIDTH/3 + 15, Game.HEIGHT/5 + 170);
		g.drawString("- Esc", Game.WIDTH/3 + 15, Game.HEIGHT/5 + 240);
		
		g.setColor(new Color(0x00abe6));
		g.drawString(" for movement", Game.WIDTH/3 + 15 + g.getFontMetrics().stringWidth("- Arrow keys") , Game.HEIGHT/5 + 30);
		g.drawString(" to fire", Game.WIDTH/3 + 15 + g.getFontMetrics().stringWidth("- Space") , Game.HEIGHT/5 + 100);
		g.drawString(" for speed boost", Game.WIDTH/3 + 15 + g.getFontMetrics().stringWidth("- Shift") , Game.HEIGHT/5 + 170);
		g.drawString(" to pause game", Game.WIDTH/3 + 15 + g.getFontMetrics().stringWidth("- Esc") , Game.HEIGHT/5 + 240);
		
		g.setFont(font3);
		g.setColor(new Color(0xff9966));
		g.drawString("Made by: Alden Peffer", Game.WIDTH/3 + 150, 3*Game.HEIGHT/5 + 70);
		
		back.render(g, backHover);
	}
	
	public static MenuButton getButton() {
		return back;
	}
}
