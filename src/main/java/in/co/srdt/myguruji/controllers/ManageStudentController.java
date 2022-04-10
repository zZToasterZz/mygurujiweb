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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.Course;
import in.co.srdt.myguruji.model.Student;
import in.co.srdt.myguruji.utils.ApplicationGateway;

@Controller
@RequestMapping("/administration")
public class ManageStudentController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;
	
	@RequestMapping("/managestudents")
	public String goToManageClass() {

		return "forms/manageStudentDetails :: manageStudentDetails";
	}
	
	@RequestMapping("/getstudentdetails/{emplid}")
	public String getStudentDetails(@PathVariable("emplid") String emplid, Model model)
	{
		Student[] students = null;
		String getStudentsURL = appGateway.getBaseUrl() + "/api/student/search";
		String payLoad = "{\r\n" + 
				"	\"studentid\" : 0,\r\n" + 
				"    \"emplid\": \""+emplid+"\",\r\n" + 
				"    \"applnbr\": \"\",\r\n" + 
				"    \"campusid\": \"\",\r\n" + 
				"    \"firstname\": \"\",\r\n" + 
				"    \"emailaddr\": \"\",\r\n" + 
				"    \"primarycontact\": \"\"\r\n" + 
				"}";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLoad, httpHeaders);
		
		ResponseEntity<Student[]> response = restTemplate.exchange(getStudentsURL, HttpMethod.POST, request, Student[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			students = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		model.addAttribute("studentDetails",students[0]);
		
		return "forms/getStudentDetails :: getStudentDetails";
	}
	
	@ResponseBody
	@RequestMapping(value = "/manageStudents/getStudents", method= RequestMethod.POST)
	public Student[] getAllStudents(HttpServletRequest requestFromDT) 
	{	
		Student[] students = null;
		String getStudentsURL = appGateway.getBaseUrl() + "/api/student/search";
		
		String payLoad = "{\r\n" + 
				"	\"studentid\" : 0,\r\n" + 
				"    \"emplid\": \""+requestFromDT.getParameter("erpId")+"\",\r\n" + 
				"    \"applnbr\": \"\",\r\n" + 
				"    \"campusid\": \""+requestFromDT.getParameter("campusId")+"\",\r\n" + 
				"    \"firstname\": \""+requestFromDT.getParameter("firstName")+"\",\r\n" + 
				"    \"emailaddr\": \""+requestFromDT.getParameter("email")+"\",\r\n" + 
				"    \"primarycontact\": \""+requestFromDT.getParameter("contactNumber")+"\"\r\n" + 
				"}";
	
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLoad, httpHeaders);
		
		ResponseEntity<Student[]> response = restTemplate.exchange(getStudentsURL, HttpMethod.POST, request, Student[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			students = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		return students;
	}

	
	@ModelAttribute("courses")
	public List<Course> getCourses()
	{
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
