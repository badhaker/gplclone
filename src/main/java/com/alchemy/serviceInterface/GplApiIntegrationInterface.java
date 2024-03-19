package com.alchemy.serviceInterface;

import org.springframework.web.client.HttpClientErrorException;

import com.alchemy.dto.JwtTokenResponse;
import com.alchemy.dto.ThirdParyApiLoginCredentialsDto;
import com.alchemy.entities.MyResponse;

public interface GplApiIntegrationInterface {

	public JwtTokenResponse gplApiResponse() throws Exception;

	public MyResponse gplApiResponseDetail(String email) throws Exception, HttpClientErrorException;

	public MyResponse gplAzureADSFDetail(String email) throws Exception;

	ThirdParyApiLoginCredentialsDto addThirdParyApiLoginCredentials(ThirdParyApiLoginCredentialsDto dto);
}
