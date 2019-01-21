package maths;

public class Matrix4f {
	private float[][] matrix;
	
	public Matrix4f() {
		matrix = new float[4][4];
	}
	
	public Matrix4f identity() {
		matrix[0][0] = 1;	matrix[0][1] = 0;	matrix[0][2] = 0;	matrix[0][3] = 0;
		matrix[1][0] = 0;	matrix[1][1] = 1;	matrix[1][2] = 0;	matrix[1][3] = 0;
		matrix[2][0] = 0;	matrix[2][1] = 0;	matrix[2][2] = 1;	matrix[2][3] = 0;
		matrix[3][0] = 0;	matrix[3][1] = 0;	matrix[3][2] = 0;	matrix[3][3] = 1;
		
		return this;
	}
	
	public Matrix4f translate(Vector3f vector) {
		matrix[0][0] = 1;	matrix[0][1] = 0;	matrix[0][2] = 0;	matrix[0][3] = vector.getX();
		matrix[1][0] = 0;	matrix[1][1] = 1;	matrix[1][2] = 0;	matrix[1][3] = vector.getY();
		matrix[2][0] = 0;	matrix[2][1] = 0;	matrix[2][2] = 1;	matrix[2][3] = vector.getZ();
		matrix[3][0] = 0;	matrix[3][1] = 0;	matrix[3][2] = 0;	matrix[3][3] = 1;
		
		return this;
	}
	
	public Matrix4f rotate(Vector3f vector) {
		Matrix4f rotateX = new Matrix4f();
		Matrix4f rotateY = new Matrix4f();
		Matrix4f rotateZ = new Matrix4f();
		
		Vector3f rotatedVector = new Vector3f((float) Math.toRadians(vector.getX()), (float) Math.toRadians(vector.getY()), (float) Math.toRadians(vector.getZ()));
		
		rotateZ.matrix[0][0] = (float)Math.cos(rotatedVector.getZ()); rotateZ.matrix[0][1] = -(float)Math.sin(rotatedVector.getZ()); rotateZ.matrix[0][2] = 0; rotateZ.matrix[0][3] = 0;
		rotateZ.matrix[1][0] = (float)Math.sin(rotatedVector.getZ()); rotateZ.matrix[1][1] =  (float)Math.cos(rotatedVector.getZ()); rotateZ.matrix[1][2] = 0; rotateZ.matrix[1][3] = 0;
		rotateZ.matrix[2][0] = 0;				  					  rotateZ.matrix[2][1] = 0;									     rotateZ.matrix[2][2] = 1; rotateZ.matrix[2][3] = 0;
		rotateZ.matrix[3][0] = 0;				 	 				  rotateZ.matrix[3][1] = 0;									     rotateZ.matrix[3][2] = 0; rotateZ.matrix[3][3] = 1;
		
		rotateX.matrix[0][0] = 1; rotateX.matrix[0][1] = 0;										rotateX.matrix[0][2] = 0;									   rotateX.matrix[0][3] = 0;
		rotateX.matrix[1][0] = 0; rotateX.matrix[1][1] = (float)Math.cos(rotatedVector.getX()); rotateX.matrix[1][2] = -(float)Math.sin(rotatedVector.getX()); rotateX.matrix[1][3] = 0;
		rotateX.matrix[2][0] = 0; rotateX.matrix[2][1] = (float)Math.sin(rotatedVector.getX()); rotateX.matrix[2][2] = (float)Math.cos(rotatedVector.getX());  rotateX.matrix[2][3] = 0;
		rotateX.matrix[3][0] = 0; rotateX.matrix[3][1] = 0;										rotateX.matrix[3][2] = 0;									   rotateX.matrix[3][3] = 1;
		
		rotateY.matrix[0][0] = (float)Math.cos(rotatedVector.getY()); rotateY.matrix[0][1] = 0; rotateY.matrix[0][2] = -(float)Math.sin(rotatedVector.getY()); rotateY.matrix[0][3] = 0;
		rotateY.matrix[1][0] = 0;									  rotateY.matrix[1][1] = 1; rotateY.matrix[1][2] = 0;									   rotateY.matrix[1][3] = 0;
		rotateY.matrix[2][0] = (float)Math.sin(rotatedVector.getY()); rotateY.matrix[2][1] = 0; rotateY.matrix[2][2] = (float)Math.cos(rotatedVector.getY());  rotateY.matrix[2][3] = 0;
		rotateY.matrix[3][0] = 0;									  rotateY.matrix[3][1] = 0; rotateY.matrix[3][2] = 0;									   rotateY.matrix[3][3] = 1;
		
		matrix = rotateZ.mul(rotateY.mul(rotateX)).getMatrix();
		
		return this;
	}
	
