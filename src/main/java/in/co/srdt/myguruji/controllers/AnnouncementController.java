package in.co.srdt.myguruji.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import in.co.srdt.myguruji.config.AccessToken;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import in.co.srdt.myguruji.model.AnnounceBatchModel;
import in.co.srdt.myguruji.model.BatchDetailsModel;
import in.co.srdt.myguruji.model.Student;
import in.co.srdt.myguruji.model.StudentMailModel;
import in.co.srdt.myguruji.model.coursePlan.CourseModel;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;
import in.co.srdt.myguruji.utils.MailProperties;
import in.co.srdt.myguruji.utils.MailerInfo;
import in.co.srdt.myguruji.utils.Secret;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController
{
	@Autowired
    RestTemplate restTemplate;

	@Autowired
    private ApplicationGateway appGateway;
	
	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;
	
	@Autowired
	MailerInfo mailerInfo;
	
	@Autowired
	Secret secret;
	
	@Autowired
	MailProperties props;

	@Autowired
	GetEmplId getEmplID;//=new GetEmplId;
	
	@GetMapping("/studentAnnouncement/{flag}")
    public String createAnnouncement(Authentication authentication, HttpServletRequest request, Model model
    		, @PathVariable("flag") String flag)
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
        model.addAttribute("createdby", emplid);
        
        if(flag.equals("stdnt"))
        	return "forms/announcement/stdntAnnounce :: stdntAnnounce";
        else
        	return "forms/announcement/batchAnnounce :: batchAnnounce";
	}
	
	@RequestMapping("/batchdetails/{batchid}")
	@ResponseBody
	public BatchDetailsModel getBatchDetails(@PathVariable("batchid") String batchid)
	{
		BatchDetailsModel batch = null;

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/batch/getbatchdetailsbyid/" + batchid;

        ResponseEntity<BatchDetailsModel> response= restTemplate.exchange(url, HttpMethod.GET, request1, BatchDetailsModel.class);

        if(response.getStatusCode() == HttpStatus.OK){
        	batch = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        
		return batch;
	}
	
	@RequestMapping(value="/announce/batch", method = RequestMethod.POST,consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public SingleResponseModel announceToBatch(AnnounceBatchModel info)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		SingleResponseModel res = new SingleResponseModel();
		
		/*Get Students from Batch*/
		BatchDetailsModel batch = null;
        HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
        String url = appGateway.getBaseUrl() + "/api/batch/getbatchdetailsbyid/" + info.getBatchid();
        ResponseEntity<BatchDetailsModel> response= restTemplate.exchange(url ,HttpMethod.GET,request1, BatchDetailsModel.class);
        if(response.getStatusCode() == HttpStatus.OK) {
        	batch = response.getBody();
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
		List<Student> students = batch.getStudents();
		
		String bcc[] = new String[students.size()-1];
		String cc[] = new String[students.size()-1];
		
		String to = "";
		
		for(int i = 0; i<students.size(); i++)
		{
			if(i == 0)
			{
				to = students.get(i).getEmailaddr();
				continue;
			}
			bcc[i-1] = students.get(i).getEmailaddr();
			//cc[i] = students.get(i).getEmailaddr();
		}
		String flag = "";
		try{
			sendmail(info.getSubject(), info.getBody(), to, bcc, cc, flag);
			res.setMessage("MAIL_SENT");
		}catch(Exception e){
			res.setMessage("MAIL_FAIL");
		}
		return res;
	}
	
	@PostMapping("/mailstudent/mail")
	@ResponseBody
	private SingleResponseModel mailstudent(@RequestBody StudentMailModel student)
	{
		String cc[]=new String[0];
		try {
			sendmail(student.getSubject(),student.getBody(),student.getEmailid(),cc,cc,"");
			return new SingleResponseModel("success");
		}catch(Exception e) {
			return new SingleResponseModel("fail");
		}
	}
	
	private void sendmail(String subject, String body,String to, String bcc[], String cc[], String flag) throws AddressException, MessagingException, IOException
	{
		Session session = Session.getInstance(props.getProperties(), new javax.mail.Authenticator() {
		  protected PasswordAuthentication getPasswordAuthentication() {
			 return new PasswordAuthentication(mailerInfo.getMailid(), mailerInfo.getMailpass());
		  }
		});
		
		MimeMessage msg = new MimeMessage(session);
		MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
	    
		Context thymeleafContext = new Context();
		thymeleafContext.setVariable("body", body);
	    String htmlBody = thymeleafTemplateEngine.process("mail/batchAnnounce.html", thymeleafContext);
	    
	    helper.setText(htmlBody,true);
	    helper.setFrom(mailerInfo.getMailid());
	    helper.setTo(InternetAddress.parse(to));
	    //helper.setTo(InternetAddress.parse("shantanusrivastava.srdt@gmail.com"));
	    //helper.setCc(cc);
	    helper.setBcc(bcc);
	    helper.setSubject(subject);
	    helper.setSentDate(new Date());
	    
		Transport.send(msg);
	}
}