package in.co.srdt.myguruji.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.CourseByEmpl;
import in.co.srdt.myguruji.model.QuestionCount;
import in.co.srdt.myguruji.model.QuestionDetails;
import in.co.srdt.myguruji.model.QuestionGravity;
import in.co.srdt.myguruji.model.QuestionType;
import in.co.srdt.myguruji.model.TopicsList;
import in.co.srdt.myguruji.model.coursePlan.CourseObjGET;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/assessment")
public class ManageQuestionBankController {
	
	String topic = "";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;
	
	@Autowired
	GetEmplId getEmplID;//=new GetEmplId;
	
	@RequestMapping("/managequestionbank")
	public String goToManageClass() {

		return "forms/assessmentManagement/manageQuestionBank :: manageQuestionBank";
	}
	
	/* ********************************* Question Forms Load ******************************** */
	
	@RequestMapping("/loadAddSATQuestionForm/{courseid}")
	public String goToAddSATQuestionForm(Model model, @PathVariable("courseid") String courseid) {
		
		CourseObjGET[] outcomes = fetchOutcomes(courseid);
		//CourseObjGET[] books = fetchBooks(courseid);
		
		Set<CourseObjGET> books = fetchBooks(courseid);
		
		model.addAttribute("books", books);
		model.addAttribute("outcomes", outcomes);
		model.addAttribute("topicname", topic);
		return "forms/assessmentManagement/addSATQuestion :: addSATQuestion";
	}
	@RequestMapping("/loadAddLATQuestionForm/{courseid}")
	public String goToAddLATQuestionForm(Model model, @PathVariable("courseid") String courseid) {
		CourseObjGET[] outcomes = fetchOutcomes(courseid);
		//CourseObjGET[] books = fetchBooks(courseid);
		Set<CourseObjGET> books = fetchBooks(courseid);
		model.addAttribute("books", books);
		model.addAttribute("outcomes", outcomes);
		model.addAttribute("topicname", topic);
		return "forms/assessmentManagement/addLATQuestion :: addLATQuestion";
	}
	@RequestMapping("/loadAddFIBQuestionForm/{courseid}")
	public String goToAddFIBQuestionForm(Model model, @PathVariable("courseid") String courseid) {
		CourseObjGET[] outcomes = fetchOutcomes(courseid);
		//CourseObjGET[] books = fetchBooks(courseid);
		Set<CourseObjGET> books = fetchBooks(courseid);
		model.addAttribute("books", books);
		model.addAttribute("outcomes", outcomes);
		model.addAttribute("topicname", topic);
		return "forms/assessmentManagement/addFIBQuestion :: addFIBQuestion";
	}
	@RequestMapping("/loadAddTNFQuestionForm/{courseid}")
	public String goToAddTNFQuestionForm(Model model, @PathVariable("courseid") String courseid) {
		CourseObjGET[] outcomes = fetchOutcomes(courseid);
		//CourseObjGET[] books = fetchBooks(courseid);
		Set<CourseObjGET> books = fetchBooks(courseid);
		model.addAttribute("books", books);
		model.addAttribute("outcomes", outcomes);
		model.addAttribute("topicname", topic);
		return "forms/assessmentManagement/addTNFQuestion :: addTNFQuestion";
	}
	@RequestMapping("/loadAddATTQuestionForm/{courseid}")
	public String goToAddATTQuestionForm(Model model, @PathVariable("courseid") String courseid) {
		CourseObjGET[] outcomes = fetchOutcomes(courseid);
		//CourseObjGET[] books = fetchBooks(courseid);
		Set<CourseObjGET> books = fetchBooks(courseid);
		model.addAttribute("books", books);
		model.addAttribute("outcomes", outcomes);
		model.addAttribute("topicname", topic);
		return "forms/assessmentManagement/addATTQuestion :: addATTQuestion";
	}
	@RequestMapping("/loadAddSCQQuestionForm/{courseid}")
	public String goToAddSCQQuestionForm(Model model, @PathVariable("courseid") String courseid) {
		CourseObjGET[] outcomes = fetchOutcomes(courseid);
		//CourseObjGET[] books = fetchBooks(courseid);
		Set<CourseObjGET> books = fetchBooks(courseid);
		model.addAttribute("books", books);
		model.addAttribute("outcomes", outcomes);
		model.addAttribute("topicname", topic);
		return "forms/assessmentManagement/addSCQQuestion :: addSCQQuestion";
	}
	@RequestMapping("/loadAddMCQQuestionForm/{courseid}")
	public String goToAddMCQQuestionForm(Model model, @PathVariable("courseid") String courseid) {
		CourseObjGET[] outcomes = fetchOutcomes(courseid);
		//CourseObjGET[] books = fetchBooks(courseid);
		Set<CourseObjGET> books = fetchBooks(courseid);
		model.addAttribute("books", books);
		model.addAttribute("outcomes", outcomes);
		model.addAttribute("topicname", topic);
		return "forms/assessmentManagement/addMCQQuestion :: addMCQQuestion";
	}
	
