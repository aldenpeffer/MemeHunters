package main;
import main.Game;
import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;

	public Window(int width, int height, String title, Game game){
        frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }
	
	public JFrame getFrame(){
		return frame;
	}
}
