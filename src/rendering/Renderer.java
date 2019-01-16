package rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import io.Window;
import maths.Matrix4f;
import models.ModelEntity;
import shader.BasicShader;
 
public class Renderer {
	private BasicShader shader;
	private Window window; //NEU
	
	public Renderer(Window window, BasicShader shader) {
		this.shader = shader;
		this.window = window;
	}
	
	//NEU
	public void update() {
		shader.loadProjectionMatrix(new Matrix4f().projection(70.0f, (float) window.getWidth() / window.getHeight(), 0.1f, 1000.0f));
	}
	
	public void loadCamera(Camera cam) {
		shader.loadViewMatrix(cam.getViewMatrix());
	}
    
    /**
     * Rendert das Model und nutzt Shader
     * @param entity
     */
    public void renderModelEntity(ModelEntity entity){
    	shader.loadTransformationMatrix(entity.getTransformationMatrix());
        shader.useMatrices();
        GL30.glBindVertexArray(entity.getModel().getVertexArrayID()); //soll zur Geometrie gehoeren
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getMaterial().getTextureID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
}