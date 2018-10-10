package hu.tobias.beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import hu.tobias.entities.Promise;
import hu.tobias.entities.Scout;
import hu.tobias.services.dao.PromiseDao;
import hu.tobias.services.dao.ScoutDao;

@Named(value = "promiseBean")
@ViewScoped
public class PromiseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ScoutDao scoutService;
	@EJB
	private PromiseDao promiseService;
	
	//private List<Promise> promiseList; //TODO promiseplace list
	private Promise selectedPromise = new Promise();
	
	public Promise getSelectedPromise() {
		return selectedPromise;
	}
	public void setSelectedPromise(Promise selectedPromise) {
		this.selectedPromise = selectedPromise;
	}
	
	public boolean setForEditModal(Promise p) {
		selectedPromise = p;
		return true;
	}
	
	public boolean setForNewModal(Scout s) {
		selectedPromise = new Promise(s);
		return true;
	}
	
	public void savePromise(Promise p) {
		if (p.getId() == null) {
			promiseService.create(p);
			scoutService.update(p.getScout());
		} else
			promiseService.update(p);
	}
	
	public void deletePromise(Promise p) {
		promiseService.delete(p);
	}

}
