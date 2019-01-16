package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import maths.Vector3f;


public class BlockManager {

	private List <BlockFormObject> blockForms = new ArrayList<>();
	private List<ModelEntity> blockList = new ArrayList<>();
	private List<TexturedModel> allModels = new ArrayList<>();
	
	private ModelEntity me1, me2, me3, me4;
	
	public void addModel(TexturedModel model) {
		allModels.add(model);
	}
	
	public List<TexturedModel> getAllModels() {
		return allModels;
	}

	public void addBlock(ModelEntity block) {
		blockList.add(block);
	}

	public List<ModelEntity> getAllBlocks() {
		return blockList;
	}
	
	public List<BlockFormObject> getBlockFormObjects() {
		return blockForms;
	}

	public int blockFormGenerator() {
		Random r = new Random();
		int form = r.nextInt((5 - 1) + 1) + 1;
		return form;
	}

	public void createNewBlockForm() {
		int formID = blockFormGenerator();
		BlockForm form = getEnum(formID);
		String texture = form.getColorTexture();

		switch (form) {
		case REC:
			create_REC_Object(texture);
			break;
		case T:
			create_T_Object(texture);
			break;
		case L:
			create_L_Object(texture);
			break;
		case I:
			create_I_Object(texture);
			break;
		case Z:
			create_Z_Object(texture);
			break;
		default:
			System.out.println("Ein Fehler ist in der Auswahl der Blockform eingetreten. Das Programm wird beendet.");
			System.exit(-1);
			break;
		}
	}

	public BlockForm getEnum(int formID) {
		for (BlockForm form : BlockForm.values()) {
			if (form.getFormID() == formID)
				return form;
		}
		return null;
	}

	public void create_REC_Object(String texture) {
		me1 = new ModelEntity();
		me2 = new ModelEntity();
		me3 = new ModelEntity();
		me4 = new ModelEntity();
		
		me1 = createBlockElement(texture, 9, 21, 9);
		me2 = createBlockElement(texture, 9, 21, 11);
		me3 = createBlockElement(texture, 11, 21, 11);
		me4 = createBlockElement(texture, 11, 21, 9);
		
		blockForms.add(new BlockFormObject(new ArrayList<ModelEntity>(Arrays.asList(me1, me2, me3, me4))));
	}

	public void create_T_Object(String texture) {
		me1 = new ModelEntity();
		me2 = new ModelEntity();
		me3 = new ModelEntity();
		me4 = new ModelEntity();
		
		me1 = createBlockElement(texture, 7, 21, 11);
		me2 = createBlockElement(texture, 9, 21, 11);
		me3 = createBlockElement(texture, 11, 21, 11);
		me4 = createBlockElement(texture, 9, 21, 9);
		
		blockForms.add(new BlockFormObject(new ArrayList<ModelEntity>(Arrays.asList(me1, me2, me3, me4))));
	}

	public void create_L_Object(String texture) {
		me1 = new ModelEntity();
		me2 = new ModelEntity();
		me3 = new ModelEntity();
		me4 = new ModelEntity();
		
		me1 = createBlockElement(texture, 7, 21, 9);
		me2 = createBlockElement(texture, 9, 21, 9);
		me3 = createBlockElement(texture, 11, 21, 9);
		me4 = createBlockElement(texture, 11, 21, 7);
		
		blockForms.add(new BlockFormObject(new ArrayList<ModelEntity>(Arrays.asList(me1, me2, me3, me4))));
	}

	public void create_I_Object(String texture) {
		me1 = new ModelEntity();
		me2 = new ModelEntity();
		me3 = new ModelEntity();
		me4 = new ModelEntity();
		
		me1 = createBlockElement(texture, 7, 21, 9);
		me2 = createBlockElement(texture, 9, 21, 9);
		me3 = createBlockElement(texture, 11, 21, 9);
		me4 = createBlockElement(texture, 13, 21, 9);
		
		blockForms.add(new BlockFormObject(new ArrayList<ModelEntity>(Arrays.asList(me1, me2, me3, me4))));
	}

