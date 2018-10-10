package hu.tobias.entities.enums;

public enum FeeStatusType {
	URES("---"), OV("kp ővnél"), RPK("kp rpknál"), CSPK("kp cspknál"), UTAL("utalva"), NO("nem fizetett");

	private String label;

	private FeeStatusType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
