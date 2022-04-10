package in.co.srdt.myguruji.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.AssessResponseModel;
import in.co.srdt.myguruji.model.AssessResult;
import in.co.srdt.myguruji.model.AssessResultList;
import in.co.srdt.myguruji.model.AssessmentDetails;
import in.co.srdt.myguruji.model.AssessmentStrategyModel;
import in.co.srdt.myguruji.model.Batch;
import in.co.srdt.myguruji.model.BatchDetailsModel;
import in.co.srdt.myguruji.model.GetQuestionPaperDetails;
import in.co.srdt.myguruji.model.GetSectionDetails;
import in.co.srdt.myguruji.model.MarksModel;
import in.co.srdt.myguruji.model.StatusModel;
import in.co.srdt.myguruji.model.Student;
import in.co.srdt.myguruji.model.StudentDetailsModel;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/assessmentresult")
public class ResultController
{
	@Autowired
	GetEmplId getEmplID;//=new GetEmplId;
	
	@Autowired
    RestTemplate restTemplate;
	
	@Autowired
    private ApplicationGateway appGateway;
	
	@RequestMapping("/viewresult")
	public String viewResult(Model model, HttpServletRequest request,Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
        CourseModel[] courses = null;

        ResponseEntity<CourseModel[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CourseModel[].class);

        if(response.getStatusCode() == HttpStatus.OK) {
            courses = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        
        model.addAttribute("course",courses);
        model.addAttribute("emplid",emplid);
        
		return "forms/result/viewResult :: viewResult";
	}
	
	@RequestMapping("/getassessmentbycourse/{crseid}")
	@ResponseBody
	public AssessmentDetails[] getassessbycourse(@PathVariable("crseid") String crseid)
	{
		String url = appGateway.getBaseUrl() + "/api/assessment/getconductedassessmentbycourseid/"+crseid;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		AssessmentDetails[] assess = null;
        ResponseEntity<AssessmentDetails[]> response= restTemplate.exchange(url ,HttpMethod.GET,request, AssessmentDetails[].class);

        if(response.getStatusCode() == HttpStatus.OK)
        {
            assess = response.getBody();
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
		return assess;
	}
	
	@RequestMapping("/getassessmentstrategy/{assessid}")
	@ResponseBody
	public AssessmentStrategyModel getassessmentstrategy(@PathVariable("assessid") String assessid)
	{
		String url = appGateway.getBaseUrl() + "/api/evaluation/getstrategy/"+assessid;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		AssessmentStrategyModel strategy = null;
		
		ResponseEntity<AssessmentStrategyModel> response = restTemplate.exchange(url, HttpMethod.GET, request, AssessmentStrategyModel.class );
		if(response.getStatusCode() == HttpStatus.OK)
			strategy = response.getBody();
        else
            System.out.println(response.getStatusCode());
		
		return strategy;
	}
	
	@RequestMapping("/getbatchbyassessment/{assessid}")
	@ResponseBody
	public Batch[] getbatchbyassess(@PathVariable("assessid") String assessid)
	{
		String url = appGateway.getBaseUrl() + "/api/assessment/getbatchesbyassessmentid/"+assessid;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		Batch[] batch = null;
        ResponseEntity<Batch[]> response= restTemplate.exchange(url ,HttpMethod.GET,request, Batch[].class);
        
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
	
	@RequestMapping("/getassessmentresult/{assessid}/{batchid}")
	@ResponseBody
	//public AssessResult[] getassessmentresult(@PathVariable("assessid") String assessid,@PathVariable("batchid") String batchid)
	public List<AssessResult> getassessmentresult(@PathVariable("assessid") String assessid,@PathVariable("batchid") String batchid)
	{
		List<AssessResult> finalResult = new ArrayList<AssessResult>();
		
		String url = appGateway.getBaseUrl() + "/api/assessment/getassessresult/"+assessid+"/"+batchid;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		AssessResult[] result = null;
        ResponseEntity<AssessResult[]> response= restTemplate.exchange(url ,HttpMethod.GET,request, AssessResult[].class);
        if(response.getStatusCode() == HttpStatus.OK)
        {
        	result = response.getBody();
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        
        String url2 = appGateway.getBaseUrl() + "/api/batch/getbatchdetailsbyid/"+batchid;
        BatchDetailsModel batch = null;
        ResponseEntity<BatchDetailsModel> response2= restTemplate.exchange(url2 ,HttpMethod.GET,request, BatchDetailsModel.class);
        if(response2.getStatusCode() == HttpStatus.OK)
        {
        	batch = response2.getBody();
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response2.getStatusCode());
        }
        
        for(Student x : batch.getStudents())
        {
        	boolean flag = true;
        	for(int j = 0; j<result.length; j++)
        	{
        		if(x.getStudentid() == result[j].getStudentid())
        		{
        			result[j].setAttendance("Y");
        			result[j].setSubmission("Y");
        			finalResult.add( result[j] );
        			flag = false;
        			break;
        		}
        	}
        	if(flag == true)
        	{
	    		AssessResult temp = new AssessResult();
	    		temp.setStudentid(x.getStudentid());
	    		temp.setFirstname(x.getFirstname());
	    		temp.setLastname(x.getLastname());
	    		temp.setOptmarks(0);
	    		temp.setPassword("0");
	    		temp.setEmplid(x.getEmplid());
	    		temp.setEmailid(x.getEmailaddr());
	    		temp.setLoginid(x.getEmplid());
	    		temp.setAttendance("N");
	    		temp.setSubmission("N");
	    		
	    		finalResult.add(temp);
        	}
        }
        
        /*************GETTING TOTAL MARKS**********/
        String url3 = appGateway.getBaseUrl() + "/api/assessment/getassessmentdetailsbyid/"+assessid;
        GetQuestionPaperDetails questionPaper = null;
        ResponseEntity<GetQuestionPaperDetails> response3= restTemplate.exchange(url3 ,HttpMethod.GET,request, GetQuestionPaperDetails.class);
        if(response3.getStatusCode() == HttpStatus.OK)
        {
        	questionPaper = response3.getBody();
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response3.getStatusCode());
        }
        /*************GETTING TOTAL MARKS**********/
        double totalMarks = 0;
        for(GetSectionDetails x : questionPaper.getSectiondetails())
        {
        	totalMarks += x.getSectionmarks();
        }
        
        AssessResult temp = new AssessResult();
		temp.setStudentid(-1);
		temp.setFirstname("TEST");
		temp.setLastname("TEST");
		temp.setOptmarks(0);
		temp.setPassword(""+totalMarks);
		temp.setEmplid("TEST");
		temp.setEmailid("TEST");
		temp.setLoginid("TEST");
		
		finalResult.add(temp);
		
		return finalResult;
	}
	
	@RequestMapping("/getassessdetails/{stdntid}/{assessid}")
	@ResponseBody
	public AssessResponseModel getassessdetails(@PathVariable("stdntid") String stdntid, @PathVariable("assessid") String assessid)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		
		StudentDetailsModel student = null;
		String url1 = appGateway.getBaseUrl() + "/api/student/getstudentbyid/"+stdntid;
		ResponseEntity<StudentDetailsModel> response1 = restTemplate.exchange(url1, HttpMethod.GET, request, StudentDetailsModel.class);
		if(response1.getStatusCode() == HttpStatus.OK)
        {
        	student = response1.getBody();
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response1.getStatusCode());
        }
		
		AssessResponseModel assessres = null;
		String url2 = appGateway.getBaseUrl() + "/api/assessment/getassessresponce/"+student.getStudent().getEmplid()+"/"+assessid;
		
		ResponseEntity<AssessResponseModel> response2 = restTemplate.exchange(url2, HttpMethod.GET, request, AssessResponseModel.class);
		if(response1.getStatusCode() == HttpStatus.OK)
        {
			assessres = response2.getBody();
			assessres.setEmplid(student.getStudent().getEmplid());
			assessres.setFullname(student.getStudent().getFullname());
			assessres.setStudentid(""+student.getStudent().getStudentid());
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response2.getStatusCode());
        }
		
