package hu.tobias.entities.enums;

public enum QualificationType {
	OV("őrsvezetői"), ST("segédtiszti"), CST("cserkésztiszti"), 
	CSPKVK("csapatparancsnoki"), VIZI("vízi vezetői");

	private String label;

	private QualificationType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
