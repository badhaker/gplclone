package com.alchemy.serviceInterface;

import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

public interface JwtTokenUtilInterface {

	public String getUsernameFromToken(String token);

	public Date getExpirationDateFromToken(String token);

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

	public Claims getAllClaimsFromToken(String token);

	public Boolean isTokenExpired(String token);

	public String generateToken(UserDetails userDetails);

	public Boolean validateToken(String token, UserDetails userDetails);

	public String getTokenType(String token) throws JwtException;

	Date calculateExpirationDate(Date createdDate);

	boolean canTokenBeRefreshed(String token);

	String refreshToken(String accessToken, UserDetails userDetails);
}
