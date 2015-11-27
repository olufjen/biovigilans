package no.naks.biovigilans.felles.control;

import no.naks.rammeverk.mailer.*;
public interface EmailWebService {

	public void setMailTo(String mailTo) ;
	public void sendEmail(String mailInfo);
	public String getSubject();
	public void setSubject(String subject);
	public String getEmailText();
	public void setEmailText(String emailText);
}
