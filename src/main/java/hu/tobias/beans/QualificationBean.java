package hu.tobias.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import hu.tobias.entities.Qualification;
import hu.tobias.entities.Scout;
import hu.tobias.services.dao.QualificationDao;
import hu.tobias.services.dao.ScoutDao;

@Named(value = "qualificationBean")
@ViewScoped
public class QualificationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ScoutDao scoutService;
	@EJB
	private QualificationDao qualificationService;

	private Qualification selectedQualification = new Qualification();
	private Qualification newQualification = new Qualification();

	public Qualification getSelectedQualification() {
		return selectedQualification;
	}

	public void setSelectedQualification(Qualification selectedQualification) {
		this.selectedQualification = selectedQualification;
	}

	public Qualification getNewQualification() {
		return newQualification;
	}

	public void setNewQualification(Qualification newQualification) {
		this.newQualification = newQualification;
	}

	public boolean setForEditModal(Qualification q) {
		selectedQualification = q;
		return true;
	}

	public boolean setForNewModal(Scout s) {
		newQualification = new Qualification(s);
		return true;
	}

	public void saveQualification(Qualification q, Runnable function) {
		if (q.getId() == null) {
			qualificationService.create(q);
		} else
			qualificationService.update(q);
		function.run();
	}

	public void deleteQualification(Qualification q, Runnable function) {
		qualificationService.delete(q);
		function.run();
	}

}
