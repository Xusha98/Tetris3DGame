package main;

import java.awt.Rectangle;

import org.newdawn.slick.SlickException;

import engine.io.Image;

public class MainMenu {

	 	boolean playHover = false;
	    Rectangle playRect;
	    Image bg,Game_Title;
	    Image play_button,play_hover,play_pressed;
	    Image mode,option_hover,option_pressed;
	    Image help,quit_hover,quit_pressed;


	    public void init(GameContainer gamecontainer1, StateBasedGame statebasedgame1) throws SlickException{
	    	
	    bg = Image.loadImage("resources/Textures/BG.png");
	    play_button = Image.loadImage("res/Main_menu_buttons/play.png");
	    //playRect = new Rectangle(205, 206, play.getWidth(), play.getHeight());
	    //play_hover = new Image("res/Main_menu_buttons/play_hover.png");
	    //play_pressed = new Image("res/Main_menu_buttons/play_pressed.png");
	    help = Image.loadImage("res/Main_menu_buttons/quit.png");
	    //quit_hover = new Image("res/Main_menu_buttons/quit_hover.png");
	    //quit_pressed = new Image("res/Main_menu_buttons/quit_pressed.png");
	    mode = Image.loadImage("res/Main_menu_buttons/options.png");
	    //option_hover = new Image("res/Main_menu_buttons/options_hover.png");
	    //option_pressed = new Image("res/Main_menu_buttons/options_pressed.png");
	    }

	    public void render(GameContainer gamecontainer1, StateBasedGame statebasedgame1, Graphics g1) throws SlickException{
	            bg.draw(0,0);
	            play.draw(205,206);
	            mode.draw(205,286);
	            help.draw(205, 366);
	            if(playHover) play_hover.draw(205, 206);
	            Game_Title.draw(0,-100);
	            //Mouse Position
	            g1.drawString(mousePos, 10, 25);
	        }


	    public void update(GameContainer gamecontainer1, StateBasedGame statebasedgame1, int delta1) throws SlickException{ 
	        playHover = false;
	        if(playRect.contains(Mouse.getX(), Mouse.getY()) {
	            playHover = true;
	            if(Mouse.isbuttonDown(0)) {
	                System.out.println("The button was pressed!");
	            }
	        }
	    }
	    public int getID(){
	        return 1;
	    }

	
	
	
	
}
