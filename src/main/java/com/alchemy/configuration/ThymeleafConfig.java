package com.alchemy.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ThymeleafConfig {

	

	  
	  @Autowired
	    private ApplicationContext applicationContext;

	    @Bean
	    public SpringResourceTemplateResolver templateResolver() {
	        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	        templateResolver.setApplicationContext(applicationContext);
	        templateResolver.setPrefix("classpath:/templates/");
	        templateResolver.setSuffix(".html");
	        templateResolver.setTemplateMode(TemplateMode.HTML);
	        templateResolver.setCacheable(false);
	        return templateResolver;
	    }

	    @Bean
	    public SpringTemplateEngine templateEngine() {
	        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	        templateEngine.setTemplateResolver(templateResolver());
	        return templateEngine;
	    }

	    @Bean
	    public ViewResolver thymeleafViewResolver() {
	        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	        viewResolver.setTemplateEngine(templateEngine());
	        return viewResolver;
	    }

	}

	
