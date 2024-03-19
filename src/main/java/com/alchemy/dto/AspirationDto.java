package com.alchemy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class AspirationDto {

//	@NotBlank(message = ErrorMessageCode.EMPLOYEE_DETAILS_REQUIRED + "*" + ErrorMessageKey.CAREER_ASPIRATION_E034006)
//	@NotEmpty(message = ErrorMessageCode.EMPLOYEE_DETAILS_REQUIRED + "*" + ErrorMessageKey.CAREER_ASPIRATION_E034006)
//	@NotNull(message = ErrorMessageCode.EMPLOYEE_DETAILS_REQUIRED + "*" + ErrorMessageKey.CAREER_ASPIRATION_E034006)
	@Size(max = Constant.CAREERASPIRATION_MESSAGE_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_DESCRIPTION
			+ "*" + ErrorMessageKey.CAREER_ASPIRATION_E034002)
	private String details;

	@NotNull(message = ErrorMessageCode.CAREER_ASPIRATION_STATUS_NEXTCAREERMOVE + "*"
			+ ErrorMessageKey.CAREER_ASPIRATION_E034005)
	private Boolean nextcareerMove;

	private boolean fileUpdate;

	@NotNull(message = ErrorMessageCode.GPL_FUNCTION_REQUIRED + "*" + ErrorMessageKey.GPLFUNCTION_E031902)
	private Long functionId1;

	@NotNull(message = ErrorMessageCode.GPL_DEPARTMENT_ID_REQUIRED + "*" + ErrorMessageKey.GPLDEPARTMENT_E0311002)
	private Long departmentId1;

	@NotNull(message = ErrorMessageCode.GPL_ROLE_REQUIRED + "*" + ErrorMessageKey.GPLROLE_E0311103)
	private Long roleId1;

	@NotBlank(message = ErrorMessageCode.GPL_EXPERIENCE_REQUIRED + "*" + ErrorMessageKey.CAREER_ASPIRATION_E034005)
	@NotEmpty(message = ErrorMessageCode.GPL_EXPERIENCE_REQUIRED + "*" + ErrorMessageKey.CAREER_ASPIRATION_E034005)
	@NotNull(message = ErrorMessageCode.GPL_EXPERIENCE_REQUIRED + "*" + ErrorMessageKey.CAREER_ASPIRATION_E034005)
	@Size(max = Constant.PREFERANCE_MESSAGE_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_DESCRIPTION
			+ "*" + ErrorMessageKey.PREFERENCE_E034102)
	private String experience1;

	private Long functionId2;

	private Long departmentId2;

	private Long roleId2;

	private String experience2;

	private Long city1;

	private Long city2;

	private Long city3;

	public AspirationDto() {
		super();
	}

	public boolean isFileUpdate() {
		return fileUpdate;
	}

	public void setFileUpdate(boolean fileUpdate) {
		this.fileUpdate = fileUpdate;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Boolean getNextcareerMove() {
		return nextcareerMove;
	}

	public void setNextcareerMove(Boolean nextcareerMove) {
		this.nextcareerMove = nextcareerMove;
	}

	public Long getFunctionId1() {
		return functionId1;
	}

	public void setFunctionId1(Long functionId1) {
		this.functionId1 = functionId1;
	}

	public Long getDepartmentId1() {
		return departmentId1;
	}

	public void setDepartmentId1(Long departmentId1) {
		this.departmentId1 = departmentId1;
	}

	public Long getRoleId1() {
		return roleId1;
	}

	public void setRoleId1(Long roleId1) {
		this.roleId1 = roleId1;
	}

	public String getExperience1() {
		return experience1;
	}

	public void setExperience1(String experience1) {
		this.experience1 = experience1;
	}

	public Long getFunctionId2() {
		return functionId2;
	}

	public void setFunctionId2(Long functionId2) {
		this.functionId2 = functionId2;
	}

	public Long getDepartmentId2() {
		return departmentId2;
	}

	public void setDepartmentId2(Long departmentId2) {
		this.departmentId2 = departmentId2;
	}

	public Long getRoleId2() {
		return roleId2;
	}

	public void setRoleId2(Long roleId2) {
		this.roleId2 = roleId2;
	}

	public String getExperience2() {
		return experience2;
	}

	public void setExperience2(String experience2) {
		this.experience2 = experience2;
	}

	public Long getCity1() {
		return city1;
	}

	public void setCity1(Long city1) {
		this.city1 = city1;
	}

	public Long getCity2() {
		return city2;
	}

	public void setCity2(Long city2) {
		this.city2 = city2;
	}

	public Long getCity3() {
		return city3;
	}

	public void setCity3(Long city3) {
		this.city3 = city3;
	}

}
