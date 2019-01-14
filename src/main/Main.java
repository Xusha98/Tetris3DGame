package main;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL15;

import engine.io.Window;
import engine.maths.Vector3f;
import engine.rendering.Camera;
import engine.rendering.Renderer;
import engine.rendering.models.ModelEntity;
import engine.rendering.models.TexturedModel;
import engine.shaders.BasicShader;

public class Main {
	private static final int WIDTH = 800, HEIGHT = 600, FPS = 60;
	private static Window window = new Window(WIDTH, HEIGHT, FPS, "3D Tetris");
	private static BasicShader shader = new BasicShader();
	private static Renderer renderer = new Renderer(window, shader);
	private static Camera cam = new Camera();
	//private MainMenu mm = new MainMenu();

	private static List<ModelEntity> blockList = new ArrayList<>();
	private static List<ModelEntity> formList = new ArrayList<>();
	private static List<ModelEntity> background = new ArrayList<>();
	public static List<TexturedModel> allModels = new ArrayList<>();
	private static GameState state = GameState.MAIN_MENU;
	private static GameMode mode = GameMode.NORMAL;

    public static void main(String[] args) {


    	window.setBackgroundColor(0.0f, 0.0f, 0.0f);
    	window.setIcon("icon.png");
    	//window.setCursor("beautiful.png");
    	//window.setFullScreen(true);
    	window.create();
    	window.lockMouse();
    	shader.create();

    	//setBackground();

        TexturedModel model = new TexturedModel(new float[] {
        		-1.0f, 1.0f, -1.0f, //V0
        		-1.0f, -1.0f, -1.0f, //V1
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, 1.0f, -1.0f, //V3
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, -1.0f, 1.0f, //V5
        		1.0f, -1.0f, 1.0f, //V6
        		1.0f, 1.0f, 1.0f, //V7
        		1.0f, 1.0f, -1.0f, //V3
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, -1.0f, 1.0f, //V6
        		1.0f, 1.0f, 1.0f, //V7
        		-1.0f, 1.0f, -1.0f, //V0
        		-1.0f, -1.0f, -1.0f, //V1
        		-1.0f, -1.0f, 1.0f, //V5
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, 1.0f, -1.0f, //V0
        		1.0f, 1.0f, -1.0f, //V3
        		1.0f, 1.0f, 1.0f, //V7
        		-1.0f, -1.0f, 1.0f, //V5
        		-1.0f, -1.0f, -1.0f, //V1
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, -1.0f, 1.0f //V6
        		}, new float[]{
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f
        				}, new int[]{
        						0, 1, 3,
        						3, 1, 2,
        						4, 5, 7,
        						7, 5, 6,
        						8, 9, 11,
        						11, 9, 10,
        						12, 13, 15,
        						15, 13, 14,
        						16, 17, 19,
        						19, 17, 18,
        						20, 21, 23,
        						23, 21, 22
        						}, "blue.png");
        allModels.add(model);

        ModelEntity entity = new ModelEntity(model, new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        //blockList.add(entity);


        TexturedModel model2 = new TexturedModel(new float[] {
        		-1.0f, 1.0f, -1.0f, //V0
        		-1.0f, -1.0f, -1.0f, //V1
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, 1.0f, -1.0f, //V3
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, -1.0f, 1.0f, //V5
        		1.0f, -1.0f, 1.0f, //V6
        		1.0f, 1.0f, 1.0f, //V7
        		1.0f, 1.0f, -1.0f, //V3
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, -1.0f, 1.0f, //V6
        		1.0f, 1.0f, 1.0f, //V7
        		-1.0f, 1.0f, -1.0f, //V0
        		-1.0f, -1.0f, -1.0f, //V1
        		-1.0f, -1.0f, 1.0f, //V5
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, 1.0f, -1.0f, //V0
        		1.0f, 1.0f, -1.0f, //V3
        		1.0f, 1.0f, 1.0f, //V7
        		-1.0f, -1.0f, 1.0f, //V5
        		-1.0f, -1.0f, -1.0f, //V1
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, -1.0f, 1.0f //V6
        		}, new float[]{
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f
        				}, new int[]{
        						0, 1, 3,
        						3, 1, 2,
        						4, 5, 7,
        						7, 5, 6,
        						8, 9, 11,
        						11, 9, 10,
        						12, 13, 15,
        						15, 13, 14,
        						16, 17, 19,
        						19, 17, 18,
        						20, 21, 23,
        						23, 21, 22
        						}, "green.png");
        allModels.add(model2);

        ModelEntity entity2 = new ModelEntity(model2, new Vector3f(3, 3, 3), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        //blockList.add(entity2);
        
        TexturedModel model3 = new TexturedModel(new float[] {
        		-1.0f, 1.0f, -1.0f, //V0
        		-1.0f, -1.0f, -1.0f, //V1
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, 1.0f, -1.0f, //V3
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, -1.0f, 1.0f, //V5
        		1.0f, -1.0f, 1.0f, //V6
        		1.0f, 1.0f, 1.0f, //V7
        		1.0f, 1.0f, -1.0f, //V3
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, -1.0f, 1.0f, //V6
        		1.0f, 1.0f, 1.0f, //V7
        		-1.0f, 1.0f, -1.0f, //V0
        		-1.0f, -1.0f, -1.0f, //V1
        		-1.0f, -1.0f, 1.0f, //V5
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, 1.0f, -1.0f, //V0
        		1.0f, 1.0f, -1.0f, //V3
        		1.0f, 1.0f, 1.0f, //V7
        		-1.0f, -1.0f, 1.0f, //V5
        		-1.0f, -1.0f, -1.0f, //V1
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, -1.0f, 1.0f //V6
        		}, new float[]{
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f
        				}, new int[]{
        						0, 1, 3,
        						3, 1, 2,
        						4, 5, 7,
        						7, 5, 6,
        						8, 9, 11,
        						11, 9, 10,
        						12, 13, 15,
        						15, 13, 14,
        						16, 17, 19,
        						19, 17, 18,
        						20, 21, 23,
        						23, 21, 22
        						}, "yellow.png");
        allModels.add(model3);

        ModelEntity entity3 = new ModelEntity(model3, new Vector3f(5, 5, 5), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        //blockList.add(entity3);
        
        TexturedModel model4 = new TexturedModel(new float[] {
        		-1.0f, 1.0f, -1.0f, //V0
        		-1.0f, -1.0f, -1.0f, //V1
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, 1.0f, -1.0f, //V3
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, -1.0f, 1.0f, //V5
        		1.0f, -1.0f, 1.0f, //V6
        		1.0f, 1.0f, 1.0f, //V7
        		1.0f, 1.0f, -1.0f, //V3
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, -1.0f, 1.0f, //V6
        		1.0f, 1.0f, 1.0f, //V7
        		-1.0f, 1.0f, -1.0f, //V0
        		-1.0f, -1.0f, -1.0f, //V1
        		-1.0f, -1.0f, 1.0f, //V5
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, 1.0f, -1.0f, //V0
        		1.0f, 1.0f, -1.0f, //V3
        		1.0f, 1.0f, 1.0f, //V7
        		-1.0f, -1.0f, 1.0f, //V5
        		-1.0f, -1.0f, -1.0f, //V1
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, -1.0f, 1.0f //V6
        		}, new float[]{
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f
        				}, new int[]{
        						0, 1, 3,
        						3, 1, 2,
        						4, 5, 7,
        						7, 5, 6,
        						8, 9, 11,
        						11, 9, 10,
        						12, 13, 15,
        						15, 13, 14,
        						16, 17, 19,
        						19, 17, 18,
        						20, 21, 23,
        						23, 21, 22
        						}, "red.png");
        allModels.add(model4);

        ModelEntity entity4 = new ModelEntity(model4, new Vector3f(7, 7, 7), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        //blockList.add(entity3);
        
        background.add(entity);
        background.add(entity2);
        background.add(entity3);
        background.add(entity4);

        while (!window.closed()) {
        	if (window.isUpdating()) {
        		window.update();
        		renderer.update();

        		cam.update(window);
        		renderer.loadCamera(cam);
        		input();


        		switch (state) {
        		case PAUSE:
        			GL15.glColor3f(1.0f, 0.0f, 0.0f);
        			GL15.glRectf(0, 0, 640, 480);
        			break;
        		case GAME:
        			GL15.glColor3f(0.0f, 1.0f, 0.0f);
        			GL15.glRectf(0, 0, 640, 480);
        			break;
        		case MAIN_MENU:
        			
        			//mm.render();


        			break;
        		}


        		shader.bind();
        		
//        		for(int i = 1; i < background.size(); i++) {
//        			ModelEntity me = background.get(i);
//        			renderer.renderModelEntity(me);
//        		}
//        		ModelEntity em = background.get(0);
//        		renderer.renderModelEntity(em);

        		for(ModelEntity me : background) {
        			renderer.renderModelEntity(me);
        			//System.out.println("Grund VertexArrayID: "+me.getModel().getVertexArrayID()+" Position: "+me.getPosition().getX()+", "+me.getPosition().getY()+", "+me.getPosition().getZ());
        		}
        		if(!blockList.isEmpty()) {
        			for(ModelEntity me : blockList) {
        				renderer.renderModelEntity(me);
		            	//System.out.println("Block VertexArrayID: "+me.getModel().getVertexArrayID()+" Position: "+me.getPosition().getX()+", "+me.getPosition().getY()+", "+me.getPosition().getZ());
		            }
        		}
        		
        		System.out.println("X: "+background.get(0).getPosition().getX()+"; Y: "+background.get(0).getPosition().getY()+"; Z: "+background.get(0).getPosition().getZ());
	            shader.unbind();
	            window.swapBuffers();
        	}
        }

        removeAll();
    }

    /*
     * Achtung: einiges an Input auch in Camera Klasse
     * 
     * Vergebene Tasten:
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
     * */
    public static void input() {
    	if(window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {window.close();}
    	if(window.isKeyPressed(GLFW.GLFW_KEY_U)){window.unlockMouse();}
    	if(window.isKeyPressed(GLFW.GLFW_KEY_L)){window.lockMouse();}

    	if(window.isKeyPressed(GLFW.GLFW_KEY_ENTER)) {
    		if(state == GameState.MAIN_MENU) {
    			state = GameState.GAME;
    			System.out.println("Current state is:" +state);
    		} else if(state == GameState.GAME) {
    			state = GameState.MAIN_MENU;
    			System.out.println("Current state is:" +state);
    		}

    	}

    	if(window.isKeyPressed(GLFW.GLFW_KEY_P)) {
    		if(state == GameState.GAME) {
    			state = GameState.PAUSE;
    			System.out.println("Game is paused!");
    		} else if(state == GameState.PAUSE) {
    				state = GameState.GAME;
    				System.out.println("Game resumed!");
    		}
    	}
    }

    public static void setBackground() {
    	for(int i = 1; i <= 18; i=i+2) {
    		for(int j = 1; j <= 18; j=j+2) {
    			TexturedModel model = new TexturedModel(new float[] {
    	        		-1.0f, 0, 1.0f,  //TOP LEFT V0
    	        		1.0f, 0, 1.0f,  //TOP RIGHT V1
    	        		1.0f, 0, -1.0f, //BOTTOM RIGHT V2
    					-1.0f, 0, -1.0f  //BOTTOM LEFT V3
    	        }, new float[] {
    	        		 0, 0,           //TOP LEFT V0
    	        		 1, 0,           //TOP RIGHT V1
    	        		 1, 1,           //BOTTOM RIGHT V2
    	        		 0, 1            //BOTTOM LEFT V3
    	        }, new int[] {
    	        		 0, 1, 2,        //Triangle 1
    					 2, 3, 0         //Triangle 2
    	        }, "fieldElement.png");
    	        allModels.add(model);
    	        ModelEntity entity = new ModelEntity(model, new Vector3f(j, 0, i), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    	        background.add(entity);
    		}
    	}
    	

    	/*				-1.0f, 1.0f, 0,  //TOP LEFT V0
    	        		1.0f, 1.0f, 0,  //TOP RIGHT V1
    	        		1.0f, -1.0f, 0, //BOTTOM RIGHT V2
    					-1.0f, -1.0f, 0  //BOTTOM LEFT V3*/

    	/*S�ulen:
    	 * for(int i = 0; i < 20; i=i+18) {
    		for(int j = 0; j <= 18; j=j+18) {
    			TexturedModel model = new TexturedModel(new float[] {
    	        		-1.0f, 19.0f, 0,  //TOP LEFT V0
    	        		1.0f, 19.0f, 0,  //TOP RIGHT V1
    	        		1.0f, -1.0f, 0, //BOTTOM RIGHT V2
    					-1.0f, -1.0f, 0  //BOTTOM LEFT V3
    	        }, new float[] {
    	        		 0, 0,           //TOP LEFT V0
    	        		 1, 0,           //TOP RIGHT V1
    	        		 1, 1,           //BOTTOM RIGHT V2
    	        		 0, 1            //BOTTOM LEFT V3
    	        }, new int[] {
    	        		 0, 1, 2,        //Triangle 1
    					 2, 3, 0         //Triangle 2
    	        }, "fieldElement.png");
    	    	allModels.add(model);
    	        ModelEntity entity = new ModelEntity(model, new Vector3f(j, 0, i), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    	        background.add(entity);
    		}
    	}*/
    }

    public static void removeAll() {
    	for(TexturedModel tm : allModels) {
    		tm.remove();
    	}
    	shader.remove();
        window.stop();
    }
}
