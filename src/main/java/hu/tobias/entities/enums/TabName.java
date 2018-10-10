package hu.tobias.entities.enums;

public enum TabName {
	INFO("info"), PERSONAL("personal"), PARENTS("parents"), SCOUTINFO("scoutinfo"), CHALLENGES("challenges"), 
	PROMISES("promises"), FEES("fees"), UNIFORM("uniform"), LEADER("leader"), GROUP("group"), SCOUTS("scouts"),
	NEWS("news");

	private String label;

	private TabName(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
