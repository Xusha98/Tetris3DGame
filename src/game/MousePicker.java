package game;

import org.lwjglx.input.Mouse;
import maths.*;

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
		this.viewMatrix = cam.getViewMatrix();
	}
	public Vector3f getCurrentRay() {
		return this.currentRay;
	}
	
	public void update() {
		viewMatrix = cam.getViewMatrix();
		currentRay = calculateMouseRay();
	}
	
	public Vector3f calculateMouseRay() {
		float x = Mouse.getX();
		float y = Mouse.getY();
		Vector2f normalizedVec = normalizeMousePos(x,y); 
		Vector4f clipCoords = new Vector4f(normalizedVec.getX(), normalizedVec.getY(), -1f, 1f);
		Vector4f eyeCoords = toViewCoords(clipCoords);
		Vector3f rayInWorld = getWorldCoords(eyeCoords);
		return rayInWorld;
		
	}
	
	private Vector2f normalizeMousePos(float mouse_x, float mouse_y){
		float x = (2f*mouse_x) / window.getWidth() -1;
		float y = (2f*mouse_y) / window.getHeight() - 1f;
		
		return new Vector2f(x,y);
		
	}
	
	private Vector4f toViewCoords(Vector4f coor) {
		Matrix4f invProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoordinates = Matrix4f.transform(invProjection, coor, null);
		return new Vector4f(eyeCoordinates.getX(), eyeCoordinates.getY(), -1f, 0f);
	}
	
	private Vector3f getWorldCoords(Vector4f eyeCoords) {
		Matrix4f invView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayPosInWorld = Matrix4f.transform(invView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f (rayPosInWorld.getX(), rayPosInWorld.getY(), rayPosInWorld.getZ());
		mouseRay.normalize();
		return mouseRay;
	}

}
