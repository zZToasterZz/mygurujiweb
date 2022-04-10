package in.co.srdt.myguruji.controllers;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.BatchesUnmappedGET;
import in.co.srdt.myguruji.model.coursePlan.BatchByCourseIdGET;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.CourseObjGET;
import in.co.srdt.myguruji.model.coursePlan.CoursePlanDetailsGet;
import in.co.srdt.myguruji.model.coursePlan.CoursePlanSearch;
import in.co.srdt.myguruji.model.coursePlan.CoursePlanUnitDetails;
import in.co.srdt.myguruji.model.coursePlan.CoursePlanWrapper;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.model.coursePlan.SubunitModel;
import in.co.srdt.myguruji.model.coursePlan.SubunitPost;
import in.co.srdt.myguruji.model.coursePlan.TopicsFetchModel;
import in.co.srdt.myguruji.model.coursePlan.TopicsModel;
import in.co.srdt.myguruji.model.coursePlan.UnitsGET;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/administration")
public class CoursePlanController
{
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ApplicationGateway appGateway;

    @Autowired
	GetEmplId getEmplID;//=new GetEmplId;
    
    @GetMapping("/manageCoursePlan")
    public String manageCoursePlan(Authentication authentication, HttpServletRequest request, Model model)
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

        model.addAttribute("course",Arrays.asList(courses));
        model.addAttribute("createdby",emplid);

