package com.alchemy.dto;

import java.util.ArrayList;

import com.alchemy.entities.GenderEnum;

public class UserUpdateDto {
	private String name;

	private GenderEnum gender;

	private String phoneNumber;

	private ArrayList<Long> roleId;

//	@NotNull(message = ErrorMessageCode.DEPARTMENT_ID_REQUIRED + "*" + ErrorMessageKey.USER_E031203)
//	private Long departmentId;
//
//	@NotNull(message = ErrorMessageCode.DESIGNATION_ID_REQUIRED + "*" + ErrorMessageKey.USER_E031203)
//	private Long designationId;
//
//	@NotNull(message = ErrorMessageCode.LEVEL_ID_REQUIRED + "*" + ErrorMessageKey.USER_E031203)
//	private Long levelId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

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
