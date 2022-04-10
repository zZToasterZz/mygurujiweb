
package in.co.srdt.myguruji.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.Course;
import in.co.srdt.myguruji.model.FacultyDetails;
import in.co.srdt.myguruji.utils.ApplicationGateway;

@Controller
@RequestMapping("/administration")
public class ManageFacultyController {
	
	@Autowired
	private RestTemplate restTemplate;
		
	@Autowired
	private ApplicationGateway appGateway;
	
	@RequestMapping("/managefaculty")
	public String goToManageClass() {
		return "forms/manageFacultiesDetails :: manageFacultiesDetails";
	}
	@RequestMapping(path="/facultydetails/{id}")
	public String displayBatchDetails(@PathVariable("id") String emplid, Model model)
	{
		
		FacultyDetails[] faculties = null;

		
		String payLoad ="{\r\n" + 
				"	\"emplid\" : \"\",\r\n" + 
				"    \"facultycode\" : \"\",\r\n" + 
				"    \"facultyid\" : \""+emplid+"\",\r\n" + 
				"    \"designation\" : \"\",\r\n" + 
				"    \"firstname\" : \"\",\r\n" + 
				"    \"emailaddr\" : \"\",\r\n" + 
				"    \"primarycontact\" : \"\"\r\n" + 
				"}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLoad, httpHeaders);
		
		ResponseEntity<FacultyDetails[]> response = restTemplate.exchange(appGateway.getBaseUrl() + "/api/faculty/search", HttpMethod.POST, request, FacultyDetails[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			faculties = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		
		model.addAttribute("facultyDetail",faculties[0]);
		
		return "forms/facultyDetails :: facultyDetails";
	}
	
	
	@ResponseBody
	@RequestMapping("/manageclasses/getFaculties")
	public FacultyDetails[] getAllCourses(HttpServletRequest requestFromDT) {
		
		
		FacultyDetails[] faculties = null;

		String getBatchesURL = appGateway.getBaseUrl() + "/api/faculty/search";
		
		String payLode = "{" +
				"\"emailaddr\"" + ":\"" +requestFromDT.getParameter("email")+ "\"," +
				"\"emplid\"" + ":\"" +requestFromDT.getParameter("emplid")+ "\"," +
				"\"firstname\"" + ":\"" +requestFromDT.getParameter("firstName")+ "\"," +
				"\"primarycontact\"" + ":\"" +requestFromDT.getParameter("contactNumber")+ "\"," +
				"\"facultyid\"" + ":\"" +requestFromDT.getParameter("facId")+ "\"," +
				"\"designation\"" + ":\"\","+
				"\"facultycode\"" + ":\"\""+
				"}";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLode, httpHeaders);
		
		ResponseEntity<FacultyDetails[]> response = restTemplate.exchange(getBatchesURL, HttpMethod.POST, request, FacultyDetails[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			faculties = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return faculties;
	}
	
	
	
	@ModelAttribute("courses")
	public List<Course> getCourses(){
		Course[] courses = null;
		List<Course> courseList = null;

		String getAllCoursesURL = appGateway.getBaseUrl() + "/api/course/getcourse";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<Course[]> response = restTemplate.exchange(getAllCoursesURL, HttpMethod.GET, request, Course[].class);
		
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