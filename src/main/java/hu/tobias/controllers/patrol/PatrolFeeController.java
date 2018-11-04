package hu.tobias.controllers.patrol;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Fee;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.FeeStatusType;
import hu.tobias.services.dao.FeeDao;
import hu.tobias.services.dao.FeeTypeTableDao;
import hu.tobias.services.dao.ScoutDao;

@Named(value = "patrolFee")
@ViewScoped
public class PatrolFeeController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ScoutDao scoutService;
	@EJB
	private FeeDao feeService;
	@EJB
	private FeeTypeTableDao feeTypeService;

	@Inject
	private PatrolController patrolController;

	private Integer selectedYear;
	private List<Integer> years = new ArrayList<Integer>();

	private Fee selectedFee = new Fee();
	private Fee newFee = new Fee();

	private HashMap<Scout, Fee> yearFees = new HashMap<Scout, Fee>();

	@PostConstruct
	public void init() {
		for (Integer i = LocalDateTime.now().getYear(); i >= 2016; i--)
			years.add(i);

		if (selectedYear == null) {
			selectedYear = years.get(0);
		}
	}

	public PatrolController getPatrolController() {
		return patrolController;
	}

	public void setPatrolController(PatrolController patrolController) {
		this.patrolController = patrolController;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public Fee getSelectedFee() {
		return selectedFee;
	}

	public void setSelectedFee(Fee selectedFee) {
		this.selectedFee = selectedFee;
	}

	public Fee getNewFee() {
		return newFee;
	}

	public void setNewFee(Fee newFee) {
		this.newFee = newFee;
	}

	public HashMap<Scout, Fee> getYearFees() {
		return yearFees;
	}

	public void setYearFees(HashMap<Scout, Fee> yearFees) {
		this.yearFees = yearFees;
	}

	public boolean setForSelectedModal(Fee f) {
		selectedFee = f;

		return true;
	}

	public boolean setForNewModal(Scout s, Integer y) {
		newFee = new Fee(s, y);
		newFee.setAmount(feeTypeService.findActualTeamFee().getAmount());
		newFee.setStatus(FeeStatusType.OV);

		return true;
	}

	public void saveFee(Fee f) {
		if (f.getId() == null) {
			feeService.create(f);
			f.getScout().getFees().add(f);
			scoutService.update(f.getScout());
		} else
			feeService.update(f);
		patrolController.loadData();
	}

	public void deleteFee(Fee f) {
		for (Fee item : f.getScout().getFees()) {
			if (item.getYear().equals(f.getYear())) {
				f.getScout().getFees().remove(item);
				break;
			}
		}
		scoutService.update(f.getScout());
		feeService.delete(f);
		patrolController.loadData();
	}

	public void changeEdit() {
		patrolController.getUserController().changeEdit();
	}

}
