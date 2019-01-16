package models;

import java.util.ArrayList;
import java.util.List;

public class BlocksObject {
	
	private List<ModelEntity> blockList = new ArrayList<>();
	
	public void addBlock(ModelEntity block) {
		blockList.add(block);
	}
	
	public List<ModelEntity> getAllBlocks() {
		return blockList;
	}

}
