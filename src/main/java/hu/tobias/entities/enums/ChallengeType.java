package hu.tobias.entities.enums;

public enum ChallengeType {
	KCS1("Piros pajzs"), KCS2("Fehér pajzs"), KCS3("Zöld pajzs"), 
	CS1("Újonc"), CS2("Cserkész 2.év"), CS3("Cserkész 3.év"), CS4("Cserkész 4.év"), 
	KO("Kósza kolonc"), KO1("Kósza 1.év"), KO2("Kósza 2.év"), KO3("Kósza 3.év"), KO4("Kósza 4.év"),
	VA1("Vándor 1.év"), VA2("Vándor 2.év"), VA3("Vándor 3.év"), VA4("Vándor 4.év"), VAV("Világjáró öv"),
	R11("I/1."), R12("I/2."), R21("II/1."), R22("II/2."), R31("III/1."), R32("III/2.");

	private String label;

	private ChallengeType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