        return "forms/coursePlan/manageCoursePlan :: manageCoursePlan";
    }

    @GetMapping("/editcourseplan/{planid}")
    public String editCoursePlan(@PathVariable("planid") String id,Model model, HttpServletRequest request
    		,Authentication authentication)
    {
        String cloneFlag = "N";
        if( id.endsWith("_CLONEPLAN") )
        {
            id = id.replace("_CLONEPLAN", "");
            cloneFlag = "Y";
        }

        String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());

        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);

        String url = appGateway.getBaseUrl() + "/api/courseplan/getcourseplandetails/"+id;

        CoursePlanDetailsGet[] coursePlans = null;

        ResponseEntity<CoursePlanDetailsGet[]> response1= restTemplate.exchange(url,HttpMethod.GET,request1, CoursePlanDetailsGet[].class);
        if(response1.getStatusCode() == HttpStatus.OK) {
            coursePlans = response1.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response1.getStatusCode());
        }
        
        CourseModel[] courses = null;
        String url2 = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
        ResponseEntity<CourseModel[]> response= restTemplate.exchange(url2, HttpMethod.GET, request1, CourseModel[].class);
        if(response.getStatusCode() == HttpStatus.OK) {
            courses = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }


        //GET ALL TOPICS FOR COURSEID
        String topicFetch ="{\"courseid\":"+coursePlans[0].getCourseid()+","+"\"topicids\":[]}";
        TopicsModel[] topics = null;

        HttpEntity<String> request3 = new HttpEntity<String>(topicFetch, httpHeaders);
        String url3 = appGateway.getBaseUrl() + "/api/topic/findtopicsbycourseidandtopicid";
        ResponseEntity<TopicsModel[]> response3= restTemplate.exchange(url3,HttpMethod.POST,request3, TopicsModel[].class);
        if(response3.getStatusCode() == HttpStatus.OK) {
            topics = response3.getBody();
        } else {
            System.out.println("Request Failed HERE");
            System.out.println(response3.getStatusCode());
        }

        //GET ALL OBJECTIVES FOR COURSEID
        CourseObjGET[] objectives = null;
        HttpEntity<String> request4 = new HttpEntity<String>(httpHeaders);
        //String url4 = appGateway.getBaseUrl() + "/api/course/getcourseobjectivebycourseid/"+coursePlans[0].getCourseid();
        String url4 = appGateway.getBaseUrl() + "/api/courseoutcome/getcourseoutcomebycourseid/"+coursePlans[0].getCourseid();
        ResponseEntity<CourseObjGET[]> response4= restTemplate.exchange(url4,HttpMethod.GET,request4, CourseObjGET[].class);
        if(response4.getStatusCode() == HttpStatus.OK) {
            objectives = response4.getBody();
        } else {
            System.out.println("Request Failed HERE");
            System.out.println(response4.getStatusCode());
        }

        //CREATE USED TOPICS ARRAY
        List<CoursePlanUnitDetails> units = coursePlans[0].getUnits();
        String usedTopics = "";
        String usedObjectives = "";

        if(units.size() > 0)
        {
            for(CoursePlanUnitDetails x : units)
            {
                usedTopics += ","+x.getTopicsid();
                usedObjectives += ","+x.getObjectivesid();
            }
        }
        else
        {
            System.out.println("NO UNITS ERROR");
        }
        usedTopics = usedTopics.substring(1);
        usedObjectives = usedObjectives.substring(1);

        model.addAttribute("CLONE_FLAG",cloneFlag);
        model.addAttribute("topics", topics);
        model.addAttribute("objectives", objectives);
        model.addAttribute("usedTopics",usedTopics);
        model.addAttribute("usedObjectives",usedObjectives);
        model.addAttribute("course",Arrays.asList(courses));
        model.addAttribute("courseplan",coursePlans[0]);
        model.addAttribute("createdby",emplid);

        return "forms/coursePlan/editCoursePlan :: editCoursePlan";
    }

    @ResponseBody
    @RequestMapping(value = "/managecourseplan/getclourseplans", method = RequestMethod.POST)
    public CoursePlanSearch[] getAllCoursePlan(HttpServletRequest searchCourseData, Authentication authentication)
    {
        CoursePlanSearch[] coursePlan = null;
        
        String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
        
        try
        {
            String payLoad = "{" +
                    "\"courseid\": \""+searchCourseData.getParameter("courseid")+"\"," +
                    "\"plancode\": \""+searchCourseData.getParameter("coursecode")+"\"," +
                    "\"plantitle\": \""+searchCourseData.getParameter("coursetitle")+"\"," +
                    "\"emplid\": \""+emplid+"\"" +
                    "}";

            HttpHeaders httpHeaders = new HttpHeaders();
    		httpHeaders.add("Authorization", AccessToken.getAccessToken());
    		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(payLoad, httpHeaders);

            String url = appGateway.getBaseUrl() + "/api/courseplan/search";

            ResponseEntity<CoursePlanSearch[]> response= restTemplate.exchange(url,HttpMethod.POST,request, CoursePlanSearch[].class);
            if(response.getStatusCode() == HttpStatus.OK) {
                coursePlan = response.getBody();
            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return coursePlan;
    }

    @GetMapping("/createcourseplan")
    public String createCoursePlan(HttpServletRequest request1, Model model, Authentication authentication)
    {
        CourseModel[] courses = null;

        String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
        String url = appGateway.getBaseUrl() + "/api/course/getcoursebyemplid/" + emplid;
        
        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>("", httpHeaders);

        ResponseEntity<CourseModel[]> response= restTemplate.exchange(url,HttpMethod.GET,request, CourseModel[].class);
        if(response.getStatusCode() == HttpStatus.OK) {
            courses = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        model.addAttribute("course",Arrays.asList(courses));
        model.addAttribute("createdby",emplid);

        return "forms/coursePlan/createCoursePlan :: createCoursePlan";
    }

    @GetMapping("/getBatchById/{id}")
    @ResponseBody
    public BatchByCourseIdGET[] getBatchById(@PathVariable("id") String id)
    {
        //System.out.println(id);

        BatchByCourseIdGET[] batches = null;

        String url = appGateway.getBaseUrl() + "/api/batch/getbatchbycourseid/"+id;
        //String batchURL = "http://192.200.12.77:81/api/batch/getbatchbycourseid/"+id;

        //headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setBearerAuth(BearerTokenTemporary.token);
        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>("", httpHeaders);

        ResponseEntity<BatchByCourseIdGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, BatchByCourseIdGET[].class);
        if(response.getStatusCode() == HttpStatus.OK) {
            batches = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        return batches;
    }

    @RequestMapping(value = "/createPlan", method = RequestMethod.POST,consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody String createPlan(CoursePlanWrapper coursePlanWrapper)
    {
    	String dirtyDescr = coursePlanWrapper.getCourseplandescr();
    	dirtyDescr = dirtyDescr.replaceAll("[^a-zA-Z0-9. ,;:@!]", "");
    	dirtyDescr = dirtyDescr.replaceAll(" & ", " and ");
    	dirtyDescr = dirtyDescr.replaceAll("&", " and ");
    	coursePlanWrapper.setCourseplandescr(dirtyDescr);
    	
        SingleResponseModel res = new SingleResponseModel();
        try
        {
        	HttpHeaders httpHeaders = new HttpHeaders();
    		httpHeaders.add("Authorization", AccessToken.getAccessToken());
    		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CoursePlanWrapper> request = new HttpEntity<CoursePlanWrapper>(coursePlanWrapper, httpHeaders);

            String url = appGateway.getBaseUrl() + "/api/courseplan/create";

            ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url,HttpMethod.POST,request, SingleResponseModel.class);
            if(response.getStatusCode() == HttpStatus.OK)
            {
                res = response.getBody();
            }
            else
            {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
                res.setMessage("Succcess : 0");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            res.setMessage("Succcess : 0");
        }
        return "Data Posted \n" + res.getMessage();
    }

    @ResponseBody
    @RequestMapping(value = "/updatePlan", method = RequestMethod.POST,consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updatePlan(@ModelAttribute("createCoursePlan") CoursePlanWrapper coursePlanWrapper)
    {
    	String dirtyDescr = coursePlanWrapper.getCourseplandescr();
    	dirtyDescr = dirtyDescr.replaceAll("[^a-zA-Z0-9. ,;:@!]", "");
    	dirtyDescr = dirtyDescr.replaceAll(" & ", " and ");
    	dirtyDescr = dirtyDescr.replaceAll("&", " and ");
    	coursePlanWrapper.setCourseplandescr(dirtyDescr);
    	
        SingleResponseModel res = new SingleResponseModel();
        try {
        	HttpHeaders httpHeaders = new HttpHeaders();
    		httpHeaders.add("Authorization", AccessToken.getAccessToken());
    		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CoursePlanWrapper> request = new HttpEntity<CoursePlanWrapper>(coursePlanWrapper, httpHeaders);

            String url = appGateway.getBaseUrl() + "/api/courseplan/update";

            ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url,HttpMethod.POST,request, SingleResponseModel.class);
            if(response.getStatusCode() == HttpStatus.OK)
            {
                res = response.getBody();
                //System.out.println(res.getMessage());
            }
            else
            {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
                return "ERROR";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "ERROR";
        }
        return "Data Posted \n" + res.getMessage();
    }

    @GetMapping("/manageCoursePlan/addsubunits/{id}")
    public String addSubUnits(@PathVariable("id") String id, Model model, HttpServletRequest req
    		, Authentication authentication)
    {
    	String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());

        id = id.trim();
        UnitsGET[] units = null;
        //String url = "http://192.200.12.77:81/api/unit/getunitsbyplanid/"+id;

        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>("", httpHeaders);

        String url = appGateway.getBaseUrl() + "/api/unit/getunitsbyplanid/"+id;

        ResponseEntity<UnitsGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, UnitsGET[].class);
        if(response.getStatusCode() == HttpStatus.OK) {
            units = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        model.addAttribute("planid",id);
        model.addAttribute("units", units);
        model.addAttribute("createdby", emplid);
        return "forms/coursePlan/createSubUnits :: createSubUnits";
    }

    @GetMapping("/manageCoursePlan/editsubunits/{planid}/{courseid}")
    public String editSubUnits(@PathVariable("planid") String id,@PathVariable("courseid") String courseid, Model model
    		, HttpServletRequest req, Authentication authentication)
    {
    	String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());

        id = id.trim();
        UnitsGET[] units = null;
        
        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>("", httpHeaders);

        String url = appGateway.getBaseUrl() + "/api/unit/getunitsbyplanid/"+id;

        ResponseEntity<UnitsGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, UnitsGET[].class);
        if(response.getStatusCode() == HttpStatus.OK) {
            units = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        model.addAttribute("planid",id);
        model.addAttribute("courseid", courseid.trim());
        model.addAttribute("units", units);
        model.addAttribute("createdby", emplid);
        return "forms/coursePlan/editSubUnits :: editSubUnits";
    }

    @ResponseBody
    @RequestMapping("/manageCoursePlan/fetchTopics")
    public TopicsModel[] fetchTopics(@RequestBody TopicsFetchModel fetchRequest)
    {
        TopicsModel[] topics = null;

        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TopicsFetchModel> request = new HttpEntity<TopicsFetchModel>(fetchRequest, httpHeaders);

        String url = appGateway.getBaseUrl() + "/api/topic/findtopicsbycourseidandtopicid";

        ResponseEntity<TopicsModel[]> response= restTemplate.exchange(url,HttpMethod.POST,request, TopicsModel[].class);
        if(response.getStatusCode() == HttpStatus.OK) {
            topics = response.getBody();
        } else {
            System.out.println("Request Failed HERE");
            System.out.println(response.getStatusCode());
        }

        return topics;
    }

    @ResponseBody
    @RequestMapping("/manageCoursePlan/fetchObjectives/{id}")
    public CourseObjGET[] fetchObjectives(@PathVariable("id") String courseid)
    {
        CourseObjGET[] objectives = null;
        
        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(httpHeaders);

        //String url = appGateway.getBaseUrl() + "/api/course/getcourseobjectivebycourseid/"+courseid;
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
    @RequestMapping(value = "/manageCoursePlan/addsubunits/createsubunits", method = RequestMethod.POST,consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createSubUnits(@ModelAttribute("subunits") SubunitPost subunit)
    {
        SingleResponseModel res = new SingleResponseModel();

        String arrS="[";
        String arrE="]";
        String data = "";
        for(SubunitModel x : subunit.getSubunit())
        {
            data += "{" +
                    "\"subunittitle\":\""+x.getSubunittitle()+"\"," +
                    "\"subunitdescr\":\""+x.getSubunitdescr()+"\"," +
                    "\"unitid\":"+x.getUnitid()+"," +
                    "\"createdby\":\""+x.getCreatedby()+"\"" +
                    "},";
        }

        data = arrS+data.substring(0,data.length()-1)+arrE;

        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(data, httpHeaders);

        String url = appGateway.getBaseUrl() + "/api/subunit/create";

        ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url,HttpMethod.POST,request, SingleResponseModel.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            res = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        return res.getMessage();
    }

    @ResponseBody
    @RequestMapping("/deletePlan/{id}")
    public SingleResponseModel deletePlan(@PathVariable("id") String planid)
    {
        SingleResponseModel res = new SingleResponseModel();

        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(httpHeaders);

        String url = appGateway.getBaseUrl() + "/api/courseplan/remove/"+planid;

        ResponseEntity<SingleResponseModel> response= restTemplate.exchange(url,HttpMethod.GET,request, SingleResponseModel.class);
        if(response.getStatusCode() == HttpStatus.OK)
        {
            res = response.getBody();
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        return res;
    }

    @ResponseBody
    @RequestMapping("/getUnmappedBatches/{id}")
    public BatchesUnmappedGET[] getUnmappedBatches(@PathVariable("id") String courseid, HttpServletRequest req
    		,Authentication authentication)
    {
        BatchesUnmappedGET[] res = null;

        String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());

        HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(httpHeaders);

        String url = appGateway.getBaseUrl() + "/api/batch/getnotmapbatchbycourseid/"+courseid+"/"+emplid;

        ResponseEntity<BatchesUnmappedGET[]> response= restTemplate.exchange(url,HttpMethod.GET,request, BatchesUnmappedGET[].class);
        if(response.getStatusCode() == HttpStatus.OK)
        {
            res = response.getBody();
        }
        else
        {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        return res;
    }
}