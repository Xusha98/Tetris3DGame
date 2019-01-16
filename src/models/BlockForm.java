package models;

public enum BlockForm {
	
	REC(1, "blue.png"), T(2, "red.png"), L(3, "green.png"), I(4, "yellow.png"), Z(5, "orange.png");
	
	private String colorTexture;
	private int formID;
	
	BlockForm(int formID, String colorTexture) {
		this.formID = formID;
		this.colorTexture = colorTexture;
	}
	
	public int getFormID() {
		return formID;
	}
	
	public String getColorTexture() {
		return colorTexture;
	}

}
