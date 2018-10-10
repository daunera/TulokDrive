package hu.tobias.controllers.team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.Gender;
import hu.tobias.entities.enums.Religion;
import hu.tobias.entities.enums.Status;
import hu.tobias.services.dao.ScoutDao;

@Named(value = "teamInfo")
@ViewScoped
public class TeamInfoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ScoutDao scoutService;

	@Inject
	private TeamController teamController;

	private List<Scout> scouts = new ArrayList<Scout>();

	private String[] activity = new String[] { "aktív", "inaktív", "kilépett", "összesen" };

	public TeamInfoController() {
	}

	@PostConstruct
	public void init() {
		loadData();
	}

	public void loadData() {
		scouts = scoutService.findAll();
	}

	public TeamController getTeamController() {
		return teamController;
	}

	public void setTeamController(TeamController teamController) {
		this.teamController = teamController;
	}

	public List<Scout> getScouts() {
		return scouts;
	}

	public void setScouts(List<Scout> scouts) {
		this.scouts = scouts;
	}

	public String[] getActivity() {
		return activity;
	}

	public int getNumberByStatusGender(String status, String gender) {
		Gender g = Gender.valueOf(gender);
		Status s;
		switch (status) {
		case "aktív":
			s = Status.ACTIVE;
			break;
		case "inaktív":
			s = Status.INACTIVE;
			break;
		case "kilépett":
			s = Status.QUITTED;
			break;
		case "---":
			s = Status.NOTDEFINED;
			break;
		default:
			return getNumberByGender(g);
		}

		int num = 0;
		for (Scout o : scouts) {
			if (s.equals(o.getStatus()) && g.equals(o.getPerson().getGender()))
				num++;
		}
		return num;
	}

	public int getNumberByGender(Gender g) {
		int num = 0;
		for (Scout o : scouts) {
			if (g.equals(o.getPerson().getGender()))
				num++;
		}
		return num;
	}

	public int getNumberByStatus(String status) {
		Status s;
		switch (status) {
		case "aktív":
			s = Status.ACTIVE;
			break;
		case "inaktív":
			s = Status.INACTIVE;
			break;
		case "kilépett":
			s = Status.QUITTED;
			break;
		case "---":
			s = Status.NOTDEFINED;
			break;
		default:
			return scouts.size();
		}
		int num = 0;
		for (Scout o : scouts) {
			if (o.getStatus() != null && s.equals(o.getStatus()))
				num++;
		}
		return num;
	}

	public int getNumberByReligionGender(String religion, String gender) {
		Gender g = Gender.valueOf(gender);
		Religion s = Religion.NEMISMERT;
		for (Religion r : Religion.values()) {
			if (r.getLabel().equals(religion))
				s = r;
		}
		int num = 0;
		for (Scout o : scouts) {
			if (s.equals(o.getPerson().getReligion()) && g.equals(o.getPerson().getGender()))
				num++;
		}
		return num;
	}

	public int getNumberByReligion(String religion) {
		Religion s = Religion.NEMISMERT;
		for (Religion r : Religion.values()) {
			if (r.getLabel().equals(religion))
				s = r;
		}
		int num = 0;
		for (Scout o : scouts) {
			if (s.equals(o.getPerson().getReligion()))
				num++;
		}
		return num;
	}

}
