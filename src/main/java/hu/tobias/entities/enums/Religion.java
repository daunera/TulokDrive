package hu.tobias.entities.enums;

public enum Religion {
	NEMISMERT("nem ismert"), KATOLIKUS("katolikus"), REFORMATUS("református"), EVANGELIKUS("evangélikus"), GOROGKAT(
			"görög katolikus"), EGYEB("egyéb");

	private String label;

	private Religion(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
