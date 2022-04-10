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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.CommonModelReport;
import in.co.srdt.myguruji.model.CourseDetails;
import in.co.srdt.myguruji.model.LockedStudentsModel;
import in.co.srdt.myguruji.model.UserLockModel;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/administrator")
public class ProfileLockController
{
	@Autowired
    RestTemplate restTemplate;
	
    @Autowired
    private ApplicationGateway appGateway;
    
    GetEmplId getEmplID = new GetEmplId();
    
	@RequestMapping("/profilelock")
	public String profilelock(Authentication authentication,HttpServletRequest request, Model model)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
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
		
		/*CourseModel[] courses = new CourseModel[ crse.length ];
		
		int i = 0;
		for(CourseDetails x : crse)
		{
			courses[i] = new CourseModel(""+x.getCourseid(), x.getCoursetitle(), x.getCoursecode(), x.getCoursedescr());
			++i;
		}*/
		
        /*String url2 = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + login.getEmplid();
        HttpEntity<String> request1 = new HttpEntity<String>(headers);
        ResponseEntity<CourseModel[]> response= restTemplate.exchange(url2, HttpMethod.GET, request1, CourseModel[].class);
        if(response.getStatusCode() == HttpStatus.OK) {
            courses = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }*/
        
        model.addAttribute("courses", crse);
        //model.addAttribute("courses", courses);
        model.addAttribute("createdby", emplid);
		return "forms/administration/profilelock :: profilelock";
	}
	
	@RequestMapping("/lockusers")
	@ResponseBody
	public String lockusers(@RequestBody UserLockModel[] info)
	{
		String url2 = appGateway.getBaseUrl() + "/api/lockuser/lockStudent";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserLockModel[]> request1 = new HttpEntity<UserLockModel[]>(info,httpHeaders);
        ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url2, HttpMethod.POST, request1, SingleResponseModel.class);
        if(response.getStatusCode() == HttpStatus.OK)
        {
        	return "SUCCESS";
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
            return "ERROR";
        }
				
	}
	
	@RequestMapping("/getcourses")
	@ResponseBody
	public CourseModel[] getallcourses(Authentication authentication,HttpServletRequest request)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		CourseModel[] courses = null;
        String url2 = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        ResponseEntity<CourseModel[]> response= restTemplate.exchange(url2, HttpMethod.GET, request1, CourseModel[].class);
        if(response.getStatusCode() == HttpStatus.OK) {
            courses = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        
        return courses;
	}
	
	@RequestMapping("/batchdetails/{batchid}")
	@ResponseBody
	public CommonModelReport[] getBatchDetails(@PathVariable("batchid") String batchid)
	{
		CommonModelReport[] batch = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/lockuser/getLockedUsersDetailsByBatchId/" + batchid;
        //String url = "http://10.8.20.35:9120/api/lockuser/getLockedUsersDetailsByBatchId/" + batchid;
        ResponseEntity<CommonModelReport[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CommonModelReport[].class);
        
        if(response.getStatusCode() == HttpStatus.OK)
        {
        	batch = response.getBody();
    		return batch;
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
            return null;
        }
        
	}
	
	/* COMMENT BY SNIGDHAA 19-JAN-2021
	@RequestMapping("/batchdetails/{batchid}")
	@ResponseBody
	public BatchDetailsModel getBatchDetails(@PathVariable("batchid") String batchid)
	{
		BatchDetailsModel batch = null;
		
        HttpEntity<String> request1 = new HttpEntity<String>(headers);
        String url = appGateway.getBaseUrl() + "/api/batch/getbatchdetailsbyid/" + batchid;
        
        ResponseEntity<BatchDetailsModel> response= restTemplate.exchange(url ,HttpMethod.GET,request1, BatchDetailsModel.class);
        
        if(response.getStatusCode() == HttpStatus.OK)
        {
        	batch = response.getBody();
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        
		return batch;
	}
	*/
	
	@RequestMapping("/getlockedusers")
	@ResponseBody
	public LockedStudentsModel[] getlockedusers(HttpServletRequest request, Model model)
	{
		LockedStudentsModel[] lst=null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/lockuser/getLockedUsersList";
		//String url = "http://10.8.20.35:9120/api/lockuser/getLockedUsersList";
		
        ResponseEntity<LockedStudentsModel[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, LockedStudentsModel[].class);
        
        if(response.getStatusCode() == HttpStatus.OK)
        {
        	lst = response.getBody();
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        return lst;
	}
	
	@RequestMapping("/unlockusers")
	@ResponseBody
	public String unlockusers(@RequestBody UserLockModel[] info)
	{
		for(UserLockModel x : info)
		{
			String url2 = appGateway.getBaseUrl() + "/api/lockuser/unlockStudent/"+x.getStudentid();
			//String url2 = "http://10.8.20.35:9120/api/lockuser/unlockStudent/"+x.getStudentid();
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", AccessToken.getAccessToken());
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<UserLockModel[]> request1 = new HttpEntity<UserLockModel[]>(info,httpHeaders);
	        ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url2, HttpMethod.GET, request1, SingleResponseModel.class);
	        if(response.getStatusCode() == HttpStatus.OK)
	        {
	        	return "SUCCESS";
	        }
	        else
	        {
	            System.out.println("Request Failed");
	            System.out.println(response.getStatusCode());
	            return "ERROR";
	        }
		}
		
		return "SUCCESS";
	}
}