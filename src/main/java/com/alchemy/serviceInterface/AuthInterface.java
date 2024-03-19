package com.alchemy.serviceInterface;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.HttpClientErrorException;

import com.alchemy.dto.ForgotPasswordConfirmDto;
import com.alchemy.dto.OTPDto;
import com.alchemy.dto.OnboardDto;
import com.alchemy.dto.UserDto;
import com.alchemy.entities.InviteEntity;
import com.alchemy.entities.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface AuthInterface {
	
	public UserEntity saveNewEnrollEmployee(String email) throws HttpClientErrorException, Exception;
	
	public UserDto registerUser(UserDto userDto) throws Exception;

	Boolean comparePassword(String password, String hashPassword);

	UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException, JsonMappingException, JsonProcessingException, Exception;

	ArrayList<String> getPermissionByUser(Long userId) throws Exception;

	ArrayList<String> getUserPermission(Long userId) throws IOException;

	Boolean updateUserWithPassword(ForgotPasswordConfirmDto payload) throws Exception;

	void generateOtpAndSendEmail(OTPDto otpDto, Long userId, String emailTemplate) throws Exception;

	InviteEntity onboardUser(OnboardDto dashboardDto) throws Exception;

	public void saveGplEmployeeDetail(UserEntity userEntity) throws HttpClientErrorException, Exception;

}
