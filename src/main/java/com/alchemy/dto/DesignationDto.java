package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class DesignationDto {

	@NotBlank(message = ErrorMessageCode.DESIGNATION_REQUIRED + "*" + ErrorMessageKey.DESIGNATION_E031303)
	@NotEmpty(message = ErrorMessageCode.DESIGNATION_REQUIRED + "*" + ErrorMessageKey.DESIGNATION_E031303)
	@NotNull(message = ErrorMessageCode.DESIGNATION_REQUIRED + "*" + ErrorMessageKey.DESIGNATION_E031303)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.DESIGNATION_E031304)
	private String name;

	@Size(max = Constant.DESCRIPTION_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_DESCRIPTION + "*"
			+ ErrorMessageKey.DESIGNATION_E031304)
	@Size(max = Constant.DESCRIPTION_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_DESCRIPTION + "*"
			+ ErrorMessageKey.BANNER_E032105)
	private String description;

	public String getName() {
		return name.trim();
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

	public DesignationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignationDto(
			@NotBlank(message = "Designation name required*GPL-E031503") @NotEmpty(message = "Designation name required*GPL-E031503") @NotNull(message = "Designation name required*GPL-E031503") @Size(max = 100, message = "Name should not be greater than 100 characters*GPL-E031504") String name,
			@Size(max = 250, message = "Name should not be greater than 100 characters*GPL-E031504") String description) {
		super();
		this.name = name;
		this.description = description;
	}

}
