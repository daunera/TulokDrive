package hu.tobias.controllers;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

import hu.tobias.entities.Leader;
import hu.tobias.entities.NewPassword;
import hu.tobias.services.dao.LeaderDao;
import hu.tobias.services.dao.NewPasswordDao;
import hu.tobias.services.utils.Utils;

@Named(value = "passwordController")
@ViewScoped
public class PasswordController implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String password;
	private String passwordAgain;
	private String username;

	private NewPassword newPw;

	private boolean alertDone;
	private boolean alertError;
	private String errorMessage;

	private boolean valid;

	@EJB
	private LeaderDao userService;

	@EJB
	private NewPasswordDao passwordService;

	@EJB
	private EmailSessionBean emailBean;

	@PostConstruct
	public void init() {
		alertDone = false;
		alertError = false;
		errorMessage = "";
		password = "";
		passwordAgain = "";
		valid = false;
	}

	public void loadData() {
		try {
			newPw = null;
			List<NewPassword> pws = passwordService.findAll();
			for (NewPassword np : pws) {
				if (BCrypt.checkpw(code, np.getCode()))
					newPw = np;
			}
			if (newPw == null)
				throw new Exception();
		} catch (Exception e) {
			valid = false;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(newPw.getCreated());

		Long hoursBetween = ChronoUnit.HOURS.between(cal.toInstant(), Calendar.getInstance().toInstant());

		if (hoursBetween >= 0 && hoursBetween < 25) {
			valid = true;
		} else {
			passwordService.delete(newPw);
		}
	}

	public void getNewPassword() {
		alertDone = false;
		alertError = false;
		try {
			if (username != "") {
				Leader user = userService.findUserByUsername(username);
				NewPassword newRow = new NewPassword();
				newRow.setUser(user);
				newRow.setCreated(Utils.now());
				String secretCode = RandomStringUtils.randomAlphanumeric(32);
				newRow.setCode(secretCode);
				passwordService.create(newRow);
				emailBean.sendToDoPwChangeMail(user.getEmail(), user.getPersonalName(), "newpw/" + secretCode);

				alertDone = true;
			}
		} catch (Exception e) {
			alertError = true;
			errorMessage = "Nincs ilyen felhasználó!";
		}
	}

	public void setNewPassword() {
		if (valid) {
			alertDone = false;
			alertError = false;
			errorMessage = "";
			try {
				if (!password.equals(passwordAgain)) {
					alertError = true;
					errorMessage = "A megadott jelszavaknak meg kell egyeznie!";
				} else if (newPw != null) {
					newPw.getUser().setPassword(password);
					userService.update(newPw.getUser());
					passwordService.delete(newPw);
					emailBean.sendPwChangedMailFromForgot(newPw.getUser().getEmail(), newPw.getUser().getPersonalName(),
							password);
					alertDone = true;
				}
			} catch (Exception e) {
				alertError = true;
				errorMessage = "Nem sikerült új jelszót beállítani";
			}
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public NewPassword getNewPw() {
		return newPw;
	}

	public void setNewPw(NewPassword newPw) {
		this.newPw = newPw;
	}

	public boolean isAlertDone() {
		return alertDone;
	}

	public void setAlertDone(boolean alertDone) {
		this.alertDone = alertDone;
	}

	public boolean isAlertError() {
		return alertError;
	}

	public void setAlertError(boolean alertError) {
		this.alertError = alertError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
