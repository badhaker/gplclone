package com.alchemy.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OnboardDto {

	private UUID uuid;
	@NotBlank(message = "password is Required")
	@NotEmpty(message = "password is Required")
	@NotNull(message = "password is Required")
	private String password;

	@NotBlank(message = "password is Required")
	@NotEmpty(message = "password is Required")
	@NotNull(message = "password is Required")
	private String confirmPassword;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public OnboardDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OnboardDto(UUID uuid, String password, String confirmPassword) {
		super();
		this.uuid = uuid;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

}
