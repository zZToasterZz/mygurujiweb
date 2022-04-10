package in.co.srdt.myguruji.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.AssessResponseModel;
import in.co.srdt.myguruji.model.AssessmentByCourseIdGET;
import in.co.srdt.myguruji.model.Batch;
import in.co.srdt.myguruji.model.BatchDetailsModel;
import in.co.srdt.myguruji.model.BatchDetailsbySchedule;
import in.co.srdt.myguruji.model.Course;
import in.co.srdt.myguruji.model.CourseDetails;
import in.co.srdt.myguruji.model.FacultyDetails;
import in.co.srdt.myguruji.model.ScheduleModel;
import in.co.srdt.myguruji.model.SchedulesByBatch;
import in.co.srdt.myguruji.model.Student;
import in.co.srdt.myguruji.model.UserCredential;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;
import in.co.srdt.myguruji.utils.MailProperties;
import in.co.srdt.myguruji.utils.MailerInfo;

@Controller
@RequestMapping("/manageassessschedule")
public class ManageSchedulingController
{
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;
	
	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;
	
	@Autowired
	MailerInfo mailerInfo;
	
	@Autowired
	MailProperties props;
	
	@Autowired
	GetEmplId getEmplID;//=new GetEmplId;
	
	@RequestMapping("/createschedule")
	public String createSchedule(Authentication authentication, HttpServletRequest request, Model model)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		String role = getEmplID.getRoleByRequest(request);
		/***********GET ROLES**********/
		//String getRolesUrl = appGateway.getBaseUrl() + "/api/role/getrolesbyloginid/"+emplid+"";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		//HttpEntity<String> rolereq = new HttpEntity<String>(httpHeaders);
		
		//ResponseEntity<Role[]> roleres = restTemplate.exchange(getRolesUrl, HttpMethod.GET, rolereq, Role[].class);
		//List<Role> roles = Arrays.asList(roleres.getBody());
		
		if(role==null)
		{
			UserCredential userCredential = new UserCredential();
			model.addAttribute("userCredential", userCredential);
			model.addAttribute("flag", false);
			model.addAttribute("isInvalid","N");
			model.addAttribute("hasRole", "N");
			return "forms/login/login";
		}
		
		model.addAttribute("user_role_scdl",role);
		/***********GET ROLES*********END****/
		
		String batchPayload = request.getParameter("batches");
		String classPayload = request.getParameter("classes");
		String courseid = request.getParameter("courseid");
		String reschedule = request.getParameter("reschedule");
		String assessid;
		
		if(reschedule.equals("yes")) 
		{
			assessid = request.getParameter("assessmentid");
			SchedulesByBatch[] schedulesByBatches = null;
			String URL = appGateway.getBaseUrl() + "/api/assessment/getexistingschedulebybatchid";
			HttpEntity<String> request1 = new HttpEntity<String>(batchPayload, httpHeaders);

			ResponseEntity<SchedulesByBatch[]> response = restTemplate.exchange(URL, HttpMethod.POST, request1, SchedulesByBatch[].class);
			if(response.getStatusCode() == HttpStatus.OK) {
				schedulesByBatches = response.getBody();
			} else {
				System.out.println("Request Failed");
				System.out.println(response.getStatusCode());
			}
			for(SchedulesByBatch x:schedulesByBatches) 
			{			
				if(assessid.equals(String.valueOf(x.getAssessmentid()))) 
				{
					model.addAttribute("assid",x.getAssessmentid());
					model.addAttribute("assdes",x.getAssessmentitle());
					model.addAttribute("descr",x.getAssessmendescr());
					model.addAttribute("selbatches",x.getBatchid());
					model.addAttribute("selclass",x.getBatchcodes());
					break;
				}				
			}			
		}
		
