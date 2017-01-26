package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import main.Game.STATE;

public class MenuMotionListener extends MouseMotionAdapter{
	
	private Menu menu;
	private MenuButton[] buttonArray;
	
	public MenuMotionListener(Menu menu){
		this.menu = menu;
		buttonArray = menu.getButtonArray();
	}
	
	public void	mouseMoved(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if(Game.gameState == STATE.Menu){
			HelpMenu.backHover = false;
			GameOverMenu.menuHover = false;
			GameOverMenu.exitHover = false;
			for(int i = 0; i < buttonArray.length; i++){
				if(mx >= buttonArray[i].getX() && mx <= buttonArray[i].getX() + buttonArray[i].getWidth()){
					if(my >= buttonArray[i].getY() && my <= buttonArray[i].getY() + buttonArray[i].getHeight()){
						if(buttonArray[i].getText().equals("Play")){
							Menu.playHover = true;
							Menu.exitHover = false;
							Menu.helpHover = false;
							break;
						}else if(buttonArray[i].getText().equals("Help")){
							Menu.helpHover = true;
							Menu.playHover = false;
							Menu.exitHover = false;
							break;
						}else if(buttonArray[i].getText().equals("Exit")){
							Menu.exitHover = true;
							Menu.playHover = false;
							Menu.helpHover = false;
							break;
						}
					}
				}
				Menu.exitHover = false;
				Menu.playHover = false;
				Menu.helpHover = false;
			}
		}else if(Game.gameState == STATE.Gameover){
			Menu.exitHover = false;
			Menu.playHover = false;
			Menu.helpHover = false;
			HelpMenu.backHover = false;
			MenuButton[] gameOverButtons = GameOverMenu.getButtonArray();
			for(int i = 0; i < gameOverButtons.length; i++){
				if(mx >= gameOverButtons[i].getX() && mx <= gameOverButtons[i].getX() + gameOverButtons[i].getWidth()){
					if(my >= gameOverButtons[i].getY() && my <= gameOverButtons[i].getY() + gameOverButtons[i].getHeight()){
						if(gameOverButtons[i].getText().equals("Menu")){
							GameOverMenu.menuHover = true;
							GameOverMenu.exitHover = false;
							break;
						}else if(gameOverButtons[i].getText().equals("Exit")){
							GameOverMenu.menuHover = false;
							GameOverMenu.exitHover = true;
							break;
						}
					}
				}
				GameOverMenu.menuHover = false;
				GameOverMenu.exitHover = false;
			}
		}else if(Game.gameState == STATE.Help){
			Menu.exitHover = false;
			Menu.playHover = false;
			Menu.helpHover = false;
			GameOverMenu.menuHover = false;
			GameOverMenu.exitHover = false;
			MenuButton backButton = HelpMenu.getButton();
			if(mx >= backButton.getX() && mx <= backButton.getX() + backButton.getWidth()){
				if(my >= backButton.getY() && my <= backButton.getY() + backButton.getHeight()){
					HelpMenu.backHover = true;
				}else{
					HelpMenu.backHover = false;
				}
			}else{
				HelpMenu.backHover = false;
			}
			
		}else if(Game.gameState == STATE.Game && KeyInput.paused){
			PauseMenu.menuHover = false;
			PauseMenu.exitHover = false;
			
			MenuButton[] pauseButtons = PauseMenu.getButtonArray();
			for(int i = 0; i < pauseButtons.length; i++){
				if(mx >= pauseButtons[i].getX() && mx <= pauseButtons[i].getX() + pauseButtons[i].getWidth()){
					if(my >= pauseButtons[i].getY() && my <= pauseButtons[i].getY() + pauseButtons[i].getHeight()){
						if(pauseButtons[i].getText().equals("Menu")){
							PauseMenu.menuHover = true;
							PauseMenu.exitHover = false;
							break;
						}else if(pauseButtons[i].getText().equals("Exit")){
							PauseMenu.menuHover = false;
							PauseMenu.exitHover = true;
							break;
						}
					}
				}else{
					PauseMenu.menuHover = false;
					PauseMenu.exitHover = false;
				}
			}
				
		}
	}

}
