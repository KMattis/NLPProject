package de.microstep;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

	private static final long serialVersionUID = -2469776328386654336L;
	private String user;
	private String password;
	private List<GrantedAuthority> auth = new ArrayList<>();
	
	public User(String user, String password, boolean admin) {
		this.user = user;
		this.password = password;
		auth.add(new SimpleGrantedAuthority("ROLE_USER"));
		if(admin)
			auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return auth;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return user;
	}

	//At the moment, just return true in the following methods
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
