package in.co.srdt.myguruji.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import in.co.srdt.myguruji.config.AccessToken;

//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;

import in.co.srdt.myguruji.model.CatgContentByUnitGET;
import in.co.srdt.myguruji.model.ContentByUnitGET;
import in.co.srdt.myguruji.model.ContentInfoModel;
import in.co.srdt.myguruji.model.LectureScheduleModel;
import in.co.srdt.myguruji.model.LinksModel;
import in.co.srdt.myguruji.model.Login;
import in.co.srdt.myguruji.model.ResponseMessage;
import in.co.srdt.myguruji.model.Student;
import in.co.srdt.myguruji.model.StudentDownloadRecord;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationContent;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/content")
public class ContentController
{
	@Autowired
	ApplicationContent contentUtil;
	
	@Autowired
    RestTemplate restTemplate;

    @Autowired
    private ApplicationGateway appGateway;

    @Autowired
	GetEmplId getEmplID;//=new GetEmplId;
    
    /*@PostMapping("/scheduleClass")
	@ResponseBody
	public String scheduleClass(HttpServletRequest request) throws UnirestException, UnsupportedEncodingException {
		long epochCrrnt = System.currentTimeMillis()/1000;
		long epochExpire = (epochCrrnt + 2 * 60 * 1000);

    	String s = Jwts.builder()
				.setAudience(null)
				.setIssuer("BdNvJFIXQMS0Nh4GFFxHag")
				.setExpiration(Date.from(Instant.ofEpochSecond(epochExpire)))
				.setIssuedAt(Date.from(Instant.ofEpochSecond(epochCrrnt)))
				.claim("aud", "")
				.claim("iss", "BdNvJFIXQMS0Nh4GFFxHag")
				.signWith(SignatureAlgorithm.HS256, "C91g7Q7XnIAfhBCvQZYhdC2ADzgueR8KU1yC".getBytes("UTF-8"))
				.compact();

		String topic = request.getParameter("titleSC");
		String start_time = request.getParameter("dateTimeSC")+":00";
		String duration = request.getParameter("durationSC");
		String agenda = request.getParameter("descr");
		HttpResponse<String> response = Unirest.post("https://api.zoom.us/v2/users/harihar.singh@srdt.co.in/meetings")
				.header("content-type", "application/json")
				.header("authorization", "Bearer" + s)
				.body("{\"topic\":\""+topic+"\",\"type\":2,\"start_time\":\""+start_time+"\",\"duration\":"+duration+",\"schedule_for\":\"\",\"timezone\":\"Asia/Calcutta\",\"password\":\"\",\"agenda\":\""+agenda+"\",\"settings\":{\"host_video\":false,\"participant_video\":false,\"cn_meeting\":false,\"in_meeting\":false,\"join_before_host\":false,\"mute_upon_entry\":true,\"watermark\":false,\"use_pmi\":false,\"approval_type\":0,\"audio\":\"both\",\"auto_recording\":\"none\",\"enforce_login\":false,\"enforce_login_domains\":\"\",\"alternative_hosts\":\"\",\"global_dial_in_countries\":[\"US\"],\"registrants_email_notification\":false}}")
				.asString();
		if(response.getStatus() == 201){
			System.out.println(response.getBody().toString());
			return "Class Scheduled Successfully!";
		} else {
			System.out.println();
			System.out.println(response.getBody().toString());
			return "Failed";
		}
	}*/
	
