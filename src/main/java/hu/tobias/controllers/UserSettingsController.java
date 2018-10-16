package hu.tobias.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.mindrot.jbcrypt.BCrypt;

import hu.tobias.services.dao.LeaderDao;
import hu.tobias.services.dao.UserrolesDao;
import hu.tobias.services.utils.Utils;

@Named(value = "userSettings")
@ViewScoped
public class UserSettingsController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private LeaderDao leaderService;
	@EJB
	private UserrolesDao roleService;

	@EJB
	private EmailSessionBean emailBean;

	@Inject
	private UserController userController;

	private String username;
	private String oldPassword;
	private String newPassword;
	private String newPasswordAgain;
	private String errorMessage;
	private String infoMessage;

	@PostConstruct
	public void init() {
		loadData();
	}

	private void loadData() {
		username = userController.getLeader().getUsername();
		infoMessage = "Jelszó megváltoztatása";
	}

	public UserController getUserController() {
		return userController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordAgain() {
		return newPasswordAgain;
	}

	public void setNewPasswordAgain(String newPasswordAgain) {
		this.newPasswordAgain = newPasswordAgain;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}

	public void edit() {
		oldPassword = "";
		newPassword = "";
		newPasswordAgain = "";
		errorMessage = "";
		loadData();
		userController.changeEdit();
	}

	public void saveEdit() {
		errorMessage = "";
		if (!Utils.isEmpty(username) && !username.equals(userController.getLeader().getUsername())) {
			userController.getLeader().setUsername(username);
			userController.getLeader().getRole().setUsername(username);
			roleService.update(userController.getLeader().getRole());
		}
		if (!(Utils.isEmpty(oldPassword) && Utils.isEmpty(newPassword) && Utils.isEmpty(newPasswordAgain))) {
			if (checkPasswords()) {
				userController.getLeader().setPassword(newPassword);
				leaderService.update(userController.getLeader());
				emailBean.sendPwChangedMailFromSettings(userController.getLeader().getEmail(),
						userController.getLeader().getPersonalName());
				infoMessage = "Sikerült a jelszó megváltoztatása";
			} else {
				return;
			}
		} else {
			leaderService.update(userController.getLeader());
		}

		userController.reloadUser();
		userController.changeEdit();

	}

	public void undoEdit() {
		loadData();
		userController.reloadUser();
		userController.changeEdit();
	}

	public boolean checkPasswords() {
		boolean okay = true;
		if (Utils.isEmpty(userController.getLeader().getSalt())) {
			userController.getLeader().setSalt(BCrypt.gensalt());
			leaderService.update(userController.getLeader());
		}

		if (Utils.isEmpty(oldPassword) || Utils.isEmpty(newPassword) || Utils.isEmpty(newPasswordAgain)) {
			setErrorMessage("Üres valamelyik mező");
			okay = false;
		} else if (!(BCrypt.checkpw(oldPassword, userController.getLeader().getPassword()))) {
			setErrorMessage("A régi jelszavad helytelen");
			okay = false;
		} else if (!newPassword.equals(newPasswordAgain)) {
			setErrorMessage("Az új jelszó mezők nem egyeznek");
			okay = false;
		} else {
			setErrorMessage("");
			okay = true;
		}

		return okay;
	}
}
