package hu.tobias.controllers.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.entities.Challenge;
import hu.tobias.entities.Promise;
import hu.tobias.entities.Qualification;
import hu.tobias.entities.Scout;
import hu.tobias.services.comparator.ChallengeComparator;
import hu.tobias.services.comparator.PromiseComparator;
import hu.tobias.services.comparator.QualificationComparator;
import hu.tobias.services.dao.QualificationDao;
import hu.tobias.services.dao.ScoutDao;

@Named(value = "profileScout")
@ViewScoped
public class ProfileScoutController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ScoutDao scoutService;
	@EJB
	private QualificationDao qualificationService;

	@Inject
	private ProfileController profileController;

	private List<Promise> promiseList;
	private List<Challenge> challengeList;
	private List<Qualification> qualificationList;

	private Qualification newQualification = new Qualification();
	private Qualification selectedQualification = new Qualification();

	private Runnable loader = new Runnable() {

		@Override
		public void run() {
			profileController.loadData();
			loadData();
		}
	};

	public ProfileScoutController() {
	}

	@PostConstruct
	public void init() {
		loadData();
	}

	public void loadData() {
		loadPromises();
		loadChallenges();
		loadQualifications();
	}

	private void loadPromises() {
		promiseList = new ArrayList<Promise>(profileController.getScout().getPromises());
		Collections.sort(promiseList, new PromiseComparator());
	}

	private void loadChallenges() {
		challengeList = new ArrayList<Challenge>(profileController.getScout().getChallenges());
		Collections.sort(challengeList, new ChallengeComparator());
	}

	private void loadQualifications() {
		qualificationList = new ArrayList<Qualification>(profileController.getScout().getQualifications());
		Collections.sort(qualificationList, new QualificationComparator());
	}

	public ProfileController getProfileController() {
		return profileController;
	}

	public void setProfileController(ProfileController profileController) {
		this.profileController = profileController;
	}

	public Qualification getNewQualification() {
		return newQualification;
	}

	public void setNewQualification(Qualification newQualification) {
		this.newQualification = newQualification;
	}

	public List<Promise> getPromiseList() {
		return promiseList;
	}

	public void setPromiseList(List<Promise> promiseList) {
		this.promiseList = promiseList;
	}

	public List<Challenge> getChallengeList() {
		return challengeList;
	}

	public void setChallengeList(List<Challenge> challengeList) {
		this.challengeList = challengeList;
	}

	public List<Qualification> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List<Qualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public Qualification getSelectedQualification() {
		return selectedQualification;
	}

	public void setSelectedQualification(Qualification selectedQualification) {
		this.selectedQualification = selectedQualification;
	}

	public Runnable getLoader() {
		return loader;
	}

	public void setLoader(Runnable loader) {
		this.loader = loader;
	}

	public void saveEdit() {
		scoutService.update(profileController.getScout());
		profileController.getUserController().changeEdit();
	}

	public boolean setForNewQualificationModal(Scout s) {
		newQualification = new Qualification(s);
		return true;
	}

	public boolean setForEditQualificationModal(Qualification q) {
		selectedQualification = q;
		return true;
	}

	public void deleteQualification(Qualification q) {
		qualificationList.remove(q);
		scoutService.update(q.getScout());
		qualificationService.delete(q);

		profileController.loadData();
		loadQualifications();
	}

	public void saveQualification(Qualification q) {
		if (q.getId() == null) {
			qualificationService.create(q);
			qualificationList.add(q);
			scoutService.update(q.getScout());
		} else
			qualificationService.update(q);

		profileController.loadData();
		loadQualifications();
	}
}