		if(role.equalsIgnoreCase("coe"))
		{
			List<CourseModel> crses = new ArrayList<>();
			
			CourseDetails[] crse = null;
			String payLoad="{\r\n" + 
					"	\"id\":\"\",\r\n" + 
					"	\"code\":\"\",\r\n" + 
					"	\"title\":\"\",\r\n" + 
					"	\"emplid\":\"\"\r\n" + 
					"}";
			HttpEntity<String> request2 = new HttpEntity<String>(payLoad,httpHeaders);
			String url2 = appGateway.getBaseUrl() + "/api/course/search";
			ResponseEntity<CourseDetails[]> response2= restTemplate.exchange(url2, HttpMethod.POST, request2, CourseDetails[].class);
			if(response2.getStatusCode() == HttpStatus.OK) {
	            crse = response2.getBody();
	        } else {
	            System.out.println("Request Failed");
	            System.out.println(response2.getStatusCode());
	        }
			
			for(CourseDetails x : crse)
			{
				crses.add(new CourseModel() {{
					setId(""+x.getCourseid());
					setCode(x.getCoursecode());
					setTitle(x.getCoursetitle());
					setDescr(x.getCoursedescr());
				}});
			}
			String cdescr="";
			for(CourseModel x:crses)
			{
				if(courseid.equals(x.getId()))
				{
					cdescr=courseid+" : "+x.getDescr();
				}
			}
			model.addAttribute("cdescr",cdescr);
			model.addAttribute("course",crses);
			model.addAttribute("cid",courseid);
			if(!reschedule.equals("yes"))
			{
				model.addAttribute("selbatches",batchPayload.substring(1,batchPayload.length()-1));
				model.addAttribute("selclass",classPayload);
			}			
		}
		else
		{
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
			if(!reschedule.equals("yes"))
			{
				model.addAttribute("selbatches",batchPayload.substring(1,batchPayload.length()-1));
				model.addAttribute("selclass",classPayload);
			}
			String cdescr="";
			for(CourseModel x:courses) 
			{
				if(courseid.equals(x.getId())) 
				{
					cdescr=courseid+" : "+x.getDescr();
				}
			}
			model.addAttribute("course",Arrays.asList(courses));
			model.addAttribute("cdescr",cdescr);
			model.addAttribute("cid",courseid);
		}
		model.addAttribute("createdby", emplid);
		model.addAttribute("reschedule",reschedule);
		
