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
			Config dbCfg = Config.getConfig("database");
			String driverName = dbCfg.getString("driver.name");
			String dbName = dbCfg.getString("database.name");
			String jdbcUrl = String.format("jdbc:%1s:./%2s", driverName, dbName);
			dbCon = DriverManager.getConnection(jdbcUrl);
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
}
