package hu.tobias.entities.enums;

public enum Size {
	NOTDEFINED("nem ismert"), XXXS("XXXS"), XXS("XXS"), XS("XS"), 
	S("S"), M("M"), L("L");

	private String label;

	private Size(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
