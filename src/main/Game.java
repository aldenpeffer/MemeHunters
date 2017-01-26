package main;

import main.Window;
import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1280, HEIGHT = 960;
	private Thread thread;
	private boolean running = false;
	private static Handler handler;
	private Random rand = new Random();
	private static HUD hud;
	private static Menu menu;
	private static StarSpawner starSpawner;
	private static AsteroidSpawner asteroidSpawner;
	public static Font font;
	public Font ttfBase;
	public static Window window;
	private MenuMotionListener motionListener;
	private HelpMenu help;
	private static GameOverMenu gameOverMenu;
	private static int ticks = 0;
	private static float alpha = 1.0f;
	private KeyInput keyInput;
	private PauseMenu pauseMenu;
	
	public enum STATE{
		Menu,
		Game,
		Gameover,
		Help
	};
	
	public static STATE gameState = STATE.Menu;
	
	public Game(){
		try {
			InputStream myStream = new BufferedInputStream(new FileInputStream("src/Resources/unibody.ttf"));
			ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
			font = ttfBase.deriveFont(Font.PLAIN, 18);
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		
		handler = new Handler();
		hud = new HUD();
		keyInput = new KeyInput(handler);
		this.addKeyListener(keyInput);
		menu = new Menu(handler);
		this.addMouseListener(menu);
		motionListener = new MenuMotionListener(menu);
		help = new HelpMenu(handler, menu);
		pauseMenu = new PauseMenu(handler);
		this.addMouseMotionListener(motionListener);
		window = new Window(WIDTH, HEIGHT, "Meme Hunters", this);
	
	}
    
	public static int clamp(int var, int min, int max){
		if(var >= max){
			return max;
		}else if(var <= min){
			return min;
		}else{
			return var;
		}
	}
	
	public static void main(String[] args){
		new Game();
    }

	public void run()
    {
		this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){     
        	long now = System.nanoTime();
        	delta += (now - lastTime) / ns;
        	lastTime = now;
        	while(delta >=1){
        		tick();
        		delta--;
        	}
        	if(running) render();
            frames++;
                            
            if(System.currentTimeMillis() - timer > 1000){
            	timer += 1000;
            	//System.out.println("FPS: "+ frames);
            	frames = 0;
            }
        }
        stop();
    }
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();	
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(0x000c1a));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(gameState == STATE.Game){
			//Game rendering
			handler.render(g);
			hud.render(g);	
			if(KeyInput.paused){
				//Paused menu rendering
				pauseMenu.render(g);
			}
			
		}else if(gameState == STATE.Menu){
			//Menu rendering
			if(!Menu.MENUFADE){
				handler.render(g);
				menu.render(g);
			}else{
				Graphics2D g2d = (Graphics2D) g;
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
				handler.render(g);
				menu.render(g);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			}
		}else if(gameState == STATE.Gameover){
			handler.render(g);
			gameOverMenu.render(g);
		}else if(gameState == STATE.Help){
			handler.render(g);
			help.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	private void tick(){
		if(gameState == STATE.Game){
			//Game ticking
			if(!keyInput.getPaused()){	
				handler.tick();
				hud.tick();
				starSpawner.tick();
				asteroidSpawner.tick();
			}else{
			//Pause menu ticks
			}
			
		}else if(gameState == STATE.Menu){
			//Menu ticking
			if(!Menu.MENUFADE){
				handler.tick();
				menu.tick();
			}else{
				ticks++;
				
				if(ticks % 2  == 0 && alpha > 0 && Menu.MENUFADE){
					
					alpha = alpha - 0.05f;
				    if(alpha < 0) alpha = 0;
				    if(alpha == 0){
				    	menu.removeAllStars();
				    	Game.enterGameState();
				    	Menu.MENUFADE = false;
				    }
				}
				handler.tick();
				menu.tick();
			}
		}else if(gameState == STATE.Gameover){
			handler.tick();
			starSpawner.tick();
			gameOverMenu.tick();
		}else if(gameState == STATE.Help){
			handler.tick();
			help.tick();
		}
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try{
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void enterGameState(){	
		alpha = 1.0f;
		ticks = 0;
		HUD.HEALTH = 100;
		HUD.SCORE = 0;
		asteroidSpawner = new AsteroidSpawner(handler);
		AsteroidSpawner.SPAWNCOUNT = 0;
		AsteroidSpawner.asteroidTicks = 0;
		asteroidSpawner.deleteAsteroids();
		deletePlayer();
		Player player = new Player(WIDTH/2-57, HEIGHT/2-32, ID.Player, handler);
		starSpawner = new StarSpawner(handler, 80);
		handler.addObject(player);
		gameState = STATE.Game;
	}
	
	public static void enterMenuState(){
		gameState = STATE.Menu;
	}

	public static void enterGameOverState() {
		gameOverMenu = new GameOverMenu(handler);
		gameState = STATE.Gameover;
	}
	
	public static void enterHelpState() {
		gameState = STATE.Help;
	}
	
	public static void deletePlayer(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Player){
				handler.removeObject(tempObject);

			}
		}
	}
	
}
