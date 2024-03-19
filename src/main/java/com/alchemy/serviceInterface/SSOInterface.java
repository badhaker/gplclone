package com.alchemy.serviceInterface;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.client.HttpClientErrorException;
import org.xml.sax.SAXException;

import com.alchemy.dto.AuthResponseDto;

public interface SSOInterface {
	public String generateRedirectUrl();

	public String ssoAuthentication(String samlResponse) throws ParserConfigurationException, SAXException, IOException;

	public AuthResponseDto generateAccessToken(String email) throws Exception;

	public AuthResponseDto validateUUID(String string) throws HttpClientErrorException, Exception;

}
