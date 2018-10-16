package hu.tobias.entities;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Email {

	private String toAddress;
	private String toName;
	private String subject;
	private String title;
	private List<String> lines;
	private String link;

	public Email() {
		lines = new ArrayList<String>();
		setDefaultLink();
	}

	public Email(String a, String n, String s, String t, String b) {
		this.toAddress = a;
		this.toName = n;
		this.subject = s;
		this.title = t;
		this.lines = new ArrayList<String>();
		this.lines.add(b);
		setDefaultLink();
	}

	public Email(String a, String n, String s, String t, String b, String l) {
		this.toAddress = a;
		this.toName = n;
		this.subject = s;
		this.title = t;
		this.lines = new ArrayList<String>();
		this.lines.add(b);
		setLink(l);
		setDefaultLink();
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public void addLine(String line) {
		this.lines.add(line);
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		this.link = context.getRequestScheme() + "://" + context.getRequestServerName() + ":"
				+ context.getRequestServerPort() + context.getRequestContextPath() + "/" + link;
	}

	public void setFullLink(String link) {
		this.link = link;
	}

	public void setDefaultLink() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		this.link = context.getRequestScheme() + "://" + context.getRequestServerName() + ":"
				+ context.getRequestServerPort() + context.getRequestContextPath();
		
	}

}
