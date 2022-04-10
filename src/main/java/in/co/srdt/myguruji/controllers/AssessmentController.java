package in.co.srdt.myguruji.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import in.co.srdt.myguruji.config.AccessToken;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.model.AssessmentByCourseIdGET;
import in.co.srdt.myguruji.model.AssessmentDetails;
import in.co.srdt.myguruji.model.CourseByEmpl;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/manageassessment")
public class AssessmentController
{	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;

	@Autowired
	GetEmplId getEmplID;//=new GetEmplId;
	
	@RequestMapping("/assessmentsearch")
	public String goToManageClass() {
		return "forms/assessment/manageAssessment :: manageAssessment";
	}
	
	@RequestMapping(value = "/addNewAssessment/{courseid}")
	public String addNewAssessment(@PathVariable("courseid")long courseid, HttpServletRequest request, Model model, Authentication authentication)
	{
		String emplid=getEmplID.getLogedinUserEmplid(authentication.getName());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		String url = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
		CourseModel[] courses = null;

		ResponseEntity<CourseModel[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CourseModel[].class);

		if(response.getStatusCode() == HttpStatus.OK) {
			courses = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		model.addAttribute("courses",Arrays.asList(courses));
		model.addAttribute("createdby", emplid);
		model.addAttribute("courseid", courseid);
		return "forms/assessment/addNewAssessment :: addNewAssessment";
	}
	
	@RequestMapping(value = "/createassessment", method = RequestMethod.POST,consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public String createAssessment(AssessmentDetails assessmentDetails)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		AssessmentDetails res = new AssessmentDetails();
		String resp = "";
		
		try {
			HttpEntity<AssessmentDetails> request = new HttpEntity<AssessmentDetails>(assessmentDetails, httpHeaders);
			
			String url = appGateway.getBaseUrl() + "/api/assessment/createassessment";
			
			ResponseEntity<AssessmentDetails> response= restTemplate.exchange(url,HttpMethod.POST,request, AssessmentDetails.class);
			if(response.getStatusCode() == HttpStatus.OK) {
				res = response.getBody();
				resp = "Assessment Created : "+res.getAssessmentid();
			} else {
				System.out.println("Request Failed");
				System.out.println(response.getStatusCode());
			}
		}
		catch (Exception e){
			e.printStackTrace();
			resp = "Failed to create assessment.";
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping("/getassessmentbycourseid/{courseId}")
	public AssessmentByCourseIdGET[] getTopicsByCourseId(@PathVariable("courseId") String courseId)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		AssessmentByCourseIdGET[] assessmentList = null;
		String getAssessmentsUrl = appGateway.getBaseUrl() + "/api/assessment/getassessmentbycourseid/" + courseId;

		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<AssessmentByCourseIdGET[]> response = restTemplate.exchange(getAssessmentsUrl, HttpMethod.GET, request, AssessmentByCourseIdGET[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) { 
			assessmentList = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return assessmentList;
	}	
	
	@ModelAttribute("courses")
	public List<CourseByEmpl> getCourses(Authentication authentication)
	{
		String emplid=getEmplID.getLogedinUserEmplid(authentication.getName());		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		CourseByEmpl[] courses = null;
		List<CourseByEmpl> courseList = null;

		String getCoursesByEmplURL = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;

		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<CourseByEmpl[]> response = restTemplate.exchange(getCoursesByEmplURL, HttpMethod.GET, request, CourseByEmpl[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			courses = response.getBody();
			courseList = Arrays.asList(courses);
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return courseList;
	}

}
