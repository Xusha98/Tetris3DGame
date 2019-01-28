package game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL15;
import org.lwjglx.input.Mouse;
import org.newdawn.slick.SlickException;

import audio.AudioMaster;
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
	
	public static class AudioPlayer extends Thread {
		private AudioMaster am;
		public AudioPlayer(String file) {
			am = new AudioMaster(file);
			am.init();
		}
		public void run() {
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	am.play();
			            }
			        }, 
			        100, 84000 
			);
		}
		public void clear() {
			am.destroy();
		}
	}
	
	public static class SoundPlayer extends Thread {
		private AudioMaster am;
		public SoundPlayer(String file) {
			am = new AudioMaster(file);
			am.init();
		}
		public void run() {
			am.play();
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	clear();
			            }
			        }, 
			        2000 
			);
		}
		public void clear() {
			am.destroy();
		}
	}
	

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
	private static GameState state = GameState.MAIN_MENU;
	private static GameMode mode = null; //GameMode.NORMAL;



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
		MainMenu.init();
		Pause.init();

	}

	public static float x = 0;
	public static float z = 0;
	public static float y = 0;
	
//	public static boolean runGameMusic = true;
//	public static boolean runMenuMusic = true;
	public static AudioPlayer musicThread;
	public static SoundPlayer soundThread;
	
	public static void run() {

		init();
		musicThread = new AudioPlayer("TetrisMusic");
		musicThread.start();
		
		while (!window.closed()) {
			if (window.isUpdating()) {
//				if(runMenuMusic && state == GameState.MAIN_MENU) {
//					musicThread = new AudioPlayer("MenuMusic");
//					musicThread.start();
//					runMenuMusic = false;
//				}
//				else if(runGameMusic && state == GameState.GAME) {
//					musicThread = new AudioPlayer("TetrisMusic");
//					musicThread.start();
//					runGameMusic = false;
//				}
				
				
				
				x = 0;
				z = 0;

				window.update();
				renderer.update();

				cam.update(window);
				renderer.loadCamera(cam);
				input();

				shader.bind();

				switch (state) {

				case PAUSE:
					
					Pause.render();
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
					
					controllFields();
					deleteLayer();

					switch (mode) {

					case CHEAT:
						// Zeit laeuft
						if (!stopTime) {
							for (ModelEntity me : blockManager.getAllBlocks()) {
								renderer.renderModelEntity(me);
								if(!currentMovingBlocks.getBlocks().contains(me) && !me.isHasFinalPos()) {
									float yMax = getHighestPos(me.getPosition().getX(), me.getPosition().getZ());
									if (!me.isHasFinalPos() && me.getPosition().getY() <= yMax) { // && me.getPosition().getY() > -0.0000001 TODO me.getPosition().getY() > -0.0000001 && me.getPosition().getY() <= 1
										me.setPosition(new Vector3f(me.getPosition().getX(), yMax, me.getPosition().getZ())); //TODO: 1 bei y
										me.setHasFinalPos(true);
									} else if(!me.isHasFinalPos()) {
										me.addPosition(x, -0.04f - y, z);	
									}
								}
								if(currentMovingBlocks.getBlocks().contains(me)) {
									float yMax = getHighestPos(me.getPosition().getX(), me.getPosition().getZ());
									if (!me.isHasFinalPos() && me.getPosition().getY() <= yMax) { // && me.getPosition().getY() > -0.0000001 TODO me.getPosition().getY() > -0.0000001 && me.getPosition().getY() <= 1
										me.setPosition(new Vector3f(me.getPosition().getX(), yMax, me.getPosition().getZ())); //TODO: 1 bei y
										me.setHasFinalPos(true);
										
										for(ModelEntity mE : currentMovingBlocks.getBlocks()) {
											me.setHasFinalPos(true);
										}
										for(ModelEntity mE : currentMovingBlocks.getInvisible()) {
											me.setHasFinalPos(true);
										}
										for(int i = currentMovingBlocks.getInvisible().size()-1; i >= 0; i--) {
											currentMovingBlocks.getInvisible().remove(i);
										}
									} else if(!me.isHasFinalPos()) {
										me.addPosition(x, -0.02f - y, z);					
									}
								}					
							}
							for(ModelEntity mE : currentMovingBlocks.getInvisible()) {
								if(!mE.isHasFinalPos()) {
									mE.addPosition(x, -0.02f - y, z);
								}
							}
						}
						// Zeit gestoppt
						else {
							for (ModelEntity me : blockManager.getAllBlocks()) {
								renderer.renderModelEntity(me);
								if(!currentMovingBlocks.getBlocks().contains(me) && !me.isHasFinalPos()) {
									float yMax = getHighestPos(me.getPosition().getX(), me.getPosition().getZ());
									if (!me.isHasFinalPos() && me.getPosition().getY() <= yMax) { // && me.getPosition().getY() > -0.0000001 TODO me.getPosition().getY() > -0.0000001 && me.getPosition().getY() <= 1
										me.setPosition(new Vector3f(me.getPosition().getX(), yMax, me.getPosition().getZ())); //TODO: 1 bei y
										me.setHasFinalPos(true);
									} else if(!me.isHasFinalPos()) {
										me.addPosition(x, -0.04f - y, z);	
									}
								}
								if(currentMovingBlocks.getBlocks().contains(me)) {
									float yMax = getHighestPos(me.getPosition().getX(), me.getPosition().getZ());
									if (!me.isHasFinalPos() && me.getPosition().getY() <= yMax) { // && me.getPosition().getY() > -0.0000001 TODO me.getPosition().getY() > -0.0000001 && me.getPosition().getY() <= 1
										me.setPosition(new Vector3f(me.getPosition().getX(), yMax, me.getPosition().getZ())); //TODO: 1 bei y
										me.setHasFinalPos(true);
										
										for(ModelEntity mE : currentMovingBlocks.getBlocks()) {
											me.setHasFinalPos(true);
										}
										for(ModelEntity mE : currentMovingBlocks.getInvisible()) {
											me.setHasFinalPos(true);
										}
										for(int i = currentMovingBlocks.getInvisible().size()-1; i >= 0; i--) {
											currentMovingBlocks.getInvisible().remove(i);
										}
									} else if(!me.isHasFinalPos()) {
										me.addPosition(x, -y, z);					
									}
								}					
							}
							for(ModelEntity mE : currentMovingBlocks.getInvisible()) {
								if(!mE.isHasFinalPos()) {
									mE.addPosition(x, -y, z);
								}
							}
						}
						break;

					/*
					 * ___________________________________________________________________________________________________________
					 */

					case NORMAL:
						for (ModelEntity me : blockManager.getAllBlocks()) {
							renderer.renderModelEntity(me);
							if(!currentMovingBlocks.getBlocks().contains(me) && !me.isHasFinalPos()) {
								float yMax = getHighestPos(me.getPosition().getX(), me.getPosition().getZ());
								if (!me.isHasFinalPos() && me.getPosition().getY() <= yMax) { 
									me.setPosition(new Vector3f(me.getPosition().getX(), yMax, me.getPosition().getZ())); //TODO: 1 bei y
									me.setHasFinalPos(true);
								} else if(!me.isHasFinalPos()) {
									me.addPosition(x, -0.04f - y, z);	
								}
							}
							if(currentMovingBlocks.getBlocks().contains(me)) {
								float yMax = getHighestPos(me.getPosition().getX(), me.getPosition().getZ());
								if (!me.isHasFinalPos() && me.getPosition().getY() <= yMax) { 
									me.setPosition(new Vector3f(me.getPosition().getX(), yMax, me.getPosition().getZ())); //TODO: 1 bei y
									me.setHasFinalPos(true);
									for(ModelEntity mE : currentMovingBlocks.getBlocks()) {
										me.setHasFinalPos(true);
									}
									for(ModelEntity mE : currentMovingBlocks.getInvisible()) {
										me.setHasFinalPos(true);
										
									}
									for(int i = currentMovingBlocks.getInvisible().size()-1; i >= 0; i--) {
										currentMovingBlocks.getInvisible().remove(i);
									}
								} else if(!me.isHasFinalPos()) {
									me.addPosition(x, -0.02f - y, z);	
								}
							}					
						}
						for(ModelEntity mE : currentMovingBlocks.getInvisible()) {
							if(!mE.isHasFinalPos()) {
								mE.addPosition(x, -0.02f - y, z);
							}
						}
						break;
					}
					break;

				/*-------------------------------------------------------------------------------------------------------------------------------------------------------------*/
				case MAIN_MENU:
					removeAll();
					MainMenu.render();
					MainMenu.update();
					
					break;
				}

				shader.unbind();
				window.swapBuffers();
			}
		}

		removeAll();
		finish();

	}
	
	/**
	 * wenn bewegte Bloecke Position finden, Position gespeichert
	 * checkt ob eine Flaeche mit Bloecken ausgefuellt ist
	 */
	public static void controllFields() {
		int yCoord = 0, zCoord = 0, xCoord = 0;
		if(state == GameState.GAME) {
			for(ModelEntity me : blockManager.getAllBlocks()) {
				yCoord = (int) me.getPosition().getY();
				zCoord = (int) me.getPosition().getZ();
				xCoord = (int) me.getPosition().getX();
				
				int indexY = yCoord / 2;
				int indexZ = zCoord / 2;
				int indexX = xCoord / 2;
				
				if(me.isHasFinalPos() && indexY >= 9) {
					state = GameState.MAIN_MENU;
//					musicThread.clear();
//					musicThread.interrupt();
//					runGameMusic = true;
				}
				if(me.isHasFinalPos() && indexY < 9) {
					fieldOccupied[indexY][indexZ][indexX] = true;
					
				}
				else {
					if(indexY < 9) {
						fieldOccupied[indexY][indexZ][indexX] = false;
					}				
				}
			}
		}
	}
	
	public static void deleteLayer() {
		int yCoord = 0, zCoord = 0, xCoord = 0;
		boolean moveBlocksDown = false;
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
				moveBlocksDown = true;

				for(int z = 0; z < 9; z++) {
					for(int x = 0; x < 9; x++) {
						xCoord = x * 2 + 1;
						zCoord = z * 2 + 1;
						yCoord = y * 2 + 1;
						for(int j = blockManager.getAllBlocks().size()-1; j >= 0; j--) {
							ModelEntity me = blockManager.getAllBlocks().get(j);
							if(me.getPosition().getX() == xCoord && me.getPosition().getY() == yCoord && me.getPosition().getZ() == zCoord) {
								System.out.println("geloeschter Block: "+xCoord+", "+yCoord+", "+zCoord);
								blockManager.getAllModels().remove(me.getModel());
								blockManager.getAllBlocks().remove(me);
								for(int i = 0; i < blockManager.getBlockFormObjects().size(); i++) {
									BlockFormObject bfo = blockManager.getBlockFormObjects().get(i);
									if(bfo.getBlocks().contains(me)) {
										bfo.removeBlock(me);
										bfo.setCountBlocks(bfo.getBlocks().size());
									}
								}
							}
						}
						fieldOccupied[y][z][x] = false;
					}
				}
			}
		}
		if(moveBlocksDown) {
			for(ModelEntity me : blockManager.getAllBlocks()) {
				me.setHasFinalPos(false);
			}
			controllFields();
		}
		
	}
	
	
	/**
	 * gibt hoechste moegliche Blockposition bei einem Block von currentMovingBlocks mit bestimmter x und z Koordinate
	 * @param x
	 * @param z
	 * @return
	 */
	public static float getHighestPos(float x, float z) {
		float y = 0;
		int yI = 0;
		boolean notOccupied = true;
		
		int indexZ = (int) z / 2;
		int indexX = (int) x / 2;
		
		for(int indexY = 0; indexY < 9; indexY++) {
			if(fieldOccupied[indexY][indexZ][indexX]) {
				notOccupied = false;
				if(indexY >= yI) {
					yI = indexY;
				}				
			}				
		}
		
		if(notOccupied)
			return 1;
		else
			y = yI * 2 + 1;
		return y+2;
	}

	/*
	 * Achtung: einiges an Input auch in Camera Klasse
	 *
	 * Vergebene Tasten:
	 * 
	 * UP: Blockform bewegt sich in positive z-Richtung 
	 * DOWN: Blockform bewegt sich in negative z-Richtung 
	 * LEFT: Blockform bewegt sich in negative x-Richtung 
	 * RIGHT: Blockform bewegt sich in positive x-Richtung 
	 * 2: Blockform bewegt sich schneller nach unten
	 * B: Blockform rotiert um xz
	 * N: Blockform rotiert um xy
	 * M: Blockform rotiert um zy
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
	 * 
	 * T: stoppt/startet die Zeit im Cheatmodus
	 */
	public static void input() {
		if (window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
			musicThread.clear();
			musicThread.interrupt();
			window.close();
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_U)) {
			window.unlockMouse();
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_L)) {
			window.lockMouse();
		}

		if (window.isKeyPressed(GLFW.GLFW_KEY_ENTER)) {
//			if (state == GameState.MAIN_MENU && mode != null) {
//				state = GameState.GAME;
//				//System.out.println("Current state is:" + state);
////				AudioPlayer ap = new AudioPlayer("TetrisMusic");
////				ap.start();
//			} else if(state == GameState.MAIN_MENU && mode == null) {
//				System.out.println("Bitte waehlen Sie den Modus. 'C' fuer Cheatmodus und 'N' fuer Normalmodus.");
//			} else if (state == GameState.GAME) {
//				state = GameState.MAIN_MENU;
//				mode = null;
//				//AudioMaster.play("MenuMusic");
//				//System.out.println("Current state is:" + state);
//			}
			if (state == GameState.GAME) {
				state = GameState.MAIN_MENU;
				mode = null;
			}

		}
		if(window.isKeyPressed(GLFW.GLFW_KEY_N)) {
			if(mode == null)
				mode = GameMode.NORMAL;
			if(mode == GameMode.CHEAT)
				System.out.println("Wechseln des Modus nicht moeglich. Sie befinden Sich im Cheatmode. Zum Abbrechen des Spiels Enter druecken.");
			if(state == GameState.MAIN_MENU) {
				state = GameState.GAME;
			}
		}
		if(window.isKeyPressed(GLFW.GLFW_KEY_C)) {
			if(mode == null)
				mode = GameMode.CHEAT;
			if(mode == GameMode.NORMAL)
				System.out.println("Wechseln des Modus nicht moeglich. Sie befinden Sich im Normalmode. Zum Abbrechen des Spiels Enter druecken.");
			if(state == GameState.MAIN_MENU) {
				state = GameState.GAME;
			}
		}
		if(window.isKeyPressed(GLFW.GLFW_KEY_H)) {
			//TODO: Help menu
		}


		if (window.isKeyPressed(GLFW.GLFW_KEY_P)) {
			if (state == GameState.GAME) {
				state = GameState.PAUSE;
				//System.out.println("Game is paused!");
				
				
				// TODO: Add pause music 
			} else if (state == GameState.PAUSE) {
				state = GameState.GAME;
				//System.out.println("Game resumed!");
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
		if (window.isKeyPressed(GLFW.GLFW_KEY_H)) {
			float[] minMaxX = currentMovingBlocks.getMinMaxOfAxis('x');
			float[] minMaxZ = currentMovingBlocks.getMinMaxOfAxis('z');
			if(minMaxX[0] < 0) {
				System.out.println("Sie versuchen in einer kritischen Region zu rotieren. Der Block wird ggf. etwas verrueckt.");
				for(ModelEntity me : currentMovingBlocks.getAll()) {
					me.addPosition(6.0f, 0, 0);
				}
			}
			if(minMaxX[1] > 17) {
				System.out.println("Sie versuchen in einer kritischen Region zu rotieren. Der Block wird ggf. etwas verrueckt.");
				for(ModelEntity me : currentMovingBlocks.getAll()) {
					me.addPosition(-6.0f, 0, 0);
				}
			}
			if(minMaxZ[0] < 0) {
				System.out.println("Sie versuchen in einer kritischen Region zu rotieren. Der Block wird ggf. etwas verrueckt.");
				for(ModelEntity me : currentMovingBlocks.getAll()) {
					me.addPosition(0, 0, 6.0f);
				}
			}
			if(minMaxZ[1] > 17) {
				System.out.println("Sie versuchen in einer kritischen Region zu rotieren. Der Block wird ggf. etwas verrueckt.");
				for(ModelEntity me : currentMovingBlocks.getAll()) {
					me.addPosition(0, 0, -6.0f);
				}
			}
			currentMovingBlocks.turnZX();
//			soundThread = new SoundPlayer("collide");
//			soundThread.start();
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_J)) {
			float[] minMaxX = currentMovingBlocks.getMinMaxOfAxis('x');
			float[] minMaxY = currentMovingBlocks.getMinMaxOfAxis('y');
			boolean ok = true;
			if(minMaxX[0] < 0) {
				System.out.println("Sie versuchen in einer kritischen Region zu rotieren. Der Block wird ggf. etwas verrueckt.");
				for(ModelEntity me : currentMovingBlocks.getAll()) {
					me.addPosition(6.0f, 0, 0);
				}
			}
			if(minMaxX[1] > 17) {
				System.out.println("Sie versuchen in einer kritischen Region zu rotieren. Der Block wird ggf. etwas verrueckt.");
				for(ModelEntity me : currentMovingBlocks.getAll()) {
					me.addPosition(-6.0f, 0, 0);
				}
			}
			if(minMaxY[0] < 3) {
				System.out.println("Rotieren nicht mehr möglich, zu niedrige y-Position");
				ok = false;
			}
			if (ok)
				currentMovingBlocks.turnYX();
//			soundThread = new SoundPlayer("collide");
//			soundThread.start();
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_K)) {
			float[] minMaxZ = currentMovingBlocks.getMinMaxOfAxis('z');
			float[] minMaxY = currentMovingBlocks.getMinMaxOfAxis('y');
			boolean ok = true;
			if(minMaxZ[0] < 0) {
				System.out.println("Sie versuchen in einer kritischen Region zu rotieren. Der Block wird ggf. etwas verrueckt.");
				for(ModelEntity me : currentMovingBlocks.getAll()) {
					me.addPosition(0, 0, 6.0f);
				}
			}
			if(minMaxZ[1] > 17) {
				System.out.println("Sie versuchen in einer kritischen Region zu rotieren. Der Block wird ggf. etwas verrueckt.");
				for(ModelEntity me : currentMovingBlocks.getAll()) {
					me.addPosition(0, 0, -6.0f);
				}
			}
			if(minMaxY[0] < 3) {
				System.out.println("Rotieren nicht mehr möglich, zu niedrige y-Position");
				ok = false;
			}
			if(ok)
				currentMovingBlocks.turnYZ();
//			soundThread = new SoundPlayer("collide");
//			soundThread.start();
		}
		if (window.isKeyPressed(GLFW.GLFW_KEY_1)) {
			state = GameState.GAME;
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
		blockManager.clear();	
		newBlock = true;
		currentMovingBlocks = null;
	}
	
	public static void finish() {
		for (TexturedModel tm : backgroundModels) {
			tm.remove();
		}
		
		shader.remove();
		window.stop();
	}
}
