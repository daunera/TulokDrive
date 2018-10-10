package hu.tobias.controllers;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
@LocalBean
public class EmailSessionBean {

	private String host = "smtp.gmail.com";
	private int port = 587;
	private String username = "bot@vzma.hu";
	private String password = "Bumburnyak10";
	private boolean debug = true;

	public void sendEmail(String toAddress, String toName, String subject, String title, String text) {
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
			InternetAddress[] address = { new InternetAddress(toAddress) };
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject(subject);
			message.setContent(getHTMLbody(title, toName, text), "text/html; charset=utf-8");
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
	
	private String getHTMLbody(String title, String name, String text) {
		
		String header = "<html>\r\n" + 
				"<body>\r\n" + 
				"    <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"color: #333; background: #fff; padding: 0; margin: 0; width: 100%; font: 15px 'Helvetica Neue', Arial, Helvetica;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr width=\"100%\">\r\n" + 
				"                <td align=\"top\" align=\"left\" style=\"background: #f0f0f0; font: 15px 'Helvetica Neue', Arial, Helvetica;\">\r\n" + 
				"                    <table style=\"border: none; padding: 0 18px; margin: 50px auto; width: 500px;\">\r\n" + 
				"                        <tbody>\r\n" + 
				"                            <tr width=\"100%\" height=\"60\">\r\n" + 
				"                                <td valign=\"top\" align=\"left\" style=\"border-top-left-radius: 10px; border-top-right-radius: 10px; background: #65737e; padding: 12px 18px; text-align: center;\">";
		String headerImg = "<img height=\"40\" src=\"http://varosmajor.cserkesz.hu/wp-content/uploads/2018/07/navlogo.png\" title=\"Trello\" style=\"font-weight: bold; font-size: 18px; color: #fff; vertical-align: top;\"> \r\n" + 
				"								</td>\r\n" + 
				"                            </tr>\r\n" + 
				"                            <tr width=\"100%\">\r\n" + 
				"                                <td valign=\"top\" align=\"left\" style=\"border-bottom-left-radius: 10px; border-bottom-right-radius: 10px; background:#fff; padding: 18px 16px;\">";
		
		String bodyTitle = "<h1 style=\"margin-top: 0;\"> "+title+" </h1>";
		String bodyName = "<p> Kedves "+name+"! </p>";
		String bodyText = "<p>"+text+"</p>";
		
		String footer = "<p align=\"center\"> <a href=\"http://tulok.vzma.hu\" style=\"-webkit-appearance: none; border-radius: 5px; -moz-border-radius: 5px; -webkit-border-radius: 5px; background: #343d46; border: 1px solid #666666; color: #fff; cursor: pointer; display: inline-block; font-weight: 700; line-height: 20px; margin: 10px 0px; padding: 10px 50px; text-decoration: none;\"> Irány a honlap! </a> </p>\r\n" + 
				"									<p style=\"margin-bottom: 10px;\"> Szép napot! <br> &#x1F984; MuuuBot </p>\r\n" + 
				"								</td>\r\n" + 
				"                            </tr>\r\n" + 
				"							<tr><td  align=\"center\" style=\"padding: 10px 16px; color: #a7a7a7; font-size: 85%;\">Valami gond van? <a href=\"mailto:dauner.agoston+tulokdrive@cserkesz.hu?subject=TulokDrive+visszajelzés\" style=\"color: inherit;\">Írj emailt!</a></td></tr>\r\n" + 
				"                        </tbody>\r\n" + 
				"                    </table>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		
		
		return header+headerImg+bodyTitle+bodyName+bodyText+footer;
	}
	
	public void sendNewGeneratedPwMail(String toAddress, String toName, String newPw) {
		String subject = "Új TulokDrive jelszó";
		String title = "Új jelszavad lett";
		String text = "Új jelszót állítottam be neked. Mostantól ezzel fogsz tudni belépni, de bármikor megváltoztathatod a beállításaidnál.<br><br>Jelszavad: "+newPw;
		
		sendEmail(toAddress, toName, subject, title, text);
	}
	
	public void sendPwChangedMail(String toAddress, String toName) {
		String subject = "TulokDrive jelszót váltottál";
		String title = "Megváltoztattad a jelszavad";
		String text = "A beállításoknál megváltoztattad a saját jelszavad.<br><br>Amennyiben mégsem te voltál, akkor kicsit gáz van :/";
		
		sendEmail(toAddress, toName, subject, title, text);
	}
}
