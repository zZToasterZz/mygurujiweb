package in.co.srdt.myguruji.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationGateway {

	@Value("${in.co.srdt.myguruji.zoolserver}")
	private String baseUrl;
	
	@Value("${in.co.srdt.myguruji.oauthurl}")
	private String oauthUrl;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getOauthUrl() {
		return oauthUrl;
	}

	public void setOauthUrl(String oauthUrl) {
		this.oauthUrl = oauthUrl;
	}
	
	
}