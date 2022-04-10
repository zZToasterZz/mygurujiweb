package in.co.srdt.myguruji.controllers;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.CommonModelReport;
import in.co.srdt.myguruji.model.CoursePlanInfo;
import in.co.srdt.myguruji.model.DoubleResponseModel;
import in.co.srdt.myguruji.model.FacultyBatchInfo;
import in.co.srdt.myguruji.model.StudentCoursePlanInfo;
import in.co.srdt.myguruji.model.UserLoginSync;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/adminlogin")
public class AdminController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;
	
	@Autowired
	GetEmplId getEmplID;// = new GetEmplId();
	
	@RequestMapping("/resetPassword")
	public String resetPassword(HttpServletRequest request, Authentication authentication)
	{
		return "forms/adminlogin/resetPassword.html :: resetPassword";
	}
	
	@RequestMapping("/loadCoursePlan")
	public String loadCoursePlan()
	{
		return "forms/adminlogin/coursePlan.html :: coursePlan";
	}
	
	@RequestMapping("/facultyTag")
	public String facultyTag()
	{
		return "forms/adminlogin/facultyTag.html :: facultyTag";
	}
	
	@RequestMapping("/loadSynchLogin")
	public String loadSynchLogin()
	{
		return "forms/adminlogin/synchLogin.html :: synchLogin";
	}
	
	@RequestMapping("/delgradebook")
	public String delgradebook()
	{
		return "forms/adminlogin/deletegradebook.html :: deletegradebook";
	}
	
	@RequestMapping("/studentEnrollment/{stdnt}")
	public String studentEnrollment(@PathVariable("stdnt") String isStdnt, Model model, HttpServletRequest request
			,Authentication authentication)
	{
		if(isStdnt.equals("Y"))
		{
			model.addAttribute("campusid",authentication.getName());
		}
		model.addAttribute("isStudent",isStdnt);
		return "forms/adminlogin/studentEnrollment.html :: studentEnrollment";
	}
	
	@RequestMapping("/searchDetails/{type}/{emplid}")
	@ResponseBody
	public DoubleResponseModel searchDetails(@PathVariable("type") String type,@PathVariable("emplid") String emplid
			, HttpServletRequest request, Authentication authentication)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		DoubleResponseModel drm = null;
		if(type.equalsIgnoreCase("faculty")) 
		{
			String url=appGateway.getBaseUrl() + "/api/faculty/getFacultyLoginsyncStatus/"+emplid;
			//String url="http://10.8.20.35:9120/api/faculty/getFacultyLoginsyncStatus/"+emplid;		
			
			HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);        
	        ResponseEntity<DoubleResponseModel> response= restTemplate.exchange(url ,HttpMethod.GET,request1, DoubleResponseModel.class);
	        if(response.getStatusCode() == HttpStatus.OK) {
	        	drm = response.getBody();
	        } else {
	            System.out.println("Request Failed");
	            System.out.println(response.getStatusCode());
	        }	        
		}		
		else
		{			
			String url=appGateway.getBaseUrl() + "/api/student/getStudentLoginsyncStatus/"+emplid;
			//String url="http://10.8.20.35:9120/api/student/getStudentLoginsyncStatus/"+emplid;		
			
			HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);        
	        ResponseEntity<DoubleResponseModel> response= restTemplate.exchange(url ,HttpMethod.GET,request1, DoubleResponseModel.class);
	        if(response.getStatusCode() == HttpStatus.OK) {
	        	drm = response.getBody();
	        } else {
	            System.out.println("Request Failed");
	            System.out.println(response.getStatusCode());
	        }
		}
		
		return drm;
	}
	
	@PostMapping("/resetpwd")
	@ResponseBody
	public SingleResponseModel resetpwd(@RequestBody String jsonData, HttpServletRequest request)
	{
		//String url="http://10.8.20.35:71/api/userlogin/changepwd";
		String url=appGateway.getOauthUrl() + "/mgoauth/userlogin/changepwd";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(jsonData,httpHeaders);
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
	
	@RequestMapping("/getCoursePlanDtl/{crsecode}/{batchcode}")
	@ResponseBody
	public CoursePlanInfo[] getCoursePlanDtl(HttpServletRequest request,
			@PathVariable("crsecode") String coursecode, @PathVariable("batchcode") String batchcode)
	{
		CoursePlanInfo[] cpi = null;
		String url = appGateway.getBaseUrl() + "/api/courseplan/getCoursePlanByCourseCodeandBatchcode/"+coursecode+"/"+batchcode;
		//String url = "http://10.8.20.35:9120/api/courseplan/getCoursePlanByCourseCodeandBatchcode/"+coursecode+"/"+batchcode;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<CoursePlanInfo[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CoursePlanInfo[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			cpi = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return cpi;
	}
	
	@RequestMapping("/getFacultyTag/{facid}/{batchcode}")
	@ResponseBody
	public FacultyBatchInfo[] getFacultyTag(HttpServletRequest request,
			@PathVariable("facid") String facid, @PathVariable("batchcode") String batchcode)
	{
		FacultyBatchInfo[] fbi = null;
		String url = appGateway.getBaseUrl() + "/api/faculty/getFacultyDetailsForTagging/"+facid+"/"+batchcode;
		//String url = "http://10.8.20.35:9120/api/faculty/getFacultyDetailsForTagging/"+facid+"/"+batchcode;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<FacultyBatchInfo[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, FacultyBatchInfo[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			fbi = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return fbi;
	}
	
	@RequestMapping("/getStudentEnroll/{campusid}")
	@ResponseBody
	public StudentCoursePlanInfo[] getStudentEnroll(HttpServletRequest request,
			@PathVariable("campusid") String campusid)
	{
		StudentCoursePlanInfo[] scpi = null;
		String url = appGateway.getBaseUrl() + "/api/student/getStudentCourseplanMap/"+campusid;
		//String url = "http://10.8.20.35:9120/api/student/getStudentCourseplanMap/"+campusid;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<StudentCoursePlanInfo[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, StudentCoursePlanInfo[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			scpi = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return scpi;
	}
	
	@RequestMapping("/getAsynchDataFac")
	@ResponseBody
	public CommonModelReport[] getAsynchDataFac()
	{
		CommonModelReport[] cmr = null;		
		String url = appGateway.getBaseUrl() + "/api/admindashboard/getAsyncEmplIdsforFaculty";
		//String url = "http://10.8.20.35:9122/api/admindashboard/getAsyncEmplIdsforFaculty";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<CommonModelReport[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CommonModelReport[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			cmr = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return cmr;
	}
	
	@RequestMapping("/getAsynchData")
	@ResponseBody
	public CommonModelReport[] getAsynchData()
	{
		CommonModelReport[] cmr = null;
		String url = appGateway.getBaseUrl() + "/api/admindashboard/getAsyncEmplIdsforStudents";
		//String url = "http://10.8.20.35:9122/api/admindashboard/getAsyncEmplIdsforStudents";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<CommonModelReport[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CommonModelReport[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			cmr = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return cmr;
	}
	
	@RequestMapping("/synchLoginData")
	@ResponseBody
	public SingleResponseModel[] synchLoginData(@RequestBody UserLoginSync[] req)
	{
		SingleResponseModel[] srm = null;
		
		String url = appGateway.getOauthUrl() + "/mgoauth/userlogin/synclogins";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserLoginSync[]> request1 = new HttpEntity<UserLoginSync[]>(req,httpHeaders);
		ResponseEntity<SingleResponseModel[]> response= restTemplate.exchange(url ,HttpMethod.POST,request1, SingleResponseModel[].class);

		if(response.getStatusCode() == HttpStatus.OK){
			srm = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return srm;
	}
	
	@RequestMapping("/mapLoginData/{type}")
	@ResponseBody
	public SingleResponseModel mapLoginData(@PathVariable("type") String type)
	{
		SingleResponseModel srm = null;
		String url="";
		if(type.equalsIgnoreCase("student"))
			url = appGateway.getBaseUrl() + "/api/admindashboard/studentRoleMapping";
			//url = "http://10.8.20.35:9120/api/admindashboard/studentRoleMapping";
		else
			url = appGateway.getBaseUrl() + "/api/admindashboard/facultyRoleMapping";
			//url = "http://10.8.20.35:9120/api/admindashboard/facultyRoleMapping";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url ,HttpMethod.POST,request1, SingleResponseModel.class);

		if(response.getStatusCode() == HttpStatus.OK){
			srm = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return srm;
	}

	@RequestMapping("/getbatchdetailsbycode/{batchcode}")
	@ResponseBody
	public CommonModelReport getbatchdetailsbycode(@PathVariable("batchcode") String batchcode)
	{
		CommonModelReport cmr = null;
		String url=appGateway.getBaseUrl() + "/api/batch/getBatchDetailsbyBatchCode/"+batchcode;
		//url = "http://10.8.20.35:9120/api/admindashboard/facultyRoleMapping";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<CommonModelReport> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CommonModelReport.class);

		if(response.getStatusCode() == HttpStatus.OK){
			cmr = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return cmr;
	}
	
	@RequestMapping("/deletegradebook/{batchcode}/{term}")
	@ResponseBody
	public SingleResponseModel deletegradebook(@PathVariable("batchcode") String batchcode
			, @PathVariable("term") String term)
	{
		SingleResponseModel resp = null;
		String url="http://103.245.34.253:37520/PSIGW/RESTListeningConnector/PSFT_CS/SRM_CLS_ASN_DELETE.v1/srm_cls_assn_del_dc/";
		String json="{\r\n"
				+ "\"srm_cls_assn_del_dc\": {\r\n"
				+ "    \"strm\": "+term+",\r\n"
				+ "    \"class_nbr\": "+batchcode+"\r\n"
				+ "    }\r\n"
				+ "}";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(json,httpHeaders);
		ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url ,HttpMethod.POST,request1, SingleResponseModel.class);

		if(response.getStatusCode() == HttpStatus.OK)
		{
			resp = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return resp;				
	}
	
	@RequestMapping("/deletegradebooklms/{batchcode}")
	@ResponseBody
	public SingleResponseModel deletegradebooklms(@PathVariable("batchcode") String batchcode)
	{
		SingleResponseModel resp = null;
		String url=appGateway.getBaseUrl() + "/api/gradebook/deleteGradeBookByBatchCode/"+batchcode;

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url ,HttpMethod.GET,request1, SingleResponseModel.class);

		if(response.getStatusCode() == HttpStatus.OK)
		{
			resp = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		
		return resp;				
	}
}