package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class GplRoleDto {

	@NotBlank(message = ErrorMessageCode.GPL_ROLE_REQUIRED + "*" + ErrorMessageKey.GPLROLE_E0311102)
	@NotEmpty(message = ErrorMessageCode.GPL_ROLE_REQUIRED + "*" + ErrorMessageKey.GPLROLE_E0311102)
	@NotNull(message = ErrorMessageCode.GPL_ROLE_REQUIRED + "*" + ErrorMessageKey.GPLROLE_E0311102)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.GPLROLE_E0311102)

	private String name;

	@NotNull(message = ErrorMessageCode.GPL_DEPARTMENT_ID_REQUIRED + "*" + ErrorMessageKey.GPLDEPARTMENT_E0311002)
	private Long gplDepartmentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGplDepartmentId() {
		return gplDepartmentId;
	}

	public void setGplDepartmentId(Long gplDepartmentId) {
		this.gplDepartmentId = gplDepartmentId;
	}

}
