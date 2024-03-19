package com.alchemy.configuration;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.alchemy.serviceInterface.JwtTokenUtilInterface;
import com.alchemy.utils.Constant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable, JwtTokenUtilInterface {
	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY_FOR_ACCESS_TOKEN = 7200;// access token valid for 2 hrs

	public static final long JWT_TOKEN_VALIDITY_FOR_REFRESH_TOKEN = 72000;// for one day = 20 * 60 * 60; refresh token
																			// valid for 20 hr

	@Value("${jwt.secret}")
	private String secret;

	// retrieve username from jwt token
	@Override
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	@Override
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	@Override
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	@Override
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	@Override
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	@Override
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).claim(Constant.TYPE, Constant.TOKEN_TYPE_ACCESS).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_FOR_ACCESS_TOKEN * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// validate token
	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	@Override
	public String getTokenType(String token) throws JwtException {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return (String) claims.get(Constant.TYPE);
	}

	@Override
	public String refreshToken(String accessToken, UserDetails userDetails) {
		final Date createdDate = new Date();
		final Date expirationDate = calculateExpirationDate(createdDate);

		final Claims claims = getAllClaimsFromToken(accessToken);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);
		return Jwts.builder().setClaims(claims).claim(Constant.TYPE, Constant.TOKEN_TYPE_REFRESH)
				.setSubject(userDetails.getUsername()).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	@Override
	public Date calculateExpirationDate(Date createdDate) {
		return new Date(createdDate.getTime() + JWT_TOKEN_VALIDITY_FOR_REFRESH_TOKEN * 1000);
	}

	@Override
	public boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	private boolean ignoreTokenExpiration(String token) {
		return false;
	}

}