		return "forms/scheduling/createSchedule :: createSchedule";
	}
	
	@RequestMapping("/createschedulesearch")
	public String createSchedulesearch(Authentication authentication, HttpServletRequest request, Model model)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		String role = getEmplID.getRoleByRequest(request);
		/***********GET ROLES**********/
		//String getRolesUrl = appGateway.getBaseUrl() + "/api/role/getrolesbyloginid/"+emplid+"";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> rolereq = new HttpEntity<String>(httpHeaders);
		
		//ResponseEntity<Role[]> roleres = restTemplate.exchange(getRolesUrl, HttpMethod.GET, rolereq, Role[].class);
		//List<Role> roles = Arrays.asList(roleres.getBody());
		
		if(role==null)
		{
			UserCredential userCredential = new UserCredential();
			model.addAttribute("userCredential", userCredential);
			model.addAttribute("flag", false);
			model.addAttribute("isInvalid","N");
			model.addAttribute("hasRole", "N");
			return "forms/login/login";
		}
		
		model.addAttribute("user_role_scdl",role);
		/***********GET ROLES**********/
		
		if(role.equalsIgnoreCase("coe"))
		{
			List<CourseModel> crses = new ArrayList<>();
			
			CourseDetails[] crse = null;
			String payLoad="{\r\n" + 
					"	\"id\":\"\",\r\n" + 
					"	\"code\":\"\",\r\n" + 
					"	\"title\":\"\",\r\n" + 
					"	\"emplid\":\"\"\r\n" + 
					"}";
			HttpEntity<String> request2 = new HttpEntity<String>(payLoad,httpHeaders);
			String url2 = appGateway.getBaseUrl() + "/api/course/search";
			ResponseEntity<CourseDetails[]> response2= restTemplate.exchange(url2, HttpMethod.POST, request2, CourseDetails[].class);
			if(response2.getStatusCode() == HttpStatus.OK) {
	            crse = response2.getBody();
	        } else {
	            System.out.println("Request Failed");
	            System.out.println(response2.getStatusCode());
	        }
			
			for(CourseDetails x : crse)
			{
				crses.add(new CourseModel() {{
					setId(""+x.getCourseid());
					setCode(x.getCoursecode());
					setTitle(x.getCoursetitle());
					setDescr(x.getCoursedescr());
				}});
			}
			
			model.addAttribute("course",crses);
		}
		else
		{			
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
		}
		model.addAttribute("createdby", emplid);
		
		return "forms/scheduling/createSchedule :: createSchedulesearch";
	}
	
	@ResponseBody
	@RequestMapping("/getBatches/{id}")
	public Batch[] getAllCourses(@PathVariable String id)
	{
		Batch[] batches = null;
		
		String getBatchesURL = appGateway.getBaseUrl() + "/api/batch/getbatchbycourseid/"+id;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<Batch[]> response = restTemplate.exchange(getBatchesURL, HttpMethod.GET, request, Batch[].class);
		
		if(response.getStatusCode() == HttpStatus.OK){
			batches = response.getBody();
		} else{
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return batches;
	}
	
	@RequestMapping("/getassessmentbycourseid/{id}")
	@ResponseBody
	public AssessmentByCourseIdGET[] getAssessmentByCrseId(@PathVariable("id") String id, HttpServletRequest request1
			,Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		String role = getEmplID.getRoleByRequest(request1);
		AssessmentByCourseIdGET[] assessmentFiltered = null;
		String url = appGateway.getBaseUrl() + "/api/assessment/getnotscheduledassessmentbycourseid/"+id+"/"+emplid;
		
		/***********GET ROLES**********/
		//String getRolesUrl = appGateway.getBaseUrl() + "/api/role/getrolesbyloginid/"+emplid+"";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> rolereq = new HttpEntity<String>(httpHeaders);
		
		//ResponseEntity<Role[]> roleres = restTemplate.exchange(getRolesUrl, HttpMethod.GET, rolereq, Role[].class);
		//List<Role> roles = Arrays.asList(roleres.getBody());
		
		if(role==null)
		{
			return assessmentFiltered;
		}
		
		if(role.equalsIgnoreCase("coe"))
		{
			url = appGateway.getBaseUrl() + "/api/assessment/getnotscheduledassessmentbycourseid/"+id;
		}
		/***********GET ROLES*********END****/
		
		AssessmentByCourseIdGET[] assessments = null;
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		ResponseEntity<AssessmentByCourseIdGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, AssessmentByCourseIdGET[].class);
		if(response.getStatusCode() == HttpStatus.OK) {
			assessments = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		/********TEMPORARY CODE TO FILTER ASSESSMENTS WITH MAPPED TEMPLATES ONLY********/
		int len = 0;
		for(AssessmentByCourseIdGET x : assessments){
			if(x.getTemplateid() > 0)
			{
				len++;
			}
		}
		assessmentFiltered = new AssessmentByCourseIdGET[len];
		
		int ind = 0;
		for(AssessmentByCourseIdGET x : assessments)
		{
			if(x.getTemplateid() > 0)
			{
				assessmentFiltered[ind] = x;
				ind++;
			}
		}
		return assessmentFiltered;
		/********TEMPORARY CODE TO FILTER ASSESSMENTS WITH MAPPED TEMPLATES ONLY********/
		
		//return assessments;
	}
	
	@PostMapping("/deleteasses/{id}")
	@ResponseBody
	public SingleResponseModel deleteSchedule(@PathVariable String id)
	{
		String URL = appGateway.getBaseUrl() + "/api/assessment/deleteSchedule/"+id;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		SingleResponseModel res = null;
		ResponseEntity<SingleResponseModel> response= restTemplate.exchange(URL,HttpMethod.POST,request, SingleResponseModel.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			res = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		URL=appGateway.getBaseUrl() + "/api/assessment/getbatchbyassessmentid/"+id;
		//URL="http://10.8.20.35:9128/api/assessment/getbatchbyscheduleid/"+id;
		BatchDetailsbySchedule[] schedule=null;
		ResponseEntity<BatchDetailsbySchedule[]> respons= restTemplate.exchange(URL,HttpMethod.GET,request, BatchDetailsbySchedule[].class);
		
		if(respons.getStatusCode() == HttpStatus.OK) {
			schedule = respons.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(respons.getStatusCode());
		}
		
		List<String> emails = new ArrayList<>();
		List<String> emailsf = new ArrayList<>();
		
		for(BatchDetailsbySchedule z : schedule)
		{
			String x=z.getBatchid();
			System.out.println("GET BTCH: "+x);
			String URL1 = appGateway.getBaseUrl() + "/api/batch/getbatchdetailsbyid/"+x;
			HttpEntity<BatchDetailsModel> request1 = new HttpEntity<BatchDetailsModel>(httpHeaders);
			BatchDetailsModel res1 = null;
			ResponseEntity<BatchDetailsModel> response1= restTemplate.exchange(URL1,HttpMethod.GET,request1, BatchDetailsModel.class);
			if(response1.getStatusCode() == HttpStatus.OK)
			{
				res1 = response1.getBody();
				//System.out.println(res1.getStudents().get(0).toString());
			}
			else
			{
				System.out.println("Request Failed");
				System.out.println(response.getStatusCode());
				res.setMessage("ERROR");
				return res;
			}
			
			for(Student y : res1.getStudents())
			{
				emails.add(y.getEmailaddr());
			}
			for(FacultyDetails y : res1.getFaculty())
			{
				emailsf.add(y.getEmailaddr());
			}			
		}
		
		URL = appGateway.getBaseUrl() + "/api/assessment/getassessmentdetailsbyid/"+id;
		HttpEntity<String> req = new HttpEntity<String>(httpHeaders);
		AssessResponseModel resp = null;
		ResponseEntity<AssessResponseModel> respon= restTemplate.exchange(URL,HttpMethod.GET,req, AssessResponseModel.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			resp = respon.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		String htmlBody;
		try
		{
			String originalString = schedule[0].getStartdatetime();
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
            String newstr = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss").format(date);
            String dttm[] = newstr.split(",");
            
            String startdate = dttm[0];
            String starttime = dttm[1];
            
            originalString = schedule[0].getEnddatetime();
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
            newstr = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss").format(date);
            dttm = newstr.split(",");
            
            String enddate = dttm[0];
            String endtime = dttm[1];
                        
            Context thymeleafContext = new Context();
    		thymeleafContext.setVariable("subject", id);
    		thymeleafContext.setVariable("startdate", startdate);
    		thymeleafContext.setVariable("starttime", starttime);
    		thymeleafContext.setVariable("enddate", enddate);
    		thymeleafContext.setVariable("endtime", endtime);
    		thymeleafContext.setVariable("descr", resp.getTitle());
    		thymeleafContext.setVariable("crse", resp.getCoursedescr());
    		thymeleafContext.setVariable("textbody", "An assessment has been cancelled.");
    		
    	    htmlBody = thymeleafTemplateEngine.process("mail/assessmentSchedule.html", thymeleafContext);
		}
		catch(Exception e1)
		{
			res.setMessage("ERROR");
			return res;
		}
		try
	    {
	    	String bcc[] = new String[emails.size()+emailsf.size()];
	    	int counter = 0;	    	
	    	for(String x : emails)
	    	{
	    		bcc[counter] = x;
	    		counter++;
	    	}
			for(String x : emailsf)
	    	{
	    		bcc[counter] = x;
				counter++;
	    	}
			sendmail( bcc, htmlBody,"Assessment Cancelled");
		}
	    catch(MessagingException | IOException e)
	    {
	    	System.out.println(e.getMessage());
	    	res.setMessage("MAIL_ERROR");
	    	return res;
		}
		//res.setCreatedby("MAIL_AVOID");
		return res;
	}
	
	@PostMapping("/rescheduleasses")
	@ResponseBody
	public ScheduleModel reSchedule(@RequestBody ScheduleModel schedule)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ScheduleModel> request = new HttpEntity<ScheduleModel>(schedule,httpHeaders);
		ScheduleModel res = null;
		String URL = appGateway.getBaseUrl() + "/api/assessment/assesementreschedule";
		//String URL ="http://10.8.20.35:9120/api/assessment/assesementreschedule";
		ResponseEntity<ScheduleModel> response= restTemplate.exchange(URL,HttpMethod.POST,request, ScheduleModel.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			res = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		URL = appGateway.getBaseUrl() + "/api/course/getcourse/"+schedule.getCourseid();
		HttpEntity<String> req = new HttpEntity<String>(httpHeaders);
		Course resp = null;
		ResponseEntity<Course> respon= restTemplate.exchange(URL,HttpMethod.GET,req, Course.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			resp = respon.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		List<String> emails = new ArrayList<>();
		
		for(long x : schedule.getBatchids())
		{
			System.out.println("GET BTCH: "+x);
			String URL1 = appGateway.getBaseUrl() + "/api/batch/getbatchdetailsbyid/"+x;
			HttpEntity<BatchDetailsModel> request1 = new HttpEntity<BatchDetailsModel>(httpHeaders);
			BatchDetailsModel res1 = null;
			ResponseEntity<BatchDetailsModel> response1= restTemplate.exchange(URL1,HttpMethod.GET,request1, BatchDetailsModel.class);
			if(response1.getStatusCode() == HttpStatus.OK)
			{
				res1 = response1.getBody();
				//System.out.println(res1.getStudents().get(0).toString());
			}
			else
			{
				System.out.println("Request Failed");
				System.out.println(response.getStatusCode());
				res.setCreatedby("ERROR");
				return res;
			}
			
			for(Student y : res1.getStudents())
			{
				emails.add(y.getEmailaddr());
			}
			for(FacultyDetails y : res1.getFaculty())
			{
				emails.add(y.getEmailaddr());
			}
		}
		String htmlBody;
		try
		{
			String originalString = schedule.getStartdatetime();
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
            String newstr = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss").format(date);
            String dttm[] = newstr.split(",");
            
            String startdate = dttm[0];
            String starttime = dttm[1];
            
            originalString = schedule.getEnddatetime();
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
            newstr = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss").format(date);
            dttm = newstr.split(",");
            
            String enddate = dttm[0];
            String endtime = dttm[1];
            
            Context thymeleafContext = new Context();
    		thymeleafContext.setVariable("subject", schedule.getAssessmentid());
    		thymeleafContext.setVariable("startdate", startdate);
    		thymeleafContext.setVariable("starttime", starttime);
    		thymeleafContext.setVariable("enddate", enddate);
    		thymeleafContext.setVariable("endtime", endtime);
    		thymeleafContext.setVariable("descr", schedule.getDescr());
    		thymeleafContext.setVariable("textbody", "An assessment has been re-scheduled.");
    		thymeleafContext.setVariable("crse", resp.getTitle());
    		
    	    htmlBody = thymeleafTemplateEngine.process("mail/assessmentSchedule.html", thymeleafContext);
		}
		catch(Exception e1)
		{
			res.setCreatedby("ERROR");
			return res;
		}
		
	    try
	    {
	    	String bcc[] = new String[emails.size()];
	    	int counter = 0;
	    	for(String x : emails)
	    	{
	    		bcc[counter] = x;
	    		counter++;
	    	}
			sendmail( bcc, htmlBody,"Assessment Re-Scheduled");
		}
	    catch(MessagingException | IOException e)
	    {
	    	res.setCreatedby("MAIL_ERROR");
	    	return res;
		}
		//res.setCreatedby("MAIL_AVOID");
		return res;
	}
	
	@PostMapping("/create")
	@ResponseBody
	public ScheduleModel createSchedule(@RequestBody ScheduleModel schedule)
	{
		//System.out.println("schedule:::"+schedule.toString());
		String URL = appGateway.getBaseUrl() + "/api/assessment/createschedule";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ScheduleModel> request = new HttpEntity<ScheduleModel>(schedule,httpHeaders);
		ScheduleModel res = null;
		ResponseEntity<ScheduleModel> response= restTemplate.exchange(URL,HttpMethod.POST,request, ScheduleModel.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			res = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		res.setCreatedby("SUCCESS");
		
		URL = appGateway.getBaseUrl() + "/api/course/getcourse/"+schedule.getCourseid();
		HttpEntity<String> req = new HttpEntity<String>(httpHeaders);
		Course resp = null;
		ResponseEntity<Course> respon= restTemplate.exchange(URL,HttpMethod.GET,req, Course.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			resp = respon.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		List<String> emails = new ArrayList<>();
		
		for(long x : schedule.getBatchids())
		{
			String URL1 = appGateway.getBaseUrl() + "/api/batch/getbatchdetailsbyid/"+x;
			HttpEntity<BatchDetailsModel> request1 = new HttpEntity<BatchDetailsModel>(httpHeaders);
			BatchDetailsModel res1 = null;
			ResponseEntity<BatchDetailsModel> response1= restTemplate.exchange(URL1,HttpMethod.GET,request1, BatchDetailsModel.class);
			if(response1.getStatusCode() == HttpStatus.OK)
			{
				res1 = response1.getBody();
			}
			else
			{
				System.out.println("Request Failed");
				System.out.println(response.getStatusCode());
				res.setCreatedby("ERROR");
				return res;
			}
			
			for(Student y : res1.getStudents())
			{
				emails.add(y.getEmailaddr());
			}
			for(FacultyDetails y : res1.getFaculty())
			{
				emails.add(y.getEmailaddr());
			}
			
		}
		String htmlBody;
		try
		{
			String originalString = schedule.getStartdatetime();
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
            String newstr = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss").format(date);
            String dttm[] = newstr.split(",");
            
            String startdate = dttm[0];
            String starttime = dttm[1];
            
            originalString = schedule.getEnddatetime();
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
            newstr = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss").format(date);
            dttm = newstr.split(",");
            
            String enddate = dttm[0];
            String endtime = dttm[1];
            
            Context thymeleafContext = new Context();
    		thymeleafContext.setVariable("subject", schedule.getAssessmentid());
    		thymeleafContext.setVariable("startdate", startdate);
    		thymeleafContext.setVariable("starttime", starttime);
    		thymeleafContext.setVariable("enddate", enddate);
    		thymeleafContext.setVariable("endtime", endtime);
    		thymeleafContext.setVariable("descr", schedule.getDescr());
    		thymeleafContext.setVariable("textbody", "An assessment has been scheduled.");
    		thymeleafContext.setVariable("crse", resp.getTitle());
    		
    	    htmlBody = thymeleafTemplateEngine.process("mail/assessmentSchedule.html", thymeleafContext);
		}
		catch(Exception e1)
		{
			res.setCreatedby("ERROR");
			return res;
		}
		
	    try
	    {
	    	String bcc[] = new String[emails.size()];
	    	int counter = 0;
	    	for(String x : emails)
	    	{
	    		bcc[counter] = x;
	    		counter++;
	    	}
			sendmail( bcc, htmlBody,"Assessment Scheduled");
		}
	    catch(MessagingException | IOException e)
	    {
	    	res.setCreatedby("MAIL_ERROR");
	    	return res;
		}
		//res.setCreatedby("MAIL_AVOID");
	    
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/examSchedulesByBatches", method = RequestMethod.POST)
	public SchedulesByBatch[] getSchedulesByBatch(HttpServletRequest request)
	{
		String batchPayload = request.getParameter("batches");
		
		SchedulesByBatch[] schedulesByBatches = null;
		String URL = appGateway.getBaseUrl() + "/api/assessment/getexistingschedulebybatchid";
		//String URL = appGateway.getBaseUrl() + "/api/assessment/getexistingschedulebybatchid";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(batchPayload, httpHeaders);

		ResponseEntity<SchedulesByBatch[]> response = restTemplate.exchange(URL, HttpMethod.POST, request1, SchedulesByBatch[].class);
		if(response.getStatusCode() == HttpStatus.OK) {
			schedulesByBatches = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		return schedulesByBatches;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getStudentCount", method = RequestMethod.POST)
	public SingleResponseModel getStudentCount(HttpServletRequest request)
	{
		String batchPayload = request.getParameter("batches");
		
		SingleResponseModel stdntcnt = null;
		String URL = appGateway.getBaseUrl() + "/api/assessment/getStudentCountByBatchId";
		//String URL = "http://10.8.20.35:9123/api/assessment/getStudentCountByBatchId";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(batchPayload, httpHeaders);

		ResponseEntity<SingleResponseModel> response = restTemplate.exchange(URL, HttpMethod.POST, request1, SingleResponseModel.class);
		if(response.getStatusCode() == HttpStatus.OK) {
			stdntcnt = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		return stdntcnt;
	}
	
	private void sendmail( String[] bcc, String htmlBody, String subject) throws AddressException, MessagingException, IOException
	{
		Session session = Session.getInstance(props.getProperties(), new javax.mail.Authenticator(){
		  protected PasswordAuthentication getPasswordAuthentication(){
			 return new PasswordAuthentication(mailerInfo.getMailid(), mailerInfo.getMailpass());
		  }
		});
		
		MimeMessage msg = new MimeMessage(session);
		MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
	    
	    helper.setText(htmlBody,true);
	    helper.setFrom(mailerInfo.getMailid());
	    helper.setTo(bcc);
	    //helper.setTo(InternetAddress.parse(bcc[0]));
		//helper.setTo(InternetAddress.parse("shantanusrivastava.srdt@gmail.com"));
	    //helper.setTo(InternetAddress.parse("snigdhaavaish.srdt@gmail.com"));
	    //helper.setBcc(bcc);
	    helper.setSubject(subject);
	    helper.setSentDate(new Date());
	    //Transport.send(msg);
	}
}