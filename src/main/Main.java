package main;

import org.lwjgl.glfw.GLFW;

import engine.io.Window;
import engine.maths.Vector3f;
import engine.rendering.Camera;
import engine.rendering.Renderer;
import engine.rendering.models.ModelEntity;
import engine.rendering.models.TexturedModel;
import engine.shaders.BasicShader;
 
public class Main {
	private static final int WIDTH = 800, HEIGHT = 600, FPS = 60;
	private static Window window = new Window(WIDTH, HEIGHT, FPS, "LWJGL");
	private static BasicShader shader = new BasicShader();
	private static Renderer renderer = new Renderer(window, shader);
	private static Camera cam = new Camera();
	
    public static void main(String[] args) {
    	window.setBackgroundColor(1.0f, 0.0f, 0.0f);
    	window.setIcon("beautiful.png");
    	//window.setCursor("beautiful.png");
    	//window.setFullScreen(true);
    	window.create();
    	window.lockMouse();
    	shader.create();
        
        TexturedModel model = new TexturedModel(new float[] { 
        		-0.5f, 0.5f, -0.5f, //V0 
        		-0.5f, -0.5f, -0.5f, //V1 
        		0.5f, -0.5f, -0.5f, //V2 
        		0.5f, 0.5f, -0.5f, //V3 
        		-0.5f, 0.5f, 0.5f, //V4 
        		-0.5f, -0.5f, 0.5f, //V5 
        		0.5f, -0.5f, 0.5f, //V6 
        		0.5f, 0.5f, 0.5f, //V7 
        		0.5f, 0.5f, -0.5f, //V3 
        		0.5f, -0.5f, -0.5f, //V2 
        		0.5f, -0.5f, 0.5f, //V6 
        		0.5f, 0.5f, 0.5f, //V7 
        		-0.5f, 0.5f, -0.5f, //V0 
        		-0.5f, -0.5f, -0.5f, //V1 
        		-0.5f, -0.5f, 0.5f, //V5 
        		-0.5f, 0.5f, 0.5f, //V4 
        		-0.5f, 0.5f, 0.5f, //V4 
        		-0.5f, 0.5f, -0.5f, //V0 
        		0.5f, 0.5f, -0.5f, //V3 
        		0.5f, 0.5f, 0.5f, //V7 
        		-0.5f, -0.5f, 0.5f, //V5 
        		-0.5f, -0.5f, -0.5f, //V1 
        		0.5f, -0.5f, -0.5f, //V2
        		0.5f, -0.5f, 0.5f //V6 
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
        
        ModelEntity entity = new ModelEntity(model, new Vector3f(0, 0, 2), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        
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
        
        ModelEntity entity2 = new ModelEntity(model2, new Vector3f(2, 2, 2), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        
        
        while (!window.closed()) {
        	if (window.isUpdating()) {
        		window.update();
        		renderer.update();
        		
        		//cam.addPosition(0, 0, -0.05f);
        		cam.update(window);
        		renderer.loadCamera(cam);
        		input();
        		
        		//entity.addRotation(2, 2, 0);
        		//entity.addPosition(0, 0, 0.05f);
        		
        		shader.bind();
	            renderer.renderModelEntity(entity);
	            renderer.renderModelEntity(entity2);
	            shader.unbind();
	            window.swapBuffers();
        	}
        }
 
        model.remove();
        shader.remove();
        window.stop();
    }
    
    public static void input() {
    	if(window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE))
    		window.close();
    	if(window.isKeyPressed(GLFW.GLFW_KEY_U))
    		window.unlockMouse();
    	if(window.isKeyPressed(GLFW.GLFW_KEY_L))
    		window.lockMouse();
    }
}