package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjglx.input.Mouse;
import org.lwjglx.util.glu.GLU;
import org.lwjglx.util.vector.Vector2f;
import org.newdawn.slick.SlickException;

import io.Image;
import io.Window;
import maths.Vector3f;
import models.ModelEntity;
import models.TexturedModel;
import rendering.TextureLoader;

public class MainMenu extends Game {

	static boolean cheatMode_active = false;
	static ModelEntity bg;
	static ModelEntity play_button;
	static ModelEntity normal_mode_button;
	static ModelEntity cheat_mode_button;
	static ModelEntity help_button;
	private static ArrayList<ModelEntity> menuModels = new ArrayList<>();
	
	
	
	/*
	 * static Image bg,Game_Title; static Image play_button,play_hover,play_pressed;
	 * static Image normal_mode_button,option_hover,option_pressed; static Image
	 * cheat_mode_button; static Image help_button,quit_hover,quit_pressed;
	 */
	private static final int BUTTON_WIDTH = 35;
	private static final int BUTTON_HEIGHT = 35;

	public static void init() {
			
		TexturedModel background = new TexturedModel(new float[] { -10, 10, 0, // TOP LEFT V0
																	10, 10, 0, // TOP RIGHT V1
																	10, -10, 0, // BOTTOM RIGHT V2
																	-10, -10, 0 },
															new float[] { 0, 0, // TOP LEFT V0
																1, 0, // TOP RIGHT V1
																1, 1, // BOTTOM RIGHT V2
																0, 1}, 
															new int[] { 0, 1, 2, // Triangle 1
																		2, 3, 0 // Triangle 2
															}, "menuBackground.png");
		TexturedModel play_model = new TexturedModel(new float[] {-7.5f, 1, 0, // TOP LEFT V0
																 	7.5f, 1, 0, // TOP RIGHT V1
																	7.5f, -1, 0, // BOTTOM RIGHT V2
																	-7.5f, -1, 0 },
																	new float[] { 0, 0, // TOP LEFT V0
																				1, 0, // TOP RIGHT V1
																				1, 1, // BOTTOM RIGHT V2
																				0, 1}, 
																	new int[] { 0, 1, 2, // Triangle 1
																			2, 3, 0 // Triangle 2
																	},"play.png");
		TexturedModel help_model = new TexturedModel(new float[] { -7.5f, 1, 0, // TOP LEFT V0
			 														7.5f, 1, 0, // TOP RIGHT V1
			 														7.5f, -1, 0, // BOTTOM RIGHT V2
																	-7.5f, -1, 0 },
																	new float[] { 0, 0, // TOP LEFT V0
																			1, 0, // TOP RIGHT V1
																			1, 1, // BOTTOM RIGHT V2
																			0, 1}, 
																	new int[] { 0, 1, 2, // Triangle 1
																			2, 3, 0 // Triangle 2
																			},"help.png");
		TexturedModel normal_mode = new TexturedModel(new float[] { -7.5f, 1, 0, // TOP LEFT V0
			 														7.5f, 1, 0, // TOP RIGHT V1
			 														7.5f, -1, 0, // BOTTOM RIGHT V2
			 														-7.5f, -1, 0 // TOP LEFT V0
																	 },
																	new float[] { 0, 0, // TOP LEFT V0
																				1, 0, // TOP RIGHT V1
																				1, 1, // BOTTOM RIGHT V2
																				0, 1}, 
																	new int[] { 0, 1, 2, // Triangle 1
																			2, 3, 0 // Triangle 2
																			},"normalMode.png");
		TexturedModel cheat_mode = new TexturedModel(new float[] { -7.5f, 1, 0, // TOP LEFT V0
																	7.5f, 1, 0, // TOP RIGHT V1
																	7.5f, -1, 0, // BOTTOM RIGHT V2
																	-7.5f, -1, 0 },
																	new float[] { 0, 0, // TOP LEFT V0
																			1, 0, // TOP RIGHT V1
																			1, 1, // BOTTOM RIGHT V2
																			0, 1}, 
																	new int[] { 0, 1, 2, // Triangle 1
																			2, 3, 0 // Triangle 2
																			},"cheatMode.png");
	
		
		
		ModelEntity bg = new ModelEntity(background, new Vector3f(7.5f, 20, -1.0f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		menuModels.add(bg);
		
		ModelEntity play_button = new ModelEntity(play_model,new Vector3f(7.5f, 25, -1.1f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		menuModels.add(play_button);
		
		ModelEntity help_button = new ModelEntity(help_model,new Vector3f(7.5f, 22, -1.1f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		menuModels.add(help_button);
		
		ModelEntity normal_mode_button = new ModelEntity(normal_mode, new Vector3f(7.5f, 19, -1.1f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		menuModels.add(normal_mode_button);
		
		ModelEntity cheat_mode_button = new ModelEntity(cheat_mode, new Vector3f(7.5f, 16, -1.1f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)); 
		menuModels.add(cheat_mode_button);
		

	}

	public static void render() {

		init();
		
		
		for (ModelEntity menu : menuModels) {
			
			renderer.renderModelEntity(menu);
			menu.addRotation(0, 0, 0);
			
		}
		
		
		//drawMenu();
/**
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, bg.getTextureID());
		
		 GL11.glEnable(GL11.GL_TEXTURE_2D); 
		 GL11.glMatrixMode(GL11.GL_PROJECTION);
		 GL11.glLoadIdentity(); 
		  // Create a new perspective with 30 degree angle
		  //(field of view), 640 / 480 aspect ratio, 0.001f zNear, 100 zFar // Note: +x
		 //* is to the right // +y is to the top // +z is to the camera 
		 GL11.glOrtho(-1, 1, -1, 1, -1, 1); 
		 GLU.gluPerspective((float) 0, WIDTH / HEIGHT, 0.001f, 100);
		 GL11.glMatrixMode(GL11.GL_MODELVIEW);
		 
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		// set the color of the quad (R,G,B,A)
		// GL11.glColor3f(1.0f,0.0f,1.0f);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(0, 0);

		GL11.glTexCoord2f(20, 0);
		GL11.glVertex2f(20, 0);

		GL11.glTexCoord2f(0, 20);
		GL11.glVertex2f(20, 20);

		GL11.glTexCoord2f(20, 20);
		GL11.glVertex2f(0, 20);
		GL11.glEnd();

		// GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, help_button.getTextureID());

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(5, 5, -0.1f);

		GL11.glTexCoord2f(0, 250);
		GL11.glVertex3f(15, 5, -0.1f);

		GL11.glTexCoord2f(35, 0);
		GL11.glVertex3f(15, 7, -0.1f);

		GL11.glTexCoord2f(35, 250);
		GL11.glVertex3f(5, 7, -0.1f);
		GL11.glEnd();

		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, play_button.getTextureID());

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(5, 13, -0.1f);

		GL11.glTexCoord2f(0, 250);
		GL11.glVertex3f(5, 15, -0.1f);

		GL11.glTexCoord2f(35, 0);
		GL11.glVertex3f(15, 15, -0.1f);

		GL11.glTexCoord2f(35, 250);
		GL11.glVertex3f(15, 13, -0.1f);
		GL11.glEnd();

		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, cheat_mode_button.getTextureID());

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(5, 9, -0.1f);

		GL11.glTexCoord2f(0, 250);
		GL11.glVertex3f(5, 11, -0.1f);

		GL11.glTexCoord2f(35, 0);
		GL11.glVertex3f(15, 11, -0.1f);

		GL11.glTexCoord2f(35, 250);
		GL11.glVertex3f(15, 9, -0.1f);
		GL11.glEnd();

		/**
		 * Graphics2D g2 = (Graphics2D) g; //Image.drawImage(bg,0,0, 205,206);
		 * 
		 * //Image.drawImage(play_button,0,0, 205,206); // Image.drawImage(help_button,
		 * 0, 0, 205, 366);
		 * 
		 * playRectangle = new Rectangle(205, 206, 207, 208); modeRectangle = new
		 * Rectangle(205, 206, 207, 208); helpRectangle = new Rectangle(205, 206, 207,
		 * 208); g2.draw(playRectangle); //play-button g2.draw(modeRectangle); //
		 * mode-button g2.draw(helpRectangle); // help-button
		 * 
		 * g2.drawString("Lol", 0, 0);
		 * 
		 * int x = 0; int y = 0; int z = 0; Vector3f point1 = new Vector3f(x, y, z);
		 * Vector3f point2 = new Vector3f(x + BUTTON_WIDTH, y, z); Vector3f point3 = new
		 * Vector3f(x + BUTTON_WIDTH, y + BUTTON_HEIGHT, z);
		 * 
		 * 
		 * cam.setPosition(0,0,0);
		 * 
		 * TexturedModel button = new TexturedModel(new float[] {0f,0f,0f, 0f,0f,0f},
		 * new float[] {0f,1f,1f,0f}, null, "cheatMode.png"); ModelEntity button2 = new
		 * ModelEntity(button, point1, point2, point3);
		 * renderer.renderModelEntity(button2);
		 * 
		 * 
		 * 
		 * GL11.glBegin(GL11.GL_QUADS); // >> glVertex commands are used within
		 * glBegin/glEnd pairs to specify point, line, and polygon vertices. // >>
		 * glColor sets the current colour. (All subsequent calls to glVertex will be
		 * assigned this colour) // >> The number after 'glVertex'/'glColor' indicates
		 * the amount of components. (xyzw/rgba) // >> The character after the number
		 * indicates the type of arguments. // >> (for 'glVertex' = d: Double, f: Float,
		 * i: Integer) // >> (for 'glColor' = d: Double, f: Float, b: Signed Byte, ub:
		 * Unsigned Byte) GL11.glColor3f(1.0f, 0.0f, 0.0f); // Pure Green
		 * GL11.glVertex2i(0, 0); // Upper-left GL11.glColor3b((byte) 0, (byte) 127,
		 * (byte) 0); // Pure Red GL11.glVertex2d(10, 0.0); // Upper-right
		 * GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255); // White
		 * GL11.glVertex2f(10, 20); // Bottom-right GL11.glColor3d(0.0d, 0.0d, 1.0d); //
		 * Pure Blue GL11.glVertex2i(0, 20); // Bottom-left // If we put another four
		 * calls to glVertex2i here, a second quadrilateral will be drawn. GL11.glEnd();
		 **/

	}

	public static void update()  {
		/**	
		cheatMode_active = false;
		if (playRectangle.contains(Mouse.getX(), Mouse.getY()) && Mouse.isButtonDown(0)) {

		}
		if (modeRectangle.contains(Mouse.getX(), Mouse.getY()) && Mouse.isButtonDown(0)) {
			cheatMode_active = true;
			// cheat_mode_button.draw(205,206);
			System.out.println("Cheat mode is on!");
		}**/

	}

	public int getID() {
		return 1;
	}
/**
	public static void drawMenu() {

		GL11.glPushMatrix();

		FloatBuffer buf = BufferUtils.createFloatBuffer(16 * 4);

		//Get your current model view matrix from OpenGL. 

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

		//now draw your stuff here that needs billboarding

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, bg.getTextureID());

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);

		GL11.glVertex3f(0, 0, -3.0f);

		GL11.glTexCoord2f(20, 0);

		GL11.glVertex3f(20, 0, -3.0f);

		GL11.glTexCoord2f(0, 20);

		GL11.glVertex3f(20, 20, -3.0f);

		GL11.glTexCoord2f(20, 20);

		GL11.glVertex3f(0, 20, -3.0f);

		GL11.glEnd();

		// GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, help_button.getTextureID());

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);

		GL11.glVertex3f(5, 5, -3.1f);

		GL11.glTexCoord2f(0, 1);

		GL11.glVertex3f(15, 5, -3.1f);

		GL11.glTexCoord2f(1, 1);

		GL11.glVertex3f(15, 7, -3.1f);

		GL11.glTexCoord2f(1, 0);

		GL11.glVertex3f(5, 7, -3.1f);

		GL11.glEnd();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, play_button.getTextureID());

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);

		GL11.glVertex3f(5, 13, -3.1f);

		GL11.glTexCoord2f(0, 1);

		GL11.glVertex3f(5, 15, -3.1f);

		GL11.glTexCoord2f(1, 1);

		GL11.glVertex3f(15, 15, -3.1f);

		GL11.glTexCoord2f(1, 0);

		GL11.glVertex3f(15, 13, -3.1f);

		GL11.glEnd();

		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, cheat_mode_button.getTextureID());

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);

		GL11.glVertex3f(5, 9, -3.1f);

		GL11.glTexCoord2f(0, 1);

		GL11.glVertex3f(5, 11, -3.1f);

		GL11.glTexCoord2f(1, 1);

		GL11.glVertex3f(15, 11, -3.1f);

		GL11.glTexCoord2f(1, 0);

		GL11.glVertex3f(15, 9, -3.1f);

		GL11.glEnd();

		GL11.glDisable(GL11.GL_TEXTURE_2D);

	} **/

}
