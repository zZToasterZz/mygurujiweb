package in.co.srdt.myguruji.controllers;

import java.util.Arrays;

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
import in.co.srdt.myguruji.model.Batch;
import in.co.srdt.myguruji.model.BatchDetailsModel;
import in.co.srdt.myguruji.model.CommonModelReport;
import in.co.srdt.myguruji.model.Course;
import in.co.srdt.myguruji.model.DoubleResponseModel;
import in.co.srdt.myguruji.model.FacultyInfo;
import in.co.srdt.myguruji.model.GradebookPSMarksModel;
import in.co.srdt.myguruji.model.GradebookResponseModel;
import in.co.srdt.myguruji.model.GradebookSaveMarks;
import in.co.srdt.myguruji.model.Login;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/gradebook")
public class GradebookController 
{	
	@Autowired
    RestTemplate restTemplate;
	
    @Autowired
    private ApplicationGateway appGateway;
    
    @Autowired
	GetEmplId getEmplID;//=new GetEmplId;

    @RequestMapping("/openGradebook")
    public String openGradebook(HttpServletRequest request, Model model, Authentication authentication)
    {
    	String emplid=getEmplID.getLogedinUserEmplid(authentication.getName());
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
            System.out.println("Request Failed: "+response.getStatusCode());
            
        }

        model.addAttribute("course",Arrays.asList(courses));
        model.addAttribute("createdby",emplid);
        
