package hu.tobias.entities.enums;

public enum PromiseType {
	KCS("Kiscserkész ígéret"), CS("Cserkész fogadalom"), FE("Felnőtt fogadalom"), TISZTI("Tiszti fogadalom");

	private String label;

	private PromiseType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
