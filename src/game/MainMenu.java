package game;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.input.Mouse;
import maths.Vector3f;
import models.ModelEntity;
import models.TexturedModel;

public class MainMenu extends Game {

	static boolean cheatMode_active = false;
	static ModelEntity bg;
	static ModelEntity play_button;
	static ModelEntity normal_mode_button;
	static ModelEntity cheat_mode_button;
	static ModelEntity help_button;
	static ModelEntity help_view;
	private static ArrayList<ModelEntity> menuModels = new ArrayList<>();



	/**
	 * static Image bg,Game_Title; static Image play_button,play_hover,play_pressed;
	 * static Image normal_mode_button,option_hover,option_pressed; static Image
	 * cheat_mode_button; static Image help_button,quit_hover,quit_pressed;

	private static final int BUTTON_WIDTH = 35;
	private static final int BUTTON_HEIGHT = 35;
**/
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

		TexturedModel help = new TexturedModel(new float[] { -10, 10, 0, // TOP LEFT V0
				10, 10, 0, // TOP RIGHT V1
				10, -10, 0, // BOTTOM RIGHT V2
				-10, -10, 0 },
		new float[] { 0, 0, // TOP LEFT V0
			1, 0, // TOP RIGHT V1hhhhh
			1, 1, // BOTTOM RIGHT V2
			0, 1},
		new int[] { 0, 1, 2, // Triangle 1
					2, 3, 0 // Triangle 2
		}, "helpBackground.png");



		bg = new ModelEntity(background, new Vector3f(7.5f, 20, 0.0f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		menuModels.add(bg);

		play_button = new ModelEntity(play_model,new Vector3f(7.5f, 25, -0.1f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		menuModels.add(play_button);

		help_button = new ModelEntity(help_model,new Vector3f(7.5f, 22, -0.1f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		menuModels.add(help_button);

		normal_mode_button = new ModelEntity(normal_mode, new Vector3f(7.5f, 19, -0.1f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		menuModels.add(normal_mode_button);

		cheat_mode_button = new ModelEntity(cheat_mode, new Vector3f(7.5f, 16, -0.1f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		menuModels.add(cheat_mode_button);

		help_view = new ModelEntity(help, new Vector3f(7.5f, 20, -1.0f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
		//menuModels.add(cheat_mode_button);



	}

	public static void render() {

		//window.unlockMouse();

		for (ModelEntity menu : menuModels) {

			renderer.renderModelEntity(menu);
			menu.addRotation(0, 0, 0);

		}


	}

	public static void update()  {

		if (window.isKeyDown(GLFW.GLFW_KEY_H)) {
			renderer.renderModelEntity(help_view);
			help_view.addRotation(0,0,0);
		}

	}

	public int getID() {
		return 1;
	}

}
