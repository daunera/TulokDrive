package hu.tobias.controllers.patrol;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Promise;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.PromiseType;
import hu.tobias.services.dao.PromiseDao;
import hu.tobias.services.dao.ScoutDao;

@Named(value = "patrolPromise")
@ViewScoped
public class PatrolPromiseController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ScoutDao scoutService;
	@EJB
	private PromiseDao promiseService;
	
	@Inject
	private PatrolController patrolController;
	
	private Promise selectedPromise = new Promise();
	private Promise newPromise = new Promise();
	
	public PatrolPromiseController() {
	}
	
	@PostConstruct
	public void init() {
	}

	public PatrolController getPatrolController() {
		return patrolController;
	}

	public void setPatrolController(PatrolController patrolController) {
		this.patrolController = patrolController;
	}

	public Promise getSelectedPromise() {
		return selectedPromise;
	}

	public void setSelectedPromise(Promise selectedPromise) {
		this.selectedPromise = selectedPromise;
	}

	public Promise getNewPromise() {
		return newPromise;
	}

	public void setNewPromise(Promise newPromise) {
		this.newPromise = newPromise;
	}

	public boolean setForNewModal(Scout s, String type) {
		PromiseType p = PromiseType.valueOf(type);
		
		patrolController.setModdedScout(s);
		newPromise = new Promise(s,p);

		return true;
	}
	
	public boolean setForSelectedModal(Scout s, Promise p) {		
		patrolController.setModdedScout(s);
		selectedPromise = p;

		return true;
	}
	
	public void savePromise(Scout s, Promise p) {
		if (p.getId() == null) {
			promiseService.create(p);
			s.getPromises().add(p);
			scoutService.update(s);
		}
		else
			promiseService.update(p);
		patrolController.loadData();
	}
	
	public void deletePromise(Scout s, Promise p) {
		for(Promise item : s.getPromises()) {
			if(item.getType().equals(p.getType()))
				s.getPromises().remove(item);
		}
		scoutService.update(s);
		promiseService.delete(p);
		patrolController.loadData();
	}

}
