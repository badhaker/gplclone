package com.alchemy.dto;

public class RolePermissionDto {

	private Long id;

	private String roleName;

	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
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

	public RolePermissionDto(Long id, String roleName, String description) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.description = description;
	}

	public RolePermissionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
