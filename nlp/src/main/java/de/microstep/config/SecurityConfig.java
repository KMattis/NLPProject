package de.microstep.config;

import java.io.IOError;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import de.microstep.SQL;
import de.microstep.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static boolean addedAuthenticationProvider = false;
	
	private static final PreparedStatement stat;
	
	static{
		try {
			stat = SQL.getCon().prepareStatement("SELECT * FROM USERS WHERE USER = ?");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOError(e); //Should never happen
		}
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		if(addedAuthenticationProvider) return;
		addedAuthenticationProvider = true;
		auth.authenticationProvider(new AuthenticationProvider() {
			@Override
			public boolean supports(Class<?> arg0) {
				return true;
			}
			@Override
			public Authentication authenticate(Authentication auth) throws AuthenticationException {
				String username = auth.getName();
				try {
					stat.setString(1, username);
					ResultSet result = stat.executeQuery();
					
					if(result.next()){
						String password = result.getString(2);
						boolean admin = Boolean.parseBoolean(result.getString(3));
						UserDetails user = new User(username, password, admin);
						return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
					}else{
						throw new AuthenticationCredentialsNotFoundException("user was not found");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
}
