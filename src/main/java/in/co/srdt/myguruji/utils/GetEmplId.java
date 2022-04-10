package in.co.srdt.myguruji.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetEmplId {
	
	@Autowired
	RestTemplate restTemplate;// = new RestTemplate();
	
	@Autowired
	ApplicationGateway applicationGateway;// = new ApplicationGateway();
	
	public String getLogedinUserEmplid(String username) {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);

		String emplidurl = applicationGateway.getOauthUrl() + "mgoauth/userlogin/getUserDetails/" + username;
		
		ResponseEntity<String> emplid = restTemplate.exchange(emplidurl, HttpMethod.GET, request, String.class);
		
		return emplid.getBody();
	}
	
	public String getRoleByRequest(HttpServletRequest request) 
	{		
		String role="";
		if(request.isUserInRole("ROLE_Faculty"))
		{
			role="Faculty";
		}
		else if(request.isUserInRole("ROLE_Student"))
		{
			role="Student";
		}
		else if(request.isUserInRole("ROLE_Coe"))
		{
			role="Coe";
		}
		else if(request.isUserInRole("ROLE_Iqac"))
		{
			role="Iqac";
		}
		else
		{
			role=null;
		}
		
		return role;
	}

}
