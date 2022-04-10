package in.co.srdt.myguruji.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailerInfo
{
	@Value("${in.co.srdt.myguruji.mailid}")
	private String mailid;
	
	@Value("${in.co.srdt.myguruji.mailpass}")
	private String mailpass;

	public String getMailid()
	{
		return mailid;
	}
	public String getMailpass()
	{
		return mailpass;
	}
}