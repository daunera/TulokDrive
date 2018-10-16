package hu.tobias.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.tobias.services.dao.LeaderDao;
import hu.tobias.services.utils.Utils;

@Named(value = "userSettings")
@ViewScoped
public class UserSettingsController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private LeaderDao leaderService;
	
	@EJB
	private EmailSessionBean emailBean;
	
	@Inject
	private UserController userController;
	
	private String oldPassword;
	private String newPassword;
	private String newPasswordAgain;
	private String errorMessage;
	
	private boolean isError;
	
	public UserController getUserController() {
		return userController;
	}
	public void setUserController(UserController userController) {
		this.userController = userController;
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
	public void saveEdit() {
		//TODO check data validation
		userController.changeEdit();
	}
	
	public void undoEdit() {
		userController.reloadUserName();
		userController.changeEdit();
	}
	
	public boolean setForPasswordModal() {
		setErrorMessage("");
		isError = false;
		return true;
	}
	
	public boolean checkPasswords() {
		if (Utils.isEmpty(oldPassword) || Utils.isEmpty(newPassword) || Utils.isEmpty(newPasswordAgain)) {
			isError = true;
			setErrorMessage("Üres valamelyik mező");
		} else if(!(Utils.sha256(oldPassword).equals(userController.getLeader().getPassword()))){
			isError = true;
			setErrorMessage("A régi jelszavad helytelen");
		} else if (!newPassword.equals(newPasswordAgain)) {
			isError = true;
			setErrorMessage("Az új jelszó mezők nem egyeznek");
		} else {
			isError = false;
			setErrorMessage("");
		}
		
		return true;
	}
	
	public void modifyPassword() {
		if (!isError) {
			userController.getLeader().setPassword(newPassword);
			leaderService.update(userController.getLeader());
			emailBean.sendPwChangedMailFromSettings(userController.getLeader().getEmail(), userController.getLeader().getPersonalName());
		}
	}

}
