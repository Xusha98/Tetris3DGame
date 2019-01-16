package main;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.nio.FloatBuffer;

import javax.swing.JFrame;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjglx.input.Mouse;
import org.lwjglx.opengl.AWTGLCanvas;
import org.lwjglx.util.glu.GLU;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.newdawn.slick.SlickException;

import engine.io.Image;
import engine.io.Window;
import engine.maths.Vector3f;
import engine.rendering.Material;
import engine.rendering.models.MenuItemModel;
import engine.rendering.models.ModelEntity;
import engine.rendering.models.TexturedModel;


public class MainMenu extends Game {

	 	static boolean cheatMode_active = false;
	 	static Rectangle playRectangle, modeRectangle, helpRectangle;
	 	static Material bg;
	    static Material play_button;
	    static Material normal_mode_button;
	    static Material cheat_mode_button;
	    static Material help_button;
	    private static final int BUTTON_WIDTH = 250;
	    private static final int BUTTON_HEIGHT = 35;

	    
		public static void init() {

			
		bg = new Material("menuBackground.png");
		play_button  = new Material("play.png");
		help_button = new Material("help.png");
		normal_mode_button = new Material("normalMode.png");
		cheat_mode_button = new Material("cheatMode.png");

	    }

	    public static void render(){
	    	
	    	
	    	init(); 
	    	
	    	Vector3f up = new Vector3f(0, 1, 0); // <- cylinder axis
	    	Matrix4f modelMatrix = new Matrix4f();
	    	//modelMatrix.billboardCylindrical(new Vector3f(0,0,0), cam.getPosition(), up);
	    	//GL11.glLoadMatrixf(modelMatrix);
	    
	    	drawMenu();
	    
	    	cam.setPosition(new Vector3f(15, 20, -10));
	    	
	   
	    		// switch to 2d drawing
	    	//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);	
	    	//GL11.glEnable(GL11.GL_TEXTURE_2D);
	    	
	    	//GL11.glBindTexture(GL11.GL_TEXTURE_2D, bg.getTextureID());
	    	/**
	    	GL11.glEnable(GL11.GL_TEXTURE_2D);
	    	GL11.glMatrixMode(GL11.GL_PROJECTION);
	    	GL11.glLoadIdentity();
	        // Create a new perspective with 30 degree angle (field of view), 640 / 480 aspect ratio, 0.001f zNear, 100 zFar
	        // Note: 	+x is to the right
	        //     		+y is to the top
	        //			+z is to the camera
	    	GL11.glOrtho(-1, 1, -1, 1, -1, 1);
	    	GLU.gluPerspective((float) 0, WIDTH / HEIGHT, 0.001f, 100);
	    	GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    	**/
	    	
	    	 
	    	// set the color of the quad (R,G,B,A)
	    	//GL11.glColor3f(1.0f,0.0f,1.0f);
	    	/**
	    	
	    		/**
	    		Graphics2D g2 = (Graphics2D) g;
	    		//Image.drawImage(bg,0,0, 205,206);

	            //Image.drawImage(play_button,0,0, 205,206);
	           // Image.drawImage(help_button, 0, 0, 205, 366);

	    		playRectangle = new Rectangle(205, 206, 207, 208);
	    		modeRectangle = new Rectangle(205, 206, 207, 208);
	    		helpRectangle = new Rectangle(205, 206, 207, 208);
	            g2.draw(playRectangle); //play-button
	            g2.draw(modeRectangle); // mode-button
	            g2.draw(helpRectangle); // help-button
			
	            g2.drawString("Lol", 0, 0);
	         
	    		int x = 0;
	    		int y = 0;
	    		int z = 0;
	    		Vector3f point1 = new Vector3f(x, y, z);
	            Vector3f point2 = new Vector3f(x + BUTTON_WIDTH, y, z);
	            Vector3f point3 = new Vector3f(x + BUTTON_WIDTH, y + BUTTON_HEIGHT, z);
	            
	    		
	            cam.setPosition(0,0,0);
	    		
	            TexturedModel button = new TexturedModel(new float[] {0f,0f,0f,
	            													  0f,0f,0f}, 
	            		new float[] {0f,1f,1f,0f},
	            		null, 
	            		"cheatMode.png");
	            ModelEntity button2 = new ModelEntity(button, point1, point2, point3);
	            renderer.renderModelEntity(button2);
				  
	    		
	    		
	    		 GL11.glBegin(GL11.GL_QUADS);
	             // >> glVertex commands are used within glBegin/glEnd pairs to specify point, line, and polygon vertices.
	             // >> glColor sets the current colour. (All subsequent calls to glVertex will be assigned this colour)
	             // >> The number after 'glVertex'/'glColor' indicates the amount of components. (xyzw/rgba)
	             // >> The character after the number indicates the type of arguments.
	             // >>      (for 'glVertex' = d: Double, f: Float, i: Integer)
	             // >>      (for 'glColor'  = d: Double, f: Float, b: Signed Byte, ub: Unsigned Byte)
	    		 	GL11.glColor3f(1.0f, 0.0f, 0.0f);                    // Pure Green
	    		 	GL11.glVertex2i(0, 0);                               // Upper-left
	    		 	GL11.glColor3b((byte) 0, (byte) 127, (byte) 0);      // Pure Red
	    		 	GL11.glVertex2d(10, 0.0);                         // Upper-right
	    		 	GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255); // White
	    		 	GL11.glVertex2f(10, 20);                     // Bottom-right
	    		 	GL11.glColor3d(0.0d, 0.0d, 1.0d);                    // Pure Blue
	    		 	GL11.glVertex2i(0, 20);                             // Bottom-left
	             // If we put another four calls to glVertex2i here, a second quadrilateral will be drawn.
	             GL11.glEnd();
	    		 **/
	    }
	        

