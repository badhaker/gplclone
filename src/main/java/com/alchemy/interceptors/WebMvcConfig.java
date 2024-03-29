package com.alchemy.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private ApiLogger apiLogger;

	@Autowired
	AuthLogger authLogger;

	public WebMvcConfig() {

		// TODO Auto-generated constructor stub
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(apiLogger);
		registry.addInterceptor(authLogger);
	}

}
