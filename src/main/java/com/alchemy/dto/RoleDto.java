package com.alchemy.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.alchemy.utils.ErrorMessageCode;
import com.alchemy.utils.ErrorMessageKey;

public class RoleDto {

	@NotBlank(message = ErrorMessageCode.ROLE_NAME_REQUIRED + "*" + ErrorMessageKey.ROLE_E031204)
	@NotEmpty(message = ErrorMessageCode.ROLE_NAME_REQUIRED + "*" + ErrorMessageKey.ROLE_E031204)
	@NotNull(message = ErrorMessageCode.ROLE_NAME_REQUIRED + "*" + ErrorMessageKey.ROLE_E031204)
	private String roleName;

	private String description;
	
	List<Long> permissionId = new ArrayList<>();

	public List<Long> getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(List<Long> permissionId) {
		this.permissionId = permissionId;
	}

	public String getRoleName() {
		return roleName.trim();
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RoleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoleDto( String roleName,String description, List<Long> permissionId) {
		super();
		this.roleName = roleName;
		this.description = description;
		this.permissionId = permissionId;
	}

	
	
	
}
