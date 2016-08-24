package de.microstep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import de.microstep.Configs;

@Configuration
@ComponentScan("de.microstep")
public class DispatcherConfig {
	
	@Bean
	public UrlBasedViewResolver viewResolver(){
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix(Configs.getConfig("spring").getString("viewResolver.prefix"));
		resolver.setSuffix(Configs.getConfig("spring").getString("viewResolver.suffix"));
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

}
