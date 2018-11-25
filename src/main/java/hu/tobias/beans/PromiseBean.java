package hu.tobias.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import hu.tobias.entities.Promise;
import hu.tobias.entities.Scout;
import hu.tobias.services.dao.PromiseDao;

@Named(value = "promiseBean")
@ViewScoped
public class PromiseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PromiseDao promiseService;

	private Promise selectedPromise = new Promise();
	private Promise newPromise = new Promise();

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

	public boolean setForEditModal(Promise p) {
		selectedPromise = p;
		return true;
	}

	public boolean setForNewModal(Scout s) {
		newPromise = new Promise(s);
		return true;
	}

	public void savePromise(Promise p, Runnable function) {
		if (p.getId() == null) {
			promiseService.create(p);
		} else
			promiseService.update(p);
		function.run();
	}

	public void deletePromise(Promise p, Runnable function) {
		promiseService.delete(p);
		function.run();
	}

}