	public void create_Z_Object(String texture) {
		me1 = new ModelEntity();
		me2 = new ModelEntity();
		me3 = new ModelEntity();
		me4 = new ModelEntity();
		
		me1 = createBlockElement(texture, 9, 21, 9);
		me2 = createBlockElement(texture, 11, 21, 9);
		me3 = createBlockElement(texture, 11, 21, 7);
		me4 = createBlockElement(texture, 13, 21, 7);
		
		blockForms.add(new BlockFormObject(new ArrayList<ModelEntity>(Arrays.asList(me1, me2, me3, me4))));
	}
	
	public ModelEntity createBlockElement(String texture, int xPos, int yPos, int zPos) {
		TexturedModel model = new TexturedModel(new float[] {
        		-1.0f, 1.0f, -1.0f, //V0
        		-1.0f, -1.0f, -1.0f, //V1
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, 1.0f, -1.0f, //V3
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, -1.0f, 1.0f, //V5
        		1.0f, -1.0f, 1.0f, //V6
        		1.0f, 1.0f, 1.0f, //V7
        		1.0f, 1.0f, -1.0f, //V3
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, -1.0f, 1.0f, //V6
        		1.0f, 1.0f, 1.0f, //V7
        		-1.0f, 1.0f, -1.0f, //V0
        		-1.0f, -1.0f, -1.0f, //V1
        		-1.0f, -1.0f, 1.0f, //V5
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, 1.0f, 1.0f, //V4
        		-1.0f, 1.0f, -1.0f, //V0
        		1.0f, 1.0f, -1.0f, //V3
        		1.0f, 1.0f, 1.0f, //V7
        		-1.0f, -1.0f, 1.0f, //V5
        		-1.0f, -1.0f, -1.0f, //V1
        		1.0f, -1.0f, -1.0f, //V2
        		1.0f, -1.0f, 1.0f //V6
        		}, new float[]{
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f,
        				0f, 0f,
        				0f, 1f,
        				1f, 1f,
        				1f, 0f
        				}, new int[]{
        						0, 1, 3,
        						3, 1, 2,
        						4, 5, 7,
        						7, 5, 6,
        						8, 9, 11,
        						11, 9, 10,
        						12, 13, 15,
        						15, 13, 14,
        						16, 17, 19,
        						19, 17, 18,
        						20, 21, 23,
        						23, 21, 22
        						}, texture);
        allModels.add(model);

        ModelEntity entity = new ModelEntity(model, new Vector3f(xPos, yPos, zPos), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        blockList.add(entity);
        
        return entity;
	}
	
	//gibt kleinsten X Wert zurueck von allen Bloecken der gegenwaertigen Form
	public float getMinX(BlockFormObject bfm) {
		float x = 100.0f;
		for(ModelEntity me : bfm.getBlocks()) {
			if(me.getPosition().getX() < x)
				x = me.getPosition().getX();
		}
		return x;
	}
	
	//gibt groeßten X Wert zurueck von allen Bloecken der gegenwaertigen Form
	public float getMaxX(BlockFormObject bfm) {
		float x = 0;
		for(ModelEntity me : bfm.getBlocks()) {
			if(me.getPosition().getX() > x)
				x = me.getPosition().getX();
		}
		return x;
	}
	
	//gibt kleinsten Y Wert zurueck von allen Bloecken der gegenwaertigen Form
	public float getMinZ(BlockFormObject bfm) {
		float z = 100.0f;
		for(ModelEntity me : bfm.getBlocks()) {
			if(me.getPosition().getZ() < z)
				z = me.getPosition().getZ();
		}
		return z;
	}
	
	//gibt groeßten Y Wert zurueck von allen Bloecken der gegenwaertigen Form
	public float getMaxZ(BlockFormObject bfm) {
		float z = 0;
		for(ModelEntity me : bfm.getBlocks()) {
			if(me.getPosition().getZ() > z)
				z = me.getPosition().getZ();
		}
		return z;
	}

}
