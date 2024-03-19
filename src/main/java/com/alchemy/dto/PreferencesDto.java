package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class PreferencesDto {
	@NotNull(message = ErrorMessageCode.GPL_FUNCTION_REQUIRED + "*" + ErrorMessageKey.GPLFUNCTION_E031902)
	private Long functionId;

	@NotNull(message = ErrorMessageCode.GPL_DEPARTMENT_ID_REQUIRED + "*" + ErrorMessageKey.GPLDEPARTMENT_E0311002)
	private Long departmentId;

	@NotNull(message = ErrorMessageCode.GPL_ROLE_REQUIRED + "*" + ErrorMessageKey.GPLROLE_E0311103)
	private Long roleId;

	@NotBlank(message = ErrorMessageCode.GPL_EXPERIENCE_REQUIRED + "*" + ErrorMessageKey.CAREER_ASPIRATION_E034005)
	@NotEmpty(message = ErrorMessageCode.GPL_EXPERIENCE_REQUIRED + "*" + ErrorMessageKey.CAREER_ASPIRATION_E034005)
	@NotNull(message = ErrorMessageCode.GPL_EXPERIENCE_REQUIRED + "*" + ErrorMessageKey.CAREER_ASPIRATION_E034005)
	@Size(max = Constant.PREFERANCE_MESSAGE_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_DESCRIPTION
			+ "*" + ErrorMessageKey.PREFERENCE_E034102)
	private String experience;

	public PreferencesDto() {
		super();

	}

	public PreferencesDto(Long functionId, Long departmentId, Long roleId, String experience) {
		super();
		this.functionId = functionId;
		this.departmentId = departmentId;
		this.roleId = roleId;
		this.experience = experience;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

}
