package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class GplDepartmentDto {

	@NotBlank(message = ErrorMessageCode.GPL_DEPARTMENT_REQUIRED + "*" + ErrorMessageKey.GPLDEPARTMENT_E0311002)
	@NotEmpty(message = ErrorMessageCode.GPL_DEPARTMENT_REQUIRED + "*" + ErrorMessageKey.GPLDEPARTMENT_E0311002)
	@NotNull(message = ErrorMessageCode.GPL_DEPARTMENT_REQUIRED + "*" + ErrorMessageKey.GPLDEPARTMENT_E0311002)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.GPLDEPARTMENT_E0311002)

	private String name;

	@NotNull(message = ErrorMessageCode.GPL_FUNCTION_ID_REQUIRED + "*" + ErrorMessageKey.GPLFUNCTION_E031902)
	private Long gplFunctionId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGplFunctionId() {
		return gplFunctionId;
	}

	public void setGplFunctionId(Long gplFunctionId) {
		this.gplFunctionId = gplFunctionId;
	}

}
