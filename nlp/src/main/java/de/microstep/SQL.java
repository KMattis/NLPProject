package de.microstep;

import java.io.IOError;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class to provide basic SQL functionality
 * @author Klaus Mattis
 * @since 24.08.2016
 */
public class SQL {

	private static final Connection dbCon;

	static {
		try {
			//Load class manually...
			Class.forName("org.h2.Driver");
			
			String driver   = Configs.getConfig("database").getString("driver.name");
			String database = Configs.getConfig("database").getString("database.name");
			String jdbcUrl = String.format("jdbc:%1s:./%2s", driver, database);
			
			dbCon = DriverManager.getConnection(jdbcUrl);
			
			execute(Configs.getConfig("database").getString("createTable.users"));
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
			e.printStackTrace();
			throw new IOError(e); //We can't go on now...
		}
	}
	
	/**
	 * @param SQLQuery the sql-format query
	 * @return a result set delivered by executing the query string
	 * @throws SQLException if an SQLError occurred
	 */
	public static ResultSet query(String SQLQuery) throws SQLException {
		Statement statement = dbCon.createStatement();
		return statement.executeQuery(SQLQuery);
	}

	/**
	 * This method executes the given update command
	 * @param string the sql-format update command
	 * @throws SQLException uf an SQLError occured
	 */
	public static void execute(String string) throws SQLException {
		Statement stat = dbCon.createStatement();
		stat.executeUpdate(string);
		stat.close();
		dbCon.commit();
	}

	/**
	 * @return the current connection to the database
	 */
	public static Connection getCon() {
		return dbCon;
	}
}
