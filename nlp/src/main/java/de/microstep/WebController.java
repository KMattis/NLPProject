package de.microstep;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
	
	private static final String HEADER = "header_menu";
	private static final String MESSAGE = "message";
	
	@RequestMapping("/welcome")
	public ModelAndView welcome(){
	
		ModelAndView model = new ModelAndView("welcome");
		
		model.addObject(HEADER, PageCreator.getHeader());
		
		model.addObject(MESSAGE, "Hello, World!");
		
		return model;
	}
	
	@RequestMapping("/registration")
	public Object register(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = new ModelAndView("registration");
		model.addObject(HEADER, PageCreator.getHeader());
		
		if(!SecurityUtils.isAdmin()){
//			model.addObject(MESSAGE, "redirect:/index.jsp<p style=\"align:center\">Permission denied (missing admin rights)</p>");
			return "redirect:/index.jsp";
		}
		
		String message = "";
		
		Map parameters = request.getParameterMap();
		if(!parameters.isEmpty()){
			Object user_obj = parameters.get("name");
			Object pass_obj = parameters.get("pass");
			Object admin_obj = parameters.get("admin");
			if(user_obj == null || pass_obj==null || admin_obj == null){
				message = "Missing input. Please enter user information again.<br/><br/>";
			}else{
				System.out.println(user_obj + " " + user_obj.getClass());
				String username = ((String[])user_obj)[0];
				String password = ((String[])pass_obj)[0];
				boolean admin = Boolean.parseBoolean(((String[])admin_obj)[0]);
				
				System.out.println(username + " " + password + " " + admin);
				
				UserDetails user = new User(username, password, admin);
				//TODO add to database
				try {
					SQL.execute("INSERT INTO USERS VALUES(USER='" + username + "', PASS='" + password + "', ADMIN='" + String.valueOf(admin) + "');");
					Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(auth);
					message = "User succesfully registered<br/>";
				} catch (SQLException e) {
					message = "Could not add user because of SQLException: " + e.getMessage() + "<br/>";
				}
			}
		}
		
		model.addObject(MESSAGE, message);
		
		return model;
	}
	
	@RequestMapping("/admin")
	public Object admin(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = new ModelAndView("admin");
		model.addObject(HEADER, PageCreator.getHeader());
		
		if(!SecurityUtils.isAdmin()){
//			model.addObject(MESSAGE, "<p style=\"align:center\">Permission denied (missing admin rights)</p>");
			return "redirect:/index.jsp";
		}
		
		model.addObject(MESSAGE, "This is the admin page.");
		
		return model;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/index.jsp";
	}

}
