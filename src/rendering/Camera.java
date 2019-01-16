package rendering;

import org.lwjgl.glfw.GLFW;

import io.Window;
import maths.Matrix4f;
import maths.Vector3f;

public class Camera {

	private Vector3f position, rotation;
	private float oldMouseX = 0, oldMouseY = 0, newMouseX = 0, newMouseY = 0, mouseSensitivity = 0.15f;
	private float moveSpeed = 0.2f; //0.05f ist langsam

	public Camera() {
		position = new Vector3f(9, 25, -10);
		rotation = new Vector3f(0, 0, 0);
	}

	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	public Matrix4f getViewMatrix() {
		Matrix4f rotateX = new Matrix4f().rotateAround(rotation.getX(), new Vector3f(1, 0, 0));
		Matrix4f rotateY = new Matrix4f().rotateAround(rotation.getY(), new Vector3f(0, 1, 0));
		Matrix4f rotateZ = new Matrix4f().rotateAround(rotation.getZ(), new Vector3f(0, 0, 1));

		Matrix4f rotation = rotateX.mul(rotateZ.mul(rotateY));
		//Matrix4f rotation = rotateX.mul(rotateY.mul(rotateZ));

		Vector3f negPosition = new Vector3f(-position.getX(), -position.getY(), -position.getZ());
		//Vector3f negPosition = position.mul(-1);
		Matrix4f translation = new Matrix4f().translate(negPosition);

		//return translation.mul(rotation);
		return rotation.mul(translation);
	}

	public void addPosition(Vector3f value) {
		position = position.add(value);
	}

	public void addPosition(float x, float y, float z) {
		position = position.add(new Vector3f(x, y, z));
	}

	public void addRotation(Vector3f value) {
		rotation = rotation.add(value);
	}

	public void addRotation(float x, float y, float z) {
		rotation = rotation.add(new Vector3f(x, y, z));
	}

	public void setPosition(Vector3f value) {
		position = value;
	}

	public void setPosition(float x, float y, float z) {
		position = new Vector3f(x, y, z);
	}

	public void setRotation(Vector3f value) {
		rotation = value;
	}

	public void setRotation(float x, float y, float z) {
		rotation = new Vector3f(x, y, z);
	}

	public void update(Window window) {
		/*//keine Rotation moeglich, nur Drehungen
		if(window.isKeyDown(GLFW.GLFW_KEY_W))
			this.addPosition(0, 0, moveSpeed);
		if(window.isKeyDown(GLFW.GLFW_KEY_S))
			this.addPosition(0, 0, -moveSpeed);
		if(window.isKeyDown(GLFW.GLFW_KEY_D))
			this.addPosition(moveSpeed, 0, 0);
		if(window.isKeyDown(GLFW.GLFW_KEY_A))
			this.addPosition(-moveSpeed, 0, 0);
		if(window.isKeyDown(GLFW.GLFW_KEY_SPACE))
			this.addPosition(0, moveSpeed, 0);
		if(window.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
			this.addPosition(0, -moveSpeed, 0);*/

		if(window.isKeyDown(GLFW.GLFW_KEY_W))
			this.addPosition((float) (Math.sin(Math.toRadians(rotation.getY()))) * -moveSpeed, 0, (float) (Math.cos(Math.toRadians(rotation.getY()))) * moveSpeed);
		if(window.isKeyDown(GLFW.GLFW_KEY_S))
			this.addPosition((float) (Math.sin(Math.toRadians(rotation.getY()))) * moveSpeed, 0, (float) (Math.cos(Math.toRadians(rotation.getY()))) * -moveSpeed);
		if(window.isKeyDown(GLFW.GLFW_KEY_D))
			this.addPosition((float) (Math.sin(Math.toRadians(rotation.getY() - 90))) * -moveSpeed, 0, (float) (Math.cos(Math.toRadians(rotation.getY() - 90))) * moveSpeed);
		if(window.isKeyDown(GLFW.GLFW_KEY_A))
			this.addPosition((float) (Math.sin(Math.toRadians(rotation.getY() - 90))) * moveSpeed, 0, (float) (Math.cos(Math.toRadians(rotation.getY() - 90))) * -moveSpeed);
		if(window.isKeyDown(GLFW.GLFW_KEY_SPACE))
			this.addPosition(0, moveSpeed, 0);
		if(window.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
			this.addPosition(0, -moveSpeed, 0);
		if(window.isKeyDown(GLFW.GLFW_KEY_C))
			this.position = new Vector3f(9, 25, -10);

			//Tasten fuer Zoomen reserviert
		if(window.isKeyDown(GLFW.GLFW_KEY_N))
				//hier kommt das Reinzoomen rein
		if(window.isKeyDown(GLFW.GLFW_KEY_M))
				//hier kommt das Rauszoomen rein

		newMouseY = (float) window.getMouseY();
		newMouseX = (float) window.getMouseX();

		float dx = newMouseX - oldMouseX;
		float dy = newMouseY - oldMouseY;

		this.addRotation(-dy*mouseSensitivity, -dx*mouseSensitivity, 0);

		oldMouseX = newMouseX;
		oldMouseY = newMouseY;
	}

}
