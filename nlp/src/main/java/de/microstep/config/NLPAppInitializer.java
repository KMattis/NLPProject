package de.microstep.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class NLPAppInitializer extends AbstractSecurityWebApplicationInitializer{
	public NLPAppInitializer() {
		super(de.microstep.config.SecurityConfig.class);
	}
	
	@Override
	protected void afterSpringSecurityFilterChain(ServletContext servletContext) {
		super.afterSpringSecurityFilterChain(servletContext);
		AnnotationConfigWebApplicationContext dispatcherContext = 
				new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(DispatcherConfig.class);
		ServletRegistration.Dynamic dispatcher = 
				servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/", "/home", "/register", "/logout", "/users");
	}
}
