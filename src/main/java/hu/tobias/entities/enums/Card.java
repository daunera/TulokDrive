package hu.tobias.entities.enums;

public enum Card {
	NOTDEFINED("nem ismert"), COMBO("kombinált"), OLDCOMBO("tagsági és kedvezmény"), JUSTMEMBER("csak tagsági"), 
	JUSTDISCOUNT("csak kedvezmény"), NOTHING("nincs");

	private String label;

	private Card(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
