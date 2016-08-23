package de.microstep;

public class PageCreator {
	
	public static String getHeader(){
		boolean isAdmin = SecurityUtils.isAdmin();
		
		String adminMenu = isAdmin ? "<li><a>Administration</a><ul><li><a href=\"admin.html\">Admin Page"
				+ "</a></li></ul></li>" : "";
		
		String logoutUrl = "<c:url var=\"logout_url\" value=\"/logout\"/>";
		
		return "<ul id=\"menu\"><li><a href=\"index.jsp\">Home</a></li>"
				+ adminMenu +"<li><a href=\"welcome.html\">NLP</a></li>"
				+ "<li><a href=\"<c:url var=\"logout_url\" value=\"/logout\"/>\">Logout</a></li></ul>";
	}

}