		return assessres;
	}
	
	@RequestMapping("/savemarks")
	@ResponseBody
	public SingleResponseModel savemarks(@RequestBody MarksModel[] marks)
	{
		String statusUrl = appGateway.getBaseUrl()+"/api/evaluation/getevaluationstatus/"+marks[0].getAssessmentid()+"/"+marks[0].getStudentid();
		StatusModel evalStatus = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<StatusModel> statReq = new HttpEntity<StatusModel>(httpHeaders);
		ResponseEntity<StatusModel> statRes = restTemplate.exchange(statusUrl, HttpMethod.GET, statReq, StatusModel.class);
		if(statRes.getStatusCode() == HttpStatus.OK)
		{
			evalStatus = statRes.getBody();
		}
		else
		{
			return new SingleResponseModel() {{setMessage("error");}};
		}
		
		if(evalStatus.getStatus() != null)
		if(evalStatus.getStatus().equals("S"))
		{
			return new SingleResponseModel() {{setMessage("FROZEN");}};
		}
		
		SingleResponseModel[] responses = null;
		HttpEntity<MarksModel[]> request1 = new HttpEntity<MarksModel[]>(marks,httpHeaders);
		String url1 = appGateway.getBaseUrl() + "/api/assessment/setallmarks";
		ResponseEntity<SingleResponseModel[]> response = restTemplate.exchange(url1, HttpMethod.POST, request1,SingleResponseModel[].class);
		if(response.getStatusCode() == HttpStatus.OK)
		{
			responses = response.getBody();
		}
		else
		{
			System.out.println(response.getStatusCode());
			return new SingleResponseModel() {{setMessage("error");}};
		}
		
		SingleResponseModel res = null;
		StatusModel status = new StatusModel(0, marks[0].getAssessmentid(), marks[0].getStudentid(), "T");
		
		String url2 = appGateway.getBaseUrl() + "/api/evaluation/setevaluationstatus";
		HttpEntity<StatusModel> request2 = new HttpEntity<StatusModel>(status,httpHeaders);
		
		ResponseEntity<SingleResponseModel> response2 = restTemplate.exchange(url2, HttpMethod.POST, request2, SingleResponseModel.class);
		if(response2.getStatusCode() == HttpStatus.OK)
		{
			res = response2.getBody();
		}
		else
		{
			System.out.println(response2.getStatusCode());
			return new SingleResponseModel() {{setMessage("error");}};
		}
		if(res.getMessage().equals("error")) {
			return new SingleResponseModel() {{setMessage("error");}};
		}
		
		return new SingleResponseModel() {{setMessage("success");}};
	}
	
	@RequestMapping("/submitmarks")
	@ResponseBody
	public SingleResponseModel submitmarks(@RequestBody MarksModel[] marks)
	{
		String statusUrl = appGateway.getBaseUrl()+"/api/evaluation/getevaluationstatus/"+marks[0].getAssessmentid()+"/"+marks[0].getStudentid();
		StatusModel evalStatus = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<StatusModel> statReq = new HttpEntity<StatusModel>(httpHeaders);
		ResponseEntity<StatusModel> statRes = restTemplate.exchange(statusUrl, HttpMethod.GET, statReq, StatusModel.class);
		if(statRes.getStatusCode() == HttpStatus.OK)
		{
			evalStatus = statRes.getBody();
		}
		else
		{
			return new SingleResponseModel() {{setMessage("error");}};
		}
		
		if(evalStatus.getStatus() != null)
		if(evalStatus.getStatus().equals("S"))
		{
			return new SingleResponseModel() {{setMessage("FROZEN");}};
		}
		
		SingleResponseModel[] responses = null;
		HttpEntity<MarksModel[]> request = new HttpEntity<MarksModel[]>(marks,httpHeaders);
		String url1 = appGateway.getBaseUrl() + "/api/assessment/setallmarks";
		ResponseEntity<SingleResponseModel[]> response = restTemplate.exchange(url1, HttpMethod.POST, request,SingleResponseModel[].class);
		if(response.getStatusCode() == HttpStatus.OK)
		{
			responses = response.getBody();
		}
		else
		{
			System.out.println(response.getStatusCode());
			return new SingleResponseModel() {{setMessage("error");}};
		}
		
		SingleResponseModel res = null;
		StatusModel status = new StatusModel(0, marks[0].getAssessmentid(), marks[0].getStudentid(), "S");
		String url2 = appGateway.getBaseUrl() + "/api/evaluation/setevaluationstatus";		
		HttpEntity<StatusModel> request2 = new HttpEntity<StatusModel>(status,httpHeaders);
		
		ResponseEntity<SingleResponseModel> response2 = restTemplate.exchange(url2, HttpMethod.POST, request2, SingleResponseModel.class);
		if(response2.getStatusCode() == HttpStatus.OK)
		{
			res = response2.getBody();
		}
		else
		{
			System.out.println(response2.getStatusCode());
			return new SingleResponseModel() {{setMessage("error");}};
		}
		if(res.getMessage().equals("error")) {
			return new SingleResponseModel() {{setMessage("error");}};
		}
		
		return new SingleResponseModel() {{setMessage("success");}};
	}
	
	@RequestMapping("/getResultList/{assessid}/{crseid}/{btchid}")
	@ResponseBody
	//public AssessResultList[] getResultList(@PathVariable("assessid")String assessid, @PathVariable("crseid")String crseid, @PathVariable("btchid")String btchid)
	public List<AssessResultList> getResultList(@PathVariable("assessid")String assessid, @PathVariable("crseid")String crseid, @PathVariable("btchid")String btchid)
	{
		SingleResponseModel total = null;
		AssessResultList[] assessResult = null;
		String url = appGateway.getBaseUrl() + "/api/evaluation/getresultlist/"+assessid+"/"+crseid+"/"+btchid;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
		ResponseEntity<AssessResultList[]> response = restTemplate.exchange(url, HttpMethod.GET, request,AssessResultList[].class);
		if(response.getStatusCode() == HttpStatus.OK){
			assessResult = response.getBody();
		}else{
			System.out.println("Request Failed");
		}
		
		String totalUrl = appGateway.getBaseUrl()+"/api/evaluation/getAssessmentTotalMarks/"+assessid;
		HttpEntity<String> rq = new HttpEntity<>(httpHeaders);
		ResponseEntity<SingleResponseModel> rs = restTemplate.exchange(totalUrl, HttpMethod.GET, rq, SingleResponseModel.class);
		total = rs.getBody();
		
		List<AssessResultList> finalResult = new ArrayList<>();
		
		/*****************************************/
		String url2 = appGateway.getBaseUrl() + "/api/batch/getbatchdetailsbyid/"+btchid;
        BatchDetailsModel batch = null;
        ResponseEntity<BatchDetailsModel> response2= restTemplate.exchange(url2 ,HttpMethod.GET,request, BatchDetailsModel.class);
        if(response2.getStatusCode() == HttpStatus.OK)
        {
        	batch = response2.getBody();
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response2.getStatusCode());
        }
        /*******/
        for(Student x : batch.getStudents())
        {
        	boolean flag = true;
        	for(int j = 0; j<assessResult.length; j++)
        	{
        		if( (""+x.getStudentid()).equals(assessResult[j].getStudentid()))
        		{
        			assessResult[j].setAttendance("Y");
        			assessResult[j].setSubmission("Y");
        			finalResult.add( assessResult[j] );
        			flag = false;
        			break;
        		}
        	}
        	if(flag == true)
        	{
        		AssessResultList temp = new AssessResultList();
	    		temp.setStudentid(""+x.getStudentid());
	    		temp.setName(x.getFirstname());
	    		temp.setEmplid(x.getEmplid());
	    		temp.setAttendance("N");
	    		temp.setSubmission("N");
	    		temp.setCampus_id(x.getCampusid());
	    		temp.setTotalmarks(total.getMessage());
	    		temp.setMarks("0.0");
	    		temp.setStatus("X");
	    		
	    		finalResult.add(temp);
        	}
        }
		/******************************************/
        
        return finalResult;
		//return assessResult;
	}
}