package io;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Image {

    public ByteBuffer getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private ByteBuffer image;
    private int width, height;

    Image(int width, int height, ByteBuffer image) {
        this.image = image;
        this.height = height;
        this.width = width;
    }

    public static Image loadImage(String path) {
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            image = STBImage.stbi_load(path, w, h, comp, 4);
            if (image == null) {
                System.err.println("Couldn't load " + path);
            }
            width = w.get();
            height = h.get();
        }
        return new Image(width, height, image);
    }

   /**
    public static void drawImage(Image i, int x_min, int x_max, int y_min, int y_max) {

    GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(100,100);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(100+texture.getTextureWidth(),100);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(100+texture.getTextureWidth(),100+texture.getTextureHeight());
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(100,100+texture.getTextureHeight());
	GL11.glEnd();


    }
**/

}
