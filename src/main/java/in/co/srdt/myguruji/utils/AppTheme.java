package in.co.srdt.myguruji.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppTheme {
	
	@Value("${in.co.srdt.myguruji.theme}")
	private String appTheme;

	public String getAppTheme() {
		return appTheme;
	}
	public void setAppTheme(String appTheme) {
		this.appTheme = appTheme;
	}
}
