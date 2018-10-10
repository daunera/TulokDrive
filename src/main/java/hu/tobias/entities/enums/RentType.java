package hu.tobias.entities.enums;

public enum RentType {
	NOW("2018-tól"), OLD("2014-től"), VERYOLD("régi"), NOTDEFINED("nem ismert");

	private String label;

	private RentType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
