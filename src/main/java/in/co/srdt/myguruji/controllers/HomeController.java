package in.co.srdt.myguruji.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import in.co.srdt.myguruji.config.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.model.Batch;
import in.co.srdt.myguruji.model.CatgContentByUnitGET;
import in.co.srdt.myguruji.model.CourseByEmpl;
import in.co.srdt.myguruji.model.FacultyDetails;
import in.co.srdt.myguruji.model.LectureScheduleModel;
import in.co.srdt.myguruji.model.PlanByBatchGET;
import in.co.srdt.myguruji.model.Student;
import in.co.srdt.myguruji.model.StudentDetailsModel;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.CoursePlanDetailsGet;
import in.co.srdt.myguruji.model.coursePlan.CoursePlanSearch;
import in.co.srdt.myguruji.model.coursePlan.CoursePlanUnitDetails;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.AppTheme;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@EnableOAuth2Sso
public class HomeController extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AppTheme appTheme;
	
	@Autowired
	private ApplicationGateway appGateway;
	
	@Autowired
	GetEmplId getEmplID;//=new GetEmplId;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.anyRequest()
				.authenticated()
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/login");
	}
	
	@RequestMapping(value = {"/","/home"})
	public String homePage(Authentication authentication, HttpServletRequest request, Model model) 
	{	
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		final String themeColor = appTheme.getAppTheme();
		final String theme = "/css/w3-theme-" + appTheme.getAppTheme() + ".css";

		CourseByEmpl[] courses = null;
		List<CourseByEmpl> courseList = null;

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);

		String getCoursesByEmplURL = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
		ResponseEntity<CourseByEmpl[]> response = restTemplate.exchange(getCoursesByEmplURL, HttpMethod.GET, request1, CourseByEmpl[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			courses = response.getBody();
			if(courses == null)	{
				courseList = new ArrayList<>();
			} else {
			    courseList = Arrays.asList(courses);
			}
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		model.addAttribute("courses", courseList);
		model.addAttribute("theme", theme);
		model.addAttribute("themeColor", themeColor);
		return "home";
	}

	@RequestMapping("/forms/dashboard")
	public String loadDashboard(Authentication authentication, HttpServletRequest request, Model model) {

		//request.isUserInRole("ROLE_Faculty")
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		if(request.isUserInRole("ROLE_Faculty")) {
			FacultyDetails[] facultyDetails = null;
			String getFacultyDetails = appGateway.getBaseUrl() + "/api/faculty/search";
			String payLoad = "{\r\n" +
					"	 \"facultyid\" : 0,\r\n" +
					"    \"facultycode\" : \"\",\r\n" +
					"    \"emplid\" : \""+emplid+"\",\r\n" +
					"    \"designation\" : \"\",\r\n" +
					"    \"firstname\" : \"\",\r\n" +
					"    \"emailaddr\" : \"\",\r\n" +
					"    \"primarycontact\" : \"\"\r\n" +
					"}";

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", AccessToken.getAccessToken());
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> facultyDetailRequest = new HttpEntity<String>(payLoad, httpHeaders);

			ResponseEntity<FacultyDetails[]> response = restTemplate.exchange(getFacultyDetails, HttpMethod.POST, facultyDetailRequest, FacultyDetails[].class);

			if(response.getStatusCode() == HttpStatus.OK)
			{
				facultyDetails = response.getBody();
			}
			else
			{
				System.out.println("Request Failed");
				System.out.println(response.getStatusCode());
			}
			model.addAttribute("facultyDetails", facultyDetails[0]);
			return "forms/dashboard_faculty :: dashboard";
		} else if(request.isUserInRole("ROLE_Student"))
		{
			String studentsrch = "{\r\n" +
					"	\"studentid\" : 0,\r\n" +
					"    \"emplid\": \""+emplid+"\",\r\n" +
					"    \"applnbr\": \"\",\r\n" +
					"    \"campusid\": \"\",\r\n" +
					"    \"firstname\": \"\",\r\n" +
					"    \"emailaddr\": \"\",\r\n" +
					"    \"primarycontact\": \"\"\r\n" +
					"}";
			String userDataUrl = appGateway.getBaseUrl()+"/api/student/search";

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", AccessToken.getAccessToken());
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> restreq1 = new HttpEntity<String>(studentsrch, httpHeaders);
			ResponseEntity<Student[]> stu = restTemplate.exchange(userDataUrl, HttpMethod.POST, restreq1, Student[].class);

			Student[] student = stu.getBody();
			String name = student[0].getFirstname()+" "+student[0].getLastname();

			model.addAttribute("name",name);
			model.addAttribute("campusid",authentication.getName());

			return "forms/dashboard_Students :: dashboard";
		}
		else if(request.isUserInRole("ROLE_Admin")){
			model.addAttribute("name","Admin");
			model.addAttribute("campusid",emplid);
			return "forms/dashboard_Students :: dashboard";
		}
		else if(request.isUserInRole("ROLE_Super Admin")){
			model.addAttribute("name","Administrator");
			model.addAttribute("campusid",emplid);
			return "forms/dashboard_Students :: dashboard";
		}
		else {
			if(emplid.equals("srmu_coe"))
				model.addAttribute("name","COE");
			else
				model.addAttribute("name","IQAC");
			model.addAttribute("campusid","SRMU");
			return "forms/dashboard_Students :: dashboard";
		}
	}

	@RequestMapping("/forms/navbar")
	public String loadTopNavbar(Authentication authentication, Model model) {
		model.addAttribute("emplid", authentication.getName());
		return "fragments/profilePic :: profilePic";
	}

	@RequestMapping("/courseplan/details/{planid}")
	public String goToCourse(@PathVariable("planid") String planid, Model model, HttpServletRequest request
			, Authentication authentication) 
	{
		String emplid=getEmplID.getLogedinUserEmplid(authentication.getName());
		String role=getEmplID.getRoleByRequest(request);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);

		String url = appGateway.getBaseUrl() + "/api/courseplan/getcourseplandetails/"+planid;

		CoursePlanDetailsGet[] coursePlans = null;

		ResponseEntity<CoursePlanDetailsGet[]> response1= restTemplate.exchange(url,HttpMethod.GET,request1, CoursePlanDetailsGet[].class);
		if(response1.getStatusCode() == HttpStatus.OK) {
			coursePlans = response1.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response1.getStatusCode());
		}

		List<CoursePlanUnitDetails> unt = new ArrayList<>();

		/***********SETTING COURSE LEVEL ASSIGNMENT COUNT*******START***/
		ResponseEntity<SingleResponseModel> countRes = null;
		HttpEntity<String> unireq = new HttpEntity<String>(httpHeaders);
		if(request.isUserInRole("ROLE_Student"))
		{
			String asgnCrsCntUrl = appGateway.getBaseUrl()+"/api/assignment/getCourseAssignmentCount/"
					+coursePlans[0].getCourseplanid()+"/"+emplid;
			countRes = restTemplate.exchange(asgnCrsCntUrl, HttpMethod.GET,unireq, SingleResponseModel.class);
			if(countRes.getStatusCode() == HttpStatus.OK)
			{
				SingleResponseModel count = countRes.getBody();
				if(count.getMessage() != null)
					coursePlans[0].setAssignmentCount(Long.parseLong(count.getMessage()));
				else
					coursePlans[0].setAssignmentCount(0);
			}
			else
			{
				System.out.println("Error fetching assignment count");
				coursePlans[0].setAssignmentCount(0);
			}
		}
		else if(request.isUserInRole("ROLE_Faculty"))
		{
			try
			{
				String countUrl = appGateway.getBaseUrl()+"/api/assignment/getAssignmentCountForFaculty/"+coursePlans[0].getCourseplanid()+"/"+authentication.getName()+"/0";
				countRes = restTemplate.exchange(countUrl, HttpMethod.GET,unireq, SingleResponseModel.class);
				if(countRes.getStatusCode() == HttpStatus.OK)
				{
					SingleResponseModel count = countRes.getBody();
					if(count.getMessage() != null)
						coursePlans[0].setAssignmentCount(Long.parseLong(count.getMessage()));
					else
						coursePlans[0].setAssignmentCount(0);
				}
				else
				{
					System.out.println("Error fetching assignment count");
					coursePlans[0].setAssignmentCount(0);
				}
			}
			catch(Exception e)
			{
				coursePlans[0].setAssignmentCount(0);
			}
		}
		/***********SETTING COURSE LEVEL ASSIGNMENT COUNT*******END*****/

		for(CoursePlanUnitDetails x : coursePlans[0].getUnits())
		{
			HttpEntity<String> request12 = new HttpEntity<String>(httpHeaders);
			String url2 = appGateway.getBaseUrl() + "/api/content/getcategorizedunitcontent/"+x.getUnitid();
			CatgContentByUnitGET[] unitContent = null;
			ResponseEntity<CatgContentByUnitGET[]> response2= restTemplate.exchange(url2,HttpMethod.GET,request12, CatgContentByUnitGET[].class);
			if(response2.getStatusCode() == HttpStatus.OK) {
				unitContent = response2.getBody();
			} else {
				System.out.println("Request Failed");
				System.out.println(response2.getStatusCode());
			}

			for(CatgContentByUnitGET y : unitContent)
			{
				if(y.getContenttype().equals("docs"))
				{
					x.setDocsCount(y.getContentcount());
				}
				if(y.getContenttype().equals("vid"))
				{
					x.setVidsCount(y.getContentcount());
				}
			}

			String url3 = appGateway.getBaseUrl() + "/api/lecschedule/getschedulebyunitid/"+x.getUnitid();
			LectureScheduleModel[] lecs = null;
			ResponseEntity<LectureScheduleModel[]> response3= restTemplate.exchange(url3,HttpMethod.GET,request12, LectureScheduleModel[].class);
			if(response3.getStatusCode() == HttpStatus.OK) {
				lecs = response3.getBody();
			} else {
				System.out.println("Request Failed");
				System.out.println(response3.getStatusCode());
			}

			int links = 0;
			int classes = 0;
			for(LectureScheduleModel y : lecs)
			{
				if(y.getTypes().equals("link") || y.getTypes().equals("3"))
				{
					++links;
				}
				else if(y.getTypes().equals("onlcls") || y.getTypes().equals("4"))
				{
					++classes;
				}
			}
			x.setLinksCount(links);
			x.setClassesCount(classes);

			/***********SETTING UNIT LEVEL ASSIGNMENT COUNT*******START****/
			if(request.isUserInRole("ROLE_Student"))
			{
				String asgnUntCntUrl = appGateway.getBaseUrl()+"/api/assignment/getUnitAssignmentCount/"
						+coursePlans[0].getCourseplanid()+"/"+emplid+"/"+x.getUnitid();
				countRes = restTemplate.exchange(asgnUntCntUrl, HttpMethod.GET,unireq, SingleResponseModel.class);
				if(countRes.getStatusCode() == HttpStatus.OK)
				{
					SingleResponseModel count = countRes.getBody();
					if(count.getMessage() != null)
						x.setAssignmentCount(Long.parseLong(count.getMessage()));
					else
						x.setAssignmentCount(0);
				}
				else
				{
					System.out.println("Error fetching assignment count");
				}
			}
			else
			{
				try
				{
					String countUrl = appGateway.getBaseUrl()+"/api/assignment/getAssignmentCountForFaculty/"
							+coursePlans[0].getCourseplanid()+"/"+emplid+"/"+x.getUnitid();;
					countRes = restTemplate.exchange(countUrl, HttpMethod.GET,unireq, SingleResponseModel.class);
					if(countRes.getStatusCode() == HttpStatus.OK)
					{
						SingleResponseModel count = countRes.getBody();
						if(count.getMessage() != null)
							x.setAssignmentCount(Long.parseLong(count.getMessage()));
						else
							x.setAssignmentCount(0);
					}
					else
					{
						System.out.println("Error fetching assignment count");
						x.setAssignmentCount(0);
					}
				}
				catch(Exception e)
				{
					x.setAssignmentCount(0);
				}
			}
			/***********SETTING UNIT LEVEL ASSIGNMENT COUNT*******END******/
			unt.add(x);
		}

		coursePlans[0].setUnits(unt);

		 /*for(CoursePlanUnitDetails x : coursePlans[0].getUnits())
		 {
			 System.out.println("UNIT : "+x.getUnitid());
			 System.out.println("Docs "+x.getDocsCount());
			 System.out.println("Vids "+x.getVidsCount());
			 System.out.println("Links "+x.getLinksCount());
			 System.out.println("Classes "+x.getClassesCount());
		 }*/

		model.addAttribute("plan",coursePlans[0]);		
		model.addAttribute("loginid", emplid);
		model.addAttribute("role", role);
		return "forms/courseDetails :: courseDetails";
	}

	@ResponseBody
	@RequestMapping(value = "/home/loadCoursePlans")
	public List<CoursePlanSearch> getAllCoursePlan(HttpServletRequest req, Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName()); 

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		String url1 = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
		CourseModel[] courses = null;

		ResponseEntity<CourseModel[]> response1= restTemplate.exchange(url1 ,HttpMethod.GET,request1, CourseModel[].class);

		if(response1.getStatusCode() == HttpStatus.OK) {
			courses = response1.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response1.getStatusCode());
		}
		/*GET COURSES*/

		CoursePlanSearch[] coursePlan = null;
		List<CoursePlanSearch> cp = new ArrayList<CoursePlanSearch>();
		for(int i=0; i<courses.length; i++)
		{
			try
			{
				String payLoad = "{" +
						"\"courseid\": \""+courses[i].getId()+"\"," +
						"\"plancode\": \"\"," +
						"\"plantitle\": \"\"," +
						"\"emplid\": \""+emplid+"\"" +
						"}";

				HttpEntity<String> request = new HttpEntity<String>(payLoad, httpHeaders);

				String url = appGateway.getBaseUrl() + "/api/courseplan/search";

				ResponseEntity<CoursePlanSearch[]> response= restTemplate.exchange(url,HttpMethod.POST,request, CoursePlanSearch[].class);
				if(response.getStatusCode() == HttpStatus.OK) {
					coursePlan = response.getBody();
				} else {
					System.out.println("Request Failed");
					System.out.println(response.getStatusCode());
				}

				cp.addAll( Arrays.asList(coursePlan) );
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return cp;
	}

	@ResponseBody
	@RequestMapping(value = "/home/loadStdntCoursePlans")
	public List<PlanByBatchGET> getStdntCoursePlan(HttpServletRequest req, Authentication authentication)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String stdntSrch = "{\r\n" +
				"	\"studentid\" : 0,\r\n" +
				"    \"emplid\": \"\",\r\n" +
				"    \"applnbr\": \"\",\r\n" +
				"    \"campusid\": \""+authentication.getName()+"\",\r\n" +
				"    \"firstname\": \"\",\r\n" +
				"    \"emailaddr\": \"\",\r\n" +
				"    \"primarycontact\": \"\"\r\n" +
				"}";
		HttpEntity<String> request1 = new HttpEntity<String>(stdntSrch, httpHeaders);
		String url1 = appGateway.getBaseUrl() + "/api/student/search";
		Student[] student = null;

		ResponseEntity<Student[]> response1= restTemplate.exchange(url1 ,HttpMethod.POST,request1, Student[].class);

		if(response1.getStatusCode() == HttpStatus.OK) 
		{
			student = response1.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response1.getStatusCode());
		}

		/*GET STUDENT BATCHES*/
		long studentId = student[0].getStudentid();
		StudentDetailsModel studentDetails = null;
		HttpEntity<String> request2 = new HttpEntity<String>(httpHeaders);
		String url2 = appGateway.getBaseUrl() + "/api/student/getstudentbyid/"+studentId;

		ResponseEntity<StudentDetailsModel> response2= restTemplate.exchange(url2 ,HttpMethod.GET,request2, StudentDetailsModel.class);

		if(response2.getStatusCode() == HttpStatus.OK) {
			studentDetails = response2.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}

		List<PlanByBatchGET> planByBatch = new ArrayList<PlanByBatchGET>();

		if(studentDetails.getBatches() == null)
		{
			return planByBatch;
		}

		for(Batch x : studentDetails.getBatches())
		{
			PlanByBatchGET[] plan = null;
			HttpEntity<String> request3 = new HttpEntity<String>(httpHeaders);
			String url3 = appGateway.getBaseUrl() + "/api/courseplan/getcourseplandetailsbybatchid/"+x.getBatchid();

			ResponseEntity<PlanByBatchGET[]> response3= restTemplate.exchange(url3 ,HttpMethod.GET,request3, PlanByBatchGET[].class);

			if(response3.getStatusCode() == HttpStatus.OK) {
				plan = response3.getBody();
			} else {
				System.out.println("Request Failed");
				System.out.println(response3.getStatusCode());
			}

			//if no courseplan exists for current batch then move on to the next batch.
			if(plan == null)
			{
				continue;
			}

			if(plan.length > 0) {
				//plan[0].setBatchdescr(x.getDescr());
				plan[0].setBatchdescr(plan[0].getCourseplantitle());
				planByBatch.add(plan[0]);
			}
		}
		return planByBatch;
	}

	/*@RequestMapping("/locked")
	public String profilelocked(SessionStatus status, Model model)
	{
		String msg1 = (String)session.getAttribute("lockmsg1");
		String msg2 = (String)session.getAttribute("lockmsg2");

		session.removeAttribute("roles");
		session.removeAttribute("login");
		session.invalidate();
		status.setComplete();

		model.addAttribute("message1", msg1);
		model.addAttribute("message2", msg2);

		return "forms/locked/locked";
	}*/

	@ResponseBody
	@RequestMapping("/server/ping")
	public String ping()
	{
		return "ok";
	}
	
	@RequestMapping("/changepwd")
	public String changePassword()
	{
		return "forms/login/changePwd :: changePwd";
	}
	
	@RequestMapping("/login/changepass")
	@ResponseBody
	public String changePass(@RequestParam("oldpwd")String oldpwd, @RequestParam("newpwd")String newpwd, Authentication authentication, HttpServletRequest request)
	{
		String emplid=getEmplID.getLogedinUserEmplid(authentication.getName());
		String role=getEmplID.getRoleByRequest(request);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		String payLoad = "";
		if(role.equals("Student"))
		{
			//System.out.println("Here");
			String url = appGateway.getBaseUrl()+"/mg/api/student/search";
			String srchString = "{\r\n" + 
					"	 \"studentid\" : 0,\r\n" + 
					"    \"emplid\": \"\",\r\n" + 
					"    \"applnbr\": \"\",\r\n" + 
					"    \"campusid\": \""+authentication.getName()+"\",\r\n" + 
					"    \"firstname\": \"\",\r\n" + 
					"    \"emailaddr\": \"\",\r\n" + 
					"    \"primarycontact\": \"\"\r\n" + 
					"}";
			HttpEntity<String> req = new HttpEntity<String>(srchString, httpHeaders);
			Student[] stu = null;
			ResponseEntity<Student[]> res = restTemplate.exchange(url, HttpMethod.POST, req, Student[].class);
			stu = res.getBody();
			
			payLoad = "{\"loginid\" : \""+stu[0].getCampusid()+"\","+
					 "\"oldpwd\" : \""+oldpwd+"\","+
					 "\"newpwd\" : \""+newpwd+"\"}";
		}
		else
		{
			payLoad = "{\"loginid\" : \""+emplid+"\","+
					 "\"oldpwd\" : \""+oldpwd+"\","+
					 "\"newpwd\" : \""+newpwd+"\"}";
		}
		
		 SingleResponseModel res = new SingleResponseModel();
		 try
		 {			 
			 HttpEntity<String> req = new HttpEntity<String>(payLoad, httpHeaders);
			 
			 String url = appGateway.getOauthUrl() + "/mgoauth/userlogin/changepwd";
			 ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url,HttpMethod.POST,req, SingleResponseModel.class);
			 if(response.getStatusCode() == HttpStatus.OK)
			 {
				 res = response.getBody();
			 }
			 else
			 {
				 System.out.println("Request Failed");
				 System.out.println(response.getStatusCode());
				 res.setMessage("ERROR");
			 }
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
			 res.setMessage("ERROR");
		 }
		return res.getMessage();
	}
}