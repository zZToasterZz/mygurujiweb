package in.co.srdt.myguruji.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import in.co.srdt.myguruji.config.AccessToken;
import in.co.srdt.myguruji.model.FeedbackInfoModel;
import in.co.srdt.myguruji.model.FeedbackParameterModel;
import in.co.srdt.myguruji.model.GradingScaleModel;
import in.co.srdt.myguruji.model.coursePlan.SingleResponseModel;
import in.co.srdt.myguruji.utils.ApplicationGateway;
import in.co.srdt.myguruji.utils.GetEmplId;

@Controller
@RequestMapping("/feedback")
public class FeedbackController
{
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	GetEmplId getEmplID;// = new GetEmplId();
	
	@Autowired
	ApplicationGateway appGateway;
	
	@RequestMapping("/feedbacktypes")
	public String feedbackType()
	{
		return "forms/feedback/feedbacktype :: feedbacktype";
	}
	
	@RequestMapping("/feedbackparams")
	public String feedbackparams(Model model)
	{
		model.addAttribute("parents",getparentnodes());
		return "forms/feedback/feedbackparams :: feedbackparams";
	}
	
	@RequestMapping("/gradingscale")
	public String gradingscale(Model model, Authentication authentication)
	{
		String emplid = getEmplID.getLogedinUserEmplid(authentication.getName());
		
		model.addAttribute("createdby",emplid);
		return "forms/feedback/gradingscale :: gradingscale";
	}
	
	@PostMapping("/creategradingscale")
	@ResponseBody
	public SingleResponseModel gradingScale(@RequestBody GradingScaleModel[] gradingscalemodel)
	{
		SingleResponseModel resp = null;
		String url = appGateway.getBaseUrl() + "/api/feedback/saveGradingScale";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<GradingScaleModel[]> request = new HttpEntity<GradingScaleModel[]>(gradingscalemodel,httpHeaders);
		ResponseEntity<SingleResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, request,SingleResponseModel.class);
		
