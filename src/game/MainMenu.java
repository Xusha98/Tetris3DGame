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
	static MousePicker mp = new MousePicker(window, renderer.getProjectionMatrix(), cam);
	
	
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
		mp.update();
		System.out.println(mp.getCurrentRay());

	}

	public static void update()  {
		

	}

	public int getID() {
		return 1;
	}

}
