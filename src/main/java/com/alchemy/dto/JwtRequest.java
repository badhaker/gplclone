package com.alchemy.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class JwtRequest implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = ErrorMessageCode.EMAIL_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	@NotEmpty(message = ErrorMessageCode.EMAIL_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	@NotNull(message = ErrorMessageCode.EMAIL_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	private String email;

	@NotBlank(message = ErrorMessageCode.PASSWORD_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	@NotEmpty(message = ErrorMessageCode.PASSWORD_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	@NotNull(message = ErrorMessageCode.PASSWORD_REQUIRED + "*" + ErrorMessageKey.USER_E031204)
	private String password;

	public JwtRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
