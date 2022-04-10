package in.co.srdt.myguruji.controllers;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.CourseDetails;
import in.co.srdt.myguruji.model.Topic;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/topics")
public class TopicsController
{
	@Autowired
    RestTemplate restTemplate;
	
	@Autowired
    private ApplicationGateway appGateway;
	
	@Autowired
	GetEmplId getEmplid;
	
	@RequestMapping("/createtopic")
	public String createtopic(Model model, HttpServletRequest request, Authentication authentication)
	{
		String emplid = getEmplid.getLogedinUserEmplid(authentication.getName());
		CourseDetails[] crse = null;
		String payLoad="{\r\n" + 
				"	\"id\":\"\",\r\n" + 
				"	\"code\":\"\",\r\n" + 
				"	\"title\":\"\",\r\n" + 
				"	\"emplid\":\"\"\r\n" + 
				"}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request2 = new HttpEntity<String>(payLoad,httpHeaders);
		String url = appGateway.getBaseUrl() + "/api/course/search";
		ResponseEntity<CourseDetails[]> response2= restTemplate.exchange(url, HttpMethod.POST, request2, CourseDetails[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
            crse = response2.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response2.getStatusCode());
        }
		
		model.addAttribute("courses",crse);
		model.addAttribute("createdby",emplid);
		
		return "forms/topics/createtopics :: createtopics";
	}
	
	@GetMapping("/getclo/{courseid}")
	@ResponseBody
	public CourseModel[] getclobycourseid(@PathVariable("courseid") String id)
	{
		String url = appGateway.getBaseUrl() + "/api/courseoutcome/getcourseoutcomebycourseid/"+id;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request2 = new HttpEntity<String>(httpHeaders);
		CourseModel[] clo = null;
		ResponseEntity<CourseModel[]> response2= restTemplate.exchange(url, HttpMethod.GET, request2, CourseModel[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
            clo = response2.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response2.getStatusCode());
        }
		
		return clo;
	}
	
	@PostMapping("/savetopic")
	@ResponseBody
	public SingleResponseModel savetopic(@RequestBody Topic[] topics)
	{
		SingleResponseModel x = null;
		String url = appGateway.getBaseUrl() + "/api/topic/create";
		//String url = "http://10.8.20.35:9120/api/topic/create";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Topic[]> request = new HttpEntity<>(topics, httpHeaders);
		ResponseEntity<SingleResponseModel> response2= restTemplate.exchange(url, HttpMethod.POST, request, SingleResponseModel.class);
		if(response2.getStatusCode() == HttpStatus.OK) {
            x = response2.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response2.getStatusCode());
        }
		
		return x;
	}
	
	@GetMapping("/gettopicbycourse/{courseid}")
	@ResponseBody
	public CourseModel[] getTopics(@PathVariable("courseid") String id)
	{
		CourseModel[] topics = null;
		String url = appGateway.getBaseUrl() + "/api/topic/gettopicsbycourseid/"+id;
		//String url = "http://10.8.20.35:9120/api/topic/gettopicsbycourseid/"+id;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request2 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<CourseModel[]> response2= restTemplate.exchange(url, HttpMethod.GET, request2, CourseModel[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
            topics = response2.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response2.getStatusCode());
        }
		
		return topics;
	}
}