	public Matrix4f rotateAround(float angle, Vector3f axis) {
		Matrix4f rotate = new Matrix4f();
		
		float x = axis.getX();
		float y = axis.getY();
		float z = axis.getZ();		

		float sin = (float) Math.sin(Math.toRadians(angle));
        float cos = (float) Math.cos(Math.toRadians(angle));
        float C = 1.0f - cos;

		rotate.matrix[0][0] = cos + x * x * C;     rotate.matrix[0][1] = x * y * C - z * sin; rotate.matrix[0][2] = x * z * C + y * sin; rotate.matrix[0][3] = 0;
		rotate.matrix[1][0] = x * y * C + z * sin; rotate.matrix[1][1] = cos + y * y * C;     rotate.matrix[1][2] = y * z * C - x * sin; rotate.matrix[1][3] = 0;
		rotate.matrix[2][0] = x * z * C - y * sin; rotate.matrix[2][1] = y * z * C + x * sin; rotate.matrix[2][2] = cos + z * z * C;     rotate.matrix[2][3] = 0;
		rotate.matrix[3][0] = 0;				   rotate.matrix[3][1] = 0;					  rotate.matrix[3][2] = 0; 				     rotate.matrix[3][3] = 1;

		matrix = rotate.getMatrix();

		return this;
	}
	
	public Matrix4f scale(Vector3f vector) {
		matrix[0][0] = vector.getX(); matrix[0][1] = 0;				matrix[0][2] = 0;			  matrix[0][3] = 0;
		matrix[1][0] = 0;			  matrix[1][1] = vector.getY(); matrix[1][2] = 0;			  matrix[1][3] = 0;
		matrix[2][0] = 0;			  matrix[2][1] = 0;				matrix[2][2] = vector.getZ(); matrix[2][3] = 0;
		matrix[3][0] = 0;			  matrix[3][1] = 0;				matrix[3][2] = 0;			  matrix[3][3] = 1;
		
		return this;
	}
	
	public Matrix4f projection(float fov, float aspectRatio, float zNear, float zFar) {
		float tanHalfFOV = (float) Math.tan(Math.toRadians(fov / 2));
		float zRange = zNear - zFar;
		
		matrix[0][0] = 1.0f / (tanHalfFOV * aspectRatio); matrix[0][1] = 0;					matrix[0][2] = 0;						 matrix[0][3] = 0;
		matrix[1][0] = 0;								  matrix[1][1] = 1.0f / tanHalfFOV;	matrix[1][2] = 0;						 matrix[1][3] = 0;
		matrix[2][0] = 0;								  matrix[2][1] = 0;					matrix[2][2] = (-zNear - zFar) / zRange; matrix[2][3] = 2 * zFar * zNear / zRange;
		matrix[3][0] = 0;								  matrix[3][1] = 0;					matrix[3][2] = 1;						 matrix[3][3] = 0;
			
		return this;
	}
	