    	return "forms/gradebook/manageGradebook.html :: manageGradebook";
    }
    
    @RequestMapping("/getTypes/{batchid}")
    @ResponseBody
    public CommonModelReport[] getTypes(Authentication authentication, HttpServletRequest request, Model model
    		, @PathVariable("batchid") String batchid)
    {
    	CommonModelReport[] resp=null;
    	
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/gradebook/assessmentAssignmentType/" + batchid;
        //String url ="http://localhost:9111/api/gradebook/assessmentAssignmentType/" + batchid;
        
        ResponseEntity<CommonModelReport[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, CommonModelReport[].class);

        if(response.getStatusCode() == HttpStatus.OK) 
        {
            resp = response.getBody();            
        } else {
            System.out.println("Request Failed: "+response.getStatusCode());
            
        }

        return resp;
    }
    
    @RequestMapping("/getAssignments/{batchid}")
    @ResponseBody
    public String[] getAssignments(Authentication authentication, HttpServletRequest request, Model model
    		, @PathVariable("batchid") String batchid)
    {
    	String[] resp=null;
    	
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/gradebook/assessmentAssignmentTitleList/" + batchid;
        //String url ="http://localhost:9120/api/gradebook/assessmentAssignmentTitleList/" + batchid;
        
        ResponseEntity<String[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, String[].class);

        if(response.getStatusCode() == HttpStatus.OK) 
        {
            resp = response.getBody();            
        } else 
        {
            System.out.println("Request Failed: "+response.getStatusCode());
            
        }
        
    	return resp;
    }
    
    @RequestMapping("/getMarks/{id}/{type}")
    @ResponseBody
    public DoubleResponseModel[] getMarks(Authentication authentication, HttpServletRequest request, Model model
    		, @PathVariable("id") String id, @PathVariable("type") String type)
    {
    	DoubleResponseModel[] resp=null;
    	
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/gradebook/getMarksById/" + id +"/"+type;        
        //String url ="http://10.8.20.35:9120/api/gradebook/getMarksById/" + id +"/"+type;
        
        ResponseEntity<DoubleResponseModel[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, DoubleResponseModel[].class);

        if(response.getStatusCode() == HttpStatus.OK) 
        {
            resp = response.getBody();            
        } else 
        {
            System.out.println("Request Failed: "+response.getStatusCode());
            
        }
        
    	return resp;
    }
    
    @RequestMapping("/saveMarks")
    @ResponseBody
    public SingleResponseModel saveMarks(@RequestBody GradebookSaveMarks[] mrks)
    {
    	SingleResponseModel resp=null;
    	
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GradebookSaveMarks[]> request1 = new HttpEntity<GradebookSaveMarks[]>(mrks,httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/gradebook/addgrdebookmarks";
        //String url ="http://localhost:9120/api/gradebook/addgrdebookmarks";
        ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url ,HttpMethod.POST,request1, SingleResponseModel.class);
        if(response.getStatusCode() == HttpStatus.OK) 
        {
            resp = response.getBody();            
        } else 
        {
            System.out.println("Request Failed: "+response.getStatusCode());
            
        }
        
    	return resp;
    }
    
    /************** SEND DATA IN PS, TO BE USED LATER **********/
    /*@RequestMapping("/pushMarksInPS/{class_nbr}")
    @ResponseBody
    public SingleResponseModel pushMarksInPS(@PathVariable("class_nbr") String class_nbr ,HttpServletRequest request)
    {
    	GradebookPSMarksModel[] mrks = null;    	
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<String> req1 = new HttpEntity<String>(headers);
    	String url = appGateway.getBaseUrl() + "/api/gradebook/getAllGradebookPSMarksListClassnbr/"+class_nbr;
        //String url ="http://10.8.20.35:9111/api/gradebook/getAllGradebookPSMarksListClassnbr/"+class_nbr;
        ResponseEntity<GradebookPSMarksModel[]> res= restTemplate.exchange(url ,HttpMethod.GET,req1, GradebookPSMarksModel[].class);
        if(res.getStatusCode() == HttpStatus.OK) 
        {
        	mrks = res.getBody();
        } else 
        {
            System.out.println("Request Failed: "+res.getStatusCode());           
        }
        
        int i=0;
    	//String psurl ="http://10.8.20.57:8015/PSIGW/RESTListeningConnector/PSFT_CS/SRM_ERP_GRADE_SYNC_SO.v1/SRM_ERP_GRADE_REQ";
        String psurl ="http://103.245.34.253:37520/PSIGW/RESTListeningConnector/PSFT_CS/SRM_ERP_GRADE_SYNC_SO.v1/SRM_ERP_GRADE_REQ";
        for(GradebookPSMarksModel x : mrks)
	    {
        	String json="{\r\n"
        			+ "    \"SRM_ERP_GRADE_REQ\": {\r\n"
        			+ "        \"emplid\": \""+x.getEmplid()+"\",\r\n"
        			+ "        \"class_nbr\": \""+x.getClass_nbr()+"\",\r\n"
        			+ "        \"strm\": \""+x.getStrm()+"\",\r\n"
        			+ "        \"lam_type\": \""+x.getLam_type()+"\",\r\n"
        			+ "        \"descrshort\": \""+x.getDescrshort()+"\",\r\n"
        			+ "        \"sequence_no\": "+x.getSequence_no()+",\r\n"
        			+ "        \"mark_out_of\": "+x.getMarks_out_of()+",\r\n"
        			+ "        \"student_grade\": "+x.getStudent_grade()+",\r\n"
        			+ "        \"instructor_id\": \""+x.getInstructor_id()+"\"\r\n"
        			+ "    }\r\n"
        			+ "}";
        	HttpEntity<String> request1 = new HttpEntity<String>(json,headers);	        
	        ResponseEntity<GradebookSaveMarks> response= restTemplate.exchange(psurl ,HttpMethod.POST,request1, GradebookSaveMarks.class);
        	if(response.getStatusCode() == HttpStatus.OK) 
        	{
        		String upurl =appGateway.getBaseUrl() + "/api/gradebook/updatePushStatusById/"+x.getGradebookpsmarksId();
        		//String upurl ="http://10.8.20.35:9111/api/gradebook/updatePushStatusById/"+x.getGradebookpsmarksId();
                ResponseEntity<SingleResponseModel> resp= restTemplate.exchange(upurl ,HttpMethod.GET,req1, SingleResponseModel.class);
                if(resp.getStatusCode() == HttpStatus.OK) 
                {
                	i++;
                } else 
                {
                    System.out.println("Request Failed: "+res.getStatusCode());           
                }        		
	        } else 
	        {
	            System.out.println("Request Failed: "+response.getStatusCode());	           
	        }
	    }
        if(i==mrks.length)
    	{
        	String u = appGateway.getBaseUrl() + "/api/gradebook/updatePushStatus/"+class_nbr;
        	//String u ="http://10.8.20.35:9111/api/gradebook/updatePushStatus/"+class_nbr;
            ResponseEntity<SingleResponseModel> r= restTemplate.exchange(u ,HttpMethod.GET,req1, SingleResponseModel.class);
            if(r.getStatusCode() == HttpStatus.OK) 
            {
            	return new SingleResponseModel("Success");
            } else 
            {
                System.out.println("Request Failed: "+res.getStatusCode());           
            }    		
    	}
        return new SingleResponseModel("Error");    	
    }*/
    
    @RequestMapping("/pushMarksInPS")
    @ResponseBody
    public SingleResponseModel pushMarksInPS(HttpServletRequest request)
    {
    	GradebookPSMarksModel[] mrks = null;    	
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<String> req1 = new HttpEntity<String>(httpHeaders);
    	String url = appGateway.getBaseUrl() + "/api/gradebook/getAllGradebookPSMarksList";
        //String url ="http://10.8.20.35:9111/api/gradebook/getAllGradebookPSMarksList";
        ResponseEntity<GradebookPSMarksModel[]> res= restTemplate.exchange(url ,HttpMethod.GET,req1, GradebookPSMarksModel[].class);
        if(res.getStatusCode() == HttpStatus.OK) 
        {
        	mrks = res.getBody();
        } else 
        {
            System.out.println("Request Failed: "+res.getStatusCode());           
        }
        int i=0;int chk=1;
    	//String psurl ="http://10.8.20.57:8015/PSIGW/RESTListeningConnector/PSFT_CS/SRM_ERP_GRADE_SYNC_SO.v1/SRM_ERP_GRADE_REQ";
        String psurl ="http://103.245.34.253:37520/PSIGW/RESTListeningConnector/PSFT_CS/SRM_ERP_GRADE_SYNC_SO.v1/SRM_ERP_GRADE_REQ";
        for(GradebookPSMarksModel x : mrks)
	    {
        	chk++;
        	//System.out.println(x.toString());
        	String json="{\r\n"
        			+ "    \"SRM_ERP_GRADE_REQ\": {\r\n"
        			+ "        \"emplid\": \""+x.getEmplid()+"\",\r\n"
        			+ "        \"class_nbr\": \""+x.getClass_nbr()+"\",\r\n"
        			+ "        \"strm\": \""+x.getStrm()+"\",\r\n"
        			+ "        \"lam_type\": \""+x.getLam_type()+"\",\r\n"
        			+ "        \"descrshort\": \""+x.getDescrshort()+"\",\r\n"
        			+ "        \"sequence_no\": "+x.getSequence_no()+",\r\n"
        			+ "        \"mark_out_of\": "+x.getMarks_out_of()+",\r\n"
        			+ "        \"student_grade\": "+x.getStudent_grade()+",\r\n"
        			+ "        \"instructor_id\": \""+x.getInstructor_id()+"\"\r\n"
        			+ "    }\r\n"
        			+ "}";
        	HttpEntity<String> request1 = new HttpEntity<String>(json,httpHeaders);	        
	        ResponseEntity<GradebookSaveMarks> response= restTemplate.exchange(psurl ,HttpMethod.POST,request1, GradebookSaveMarks.class);
        	if(response.getStatusCode() == HttpStatus.OK) 
        	{
        		String upurl = appGateway.getBaseUrl() + "/api/gradebook/updatePushStatusById/"+x.getGradebookpsmarksId();
        		//String upurl ="http://10.8.20.35:9111/api/gradebook/updatePushStatusById/"+x.getGradebookpsmarksId();
                ResponseEntity<SingleResponseModel> resp= restTemplate.exchange(upurl ,HttpMethod.GET,req1, SingleResponseModel.class);
                if(resp.getStatusCode() == HttpStatus.OK) 
                {
                	//System.out.println("UPDATE SUCCESS::::"+x.getGradebookpsmarksId()+":::::::::"+new Timestamp(System.currentTimeMillis()));
                	i++;
                } else 
                {
                    System.out.println("Request Failed: "+res.getStatusCode());           
                }
	        } else 
	        {
	            System.out.println("Request Failed: "+response.getStatusCode());	           
	        }
        	if(chk==10000)
        	{	
        		chk=1;
        		break;
        	}
	    }
        if(i==mrks.length)
    	{
    		return new SingleResponseModel("Success");
    	}
        return new SingleResponseModel("Error");    	
    }
    
    @RequestMapping("/pushMarks")
    @ResponseBody
    public SingleResponseModel pushMarks(@RequestBody GradebookSaveMarks[] mrks,HttpServletRequest request)
    {
    	SingleResponseModel resp=null;
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<GradebookSaveMarks[]> request1 = new HttpEntity<GradebookSaveMarks[]>(mrks,httpHeaders);
		String url = appGateway.getBaseUrl() + "/api/gradebook/addGradebookPSMarksforPS";
        //String url ="http://10.8.20.35:9111/api/gradebook/addGradebookPSMarksforPS";
        ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url ,HttpMethod.POST,request1, SingleResponseModel.class);
    	if(response.getStatusCode() == HttpStatus.OK) 
        {
            resp = response.getBody();
        } else 
        {
            System.out.println("Request Failed: "+response.getStatusCode());
        }       	
	 	
 		return resp;    	    	
    }
    
    @RequestMapping("/submitMarks/{courseid}/{batchid}")
    @ResponseBody
    public SingleResponseModel submitMarks(HttpServletRequest request
    		, @PathVariable("courseid") String courseid
    		, @PathVariable("batchid") String batchid)
    {
    	SingleResponseModel resp=null;
    	Login login=(Login) request.getSession().getAttribute("login");
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    	DoubleResponseModel reqbody=new DoubleResponseModel(batchid+":"+login.getEmplid(),courseid);
        HttpEntity<DoubleResponseModel> request1 = new HttpEntity<DoubleResponseModel>(reqbody,httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/gradebook/freezeGradeBookStatus";
        //String url ="http://localhost:9111/api/gradebook/freezeGradeBookStatus";
        ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url ,HttpMethod.POST,request1, SingleResponseModel.class);
        if(response.getStatusCode() == HttpStatus.OK) 
        {
            resp = response.getBody();            
        } else 
        {
        	System.out.println("Request Failed: "+response.getStatusCode());
        }
        
    	return resp;
    }
    
    @RequestMapping("/getStudentMarks/{courseid}/{batchid}")
    @ResponseBody
    public GradebookResponseModel[] getStudentMarks(@PathVariable("courseid") String courseid
    		, @PathVariable("batchid") String batchid)
    {
    	GradebookResponseModel[] resp=null;
    	
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/gradebook/getGradebookList/" + batchid +"/"+courseid;
        //String url ="http://10.8.20.35:9111/api/gradebook/getGradebookList/" + batchid +"/"+courseid;
        
        ResponseEntity<GradebookResponseModel[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, GradebookResponseModel[].class);
        
        if(response.getStatusCode() == HttpStatus.OK) 
        {
        	resp = response.getBody();
        } else 
        {
        	System.out.println("Request Failed: "+response.getStatusCode());
        }
        
    	return resp;
    }
    
    @RequestMapping("/getStudentAvgMarks/{batchid}")
    @ResponseBody
    public String getStudentAvgMarks(@PathVariable("batchid") String batchid)
    {
    	String resp=null;
    	
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/gradebook/getEmplidandMarksList/" + batchid;
        //String url ="http://10.8.20.35:9120/api/gradebook/getEmplidandMarksList/" + batchid;
        
        ResponseEntity<String> response= restTemplate.exchange(url ,HttpMethod.GET,request1, String.class);

        if(response.getStatusCode() == HttpStatus.OK) 
        {
            resp = response.getBody();   
        } else 
        {
        	System.out.println("Request Failed: "+response.getStatusCode());
        }
    	
    	return resp;
    }
    
    @RequestMapping("/getValidBatches")
    @ResponseBody
    public long[] getBatchById(HttpServletRequest request, Authentication authentication)
    {
    	String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
    	FacultyInfo facinfo=null;
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url =appGateway.getBaseUrl() + "/api/faculty/getFacultydetailsByEmplId/"+emplid;        
        ResponseEntity<FacultyInfo> response= restTemplate.exchange(url ,HttpMethod.GET,request1, FacultyInfo.class);

        if(response.getStatusCode() == HttpStatus.OK) 
        {
        	facinfo=response.getBody();        	
        } else 
        {
        	System.out.println("Request Failed: "+response.getStatusCode());
        }
    	long[] batches=new long[facinfo.getBatches().size()];
    	int i=0;
        for(Batch x : facinfo.getBatches())
        {
        	batches[i++]=x.getBatchid();        	
        }        
    	return batches;
    }
    
    @RequestMapping("/printGradebook/{courseid}/{batchid}")
    public String printGradebook(@PathVariable("courseid") String courseid, @PathVariable("batchid") String batchid
    		,Model model,HttpServletRequest request, Authentication authentication)
    {
    	String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
    	FacultyInfo facinfo=null;
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        
        String url =appGateway.getBaseUrl() + "/api/faculty/getFacultydetailsByEmplId/"+emplid;
        ResponseEntity<FacultyInfo> response= restTemplate.exchange(url ,HttpMethod.GET,request1, FacultyInfo.class);
        if(response.getStatusCode() == HttpStatus.OK) 
        {
        	facinfo=response.getBody();        	
        } else 
        {
        	System.out.println("Request Failed: "+response.getStatusCode());
        }
    	String name=facinfo.getFaculty().getFirstname().trim();
        name+=facinfo.getFaculty().getMiddlename()==null?"":" "+facinfo.getFaculty().getMiddlename();
        name+=facinfo.getFaculty().getLastname()==null?"":" "+facinfo.getFaculty().getLastname();
        model.addAttribute("name",name);
        model.addAttribute("erpid",facinfo.getFaculty().getEmplid());
        
        Course resp1=null;
        url =appGateway.getBaseUrl() + "/api/course/getcourse/"+courseid;        
        ResponseEntity<Course> response1= restTemplate.exchange(url ,HttpMethod.GET,request1, Course.class);
        if(response.getStatusCode() == HttpStatus.OK) 
        {
        	resp1=response1.getBody();        	
        } else 
        {
        	System.out.println("Request Failed: "+response.getStatusCode());
        }
    	model.addAttribute("coursedescr",resp1.getTitle());
        model.addAttribute("coursecode",resp1.getCode());
        model.addAttribute("courseid",resp1.getId());
        
        BatchDetailsModel resp2=null;
        url =appGateway.getBaseUrl() + "/api/batch/getbatchdetailsbyid/"+batchid;        
        ResponseEntity<BatchDetailsModel> response2= restTemplate.exchange(url ,HttpMethod.GET,request1, BatchDetailsModel.class);
        if(response.getStatusCode() == HttpStatus.OK) 
        {
        	resp2=response2.getBody();        	
        } else 
        {
        	System.out.println("Request Failed: "+response.getStatusCode());
        }
    	model.addAttribute("catalog",resp2.getBatch().getTitle());
        model.addAttribute("batchcode",resp2.getBatch().getBatchcode());
        model.addAttribute("batchtype",resp2.getBatch().getType());
        model.addAttribute("batchid",resp2.getBatch().getBatchid());
        
    	return "forms/gradebook/printGradebook.html";
    }
}