	/**************************************************************************************************/
	
	@RequestMapping("/addQuestions/{courseid}/{topicid}/{topicdescr}/{qtypeid}/{qtypedescr}")
	public String goToAddQuestions(@PathVariable("courseid") String courseid, 
			@PathVariable("topicid") String topicid, 
			@PathVariable("topicdescr") String topicdescr, 
			@PathVariable("qtypeid") String qtypeid,
			@PathVariable("qtypedescr") String qtypedescr,
			Model model) {
		
		topic = topicdescr.replaceAll("_", " ");
		
		model.addAttribute("courseid", courseid); 
		model.addAttribute("topicid", topicid); 
		model.addAttribute("topicdescr", topicdescr.replaceAll("_", " "));
		model.addAttribute("qtypeid", qtypeid);
		model.addAttribute("qtypedescr", qtypedescr.replaceAll("_", " "));
		
		return "forms/assessmentManagement/addQuestions :: addQuestions";
	}
	
	@RequestMapping("/viewQuestions/{courseid}/{topicid}/{typeid}") 
	public String goToViewQuestions(@PathVariable("courseid") String courseid, @PathVariable("topicid") String topicid, @PathVariable("typeid") String typeid, Model model) {
		model.addAttribute("courseid", courseid);
		model.addAttribute("topicid", topicid);
		model.addAttribute("typeid", typeid);
		
		TopicsList[] topicList = null;
		
		String getTopicsUrl = appGateway.getBaseUrl() + "/api/topic/gettopicsbycourseid/" + courseid;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		ResponseEntity<TopicsList[]> response = restTemplate.exchange(getTopicsUrl, HttpMethod.GET, request, TopicsList[].class);
		topicList = response.getBody();
		model.addAttribute("topicList", topicList);
		
		return "forms/assessmentManagement/questionDetailsView :: questionDetailsView";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getquestionlist", method = RequestMethod.POST)
	public QuestionDetails[] getQuestionList(HttpServletRequest searchCourseData){
		QuestionDetails[] questionDetails = null;
		String getQuestionDetailsUrl = appGateway.getBaseUrl() + "/api/question/search";

		String payLode = "{" +
				"\"courseid\"" + ":\"" +searchCourseData.getParameter("courseid")+ "\"," +
				"\"topicid\"" + ":\"" +searchCourseData.getParameter("topicid")+ "\"," +
				"\"typeid\"" + ":\"" +searchCourseData.getParameter("typeid")+ "\"" +
				"}";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLode, httpHeaders);
		
		ResponseEntity<QuestionDetails[]> response = restTemplate.exchange(getQuestionDetailsUrl, HttpMethod.POST, request, QuestionDetails[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) { 
			questionDetails = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return questionDetails;
	}
	 
	
	@RequestMapping("/addQuestionsBlank")
	public String goToAddQuestionsOpen() {
		return "forms/assessmentManagement/addQuestionsOpen :: addQuestionsOpen";
	}
	
	
	@ResponseBody
	@RequestMapping("/getTopics/{courseId}")
	public TopicsList[] getTopicsByCourseId(@PathVariable("courseId") String courseId){
		TopicsList[] topicList = null;
		String getTopicsUrl = appGateway.getBaseUrl() + "/api/topic/gettopicsbycourseid/" + courseId;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<TopicsList[]> response = restTemplate.exchange(getTopicsUrl, HttpMethod.GET, request, TopicsList[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) { 
			topicList = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return topicList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getquestioncount", method = RequestMethod.POST)
	public QuestionCount[] getQuestionsCount(HttpServletRequest searchCourseData){
		QuestionCount[] questionCount = null;
		String getQuestionCountUrl = appGateway.getBaseUrl() + "/api/question/getquestioncount";

		String payLode = "{" +
				"\"courseid\"" + ":\"" +searchCourseData.getParameter("courseid")+ "\"," +
				"\"topicid\"" + ":\"" +searchCourseData.getParameter("topicid")+ "\"," +
				"\"typeid\"" + ":\"" +searchCourseData.getParameter("typeid")+ "\"" +
				"}";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLode, httpHeaders);
		
		ResponseEntity<QuestionCount[]> response = restTemplate.exchange(getQuestionCountUrl, HttpMethod.POST, request, QuestionCount[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) { 
			questionCount = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return questionCount;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addATTQuestion", method=RequestMethod.POST)
	public String addATTQuestion(String jsonData) {
		//jsonData= jsonData.replaceAll("\n","").replaceAll("\r","").replaceAll("\"", "'");
		//System.out.println(jsonData);
		String resultText = "";
		String postSATQuestionUrl = appGateway.getBaseUrl() + "/api/question/create";
		//String postSATQuestionUrl = "http://10.8.20.35:9123/api/question/create";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(jsonData, httpHeaders);
		
		ResponseEntity<String> response = restTemplate.exchange(postSATQuestionUrl, HttpMethod.POST, request, String.class);
		
		if(response.getStatusCode() == HttpStatus.OK) { 
			resultText = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return resultText;
	}
	
	@ModelAttribute("courses")
	public List<CourseByEmpl> getCourses(Authentication authentication){
		
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
	
	@ModelAttribute("questionsType")
	public List<QuestionType> getQuestionTypes(){
		QuestionType[] questionType = null;
		List<QuestionType> questionTypeList = new ArrayList<>();

		String getAllCoursesURL = appGateway.getBaseUrl() + "/api/question/getquestiontype";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<QuestionType[]> response = restTemplate.exchange(getAllCoursesURL, HttpMethod.GET, request, QuestionType[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			questionType = response.getBody();
			//questionTypeList = Arrays.asList(questionType);
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		for(QuestionType x : questionType)
		{
			if(x.getId() != 7)
				questionTypeList.add(x);
		}
		
		return questionTypeList;
	}
	
	@ModelAttribute("questionsGravity")
	public List<QuestionGravity> getQuestionGravity(){
		QuestionGravity[] questionGravity = null;
		List<QuestionGravity> questionGravityList = null;

		String getQuestionGravityURL = appGateway.getBaseUrl() + "/api/question/getdifficultylevel";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		ResponseEntity<QuestionGravity[]> response = restTemplate.exchange(getQuestionGravityURL, HttpMethod.GET, request, QuestionGravity[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			questionGravity = response.getBody();
			questionGravityList = Arrays.asList(questionGravity);
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return questionGravityList;
	}
	
	@ResponseBody
    @RequestMapping("/getoutcomes/{courseid}")
    public CourseObjGET[] fetchOutcomes(@PathVariable("courseid") String courseid)
    {
        CourseObjGET[] objectives = null;

        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        
        String url = appGateway.getBaseUrl() + "/api/courseoutcome/getcourseoutcomebycourseid/"+courseid;

        ResponseEntity<CourseObjGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, CourseObjGET[].class);
        if(response.getStatusCode() == HttpStatus.OK) {
            objectives = response.getBody();
        } else {
            System.out.println("Request Failed HERE");
            System.out.println(response.getStatusCode());
        }
        return objectives;
    }
	
	@ResponseBody
    @RequestMapping("/getbooks/{courseid}")
    public Set<CourseObjGET> fetchBooks(@PathVariable("courseid") String courseid)
    {
        CourseObjGET[] objectives = null;

        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        
        String url = appGateway.getBaseUrl() + "/api/courseplan/getcoursebooksbycourseid/"+courseid;

        ResponseEntity<CourseObjGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, CourseObjGET[].class);
        if(response.getStatusCode() == HttpStatus.OK) {
            objectives = response.getBody();
        } else {
            System.out.println("Request Failed HERE");
            System.out.println(response.getStatusCode());
        }
        
        Set<CourseObjGET> hash_Set = new HashSet<CourseObjGET>();
        
        for(CourseObjGET x : objectives)
        {
        	hash_Set.add(x);
        }
        
        return hash_Set;
    }
	
	@GetMapping("/viewOrEditQuestions/{mode}/{typeid}/{courseid}/{topicid}/{quesid}")
    public String viewQuestions(Model model,HttpServletRequest req,Authentication authentication
    		,@PathVariable("mode") String mode,@PathVariable("typeid") String typeid
    		,@PathVariable("courseid") String courseid,@PathVariable("topicid") String topicid
    		,@PathVariable("quesid") String quesid)
    {
		QuestionDetails[] questionDetails=null;
		QuestionDetails questionDetailsfinal=null;
		String getQuestionDetailsUrl = appGateway.getBaseUrl() + "/api/question/search";

		String payLode = "{" +
				"\"courseid\"" + ":\"" +courseid+ "\"," +
				"\"topicid\"" + ":\"" +topicid+ "\"," +
				"\"typeid\"" + ":\"" +typeid+ "\"" +
				"}";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(payLode, httpHeaders);
		
		ResponseEntity<QuestionDetails[]> response = restTemplate.exchange(getQuestionDetailsUrl, HttpMethod.POST, request, QuestionDetails[].class);
		
		if(response.getStatusCode() == HttpStatus.OK) { 
			questionDetails = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		CourseObjGET[] outcomes = fetchOutcomes(courseid);
		Set<CourseObjGET> books = fetchBooks(courseid);
		
		for(QuestionDetails x : questionDetails) 
		{
			if((""+x.getQuestionid()).equals(quesid))		
			{
				questionDetailsfinal=new QuestionDetails(x.getQuestionid(), x.getCourseid(), x.getTopicid()
						, x.getDifficultyid(), x.getTypeid(), x.getQuestiontext(), x.getOpt1(), x.getOpt2()
						, x.getOpt3(), x.getOpt4(), x.getOpt5(), x.getOpt6(), x.getCurrectopt(), x.getCreatedby()
						, x.getFile(), x.getBlmtaxonomy(), x.getCourseobj(), x.getReferid(), x.getTopictitle()
						, x.getCoursetitle(), x.getDifficultytitle(), x.getTypetitle());
				break;
			}
		}

		model.addAttribute("books", books);
		model.addAttribute("outcomes", outcomes);
		model.addAttribute("mode",mode);
		model.addAttribute("ques",questionDetailsfinal);
		//model.addAttribute("referid",questionDetailsfinal.getReferid()!=null && !questionDetailsfinal.getReferid().equals("")?Long.parseLong(questionDetailsfinal.getReferid()):0);
		model.addAttribute("referid",Long.parseLong(questionDetailsfinal.getReferid()));
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		model.addAttribute("createdBY",emplid);
		
		return "forms/assessmentManagement/ViewQuestionBank/viewSCQQuestion :: viewSCQQuestion";
    }
	
	@GetMapping("/setInactive/{quesid}")
	@ResponseBody
	public SingleResponseModel setInactive(@PathVariable("quesid") String quesid)
	{
		String url=appGateway.getBaseUrl() + "/api/question/setIsActiveStatus/"+quesid;
		//String url="http://10.8.20.35:9123/api/question/setIsActiveStatus/"+quesid;
		SingleResponseModel resp=null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		ResponseEntity<SingleResponseModel> response = restTemplate.exchange(url, HttpMethod.GET, request, SingleResponseModel.class);
		
		if(response.getStatusCode() == HttpStatus.OK) { 
			resp = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return resp;
	}	
}
