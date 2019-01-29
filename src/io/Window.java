package io;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
	private int width, height;
	private String title;
	private double fps_cap, time, processedTime = 0;
	private long window;
	private GLFWImage cursorBuffer;
	private GLFWImage.Buffer iconBuffer;
	private Vector3f backgroundColor;
	private boolean closed, isFullScreen;
	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	public Window(int width, int height, int fps, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		fps_cap = fps;
		backgroundColor = new Vector3f(0.0f, 0.0f, 0.0f);
		cursorBuffer = null;
		iconBuffer = null;
		closed = false;
	}

	public void create() {
		if (!GLFW.glfwInit()) {
			System.err.println("Error: Couldn't initialize GLFW");
			System.exit(-1);
		}
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		
		if(isFullScreen) {
			window = GLFW.glfwCreateWindow(videoMode.width(), videoMode.height(), title, GLFW.glfwGetPrimaryMonitor(), window);
		}
		else {
			window = GLFW.glfwCreateWindow(width, height, title, 0, window);
		}
		
		if (window == 0) {
			System.err.println("Error: Window couldn't be created");
			System.exit(-1);
		}
		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
		
		if(cursorBuffer != null) {
			long cursor = GLFW.glfwCreateCursor(cursorBuffer, 0, 0);
			GLFW.glfwSetCursor(window, cursor);
		}
		
		if(iconBuffer != null) {
			GLFW.glfwSetWindowIcon(window, iconBuffer);
		}
		
		GLFW.glfwShowWindow(window);
		
		time = getTime();
 	}
	
	public boolean closed() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public void close() {
		GLFW.glfwSetWindowShouldClose(window, true);
	}
	
	public void update() {
		for (int i = 0; i < GLFW.GLFW_KEY_LAST; i++) keys[i] = isKeyDown(i);
		for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) mouseButtons[i] = isMouseDown(i);
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		GLFW.glfwGetWindowSize(window, widthBuffer, heightBuffer);
		width = widthBuffer.get(0);
		height = heightBuffer.get(0);
		GLFW.glfwSetWindowSize(window, width, height);
		GL11.glViewport(0, 0, width, height);
		if(cursorBuffer != null) {
			long cursor = GLFW.glfwCreateCursor(cursorBuffer, 0, 0);
			GLFW.glfwSetCursor(window, cursor);
		}
		
		if(iconBuffer != null) {
			GLFW.glfwSetWindowIcon(window, iconBuffer);
		}
		GL11.glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GLFW.glfwPollEvents();
	}
	
	public void stop() {
		GLFW.glfwTerminate();
		closed = true;
	}
	
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(window);
	}
	
	public double getTime() {
		return (double) System.nanoTime() / (double) 1000000000;
	}
	
	public boolean isKeyDown(int keyCode) {
		return GLFW.glfwGetKey(window, keyCode) == 1;
	}
	
	public boolean isMouseDown(int mouseButton) {
		return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
	}
	
	public boolean isKeyPressed(int keyCode) {
		return isKeyDown(keyCode) && !keys[keyCode];
	}
	
	public boolean isKeyReleased(int keyCode) {
		return !isKeyDown(keyCode) && keys[keyCode];
	}
	
	public boolean isMousePressed(int mouseButton) {
		return isMouseDown(mouseButton) && !mouseButtons[mouseButton];
	}
	
	public boolean isMouseReleased(int mouseButton) {
		return !isMouseDown(mouseButton) && mouseButtons[mouseButton];
	}
	
	public double getMouseX() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, buffer, null);
		return buffer.get(0);
	}
	
	public double getMouseY() {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, null, buffer);
		return buffer.get(0);
	}
	
	public boolean isUpdating() {
		if (!closed) {
			double nextTime = getTime();
			double passedTime = nextTime - time;
			processedTime += passedTime;
			time = nextTime;
			
			while (processedTime > 1.0/fps_cap) {
				processedTime -= 1.0/fps_cap;
				return true;
			}
		}
		return false;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}

	public double getFPS() {
		return fps_cap;
	}

	public long getWindow() {
		return window;
	}
	
	public boolean isFullScreen() {
		return isFullScreen;
	}

	public void setFullScreen(boolean isFullScreen) {
		this.isFullScreen = isFullScreen;
	}

	public void setBackgroundColor(float r, float g, float b) {
		backgroundColor = new Vector3f(r, g, b);
	}
	
	public void setIcon(String path) {
		Image icon = Image.loadImage("resources/textures/"+path);
		GLFWImage iconImage = GLFWImage.malloc();
		iconBuffer = GLFWImage.malloc(1);
		iconImage.set(icon.getWidth(), icon.getHeight(), icon.getImage());
		iconBuffer.put(0, iconImage);
	}
	
	//for moving camera with the mouse
	public void lockMouse() {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	public void unlockMouse() {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
	}
}