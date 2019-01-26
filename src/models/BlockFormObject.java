package models;

import java.util.ArrayList;
import java.util.List;

import maths.Vector3f;

/**
 * Verwaltet alle Blockelemente, die zu einer Figur gehoeren
 * 
 * @author Xenia
 *
 */
public class BlockFormObject {

	private List<ModelEntity> blocks;
	private List<ModelEntity> invisible; // even the invisible
	private List<ModelEntity> all;
	private boolean hasFinalPos = false;
	private int lastLength;
	private BlockForm blockform;

	public BlockFormObject(ArrayList<ModelEntity> blocks, ArrayList<ModelEntity> invisible, BlockForm blockform) {
		this.blocks = blocks;
		lastLength = blocks.size();
		this.invisible = invisible;
		this.blockform = blockform;

		all = new ArrayList<>();
		all.addAll(blocks);
		all.addAll(invisible);
	}
	
	public float[] getMinMaxOfAxis(char axis) {
		float[] axisMinMax = new float[2];
		float min = 100, max = 0;
		
		switch(axis) {
		case 'x': 
			for(ModelEntity me : all) {
				if(min > me.getPosition().getX()) {
					min = me.getPosition().getX();
				}
				if(max < me.getPosition().getX()) {
					max = me.getPosition().getX();
				}
			}
			break;
		case 'z': 
			for(ModelEntity me : all) {
				if(min > me.getPosition().getZ()) {
					min = me.getPosition().getZ();
				}
				if(max < me.getPosition().getZ()) {
					max = me.getPosition().getZ();
				}
			}
			break;
		case 'y': 
			for(ModelEntity me : all) {
				if(min > me.getPosition().getY()) {
					min = me.getPosition().getY();
				}
				if(max < me.getPosition().getY()) {
					max = me.getPosition().getY();
				}
			}
			break;
		default: System.out.println("Ein Fehler ist aufgetreten."); break;
		}
		axisMinMax[0] = min;
		axisMinMax[1] = max;
		
		return axisMinMax;
	}

	public float[][] initRotArray(char axis) {
		float xMin = 100, xMax = 0, yMin = 100, yMax = 0, zMin = 100, zMax = 0;
		float[][] coords = null;
		switch (blockform) {
		case I:
			coords = new float[4][4];
			break;
		default:
			coords = new float[3][3];
			break;
		}
		if (axis == 'z') {
			for (ModelEntity me : all) {
				if (me.getPosition().getZ() < zMin)
					zMin = me.getPosition().getZ();
				if (me.getPosition().getZ() > zMax)
					zMax = me.getPosition().getZ();
			}
			for (int z = 0; z < coords.length; z++) {
				for (int j = 0; j < coords.length; j++) {
					coords[z][j] = zMax;
				}
				zMax -= 2.0f;
			}
		} else if (axis == 'x') {
			for (ModelEntity me : all) {
				if (me.getPosition().getX() < xMin)
					xMin = me.getPosition().getX();
				if (me.getPosition().getX() > xMax)
					xMax = me.getPosition().getX();
			}
			for (int x = 0; x < coords.length; x++) {
				for (int i = 0; i < coords.length; i++) {
					coords[i][x] = xMin;
				}
				xMin += 2.0f;
			}
		} else if (axis == 'y') {
			for (ModelEntity me : all) {
				if (me.getPosition().getY() < yMin)
					yMin = me.getPosition().getY();
				if (me.getPosition().getY() > yMax)
					yMax = me.getPosition().getY();
			}
			for (int y = 0; y < coords.length; y++) {
				for (int j = 0; j < coords.length; j++) {
					coords[y][j] = yMax;
				}
				yMax -= 2.0f;
			}
		} else if (axis == 'u') {
			for (ModelEntity me : all) {
				if (me.getPosition().getZ() < zMin)
					zMin = me.getPosition().getZ();
				if (me.getPosition().getZ() > zMax)
					zMax = me.getPosition().getZ();
			}
			for (int z = 0; z < coords.length; z++) {
				for (int i = 0; i < coords.length; i++) {
					coords[i][z] = zMin;
				}
				zMin += 2.0f;
			}
		}
		return coords;
	}

