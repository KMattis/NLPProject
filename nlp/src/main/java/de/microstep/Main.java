package de.microstep;

import java.sql.SQLException;

public class Main {
	//Setup the database
	public static void main(String[] args) throws SQLException {
		SQL.execute("CREATE TABLE IF NOT EXISTS USERS(USER VARCHAR(64), PASS VARCHAR(64), ADMIN VARCHAR(10));");
		SQL.execute("INSERT INTO USERS VALUES ('admin', 'admin', 'true');");
	}
}
