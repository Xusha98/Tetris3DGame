package game;

import org.lwjglx.input.Mouse;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import io.Window;
import rendering.Camera;

public class MousePicker {
	
	private Vector3f currentRay;
	
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Camera cam;
	private Window window;
	
	public MousePicker(Window window, Matrix4f projectionMatrix, Camera cam) {
		
		this.projectionMatrix = projectionMatrix;
		this.cam = cam;
		this.window = window;
//		this.viewMatrix = Matrix4f.create
	} 
	
	public Vector3f getCurrentRay() {
		return this.currentRay;
	}
	
	public void update() {
		// viewMatrix = Maths.createViewMatrix();	
		currentRay = calculateMouseRay();
	}
	
	public Vector3f calculateMouseRay() {
		float x = Mouse.getX();
		float y = Mouse.getY();
	}
	
	private Vector2f normalizeMousePos(float mouse_x, float mouse_y){
		float x = (2f*mouse_x) / window.getWidth() -1;
		float y = (2f*mouse_y) / window.getHeight() - 1f;
		
		return new Vector2f(x,y);
		
	}
	
	

}
