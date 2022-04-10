package in.co.srdt.myguruji.utils;

import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class MailProperties {
	private Properties props = new Properties(); {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	}
	
	public Properties getProperties()
	{
		return props;
	}
}