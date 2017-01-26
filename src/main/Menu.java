package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Game.STATE;

public class Menu extends MouseAdapter {

	private MenuButton play, help, exit;
	private MenuButton[] buttonArray;
	private String buttonPressed;
	private String buttonReleased;
	private StarSpawner menuStarSpawner;
	public static boolean playHover = false;
	public static boolean helpHover = false;
	public static boolean exitHover = false;
	private Handler handler;
	public static boolean lock = false;
	private boolean fade = false;
	private float alpha = 1.0f;
	private int ticks = 0;
	private Font font;
	public static boolean MENUFADE = false;
	
	public Menu(Handler handler){
		this.handler = handler;
		font = Game.font.deriveFont(Font.PLAIN, 80);
		int heightBuffer = 85;
		play = new MenuButton((Game.WIDTH/2) - 175, (8*Game.HEIGHT/16) - 33, 350, 75, "Play", 40);
		help = new MenuButton((Game.WIDTH/2) - 175, (8*Game.HEIGHT/16) - 33 + heightBuffer, 350, 75, "Help", 40);
		exit = new MenuButton((Game.WIDTH/2) - 175, (8*Game.HEIGHT/16) - 33 + 2*heightBuffer, 350, 75, "Exit", 40);
		
		menuStarSpawner = new StarSpawner(handler, 80);
		
		buttonArray = new MenuButton[3];
		buttonArray[0] = play;
		buttonArray[1] = help;
		buttonArray[2] = exit;			
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		buttonPressed = "";
		
		if(Game.gameState == STATE.Menu){
			for(int i = 0; i < buttonArray.length; i++){
				if(mx >= buttonArray[i].getX() && mx <= buttonArray[i].getX() + buttonArray[i].getWidth()){
					if(my >= buttonArray[i].getY() && my <= buttonArray[i].getY() + buttonArray[i].getHeight()){
						buttonPressed = buttonArray[i].getText();
					}
				}
			}
		}else if(Game.gameState == STATE.Gameover){
			MenuButton[] gameOverButtons = GameOverMenu.getButtonArray();
			for(int i = 0; i < gameOverButtons.length; i++){
				if(mx >= gameOverButtons[i].getX() && mx <= gameOverButtons[i].getX() + gameOverButtons[i].getWidth()){
					if(my >= gameOverButtons[i].getY() && my <= gameOverButtons[i].getY() + gameOverButtons[i].getHeight()){
						buttonPressed = gameOverButtons[i].getText();
					}
				}
			}
		}else if(Game.gameState == STATE.Help){
			MenuButton backButton = HelpMenu.getButton();
			if(mx >= backButton.getX() && mx <= backButton.getX() + backButton.getWidth()){
				if(my >= backButton.getY() && my <= backButton.getY() + backButton.getHeight()){
					buttonPressed = backButton.getText();
				}
			}
		}else if(Game.gameState == STATE.Game && KeyInput.paused){
			MenuButton[] pauseButtons = PauseMenu.getButtonArray();
			for(int i = 0; i < pauseButtons.length; i++){
				if(mx >= pauseButtons[i].getX() && mx <= pauseButtons[i].getX() + pauseButtons[i].getWidth()){
					if(my >= pauseButtons[i].getY() && my <= pauseButtons[i].getY() + pauseButtons[i].getHeight()){
						buttonPressed = pauseButtons[i].getText();
					}
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		buttonReleased = null; 
		
		if(Game.gameState == STATE.Menu){
			for(int i = 0; i < buttonArray.length; i++){
				if(mx >= buttonArray[i].getX() && mx <= buttonArray[i].getX() + buttonArray[i].getWidth()){
					if(my >= buttonArray[i].getY() && my <= buttonArray[i].getY() + buttonArray[i].getHeight()){
						buttonReleased = buttonArray[i].getText();
					}
				}
			}
		}else if(Game.gameState == STATE.Gameover){
			MenuButton[] gameOverButtons = GameOverMenu.getButtonArray();
			for(int i = 0; i < gameOverButtons.length; i++){
				if(mx >= gameOverButtons[i].getX() && mx <= gameOverButtons[i].getX() + gameOverButtons[i].getWidth()){
					if(my >= gameOverButtons[i].getY() && my <= gameOverButtons[i].getY() + gameOverButtons[i].getHeight()){
						buttonReleased = gameOverButtons[i].getText();
					}
				}
			}
		}else if(Game.gameState == STATE.Help){
			MenuButton backButton = HelpMenu.getButton();
			if(mx >= backButton.getX() && mx <= backButton.getX() + backButton.getWidth()){
				if(my >= backButton.getY() && my <= backButton.getY() + backButton.getHeight()){
					buttonReleased = backButton.getText();
				}
			}
		}else if(Game.gameState == STATE.Game && KeyInput.paused){
			MenuButton[] pauseButtons = PauseMenu.getButtonArray();
			for(int i = 0; i < pauseButtons.length; i++){
				if(mx >= pauseButtons[i].getX() && mx <= pauseButtons[i].getX() + pauseButtons[i].getWidth()){
					if(my >= pauseButtons[i].getY() && my <= pauseButtons[i].getY() + pauseButtons[i].getHeight()){
						buttonReleased = pauseButtons[i].getText();
					}
				}
			}
		}
		
		if(buttonReleased != null){
			if(buttonPressed.equals(buttonReleased) && !lock){
				if(Game.gameState == STATE.Menu){
					if(buttonReleased.equals("Play")){
						MENUFADE = true;
						fade = true;
						lock = true;
					}else if(buttonReleased.equals("Help")){
						Game.enterHelpState();
					}else if(buttonReleased.equals("Exit")){
						System.exit(1);
					}
				
				}else if(Game.gameState == STATE.Gameover){
					if(buttonReleased.equals("Menu")){
						Game.enterMenuState();
					}else if(buttonReleased.equals("Exit")){
						System.exit(1);
					}
				}else if(Game.gameState == STATE.Help){
					if(buttonReleased.equals("Back")){
						Game.enterMenuState();
					}
				} 
			}
			
			if(Game.gameState == STATE.Game){
				if(buttonReleased.equals("Menu")){
					lock = false;
					Game.enterMenuState();
					KeyInput.paused = false;
				}else if(buttonReleased.equals("Exit")){
					System.exit(1);
				}
			}
		}
	}
	

	
	public void tick(){	
		menuStarSpawner.tick();
	}

	public void render(Graphics g){
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("Meme Hunters", Game.WIDTH/2 - g.getFontMetrics().stringWidth("Meme Hunters")/2, 280);
		
		play.render(g, playHover);
		help.render(g, helpHover);
		exit.render(g, exitHover);	    
		
	}
	

	public MenuButton[] getButtonArray() {
		return buttonArray;
	}
	
	public void removeAllStars(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Star){
				Star star = (Star) tempObject;
				star.setRemove(true);
			}
		}
		menuStarSpawner.tick();
	}

	public StarSpawner getMenuStarSpawner(){
		return menuStarSpawner;
	}

}

