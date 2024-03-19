package com.alchemy.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class AssignRole {

	
	private Long userId;

	@NotEmpty(message = ErrorMessageCode.ROLE_ID_REQUIRED + "*" + ErrorMessageKey.ROLE_E031204)
	@NotNull(message = ErrorMessageCode.ROLE_ID_REQUIRED + "*" + ErrorMessageKey.ROLE_E031204)
	private  ArrayList<Long> roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ArrayList<Long> getRoleId() {
		return roleId;
	}

	public void setRoleId(ArrayList<Long> roleId) {
		this.roleId = roleId;
	}

	public AssignRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssignRole(Long userId, ArrayList<Long> roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

	
	
}
