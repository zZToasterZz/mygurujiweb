package in.co.srdt.myguruji.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import in.co.srdt.myguruji.utils.AppTheme;

@Controller
public class ManageCalendar {
	
	@Autowired
	private AppTheme appTheme;

	@RequestMapping("/calendar/viewevents")
	public ModelAndView goToManageCourses() {
		ModelAndView mv = new ModelAndView("/manageCalendar_M");
		
		final String themeColor = appTheme.getAppTheme();
		final String theme = "/css/w3-theme-" + appTheme.getAppTheme() + ".css";
		
		mv.addObject("theme", theme);
		mv.addObject("themeColor", themeColor);
		
		return mv;
	}
}
