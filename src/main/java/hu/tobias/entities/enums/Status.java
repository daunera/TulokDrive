package hu.tobias.entities.enums;

public enum Status {
	NOTDEFINED("---"), ACTIVE("aktív"), INACTIVE("inaktív"), QUITTED("kilépett");
	
	private String label;
	
	private Status(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
