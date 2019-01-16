package shader;

import maths.Matrix4f;

public class BasicShader extends Shader {
     
    private static final String VERTEX_FILE = "src/shader/basicVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/shader/basicFragmentShader.glsl";
    
    private int tvpMatrixLocation;
    
    private Matrix4f transformationMatrix = new Matrix4f().identity();
    private Matrix4f projectionMatrix = new Matrix4f().identity(); //NEU
    private Matrix4f viewMatrix = new Matrix4f().identity();
 
    public BasicShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    /**
     * Attribute aus Shadern miteingebunden
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textCoords");
    }

	@Override
	protected void getAllUniforms() {
		tvpMatrixLocation = super.getUniform("tvpMatrix");
	}
	
	/**
	 * quasi die sendMVP() Methode
	 */
	public void useMatrices() {
		super.loadMatrixUniform(tvpMatrixLocation, projectionMatrix.mul(viewMatrix.mul(transformationMatrix)));	//ALT (tvpMatrixLocation, transformationMatrix);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		transformationMatrix = matrix;
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		projectionMatrix = matrix;
	}
	
	public void loadViewMatrix(Matrix4f matrix) {
		viewMatrix = matrix;
	}

}