	@RequestMapping("/upload")
	@ResponseBody
	public String uploadFiles( @RequestParam("files")MultipartFile[] files
			, ContentInfoModel info, HttpServletRequest request, Authentication authentication )
	{
		//Convert description with -COMMA- to descr with commas
		String descr[] = new String[info.getDescr().length];
		for(int i=0; i<info.getDescr().length; i++)
		{
			descr[i] = info.getDescr()[i].replaceAll("-COMMA-", ",").replaceAll("-PERCENT-", "%");
		}
		info.setDescr(descr);
		
		//Convert title with -COMMA- to title with commas
		String ttl[] = new String[ info.getTitle().length ];
		for(int i=0; i<info.getTitle().length; i++)
		{
			ttl[i] = info.getTitle()[i].replaceAll("-COMMA-", ",").replaceAll("-PERCENT-", "%");
		}
		info.setTitle(ttl);
		/****************************************/
		
		if( (info.getTitle().length != info.getDescr().length) || info.getTitle().length != files.length)
		{
			return "MISMATCH";
		}
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		String empl = emplid.substring(emplid.length()-5);
		
		String location = info.getLocation();
		
		//String filePath = new File("").getAbsolutePath()+"\\"+contentUtil.getBaseLocation()+"\\"+location;
		String filePath = new File("").getAbsolutePath()+"/"+contentUtil.getBaseLocation()+"/"+location;
		String storePath = location;
		
		//CREATE DIRECTORY IF NOT EXISTS
		File dir = new File(filePath);
		if( !dir.exists() )
		{
			dir.mkdirs();
		}
		
		//WRITE FILES TO DIRECTORY AND ENTRY INTO THE DATABASE
		int i=0;
		for(MultipartFile file : files)
		{
			if(file.isEmpty())
			{
				//System.out.println("EMPTY FILE : "+i);
				continue;
			}
			try
			{
				//byte[] bytes = file.getBytes();
				
				//CREATING UNIQUE FILENAME
				String extension =  FilenameUtils.getExtension( file.getOriginalFilename() );
				String generatedFileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss"+"'"+empl+"."+extension+"'").format(new Date());
				
				//WINDOWS : Path path = Paths.get( filePath+"\\"+generatedFileName );
				//LINUX : Path path = Paths.get( filePath+File.separator+generatedFileName );
				//Files.write(path, bytes);
				
				/*****************************
				File source = new File("mySourceFile.txt");
				File target = new File(filePath+File.separator+generatedFileName);
				int readByteCount = 0;
				byte[] buffer = new byte[4096];
				
				try(FileInputStream in = new FileInputStream(source);
				    FileOutputStream out = new FileOutputStream(target)) {

				    while((readByteCount = in.read(buffer)) != -1) {

				        out.write(buffer, 0, readByteCount);
				    }
				}
				/*****************************/
				//File source = new File("mySourceFile.txt");
				File target = new File(filePath+File.separator+generatedFileName);
				int readByteCount = 0;
				byte[] buffer = new byte[4096];

				BufferedInputStream in= new BufferedInputStream(file.getInputStream());
				FileOutputStream out = new FileOutputStream(target);
			    while( (readByteCount = in.read(buffer)) != -1)
			    {
			    	//System.out.println("file read");
			        out.write(buffer, 0, readByteCount);
			    }
			    out.flush();
				out.close();
				/*****************************
				byte [] bufferedbytes= new byte[1024];
			    File myfile= new File(filePath+File.separator+generatedFileName);
			    FileOutputStream outStream = null;
			    int count=0;
			    try
			    {
			        BufferedInputStream fileInputStream= new BufferedInputStream(file.getInputStream());
			        outStream=new FileOutputStream(myfile);
			        while((count=fileInputStream.read(bufferedbytes))!=-1)
			        {
			        	outStream.write(bufferedbytes,0,count);
			        }
			        outStream.close();
			    }
			    catch (Exception e)
			    {
			    	System.out.println("Write Code Not Working");
			    	return "WRITE_ERROR";
			    }
				/*****************************/
				
				
				//storePath += "\\"+generatedFileName;
				storePath += "/"+generatedFileName;
				
				//String payLoad = "";
				String url = "";
				ContentUnitPostModel contentData = new ContentUnitPostModel();
				if(info.getLevel().equals("UNIT"))
				{
					contentData.setCourseid(info.getCourseid());
					contentData.setTypeid(info.getTypeid());
					contentData.setUnitid(info.getUnitid());
					contentData.setTitle(info.getTitle()[i]);
					contentData.setDescr(info.getDescr()[i]);
					contentData.setContentpath(storePath);
					contentData.setCreatedby(emplid);
					
					url = appGateway.getBaseUrl() + "/api/content/addunitcontent";
				}
				else if(info.getLevel().equals("CRSE"))
				{
					contentData.setCourseid(info.getCourseid());
					contentData.setTypeid(info.getTypeid());
					contentData.setUnitid(info.getUnitid());
					contentData.setTitle(info.getTitle()[i]);
					contentData.setDescr(info.getDescr()[i]);
					contentData.setContentpath(storePath);
					contentData.setCreatedby(emplid);
					
					url = appGateway.getBaseUrl() + "/api/content/addcoursecontent";
				}
				//System.out.println("URL : "+url);
				//System.out.println(contentData.getContentpath());
				ResponseMessage msg = null;
				//System.out.println(contentData.toString());
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.add("Authorization", AccessToken.getAccessToken());
				httpHeaders.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<ContentUnitPostModel> request1 = new HttpEntity<ContentUnitPostModel>(contentData,httpHeaders);
		        ResponseEntity<ResponseMessage> response= restTemplate.exchange(url ,HttpMethod.POST,request1, ResponseMessage.class);
		        if(response.getStatusCode() == HttpStatus.OK) {
		            msg = response.getBody();
		        } else {
		            System.out.println(response.getStatusCode());
		            return "LOG_ERROR";
		        }
		        storePath = location;
		        //bytes = null;
			}
			catch(IOException e)
			{
				//System.out.println("IO EXCEPTION : ");
				e.printStackTrace();
				return "IOEXCEPTION";
			}
			catch(IllegalArgumentException e)
			{
				//System.out.println("ILLEGAL ARGMENT EXCEPTION");
				e.printStackTrace();
				return "ILLEGALARG";
			}
			catch(Exception e)
			{
				//System.out.println("WRITE ERROR : ");
				e.printStackTrace();
				return "WRITE_ERROR";
			}
			++i;
		}
		
		return "SUCCESS";
	}
	
