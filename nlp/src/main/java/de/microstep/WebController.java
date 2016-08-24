package de.microstep;

import java.io.IOError;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

	private static final String PAGE_TITLE = "page_title";
	private static final String PAGE_MENU = "page_menu";
	private static final String PAGE_DATA = "page_data";
	private static final String DEFAULT_VIEW_NAME = "default";
	private static final String REDIRECT_TO_HOME = "redirect:/home";

	private PreparedStatement insertUser;
	private PreparedStatement deleteUser;
	private PreparedStatement getDeclearedUser;

	private ModelAndView generatePage(String title, String data) {
		return generatePage(DEFAULT_VIEW_NAME, title, data);
	}

	private ModelAndView generatePage(String viewName, String title, String data) {
		ModelAndView model = new ModelAndView(viewName);
		model.addObject(PAGE_MENU, PageCreator.getHeader());
		model.addObject(PAGE_TITLE, title);
		model.addObject(PAGE_DATA, data);
		return model;
	}

	@RequestMapping("/home")
	public ModelAndView home() {
		return generatePage("NLP Test Project Main Page",
				"<h1 style=\"text-align:center\">This is the NLP Test Project main page</h1>");
	}

	@RequestMapping("/register")
	public Object register(HttpServletRequest request, HttpServletResponse response) {
		if (!SecurityUtils.isAdmin()) {
			return REDIRECT_TO_HOME;
		}

		String message = "";

		@SuppressWarnings("unchecked")
		Map<String, String[]> parameters = request.getParameterMap();
		if (!parameters.isEmpty()) {
			String[] username_arr = parameters.get("name");
			String[] password_arr = parameters.get("pass");
			String[] admin_arr = parameters.get("admin");
			if (username_arr == null || password_arr == null) {
				message = "Missing input. Please enter user information again.";
			} else {
				String username = username_arr[0];
				String password = password_arr[0];
				boolean admin = admin_arr == null ? false : admin_arr[0].equals("on");

				UserDetails user = new User(username, password, admin);

				if (insertUser == null) {
					Connection con = SQL.getCon();
					try {
						insertUser = con.prepareStatement("INSERT INTO USERS VALUES(?, ?, ?)");
					} catch (SQLException e) {
						e.printStackTrace();
						throw new IOError(e); // Should never happen
					}
				}

				try {
					insertUser.setString(1, username);
					insertUser.setString(2, password);
					insertUser.setString(3, Boolean.toString(admin));
				} catch (SQLException e) {
					e.printStackTrace();
					throw new IOError(e); // Should never happen
				}

				// TODO add to database
				try {
					insertUser.execute();
					SQL.getCon().commit();
					message = "User succesfully registered";
				} catch (SQLException e) {
					message = "Could not add user because of SQLException: " + e.getMessage();
				}
			}
		}
		return generatePage("register", "Register a new user", message);
	}

	// @RequestMapping("/admin")
	// public Object admin(HttpServletRequest request, HttpServletResponse
	// response) {
	// if (!SecurityUtils.isAdmin()) {
	// return REDIRECT_TO_HOME;
	// }
	// return generatePage("admin", "This is the admin page");
	// }

	@RequestMapping(value = "/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return REDIRECT_TO_HOME;
	}

	@RequestMapping(value = "/users")
	public Object userControlPanel(HttpServletRequest request, HttpServletResponse response) {
		if (!SecurityUtils.isAdmin()) {
			return REDIRECT_TO_HOME;
		}
		String message = "";

		String username = request.getParameter("remove");
		if (username != null) {
			if (getDeclearedUser == null) {
				try {
					getDeclearedUser = SQL.getCon().prepareStatement("SELECT * FROM USERS WHERE USER = ?");
				} catch (SQLException e) {
					e.printStackTrace();
					throw new IOError(e);
				}
			}

			try {
				getDeclearedUser.setString(1, username);
				ResultSet result = getDeclearedUser.executeQuery();
				if (result.next()) {
					if (Boolean.parseBoolean(result.getString(3))) {
						message = "Cannot delete an admin user";
					} else {
						if (deleteUser == null) {
							deleteUser = SQL.getCon().prepareStatement("DELETE FROM USERS WHERE USER = ?");
						}
						deleteUser.setString(1, username);
						deleteUser.execute();
						SQL.getCon().commit();
						message = "User '" + username + "' successfully deleted";
					}
				} else {
					message = "User '" + username + "' does not exists";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOError(e);
			}
		}

		return generatePage("Manage Users", message + "<br/><br/>" + PageCreator.getUserTable());
	}
	
	@RequestMapping(value="/")
	public String defaultRedirect(){
		return REDIRECT_TO_HOME;
	}

}
