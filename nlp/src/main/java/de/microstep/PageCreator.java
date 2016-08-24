package de.microstep;

import java.io.IOError;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PageCreator {
	
	public static String getHeader(){
		boolean isAdmin = SecurityUtils.isAdmin();
		
		String homeMenu = genMenuEntry("home", "Home");
		
		String username = SecurityUtils.getUsername();
		
		String usernameMenuEntry = username == null ? "" : "<li><a>You are logged in as " + username +"</a></li>";
		
		//Administration menu stuff
		String registrationMenu = genMenuEntry("register", "Register new user");
		
		String usersMenu = genMenuEntry("users", "Manage users");
		
		String adminMenu = "<li><a>Administration</a><ul>" + registrationMenu + usersMenu + "</ul></li>";
		
		String logoutMenu = genMenuEntry("logout", "logout");
		
		
		StringBuffer menu = new StringBuffer("<ul id=\"menu\">");
		menu.append(homeMenu);
		if(isAdmin)
			menu.append(adminMenu);
		menu.append(usernameMenuEntry);
		menu.append(logoutMenu);
		menu.append("</ul>");
		
		return menu.toString();
	}
	
	public static String genMenuEntry(String link, String name){
		return "<li><a href=\"" + link + "\">" + name + "</a></li>";
	}
	
	public static String getUserTable(){
		String table = "<table><tr><td>Username</td><td>Click to delete user</td>";
		try {
			ResultSet result = SQL.query("SELECT * FROM USERS");
			while(result.next()){
				table += getUserRow(result.getString(1), Boolean.parseBoolean(result.getString(3)));
			}
			table += "</table>";
			return table;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOError(e);
		}
	}
	
	public static String getUserRow(String user, boolean admin){
		return "<tr><td>" + user + "</td>" 
				+ (admin ? "":"<td><a href=users?remove=" + user + ">Remove</a></td>")
				+ "</tr>";
	}

}
