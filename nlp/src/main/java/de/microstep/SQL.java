package de.microstep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {

	private static Connection dbCon;

	static {
		try {
			System.err.println("Initializer started");
			Class.forName("org.h2.Driver");
			Configs dbCfg = Configs.getConfig("database");
			String driverName = dbCfg.getString("driver.name");
			String dbName = dbCfg.getString("database.name");
			String jdbcUrl = String.format("jdbc:%1s:./%2s", driverName, dbName);
			dbCon = DriverManager.getConnection(jdbcUrl);
			execute("CREATE TABLE IF NOT EXISTS USERS(USER VARCHAR(64), PASS VARCHAR(64), ADMIN VARCHAR(10));");
			ResultSet result = query("SELECT COUNT(*) FROM USERS");
			boolean hasEntries = true;
			if (result.next())
				hasEntries = result.getInt(1) > 0;
			if (!hasEntries) {
				String adminName = Configs.getConfig("settings").getString("users.admin.default.name");
				String adminPass = Configs.getConfig("settings").getString("users.admin.default.pass");
				execute("INSERT INTO USERS VALUES ('" + adminName + "', '" + adminPass + "', 'true');");
			}
		} catch (Throwable e) {
			System.err.println("Print Stacktrace:");
			e.printStackTrace();
			// TODO appropriate exception handling
		}
	}

	private SQL() {
		assert false;
	}

	public static ResultSet query(String SQLQuery) throws SQLException {
		Statement statement = dbCon.createStatement();
		return statement.executeQuery(SQLQuery);
	}

	public static void execute(String string) throws SQLException {
		Statement stat = dbCon.createStatement();
		stat.executeUpdate(string);
		stat.close();
		dbCon.commit();
	}

	public static Connection getCon() {
		return dbCon;
	}
}
