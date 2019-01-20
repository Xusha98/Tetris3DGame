package game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.SlickException;

import io.Window;
import maths.Vector3f;
import models.BlockFormObject;
import models.BlockManager;
import models.ModelEntity;
import models.TexturedModel;
import rendering.Camera;
import rendering.Renderer;
import shader.BasicShader;

public class Game {

	private static List<ModelEntity> background = new ArrayList<>();
	public static List<TexturedModel> backgroundModels = new ArrayList<>();

	protected static final int WIDTH = 800, HEIGHT = 600, FPS = 60;
	protected static Window window = new Window(WIDTH, HEIGHT, FPS, "3D Tetris");
	protected static BasicShader shader = new BasicShader();
	protected static Renderer renderer = new Renderer(window, shader);
	protected static Camera cam = new Camera();

	private static BlockManager blockManager = new BlockManager();
	private static BlockFormObject currentMovingBlocks;

	// TODO: muss spaeter zu MainMenu gesetzt werden, auf Game zu testzwecken
	// gestellt
	private static GameState state = GameState.GAME;
	private static GameMode mode = GameMode.NORMAL;

	private static boolean stopTime = false;
	private static boolean newBlock = true;
	
	private static boolean[][][] fieldOccupied = new boolean[9][9][9];

	public static void init() {

		window.setBackgroundColor(0.0f, 0.0f, 0.0f);
		window.setIcon("icon.png");
		window.create();
		window.lockMouse();
		shader.create();

		setBackground();

	}

	public static float x = 0;
	public static float z = 0;
	public static float y = 0;
	public static float rX = 0;
	public static float rZ = 0;
	public static float rY = 0;

	public static void run() {

		init();

		while (!window.closed()) {
			if (window.isUpdating()) {
				x = 0;
				z = 0;
				rX = 0;
				rZ = 0;
				rY = 0;

				window.update();
				renderer.update();

				cam.update(window);
				renderer.loadCamera(cam);
				input();

				shader.bind();

				switch (state) {

				case PAUSE:
					// TODO: Pausebild laden?
					GL15.glColor3f(1.0f, 0.0f, 0.0f);
					GL15.glRectf(0, 0, 640, 480);
					break;

				/*-------------------------------------------------------------------------------------------------------------------------------------------------------------*/
				case GAME:
					for (ModelEntity me : background) {
						renderer.renderModelEntity(me);
					}
					if(currentMovingBlocks != null) {
						for(ModelEntity me : currentMovingBlocks.getBlocks()) {
							if(me.isHasFinalPos())
								currentMovingBlocks.setHasFinalPos(true);
						}
					}
					if(currentMovingBlocks != null && currentMovingBlocks.isHasFinalPos()) {
						newBlock = true;
					}
					if(newBlock) {
						blockManager.createNewBlockForm();
						currentMovingBlocks = blockManager.getBlockFormObjects().get(blockManager.getBlockFormObjects().size() - 1);
						newBlock = false;
					}
					
					//controllFields();

					switch (mode) {

					case CHEAT:
						// Zeit laeuft
						if (!stopTime) {
							for (ModelEntity me : blockManager.getAllBlocks()) {
								renderer.renderModelEntity(me);
								me.addRotation(2, 2, 2);
							}
						}
						// Zeit gestoppt
						else {
							for (ModelEntity me : blockManager.getAllBlocks()) {
								renderer.renderModelEntity(me);
								me.addRotation(0, 0, 0);
							}
						}
						break;

					/*
					 * ___________________________________________________________________________________________________________
					 */

					case NORMAL:
						for (ModelEntity me : blockManager.getAllBlocks()) {
							renderer.renderModelEntity(me);
							if(currentMovingBlocks.getBlocks().contains(me)) {
								if (!me.isHasFinalPos() && me.getPosition().getY() > -0.0000001 && me.getPosition().getY() <= 1) {
									me.setPosition(new Vector3f(me.getPosition().getX(), 1, me.getPosition().getZ()));
									me.setHasFinalPos(true);
									for(ModelEntity mE : currentMovingBlocks.getBlocks()) {
										me.setHasFinalPos(true);
									}
								} else if(!me.isHasFinalPos()) {
									me.addPosition(x, -0.02f - y, z);
									//me.addRotation(rX, rY, rZ);						
								}
							}					
						}
						break;
					}
					// GL15.glColor3f(0.0f, 1.0f, 0.0f);
					// GL15.glRectf(0, 0, 640, 480);
					break;

				/*-------------------------------------------------------------------------------------------------------------------------------------------------------------*/
				case MAIN_MENU:
					MainMenu.render();
					
					break;
				}

				shader.unbind();
				window.swapBuffers();
			}
		}

		removeAll();

	}
	
