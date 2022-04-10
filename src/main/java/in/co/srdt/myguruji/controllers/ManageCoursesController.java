package in.co.srdt.myguruji.controllers;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.Course;
import in.co.srdt.myguruji.model.CourseCreate;
import in.co.srdt.myguruji.utils.ApplicationGateway;

@Controller
@RequestMapping("/administration")
public class ManageCoursesController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;
	
	//private static final Logger logger = LoggerFactory.getLogger(ManageCoursesController.class);
	
	/*@RequestMapping("/managecourses")
	public ModelAndView goToManageCourses(@RequestParam(name="operation", required=false) String operation) {
		ModelAndView mv = new ModelAndView("/manageCourses_M");
		
		final String themeColor = appTheme.getAppTheme();
		final String theme = "/css/w3-theme-" + appTheme.getAppTheme() + ".css";
		
		mv.addObject("theme", theme);
		mv.addObject("themeColor", themeColor);
		
		CourseCreate nCourse = new CourseCreate();
		nCourse.setCreatedby("Jaishankar");
		mv.addObject("courseCreate", nCourse);
		
		if(operation != null) {
			if(operation.equals("createCourse")) {
				mv.addObject("message", "Data saved successfully");
			}
		}
		return mv;
	}*/
	
	@RequestMapping("/managecourses")
	public String goToManageCourses(Model model) {
		//ModelAndView mv = new ModelAndView("/manageCourses_M");
		
		//final String themeColor = appTheme.getAppTheme();
		//final String theme = "/css/w3-theme-" + appTheme.getAppTheme() + ".css";
		
		//mv.addObject("theme", theme);
		//mv.addObject("themeColor", themeColor);
		
		CourseCreate nCourse = new CourseCreate();
		nCourse.setCreatedby("Jaishankar");
		model.addAttribute("courseCreate", nCourse);
		
		/*if(operation != null) {
			if(operation.equals("createCourse")) {
				mv.addObject("message", "Data saved successfully");
			}
		}*/
		return "forms/manageCourses :: manageCourses";
	}
	
	/*@RequestMapping("/managecourses/{courseid}")
	public ModelAndView goToEditCourses(@PathVariable("courseid") long courseid) {
		ModelAndView mv = new ModelAndView("/manageCourses_M");
		
		final String themeColor = appTheme.getAppTheme();
		final String theme = "/css/w3-theme-" + appTheme.getAppTheme() + ".css";
		
		mv.addObject("theme", theme);
		mv.addObject("themeColor", themeColor);
		
		CourseCreate nCourse = new CourseCreate();
		
		String getAllCoursesURL = "http://localhost:9194/api/course/getcourse/" + courseid;
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		ResponseEntity<Course> response = restTemplate.exchange(getAllCoursesURL, HttpMethod.GET, request, Course.class);
		
		nCourse.setCoursecode(response.getBody().getCode());
		nCourse.setCoursetitle(response.getBody().getTitle());
		nCourse.setCoursedescr(response.getBody().getDescr());
		nCourse.setCourseid(courseid);
		nCourse.setCreatedby("Jaishankar");
		
		mv.addObject("courseCreate", nCourse);
		
		return mv;
	}*/
	
	@RequestMapping("/managecourse/{courseid}")
	public String goToEditCourses(@PathVariable("courseid") long courseid, Model model) {
		//ModelAndView mv = new ModelAndView("/manageCourses_M");
		
		//final String themeColor = appTheme.getAppTheme();
		//final String theme = "/css/w3-theme-" + appTheme.getAppTheme() + ".css";
		
		//mv.addObject("theme", theme);
		//mv.addObject("themeColor", themeColor);
		
		CourseCreate nCourse = new CourseCreate();
		
		String getAllCoursesURL = appGateway.getBaseUrl()+"/api/course/getcourse/" + courseid;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<Course> response = restTemplate.exchange(getAllCoursesURL, HttpMethod.GET, request, Course.class);
		
		nCourse.setCoursecode(response.getBody().getCode());
		nCourse.setCoursetitle(response.getBody().getTitle());
		nCourse.setCoursedescr(response.getBody().getDescr());
		nCourse.setCourseid(courseid);
		nCourse.setCreatedby("Jaishankar");
		
		model.addAttribute("courseCreate", nCourse);
		
		return "forms/editCourse :: editCourse";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/managecourses/getCourses", method = RequestMethod.POST)
	public CourseCreate[] getAllCourses(HttpServletRequest searchCourseData) {
		
		String payLode = "{" +
				"\"id\"" + ":\"" +searchCourseData.getParameter("courseid")+ "\"," +
				"\"code\"" + ":\"" +searchCourseData.getParameter("coursecode")+ "\"," +
				"\"title\"" + ":\"" +searchCourseData.getParameter("coursetitle")+ "\"" +
				"}";
		
		
		CourseCreate[] courses = null;

		//String getAllCoursesURL = "http://localhost:9194/api/course/getcourse";
		String getAllCoursesURL = appGateway.getBaseUrl() + "/api/course/search";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLode, httpHeaders);
		
		//System.out.println(headers.get("Authorization").);
		
		ResponseEntity<CourseCreate[]> response = restTemplate.exchange(getAllCoursesURL, HttpMethod.POST, request, CourseCreate[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			courses = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return courses;
	}
	
	/*@RequestMapping(value="/managecourses/submitCourse", method = RequestMethod.POST)
	public String courseSubmit(@ModelAttribute("courseDetails") CourseCreate mCourse) throws JsonProcessingException {
		String createCoursesURL = "";
		String jsonString = "";
		ObjectMapper obj = new ObjectMapper();
		
		if(mCourse.getCourseid() == 0) {
			createCoursesURL = "http://localhost:9194/api/course/create";
			jsonString = obj.writeValueAsString(mCourse);
			jsonString = "[" + jsonString + "]";
		} else {
			createCoursesURL = "http://localhost:9194/api/course/update";
			jsonString = obj.writeValueAsString(mCourse);
		}
		 
		headers.setContentType(MediaType.APPLICATION_JSON); 
		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
		  
		ResponseEntity<String> response = restTemplate.exchange(createCoursesURL, HttpMethod.POST, request, String.class);
		
		String responseBody = response.getBody();
		
		logger.info(mCourse.toString());
		logger.info(responseBody);
		
		return "redirect:/administration/managecourses?operation=createCourse";
	}*/
	
	@ResponseBody
	@RequestMapping(value="/managecourses/submitCourse", method = RequestMethod.POST)
	public String courseSubmit(HttpServletRequest saveFormData){
		String message = "";
		String createCoursesURL = appGateway.getBaseUrl() + "/api/course/create";
		String payLoad = "[{" +
				"\"coursecode\"" + ":\"" +saveFormData.getParameter("coursecode")+ "\"," +
				"\"coursetitle\"" + ":\"" +saveFormData.getParameter("coursetitle")+ "\"," +
				"\"coursedescr\"" + ":\"" +saveFormData.getParameter("coursedescr")+ "\"," +
				"\"createdby\"" + ":\"" +saveFormData.getParameter("createdby")+ "\"" +
				"}]";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLoad, httpHeaders);
		  
		ResponseEntity<String> response = restTemplate.exchange(createCoursesURL, HttpMethod.POST, request, String.class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			message = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
			message = response.getBody();
		}
		
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping(value="/managecourses/updatecourse", method = RequestMethod.POST)
	public String edidCourse(HttpServletRequest editFormData) throws JsonProcessingException {
		//ObjectMapper obj = new ObjectMapper();
		String message = "";
		String payLode = "{" +
				"\"courseid\"" + ":\"" +editFormData.getParameter("courseid")+ "\"," +
				"\"coursecode\"" + ":\"" +editFormData.getParameter("coursecode")+ "\"," +
				"\"coursetitle\"" + ":\"" +editFormData.getParameter("coursetitle")+ "\"," +
				"\"coursedescr\"" + ":\"" +editFormData.getParameter("coursedescr")+ "\"," +
				"\"createdby\"" + ":\"" +editFormData.getParameter("createdby")+ "\"" +
				"}";
		
		System.out.println(payLode);
		
		String updateCourseURL = appGateway.getBaseUrl() + "/api/course/update";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLode, httpHeaders);
		
		ResponseEntity<String> response = restTemplate.exchange(updateCourseURL, HttpMethod.POST, request, String.class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			message = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
			message = response.getBody();
		}
		
		//String jsonString = obj.writeValueAsString(requestFromDT);
		//System.out.println(payLode);
		return message;
	}
	
	@RequestMapping(value="/managecourses/addCourse")
	public String addCourse(Model model) {
		CourseCreate courseCreate = new CourseCreate();
		courseCreate.setCreatedby("Jaishankar");
		model.addAttribute("courseCreate", courseCreate);
		return "forms/addCourse :: addCourse";
	}
	
}
