package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class BusinessUnitDto {

	@NotEmpty(message = ErrorMessageCode.BUSINESS_UNIT_NAME_REQUIRED + "*" + ErrorMessageKey.BUSINESS_UNIT_E032503)
	@NotBlank(message = ErrorMessageCode.BUSINESS_UNIT_NAME_REQUIRED + "*" + ErrorMessageKey.BUSINESS_UNIT_E032503)
	@NotNull(message = ErrorMessageCode.BUSINESS_UNIT_NAME_REQUIRED + "*" + ErrorMessageKey.BUSINESS_UNIT_E032503)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.LEVEL_E031404)
	private String name;
	
	@Size(max = Constant.DESCRIPTION_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_DESCRIPTION + "*"
			+ ErrorMessageKey.LEVEL_E031404)
	private String description;

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

	public BusinessUnitDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessUnitDto(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	
}