	public static void controllFields() {
		int yCoord = 0, zCoord = 0, xCoord = 0;
		for(ModelEntity me : blockManager.getAllBlocks()) {
			yCoord = (int) me.getPosition().getY();
			zCoord = (int) me.getPosition().getZ();
			xCoord = (int) me.getPosition().getX();
			
			int indexY = yCoord / 2;
			int indexZ = zCoord / 2;
			int indexX = xCoord / 2;
			
			if(me.isHasFinalPos()) {
				fieldOccupied[indexY][indexZ][indexX] = true;
			}
			else {
				if(indexY < 9) {
					fieldOccupied[indexY][indexZ][indexX] = false;
				}				
			}
		}
		
		int sum = 0;
		for(int y = 0; y < 9; y++) {
			sum = 0;
			for(int z = 0; z < 9; z++) {
				for(int x = 0; x < 9; x++) {
					if(fieldOccupied[y][z][x]) {
						sum += 1;
					}
				}
			}
			if(sum == 81) {
				System.out.println("ein Layer gefuellt");
			}
		}
	}

	/*
	 * Achtung: einiges an Input auch in Camera Klasse
	 *
	 * Vergebene Tasten:
	 * 
	 * UP: Block bewegt sich in positive z-Richtung 
	 * DOWN: Block bewegt sich in negative z-Richtung 
	 * LEFT: Block bewegt sich in negative x-Richtung 
	 * RIGHT: Block bewegt sich in positive x-Richtung 
	 * 2: Block bewegt sich schneller nach unten
	 * 
	 * W: Kamera bewegt sich vorwaerts 
	 * S: Kamera bewegt sich rueckwaerts 
	 * A: Kamera bewegt sich nach links 
	 * D: Kamera bewegt sich nach rechts 
	 * SPACE: Kamera bewegt sich nach oben 
	 * LEFT SHIFT: Kamera bewegt sich nach unten
	 *
	 * U: Maus angezeigt 
	 * L: Maus verschwindet 
	 * ENTER: Gamestate switcht von Main menu zu Game oder umgekehrt 
	 * P: Spiel pausiert oder beendet Pausierung
	 */
	public static void input() {
		if (window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
			window.close();
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_U)) {
			window.unlockMouse();
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_L)) {
			window.lockMouse();
		}

		if (window.isKeyPressed(GLFW.GLFW_KEY_ENTER)) {
			if (state == GameState.MAIN_MENU) {
				state = GameState.GAME;
				System.out.println("Current state is:" + state);
			} else if (state == GameState.GAME) {
				state = GameState.MAIN_MENU;
				System.out.println("Current state is:" + state);
			}

		}

		if (window.isKeyPressed(GLFW.GLFW_KEY_P)) {
			if (state == GameState.GAME) {
				state = GameState.PAUSE;
				System.out.println("Game is paused!");
			} else if (state == GameState.PAUSE) {
				state = GameState.GAME;
				System.out.println("Game resumed!");
			}
		}

		if (window.isKeyPressed(GLFW.GLFW_KEY_T)) {
			if (mode == GameMode.CHEAT) {
				if (stopTime)
					stopTime = false;
				else
					stopTime = true;
				System.out.println("Current mode is:" + mode + ". Time is turned off. StopTime: " + stopTime);
			} else if (mode == GameMode.NORMAL) {
				System.out.println("Current mode is:" + mode + " Time can not be turned off.");
			}

		}

		if (window.isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
			if (blockManager.getMinX(currentMovingBlocks) != 1)
				x = -2.0f;
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
			if (blockManager.getMaxX(currentMovingBlocks) != 17)
				x = +2.0f;
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_UP)) {
			if (blockManager.getMaxZ(currentMovingBlocks) != 17)
				z = +2.0f;
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
			if (blockManager.getMinZ(currentMovingBlocks) != 1)
				z = -2.0f;
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_B)) {
			rX += 1.0f;
		}
		if (window.isKeyReleased(GLFW.GLFW_KEY_N)) {
			rZ += 1.0f;
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_M)) {
			rY += 1.0f;
		}

		// Testinput zum Generieren eines Blocks
		if (window.isKeyPressed(GLFW.GLFW_KEY_1)) {
			blockManager.createNewBlockForm();
			currentMovingBlocks = blockManager.getBlockFormObjects().get(blockManager.getBlockFormObjects().size() - 1);
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_2)) {

			y = 0.2f;

		}

		if (window.isKeyReleased(GLFW.GLFW_KEY_2)) {

			y = 0;

		}
	}

	public static void setBackground() {
		for (int i = 1; i <= 18; i = i + 2) {
			for (int j = 1; j <= 18; j = j + 2) {
				TexturedModel model = new TexturedModel(new float[] { -1.0f, 0, 1.0f, // TOP LEFT V0
						1.0f, 0, 1.0f, // TOP RIGHT V1
						1.0f, 0, -1.0f, // BOTTOM RIGHT V2
						-1.0f, 0, -1.0f // BOTTOM LEFT V3
				}, new float[] { 0, 0, // TOP LEFT V0
						1, 0, // TOP RIGHT V1
						1, 1, // BOTTOM RIGHT V2
						0, 1 // BOTTOM LEFT V3
				}, new int[] { 0, 1, 2, // Triangle 1
						2, 3, 0 // Triangle 2
				}, "fieldElement.png");
				backgroundModels.add(model);
				ModelEntity entity = new ModelEntity(model, new Vector3f(j, 0, i), new Vector3f(0, 0, 0),
						new Vector3f(1, 1, 1));
				background.add(entity);
			}
		}
		
		for(int y = 0; y < 9; y++) {
			for(int z = 0; z < 9; z++) {
				for(int x = 0; x < 9; x++) {
					fieldOccupied[y][z][x] = false;
				}
			}
		}
	}

	public static void removeAll() {
		for (TexturedModel tm : backgroundModels) {
			tm.remove();
		}
		for (TexturedModel tm : blockManager.getAllModels()) {
			tm.remove();
		}
		shader.remove();
		window.stop();
	}

	/*
	 * Aus der init():
	 * 
	 * TexturedModel model = new TexturedModel(new float[] { -1.0f, 1.0f, -1.0f,
	 * //V0 -1.0f, -1.0f, -1.0f, //V1 1.0f, -1.0f, -1.0f, //V2 1.0f, 1.0f, -1.0f,
	 * //V3 -1.0f, 1.0f, 1.0f, //V4 -1.0f, -1.0f, 1.0f, //V5 1.0f, -1.0f, 1.0f, //V6
	 * 1.0f, 1.0f, 1.0f, //V7 1.0f, 1.0f, -1.0f, //V3 1.0f, -1.0f, -1.0f, //V2 1.0f,
	 * -1.0f, 1.0f, //V6 1.0f, 1.0f, 1.0f, //V7 -1.0f, 1.0f, -1.0f, //V0 -1.0f,
	 * -1.0f, -1.0f, //V1 -1.0f, -1.0f, 1.0f, //V5 -1.0f, 1.0f, 1.0f, //V4 -1.0f,
	 * 1.0f, 1.0f, //V4 -1.0f, 1.0f, -1.0f, //V0 1.0f, 1.0f, -1.0f, //V3 1.0f, 1.0f,
	 * 1.0f, //V7 -1.0f, -1.0f, 1.0f, //V5 -1.0f, -1.0f, -1.0f, //V1 1.0f, -1.0f,
	 * -1.0f, //V2 1.0f, -1.0f, 1.0f //V6 }, new float[]{ 0f, 0f, 0f, 1f, 1f, 1f,
	 * 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f,
	 * 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f,
	 * 1f, 1f, 1f, 0f }, new int[]{ 0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11,
	 * 11, 9, 10, 12, 13, 15, 15, 13, 14, 16, 17, 19, 19, 17, 18, 20, 21, 23, 23,
	 * 21, 22 }, "blue.png"); allModels.add(model);
	 * 
	 * ModelEntity entity = new ModelEntity(model, new Vector3f(1, 1, 1), new
	 * Vector3f(0, 0, 0), new Vector3f(1, 1, 1)); blockList.add(entity);
	 * 
	 * 
	 * TexturedModel model2 = new TexturedModel(new float[] { -1.0f, 1.0f, -1.0f,
	 * //V0 -1.0f, -1.0f, -1.0f, //V1 1.0f, -1.0f, -1.0f, //V2 1.0f, 1.0f, -1.0f,
	 * //V3 -1.0f, 1.0f, 1.0f, //V4 -1.0f, -1.0f, 1.0f, //V5 1.0f, -1.0f, 1.0f, //V6
	 * 1.0f, 1.0f, 1.0f, //V7 1.0f, 1.0f, -1.0f, //V3 1.0f, -1.0f, -1.0f, //V2 1.0f,
	 * -1.0f, 1.0f, //V6 1.0f, 1.0f, 1.0f, //V7 -1.0f, 1.0f, -1.0f, //V0 -1.0f,
	 * -1.0f, -1.0f, //V1 -1.0f, -1.0f, 1.0f, //V5 -1.0f, 1.0f, 1.0f, //V4 -1.0f,
	 * 1.0f, 1.0f, //V4 -1.0f, 1.0f, -1.0f, //V0 1.0f, 1.0f, -1.0f, //V3 1.0f, 1.0f,
	 * 1.0f, //V7 -1.0f, -1.0f, 1.0f, //V5 -1.0f, -1.0f, -1.0f, //V1 1.0f, -1.0f,
	 * -1.0f, //V2 1.0f, -1.0f, 1.0f //V6 }, new float[]{ 0f, 0f, 0f, 1f, 1f, 1f,
	 * 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f,
	 * 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f,
	 * 1f, 1f, 1f, 0f }, new int[]{ 0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11,
	 * 11, 9, 10, 12, 13, 15, 15, 13, 14, 16, 17, 19, 19, 17, 18, 20, 21, 23, 23,
	 * 21, 22 }, "green.png"); allModels.add(model2);
	 * 
	 * ModelEntity entity2 = new ModelEntity(model2, new Vector3f(3, 3, 3), new
	 * Vector3f(0, 0, 0), new Vector3f(1, 1, 1)); blockList.add(entity2);
	 * 
	 * TexturedModel model3 = new TexturedModel(new float[] { -1.0f, 1.0f, -1.0f,
	 * //V0 -1.0f, -1.0f, -1.0f, //V1 1.0f, -1.0f, -1.0f, //V2 1.0f, 1.0f, -1.0f,
	 * //V3 -1.0f, 1.0f, 1.0f, //V4 -1.0f, -1.0f, 1.0f, //V5 1.0f, -1.0f, 1.0f, //V6
	 * 1.0f, 1.0f, 1.0f, //V7 1.0f, 1.0f, -1.0f, //V3 1.0f, -1.0f, -1.0f, //V2 1.0f,
	 * -1.0f, 1.0f, //V6 1.0f, 1.0f, 1.0f, //V7 -1.0f, 1.0f, -1.0f, //V0 -1.0f,
	 * -1.0f, -1.0f, //V1 -1.0f, -1.0f, 1.0f, //V5 -1.0f, 1.0f, 1.0f, //V4 -1.0f,
	 * 1.0f, 1.0f, //V4 -1.0f, 1.0f, -1.0f, //V0 1.0f, 1.0f, -1.0f, //V3 1.0f, 1.0f,
	 * 1.0f, //V7 -1.0f, -1.0f, 1.0f, //V5 -1.0f, -1.0f, -1.0f, //V1 1.0f, -1.0f,
	 * -1.0f, //V2 1.0f, -1.0f, 1.0f //V6 }, new float[]{ 0f, 0f, 0f, 1f, 1f, 1f,
	 * 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f,
	 * 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f,
	 * 1f, 1f, 1f, 0f }, new int[]{ 0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11,
	 * 11, 9, 10, 12, 13, 15, 15, 13, 14, 16, 17, 19, 19, 17, 18, 20, 21, 23, 23,
	 * 21, 22 }, "yellow.png"); allModels.add(model3);
	 * 
	 * ModelEntity entity3 = new ModelEntity(model3, new Vector3f(5, 5, 5), new
	 * Vector3f(0, 0, 0), new Vector3f(1, 1, 1)); blockList.add(entity3);
	 * 
	 * TexturedModel model4 = new TexturedModel(new float[] { -1.0f, 1.0f, -1.0f,
	 * //V0 -1.0f, -1.0f, -1.0f, //V1 1.0f, -1.0f, -1.0f, //V2 1.0f, 1.0f, -1.0f,
	 * //V3 -1.0f, 1.0f, 1.0f, //V4 -1.0f, -1.0f, 1.0f, //V5 1.0f, -1.0f, 1.0f, //V6
	 * 1.0f, 1.0f, 1.0f, //V7 1.0f, 1.0f, -1.0f, //V3 1.0f, -1.0f, -1.0f, //V2 1.0f,
	 * -1.0f, 1.0f, //V6 1.0f, 1.0f, 1.0f, //V7 -1.0f, 1.0f, -1.0f, //V0 -1.0f,
	 * -1.0f, -1.0f, //V1 -1.0f, -1.0f, 1.0f, //V5 -1.0f, 1.0f, 1.0f, //V4 -1.0f,
	 * 1.0f, 1.0f, //V4 -1.0f, 1.0f, -1.0f, //V0 1.0f, 1.0f, -1.0f, //V3 1.0f, 1.0f,
	 * 1.0f, //V7 -1.0f, -1.0f, 1.0f, //V5 -1.0f, -1.0f, -1.0f, //V1 1.0f, -1.0f,
	 * -1.0f, //V2 1.0f, -1.0f, 1.0f //V6 }, new float[]{ 0f, 0f, 0f, 1f, 1f, 1f,
	 * 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f,
	 * 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f,
	 * 1f, 1f, 1f, 0f }, new int[]{ 0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11,
	 * 11, 9, 10, 12, 13, 15, 15, 13, 14, 16, 17, 19, 19, 17, 18, 20, 21, 23, 23,
	 * 21, 22 }, "red.png"); allModels.add(model4);
	 * 
	 * ModelEntity entity4 = new ModelEntity(model4, new Vector3f(7, 7, 7), new
	 * Vector3f(0, 0, 0), new Vector3f(1, 1, 1)); blockList.add(entity4);
	 */

	/*
	 * private static TexturedModel defaultModel; private static ModelEntity
	 * defaultEntity;
	 * 
	 * public static void defaultInit() { defaultModel = new TexturedModel(new
	 * float[] { -1.0f, 1.0f, -1.0f, //V0 -1.0f, -1.0f, -1.0f, //V1 1.0f, -1.0f,
	 * -1.0f, //V2 1.0f, 1.0f, -1.0f, //V3 -1.0f, 1.0f, 1.0f, //V4 -1.0f, -1.0f,
	 * 1.0f, //V5 1.0f, -1.0f, 1.0f, //V6 1.0f, 1.0f, 1.0f, //V7 1.0f, 1.0f, -1.0f,
	 * //V3 1.0f, -1.0f, -1.0f, //V2 1.0f, -1.0f, 1.0f, //V6 1.0f, 1.0f, 1.0f, //V7
	 * -1.0f, 1.0f, -1.0f, //V0 -1.0f, -1.0f, -1.0f, //V1 -1.0f, -1.0f, 1.0f, //V5
	 * -1.0f, 1.0f, 1.0f, //V4 -1.0f, 1.0f, 1.0f, //V4 -1.0f, 1.0f, -1.0f, //V0
	 * 1.0f, 1.0f, -1.0f, //V3 1.0f, 1.0f, 1.0f, //V7 -1.0f, -1.0f, 1.0f, //V5
	 * -1.0f, -1.0f, -1.0f, //V1 1.0f, -1.0f, -1.0f, //V2 1.0f, -1.0f, 1.0f //V6 },
	 * new float[]{ 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f,
	 * 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f,
	 * 1f, 1f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f }, new int[]{ 0, 1, 3, 3,
	 * 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11, 11, 9, 10, 12, 13, 15, 15, 13, 14, 16, 17,
	 * 19, 19, 17, 18, 20, 21, 23, 23, 21, 22 }, "beautiful.png"); defaultEntity =
	 * new ModelEntity(defaultModel, new Vector3f(5, 10, 5), new Vector3f(0, 0, 0),
	 * new Vector3f(1, 1, 1)); }
	 */
}
