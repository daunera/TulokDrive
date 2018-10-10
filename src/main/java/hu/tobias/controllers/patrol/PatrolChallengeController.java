package hu.tobias.controllers.patrol;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Challenge;
import hu.tobias.entities.Scout;
import hu.tobias.entities.enums.ChallengeType;
import hu.tobias.services.dao.ChallengeDao;
import hu.tobias.services.dao.ScoutDao;

@Named(value = "patrolChallenge")
@ViewScoped
public class PatrolChallengeController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ScoutDao scoutService;
	@EJB
	private ChallengeDao challengeService;

	@Inject
	private PatrolController patrolController;

	private Challenge selectedChallenge = new Challenge();
	private Challenge newChallenge = new Challenge();

	public PatrolChallengeController() {
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
	
	public boolean setForNewModal(Scout s, String type) {
		patrolController.setModdedScout(s);
		ChallengeType p = ChallengeType.valueOf(type);
		newChallenge = new Challenge(s,p);
		return true;
	}
	
	public boolean setForSelectedModal(Scout s, Challenge c) {		
		patrolController.setModdedScout(s);
		selectedChallenge = c;
		return true;
	}
	
	public void saveChallenge(Scout s, Challenge c) {
		if (c.getId() == null) {
			challengeService.create(c);
			s.getChallenges().add(c);
			scoutService.update(s);
		}
		else
			challengeService.update(c);
		patrolController.loadData();
	}
	
	public void deleteChallenge(Scout s, Challenge c) {
		for(Challenge item : s.getChallenges()) {
			if(item.getType().equals(c.getType()))
				s.getChallenges().remove(item);
		}
		scoutService.update(s);
		challengeService.delete(c);
		patrolController.loadData();
	}

}
