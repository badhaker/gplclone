package com.alchemy.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alchemy.utils.SuccessMessageCode;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration

public class SwaggerConfiguration {


	private ApiKey apiKeys() {
		return new ApiKey(SuccessMessageCode.JWT,SuccessMessageCode.AUTHORIZATION, SuccessMessageCode.HEADER);

	}

	private List<SecurityContext> securityContexts() {
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());

	}

	private List<SecurityReference> sf() {

		AuthorizationScope scope = new AuthorizationScope(SuccessMessageCode.GLOBAL, SuccessMessageCode.ACCESS_EVERYTHING);
		return Arrays.asList(new SecurityReference(SuccessMessageCode.JWT, new AuthorizationScope[] { scope }));
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys())).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo getInfo() {
		return new ApiInfo(SuccessMessageCode.AUTHENTICATION_APPLICATION, "", "", "", new Contact("", "", ""), "", "",
				java.util.Collections.emptyList());
	};
}
