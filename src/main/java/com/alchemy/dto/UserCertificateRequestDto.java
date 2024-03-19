package com.alchemy.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class UserCertificateRequestDto {

	private Long userId;
	
	@NotNull(message = ErrorMessageCode.CERTIFICATE_ID_REQUIRED + "*" + ErrorMessageKey.USER_CERTIFICATE_E032804)
	@NotEmpty(message = ErrorMessageCode.CERTIFICATE_ID_REQUIRED + "*" + ErrorMessageKey.USER_CERTIFICATE_E032804)
	private ArrayList<Long> certificateId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ArrayList<Long> getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(ArrayList<Long> certificateId) {
		this.certificateId = certificateId;
	}

	public UserCertificateRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserCertificateRequestDto(Long userId, ArrayList<Long> certificateId) {
		super();
		this.userId = userId;
		this.certificateId = certificateId;
	}
	
	
}
