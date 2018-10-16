package hu.tobias.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import hu.tobias.entities.Email;

@Stateless
@LocalBean
public class EmailSessionBean {

	private String host = "smtp.gmail.com";
	private int port = 587;
	private String username = "bot@vzma.hu";
	private String password = "Bumburnyak10";
	private boolean debug = true;

	@Inject
	private ConfigBean configBean;

	public void sendEmail(Email mail) {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.user", username);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.starttls.enabled", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.required", "true");
		props.put("mail.smtp.ssl.trust", "*");

		Session session = Session.getInstance(props);
		session.setDebug(debug);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username, "MuuuBot"));
			InternetAddress[] address = { new InternetAddress(mail.getToAddress()) };
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject(mail.getSubject());
			message.setContent(getHTMLbody(mail), "text/html; charset=utf-8");
			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException ex) {
			ex.printStackTrace();
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}

	}

	private String getHTMLbody(Email mail) {

		Map<String, Email> root = new HashMap<String, Email>();
		root.put("mail", mail);

		StringWriter sw = new StringWriter();
		try {
			Template template = configBean.getCfg().getTemplate("mail.ftlh");
			template.process(root, sw);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sw.toString();
	}

	public void sendPwChangedMailFromSettings(String toAddress, String toName) {
		Email mail = new Email();
		mail.setToAddress(toAddress);
		mail.setToName(toName);
		mail.setSubject("Megváltozott a TulokDrive jelszavad");
		mail.setTitle("Megváltoztattad a jelszavad");
		mail.addLine("A beállításoknál megváltoztattad a saját jelszavad.");
		mail.addLine("Amennyiben mégsem te voltál, akkor kicsit gáz van :/");

		sendEmail(mail);
	}

	public void sendPwChangedMailFromAdmin(String toAddress, String toName, String newPw) {
		Email mail = new Email();
		mail.setToAddress(toAddress);
		mail.setToName(toName);
		mail.setSubject("Megváltozott a TulokDrive jelszavad");
		mail.setTitle("Megváltozott a jelszavad");
		mail.addLine("Egy admin új jelszót állított be neked.");
		mail.addLine("Jelszavad: " + newPw);

		sendEmail(mail);
	}

	public void sendPwChangedMailFromForgot(String toAddress, String toName, String newPw) {
		Email mail = new Email();
		mail.setToAddress(toAddress);
		mail.setToName(toName);
		mail.setSubject("Megváltozott a TulokDrive jelszavad");
		mail.setTitle("Megváltoztattad a jelszavad");
		mail.addLine("Új jelszót állítottál be magadnak, mert az előzőt elfelejtetted vagy nem tom.");
		mail.addLine("Jelszavad: " + newPw);

		sendEmail(mail);
	}

	public void sendToDoPwChangeMail(String toAddress, String toName, String link) {
		Email mail = new Email();
		mail.setToAddress(toAddress);
		mail.setToName(toName);
		mail.setSubject("TulokDrive jelszóváltás");
		mail.setTitle("Szeretnél jelszót váltani?");
		mail.addLine("Valaki jelszóváltást igényelt a honlapon a te felhasználód számára.");
		mail.addLine("Amennyiben nem te voltál, akkor nincs semmi teendőd.");
		mail.addLine(
				"Ha viszont váltanál, akkor katt a gombra, mert csak 24 óráig van lehetőséged ezen a linken jelszót váltani!");
		mail.setLink(link);

		sendEmail(mail);
	}

	public ConfigBean getConfigBean() {
		return configBean;
	}

	public void setConfigBean(ConfigBean configBean) {
		this.configBean = configBean;
	}
}
