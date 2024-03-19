package com.alchemy.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.alchemy.entities.GenderEnum;
import com.alchemy.utils.Constant;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;
import com.alchemy.utils.Validator;

public class UserDto {

	@NotBlank(message = ErrorMessageCode.USERNAME_REQUIRED + "*" + ErrorMessageKey.USER_E031201)
	@NotEmpty(message = ErrorMessageCode.USERNAME_REQUIRED + "*" + ErrorMessageKey.USER_E031201)
	@NotNull(message = ErrorMessageCode.USERNAME_REQUIRED + "*" + ErrorMessageKey.USER_E031201)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_FOR_NAME + "*"
			+ ErrorMessageKey.USER_E031201)
	@Pattern(regexp = Validator.NAME_PATTERN, message = ErrorMessageCode.INVALID_NAME + "*"
			+ ErrorMessageKey.DEPARTMENT_E031603)
	private String name;

	@NotBlank(message = ErrorMessageCode.EMAIL_REQUIRED + "*" + ErrorMessageKey.USER_E031201)
	@NotEmpty(message = ErrorMessageCode.EMAIL_REQUIRED + "*" + ErrorMessageKey.USER_E031201)
	@NotNull(message = ErrorMessageCode.EMAIL_REQUIRED + "*" + ErrorMessageKey.USER_E031201)
	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_EMAIL + "*"
			+ ErrorMessageKey.USER_E031201)
	@Pattern(regexp = Validator.MAIL_PATTERN, message = ErrorMessageCode.INVALID_EMAIL + "*"
			+ ErrorMessageKey.USER_E031201)
	private String email;

//	@NotBlank(message = ErrorMessageCode.GENDER_REQUIRED + "*" + ErrorMessageKey.USER_E031201)
//	@NotEmpty(message = ErrorMessageCode.GENDER_REQUIRED + "*" + ErrorMessageKey.USER_E031201)
//	@NotNull(message = ErrorMessageCode.GENDER_REQUIRED + "*" + ErrorMessageKey.USER_E031201)
//	@Size(max = Constant.NAME_LENGTH, message = ErrorMessageCode.CHARACTER_VALIDATION_GENDER + "*"
//			+ ErrorMessageKey.USER_E031201)
	private GenderEnum gender;

	private String phoneNumber;

	@NotEmpty(message = ErrorMessageCode.ROLE_REQUIRED + "*" + ErrorMessageKey.USER_E031201)
	@Size(min = 1, message = "ArrayList must contain at least one element")
	private ArrayList<Long> roleId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public ArrayList<Long> getRoleId() {
		return roleId;
	}

	public void setRoleId(ArrayList<Long> roleId) {
		this.roleId = roleId;
	}

//	public Long getDepartmentId() {
//		return departmentId;
//	}
//
//	public void setDepartmentId(Long departmentId) {
//		this.departmentId = departmentId;
//	}
//
//	public Long getDesignationId() {
//		return designationId;
//	}
//
//	public void setDesignationId(Long designationId) {
//		this.designationId = designationId;
//	}
//
//	public Long getLevelId() {
//		return levelId;
//	}
//
//	public void setLevelId(Long levelId) {
//		this.levelId = levelId;
//	}

}
