package maths;

public class Vector4f {

	private float w, x, y, z;

	public Vector4f() {

		w = 0;
		x = 0;
		y = 0;
		z = 0;

	}

	public Vector4f(float w, float x, float y, float z) {

		this.x = x;
		this.y = y;
		this.z = z;

	}

	public float getW() {

		return w;

	}

	public void setW(float w) {

		this.w = w;

	}

	public float getX() {

		return x;

	}

	public void setX(float x) {

		this.x = x;

	}

	public float getY() {

		return y;

	}

	public void setY(float y) {

		this.y = y;

	}

	public float getZ() {

		return z;

	}

	public void setZ(float z) {

		this.z = z;

	}

}