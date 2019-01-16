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
	
	public BlockFormObject(ArrayList<ModelEntity> blocks) {
		this.blocks = blocks;
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

}
