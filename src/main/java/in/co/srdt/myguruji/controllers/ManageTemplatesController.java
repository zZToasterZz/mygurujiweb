package in.co.srdt.myguruji.controllers;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.CourseByEmpl;
import in.co.srdt.myguruji.model.TemplateModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/managetemplates")
public class ManageTemplatesController {
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;
	
	@Autowired
	GetEmplId getEmplID;//=new GetEmplId;
	
	@RequestMapping("/templatesearch")
	public String templateSearch(Authentication authentication, Model model)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		model.addAttribute("emplid", emplid);
		return "forms/templates/manageTemplates :: manageTemplates";
	}
	
	@RequestMapping("/addtemplate")
	public String addtemplate(Authentication authentication, Model model)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		model.addAttribute("createdby", emplid);
		return "forms/templates/addTemplate :: addTemplate";
	}
	
	@RequestMapping(value = "/createtemplate", method = RequestMethod.POST,consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public String createtemplate(TemplateModel template)
	{
		template.setTemplateid(0);
		
		String res = "";
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", AccessToken.getAccessToken());
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<TemplateModel> request = new HttpEntity<TemplateModel>(template, httpHeaders);
			
			String url = appGateway.getBaseUrl() + "/api/assessment/createtemplate";
			
			ResponseEntity<TemplateModel> response= restTemplate.exchange(url,HttpMethod.POST,request, TemplateModel.class);
			if(response.getStatusCode() == HttpStatus.OK) {
				
				//System.out.println("GENERATED : "+response.getBody().getTemplatecode()+" : "+response.getBody().getTemplateid());
				res = "Template Generated : "+response.getBody().getTemplatecode();
			} else {
				System.out.println("Request Failed");
				System.out.println(response.getStatusCode());
				res = "Failed to create template.\nRespose Code : "+response.getStatusCode();
			}
		}
		catch (Exception e){
			e.printStackTrace();
			res = "Failed to create template.";
		}
		
		return res;
	}
	
	@ModelAttribute("courses")
	public List<CourseByEmpl> getCourses(Authentication authentication)
	{	
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		CourseByEmpl[] courses = null;
		List<CourseByEmpl> courseList = null;

		String getCoursesByEmplURL = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
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