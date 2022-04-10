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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.AssessmentByCourseIdGET;
import in.co.srdt.myguruji.model.CommonModelReport;
import in.co.srdt.myguruji.model.CourseDetails;
import in.co.srdt.myguruji.model.DoubleResponseModel;
import in.co.srdt.myguruji.model.GetQuestionPaperDetails;
import in.co.srdt.myguruji.model.GetSectionDetails;
import in.co.srdt.myguruji.model.QuestionDetailsGET;
import in.co.srdt.myguruji.model.QuestionGravity;
import in.co.srdt.myguruji.model.QuestionPaperModel;
import in.co.srdt.myguruji.model.QuestionType;
import in.co.srdt.myguruji.model.QuestionsGetParamModel;
import in.co.srdt.myguruji.model.TemplateDetailsModel;
import in.co.srdt.myguruji.model.TemplateModel;
import in.co.srdt.myguruji.model.TopicsList;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/managequestionpaper")
public class ManageQuestionPaperController
{
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private ApplicationGateway appGateway;
	
	@Autowired
	GetEmplId getEmplID;//=new GetEmplId;
	
	@RequestMapping("/questionpapersearch")
	public String templateSearch(Authentication authentication, HttpServletRequest request, Model model)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		//String role = getRoles.getRoleByRequest(request);
		/***********GET ROLES**********/
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		if(request.isUserInRole("ROLE_Coe"))
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
				crses.add(new CourseModel() {{
					setId(""+x.getCourseid());
					setCode(x.getCoursecode());
					setTitle(x.getCoursetitle());
					setDescr(x.getCoursedescr());
				}});
			}
			
			model.addAttribute("courses",crses);
			model.addAttribute("user_role_qpaper","coe");
			model.addAttribute("role","Coe");
			
			return "forms/questionPaper/manageQuestionPaper :: manageQuestionPaper";
		}
		else if(request.isUserInRole("ROLE_Iqac"))
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
				crses.add(new CourseModel() {{
					setId(""+x.getCourseid());
					setCode(x.getCoursecode());
					setTitle(x.getCoursetitle());
					setDescr(x.getCoursedescr());
				}});
			}
			
			model.addAttribute("courses",crses);
			model.addAttribute("user_role_qpaper","iqac");
			model.addAttribute("role","Iqac");
			
			//return "forms/reports/manageQuestionPaperIqac :: manageQuestionPaperIqac";
			return "forms/questionPaper/manageQuestionPaperiqac :: manageQuestionPaperiqac";
		}
		else
		{
			HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
			String url = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
			CourseModel[] courses = null;
	
			ResponseEntity<CourseModel[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CourseModel[].class);
	
			if(response.getStatusCode() == HttpStatus.OK) 
			{
				courses = response.getBody();
			} else 
			{
				System.out.println("Request Failed");
				System.out.println(response.getStatusCode());
			}
			
			model.addAttribute("courses",Arrays.asList(courses));
			model.addAttribute("user_role_qpaper","Faculty");
			model.addAttribute("role","Faculty");
			
			return "forms/questionPaper/manageQuestionPaper :: manageQuestionPaper";
		}
	}
	
	@RequestMapping("/createquestionpaper/{courseid}/{assessmentid}/{title}")
	public String createQuestionPaper(@PathVariable("courseid") long courseid, @PathVariable("assessmentid") long assessmentid, @PathVariable("title") String title
			, HttpServletRequest request, Model model,Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		title = title.replace('_', ' ');
		title = title.replace("FWD_SLSH", "/");
		
		String payLoad = "{\r\n" +
				"	\"id\":\""+courseid+"\",\r\n" +
				"	\"code\":\"\",\r\n" +
				"	\"title\":\"\",\r\n" +
				"	\"emplid\":\"\"\r\n" +
				"}";
		
		/********************GET COURSES**************************/
		String urlCourse = appGateway.getBaseUrl() + "/api/course/search";
		CourseDetails[] course = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(payLoad,httpHeaders);
		ResponseEntity<CourseDetails[]> response1= restTemplate.exchange(urlCourse,HttpMethod.POST,request1, CourseDetails[].class);
		if(response1.getStatusCode() == HttpStatus.OK) {
			course = response1.getBody();
			//System.out.println(assessments[0].getAssessmendescr());
		} else {
			System.out.println("Request Failed");
			System.out.println(response1.getStatusCode());
		}
		
		/*********************GET QUESTION TYPES*************************/
		String urlquesType = appGateway.getBaseUrl() + "/api/question/getquestiontype";
		QuestionType[] quesType = null;
		HttpEntity<String> request2 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<QuestionType[]> response2= restTemplate.exchange(urlquesType,HttpMethod.GET,request2, QuestionType[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
			quesType = response2.getBody();
			//System.out.println(assessments[0].getAssessmendescr());
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}
		
		/*********************GET QUESTION DIFFICULTY*************************/
		String urlDifficulty = appGateway.getBaseUrl() + "/api/question/getdifficultylevel";
		QuestionGravity[] difficulty = null;
		HttpEntity<String> request3 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<QuestionGravity[]> response3= restTemplate.exchange(urlDifficulty,HttpMethod.GET,request3, QuestionGravity[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
			difficulty = response3.getBody();
			//System.out.println(assessments[0].getAssessmendescr());
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}
		
		/**********************GET TOPICS BY COURSE ID******************/
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
		
		model.addAttribute("course", course[0]);
		model.addAttribute("topics", topics);
		model.addAttribute("questiontype", quesType);
		model.addAttribute("difficulty", difficulty);
		model.addAttribute("assessmentid", assessmentid);
		model.addAttribute("assessmenttitle", title);
		model.addAttribute("createdby", emplid);
		
		return "forms/questionPaper/createQuestionPaper :: createQuestionPaper";
	}
	
//	@RequestMapping("/gettemplates")
//	@ResponseBody
//	public TemplateModel[] getTemplates(HttpServletRequest request)
//	{
//		login = (Login) request.getSession().getAttribute("login");
//		String emplid = login.getEmplid();
//		
//		String urlTemplates = appGateway.getBaseUrl() + "/api/assessment/gettemplatebyemplid/"+emplid;
//		TemplateModel[] templates = null;
//		HttpEntity<String> request2 = new HttpEntity<String>(headers);
//		ResponseEntity<TemplateModel[]> response2= restTemplate.exchange(urlTemplates,HttpMethod.GET,request2, TemplateModel[].class);
//		if(response2.getStatusCode() == HttpStatus.OK) {
//			templates = response2.getBody();
//			//System.out.println(assessments[0].getAssessmendescr());
//		} else {
//			System.out.println("Request Failed");
//			System.out.println(response2.getStatusCode());
//		}
//		
//		return templates;
//	}
	
	@RequestMapping("/gettemplates")
	@ResponseBody
	public List<TemplateModel> getTemplates(HttpServletRequest request,Authentication authentication)
	{
		List<TemplateModel> allTemplates = new ArrayList<>();
		
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		/**********GET FACULTY TEMPLATES, START*********/
		String urlTemplates = appGateway.getBaseUrl() + "/api/assessment/gettemplatebyemplid/"+emplid;
		TemplateModel[] templates = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request2 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<TemplateModel[]> response2= restTemplate.exchange(urlTemplates,HttpMethod.GET,request2, TemplateModel[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
			templates = response2.getBody();
			//System.out.println(assessments[0].getAssessmendescr());
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}
		/**********GET FACULTY TEMPLATES, END*********/
		/**********GET PUBLIC TEMPLATES, START********/
		String urlTemplates2 = appGateway.getBaseUrl() + "/api/assessment/gettemplatebyemplid/myguruji";
		TemplateModel[] templates2 = null;
		HttpEntity<String> request3 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<TemplateModel[]> response3= restTemplate.exchange(urlTemplates2,HttpMethod.GET,request3, TemplateModel[].class);
		if(response3.getStatusCode() == HttpStatus.OK) {
			templates2 = response3.getBody();
			//System.out.println(assessments[0].getAssessmendescr());
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}
		/**********GET PUBLIC TEMPLATES, END**********/
		
		for(TemplateModel x : templates)
		{
			//System.out.println(x.getDescr());
			allTemplates.add(x);
		}
		for(TemplateModel x : templates2)
		{
			//System.out.println("---"+x.getDescr());
			allTemplates.add(x);
		}
		
		return allTemplates;
	}
	
	@RequestMapping("/gettemplatedetails/{id}")
	@ResponseBody
	public TemplateDetailsModel getTemplateDetails(@PathVariable("id") String id,HttpServletRequest request)
	{
		String urlTemplates = appGateway.getBaseUrl() + "/api/assessment/gettemplatedetailsbyid/"+id;
		TemplateDetailsModel templates = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request2 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<TemplateDetailsModel> response2= restTemplate.exchange(urlTemplates,HttpMethod.GET,request2, TemplateDetailsModel.class);
		if(response2.getStatusCode() == HttpStatus.OK) {
			templates = response2.getBody();
			//System.out.println(assessments[0].getAssessmendescr());
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}
		return templates;
	}
	
	@RequestMapping("/getallassessmentbycourseid/{id}")
	@ResponseBody
	public AssessmentByCourseIdGET[] getAllAssessmentByCrseId(@PathVariable("id") String id
			, HttpServletRequest request1,Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		AssessmentByCourseIdGET[] assessments = null;
		
		String url = appGateway.getBaseUrl() + "/api/assessment/getassessmentbycourseid/"+id+"/"+emplid;
		//String url = "http://10.8.20.35:9120/api/assessment/getassessmentbycourseid/"+id+"/"+emplid;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<AssessmentByCourseIdGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, AssessmentByCourseIdGET[].class);
		if(response.getStatusCode() == HttpStatus.OK) {
			assessments = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		/********TEMPORARY CODE TO FILTER ASSESSMENTS WITH NO TEMPLATES ONLY********/
		AssessmentByCourseIdGET[] assessmentFiltered = null;
		int len = 0;
		for(AssessmentByCourseIdGET x : assessments){
			if(x.getTemplateid() == 0)
			{
				len++;
			}
		}
		assessmentFiltered = new AssessmentByCourseIdGET[len];
		
		int ind = 0;
		for(AssessmentByCourseIdGET x : assessments){
			if(x.getTemplateid() == 0)
			{
				assessmentFiltered[ind] = x;
				ind++;
			}
		}
		return assessmentFiltered;
		/********TEMPORARY CODE TO FILTER ASSESSMENTS WITH NO TEMPLATES ONLY********/
		
		//return assessments;
	}
	
	
	
	@RequestMapping("/getassessmentbycourseid/{id}")
	@ResponseBody
	public AssessmentByCourseIdGET[] getAssessmentByCrseId(@PathVariable("id") String id
			, HttpServletRequest request1,Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		AssessmentByCourseIdGET[] assessments = null;
		
		String url = appGateway.getBaseUrl() + "/api/assessment/getassessmentbycourseid/"+id+"/"+emplid;
		//String url = "http://10.8.20.35:9120/api/assessment/getassessmentbycourseid/"+id+"/"+emplid;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<AssessmentByCourseIdGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, AssessmentByCourseIdGET[].class);
		if(response.getStatusCode() == HttpStatus.OK) {
			assessments = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		/********TEMPORARY CODE TO FILTER ASSESSMENTS WITH MAPPED TEMPLATES ONLY********/
		AssessmentByCourseIdGET[] assessmentFiltered = null;
		int len = 0;
		for(AssessmentByCourseIdGET x : assessments){
			if(x.getTemplateid() > 0)
			{
				len++;
			}
		}
		assessmentFiltered = new AssessmentByCourseIdGET[len];
		
		int ind = 0;
		for(AssessmentByCourseIdGET x : assessments){
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
	
	@RequestMapping(path="/postquestionpaper", method = RequestMethod.POST,consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public List<SingleResponseModel> postQuestionPaper(QuestionPaperModel questionpaper)
	{
		List<SingleResponseModel> res = new ArrayList<>();
		
		String url = appGateway.getBaseUrl() + "/api/assessment/addquestion";
		//String url = "http://10.8.20.35:9120/api/assessment/addquestion";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<QuestionPaperModel> request4 = new HttpEntity<QuestionPaperModel>(questionpaper, httpHeaders);
		ResponseEntity<SingleResponseModel> response4= restTemplate.exchange(url,HttpMethod.POST,request4, SingleResponseModel.class);
		if(response4.getStatusCode() == HttpStatus.OK)
		{
			if(!response4.getBody().getMessage().equals("Error"))
				res.add(new SingleResponseModel() {{setMessage("paper_created");}});
			else
				res.add(new SingleResponseModel() {{setMessage("error");}});
		}
		else
		{
			res.add(new SingleResponseModel() {{setMessage("paper_not_created");}});
		}
		
		/********************************Setting Strategy*******************************/
		String payLoad = "{" +
				"\"assessmentid\":"+questionpaper.getAssessmentid()+"," +
				"\"strategy\":\""+questionpaper.getStrategy()+"\"" +
				"}";
		HttpEntity<String> request = new HttpEntity<String>(payLoad,httpHeaders);
		String strategyUrl = appGateway.getBaseUrl() + "/api/evaluation/setstrategy";
		ResponseEntity<SingleResponseModel> response = restTemplate.exchange(strategyUrl,HttpMethod.POST, request, SingleResponseModel.class);
		if(response.getStatusCode() == HttpStatus.OK)
		{
			res.add(new SingleResponseModel() {{setMessage("strategy_set");}});
		}
		else
		{
			res.add(new SingleResponseModel() {{setMessage("strategy_not_set");}});
		}
		return res;
	}
	
	@GetMapping("/viewtemplate/{id}/{mode}/{courseid}")
	public String viewQuestionPaper(@PathVariable("id") String id,@PathVariable("mode") String mode
			,@PathVariable("courseid") String courseid, Model model ,HttpServletRequest req,HttpServletRequest request)
	{		
		String role=getEmplID.getRoleByRequest(request);		
		model.addAttribute("user_role", role);
		
		GetQuestionPaperDetails questionPaper = null;
		String url = appGateway.getBaseUrl() + "/api/assessment/getassessmentdetailsbyid/"+id;
		//String url = "http://10.8.20.35:9120/api/assessment/getassessmentdetailsbyid/"+id;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request4 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<GetQuestionPaperDetails> response4= restTemplate.exchange(url,HttpMethod.GET,request4, GetQuestionPaperDetails.class);
		if(response4.getStatusCode() == HttpStatus.OK) {
			questionPaper = response4.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response4.getStatusCode());
		}
		
		int sum = 0;
		for(GetSectionDetails x : questionPaper.getSectiondetails()){
			sum += x.getSectionmarks();
		}
		model.addAttribute("mode", mode);
		model.addAttribute("questionpaper", questionPaper);
		model.addAttribute("maxmarks", sum);
		
		/*** snigdhaa vaish edit-questions 02-jan-2021 ***/
		
		/*********************GET QUESTION TYPES*************************/
		String urlquesType = appGateway.getBaseUrl() + "/api/question/getquestiontype";
		QuestionType[] quesType = null;
		HttpEntity<String> request2 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<QuestionType[]> response2= restTemplate.exchange(urlquesType,HttpMethod.GET,request2, QuestionType[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
			quesType = response2.getBody();
			//System.out.println(assessments[0].getAssessmendescr());
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}
		
		/*********************GET QUESTION DIFFICULTY*************************/
		String urlDifficulty = appGateway.getBaseUrl() + "/api/question/getdifficultylevel";
		QuestionGravity[] difficulty = null;
		HttpEntity<String> request3 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<QuestionGravity[]> response3= restTemplate.exchange(urlDifficulty,HttpMethod.GET,request3, QuestionGravity[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
			difficulty = response3.getBody();
			//System.out.println(assessments[0].getAssessmendescr());
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}
		
		/**********************GET TOPICS BY COURSE ID******************/
		TopicsList[] topics = null;
		String urlTopics = appGateway.getBaseUrl() + "/api/topic/gettopicsbycourseid/"+courseid;
		//String urlTopics = "http://10.8.20.35:9120/api/topic/gettopicsbycourseid/"+courseid;
		
		HttpEntity<String> req4 = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<TopicsList[]> resp4= restTemplate.exchange(urlTopics,HttpMethod.GET,req4, TopicsList[].class);
		if(response4.getStatusCode() == HttpStatus.OK) {
			topics = resp4.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response4.getStatusCode());
		}
		
		model.addAttribute("topics", topics);
		model.addAttribute("questiontype", quesType);
		model.addAttribute("difficulty", difficulty);
		model.addAttribute("role",role);
		
		return "forms/questionPaper/viewQuestionPaper :: viewQuestionPaper";
	}
	
	@ResponseBody
	@PostMapping("/editQuestionPaper")
	public SingleResponseModel editQuestionPaper(@RequestBody DoubleResponseModel drm,HttpServletRequest request)
	{
		SingleResponseModel resp=null;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DoubleResponseModel> request2 = new HttpEntity<DoubleResponseModel>(drm,httpHeaders);
		String url = appGateway.getBaseUrl() + "/api/assessment/updateQuestionIdAssessment";
		//String url = "http://10.8.20.35:9120/api/assessment/updateQuestionIdAssessment";
		ResponseEntity<SingleResponseModel> response2= restTemplate.exchange(url, HttpMethod.POST, request2, SingleResponseModel.class);
		if(response2.getStatusCode() == HttpStatus.OK) {
            resp = response2.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response2.getStatusCode());
        }
		
		return resp;
	}
	/*** snigdhaa vaish edit-questions 02-jan-2021 ***/
	
	@GetMapping("/printtemplate/{id}")
	public String printQuestionPaper(@PathVariable("id") String id, Model model)
	{
		GetQuestionPaperDetails questionPaper = null;
		String url = appGateway.getBaseUrl() + "/api/assessment/getassessmentdetailsbyid/"+id;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request4 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<GetQuestionPaperDetails> response4= restTemplate.exchange(url,HttpMethod.GET,request4, GetQuestionPaperDetails.class);
		if(response4.getStatusCode() == HttpStatus.OK) {
			questionPaper = response4.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response4.getStatusCode());
		}
		
		int sum = 0;
		for(GetSectionDetails x : questionPaper.getSectiondetails()){
			sum += x.getSectionmarks();
		}
		model.addAttribute("questionpaper", questionPaper);
		model.addAttribute("maxmarks", sum);
		
		return "forms/questionPaper/printQuestionPaper :: printQuestionPaper";
	}
	
	@RequestMapping("/getcoeassessmentbycourseid/{id}")
	@ResponseBody
	public AssessmentByCourseIdGET[] getCoeAssessmentByCrseId(@PathVariable("id") String id
			, HttpServletRequest request1, Authentication authentication)
	{
		if(id.equals("-"))
		{
			AssessmentByCourseIdGET[] assessments = null;
			String url = appGateway.getBaseUrl() + "/api/assessment/getAllassessment";
			//String url = "http://localhost:9120" + "/api/assessment/getAllassessment";
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", AccessToken.getAccessToken());
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
			ResponseEntity<AssessmentByCourseIdGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, AssessmentByCourseIdGET[].class);
			if(response.getStatusCode() == HttpStatus.OK)
			{
				assessments = response.getBody();
			}
			else
			{
				System.out.println("Request Failed");
				System.out.println(response.getStatusCode());
			}
			
			return assessments;
		}
				
		AssessmentByCourseIdGET[] assessments = null;
		
		String url = appGateway.getBaseUrl() + "/api/assessment/getassessmentbycourseid/"+id;
		//String url = "http://10.8.20.35:9120/api/assessment/getassessmentbycourseid/"+id;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		ResponseEntity<AssessmentByCourseIdGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, AssessmentByCourseIdGET[].class);
		if(response.getStatusCode() == HttpStatus.OK) {
			assessments = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		/********TEMPORARY CODE TO FILTER ASSESSMENTS WITH MAPPED TEMPLATES ONLY********/
		AssessmentByCourseIdGET[] assessmentFiltered = null;
		int len = 0;
		for(AssessmentByCourseIdGET x : assessments){
			if(x.getTemplateid() > 0)
			{
				len++;
			}
		}
		assessmentFiltered = new AssessmentByCourseIdGET[len];
		
		int ind = 0;
		for(AssessmentByCourseIdGET x : assessments){
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
	
	@RequestMapping("/getQuestionsIqac/{faculty}/{course}")
	@ResponseBody
	public CommonModelReport[] getQuestionsIqac(@PathVariable("faculty") String faculty
			, @PathVariable("course") String course)
	{
		String url = appGateway.getBaseUrl() + "/api/bireports/getIAQCassessmentstatus/"+faculty+"/"+course;
		//String url="http://10.8.20.35:9120/api/bireports/getIAQCassessmentstatus/"+faculty+"/"+course;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		CommonModelReport[] questions = null;

		ResponseEntity<CommonModelReport[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CommonModelReport[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			questions = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		System.out.println(questions.length);
		return questions;
	}
}