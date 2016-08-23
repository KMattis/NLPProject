package de.microstep.sec;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import de.microstep.SQL;

@Configuration
@EnableWebSecurity
public class Config {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		ResultSet results = SQL.query("SELECT * FROM USERS");
		while(results.next()){
			String user = results.getString("USER");
			String pass = results.getString("PASS");
			boolean admin = Boolean.parseBoolean(results.getString("ADMIN"));
			System.out.println("Add user: " + user + " " + pass);
			if(admin)
				auth.inMemoryAuthentication().withUser(user).password(pass).roles("ROLE_ADMIN", "ROLSE_USER");
			else
				auth.inMemoryAuthentication().withUser(user).password(pass).roles("ROLSE_USER");
		}
	}
}
