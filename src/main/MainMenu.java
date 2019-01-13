package main;

import java.awt.Graphics;
import java.awt.Rectangle;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.input.Mouse;
import org.newdawn.slick.SlickException;

import engine.io.Image;
import engine.io.Window;

public class MainMenu {

	 	static boolean cheatMode_active = false;
	 	static Rectangle playRectangle, modeRectangle, helpRectangle;
	 	static Image bg,Game_Title;
	    static Image play_button,play_hover,play_pressed;
	    static Image normal_mode_button,option_hover,option_pressed;
	    static Image cheat_mode_button;
	    static Image help_button,quit_hover,quit_pressed;
	    //private int WIDTH, HEIGHT, FPS;
	    
	   /**
	    public MainMenu(int WIDTH, int HEIGHT, int FPS) {
			super();
			this.WIDTH = WIDTH;
			this.HEIGHT = HEIGHT;
			this.FPS = FPS;
		}**/

		private static void init() throws SlickException{
	    	
	    bg = Image.loadImage("resources/textures/menuBackground.png");
	    play_button = Image.loadImage("resources/buttons/play.png");
	    help_button = Image.loadImage("resources/buttons/help.png");

	    normal_mode_button = Image.loadImage("resources/buttons/normalMode.png");
	    cheat_mode_button = Image.loadImage("resources/buttons/cheatMode.png");

	    }

	    public static void render(Window window, Graphics g) throws SlickException{
	    	
	    		init();
	    		
	            //bg.draw(0,0);
	    		//playRectangle.drawRect(205, 206, 207, 208);
	            //play_button.draw(205,206);
	            //normal_mode_button.draw(205,286);
	            //help_button.draw(205, 366);
	   
	        }


	    public static void update(Window window) throws SlickException{ 
	        cheatMode_active = false;
	        if(playRectangle.contains(Mouse.getX(), Mouse.getY()) && Mouse.isButtonDown(0)) {
	        	
	        }
	        if(modeRectangle.contains(Mouse.getX(), Mouse.getY()) && Mouse.isButtonDown(0)) {
	        	cheatMode_active = true;
	        	//cheat_mode_button.draw(205,206);
	            System.out.println("Cheat mode is on!");
	        }
	        
	    }
	    public int getID(){
	        return 1;
	    }

	
	
	
	
}
