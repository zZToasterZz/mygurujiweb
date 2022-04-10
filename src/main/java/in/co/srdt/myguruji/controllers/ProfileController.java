package in.co.srdt.myguruji.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.FacultyDetails;
import in.co.srdt.myguruji.model.Student;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;
import in.co.srdt.myguruji.utils.MailProperties;
import in.co.srdt.myguruji.utils.MailerInfo;

/* DONE BY ASMITA on 23/11/2020  */
@Controller
@RequestMapping("/profile")
public class ProfileController
{
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ApplicationGateway appGateway;
	
	@Autowired
	MailProperties props;
	
	@Autowired
	MailerInfo mailerInfo;
	
	@Autowired
	SpringTemplateEngine thymeleafTemplateEngine;
	
	@Autowired
	GetEmplId getEmplID;//=new GetEmplId;
	String studenturl,facultyurl,studentpayLoad,facultypayLoad,updateurl,studentupdatepayLoad,facultyupdatepayLoad;
	
	@RequestMapping("/viewprofile")
	public String viewProfile(Authentication authentication, HttpServletRequest request, Model model)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		String role = getEmplID.getRoleByRequest(request);
		model.addAttribute("role", role); 
		
		studenturl = appGateway.getBaseUrl()+"/api/student/search";
		facultyurl = appGateway.getBaseUrl()+"/api/faculty/search";
		
		if(request.isUserInRole("ROLE_Student"))
		{
			model.addAttribute("userrole","Student");
			
			studentpayLoad = "{\r\n" + 
					"	\"studentid\" : 0,\r\n" + 
					"    \"emplid\": \""+emplid+"\",\r\n" + 
					"    \"applnbr\": \"\",\r\n" + 
					"    \"campusid\": \"\",\r\n" + 
					"    \"firstname\": \"\",\r\n" + 
					"    \"emailaddr\": \"\",\r\n" + 
					"    \"primarycontact\": \"\"\r\n" + 
					"}";
			
			Student[] stdnt = null;
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", AccessToken.getAccessToken());
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> student_req = new HttpEntity<String>(studentpayLoad, httpHeaders);
			ResponseEntity<Student[]> student_res= restTemplate.exchange(studenturl, HttpMethod.POST, student_req, Student[].class);
			
			if(student_res.getStatusCode() == HttpStatus.OK)
			{
				stdnt = student_res.getBody();
				model.addAttribute("student_data",stdnt);
			}
			else
			{
				System.out.println("Request Failed");
			}
		}
		else if(request.isUserInRole("ROLE_Faculty"))
		{
			model.addAttribute("userrole","Faculty");
			
			facultypayLoad = "{\r\n" + 
					"	\"facultyid\" : 0,\r\n" + 
					"    \"facultycode\" : \"\",\r\n" + 
					"    \"emplid\" : \""+emplid+"\",\r\n" + 
					"    \"designation\" : \"\",\r\n" + 
					"    \"firstname\" : \"\",\r\n" + 
					"    \"emailaddr\" : \"\",\r\n" + 
					"    \"primarycontact\" : \"\"\r\n" + 
					"}";
			
			FacultyDetails[] faclty = null;
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", AccessToken.getAccessToken());
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> faculty_req = new HttpEntity<String>(facultypayLoad, httpHeaders);
			ResponseEntity<FacultyDetails[]> faculty_res= restTemplate.exchange(facultyurl, HttpMethod.POST, faculty_req, FacultyDetails[].class);
			
			if(faculty_res.getStatusCode() == HttpStatus.OK)
			{
				faclty = faculty_res.getBody();
				model.addAttribute("faculty_data",faclty);
			}
			else
			{
				System.out.println("Request Failed");
			}
		}
		
