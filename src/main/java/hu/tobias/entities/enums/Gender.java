package hu.tobias.entities.enums;

public enum Gender {
	NOTDEFINED("---"), MALE("fiú"), FEMALE("lány");
	
	private String label;
	
	private Gender(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
