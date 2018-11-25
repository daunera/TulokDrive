package hu.tobias.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import hu.tobias.entities.Challenge;
import hu.tobias.entities.Scout;
import hu.tobias.services.dao.ChallengeDao;

@Named(value = "challengeBean")
@ViewScoped
public class ChallengeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ChallengeDao challengeService;

	private Challenge selectedChallenge = new Challenge();
	private Challenge newChallenge = new Challenge();

	public Challenge getSelectedChallenge() {
		return selectedChallenge;
	}

	public void setSelectedChallenge(Challenge selectedChallenge) {
		this.selectedChallenge = selectedChallenge;
	}

	public Challenge getNewChallenge() {
		return newChallenge;
	}

	public void setNewChallenge(Challenge newChallenge) {
		this.newChallenge = newChallenge;
	}

	public boolean setForEditModal(Challenge c) {
		selectedChallenge = c;
		return true;
	}

	public boolean setForNewModal(Scout s) {
		newChallenge = new Challenge(s);
		return true;
	}

	public void saveChallenge(Challenge c, Runnable function) {
		if (c.getId() == null) {
			challengeService.create(c);
		} else
			challengeService.update(c);
		function.run();
	}

	public void deleteChallenge(Challenge c, Runnable function) {
		challengeService.delete(c);
		function.run();
	}

}
