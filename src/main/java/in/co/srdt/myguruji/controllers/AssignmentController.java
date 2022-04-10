package in.co.srdt.myguruji.controllers;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import in.co.srdt.myguruji.model.AssignmentAttachment;
import in.co.srdt.myguruji.model.AssignmentCreateModel;
import in.co.srdt.myguruji.model.AssignmentDetails;
import in.co.srdt.myguruji.model.AssignmentMarksUpdateModel;
import in.co.srdt.myguruji.model.AssignmentPlan;
import in.co.srdt.myguruji.model.AssignmentQuest;
import in.co.srdt.myguruji.model.AssignmentResponseSave;
import in.co.srdt.myguruji.model.AssignmentStudent;
import in.co.srdt.myguruji.model.AssignmentStudentSubmissionStatusList;
import in.co.srdt.myguruji.model.AssignmentUnit;
import in.co.srdt.myguruji.model.BatchDetailsModel;
import in.co.srdt.myguruji.model.QuestionDetailsGET;
import in.co.srdt.myguruji.model.QuestionGravity;
import in.co.srdt.myguruji.model.QuestionType;
import in.co.srdt.myguruji.model.QuestionsGetParamModel;
import in.co.srdt.myguruji.model.ResponseWrapper;
import in.co.srdt.myguruji.model.Student;
import in.co.srdt.myguruji.model.TopicsList;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.CoursePlanDetailsGet;
import in.co.srdt.myguruji.model.coursePlan.CoursePlanUnitDetails;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationContent;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {
	
	@Autowired
	ApplicationContent contentUtil;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;

	@Autowired
	GetEmplId getEmplID;//=new GetEmplId;
	
	//DISABLED AS ON 15-DEC-2020:: DONT REMOVE
	@GetMapping("/search")
	public String search(HttpServletRequest request, Model model, Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
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
		
		return "forms/assignment/createassignment :: createassignmentsearch";
	}
	
	@GetMapping("/create/{crseid}/{planid}/{unitid}")
	public String create(@PathVariable("crseid") String crseid,@PathVariable("planid") String planid,@PathVariable("unitid") String unitid
			,HttpServletRequest request, Model model, Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
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
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now().plusMinutes(5);
		
		model.addAttribute("mindate",dtf.format(now));
		model.addAttribute("courses",Arrays.asList(courses));
		model.addAttribute("createdby", emplid);
		model.addAttribute("crseid", crseid);
		
		url = appGateway.getBaseUrl() + "/api/courseplan/getcourseplandetails/"+planid;
		CoursePlanDetailsGet[] coursePlans = null;

		ResponseEntity<CoursePlanDetailsGet[]> response1= restTemplate.exchange(url,HttpMethod.GET,request1, CoursePlanDetailsGet[].class);
		if(response1.getStatusCode() == HttpStatus.OK) {
			coursePlans = response1.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response1.getStatusCode());
		}
		if(unitid.equals("-"))
		{
			model.addAttribute("units",coursePlans[0].getUnits());
		}
		else
		{
			model.addAttribute("units",unitid);
			for(CoursePlanDetailsGet x:coursePlans)
			{
				for(CoursePlanUnitDetails y:x.getUnits()) 
				{
					if(y.getUnitid()==Long.parseLong(unitid))
					{
						model.addAttribute("units",y);
						break;
					}					
				}
			}
		}
		return "forms/assignment/createassignment :: createassignment";
	}
	
	@PostMapping("/createassignment")
	@ResponseBody
	public SingleResponseModel createassignment(@RequestBody AssignmentCreateModel assi
			, HttpServletRequest request)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<AssignmentCreateModel> request1 = new HttpEntity<AssignmentCreateModel>(assi,httpHeaders);
		String url = appGateway.getBaseUrl() + "/api/assignment/createassignment";
		SingleResponseModel resp=null;
		//String url = "http://10.8.20.35:9120/api/assignment/createassignment";
		ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url ,HttpMethod.POST,request1, SingleResponseModel.class);

		if(response.getStatusCode() == HttpStatus.OK){
			resp = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return resp;
	}
	
	@GetMapping("/getassignments/{level}/{courseid}/{unitid}/{courseplanid}")
	@ResponseBody
	public AssignmentDetails[] getassignments(@PathVariable("level") String level, @PathVariable("courseid") String crseid
			, @PathVariable("unitid") String unitid, @PathVariable("courseplanid") String courseplanid ,HttpServletRequest request
			, Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String url="";
		if(request.isUserInRole("ROLE_Faculty"))
		{
			url = appGateway.getBaseUrl() + "/api/assignment/getAssignmentDetail/"+level+"/"+emplid+"/"+courseplanid+"/"+unitid;
			//url= "http://localhost:9120/api/assignment/getAssignmentDetail/"+level+"/"+login.getEmplid()+"/"+crseid+"/"+unitid;
		}
		else//(role.equals("Student"))
		{
			long stdntid=0;
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

			HttpEntity<String> restreq1 = new HttpEntity<String>(studentsrch,httpHeaders);
			ResponseEntity<Student[]> stu = restTemplate.exchange(userDataUrl, HttpMethod.POST, restreq1, Student[].class);
			
			Student[] student = stu.getBody();
			stdntid=student[0].getStudentid();
			if(level.equals("C"))
			{
				url = appGateway.getBaseUrl() + "/api/assignment/getStudentAssignment/"+courseplanid+"/"+stdntid;
				//url= "http://localhost:9120/api/assignment/getStudentAssignment/"+courseplanid+"/"+stdntid;
			}
			else
			{				
				url = appGateway.getBaseUrl() + "/api/assignment/getStudentAssignmentforUnit/"+courseplanid+"/"+stdntid+"/"+unitid;
				//url= "http://localhost:9120/api/assignment/getStudentAssignmentforUnit/"+courseplanid+"/"+stdntid+"/"+unitid;
			}
		}

		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		AssignmentDetails[] assignments = null;

		ResponseEntity<AssignmentDetails[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, AssignmentDetails[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			assignments = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		return assignments;
	}
	
	@GetMapping("/questionPaper")
	public String questionPaper(HttpServletRequest request, Model model, Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String courseid = request.getParameter("courseid");
		String level = request.getParameter("level");
		String unitid = request.getParameter("unitid");
				
		TopicsList[] topics = null;
		String urlTopics = appGateway.getBaseUrl() + "/api/topic/gettopicsbycourseid/"+courseid;
		
		HttpEntity<String> request4 = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<TopicsList[]> response4= restTemplate.exchange(urlTopics,HttpMethod.GET,request4, TopicsList[].class);
		if(response4.getStatusCode() == HttpStatus.OK) {
			topics = response4.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response4.getStatusCode());
		}
		List<TopicsList> topicsFinal = new ArrayList<>();
		String urlquesType = appGateway.getBaseUrl() + "/api/question/getquestiontype";
		QuestionType[] quesType = null;
		HttpEntity<String> request2 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<QuestionType[]> response2= restTemplate.exchange(urlquesType,HttpMethod.GET,request2, QuestionType[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
			quesType = response2.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}
		
		String urlDifficulty = appGateway.getBaseUrl() + "/api/question/getdifficultylevel";
		QuestionGravity[] difficulty = null;
		HttpEntity<String> request3 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<QuestionGravity[]> response3= restTemplate.exchange(urlDifficulty,HttpMethod.GET,request3, QuestionGravity[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
			difficulty = response3.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}
		
		String assignmentid = request.getParameter("assignmentid");
		String title = request.getParameter("title");
		String duedate = request.getParameter("duedate");

		AssignmentCreateModel assi = null;
		String getbyassignmentidurl=appGateway.getBaseUrl() + "/api/assignment/getAssignmentdetailbyId/"+assignmentid;
		//String getbyassignmentidurl="http://localhost:9120/api/assignment/getAssignmentdetailbyId/"+assignmentid;
		
		HttpEntity<String> req = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<AssignmentCreateModel> res= restTemplate.exchange(getbyassignmentidurl,HttpMethod.GET,req, AssignmentCreateModel.class);
		if(response4.getStatusCode() == HttpStatus.OK) {
			assi = res.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response4.getStatusCode());
		}
		List<String> alltopics=new ArrayList<>();
		for(AssignmentPlan x : assi.getAssignplan())
		{
			for(AssignmentUnit y : x.getUnitlist())
			{
				for(String z : y.getTopicsids().split(","))
					alltopics.add(z);
			}
		}
		for(TopicsList x:topics)
		{
			if(alltopics.contains(""+x.getId()))
				topicsFinal.add(x);
		}
		model.addAttribute("courseid", courseid);
		model.addAttribute("topics", topicsFinal);
		model.addAttribute("questiontype", quesType);
		model.addAttribute("difficulty", difficulty);
		model.addAttribute("createdby", emplid);
		model.addAttribute("assignmentid", assignmentid);
		model.addAttribute("title", title);
		model.addAttribute("duedate", duedate.substring(0, 16));
		model.addAttribute("level", level);
		model.addAttribute("unitid", unitid);
		return "forms/assignment/assignmentQuesPaper :: assignmentQuesPaper";
	}
	
	@GetMapping("/viewQuestionPaper")
	public String viewQuestionPaper(HttpServletRequest request, Model model)
	{
		String assignmentid = request.getParameter("assignmentid");
		AssignmentCreateModel assi = null;
		String courseid = request.getParameter("courseid");
		String url = appGateway.getBaseUrl() + "/api/assignment/getAssignmentdetailbyId/"+assignmentid;
		//String url="http://localhost:9120/api/assignment/getAssignmentdetailbyId/"+assignmentid;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request4 = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<AssignmentCreateModel> response4= restTemplate.exchange(url,HttpMethod.GET,request4, AssignmentCreateModel.class);
		if(response4.getStatusCode() == HttpStatus.OK) {
			assi = response4.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response4.getStatusCode());
		}
		
		String title = request.getParameter("title");
		String duedate = request.getParameter("duedate");	
		model.addAttribute("assignmentid", assignmentid);
		model.addAttribute("title", title);
		model.addAttribute("duedate", duedate);		
		model.addAttribute("courseid", courseid);
		model.addAttribute("level", assi.getLevel());
		model.addAttribute("unitid", assi.getAssignplan().size()>0?"-" : assi.getAssignplan().get(0).getUnitlist().get(0).getUnitid());
		model.addAttribute("questions", assi.getQuestionlist());
		return "forms/assignment/assignmentQuesPaper :: viewAssignmentQuesPaper";
	}
	
	@PostMapping("/postquestionpaper")
	@ResponseBody
	public SingleResponseModel postquestionpaper(@RequestBody AssignmentQuest[] ques)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AssignmentQuest[]> request1 = new HttpEntity<AssignmentQuest[]>(ques,httpHeaders);
		String url = appGateway.getBaseUrl() + "/api/assignment/createAssignmentQuestion";
		SingleResponseModel resp=null;
		//String url = "http://10.8.20.35:9120/api/assignment/createAssignmentQuestion";
		ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url ,HttpMethod.POST,request1, SingleResponseModel.class);

		if(response.getStatusCode() == HttpStatus.OK){
			resp = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		return resp;
	}
	
	@GetMapping("/assignStudents/{assignmentid}/{courseid}/{batchid}/{courseplanid}")
	public String assignStudents(@PathVariable("assignmentid") String assignmentid,@PathVariable("batchid") String batchid
			,@PathVariable("courseid") String courseid,@PathVariable("courseplanid") String courseplanid
			, Authentication authentication, HttpServletRequest request, Model model)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		BatchDetailsModel batch = null;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/batch/getbatchdetailsbyid/" + batchid;

        ResponseEntity<BatchDetailsModel> response= restTemplate.exchange(url ,HttpMethod.GET,request1, BatchDetailsModel.class);

        if(response.getStatusCode() == HttpStatus.OK){
        	batch = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        
		model.addAttribute("createdby",emplid);
		model.addAttribute("assignmentid",assignmentid);
		model.addAttribute("batchid",batchid);
		model.addAttribute("courseid",courseid);
		model.addAttribute("courseplanid",courseplanid);
		model.addAttribute("studentslist",batch);
		
		return "forms/assignment/assignStudents :: assignStudents";
	}
	
	@GetMapping("/getStudentList/{batchid}")
	@ResponseBody
	public List<Student> assignStudents(@PathVariable("batchid") String batchid, HttpServletRequest request, Model model)
	{
		BatchDetailsModel batch = null;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/batch/getbatchdetailsbyid/" + batchid;

        ResponseEntity<BatchDetailsModel> response= restTemplate.exchange(url ,HttpMethod.GET,request1, BatchDetailsModel.class);

        if(response.getStatusCode() == HttpStatus.OK){
        	batch = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        
		return batch.getStudents();
	}
	
	@PostMapping("/saveStudentList")
	@ResponseBody
	public SingleResponseModel saveStudentList(@RequestBody AssignmentStudent[] stdnts, HttpServletRequest request)
	{
		SingleResponseModel resp=null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AssignmentStudent[]> request1 = new HttpEntity<AssignmentStudent[]>(stdnts,httpHeaders);

        String url = appGateway.getBaseUrl() + "/api/assignment/addStudentAssignmentMapp";
        //String url="http://10.8.20.35:9120/api/assignment/addStudentAssignmentMapp";
        //System.out.println(url);
        ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url ,HttpMethod.POST,request1, SingleResponseModel.class);

        if(response.getStatusCode() == HttpStatus.OK){
        	resp = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        
		return resp;
	}
	
	@PostMapping("/publishAssignment/{assignmentid}")
	@ResponseBody
	public SingleResponseModel publishAssignment(@PathVariable("assignmentid") String assignmentid, HttpServletRequest request)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		SingleResponseModel resp=null;
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        //String url = appGateway.getBaseUrl() + "/api/assignment/publishAssignment/";
        String url = appGateway.getBaseUrl() + "/api/assignment/publishAssignment/"+assignmentid;
        ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url ,HttpMethod.POST,request1, SingleResponseModel.class);

        if(response.getStatusCode() == HttpStatus.OK){
        	resp = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        
		return resp;
	}
	
	@GetMapping("/getAssignmentResult")
	public String getAssignmentResult(HttpServletRequest request,Model model)
	{
		String[] courseTitle=request.getParameter("crsetitle").split("_");
		String assignmentTitle=request.getParameter("assititle");
		String assignmentId=request.getParameter("assignmentId");
		String batchCode=request.getParameter("batchcode");
		String batchid=request.getParameter("batchid");
		String crseid=request.getParameter("crseid");
		
		model.addAttribute("courseTitle",courseTitle[1]);
		model.addAttribute("assignmentId",assignmentId);
		model.addAttribute("assignmentTitle",assignmentTitle);
		model.addAttribute("batchCode",batchCode);
		model.addAttribute("batchid",batchid);
		model.addAttribute("crseid",crseid);
		return "forms/assignment/assignmentResult :: assignmentResult";
	}
	
	@GetMapping("/getStudentSubmissionList/{assignmentid}/{batchid}/{crseid}")
	@ResponseBody
	public AssignmentStudentSubmissionStatusList[] getStudentSubmissionList(@PathVariable("assignmentid") String assignmentid
				,@PathVariable("batchid") String batchid, @PathVariable("crseid") String crseid
				,HttpServletRequest request)
	{
		AssignmentStudentSubmissionStatusList[] resp=null;
		String url=appGateway.getBaseUrl()+"/api/assignment/getStudentSubmissionStatusList/"+crseid+"/"+assignmentid+"/"+batchid;
		//String url="http://10.8.20.35:9120/api/assignment/getStudentSubmissionStatusList/"+crseid+"/"+assignmentid+"/"+batchid;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> req=new HttpEntity<String>(httpHeaders);
		ResponseEntity<AssignmentStudentSubmissionStatusList[]> res=restTemplate.exchange(url,HttpMethod.GET,req,AssignmentStudentSubmissionStatusList[].class);
		
		if(res.getStatusCode()== HttpStatus.OK)
		{
			resp=res.getBody();
		}
		else
		{
			System.out.println("error:: "+res.getStatusCode());
		}
		
		return resp;
	}
	
	@GetMapping("/getStudentAnswerSheet/{assignmentid}/{crseid}/{stdntid}/{evaluatestatus}")
	public String getStudentAnswerSheet(HttpServletRequest req,Model model
			,@PathVariable("assignmentid") String assignmentid,@PathVariable("crseid") String crseid
			,@PathVariable("stdntid") String stdntid,@PathVariable("evaluatestatus") String evaluatestatus)
	{
		String studentemplid="";
		String studentsrch = "{\r\n" + 
				"	\"studentid\" : "+stdntid+",\r\n" + 
				"    \"emplid\": \"\",\r\n" + 
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
		HttpEntity<String> restreq1 = new HttpEntity<String>(studentsrch,httpHeaders);
		ResponseEntity<Student[]> stu = restTemplate.exchange(userDataUrl, HttpMethod.POST, restreq1, Student[].class);
		
		Student[] student = stu.getBody();
		studentemplid=student[0].getEmplid();
		//String url = "http://localhost:9120/api/assignment/getAssignmentdetailbyStudentId/"+assignmentid+"/"+login.getEmplid();
		String url = appGateway.getBaseUrl() + "/api/assignment/getAssignmentdetailbyStudentId/"+assignmentid+"/"+studentemplid;
		
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<AssignmentDetails> response= restTemplate.exchange(url ,HttpMethod.GET,request, AssignmentDetails.class);
		AssignmentDetails resp = new AssignmentDetails();
		
		if(response.getStatusCode() == HttpStatus.OK){
        	resp = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
		
		model.addAttribute("assignment",resp);		
		model.addAttribute("assignmentid", assignmentid);
		model.addAttribute("crseid", crseid);
		model.addAttribute("stdntid", stdntid);
		model.addAttribute("evaluatestatus", evaluatestatus);
		//model.addAttribute("stdntname", student[0].getFullname()+"("+student[0].getCampusid()+")");
		model.addAttribute("stdntname", student[0].getFullname()+" ("+student[0].getCampusid()+")");
		
		return "forms/assignment/assignmentResultStudent :: assignmentResultStudent";
	}
	
//	@GetMapping("/getStudentAnswerSheet/{assignmentid}/{crseid}/{stdntid}")
//	public String getStudentAnswerSheet(HttpServletRequest request,HttpSession session,Model model
//			,@PathVariable("assignmentid") String assignmentid,@PathVariable("crseid") String crseid
//			,@PathVariable("stdntid") String stdntid)
//	{
//		model.addAttribute("assignmentid", assignmentid);
//		model.addAttribute("crseid", crseid);
//		model.addAttribute("stdntid", stdntid);
//		return "forms/assignment/assignmentResultStudent :: assignmentResultStudent";
//	}
	
	@PostMapping("/getquestionsbyparams/")
	@ResponseBody
	public QuestionDetailsGET[] getQuestions(@RequestBody QuestionsGetParamModel params)
	{
		QuestionDetailsGET[] questions = null;
		String url = appGateway.getBaseUrl() + "/api/question/search";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<QuestionsGetParamModel> request4 = new HttpEntity<QuestionsGetParamModel>(params, httpHeaders);
		
		ResponseEntity<QuestionDetailsGET[]> response4= restTemplate.exchange(url,HttpMethod.POST,request4, QuestionDetailsGET[].class);
		if(response4.getStatusCode() == HttpStatus.OK) {
			questions = response4.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response4.getStatusCode());
		}
		
		return questions;
	}
	
	@PostMapping("/updateStudentMarks")
	@ResponseBody
	public String updateStudentMarks(@RequestBody AssignmentMarksUpdateModel[] marks,HttpServletRequest request)
	{
		String url= appGateway.getBaseUrl() + "/api/assignment/updateStudentMarks";
		//String url="http://10.8.20.35:9120/api/assignment/updateStudentMarks";
		SingleResponseModel resp=null;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AssignmentMarksUpdateModel[]> restreq1 = new HttpEntity<AssignmentMarksUpdateModel[]>(marks,httpHeaders);
		ResponseEntity<SingleResponseModel> res = restTemplate.exchange(url, HttpMethod.POST, restreq1, SingleResponseModel.class);
		if(res.getStatusCode() == HttpStatus.OK) {
			resp = res.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(res.getStatusCode());
		}
		return resp.getMessage();
	}
	
	/************************* SHANTANU ********************************/
	
