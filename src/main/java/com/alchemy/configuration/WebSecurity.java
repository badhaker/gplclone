package com.alchemy.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.alchemy.serviceImpl.AuthServiceImpl;
import com.alchemy.serviceImpl.CustomUserDetailService;
import com.alchemy.utils.ApiUrls;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity {

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private AuthServiceImpl authServiceImpl;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
				// dont authenticate this particular request
				// Csrf - it is a type of attack where malicious site unauthorized request on
				// behalf of
				// user who already authenticated
				.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers(ApiUrls.AUTH + ApiUrls.LOGIN, ApiUrls.AUTH + ApiUrls.REGISTER,
						ApiUrls.AUTH + ApiUrls.REFRESH_TOKEN, ApiUrls.AUTH + ApiUrls.FORGOT_PASSWORD,
						ApiUrls.AUTH + ApiUrls.FORGOT_PASSWORD_CONFIRM, ApiUrls.AUTH + ApiUrls.FORGOT_PASSWORD_CONFIRM,
						"/public/**", ApiUrls.DOWNLOAD_FILE, ApiUrls.AUTH + ApiUrls.ONBOARD, "/saml/login",
						"/saml/response", "/saml/validate")
				.permitAll()
				// all other requests need to be authenticated
				.antMatchers(ApiUrls.SWAGGER_URLS).permitAll().anyRequest().authenticated().and().httpBasic().and()

				// make sure we use stateless session; session won't be used to
				// store user's state.
				// server will not manage and store session for each user
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement()

				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		;
		// Add a filter to validate the tokens with every request
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		http.authenticationProvider(authenticationProvider());

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		// DaoAthenticationProvider is clss that implements Authentication Provider
		// it used to authenticate user with username and password
		provider.setUserDetailsService(authServiceImpl);
		provider.setPasswordEncoder(bCryptPasswordEncoder());

		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(authServiceImpl).passwordEncoder(passwordEncoder());

	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, UserDetailsService userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		return authenticationManagerBuilder.build();
	}

	@Bean
	public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
		return new CustomUserDetailService(bCryptPasswordEncoder);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));
		configuration.setAllowedHeaders(
				Arrays.asList("authorization", "content-type", "x-auth-token", "lang", "x-token", "ip-address"));
		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;

	}

}
