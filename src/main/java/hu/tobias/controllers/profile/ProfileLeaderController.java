package hu.tobias.controllers.profile;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.services.dao.LeaderDao;

@Named(value = "profileLeader")
@ViewScoped
public class ProfileLeaderController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private LeaderDao leaderService;
	
	@Inject
	private ProfileController profileController;

	public ProfileLeaderController() {
	}

	@PostConstruct
	public void init() {
	}
	
	public ProfileController getProfileController() {
		return profileController;
	}

	public void setProfileController(ProfileController profileController) {
		this.profileController = profileController;
	}

	public void saveEdit() {
		leaderService.update(profileController.getScout().getLeader());
		profileController.getUserController().changeEdit();
	}

}
