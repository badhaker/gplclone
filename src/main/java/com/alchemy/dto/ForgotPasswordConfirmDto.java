package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.Validator;

public class ForgotPasswordConfirmDto {

	@NotBlank(message = ErrorMessageCode.EMAIL_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	@NotEmpty(message = ErrorMessageCode.EMAIL_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	@NotNull(message = ErrorMessageCode.EMAIL_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	String email;

	@NotBlank(message = ErrorMessageCode.NULL_OTP + "*" + ErrorMessageKey.USER_E031204)
	@NotEmpty(message = ErrorMessageCode.NULL_OTP + "*" + ErrorMessageKey.USER_E031204)
	@NotNull(message = ErrorMessageCode.NULL_OTP + "*" + ErrorMessageKey.USER_E031204)
	@Pattern(regexp = Validator.OTP_VALIDATER, message = ErrorMessageCode.INVALID_OTP + "*" + ErrorMessageKey.USER_E031204 )
	String otp;

	@NotBlank(message = ErrorMessageCode.PASSWORD_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	@NotEmpty(message = ErrorMessageCode.PASSWORD_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	@NotNull(message = ErrorMessageCode.PASSWORD_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	@Pattern(regexp = Validator.PASSWORD_PATTERN, message = ErrorMessageCode.INVALID_PASSWORD_FORMAT+ "*" + ErrorMessageKey.USER_E031204 )
	String password;

	public ForgotPasswordConfirmDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForgotPasswordConfirmDto(String email, String otp) {
		super();
		this.email = email;
		this.otp = otp;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