	//funktioniert mit kleinen Bugs
	public void turnZX() {
		float[][] z = initRotArray('z');
		float[][] x = initRotArray('x');
		System.out.println();

		switch (z.length) {
		case 3:
			for (ModelEntity me : blocks) {
				boolean next = true;
				if (next && me.getPosition().getX() == x[0][0] && me.getPosition().getZ() == z[0][0]) {
					me.setPosition(new Vector3f(x[0][2], me.getPosition().getY(), z[0][2]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[0][2] && me.getPosition().getZ() == z[0][2]) {
					me.setPosition(new Vector3f(x[2][2], me.getPosition().getY(), z[2][2]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][2] && me.getPosition().getZ() == z[2][2]) {
					me.setPosition(new Vector3f(x[2][0], me.getPosition().getY(), z[2][0]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][0] && me.getPosition().getZ() == z[2][0]) {
					me.setPosition(new Vector3f(x[0][0], me.getPosition().getY(), z[0][0]));
					next = false;
				}

				if (next && me.getPosition().getX() == x[0][1] && me.getPosition().getZ() == z[0][1]) {
					me.setPosition(new Vector3f(x[1][2], me.getPosition().getY(), z[1][2]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[1][2] && me.getPosition().getZ() == z[1][2]) {
					me.setPosition(new Vector3f(x[2][1], me.getPosition().getY(), z[2][1]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][1] && me.getPosition().getZ() == z[2][1]) {
					me.setPosition(new Vector3f(x[1][0], me.getPosition().getY(), z[1][0]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[1][0] && me.getPosition().getZ() == z[1][0]) {
					me.setPosition(new Vector3f(x[0][1], me.getPosition().getY(), z[0][1]));
					next = false;
				}
			}
			break;
		case 4:
			for (ModelEntity me : blocks) {
				boolean next = true;
				if (next && me.getPosition().getX() == x[0][1] && me.getPosition().getZ() == z[0][1]) {
					me.setPosition(new Vector3f(x[1][3], me.getPosition().getY(), z[1][3]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[1][3] && me.getPosition().getZ() == z[1][3]) {
					me.setPosition(new Vector3f(x[3][2], me.getPosition().getY(), z[3][2]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[3][2] && me.getPosition().getZ() == z[3][2]) {
					me.setPosition(new Vector3f(x[2][0], me.getPosition().getY(), z[2][0]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][0] && me.getPosition().getZ() == z[2][0]) {
					me.setPosition(new Vector3f(x[0][1], me.getPosition().getY(), z[0][1]));
					next = false;
				}

				if (next && me.getPosition().getX() == x[1][1] && me.getPosition().getZ() == z[1][1]) {
					me.setPosition(new Vector3f(x[1][2], me.getPosition().getY(), z[1][2]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[1][2] && me.getPosition().getZ() == z[1][2]) {
					me.setPosition(new Vector3f(x[2][2], me.getPosition().getY(), z[2][2]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][2] && me.getPosition().getZ() == z[2][2]) {
					me.setPosition(new Vector3f(x[2][1], me.getPosition().getY(), z[2][1]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][1] && me.getPosition().getZ() == z[2][1]) {
					me.setPosition(new Vector3f(x[1][1], me.getPosition().getY(), z[1][1]));
					next = false;
				}

				if (next && me.getPosition().getX() == x[2][3] && me.getPosition().getZ() == z[2][3]) {
					me.setPosition(new Vector3f(x[3][1], me.getPosition().getY(), z[3][1]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[3][1] && me.getPosition().getZ() == z[3][1]) {
					me.setPosition(new Vector3f(x[1][0], me.getPosition().getY(), z[1][0]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[1][0] && me.getPosition().getZ() == z[1][0]) {
					me.setPosition(new Vector3f(x[0][2], me.getPosition().getY(), z[0][2]));
					next = false;
				}
				if (next && me.getPosition().getX() == x[0][2] && me.getPosition().getZ() == z[0][2]) {
					me.setPosition(new Vector3f(x[2][3], me.getPosition().getY(), z[2][3]));
					next = false;
				}
			}
			break;
		default:
			System.out.println("Ein Fehler ist eingetreten.");
			break;
		}
	}

	public void turnYX() {
		float[][] y = initRotArray('y');
		float[][] x = initRotArray('x');
		System.out.println();
		
//		for(int i = 0; i < y.length; i++) {
//			for(int j = 0; j < y.length; j++) {
//				System.out.print(y[i][j]+" ");
//			}
//			System.out.println();
//		}

		switch (y.length) {
		case 3:
			for (ModelEntity me : blocks) {
				boolean next = true;
				if (next && me.getPosition().getX() == x[0][0] && me.getPosition().getY() == y[0][0]) {
					me.setPosition(new Vector3f(x[0][2], y[0][2], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[0][2] && me.getPosition().getY() == y[0][2]) {
					me.setPosition(new Vector3f(x[2][2], y[2][2], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][2] && me.getPosition().getY() == y[2][2]) {
					me.setPosition(new Vector3f(x[2][0], y[2][0], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][0] && me.getPosition().getY() == y[2][0]) {
					me.setPosition(new Vector3f(x[0][0], y[0][0], me.getPosition().getZ()));
					next = false;
				}

				if (next && me.getPosition().getX() == x[0][1] && me.getPosition().getY() == y[0][1]) {
					me.setPosition(new Vector3f(x[1][2], y[1][2], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[1][2] && me.getPosition().getY() == y[1][2]) {
					me.setPosition(new Vector3f(x[2][1], y[2][1], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][1] && me.getPosition().getY() == y[2][1]) {
					me.setPosition(new Vector3f(x[1][0], y[1][0], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[1][0] && me.getPosition().getY() == y[1][0]) {
					me.setPosition(new Vector3f(x[0][1], y[0][1], me.getPosition().getZ()));
					next = false;
				}
			}
			break;
		case 4:
			for (ModelEntity me : blocks) {
				boolean next = true;
				if (next && me.getPosition().getX() == x[0][1] && me.getPosition().getY() == y[0][1]) {
					me.setPosition(new Vector3f(x[1][3], y[1][3], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[1][3] && me.getPosition().getY() == y[1][3]) {
					me.setPosition(new Vector3f(x[3][2], y[3][2], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[3][2] && me.getPosition().getY() == y[3][2]) {
					me.setPosition(new Vector3f(x[2][0], y[2][0], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][0] && me.getPosition().getY() == y[2][0]) {
					me.setPosition(new Vector3f(x[0][1], y[0][1], me.getPosition().getZ()));
					next = false;
				}

				if (next && me.getPosition().getX() == x[1][1] && me.getPosition().getY() == y[1][1]) {
					me.setPosition(new Vector3f(x[1][2], y[1][2], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[1][2] && me.getPosition().getY() == y[1][2]) {
					me.setPosition(new Vector3f(x[2][2], y[2][2], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][2] && me.getPosition().getY() == y[2][2]) {
					me.setPosition(new Vector3f(x[2][1], y[2][1], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[2][1] && me.getPosition().getY() == y[2][1]) {
					me.setPosition(new Vector3f(x[1][1], y[1][1], me.getPosition().getZ()));
					next = false;
				}

				if (next && me.getPosition().getX() == x[2][3] && me.getPosition().getY() == y[2][3]) {
					me.setPosition(new Vector3f(x[3][1], y[3][1], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[3][1] && me.getPosition().getY() == y[3][1]) {
					me.setPosition(new Vector3f(x[1][0], y[1][0], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[1][0] && me.getPosition().getY() == y[1][0]) {
					me.setPosition(new Vector3f(x[0][2], y[0][2], me.getPosition().getZ()));
					next = false;
				}
				if (next && me.getPosition().getX() == x[0][2] && me.getPosition().getY() == y[0][2]) {
					me.setPosition(new Vector3f(x[2][3], y[2][3], me.getPosition().getZ()));
					next = false;
				}
			}
			break;
		default:
			System.out.println("Ein Fehler ist eingetreten.");
			break;
		}
	}

	public void turnYZ() {
		float[][] y = initRotArray('y');
		float[][] z = initRotArray('u');
		System.out.println();

		switch (y.length) {
		case 3:
			for (ModelEntity me : blocks) {
				boolean next = true;
				if (next && me.getPosition().getZ() == z[0][0] && me.getPosition().getY() == y[0][0]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[0][2], z[0][2]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[0][2] && me.getPosition().getY() == y[0][2]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[2][2], z[2][2]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[2][2] && me.getPosition().getY() == y[2][2]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[2][0], z[2][0]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[2][0] && me.getPosition().getY() == y[2][0]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[0][0], z[0][0]));
					next = false;
				}

				if (next && me.getPosition().getZ() == z[0][1] && me.getPosition().getY() == y[0][1]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[1][2], z[1][2]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[1][2] && me.getPosition().getY() == y[1][2]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[2][1], z[2][1]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[2][1] && me.getPosition().getY() == y[2][1]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[1][0], z[1][0]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[1][0] && me.getPosition().getY() == y[1][0]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[0][1], z[0][1]));
					next = false;
				}
			}
			break;
		case 4:
			for (ModelEntity me : blocks) {
				boolean next = true;
				if (next && me.getPosition().getZ() == z[0][1] && me.getPosition().getY() == y[0][1]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[1][3], z[1][3]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[1][3] && me.getPosition().getY() == y[1][3]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[3][2], z[3][2]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[3][2] && me.getPosition().getY() == y[3][2]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[2][0], z[2][0]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[2][0] && me.getPosition().getY() == y[2][0]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[0][1], z[0][1]));
					next = false;
				}

				if (next && me.getPosition().getZ() == z[1][1] && me.getPosition().getY() == y[1][1]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[1][2], z[1][2]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[1][2] && me.getPosition().getY() == y[1][2]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[2][2], z[2][2]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[2][2] && me.getPosition().getY() == y[2][2]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[2][1], z[2][1]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[2][1] && me.getPosition().getY() == y[2][1]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[1][1], z[1][1]));
					next = false;
				}

				if (next && me.getPosition().getZ() == z[2][3] && me.getPosition().getY() == y[2][3]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[3][1], z[3][1]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[3][1] && me.getPosition().getY() == y[3][1]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[1][0], z[1][0]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[1][0] && me.getPosition().getY() == y[1][0]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[0][2], z[0][2]));
					next = false;
				}
				if (next && me.getPosition().getZ() == z[0][2] && me.getPosition().getY() == y[0][2]) {
					me.setPosition(new Vector3f(me.getPosition().getX(), y[2][3], z[2][3]));
					next = false;
				}
			}
			break;
		default:
			System.out.println("Ein Fehler ist eingetreten.");
			break;
		}
	}

	public BlockForm getBlockform() {
		return blockform;
	}

	public void updateLength() {
		lastLength = lastLength - 1;
	}

	public int getCountBlocks() {
		return lastLength;
	}

	public void setCountBlocks(int length1) {
		this.lastLength = length1;
	}

	public List<ModelEntity> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<ModelEntity> blocks) {
		this.blocks = blocks;
	}

	public void removeBlock(ModelEntity me) {
		blocks.remove(me);
	}

	public boolean isHasFinalPos() {
		return hasFinalPos;
	}

	public void setHasFinalPos(boolean hasFinalPos) {
		this.hasFinalPos = hasFinalPos;
		if (this.hasFinalPos) {
			for (ModelEntity me : blocks) {
				me.setHasFinalPos(true);
			}
		}
	}

	public List<ModelEntity> getInvisible() {
		return invisible;
	}

	public List<ModelEntity> getAll() {
		return all;
	}

}
