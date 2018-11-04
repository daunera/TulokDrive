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
import hu.tobias.services.comparator.ChallengeComparator;
import hu.tobias.services.comparator.PromiseComparator;
import hu.tobias.services.comparator.QualificationComparator;
import hu.tobias.services.dao.ScoutDao;

@Named(value = "profileScout")
@ViewScoped
public class ProfileScoutController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ScoutDao scoutService;

	@Inject
	private ProfileController profileController;

	private List<Promise> promiseList;
	private List<Challenge> challengeList;
	private List<Qualification> qualificationList;

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

}
