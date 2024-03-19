package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class TrainersMasterDto {

	@NotBlank(message = ErrorMessageCode.TRAINER_NAME_REQUIRED + "*" + ErrorMessageKey.TRAINER_E032902)
	@NotEmpty(message = ErrorMessageCode.TRAINER_NAME_REQUIRED + "*" + ErrorMessageKey.TRAINER_E032902)
	@NotNull(message = ErrorMessageCode.TRAINER_NAME_REQUIRED + "*" + ErrorMessageKey.TRAINER_E032902)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.TRAINER_E032903)
	private String name;

	@NotBlank(message = ErrorMessageCode.DESCRIPTION_REQUIRED + "*" + ErrorMessageKey.TRAINER_E032902)
	@NotEmpty(message = ErrorMessageCode.DESCRIPTION_REQUIRED + "*" + ErrorMessageKey.TRAINER_E032902)
	@NotNull(message = ErrorMessageCode.DESCRIPTION_REQUIRED + "*" + ErrorMessageKey.TRAINER_E032902)
	private String description;

	@NotNull(message = ErrorMessageCode.DESIGNATION_ID_REQUIRED + "*" + ErrorMessageKey.SPONSER_E032702)
	private Long designationId;

	private Long user;

	private boolean fileUpdate;

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

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public boolean isFileUpdate() {
		return fileUpdate;
	}

	public void setFileUpdate(boolean fileUpdate) {
		this.fileUpdate = fileUpdate;
	}

}