		return "forms/login/viewProfile :: viewProfile";
	}
	
	@RequestMapping("/updateprofile/{role}")
	public String updateProfile(@PathVariable("role") String role,HttpServletRequest updateprofilerequest,Model model)
	{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		SingleResponseModel res = null;
		if(role.equals("Student"))
		{
			String email=updateprofilerequest.getParameter("emailaddr");
			String campusid = updateprofilerequest.getParameter("campusid");
			String contact = updateprofilerequest.getParameter("primarycontact");
			String name = updateprofilerequest.getParameter("fullname");
			updateurl = appGateway.getBaseUrl()+"/api/student/updateEmailAndContact/"+email+"/"+contact+"/"+campusid;
			
			HttpEntity<String> stuupdateprofileRequest = new HttpEntity<String>(httpHeaders);
			ResponseEntity<SingleResponseModel> student_upres= restTemplate.exchange(updateurl, HttpMethod.POST, stuupdateprofileRequest, SingleResponseModel.class);
			
			if(student_upres.getStatusCode() == HttpStatus.OK) 
			{
				res = student_upres.getBody();
				try
				{
					sendmail(name,campusid, email);
				}
				catch(MessagingException | IOException e)
				{
					e.printStackTrace();
					res.setMessage("MAIL_FAIL");
					return "forms/login/viewProfile :: viewProfile";
				}
				
			} else {
				System.out.println("Request Failed");
				System.out.println(student_upres.getStatusCode());
			}
			
		}
		else if(role.equals("Faculty"))
		{
			String email=updateprofilerequest.getParameter("emailaddr");
			String emplid = updateprofilerequest.getParameter("emplid");
			String contact = updateprofilerequest.getParameter("primarycontact");
			String name = updateprofilerequest.getParameter("fullname");
			updateurl = appGateway.getBaseUrl()+"/api/faculty/updateEmailAndContact/"+email+"/"+contact+"/"+emplid;
			
			HttpEntity<String> facupdateprofileRequest = new HttpEntity<String>(httpHeaders);
			ResponseEntity<SingleResponseModel> fac_upres= restTemplate.exchange(updateurl, HttpMethod.POST, facupdateprofileRequest, SingleResponseModel.class);
			
			if(fac_upres.getStatusCode() == HttpStatus.OK) {
				res = fac_upres.getBody();
				System.out.println("RESPONSE ==> "+res.getMessage());
				
				try
				{
					sendmail(name,emplid, email);
				}
				catch(MessagingException | IOException e)
				{
					e.printStackTrace();
					res.setMessage("MAIL_FAIL");
					return "forms/login/viewProfile :: viewProfile";
				}
				
			} else {
				System.out.println("Request Failed");
				System.out.println(fac_upres.getStatusCode());
			}
			
		}
		
		return "forms/login/viewProfile :: viewProfile";
	}
	
	private void sendmail(String name,String uname, String emailaddr) throws AddressException, MessagingException, IOException
	{
		System.out.println(emailaddr);
		Session session = Session.getInstance(props.getProperties(), new javax.mail.Authenticator(){
		  protected PasswordAuthentication getPasswordAuthentication(){
			 return new PasswordAuthentication(mailerInfo.getMailid(), mailerInfo.getMailpass());
		  }
		});
		
		/*
		String encr = AES.encrypt(uname, secret.getPassResetKey());
		
		encr = encr.replaceAll("=", "EQUALS");
		encr = encr.replaceAll("/", "BSLASH");
		encr = encr.replaceAll("[+]", "PLUS");
		encr = encr.replaceAll("[%]", "PERCENT");
		
		String url="http://myguruji.srmu.ac.in/passwordreset/"+encr;
		*/
		MimeMessage msg = new MimeMessage(session);
		
		MimeMessageHelper helper = new MimeMessageHelper(msg,StandardCharsets.UTF_8.name());
	    
	
		Context thymeleafContext = new Context();
		thymeleafContext.setVariable("name", name);
		thymeleafContext.setVariable("loginname", uname);
	    String htmlBody = thymeleafTemplateEngine.process("mail/updateprofile.html", thymeleafContext);
	    helper.setText(htmlBody,true);
	    //helper.addInline("mygurujilogo.png", new ClassPathResource("/static/images/mailtemplate/myGurujiLogo.png"));
	    
	    helper.setFrom(mailerInfo.getMailid());
	    //helper.setTo(InternetAddress.parse(emailaddr));
	    helper.setTo(InternetAddress.parse("asmita.srdt@gmail.com"));
	    helper.setSubject("Profile Updation");
	    //helper.setText("Dear "+name+", \n\n \t\t\tYour profile has been updated successfully!!");
	    helper.setSentDate(new Date());
	    
		Transport.send(msg);
	}
}
