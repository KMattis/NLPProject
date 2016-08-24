package de.microstep;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for checking authorities
 * @author Klaus Mattis
 * @since 24.08.1026
 * @see Spring Security
 *
 */
public class SecurityUtils {

	/**
	 * @param role the role to check
	 * @return whether the current user has the specified role, or 0 if no user is logged in
	 */
	public static boolean hasRole(String role){
		SecurityContext context = SecurityContextHolder.getContext();
		if(context == null)
			return false;
		
		Authentication authentication = context.getAuthentication();
		
		if(authentication == null)
			return false;
		
		for(GrantedAuthority auth : authentication.getAuthorities()){
			if(role.equals(auth.getAuthority()))
				return true;
		}
		
		return false;
	}
	
	/**
	 * @return whether the current user has the role "ROLE_USER"
	 */
	public static boolean isUser(){
		return hasRole("ROLE_USER");
	}
	
	/**
	 * @return whether the current user has the role "ROLE_ADMIN"
	 */
	public static boolean isAdmin(){
		return hasRole("ROLE_ADMIN");
	}
	
	/**
	 * @return the username of the current user
	 */
	public static String getUsername(){
		SecurityContext context = SecurityContextHolder.getContext();
		if(context == null)
			return null;
		
		Authentication authentication = context.getAuthentication();
		if(authentication == null)
			return null;

		return authentication.getName();
	}
	
}
