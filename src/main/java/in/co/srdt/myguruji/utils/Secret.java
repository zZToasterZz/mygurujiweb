package in.co.srdt.myguruji.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Secret
{
	@Value("${in.co.srdt.myguruji.passresetsecret}")
	String passResetKey;

	public String getPassResetKey()
	{
		return passResetKey;
	}
}