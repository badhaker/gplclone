package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class SponsorDto {

	@NotBlank(message = ErrorMessageCode.SPONSER_NAME_REQUIRED + "*" + ErrorMessageKey.SPONSER_E032702)
	@NotEmpty(message = ErrorMessageCode.SPONSER_NAME_REQUIRED + "*" + ErrorMessageKey.SPONSER_E032702)
	@NotNull(message = ErrorMessageCode.SPONSER_NAME_REQUIRED + "*" + ErrorMessageKey.SPONSER_E032702)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.SPONSER_E032703)
	private String name;

	@NotBlank(message = ErrorMessageCode.SPONSOR_DESCRIPTION_REQUIRED + "*" + ErrorMessageKey.SPONSER_E032702)
	@NotEmpty(message = ErrorMessageCode.SPONSOR_DESCRIPTION_REQUIRED + "*" + ErrorMessageKey.SPONSER_E032702)
	@NotNull(message = ErrorMessageCode.SPONSOR_DESCRIPTION_REQUIRED + "*" + ErrorMessageKey.SPONSER_E032702)
	private String description;

	@NotNull(message = ErrorMessageCode.DESIGNATION_ID_REQUIRED + "*" + ErrorMessageKey.SPONSER_E032702)
	private Long designationId;

//	@NotBlank(message = ErrorMessageCode.SPONSOR_PROFILE_REQUIRED + "*" + ErrorMessageKey.SPONSOR_E032705)
//	@NotEmpty(message = ErrorMessageCode.SPONSOR_PROFILE_REQUIRED + "*" + ErrorMessageKey.SPONSOR_E032705)
//	@NotNull(message = ErrorMessageCode.SPONSOR_PROFILE_REQUIRED + "*" + ErrorMessageKey.SPONSOR_E032705)
	@Size(max = Constant.PROFILE_LENGTH, message = ErrorMessageCode.SPONSOR_PROFILE_ACCEPT_CHARACTER + "*"
			+ ErrorMessageKey.SPONSOR_E032704)
	private String profile;

	private boolean fileUpdate;

	public boolean isFileUpdate() {
		return fileUpdate;
	}

	public void setFileUpdate(boolean fileUpdate) {
		this.fileUpdate = fileUpdate;
	}

	private Long user;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

}
