package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet alle Blockelemente, die zu einer Figur gehoeren
 * 
 * @author Xenia
 *
 */
public class BlockFormObject {
	
	private List<ModelEntity> blocks;
	private boolean hasFinalPos = false;
	private int lastLength;
	
	public BlockFormObject(ArrayList<ModelEntity> blocks) {
		this.blocks = blocks;
		lastLength = blocks.size();
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
		if(this.hasFinalPos) {
			for(ModelEntity me : blocks) {
				me.setHasFinalPos(true);
			}
		}
	}

}
