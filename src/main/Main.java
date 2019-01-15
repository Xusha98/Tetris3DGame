package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.SlickException;

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


	private static GameState state = GameState.MAIN_MENU;
	private static GameMode mode = GameMode.NORMAL;

    public static void main(String[] args) throws SlickException {


    	window.setBackgroundColor(0.0f, 0.0f, 0.0f);
    	window.setIcon("icon.png");
    	//window.setCursor("beautiful.png");
    	//window.setFullScreen(true);
    	window.create();
    	window.lockMouse();
    	shader.create();



        while (!window.closed()) {
        	if (window.isUpdating()) {
        		window.update();
        		renderer.update();

        		cam.update(window);
        		renderer.loadCamera(cam);
        		input();


        		render();


        		shader.bind();


	            shader.unbind();
	            window.swapBuffers();
        	}
        }

        Game.removeAll();
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

    	if(window.isKeyPressed(GLFW.GLFW_KEY_D)) {
    		if(state == GameState.GAME) {
    			state = GameState.PAUSE;
    			System.out.println("Game is paused!");
    		} else if(state == GameState.PAUSE) {
    				state = GameState.GAME;
    				System.out.println("Game resumed!");
    		}
    	}
    }



    	/*				-1.0f, 1.0f, 0,  //TOP LEFT V0
    	        		1.0f, 1.0f, 0,  //TOP RIGHT V1
    	        		1.0f, -1.0f, 0, //BOTTOM RIGHT V2
    					-1.0f, -1.0f, 0  //BOTTOM LEFT V3*/

    	/*Saeulen:
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


	public static void render() throws SlickException {


		switch(state){
			case MAIN_MENU:
				// Just show our background, we can add some cool menus and stuff
				// here but for now I'm keeping it simple.

				MainMenu.render(window);
				break;
			case GAME:
				// Render both our player and background and in update switch
				// we enable player1.update()
				Game.render();

				break;
			case PAUSE:
				// Render our player and background but don't allow them to
				// update

				break;

			default:
				// Switch cases should almost always have a default case
				// this is so that it catches any unexpected values although.
				break;
		}
	}


}
