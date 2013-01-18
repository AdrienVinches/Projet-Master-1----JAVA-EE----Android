package com.supinbank.tools;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
	public static void sendMail(String to, String subject, String text, Date date) {
		try {
			String smtpHost = "smtp.gmail.com";
			String from = "account@supinbank.com";

			Properties props = new Properties();
			props.put("mail.smtp.host", smtpHost);
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.ssl.enable" , "true");
			

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() 
			{
				protected PasswordAuthentication getPasswordAuthentication()
				{ return new PasswordAuthentication("viinchou@gmail.com","Adrienvinches45");	}
			});		
			session.setDebug(true);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			message.setSubject(subject,"UTF-8");
			message.setText(text,"UTF-8");
			message.setSentDate(date);
			message.setHeader("Content-type", "text/html ; charset=UTF-8");

			/*Transport tr = session.getTransport("smtp");
			tr.connect(smtpHost, username, password);*/
			message.saveChanges();

			//tr.sendMessage(message, message.getAllRecipients());
			//tr.close();
			Transport.send(message);
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
