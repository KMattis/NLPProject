package de.microstep;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializerImpl extends AbstractSecurityWebApplicationInitializer{
	public SecurityWebApplicationInitializerImpl() {
		super(de.microstep.sec.Config.class);
	}
}
