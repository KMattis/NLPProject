package de.microstep;

import java.io.IOError;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to provide functionality for our JSP-pages
 * @author Klaus Mattis
 * @since 24.08-1026
 */
public class JSPUtils {
	
	public static List<String[]> getAllUsers(){
		List<String[]> users = new ArrayList<>();
		try {
			ResultSet result = SQL.query("SELECT * FROM USERS");
			while(result.next()){
				users.add(new String[]{result.getString(1),result.getString(2),result.getString(3)});
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOError(e);
		}
	}

}
