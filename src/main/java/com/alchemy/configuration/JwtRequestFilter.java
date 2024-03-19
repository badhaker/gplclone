package com.alchemy.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alchemy.entities.UserEntity;
import com.alchemy.repositories.UserRepository;
import com.alchemy.serviceImpl.AuthServiceImpl;
import com.alchemy.utils.Constant;
import com.alchemy.utils.SuccessMessageCode;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwttokenUtil;

	@Autowired
	private AuthServiceImpl authServiceImpl;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException, ExpiredJwtException {

		final String requestTokenHeader = request.getHeader(SuccessMessageCode.AUTHORIZATION);

//		MultiReadHttpServletRequest httpServletRequest = new MultiReadHttpServletRequest(request);

		String email = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		try {
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")
					&& jwttokenUtil.getTokenType(requestTokenHeader.substring(7)).equals(Constant.TOKEN_TYPE_ACCESS)) {

				jwtToken = requestTokenHeader.substring(7);

				email = jwttokenUtil.getUsernameFromToken(jwtToken);

			}
		} catch (ExpiredJwtException e) {
			response.setHeader("Error", "JWT expired");
		} catch (Exception e) {
			logger.error(e.getMessage());

		}
//		else {
//			logger.warn(ErrorMessageCode.TOKEN_DOES_NOT_BEGIN_WITH_BEARER);
//		}

		// Once we get the token validate it.
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.authServiceImpl.loadUserByUsername(email);
			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwttokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		if (null != jwtToken) {
			final String emailString = jwttokenUtil.getUsernameFromToken(jwtToken);

			UserEntity userEntity = userRepository.findByEmail(emailString);
			request.setAttribute("X-user-id", userEntity.getId());
			request.setAttribute("X-user-email", userEntity.getEmail());

		}

		chain.doFilter(request, response);
	}

}
