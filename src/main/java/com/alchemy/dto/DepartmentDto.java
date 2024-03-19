package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.Validator;

public class DepartmentDto {

	@NotEmpty(message= ErrorMessageCode.DEPARTMENT_NAME_CANNOT_BE_NULL + "*" + ErrorMessageKey.DEPARTMENT_E031603)
	@NotNull(message= ErrorMessageCode.DEPARTMENT_NAME_CANNOT_BE_NULL + "*" + ErrorMessageKey.DEPARTMENT_E031603)
	@NotBlank(message= ErrorMessageCode.DEPARTMENT_NAME_CANNOT_BE_NULL + "*" + ErrorMessageKey.DEPARTMENT_E031603) 
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.DEPARTMENT_E031604)
	@Pattern(regexp = Validator.NAME_PATTERN, message = ErrorMessageCode.INVALID_DEPARTMENT_NAME + "*" + ErrorMessageKey.DEPARTMENT_E031603 )
	private String name;
	
	@Size(max = Constant.DESCRIPTION_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_DESCRIPTION + "*"
			+ ErrorMessageKey.DEPARTMENT_E031604)
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

	public DepartmentDto(
			@NotEmpty(message = "Department name cannot be null*GPL-E031603") @NotNull(message = "Department name cannot be null*GPL-E031603") @NotBlank(message = "Department name cannot be null*GPL-E031603") String name,
			String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public DepartmentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
