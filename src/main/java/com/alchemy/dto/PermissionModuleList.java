package com.alchemy.dto;

import java.util.List;

public class PermissionModuleList {

	public Long moduleId;

	public String moduleName;

	List<PermissionList> permissionList;

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<PermissionList> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<PermissionList> permissionList) {
		this.permissionList = permissionList;
	}

}
