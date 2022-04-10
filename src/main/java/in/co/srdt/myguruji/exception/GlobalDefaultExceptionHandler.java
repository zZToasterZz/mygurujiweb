package in.co.srdt.myguruji.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	
	@ExceptionHandler(Unauthorized.class)
	public String handlerUnauthorizedException(Model model) {
		
		model.addAttribute("errorTitle", "Session Expired");
		model.addAttribute("errorMessage", "Your Session has expired due to inactivity. <a href='/login'>Click here to login again.</a>");
		model.addAttribute("title", "401 Error Page");
		
		return "forms/error/error";
	}
	
	@ExceptionHandler(Exception.class)
	public String handlerAnyException(Model model) {
		
		model.addAttribute("errorTitle", "An error occured");
		model.addAttribute("errorMessage", "Oops ! something went wrong.");
		model.addAttribute("title", "Error Page");
		
		return "forms/error/error";
	}
	
	@ExceptionHandler(InternalServerError.class)
	public String handlerInternalServerErrorException(Model model) {
		
		model.addAttribute("errorTitle", "An error occured");
		model.addAttribute("errorMessage", "Oops ! something went wrong. Please contact Administrator !");
		model.addAttribute("title", "500 Error Page");
		
		return "forms/error/error";
	}

}
	