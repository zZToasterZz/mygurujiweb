package in.co.srdt.myguruji.controllers;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.CourseDetails;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.CourseObjGET;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/closetup")
public class CloSetupController 
{
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;
	
	@Autowired
	GetEmplId getEmplID;
	
	@RequestMapping("/openpage")
	public String openpage(Model model, HttpServletRequest req, Authentication authentication)
	{
		List<CourseModel> crses = new ArrayList<>();
		
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
		
		for(CourseDetails x : crse)
		{
			if(x.getCreatedby().equals("SRLKEM0028"))
				crses.add(new CourseModel() {{
					setId(""+x.getCourseid());
					setCode(x.getCoursecode());
					setTitle(x.getCoursetitle());
					setDescr(x.getCoursedescr());
				}});
		}
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		model.addAttribute("createdby",emplid);
		model.addAttribute("courses",crses);
		
		return "forms/CloSetup/cloSetup.html :: cloSetup";
	}
	
	@ResponseBody
    @RequestMapping("/fetchObjectives/{id}")
    public CourseObjGET[] fetchObjectives(@PathVariable("id") String courseid)
    {
		CourseObjGET[] objectives = null;

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(httpHeaders);

        String url = appGateway.getBaseUrl() + "/api/courseoutcome/getcourseoutcomebycourseid/"+courseid;
        //String url = "http://10.8.20.35:9120/api/courseoutcome/getcourseoutcomebycourseid/"+courseid;

        ResponseEntity<CourseObjGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, CourseObjGET[].class);
        if(response.getStatusCode() == HttpStatus.OK) {
            objectives = response.getBody();
        } else {
            System.out.println("Request Failed HERE");
            System.out.println(response.getStatusCode());
        }
        
        return objectives;
    }
	
	@ResponseBody
    @RequestMapping("/saveObjectives")
    public SingleResponseModel saveObjectives(@RequestBody CourseObjGET[] clo)
    {
		SingleResponseModel resp=null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CourseObjGET[]> request = new HttpEntity<CourseObjGET[]>(clo,httpHeaders);

        String url = appGateway.getBaseUrl() + "/api/courseoutcome/create";
        //String url = "http://10.8.20.35:9120/api/courseoutcome/create";

        ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url,HttpMethod.POST,request, SingleResponseModel.class);
        if(response.getStatusCode() == HttpStatus.OK) {
        	resp = response.getBody();
        } else {
            System.out.println("Request Failed HERE");
            System.out.println(response.getStatusCode());
        }
        
        return resp;
    }
}
