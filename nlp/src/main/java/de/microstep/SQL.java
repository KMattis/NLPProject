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
			Config dbCfg = Config.getConfig("database");
			String driverName = dbCfg.getString("driver.name");
			String dbName = dbCfg.getString("database.name");
			String jdbcUrl = String.format("jdbc:%1s:./%2s", driverName, dbName);
			dbCon = DriverManager.getConnection(jdbcUrl);
		} catch (SQLException e) {
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
}
