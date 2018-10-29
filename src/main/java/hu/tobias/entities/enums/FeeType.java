package hu.tobias.entities.enums;

public enum FeeType {
	CSAPAT("csapat tagdíj"), MCSSZNORM("mcssz működő tagsági díj"), MCSSZPLUS("mcssz felnőtt tagsági díj"), OTHER("egyéb");

	private String label;

	private FeeType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