		try
		{
			if (response.getStatusCode() == HttpStatus.OK)
			{
				resp = response.getBody();
				
				if(resp.getMessage().equals("Success"))
				{
					return new SingleResponseModel("success");
				}
				else if(resp.getMessage().equals("Duplicate Entry"))
				{
					return new SingleResponseModel("duplicate");
				}
				else
				{
					return new SingleResponseModel("error");
				}
			}
			{
				return new SingleResponseModel("error");
			}
		}
		catch(Exception e)
		{
			return new SingleResponseModel("error");
		}
	}
	
	@GetMapping("/getGradingScale")
	@ResponseBody
	public GradingScaleModel[] getGradingScale(HttpServletRequest request)
	{
		GradingScaleModel[] scpi = null;
		String url = appGateway.getBaseUrl() + "/api/feedback/getFeedbackGradingScaleAllVaues";
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request1 = new HttpEntity<String>(httpHeaders);
		ResponseEntity<GradingScaleModel[]> response= restTemplate.exchange(url ,HttpMethod.GET,request1, GradingScaleModel[].class);

		if(response.getStatusCode() == HttpStatus.OK)
		{
			scpi = response.getBody();
		}
		
		return scpi;
	}
	
	@RequestMapping("/getnodes")
	@ResponseBody
	public FeedbackInfoModel[] getparents()
	{
		FeedbackInfoModel[] feedback = null;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> restreq = new HttpEntity<String>(httpHeaders);
		String url = appGateway.getBaseUrl()+"/api/feedback/getFeedbackType";
		//String url = "http://localhost:9120/api/feedback/getFeedbackType";
		ResponseEntity<FeedbackInfoModel[]> response = restTemplate.exchange(url, HttpMethod.GET, restreq, FeedbackInfoModel[].class);
		if(response.getStatusCode() == HttpStatus.OK)
			feedback = response.getBody();
		
		return feedback;
	}
	
	@RequestMapping("/getparentnodes")
	@ResponseBody
	public FeedbackInfoModel[] getparentnodes()
	{
		FeedbackInfoModel[] feedback = null;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> restreq = new HttpEntity<String>(httpHeaders);
		String url = appGateway.getBaseUrl()+"/api/feedback/getFeedbackParent";
		//String url = "http://localhost:9120/api/feedback/getFeedbackParent";
		ResponseEntity<FeedbackInfoModel[]> response = restTemplate.exchange(url, HttpMethod.GET, restreq, FeedbackInfoModel[].class);
		if(response.getStatusCode() == HttpStatus.OK)
			feedback = response.getBody();
		
		return feedback;
	}
	
	@RequestMapping("/getchildnodes/{id}")
	@ResponseBody
	public FeedbackInfoModel[] getchildnodes(@PathVariable String id)
	{
		FeedbackInfoModel[] feedback = null;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> restreq = new HttpEntity<String>(httpHeaders);
		String url = appGateway.getBaseUrl()+"/api/feedback/getFeedbackChildByParentId/"+id;
		//String url = "http://localhost:9120/api/feedback/getFeedbackChildByParentId/"+id;
		ResponseEntity<FeedbackInfoModel[]> response = restTemplate.exchange(url, HttpMethod.GET, restreq, FeedbackInfoModel[].class);
		if(response.getStatusCode() == HttpStatus.OK)
			feedback = response.getBody();
		
		return feedback;
	}
	
	@RequestMapping("/getfeedbackpath")
	@ResponseBody
	public FeedbackInfoModel[] getfeedbackpath(@RequestBody HashMap<String, String[]> hm)
	{
		FeedbackInfoModel[] feedback = null;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String[]> restreq = new HttpEntity<String[]>(((String[])((Map.Entry<String, String[]>)
				(hm.entrySet().iterator()).next()).getValue()),httpHeaders);
		String url = appGateway.getBaseUrl()+"/api/feedback/getFeedbackParentListById";
		//String url = "http://localhost:9120/api/feedback/getFeedbackParentListById";
		ResponseEntity<FeedbackInfoModel[]> response = restTemplate.exchange(url, HttpMethod.POST, restreq, FeedbackInfoModel[].class);
		if(response.getStatusCode() == HttpStatus.OK)
			feedback = response.getBody();
		
		return feedback;
	}
	
	@RequestMapping("/getfeedbackstructure/{id}")
	@ResponseBody
	public FeedbackInfoModel[] getfeedbackstructure(@PathVariable String id)
	{
		FeedbackInfoModel[] feedback = null;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> restreq = new HttpEntity<String>(httpHeaders);
		String url = appGateway.getBaseUrl()+"/api/feedback/getFeedBackTypeParentByParentId/"+id;
		//String url = "http://localhost:9120/api/feedback/getFeedBackTypeParentByParentId/"+id;
		ResponseEntity<FeedbackInfoModel[]> response = restTemplate.exchange(url, HttpMethod.GET, restreq, FeedbackInfoModel[].class);
		if(response.getStatusCode() == HttpStatus.OK)
			feedback = response.getBody();
		
		return feedback;
	}
	
	@RequestMapping("/createfeedbacktype")
	@ResponseBody
	public SingleResponseModel createfeedbacktype(@RequestBody HashMap<String, String> hm, Authentication authentication)
	{
		FeedbackInfoModel payLoad = new FeedbackInfoModel();
		payLoad.setCreatedby(getEmplID.getLogedinUserEmplid(authentication.getName()));
		payLoad.setFeedbackgradeparentid("0");
		
		Iterator<Entry<String, String>> it = hm.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, String> pair = (Map.Entry<String, String>)it.next();
			String key = (String)pair.getKey();
			String value = (String)pair.getValue();
			
			if(key.equals("parent"))
			{
				payLoad.setFeedbackparent(value);
			}
			else
			{
				payLoad.setFeedbacktypename(value);
			}
			it.remove();
		}
		
		SingleResponseModel res = new SingleResponseModel("success");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<FeedbackInfoModel> restreq = new HttpEntity<FeedbackInfoModel>(payLoad,httpHeaders);
		String url = appGateway.getBaseUrl()+"/api/feedback/saveFeedbackGradingParent";
		//String url = "http://localhost:9120/api/feedback/saveFeedbackGradingParent";
		ResponseEntity<SingleResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, restreq, SingleResponseModel.class);
		if(response.getStatusCode() == HttpStatus.OK)
			res = response.getBody();
		
		return res;
	}
	
	@RequestMapping("/createparameters")
	@ResponseBody
	public SingleResponseModel createparameters(@RequestBody FeedbackParameterModel[] parameters)
	{
		SingleResponseModel res = new SingleResponseModel("Failed");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<FeedbackParameterModel[]> restreq = new HttpEntity<FeedbackParameterModel[]>(parameters,httpHeaders);
		String url = appGateway.getBaseUrl()+"/api/feedback/saveFeedbackParameter";
		//String url = "http://localhost:9120/api/feedback/saveFeedbackParameter";
		ResponseEntity<SingleResponseModel> response = restTemplate.exchange(url, HttpMethod.POST, restreq, SingleResponseModel.class);
		if(response.getStatusCode() == HttpStatus.OK)
			res = response.getBody();
		else
		{
			res.setMessage("Failed");
		}
		return res;
	}
	
	@RequestMapping("/viewparameters/{id}")
	@ResponseBody
	public FeedbackParameterModel[] viewparemeters(@PathVariable String id)
	{
		FeedbackParameterModel[] feedback = null;
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> restreq = new HttpEntity<String>(httpHeaders);
		String url = appGateway.getBaseUrl()+"/api/feedback/getFeedbackParameterByParentId/"+id;
		//String url = "http://localhost:9120/api/feedback/getFeedbackParameterByParentId/"+id;
		ResponseEntity<FeedbackParameterModel[]> response = restTemplate.exchange(url, HttpMethod.GET, restreq, FeedbackParameterModel[].class);
		if(response.getStatusCode() == HttpStatus.OK)
			feedback = response.getBody();
		return feedback;
	}
}