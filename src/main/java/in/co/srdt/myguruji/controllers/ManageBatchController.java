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
import in.co.srdt.myguruji.model.Batch;
import in.co.srdt.myguruji.model.Course;
import in.co.srdt.myguruji.utils.ApplicationGateway;

@Controller
@RequestMapping("/administration")
public class ManageBatchController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;
	
	@RequestMapping("/managebatches")
	public String goToManageClass() {

		return "forms/manageBatches :: manageBatches";
	}
	
	@RequestMapping(path="/batchdetails/{id}")
	public String displayBatchDetails(@PathVariable("id") String id, Model model)
	{
		
		Batch[] batches = null;
		
		String payLoad = "{\r\n" + 
				"	\"id\":\""+id+"\",\r\n" + 
				"	\"code\":\"\",\r\n" + 
				"	\"title\":\"\"\r\n" + 
				"}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLoad, httpHeaders);
		
		ResponseEntity<Batch[]> response = restTemplate.exchange(appGateway.getBaseUrl() + "/api/batch/search", HttpMethod.POST, request, Batch[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			batches = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		
		/*batches = restTemplate.postForObject("http://192.200.12.83:9194/api/batch/search", payLoad,new ArrayList<Batch>().getClass());
		
		System.out.println("Batch : "+batches);*/
		
		model.addAttribute("batchDetail",batches[0]);
		
		return "forms/batchDetails :: batchDetails";
	}
	
	/*@RequestMapping("/manageclasses/{classid}")
	public ModelAndView goToEditClass(@PathVariable("courseid") long courseid) {
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
	
	
	
	@ResponseBody
	@RequestMapping("/manageclasses/getBatches")
	public Batch[] getAllCourses(HttpServletRequest requestFromDT) {
		
		Batch[] batches = null;

		String getBatchesURL = appGateway.getBaseUrl() + "/api/batch/search";
		
		String payLode = "{" +
				"\"id\"" + ":\"" +requestFromDT.getParameter("batchid")+ "\"," +
				"\"code\"" + ":\"" +requestFromDT.getParameter("batchcode")+ "\"," +
				"\"title\"" + ":\"" +requestFromDT.getParameter("title")+ "\"" +
				"}";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLode, httpHeaders);
		
		ResponseEntity<Batch[]> response = restTemplate.exchange(getBatchesURL, HttpMethod.POST, request, Batch[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			batches = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return batches;
	}
	
	/*@RequestMapping(value="/manageclasses/submitClass", method = RequestMethod.POST)
	public String classSubmit(@ModelAttribute("courseDetails") CourseCreate mCourse) throws JsonProcessingException {
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