	@RequestMapping("/getcontentbyunit/{unitid}")
	@ResponseBody
	public ContentByUnitGET[] getcontentbyunit(@PathVariable String unitid)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request12 = new HttpEntity<String>(httpHeaders);
		String url2 = appGateway.getBaseUrl() + "/api/content/getunitcontentbyunitid/"+unitid;
		ContentByUnitGET[] unitContent = null;
		ResponseEntity<ContentByUnitGET[]> response2= restTemplate.exchange(url2,HttpMethod.GET,request12, ContentByUnitGET[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
			unitContent = response2.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}
		
		return unitContent;
	}
	
	@RequestMapping("/getcontentbyunit/categorised/{unitid}")
	@ResponseBody
	public CatgContentByUnitGET[] getcategorisedcontentbyunit(@PathVariable String unitid)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request12 = new HttpEntity<String>(httpHeaders);
		String url2 = appGateway.getBaseUrl() + "/api/content/getcategorizedunitcontent/"+unitid;
		CatgContentByUnitGET[] unitContent = null;
		ResponseEntity<CatgContentByUnitGET[]> response2= restTemplate.exchange(url2,HttpMethod.GET,request12, CatgContentByUnitGET[].class);
		if(response2.getStatusCode() == HttpStatus.OK) {
			unitContent = response2.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response2.getStatusCode());
		}
		return unitContent;
	}
	
	@RequestMapping(value = "/links/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public SingleResponseModel saveLinks(LinksModel info, HttpSession session)
	{
		SingleResponseModel res = new SingleResponseModel();
		if(info.getTitle().length != info.getStarturl().length || info.getStarturl().length != info.getDescr().length)
		{
			res.setMessage("MISMATCH");
			return res;
		}
		String descr[] = new String[info.getStarturl().length];
		String title[] = new String[info.getStarturl().length];
		for(int i=0; i< info.getStarturl().length; i++)
		{
			descr[i] = info.getDescr()[i].replaceAll("-COMMA-", ",").replaceAll("-PERCENT-", "%");
			title[i] = info.getTitle()[i].replaceAll("-COMMA-", ",").replaceAll("-PERCENT-", "%");
		}
		
		info.setTitle(title);
		info.setDescr(descr);
		
		String payLoad;
		String url = appGateway.getBaseUrl()+"/api/lecschedule/create";
		System.out.println(url);
		
		Login login = (Login)session.getAttribute("login");
		
		String types = "";
		for(int i=0; i< info.getStarturl().length; i++)
		{
			if(info.getTypes()[i].equals("3"))
			{
				types = "link";
			}
			if(info.getTypes()[i].equals("4"))
			{
				types = "onlcls";
			}
			payLoad = "{\"courseid\":\""+info.getCourseid()[i]+"\""+
						",\"unitid\":\""+info.getUnitid()[i]+"\""+
						",\"starturl\":\""+info.getStarturl()[i]+"\""+
						",\"title\":\""+info.getTitle()[i]+"\""+
						",\"descr\":\""+info.getDescr()[i]+"\""+
						",\"types\":\""+types+"\""+
						",\"starttime\":\"5050-01-01T12:00:00Z\""+
						",\"createdby\":\""+login.getEmplid()+"\""+
						"}";
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", AccessToken.getAccessToken());
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>(payLoad,httpHeaders);
			ResponseEntity<SingleResponseModel> response = restTemplate.exchange(url,HttpMethod.POST,request,SingleResponseModel.class);
			if(response.getStatusCode() == HttpStatus.OK)
			{
				res = response.getBody();
			}
			else
			{
				System.out.println("Request Failed!");
				System.out.println(response.getStatusCode());
			}
			
			if(!res.getMessage().equals("Schedule created"))
			{
				res.setMessage("ERROR");
				return res;
			}
		}
		
		res.setMessage("SUCCESS");
		return res;
	}
	
	@RequestMapping("/getlinksorclasses/{flag}/{unitid}")
	@ResponseBody
	public List<LectureScheduleModel> getLinksAndClasses(@PathVariable("flag") String flag, @PathVariable("unitid") String unitid)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request12 = new HttpEntity<String>(httpHeaders);
		String url3 = appGateway.getBaseUrl() + "/api/lecschedule/getschedulebyunitid/"+unitid;
		LectureScheduleModel[] lecs = null;
		ResponseEntity<LectureScheduleModel[]> response3= restTemplate.exchange(url3,HttpMethod.GET,request12, LectureScheduleModel[].class);
		if(response3.getStatusCode() == HttpStatus.OK)
		{
			lecs = response3.getBody();
		}
		else
		{
			System.out.println("Request Failed");
			System.out.println(response3.getStatusCode());
		}
		
		List<LectureScheduleModel> lecList = new ArrayList<>();
		if(flag.equals("link"))
		{
			for(LectureScheduleModel y : lecs)
			{
				if(y.getTypes().equals("link") || y.getTypes().equals("3"))
				{
					lecList.add(y);
				}
			}
		}
		else if(flag.equals("onlcls"))
		{
			for(LectureScheduleModel y : lecs)
			{
				if(y.getTypes().equals("onlcls") || y.getTypes().equals("4"))
				{
					lecList.add(y);
				}
			}
		}
		
		return lecList;
	}
	

	@PostMapping("/downloadStatusStudent")
	@ResponseBody
	public SingleResponseModel downloadStatusStudent(@RequestBody StudentDownloadRecord jsondata
			,HttpServletRequest request,Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		if(request.isUserInRole("ROLE_Faculty"))
		{
			return new SingleResponseModel("Faculty");
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
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", AccessToken.getAccessToken());
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);			
			HttpEntity<String> restreq1 = new HttpEntity<String>(studentsrch,httpHeaders);
			ResponseEntity<Student[]> stu = restTemplate.exchange(userDataUrl, HttpMethod.POST, restreq1, Student[].class);
			
			Student[] student = stu.getBody();
			stdntid=student[0].getStudentid();
			jsondata.setStudentid(stdntid);
		}
		//System.out.println(jsondata.toString());
		String url = appGateway.getBaseUrl() + "/api/content/saveStudentDownloadStatus";
		//String url = "http://10.8.20.35:9123/api/content/saveStudentDownloadStatus";
		SingleResponseModel resp = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<StudentDownloadRecord> req = new HttpEntity<StudentDownloadRecord>(jsondata, httpHeaders);		
		ResponseEntity<SingleResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, req, SingleResponseModel.class);		
		if(response.getStatusCode() == HttpStatus.OK) { 
			resp = response.getBody();
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
		//System.out.println(resp.toString());
		return resp;
	}
}