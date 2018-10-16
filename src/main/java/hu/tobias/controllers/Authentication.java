package hu.tobias.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;

import hu.tobias.entities.Leader;
import hu.tobias.services.dao.LeaderDao;
import hu.tobias.services.utils.Utils;

@Named(value = "authentication")
@ViewScoped
public class Authentication implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	private String originalURL;
	private boolean alertErr;
	private boolean alertPw;

	@EJB
	private LeaderDao userService;

	@EJB
	private EmailSessionBean emailBean;

	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

		if (originalURL != null && originalURL.split("/").length > 2) {
			String endPoint = originalURL.split("/")[2];
			if (endPoint.equals("login") || endPoint.equals("login.xhtml") || endPoint.equals("forgot")
					|| endPoint.equals("forgotpw.xhtml")) {
				if (externalContext.getSessionMap().get("user") != null) {
					try {
						externalContext.redirect(getUrl());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (originalURL == null) {
			originalURL = externalContext.getRequestContextPath();
		} else {
			String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

			if (originalQuery != null) {
				originalURL += "?" + originalQuery;
			}
		}
		alertErr = false;
		alertPw = false;
		password = "";
	}

	public void login() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

		try {
			Leader user = userService.findUserByUsername(username);
			if (Utils.isEmpty(user.getSalt())) {
				user.setSalt(BCrypt.gensalt());
				userService.update(user);
			}
			request.login(username, BCrypt.hashpw(password, user.getSalt()));

			user.setLastlogin(new Date());
			userService.update(user);
			externalContext.getSessionMap().put("user", user);
			externalContext.redirect(originalURL);

		} catch (ServletException e) {
			alertErr = true;
		}
	}

	public void logout() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.invalidateSession();
		externalContext.redirect(externalContext.getRequestContextPath());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAlertErr() {
		return alertErr;
	}

	public void setAlertErr(boolean alertErr) {
		this.alertErr = alertErr;
	}

	public boolean isAlertPw() {
		return alertPw;
	}

	public void setAlertPw(boolean alertPw) {
		this.alertPw = alertPw;
	}

	public String getUrl() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/";
	}

	public String getUrl(String s) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/" + s;
	}

}
