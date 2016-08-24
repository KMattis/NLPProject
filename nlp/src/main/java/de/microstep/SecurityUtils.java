package de.microstep;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

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
	
	public static boolean isUser(){
		return hasRole("ROLE_USER");
	}
	
	public static boolean isAdmin(){
		return hasRole("ROLE_ADMIN");
	}
	
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