//	@GetMapping("/getassignmentdetailsbyid/{assignmentid}")
//	public String getAssignmentDetailsById(@PathVariable("assignmentid") String assignmentid, Model model, HttpSession session)
//	{
//		login = (Login) session.getAttribute("login");
//		
//		String url = "http://localhost:9120/api/assignment/getAssignmentdetailbyStudentId/"+assignmentid+"/"+login.getEmplid();
//		HttpEntity<String> request = new HttpEntity<String>(headers);
//		ResponseEntity<AssignmentDetails> response= restTemplate.exchange(url ,HttpMethod.GET,request, AssignmentDetails.class);
//		AssignmentDetails resp = new AssignmentDetails();
//		
//		if(response.getStatusCode() == HttpStatus.OK){
//        	resp = response.getBody();
//        } else {
//            System.out.println("Request Failed");
//            System.out.println(response.getStatusCode());
//        }
//		
//		model.addAttribute("assignment",resp);
//		
//		return "forms/assignment/assignmentAttempt :: assignmentAttempt";
//	}
	
//	@PostMapping("/saveresponses")
//	@ResponseBody
//	public String saveassignmentresponses(@RequestBody AssignmentResponseFetch[] res)
//	{
//		String url = "http://localhost:9120/api/assignment/saveassignmentresponse ";
//		
//		List<AssignmentResponseSave> payLoad = new ArrayList<>();
//		
//		for(AssignmentResponseFetch x : res)
//		{
//			AssignmentResponseSave data = new AssignmentResponseSave() {{
//				setAssignmentResponseID(0);
//				setAssignmentid(x.getAssignmentid());
//				setStudentid(1);
//				setQuestionid(x.getQuestionid());
//				setHasAttachment(x.getHasAttachment());
//				setSubjectiveResponse(x.getSubjectiveresponse());
//				setObjectiveResponse(x.getObjectiveresponse());
//				setResponsestatus(x.getType());
//			}};
//			System.out.println(data.toString());
//			
//			payLoad.add(data);
//		}
//		
//		return "success";
//	}
	
	@GetMapping("/getassignmentdetailsbyid/{assignmentid}")
	public String getAssignmentDetailsById(@PathVariable("assignmentid") String assignmentid, Model model, Authentication authentication)
	{
		String emplid =getEmplID.getLogedinUserEmplid(authentication.getName());
				//String url = "http://localhost:9120/api/assignment/getAssignmentdetailbyStudentId/"+assignmentid+"/"+login.getEmplid();
		String url = appGateway.getBaseUrl() + "/api/assignment/getAssignmentdetailbyStudentId/"
				+assignmentid+"/"+emplid;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<AssignmentDetails> response= restTemplate.exchange(url ,HttpMethod.GET,request, AssignmentDetails.class);
		AssignmentDetails resp = new AssignmentDetails();
		
		if(response.getStatusCode() == HttpStatus.OK){
        	resp = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
		
		model.addAttribute("assignment",resp);
		
		return "forms/assignment/assignmentAttempt :: assignmentAttempt";
	}
	
	@GetMapping("/viewAssignedStudents/{assignmentid}")
	public String viewAssignedStudents(@PathVariable("assignmentid") String assignmentid, Model model)
	{
		String url = appGateway.getBaseUrl()+"/api/assignment/getAssignmentdetailsstudentstatus/"+assignmentid;
		//String url = "http://localhost:9120/api/assignment/getAssignmentdetailsstudentstatus/"+assignmentid;
		
		AssignmentDetails asgn = null;

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> restreq1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<AssignmentDetails> res = restTemplate.exchange(url, HttpMethod.GET, restreq1, AssignmentDetails.class);
		asgn = res.getBody();
		
		model.addAttribute("asgn",asgn);
		return "forms/assignment/viewAssignedStudents :: viewAssignedStudents";
	}
	
	@RequestMapping(value ="/saveresponsesTEST")
	@ResponseBody
	public SingleResponseModel saveassignmentresponsesTEST(@RequestParam("files")MultipartFile[] files, ResponseWrapper res, Authentication authentication) throws MalformedURLException, IOException
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		Student[] student = null;
		try
		{
			String studentsrch = "{" + 
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
			HttpEntity<String> restreq1 = new HttpEntity<String>(studentsrch,httpHeaders);
			ResponseEntity<Student[]> stu = restTemplate.exchange(userDataUrl, HttpMethod.POST, restreq1, Student[].class);
			student = stu.getBody();
			
			if(student.length == 0)
			{
				return new SingleResponseModel("STUDENT_GET_ERROR");
			}
		}
		catch(Exception e)
		{
			return new SingleResponseModel("STUDENT_GET_ERROR");
		}
		long studentid = student[0].getStudentid();
		
		/*************SUBMISSION STATUS GET*******************/
		String statusUrl = appGateway.getBaseUrl()+"/api/assignment/getStudentAssignmentStatus/"+res.getAssignmentid()[0]+"/"+studentid;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> restreq2 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<SingleResponseModel> response2 = restTemplate.exchange(statusUrl, HttpMethod.GET, restreq2, SingleResponseModel.class);
		SingleResponseModel status = response2.getBody();
		if(status.getMessage() != null)
		if(status.getMessage().equals("S"))
		{
			return new SingleResponseModel("S");
		}
		/*************SUBMISSION STATUS GET*******************/
		
		List<AssignmentResponseSave> responses = new ArrayList<AssignmentResponseSave>();
		int fileCounter = 0;
		
		for(int i=0; i<res.getAssignmentquestionid().length; i++)
		{
			AssignmentResponseSave resp = new AssignmentResponseSave();
			
			resp.setStudentid(studentid);
			resp.setAssignmentid(Long.parseLong(res.getAssignmentid()[i]));
			resp.setAssignmentResponseID(Long.parseLong(res.getAssignmentresponseid()[i]));
			resp.setHasAttachment(res.getHasAttachment()[i]);
			resp.setMarksObtained("0");
			try {
				resp.setObjectiveResponse(res.getObjectiveresponse()[i]);
			}catch(Exception e) {
				resp.setObjectiveResponse("");
			}
			try {
				resp.setSubjectiveResponse(res.getSubjectiveresponse()[i]);
			}catch(Exception e) {
				resp.setSubjectiveResponse("");
			}
			resp.setQuestionid(Long.parseLong(res.getQuestionid()[i]));
			resp.setResponsestatus(res.getType()[i]);
			
			String filePath = new File("").getAbsolutePath()+File.separator+contentUtil.getBaseLocation()+File.separator+contentUtil.getAssignmentContentLocation()+File.separator+"course_"+res.getCourseid()[i]+File.separator+"assignment_"+res.getAssignmentid()[i]+File.separator+"stdnt_"
					+authentication.getName()+File.separator+"question_"+res.getAssignmentquestionid()[i];
			String storePath = "course_"+res.getCourseid()[i]+File.separator+"assignment_"+res.getAssignmentid()[i]+File.separator+"stdnt_"
						+authentication.getName()+File.separator+"question_"+res.getAssignmentquestionid()[i];
			
			int totalFiles = Integer.parseInt(res.getFilesTotal()[i]);
			
			List<AssignmentAttachment> attachments = new ArrayList<AssignmentAttachment>();
			
			for(int j=0; j<totalFiles ; j++)
			{
				AssignmentAttachment attach = new AssignmentAttachment();
				MultipartFile file = files[fileCounter];
				
				if(file.isEmpty())
				{
					continue;
				}
				
				//Evil hack to get the extension
				String extension = Optional.ofNullable(file.getOriginalFilename())
							      .filter(f -> f.contains("."))
							      .map(f -> f.substring(file.getOriginalFilename().lastIndexOf(".") + 1)).get();
				
				 if(extension.equals("exe"))
				 {
					 return new SingleResponseModel("FILE_FORMAT_WRONG");
				 }
				 
				//String extension =  FilenameUtils.getExtension( file.getOriginalFilename() );
				String generatedFileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss"+"'"
						+authentication.getName()+"."+extension+"'").format(new Date());
				
				/*************************CREATING DIRECTORY*****************************/
				File dir = new File(filePath);
				if( !dir.exists() )
				{
					dir.mkdirs();
				}
				/*************************CREATING DIRECTORY*****************************/
				
				/*************************WRITING FILE***********************************/
				File target = new File(filePath+File.separator+generatedFileName);
				
				@SuppressWarnings("deprecation")
				URLConnection connection = target.toURL().openConnection();
			    String mimeType = connection.getContentType();
				
			    if(!mimeType.contains("image/") ) 
			    {
			    	if(mimeType.contains("video/") || mimeType.contains("audio/"))
			    	{
			    		return new SingleResponseModel("FILE_FORMAT_WRONG");
			    	}
			    }
			    
				int readByteCount = 0;
				byte[] buffer = new byte[4096];
				
				try
				{
					BufferedInputStream in= new BufferedInputStream(file.getInputStream());
					FileOutputStream out = new FileOutputStream(target);
				    while( (readByteCount = in.read(buffer)) != -1)
				    {
				        out.write(buffer, 0, readByteCount);
				    }
				    out.flush();
					out.close();
				}
				catch(Exception e)
				{
					return new SingleResponseModel("FILE_WRITE_ERROR");
				}
				/*************************WRITING FILE***********************************/
				
				/*************************WRITING RECORD*********************************/
				attach.setAssignmentAttachmentID(0);
				attach.setAssignmentid(Long.parseLong(res.getAssignmentid()[i]));
				attach.setAssignmentResponseID(Long.parseLong(res.getAssignmentresponseid()[i]));
				attach.setQuestionid(Long.parseLong(res.getQuestionid()[i]));
				attach.setStudentid(studentid);
				attach.setGeneratedFileName(generatedFileName);
				attach.setOriginalFileName(file.getOriginalFilename());
				attach.setFileExtension(extension);
				attach.setFilePath(storePath+File.separator+generatedFileName);
				
				attachments.add(attach);
				/*************************WRITING RECORD*********************************/
				
				fileCounter++;
			}
			
			resp.setAttachments(attachments);
			responses.add(resp);
		}
		
		try
		{
			String url = appGateway.getBaseUrl()+"/api/assignment/saveassignmentresponse";
			//String url = "http://10.8.20.35:9123/api/assignment/saveassignmentresponse";
			
			HttpEntity<List<AssignmentResponseSave>> request = new HttpEntity<List<AssignmentResponseSave>>(responses,httpHeaders);
			ResponseEntity<SingleResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, request, SingleResponseModel.class);
			if(response.getStatusCode() == HttpStatus.OK)
			{
				if(res.getType()[0].equals("S"))
					return new SingleResponseModel("SUBMITTED");
				else
					return new SingleResponseModel("SAVED");
			}
			else
			{
				return new SingleResponseModel("UNKNOWN_ERROR");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new SingleResponseModel("UNKNOWN_ERROR");
		}
	}
}
