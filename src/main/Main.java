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
	
	private static List<ModelEntity> blockList = new ArrayList<>();
	private static List<ModelEntity> formList = new ArrayList<>();
	private static List<ModelEntity> background = new ArrayList<>();
	public static List<TexturedModel> allModels = new ArrayList<>();
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
    	
    	setBackground();
    	
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
        						}, "beautiful.png");
        allModels.add(model);
        
        ModelEntity entity = new ModelEntity(model, new Vector3f(5, 10, 5), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        blockList.add(entity);
        
        
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
        						}, "beautiful.png");
        allModels.add(model2);
        
        ModelEntity entity2 = new ModelEntity(model2, new Vector3f(8, 5, 8), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        blockList.add(entity2);
        
        while (!window.closed()) {
        	if (window.isUpdating()) {
        		window.update();
        		renderer.update();
        		
        		cam.update(window);
        		renderer.loadCamera(cam);
        		input();
        		
        		
        		render();
        		
        		
        		shader.bind();
        		          
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
	            shader.unbind();
	            window.swapBuffers();
        	}
        }
 
        removeAll();
    }
    
    public static void input() {
    	if(window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {window.close();}
    	if(window.isKeyPressed(GLFW.GLFW_KEY_U)){window.unlockMouse();}
    	if(window.isKeyPressed(GLFW.GLFW_KEY_L)){window.lockMouse();}
    	
    	if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
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
    
    
    public static void removeAll() {
    	for(TexturedModel tm : allModels) {
    		tm.remove();
    	}
    	shader.remove();
        window.stop();
    }
}