package com.alchemy.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class AssignPermission {

	private Long roleId;
	
	@NotEmpty(message = ErrorMessageCode.PERMISSION_ID_REQUIRED + "*" + ErrorMessageKey.PERMISSION_E031304)
	@NotNull(message = ErrorMessageCode.PERMISSION_ID_REQUIRED + "*" + ErrorMessageKey.PERMISSION_E031304)
	private ArrayList<Long> permissionId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public ArrayList<Long> getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(ArrayList<Long> permissionId) {
		this.permissionId = permissionId;
	}

	public AssignPermission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssignPermission(Long roleId, ArrayList<Long> permissionId) {
		super();
		this.roleId = roleId;
		this.permissionId = permissionId;
	}
	
	
}
