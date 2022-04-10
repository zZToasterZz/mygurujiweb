package in.co.srdt.myguruji.controllers;

import java.util.ArrayList;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.ActivityRegisterModel;
import in.co.srdt.myguruji.model.ActivityRegisterSingle;
import in.co.srdt.myguruji.model.CatgContentByUnitGET;
import in.co.srdt.myguruji.model.CommonModelReport;
import in.co.srdt.myguruji.model.CourseDetails;
import in.co.srdt.myguruji.model.LectureScheduleModel;
import in.co.srdt.myguruji.model.QuesBankSingle;
import in.co.srdt.myguruji.model.QuestionBankData;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.CoursePlanDetailsGet;
import in.co.srdt.myguruji.model.coursePlan.CoursePlanUnitDetails;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/reports")
public class ReportsController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;
	
	GetEmplId getemplid;
	
	@RequestMapping("/test")
	@ResponseBody
	public SingleResponseModel test()
	{
		return new SingleResponseModel("testtt");
	}
	
	@RequestMapping("/activityreport")
	public String activityreport(Authentication authentication ,HttpServletRequest request, Model model) 
	{
		String emplid = getemplid.getLogedinUserEmplid(authentication.getName());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		String url = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
		CourseModel[] courses = null;

		ResponseEntity<CourseModel[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CourseModel[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			courses = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		model.addAttribute("course",Arrays.asList(courses));
		model.addAttribute("createdby", emplid);
		return "forms/Reports/ActivityReport :: activityreport";
	}
	
	@ResponseBody
	@PostMapping("/getactivityreport")
	public ActivityRegisterModel[] getactivityreport(HttpServletRequest request, Authentication authentication)
	{
		String emplid = getemplid.getLogedinUserEmplid(authentication.getName());
		String batchPayload = request.getParameter("batches");
		String crseid = request.getParameter("courseid");
		
		String reqdata="{\"pnum\":\""+emplid+"\",\"crsecode\":\""+crseid+"\",\"batchid\":"+batchPayload+"}";
		ActivityRegisterModel[] arm = null;
		String URL = appGateway.getBaseUrl() + "/api/bireports/contentdata";
		//String URL = "http://10.8.20.35:9127/api/bireports/contentdata";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request1 = new HttpEntity<String>(reqdata, httpHeaders);

		ResponseEntity<ActivityRegisterModel[]> response = restTemplate.exchange(URL, HttpMethod.POST, request1, ActivityRegisterModel[].class);
		if(response.getStatusCode() == HttpStatus.OK) {
			arm = response.getBody();
			for(ActivityRegisterModel x:arm) {
				String a=Integer.toString(Integer.parseInt(x.getDocs_count())+Integer.parseInt(x.getLinks_count())+Integer.parseInt(x.getVids_count()));				
				x.setTotal(a);
			}
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		return arm;
	}
	
	@RequestMapping("/printactivityreport/{batches}/{crseid}")
	public String printactivityreport(@PathVariable("batches") String batches,
			@PathVariable("crseid") String crseid, Authentication authentication, HttpServletRequest request, Model model) 
	{
		String emplid = getemplid.getLogedinUserEmplid(authentication.getName());
		String reqdata="{\"pnum\":\""+emplid+"\",\"crsecode\":\""+crseid+"\",\"batchid\":["+
					batches.replace("SP", " ").replace("CO", ",")+"]}";
		ActivityRegisterModel[] arm = null;
		ActivityRegisterSingle ars=null;
		String URL = appGateway.getBaseUrl() + "/api/bireports/contentdata";
		//String URL = appGateway.getBaseUrl_san() + "/api/bireports/contentdata";
		String URL1 = appGateway.getBaseUrl() + "/api/bireports/contentdatasingle";
		//String URL1 = "http://10.8.20.35:9111/api/bireports/contentdatasingle";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(reqdata, httpHeaders);

		ResponseEntity<ActivityRegisterModel[]> response = restTemplate.exchange(URL, HttpMethod.POST, request1, ActivityRegisterModel[].class);
		ResponseEntity<ActivityRegisterSingle> response1 = restTemplate.exchange(URL1, HttpMethod.POST, request1, ActivityRegisterSingle.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			arm = response.getBody();
			for(ActivityRegisterModel x:arm) {
				String a=Integer.toString(Integer.parseInt(x.getDocs_count())+Integer.parseInt(x.getLinks_count())+Integer.parseInt(x.getVids_count()));				
				x.setTotal(a);				
			}			
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		if(response1.getStatusCode() == HttpStatus.OK) {
			ars = response1.getBody();
		} else {
			System.out.println("Request Failed1");
			System.out.println(response1.getStatusCode());
		}
		
		model.addAttribute("json",arm);
		model.addAttribute("jsons",ars);
		return "forms/Reports/printActivityReport";
		//return "forms/Reports/ActivityReport :: activityreport";
	}
	
	@RequestMapping("/questionbankreport")
	public String questionbankreport(Authentication authentication, HttpServletRequest request, Model model) 
	{
		String emplid = getemplid.getLogedinUserEmplid(authentication.getName());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		String url = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
		//String url = "http://10.8.20.35:9120/api/course/getcoursebyemplid/" + login.getEmplid();
		CourseModel[] courses = null;

		ResponseEntity<CourseModel[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CourseModel[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			courses = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		model.addAttribute("course",Arrays.asList(courses));
		model.addAttribute("createdby", emplid);
		return "forms/Reports/questionBankReport :: questionbankreport";
	}
	
	@ResponseBody
	@GetMapping("/getquestionbankeport/{crseid}")
	public QuestionBankData getquestionbankeport(HttpServletRequest request,@PathVariable String crseid, Authentication authentication)
	{
		String emplid = getemplid.getLogedinUserEmplid(authentication.getName());
		
		QuestionBankData qbd=null;
		//String URL = "http://10.8.20.35:9120/api/bireports/quesbankdata/"+emplid+"/"+crseid;
		String URL = appGateway.getBaseUrl() + "/api/bireports/quesbankdata/"+emplid+"/"+crseid;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<QuestionBankData> response = restTemplate.exchange(URL, HttpMethod.GET, request1, QuestionBankData.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			qbd=response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		return qbd;
	}
	
	@RequestMapping("/printqbreport/{crseid}")
	public String printqbreport(@PathVariable("crseid") String crseid,Authentication authentication, HttpServletRequest request, Model model) 
	{
		String emplid = getemplid.getLogedinUserEmplid(authentication.getName());
		QuesBankSingle ars=null;
		String URL = appGateway.getBaseUrl() + "/api/bireports/quesbankdatasingle/"+emplid+"/"+crseid;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);

		ResponseEntity<QuesBankSingle> response1 = restTemplate.exchange(URL, HttpMethod.GET, request1, QuesBankSingle.class);
		if(response1.getStatusCode() == HttpStatus.OK) {
			ars = response1.getBody();
		} else {
			System.out.println("Request Failed1");
			System.out.println(response1.getStatusCode());
		}
		model.addAttribute("jsons",ars);
		model.addAttribute("courseid",crseid);
		return "forms/Reports/printQbReport.html";
	}
	
	@RequestMapping("/printCoursePlan/{batchid}/{crseid}/{planid}")
	public String printCoursePlan(@PathVariable("crseid") String crseid, @PathVariable("batchid") String batchid
			, @PathVariable("planid") String planid, Authentication authentication, HttpServletRequest request, Model model) 
	{
		String emplid = getemplid.getLogedinUserEmplid(authentication.getName());
		
		String reqdata="{\"pnum\":\""+emplid+"\",\"crsecode\":\""+crseid+"\",\"batchid\":["+
				batchid+"]}";
		ActivityRegisterSingle ars=null;
		String URL1 = appGateway.getBaseUrl() + "/api/bireports/contentdatasingle";
		//String URL1 = "http://10.8.20.35:9111/api/bireports/contentdatasingle";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requ = new HttpEntity<String>(reqdata, httpHeaders);

		ResponseEntity<ActivityRegisterSingle> respo = restTemplate.exchange(URL1, HttpMethod.POST, requ, ActivityRegisterSingle.class);
		if(respo.getStatusCode() == HttpStatus.OK) {
			ars = respo.getBody();
		} else {
			System.out.println("Request Failed1");
			System.out.println(respo.getStatusCode());
		}
		
		String role = (String) request.getSession().getAttribute("roles");
				
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
		if(role.equals("Student"))
		{
			String asgnCrsCntUrl = appGateway.getBaseUrl()+"/api/assignment/getCourseAssignmentCount/"+coursePlans[0].getCourseplanid()+"/"+emplid;
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
		else if(role.equals("Faculty"))
		{
			try
			{
				String countUrl = appGateway.getBaseUrl()+"/api/assignment/getAssignmentCountForFaculty/"+coursePlans[0].getCourseplanid()+"/"+emplid+"/0";
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
			if(role.equals("Student"))
			{
				String asgnUntCntUrl = appGateway.getBaseUrl()+"/api/assignment/getUnitAssignmentCount/"+coursePlans[0].getCourseplanid()+"/"+emplid+"/"+x.getUnitid();
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
					String countUrl = appGateway.getBaseUrl()+"/api/assignment/getAssignmentCountForFaculty/"+coursePlans[0].getCourseplanid()+"/"+emplid+"/"+x.getUnitid();;
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
		
		model.addAttribute("planid",planid);
		model.addAttribute("jsons",ars);
		return "forms/Reports/printCoursePlan";
	}
	/*************************** IQAC REPORTS *************************************/
	@RequestMapping("/activityreportiqac")
	public String activityreportiqac(Authentication authentication, HttpServletRequest request, Model model) 
	{
		String emplid = getemplid.getLogedinUserEmplid(authentication.getName());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		String url = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
		CourseModel[] courses = null;

		ResponseEntity<CourseModel[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CourseModel[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			courses = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		model.addAttribute("course",Arrays.asList(courses));
		model.addAttribute("createdby", emplid);
		return "forms/Reports/ActivityReportiqac :: activityreportiqac";
	}
	
	@ResponseBody
	@GetMapping("/getactivityreportiqac/{faculty}/{courseid}")
	public ActivityRegisterModel[] getactivityreportiqac(HttpServletRequest request,
			@PathVariable("faculty") String faculty, @PathVariable("courseid") String courseid)
	{		
		ActivityRegisterModel[] arm = null;
		String URL = appGateway.getBaseUrl() + "/api/bireports/SearchDetailsByEmplidCourseId/"+faculty+"/"+courseid;
		//String URL = "http://10.8.20.35:9120/api/bireports/SearchDetailsByEmplidCourseId/"+faculty+"/"+courseid;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);

		ResponseEntity<ActivityRegisterModel[]> response = restTemplate.exchange(URL, HttpMethod.GET, request1, ActivityRegisterModel[].class);
		if(response.getStatusCode() == HttpStatus.OK) {
			arm = response.getBody();
			for(ActivityRegisterModel x:arm) {
				String a=Integer.toString(Integer.parseInt(x.getDocs_count())+Integer.parseInt(x.getLinks_count())+Integer.parseInt(x.getVids_count()));				
				x.setTotal(a);
			}
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return arm;
	}
	
	@RequestMapping("/questionbankreportiqac")
	public String questionbankreportiqac(Authentication authentication, HttpServletRequest request, Model model) 
	{
		String emplid = getemplid.getLogedinUserEmplid(authentication.getName());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		String url = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
		//String url = "http://10.8.20.35:9120/api/course/getcoursebyemplid/" + login.getEmplid();
		CourseModel[] courses = null;

		ResponseEntity<CourseModel[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CourseModel[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			courses = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		model.addAttribute("course",Arrays.asList(courses));
		model.addAttribute("createdby", emplid);
		return "forms/Reports/questionBankReportiqac :: questionbankreportiqac";
	}
	
	@ResponseBody
	@GetMapping("/getquesbankreportiqac/{type}/{faculty}/{courseid}")
	public QuestionBankData getquesbankreportiqac(HttpServletRequest request,
			@PathVariable("type") String type, @PathVariable("faculty") String faculty, @PathVariable("courseid") String courseid)
	{
		QuestionBankData qbd=null;
		
		String URL;
		if(type.equals("selectfaculty"))
		{
			URL = appGateway.getBaseUrl() + "/api/bireports/quesbankdatabyfaculty/"+faculty+"/"+courseid;
			//URL = "http://10.8.20.35:9120/api/bireports/quesbankdatabyfaculty/"+faculty+"/"+courseid;
		}
		else
		{
			URL = appGateway.getBaseUrl() + "/api/bireports/quesbankdatabycourse/"+faculty+"/"+courseid;
			//URL = "http://10.8.20.35:9120/api/bireports/quesbankdatabycourse/"+faculty+"/"+courseid;
		}
		//System.out.println(URL);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<QuestionBankData> response = restTemplate.exchange(URL, HttpMethod.GET, request1, QuestionBankData.class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			qbd=response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		return qbd;
	}
	
	@RequestMapping("/getFacultyList")
	@ResponseBody
	public SingleResponseModel[] getFacultyList(HttpServletRequest request)
	{
		SingleResponseModel[] resp = null;
		//String URL = "http://10.8.20.35:9120/api/bireports/getFacultyList";
		String URL = appGateway.getBaseUrl() + "/api/bireports/getFacultyList";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<SingleResponseModel[]> response = restTemplate.exchange(URL, HttpMethod.GET, request1, SingleResponseModel[].class);
		if(response.getStatusCode() == HttpStatus.OK) {
			resp=response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		 
		return resp;
	}
	
	@RequestMapping("/getCourseList")
	@ResponseBody
	public List<CourseModel> getCourseList(HttpServletRequest request)
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
			if(x.getCoursecode().equals("bb1230"))
				continue;
			crses.add(new CourseModel() {{
				setId(""+x.getCourseid());
				setCode(x.getCoursecode());
				setTitle(x.getCoursetitle());
				setDescr(x.getCoursedescr());
			}});
		}
		
		return crses;
	}
	
	@RequestMapping("/getCorrespondingValues/{faculty}/{courseid}")
	@ResponseBody
	public CommonModelReport[] getCorrespondingValues(@PathVariable("faculty") String faculty,
			@PathVariable("courseid") String courseid)
	{
		CommonModelReport[] resp = null;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request2 = new HttpEntity<String>(httpHeaders);
		String url = appGateway.getBaseUrl() + "/api/bireports/getCourseDropdown/"+faculty+"/"+courseid;
		//String url ="http://10.8.20.35:9120/api/bireports/getCourseDropdown/"+faculty+"/"+courseid;
		ResponseEntity<CommonModelReport[]> response2= restTemplate.exchange(url, HttpMethod.GET, request2, CommonModelReport[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
            resp = response2.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response2.getStatusCode());
        }
		
		return resp;
	}
}