	    public static void update() throws SlickException{
	        cheatMode_active = false;
	        if(playRectangle.contains(Mouse.getX(), Mouse.getY()) && Mouse.isButtonDown(0)) {

	        }
	        if(modeRectangle.contains(Mouse.getX(), Mouse.getY()) && Mouse.isButtonDown(0)) {
	        	cheatMode_active = true;
	        	//cheat_mode_button.draw(205,206);
	            System.out.println("Cheat mode is on!");
	        }

	    }
	    
	    
	    public static void drawMenu() {
	    	
	    	


	    	GL11.glPushMatrix();

	    	FloatBuffer buf = BufferUtils.createFloatBuffer(16 * 4);
// Get your current model view matrix from OpenGL. 
	    	GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, buf);
	    	buf.rewind();

	    	buf.put(0, 10.0f);
	    	buf.put(1, 0.0f);
	    	buf.put(2, 0.0f);

	    	buf.put(4, 0.0f);
	    	buf.put(5, 10.0f);
	    	buf.put(6, 0.0f);
         
	    	buf.put(8, 0.0f);
	    	buf.put(9, 0.0f);
	    	buf.put(10, 10.0f);
         
	    	GL11.glLoadMatrixf(buf);

// now draw your stuff here that needs billboarding
	    	
	    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, bg.getTextureID());
	    	
		    GL11.glBegin(GL11.GL_QUADS);
	    		GL11.glTexCoord2f(0, 0);
	        	GL11.glVertex3f(0,0,-3.0f);
	        	
	        	GL11.glTexCoord2f(20, 0);
	        	GL11.glVertex3f(20,0,-3.0f);
	        	
	        	GL11.glTexCoord2f(0, 20);
	        	GL11.glVertex3f(20,20,-3.0f);
	        	
	        	GL11.glTexCoord2f(20, 20);
	        	GL11.glVertex3f(0,20,-3.0f);
	        GL11.glEnd();
	        
	       // GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);	
	   
	    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, help_button.getTextureID());
	        
	        GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(5,5,-3.1f);
	    	
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(15,5,-3.1f);
	    	
				GL11.glTexCoord2f(1,1);
				GL11.glVertex3f(15,7,-3.1f);
	    	
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(5,7,-3.1f);
			GL11.glEnd();
	        
		
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, play_button.getTextureID());
	        
	        GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(5,13,-3.1f);
	    	
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(5,15,-3.1f);
	    	
				GL11.glTexCoord2f(1,1);
				GL11.glVertex3f(15,15,-3.1f);
	    	
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(15,13,-3.1f);
			GL11.glEnd();
			
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, cheat_mode_button.getTextureID());
			
	        GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(5,9,-3.1f);
	    	
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(5,11,-3.1f);
	    	
				GL11.glTexCoord2f(1,1);
				GL11.glVertex3f(15,11,-3.1f);
	    	
				GL11.glTexCoord2f(1,0);
				GL11.glVertex3f(15,9,-3.1f);
			GL11.glEnd();
	       
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			
	    	
	    }
	    
	    public void updateMenu() {
	    	
	    	int x = Mouse.getX();
	    	int y = Mouse.getY();
	   
	        if(x < 20 && y < 20){
	         
	        	state = GameState.GAME;
	        }
	    }
	    
	    
	    public int getID(){
	        return 1;
	    }
	    
	    
	    

}
