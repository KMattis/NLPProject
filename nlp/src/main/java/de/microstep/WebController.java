package de.microstep;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
	
	@RequestMapping("/welcome")
	public ModelAndView welcome(){
	
		ModelAndView model = new ModelAndView("welcome");
		
		model.addObject("message", "Hello, World!");
		
		return model;
	}
	
	@RequestMapping("/login")
	public ModelAndView auth(){
	
		ModelAndView model = new ModelAndView("welcome");
		
		model.addObject("message", "Hello, World!");
		
		return model;
	}

}