	public Matrix4f mul(Matrix4f m) {
		Matrix4f result = new Matrix4f();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				result.set(i, j, matrix[i][0] * m.get(0, j) +
								 matrix[i][1] * m.get(1, j) +
								 matrix[i][2] * m.get(2, j) +
								 matrix[i][3] * m.get(3, j));
			}
		}
		
		return result;
	}

	
	public static Vector4f transform(Matrix4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		float x = left.get(0,0) * right.getX() + left.get(1,0) * right.getY() + left.get(2,0) * right.getZ() + left.get(3,0) * right.getW();
		float y = left.get(0,1) * right.getX() + left.get(1,1) * right.getY() + left.get(2,1) * right.getZ() + left.get(3,1) * right.getW();
		float z = left.get(0,2) * right.getX() + left.get(1,2) * right.getY() + left.get(2,2) * right.getZ() + left.get(3,2) * right.getW();
		float w = left.get(0,3) * right.getX() + left.get(1,3) * right.getY() + left.get(2,3) * right.getZ() + left.get(3,3) * right.getW();

		dest.setX(x);
		dest.setY(y);
		dest.setZ(z);
		dest.setW(w);

		return dest;
	}
	public static Matrix4f invert(Matrix4f src, Matrix4f dest) {
		float determinant = src.determinant();

		if (determinant != 0) {
			/*
			 * m00 m01 m02 m03
			 * m10 m11 m12 m13
			 * m20 m21 m22 m23
			 * m30 m31 m32 m33
			 */
			if (dest == null)
				dest = new Matrix4f();
			float determinant_inv = 1f/determinant;

			// first row
			float t00 =  determinant3x3(src.get(1,1), src.get(1,2), src.get(1,3), src.get(2,1), src.get(2,2), src.get(2,2), src.get(3,1), src.get(3,2), src.get(3,3));
			float t01 = -determinant3x3(src.get(1,0), src.get(1,2), src.get(1,3), src.get(2,0), src.get(2,2), src.get(2,3), src.get(3,0), src.get(3,2), src.get(3,3));
			float t02 =  determinant3x3(src.get(1,0), src.get(1,1), src.get(1,3), src.get(2,0), src.get(2,1), src.get(2,3), src.get(3,0), src.get(3,1), src.get(3,3));
			float t03 = -determinant3x3(src.get(1,0), src.get(1,1), src.get(1,2), src.get(2,0), src.get(2,1), src.get(2,2), src.get(3,0), src.get(3,1), src.get(3,2));
			// second row
			float t10 = -determinant3x3(src.get(0,1), src.get(0,2), src.get(0,3), src.get(2,1), src.get(2,2), src.get(2,3), src.get(3,1), src.get(3,2), src.get(3,3));
			float t11 =  determinant3x3(src.get(0,0), src.get(0,2), src.get(0,3), src.get(2,0), src.get(2,2), src.get(2,3), src.get(3,0), src.get(3,2), src.get(3,3));
			float t12 = -determinant3x3(src.get(0,0), src.get(0,1), src.get(0,3), src.get(2,0), src.get(2,1), src.get(2,3), src.get(3,0), src.get(3,1), src.get(3,3));
			float t13 =  determinant3x3(src.get(0,0), src.get(0,1), src.get(0,2), src.get(2,0), src.get(2,1), src.get(2,2), src.get(3,0), src.get(3,1), src.get(3,2));
			// third row
			float t20 =  determinant3x3(src.get(0,1), src.get(0,2), src.get(0,3), src.get(1,1), src.get(1,2), src.get(1,3), src.get(3,1), src.get(3,2), src.get(3,3));
			float t21 = -determinant3x3(src.get(0,0), src.get(0,2), src.get(0,3), src.get(1,0), src.get(1,2), src.get(1,3), src.get(3,0), src.get(3,2), src.get(3,3));
			float t22 =  determinant3x3(src.get(0,0), src.get(0,1), src.get(0,3), src.get(1,0), src.get(1,1), src.get(1,3), src.get(3,0), src.get(3,1), src.get(3,3));
			float t23 = -determinant3x3(src.get(0,0), src.get(0,1), src.get(0,2), src.get(1,0), src.get(1,1), src.get(1,2), src.get(3,0), src.get(3,1), src.get(3,2));
			// fourth row
			float t30 = -determinant3x3(src.get(0,1), src.get(0,2), src.get(0,3), src.get(1,1), src.get(1,2), src.get(1,3), src.get(2,1), src.get(2,2), src.get(2,3));
			float t31 =  determinant3x3(src.get(0,0), src.get(0,2), src.get(0,3), src.get(1,0), src.get(1,2), src.get(1,3), src.get(2,0), src.get(2,2), src.get(2,3));
			float t32 = -determinant3x3(src.get(0,0), src.get(0,1), src.get(0,3), src.get(1,0), src.get(1,1), src.get(1,3), src.get(2,0), src.get(2,1), src.get(2,3));
			float t33 =  determinant3x3(src.get(0,0), src.get(0,1), src.get(0,2), src.get(1,0), src.get(1,1), src.get(1,2), src.get(2,0), src.get(2,1), src.get(2,2));

			// transpose and divide by the determinant
			dest.set(0, 0, t00*determinant_inv);
			dest.set(1, 1, t11*determinant_inv);
			dest.set(2 ,2, t22*determinant_inv);
			dest.set(3, 3, t33*determinant_inv);
			dest.set(0, 1, t10*determinant_inv);
			dest.set(1, 0, t01*determinant_inv);
			dest.set(2, 0, t02*determinant_inv);
			dest.set(0, 2, t20*determinant_inv);
			dest.set(1, 2, t21*determinant_inv);
			dest.set(2, 1, t12*determinant_inv);
			dest.set(0, 3, t30*determinant_inv);
			dest.set(3, 0, t03*determinant_inv);
			dest.set(1, 3, t31*determinant_inv);
			dest.set(3, 1, t13*determinant_inv);
			dest.set(3, 2, t23*determinant_inv);
			dest.set(2, 3, t32*determinant_inv);
			return dest;
		} else
			return null;
	}
	
	public float determinant() {
		float f = matrix[0][0]
				* ((matrix[1][1] * matrix[2][2] * matrix[3][3] + matrix[1][2] * matrix[2][3] * matrix[3][1] + matrix[1][3] * matrix[2][1] * matrix[3][2])
					- matrix[1][3] * matrix[2][2] * matrix[3][1]
					- matrix[1][1] * matrix[2][3] * matrix[3][2]
					- matrix[1][2] * matrix[2][1] * matrix[3][3]);
		f -= matrix[0][1]
			* ((matrix[1][0] * matrix[2][2] * matrix[3][3] + matrix[1][2] * matrix[2][3] * matrix[3][0] + matrix[1][3] * matrix[2][0] * matrix[3][2])
				- matrix[1][3] * matrix[2][2] * matrix[3][0]
				- matrix[1][0] * matrix[2][3] * matrix[3][2]
				- matrix[1][2] * matrix[2][0] * matrix[3][3]);
		f += matrix[0][2]
			* ((matrix[1][0] * matrix[2][1] * matrix[3][3] + matrix[1][1] * matrix[2][3] * matrix[3][0] + matrix[1][3] * matrix[2][0] * matrix[3][1])
				- matrix[1][3] * matrix[2][1] * matrix[3][0]
				- matrix[1][0] * matrix[2][3] * matrix[3][1]
				- matrix[1][1] * matrix[2][0] * matrix[3][3]);
		f -= matrix[0][3]
			* ((matrix[1][0] * matrix[2][1] * matrix[3][2] + matrix[1][1] * matrix[2][2] * matrix[3][0] + matrix[1][2] * matrix[2][0] * matrix[3][1])
				- matrix[1][2] * matrix[2][1] * matrix[3][0]
				- matrix[1][0] * matrix[2][2] * matrix[3][1]
				- matrix[1][1] * matrix[2][0] * matrix[3][2]);
		return f;
	}

	
	private static float determinant3x3(float t00, float t01, float t02,
		     float t10, float t11, float t12,
		     float t20, float t21, float t22)
{
return   t00 * (t11 * t22 - t12 * t21)
      + t01 * (t12 * t20 - t10 * t22)
      + t02 * (t10 * t21 - t11 * t20);
}
	
	public float[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(float[][] matrix) {
		this.matrix = matrix;
	}
	
	public float get(int x, int y) {
		return matrix[x][y];
	}
	
	public void set(int x, int y, float value) {
		matrix[x][y] = value;
	}
	
	
	
	public String toString() {
		String matrix = "";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matrix += (Float.toString(get(i, j)) + ", ");
			}
			matrix += "\n";
		}
		return matrix + "";
	}
}
